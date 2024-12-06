package com.github.teamfossilsarcheology.fossil.entity.prehistoric.system;

import com.github.teamfossilsarcheology.fossil.entity.animation.ServerAnimationInfo;
import com.github.teamfossilsarcheology.fossil.entity.prehistoric.base.PrehistoricLeaping;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.phys.Vec3;

import static com.github.teamfossilsarcheology.fossil.entity.prehistoric.base.PrehistoricLeaping.*;

public class LeapSystem extends AISystem {
    private final PrehistoricLeaping mob;
    private final SynchedEntityData entityData;
    private Entity target;
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
        double jumpDistance = 30;
        if (!isLeaping() && target != null) {
            mob.lookAt(target, 100, 10);
            if (mob.distanceToSqr(target) < jumpDistance) {
                startLeap();
                ServerAnimationInfo animation = (ServerAnimationInfo) mob.getLeapStartAnimation();
                jumpDelayTick = (long) (mob.level.getGameTime() + animation.actionDelay);
                mob.setDeltaMovement(Vec3.ZERO);
            }
        }
        //TODO: doesnt hit moving target that well
        if (isLeaping()) {
            if (mob.level.getGameTime() == jumpDelayTick) {
                Vec3 offset = target.position().subtract(mob.position()).normalize().add(0, target.getBbHeight() / 2.1, 0);
                mob.setDeltaMovement(offset);
                setLeapStarted(false);
                setLeapFlying(true);
                landingDelayTick = jumpDelayTick + 5;
                jumpDelayTick = -1;
            }
            if (mob.isOnGround() && landingDelayTick != -1 && landingDelayTick <= mob.level.getGameTime()) {
                ServerAnimationInfo animation = (ServerAnimationInfo) mob.getLandAnimation();
                landingEndTick = (long) (mob.level.getGameTime() + animation.animation.animationLength);
                landingDelayTick = -1;
                setLeapFlying(false);
                setLanding(true);
            }
            if (landingEndTick != -1 && landingEndTick <= mob.level.getGameTime()) {
                setLanding(false);
                setLeaping(false);
                landingEndTick = -1;
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
        entityData.set(LEAP_FLYING, !leapFlying);
        entityData.set(LEAP_FLYING, leapFlying);
    }

    public void setLeapTarget(Entity target) {
        this.target = target;
        entityData.set(LEAP_TARGET_ID, -2);
        entityData.set(LEAP_TARGET_ID, target == null ? -1 : target.getId());
    }

    public boolean isLanding() {
        return entityData.get(LEAP_LANDING);
    }

    private void setLanding(boolean landing) {
        entityData.set(LEAP_LANDING, !landing);
        entityData.set(LEAP_LANDING, landing);
    }

    public boolean isAttackRiding() {
        return entityData.get(LEAP_RIDING);
    }

    public void setAttackRiding(boolean attackRiding) {
        //I'm to lazy figure out how to prevent server/client desync
        entityData.set(LEAP_RIDING, !attackRiding);
        entityData.set(LEAP_RIDING, attackRiding);
    }

    /**
     * Returns true if the mob is currently preparing to jump
     */
    public boolean hasLeapStarted() {
        return entityData.get(LEAP_STARTED);
    }

    public void setLeapStarted(boolean value) {
        entityData.set(LEAP_STARTED, !value);
        entityData.set(LEAP_STARTED, value);
    }

    @Override
    public void saveAdditional(CompoundTag tag) {

    }

    @Override
    public void load(CompoundTag tag) {

    }
}