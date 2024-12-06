package com.github.teamfossilsarcheology.fossil.entity.ai;

import com.github.teamfossilsarcheology.fossil.entity.animation.ServerAnimationInfo;
import com.github.teamfossilsarcheology.fossil.entity.prehistoric.base.OrderType;
import com.github.teamfossilsarcheology.fossil.entity.prehistoric.base.Prehistoric;
import com.github.teamfossilsarcheology.fossil.entity.util.Util;
import com.github.teamfossilsarcheology.fossil.network.MessageHandler;
import com.github.teamfossilsarcheology.fossil.network.S2CActivateAttackBoxesMessage;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.Difficulty;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.pathfinder.Path;

import java.util.EnumSet;
import java.util.List;
import java.util.function.Predicate;

/**
 * Similar to {@link net.minecraft.world.entity.ai.goal.MeleeAttackGoal MeleeAttackGoal} but with the option to delay
 * the damage by {@link ServerAnimationInfo#actionDelay} ticks or defer the hit logic to the targets client
 */
public class DelayedAttackGoal<T extends Prehistoric> extends Goal {
    protected static final Predicate<Entity> CAN_ATTACK_TARGET = target -> !(target instanceof Player player) || (!player.isSpectator() && !player.isCreative() && target.level.getDifficulty() != Difficulty.PEACEFUL);
    protected static final long COOLDOWN_BETWEEN_CAN_USE_CHECKS = 20L;
    protected final T mob;
    private final double speedModifier;
    private final boolean followingTargetEvenIfNotSeen;
    protected Path path;
    private double pathedTargetX;
    private double pathedTargetY;
    private double pathedTargetZ;
    private int ticksUntilNextPathRecalculation;
    protected long lastCanUseCheck;
    protected long attackEndTick = -1;
    protected long attackDamageTick = -1;
    private boolean doingHeavyAttack;


    public DelayedAttackGoal(T mob, double speedModifier, boolean followingTargetEvenIfNotSeen) {
        this.mob = mob;
        this.speedModifier = speedModifier;
        this.followingTargetEvenIfNotSeen = followingTargetEvenIfNotSeen;
        setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK));
    }


    @Override
    public boolean canUse() {
        long l = mob.level.getGameTime();
        if (l - lastCanUseCheck < COOLDOWN_BETWEEN_CAN_USE_CHECKS) {
            return false;
        }
        lastCanUseCheck = l;
        if (mob.isFleeing() || mob.getCurrentOrder() == OrderType.STAY) {
            return false;
        }
        LivingEntity target = mob.getTarget();
        if (target == null || !target.isAlive()) {
            return false;
        }
        if (mob.level.getDifficulty() == Difficulty.PEACEFUL && target instanceof Player) {
            return false;
        }
        path = mob.getNavigation().createPath(target, 0);
        if (path != null) {
            return true;
        }
        return isInRange(target);
    }

    @Override
    public boolean canContinueToUse() {
        if (mob.level.getGameTime() < attackEndTick) {
            //Prevent the goal from ending before the animation is over
            return true;
        }
        LivingEntity target = mob.getTarget();
        if (target == null || !target.isAlive() || mob.getCurrentOrder() == OrderType.STAY) {
            System.out.println("Cant continue attacking because: " + (target == null));
            return false;
        }
        if (!followingTargetEvenIfNotSeen) {
            return !mob.getNavigation().isDone();
        }
        if (!mob.isWithinRestriction(target.blockPosition())) {
            return false;
        }
        if (!CAN_ATTACK_TARGET.test(target)) {
            return false;
        }
        return true;
    }

    @Override
    public void start() {
        mob.getNavigation().moveTo(path, speedModifier);
        mob.setAggressive(true);
        ticksUntilNextPathRecalculation = 0;
        attackEndTick = -1;
        attackDamageTick = -1;
        doingHeavyAttack = false;
    }

    @Override
    public void stop() {
        LivingEntity target = mob.getTarget();
        if (!CAN_ATTACK_TARGET.test(target)) {
            mob.setTarget(null);
        }
        mob.setAggressive(false);
        mob.getNavigation().stop();
    }

    @Override
    public boolean requiresUpdateEveryTick() {
        return true;
    }

    @Override
    public void tick() {
        LivingEntity target = mob.getTarget();
        if (target == null) {
            return;
        }
        double dist = mob.distanceToSqr(target);
        ticksUntilNextPathRecalculation = Math.max(ticksUntilNextPathRecalculation - 1, 0);
        if (canUpdateMovement()) {
            mob.getLookControl().setLookAt(target, 30, 30);
        } else {
            ticksUntilNextPathRecalculation = 15;
        }
        if (mob.level.getGameTime() <= attackEndTick && doingHeavyAttack) {
            //Prevent movement
        } else if ((followingTargetEvenIfNotSeen || mob.getSensing().hasLineOfSight(target)) && ticksUntilNextPathRecalculation <= 0 && (pathedTargetX == 0.0 && pathedTargetY == 0.0 && pathedTargetZ == 0.0 || target.distanceToSqr(pathedTargetX, pathedTargetY, pathedTargetZ) >= 1.0 || mob.getRandom().nextFloat() < 0.05f)) {
            pathedTargetX = target.getX();
            pathedTargetY = target.getY();
            pathedTargetZ = target.getZ();
            ticksUntilNextPathRecalculation = 4 + mob.getRandom().nextInt(7);
            if (dist > 1024) {
                ticksUntilNextPathRecalculation += 10;
            } else if (dist > 256) {
                ticksUntilNextPathRecalculation += 5;
            }
            if (!mob.getNavigation().moveTo(target, speedModifier)) {
                ticksUntilNextPathRecalculation += 15;
            }
            ticksUntilNextPathRecalculation = adjustedTickDelay(ticksUntilNextPathRecalculation);
        }
        checkAndPerformAttack(target);
    }

    protected boolean canUpdateMovement() {
        return true;
    }

    protected boolean isInRange(Entity attackTarget) {
        return Util.canReachPrey(mob, attackTarget);
    }

    protected void checkAndPerformAttack(LivingEntity enemy) {
        long currentTime = mob.level.getGameTime();
        if (isInRange(enemy)) {
            if (currentTime > attackEndTick + 20) {
                ServerAnimationInfo animationInfo = mob.startAttack();
                if (animationInfo.usesAttackBox && enemy instanceof ServerPlayer player) {
                    MessageHandler.SYNC_CHANNEL.sendToPlayers(List.of(player), new S2CActivateAttackBoxesMessage(mob, animationInfo.animation.animationLength));
                    doingHeavyAttack = true;
                } else {
                    attackDamageTick = (long) (currentTime + animationInfo.actionDelay + 5);
                }
                attackEndTick = (long) (currentTime + animationInfo.animation.animationLength + 5);
                if (attackDamageTick > attackEndTick) attackDamageTick = attackEndTick;
            }
            if (attackDamageTick > 0 && currentTime == attackDamageTick) {
                mob.attackTarget(enemy);
                attackDamageTick = -1;
            }
        }
    }
}
