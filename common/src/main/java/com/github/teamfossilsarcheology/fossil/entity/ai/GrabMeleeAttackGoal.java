package com.github.teamfossilsarcheology.fossil.entity.ai;

import com.github.teamfossilsarcheology.fossil.entity.animation.ServerAnimationInfo;
import com.github.teamfossilsarcheology.fossil.entity.prehistoric.base.OrderType;
import com.github.teamfossilsarcheology.fossil.entity.prehistoric.base.PrehistoricSwimming;
import com.github.teamfossilsarcheology.fossil.entity.util.Util;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;

public class GrabMeleeAttackGoal extends DelayedAttackGoal<PrehistoricSwimming> {
    private static final int ATTACK = 0;
    private static final int GRAB = 1;
    private static final int GRAB_DURATION = 55;
    private LivingEntity attackTarget;
    private int attackType = -1;
    private long grabStartTick = -1;

    public GrabMeleeAttackGoal(PrehistoricSwimming entity, double speed, boolean followTargetEvenIfNotSeen) {
        super(entity, speed, followTargetEvenIfNotSeen);
    }

    @Override
    public boolean canContinueToUse() {
        if (attackType == ATTACK) {
            return super.canContinueToUse();
        }
        if (attackTarget == null || attackTarget.isRemoved() || mob.getCurrentOrder() == OrderType.STAY) {
            return false;
        }
        if (attackType == GRAB && (!mob.isDoingGrabAttack() || !mob.hasPassenger(attackTarget))) {
            attackEndTick = mob.level().getGameTime() + 20;
            return false;
        }
        return CAN_ATTACK_TARGET.test(attackTarget);
    }

    @Override
    public void start() {
        super.start();
        attackTarget = mob.getTarget();
        attackType = -1;
        grabStartTick = -1;
    }

    @Override
    public void stop() {
        super.stop();
        mob.setDoingGrabAttack(false);
    }

    @Override
    protected boolean canUpdateMovement() {
        return attackType != GRAB;
    }

    @Override
    protected void checkAndPerformAttack(LivingEntity enemy, boolean inRange) {
        long currentTime = mob.level().getGameTime();
        if (attackType == GRAB) {
            for (Entity passenger : mob.getPassengers()) {
                if (passenger instanceof LivingEntity && passenger != mob.getRidingPlayer()) {
                    if (mob.tickCount % 20 == 0) {
                        boolean hurt = passenger.hurt(mob.damageSources().mobAttack(mob), (float) mob.getAttributeValue(Attributes.ATTACK_DAMAGE));
                        if (!hurt || (currentTime >= grabStartTick + GRAB_DURATION && mob.getRandom().nextInt(5) == 0)) {
                            attackEndTick = currentTime + 20;
                            mob.stopGrabAttack(passenger);
                        }
                    }
                }
            }
        } else if (attackType == ATTACK) {
            if (inRange && attackDamageTick > 0 && currentTime >= attackDamageTick) {
                mob.attackTarget(enemy);
                mob.destroyBoat(enemy);
                attackDamageTick = -1;
                attackType = -1;
            }
        } else if (currentTime > attackEndTick + 20 && inRange) {
            //Is target smaller than 1 block (if prehistoric is adult)
            boolean tooBig = Util.isEntityLargerThan(enemy, mob.grabTargetSize());
            if (tooBig || mob.getRandom().nextInt(5) > 0 || mob.getVehicle() == enemy) {
                attackType = ATTACK;
                ServerAnimationInfo animationInfo = mob.startAttack();
                attackEndTick = (long) (currentTime + animationInfo.animation.length());
                attackDamageTick = Math.min((long) (currentTime + animationInfo.actionDelay), attackEndTick);
            } else {
                attackType = GRAB;
                mob.destroyBoat(enemy);
                mob.startGrabAttack(enemy);
                //TODO: Grab attack needs to be delayed and probably only start when looking at player?
                attackEndTick = 1;
                grabStartTick = currentTime;
            }
        }
    }
}
