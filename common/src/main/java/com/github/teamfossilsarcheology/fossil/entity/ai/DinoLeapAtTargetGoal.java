package com.github.teamfossilsarcheology.fossil.entity.ai;

import com.github.teamfossilsarcheology.fossil.entity.animation.ServerAnimationInfo;
import com.github.teamfossilsarcheology.fossil.entity.prehistoric.base.PrehistoricLeaping;
import com.github.teamfossilsarcheology.fossil.entity.prehistoric.system.LeapSystem;
import com.github.teamfossilsarcheology.fossil.entity.util.Util;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;

import java.util.EnumSet;

public class DinoLeapAtTargetGoal extends DelayedAttackGoal<PrehistoricLeaping> {
    private static final int ATTACK = 0;
    private static final int LEAP = 1;
    private static final int ATTACK_RIDING = 2;
    private int attackType = -1;
    protected long leapAirTick = -1;

    public DinoLeapAtTargetGoal(PrehistoricLeaping dino) {
        super(dino, 1, false);
        this.setFlags(EnumSet.of(Flag.JUMP, Flag.MOVE, Flag.LOOK));
    }

    @Override
    public boolean canUse() {
        if (!mob.isOnGround()) {
            return false;
        }
        return super.canUse();
    }

    @Override
    public boolean canContinueToUse() {
        if (attackType == ATTACK_RIDING) {
            if (mob.getLeapSystem().isAttackRiding() && mob.getVehicle() == mob.getTarget()) {
                return true;
            } else {
                return false;
            }
        }
        if (attackType == ATTACK_RIDING && (!mob.getLeapSystem().isAttackRiding() || mob.getVehicle() != mob.getTarget())) {
            System.out.println("Cant continue attack riding: " + mob.getLeapSystem().isAttackRiding() + " " + (mob.getVehicle() == null));
            return false;
        }
        if (attackType == LEAP) {
            //TODO: Canceli f timeout
            return true;
        }
        return super.canContinueToUse();
    }

    @Override
    public void start() {
        super.start();
        attackType = -1;
        leapAirTick = -1;
    }

    @Override
    public void stop() {
        super.stop();
        mob.getLeapSystem().setAttackRiding(false);
        mob.stopRiding();
    }

    @Override
    protected boolean canUpdateMovement() {
        return attackType == ATTACK || attackType == -1;
    }

    protected void checkAndPerformAttack(LivingEntity enemy) {
        long currentTime = mob.level.getGameTime();
        if (attackType == ATTACK_RIDING) {
            if ((mob.tickCount) % 20 == 0) {
                enemy.hurt(DamageSource.mobAttack(mob), (float) mob.getAttributeValue(Attributes.ATTACK_DAMAGE));
                if (enemy instanceof Player) enemy.setHealth(enemy.getMaxHealth());
            }
        } else if (attackType == LEAP) {
        } else if (attackType == ATTACK) {
            if (Util.canReachPrey(mob, enemy) && attackDamageTick > 0 && currentTime >= attackDamageTick) {
                mob.attackTarget(enemy);
                attackDamageTick = -1;
                attackType = -1;
            }
        } else if (currentTime > attackEndTick + 20) {
            if (false && mob.getRandom().nextInt(5) == 0 && isInRange(enemy)) {//TODO: Random?
                attackType = ATTACK;
                ServerAnimationInfo animation = mob.startAttack();
                attackEndTick = (long) (currentTime + animation.animation.animationLength);
                attackDamageTick = Math.min((long) (currentTime + animation.actionDelay), attackEndTick);
            } else if (mob.distanceToSqr(enemy) < LeapSystem.JUMP_DISTANCE) {
                attackType = LEAP;
                mob.getLeapSystem().setLeapTarget(enemy);
            }
        }
    }

    public void doLeapMovement() {
        mob.lookAt(mob.getTarget(), 100, 100);
        Vec3 offset = mob.getTarget().position().subtract(mob.position()).add(0, mob.getTarget().getBbHeight(), 0);
        mob.setDeltaMovement(offset.normalize());
    }
}
