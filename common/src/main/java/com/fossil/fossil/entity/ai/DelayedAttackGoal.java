package com.fossil.fossil.entity.ai;

import com.fossil.fossil.entity.ai.control.TestLookControl;
import com.fossil.fossil.entity.animation.AnimationInfoManager;
import com.fossil.fossil.entity.prehistoric.base.Prehistoric;
import com.fossil.fossil.network.MessageHandler;
import com.fossil.fossil.network.S2CActivateAttackBoxesMessage;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.Difficulty;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.pathfinder.Path;

import java.util.List;
import java.util.function.Predicate;

/**
 * Similar to {@link net.minecraft.world.entity.ai.goal.MeleeAttackGoal MeleeAttackGoal} but with the option to delay
 * the damage by {@link com.fossil.fossil.entity.animation.AnimationInfoManager.ServerAnimationInfo#actionDelay} ticks
 * or defer the hit logic to the targets client
 */
public class DelayedAttackGoal extends Goal {
    private static final Predicate<Entity> CAN_ATTACK_TARGET = target -> !(target instanceof Player player) || (!player.isSpectator() && !player.isCreative() && target.level.getDifficulty() != Difficulty.PEACEFUL);
    private static final long COOLDOWN_BETWEEN_CAN_USE_CHECKS = 20L;
    protected final Prehistoric prehistoric;
    private final double speedModifier;
    private final boolean followingTargetEvenIfNotSeen;
    private Path path;
    private double pathedTargetX;
    private double pathedTargetY;
    private double pathedTargetZ;
    private int ticksUntilNextPathRecalculation;
    private long lastCanUseCheck;
    protected long attackEndTick = -1;
    protected long attackDamageTick = -1;


    public DelayedAttackGoal(Prehistoric prehistoric, double speedModifier, boolean followingTargetEvenIfNotSeen) {
        this.prehistoric = prehistoric;
        this.speedModifier = speedModifier;
        this.followingTargetEvenIfNotSeen = followingTargetEvenIfNotSeen;
    }


    @Override
    public boolean canUse() {
        long l = prehistoric.level.getGameTime();
        if (l - lastCanUseCheck < COOLDOWN_BETWEEN_CAN_USE_CHECKS) {
            return false;
        }
        lastCanUseCheck = l;
        if (prehistoric.isImmobile() || prehistoric.isFleeing()) {
            return false;
        }
        LivingEntity target = prehistoric.getTarget();
        if (target == null || !target.isAlive()) {
            return false;
        }
        if (prehistoric.level.getDifficulty() == Difficulty.PEACEFUL && target instanceof Player) {
            return false;
        }
        path = prehistoric.getNavigation().createPath(target, 0);
        if (path != null) {
            return true;
        }
        return getAttackReachSqr(target) >= prehistoric.distanceToSqr(target);
    }

    @Override
    public boolean canContinueToUse() {
        if (prehistoric.level.getGameTime() < attackEndTick) {
            //Prevent the goal from ending before the animation is over
            return true;
        }
        LivingEntity target = prehistoric.getTarget();
        if (target == null || !target.isAlive()) {
            return false;
        }
        if (!followingTargetEvenIfNotSeen) {
            return !prehistoric.getNavigation().isDone();
        }
        if (!prehistoric.isWithinRestriction(target.blockPosition())) {
            return false;
        }
        if (!CAN_ATTACK_TARGET.test(target)) {
            return false;
        }
        return true;
    }

    protected double getAttackReachSqr(LivingEntity attackTarget) {
        return prehistoric.getBbWidth() * 2 * (prehistoric.getBbWidth() * 2) + attackTarget.getBbWidth();
    }

    @Override
    public void start() {
        prehistoric.getNavigation().moveTo(path, speedModifier);
        prehistoric.setAggressive(true);
        ticksUntilNextPathRecalculation = 0;
        attackEndTick = -1;
        attackDamageTick = -1;
    }

