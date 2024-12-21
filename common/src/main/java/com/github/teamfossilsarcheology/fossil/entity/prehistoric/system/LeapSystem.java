package com.github.teamfossilsarcheology.fossil.entity.prehistoric.system;

import com.github.teamfossilsarcheology.fossil.entity.animation.ServerAnimationInfo;
import com.github.teamfossilsarcheology.fossil.entity.prehistoric.base.PrehistoricLeaping;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.phys.Vec3;

import static com.github.teamfossilsarcheology.fossil.entity.prehistoric.base.PrehistoricLeaping.*;

public class LeapSystem extends AISystem {
    public static final int JUMP_DISTANCE = 30;
    private final PrehistoricLeaping mob;
    private final SynchedEntityData entityData;
    private LivingEntity target;
    private Vec3 blockTarget;
    private boolean leaping;
    private long jumpDelayTick = -1;
    private long landingDelayTick = -1;
    private long landingEndTick = -1;

    public LeapSystem(PrehistoricLeaping mob) {
        super(mob);
        this.mob = mob;
        this.entityData = mob.getEntityData();
    }

    @Override
    public void serverTick() {
        long currentTick = mob.level.getGameTime();
        if (!isLeaping() && target != null && target.isAlive()) {
            mob.lookAt(target, 100, 10);
            if (mob.distanceToSqr(target) < JUMP_DISTANCE) {
                startLeap();
                ServerAnimationInfo animation = (ServerAnimationInfo) mob.getLeapStartAnimation();
                jumpDelayTick = (long) (currentTick + animation.actionDelay);
                mob.setDeltaMovement(Vec3.ZERO);
            }
        }
        if (!isLeaping() && blockTarget != null) {
            if (mob.distanceToSqr(blockTarget) < JUMP_DISTANCE) {
                startLeap();
                ServerAnimationInfo animation = (ServerAnimationInfo) mob.getLeapStartAnimation();
                jumpDelayTick = (long) (currentTick + animation.actionDelay);
                mob.setDeltaMovement(Vec3.ZERO);
            }
        }
        if (isLeaping()) {
            if (target != null && ((target.isVehicle() && mob.getVehicle() != target) || target.isDeadOrDying())) {
                if (jumpDelayTick != -1) {
                    blockTarget = target.position();
                }
                setAttackRiding(false);
                setLeapTarget(null);
            }
            if (currentTick == jumpDelayTick) {
                double y;
                Vec3 offset;
                if (target != null) {
                    y = Math.min(target.getY() + target.getBbHeight() - mob.getY(), 5);
                    offset = target.position().subtract(mob.position()).normalize();
                    offset = offset.add(target.getDeltaMovement().x, 0, target.getDeltaMovement().z);
                } else if (blockTarget != null) {
                    y = Math.min(blockTarget.y - mob.getY(), 5);
                    offset = blockTarget.subtract(mob.position()).normalize();
                } else {
                    //Shouldn't really happen
                    y = 0;
                    offset = Vec3.ZERO;
                }
                mob.setDeltaMovement(offset.x, -0.027 * Math.pow(y, 2) + 0.262 * y + 0.183, offset.z);
                setLeapStarted(false);
                setLeapFlying(true);
                landingDelayTick = jumpDelayTick + 5;
                jumpDelayTick = -1;
            }
            if (mob.isOnGround() && landingDelayTick != -1 && landingDelayTick <= currentTick) {
                ServerAnimationInfo animation = (ServerAnimationInfo) mob.getLandAnimation();
                landingEndTick = (long) (currentTick + animation.animation.animationLength);
                landingDelayTick = -1;
                setLeapFlying(false);
                setLanding(true);
            }
            if (landingEndTick != -1 && landingEndTick <= currentTick) {
                setLanding(false);
                setLeaping(false);
                landingEndTick = -1;
                setLeapTarget(null);
            }
            if (isAttackRiding() && target != null) {
                if (mob.tickCount % 20 == 0) {
                    target.hurt(DamageSource.mobAttack(mob), (float) mob.getAttributeValue(Attributes.ATTACK_DAMAGE));
                }
                if (target.isDeadOrDying()) {
                    setAttackRiding(false);
                }
            }
            if(!isLeapFlying() && !hasLeapStarted() && !isLanding() && !isAttackRiding()) {
                //failsafe
                setLeaping(false);
            }
            //Collision is handled in entity class
        }
    }

    public void startLeap() {
        jumpDelayTick = -1;
        landingDelayTick = -1;
        landingEndTick = -1;
        setLeapFlying(false);
        setLeaping(true);
        setLeapStarted(true);
    }

    /**
     * Forcefully stops a leap including removing the leap target
     */
    public void stopLeap() {
        setLeaping(false);
        setLeapFlying(false);
        setLeapStarted(false);
        setLanding(false);
        setLeapTarget(null);
    }

    /**
     * Returns true if its currently in the air flying at the given entity
     */
    public boolean isLeapingAt(Entity entity) {
        return target == entity && isLeapFlying();
    }

    public void tryAttackRiding(Entity target) {
        if (target.getPassengers().isEmpty()) {
            setLeapFlying(false);
            setAttackRiding(true);
        } else {
            target.hurt(DamageSource.mobAttack(mob), (float) mob.getAttributeValue(Attributes.ATTACK_DAMAGE));
        }
    }

    public boolean isLeaping() {
        return leaping;
    }

    private void setLeaping(boolean leaping) {
        this.leaping = leaping;
    }

    /**
     * Returns true if the mob is currently in the air
     */
    public boolean isLeapFlying() {
        return entityData.get(LEAP_FLYING);
    }

    private void setLeapFlying(boolean leapFlying) {
        entityData.set(LEAP_FLYING, leapFlying);
    }

    public void setBlockLeapTarget(Vec3 target) {
        this.blockTarget = target;
    }

    public void setLeapTarget(LivingEntity target) {
        this.target = target;
        entityData.set(LEAP_TARGET_ID, -2);
        entityData.set(LEAP_TARGET_ID, target == null ? -1 : target.getId());
    }

    public boolean isLanding() {
        return entityData.get(LEAP_LANDING);
    }

    private void setLanding(boolean landing) {
        entityData.set(LEAP_LANDING, landing);
    }

    public boolean isAttackRiding() {
        return entityData.get(LEAP_RIDING);
    }

    public void setAttackRiding(boolean attackRiding) {
        entityData.set(LEAP_RIDING, attackRiding);
    }

    /**
     * Returns true if the mob is currently preparing to jump
     */
    public boolean hasLeapStarted() {
        return entityData.get(LEAP_STARTED);
    }

    public void setLeapStarted(boolean value) {
        entityData.set(LEAP_STARTED, value);
    }

    @Override
    public void saveAdditional(CompoundTag tag) {
        tag.putBoolean("AttackRiding", isAttackRiding());
    }

    @Override
    public void load(CompoundTag tag) {
        if (tag.getBoolean("AttackRiding")) {
            setAttackRiding(true);
            setLeaping(true);
        }
    }
}