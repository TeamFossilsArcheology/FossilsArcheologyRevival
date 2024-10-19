package com.github.teamfossilsarcheology.fossil.entity.ai;

import com.github.teamfossilsarcheology.fossil.entity.animation.AnimationInfoLoader;
import com.github.teamfossilsarcheology.fossil.entity.prehistoric.base.OrderType;
import com.github.teamfossilsarcheology.fossil.entity.prehistoric.base.PrehistoricSwimming;
import com.github.teamfossilsarcheology.fossil.entity.util.Util;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.level.block.Blocks;

public class GrabMeleeAttackGoal extends DelayedAttackGoal {
    private static final int ATTACK = 0;
    private static final int GRAB = 1;
    private static final int GRAB_DURATION = 55;
    private final PrehistoricSwimming swimming;
    private int attackType = -1;
    private long grabStartTick = -1;

    public GrabMeleeAttackGoal(PrehistoricSwimming entity, double speed, boolean followTargetEvenIfNotSeen) {
        super(entity, speed, followTargetEvenIfNotSeen);
        this.swimming = entity;
    }

    @Override
    public boolean canUse() {
        LivingEntity target = prehistoric.getTarget();
        if (target != null && (target.isInWater() || target.isPassenger() && target.level.getBlockState(target.blockPosition()) == Blocks.WATER.defaultBlockState())) {
            return super.canUse();
        }
        return false;
    }

    @Override
    public boolean canContinueToUse() {
        if (attackType == ATTACK) {
            return super.canContinueToUse();
        }
        LivingEntity target = prehistoric.getTarget();
        if (target == null || !target.isAlive() || prehistoric.getCurrentOrder() == OrderType.STAY) {
            return false;
        }
        if (attackType == GRAB && (!swimming.isDoingGrabAttack() || !swimming.hasPassenger(target))) {
            return false;
        }
        return CAN_ATTACK_TARGET.test(target);
    }

    @Override
    public void start() {
        super.start();
        attackType = -1;
        grabStartTick = -1;
    }

    @Override
    public void stop() {
        super.stop();
        swimming.setDoingGrabAttack(false);
    }

    @Override
    protected boolean canUpdateMovement() {
        return attackType != GRAB;
    }

    @Override
    protected void checkAndPerformAttack(LivingEntity enemy) {
        long currentTime = swimming.level.getGameTime();
        if (attackType == GRAB) {
            for (Entity passenger : swimming.getPassengers()) {
                if (passenger instanceof LivingEntity && passenger != swimming.getRidingPlayer()) {
                    if (swimming.tickCount % 20 == 0) {
                        boolean hurt = passenger.hurt(DamageSource.mobAttack(swimming), (float) swimming.getAttributeValue(Attributes.ATTACK_DAMAGE));
                        if (!hurt || (currentTime >= grabStartTick + GRAB_DURATION && swimming.getRandom().nextInt(5) == 0)) {
                            attackEndTick = currentTime + 20;
                            swimming.stopGrabAttack(passenger);
                        }
                    }
                }
            }
        } else if (attackType == ATTACK) {
            if (Util.canReachPrey(swimming, enemy) && attackDamageTick > 0 && currentTime >= attackDamageTick) {
                swimming.attackTarget(enemy);
                swimming.destroyBoat(enemy);
                attackDamageTick = -1;
                attackType = -1;
            }
        } else if (currentTime > attackEndTick + 20 && canHit(enemy)) {
            //Is target smaller than 2 blocks (if swimming is adult)
            boolean tooBig = !Util.isEntitySmallerThan(enemy, 2 * swimming.getScale() / swimming.data().maxScale());
            if (tooBig || swimming.getRandom().nextInt(5) > 0) {
                attackType = ATTACK;
                AnimationInfoLoader.ServerAnimationInfo animation = swimming.startAttack();
                attackEndTick = (long) (currentTime + animation.animationLength);
                attackDamageTick = Math.min((long) (currentTime + animation.actionDelay), attackEndTick);
            } else {
                attackType = GRAB;
                swimming.destroyBoat(enemy);
                swimming.startGrabAttack(enemy);
                //TODO: Grab attack needs to be delayed and probably only start when looking at player?
                attackEndTick = 1;
                grabStartTick = currentTime;
            }
        }
    }
}