    @Override
    public void stop() {
        LivingEntity target = prehistoric.getTarget();
        if (!CAN_ATTACK_TARGET.test(target)) {
            prehistoric.setTarget(null);
        }
        prehistoric.setAggressive(false);
        prehistoric.getNavigation().stop();
    }

    @Override
    public boolean requiresUpdateEveryTick() {
        return true;
    }

    @Override
    public void tick() {
        LivingEntity target = prehistoric.getTarget();
        if (target == null) {
            return;
        }
        prehistoric.getLookControl().setLookAt(target, 30, 30);
        double dist = prehistoric.distanceToSqr(target);
        ticksUntilNextPathRecalculation = Math.max(ticksUntilNextPathRecalculation - 1, 0);
        if (prehistoric.getLookControl() instanceof TestLookControl moveControl) {
            moveControl.start();
        }
        if (prehistoric.level.getGameTime() <= attackEndTick && prehistoric.getLookControl() instanceof TestLookControl moveControl) {
            //TODO: prevent only on certain attack(animationdata)
            //moveControl.setLookAt(0, 0, 0);
            //moveControl.stop();
            //((SmoothTurningMoveControl)prehistoric.getMoveControl()).stop();
        }else
        if ((followingTargetEvenIfNotSeen || prehistoric.getSensing().hasLineOfSight(target)) && ticksUntilNextPathRecalculation <= 0 && (pathedTargetX == 0.0 && pathedTargetY == 0.0 && pathedTargetZ == 0.0 || target.distanceToSqr(pathedTargetX, pathedTargetY, pathedTargetZ) >= 1.0 || prehistoric.getRandom().nextFloat() < 0.05f)) {
            pathedTargetX = target.getX();
            pathedTargetY = target.getY();
            pathedTargetZ = target.getZ();
            ticksUntilNextPathRecalculation = 4 + prehistoric.getRandom().nextInt(7);
            if (dist > 1024) {
                ticksUntilNextPathRecalculation += 10;
            } else if (dist > 256) {
                ticksUntilNextPathRecalculation += 5;
            }
            if (!prehistoric.getNavigation().moveTo(target, speedModifier)) {
                ticksUntilNextPathRecalculation += 15;
            }
            ticksUntilNextPathRecalculation = adjustedTickDelay(ticksUntilNextPathRecalculation);
        }
        checkAndPerformAttack(target, dist);
    }

    protected void checkAndPerformAttack(LivingEntity enemy, double distToEnemySqr) {
        //TODO: Use attackreach in all places
        double attackReach = prehistoric.getBbWidth() * prehistoric.getBbWidth() * 2 + enemy.getBbWidth();
        long currentTime = prehistoric.level.getGameTime();
        if (distToEnemySqr <= attackReach) {
            if (currentTime > attackEndTick) {
                AnimationInfoManager.ServerAnimationInfo animation = prehistoric.startAttack();
                if (animation.usesAttackBox && enemy instanceof ServerPlayer player) {
                    MessageHandler.SYNC_CHANNEL.sendToPlayers(List.of(player), new S2CActivateAttackBoxesMessage(prehistoric, animation.animationLength));
                } else {
                    attackDamageTick = (long) (currentTime + animation.actionDelay);
                }
                attackEndTick = (long) (currentTime + animation.animationLength + 20);
                //TODO: prevent only on certain attack(animationdata)
                //prehistoric.getNavigation().stop();
                //prehistoric.getLookControl().setLookAt(enemy);
                //prehistoric.getMoveControl().setWantedPosition(prehistoric.getX(), prehistoric.getY(), prehistoric.getZ(), 1);
                if (attackDamageTick > attackEndTick) attackDamageTick = attackEndTick;
            }
            if (attackDamageTick > 0 && currentTime == attackDamageTick) {
                prehistoric.attackTarget(enemy);
                attackDamageTick = -1;
            }
        }
    }
}
