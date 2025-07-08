package com.github.teamfossilsarcheology.fossil.entity;

import com.github.teamfossilsarcheology.fossil.entity.prehistoric.base.Prehistoric;
import com.github.teamfossilsarcheology.fossil.entity.util.Util;
import com.github.teamfossilsarcheology.fossil.item.ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class ToyBall extends ToyBase {
    protected int lerpSteps;
    protected double lerpX;
    protected double lerpY;
    protected double lerpZ;
    protected double lerpYRot;
    protected double lerpXRot;

    private static final EntityDataAccessor<Integer> COLOR_ID = SynchedEntityData.defineId(ToyBall.class, EntityDataSerializers.INT);

    public ToyBall(EntityType<ToyBall> type, Level level) {
        super(type, level, 15, SoundEvents.SLIME_ATTACK);
    }

    @Override
    protected void defineSynchedData() {
        entityData.define(COLOR_ID, DyeColor.WHITE.getId());
    }

    @Override
    public void push(Entity entity) {
        if (entity != null && !(entity instanceof ToyBase)) {
            pushBall(entity);
        }
    }

    private void pushBall(float yRot) {
        setRot(yRot, getXRot());
        push(-Mth.sin(getYRot() * Mth.DEG_TO_RAD) * 0.5f, 0.1, Mth.cos(getYRot() * Mth.DEG_TO_RAD) * 0.5f);
    }

    private void pushBall(Entity entity) {
        Vec3 offset = Util.directionVecTo(entity, this);
        double yawDiff = (Mth.atan2(offset.z, offset.x) * Mth.RAD_TO_DEG);
        pushBall(Util.yawToYRot(yawDiff));
    }

    @Override
    public boolean canBeCollidedWith() {
        return false;
    }

    @Override
    public boolean isPushable() {
        return true;
    }

    protected void pushEntities() {
        List<Entity> list = level().getEntities(this, getBoundingBox(), EntitySelector.pushableBy(this));
        if (!list.isEmpty()) {
            int i = level().getGameRules().getInt(GameRules.RULE_MAX_ENTITY_CRAMMING);
            int j;
            if (i > 0 && list.size() > i - 1 && random.nextInt(4) == 0) {
                j = 0;

                for (Entity entity : list) {
                    if (!entity.isPassenger()) {
                        ++j;
                    }
                }

                if (j > i - 1) {
                    hurt(damageSources().cramming(), 6.0F);
                }
            }

            for (j = 0; j < list.size(); ++j) {
                Entity entity = list.get(j);
                doPush(entity);
            }
        }

    }

    protected void doPush(Entity entity) {
        entity.push(this);
    }

    @Override
    public void tick() {
        super.tick();
        if (Math.abs(getDeltaMovement().x) > 0.01 || Math.abs(getDeltaMovement().z) > 0.01) {
            setRot(getYRot(), getXRot() + 14);
        }
        aiStep();
    }

    private void aiStep() {
        if (isControlledByLocalInstance()) {
            lerpSteps = 0;
            syncPacketPositionCodec(getX(), getY(), getZ());
        }
        if (lerpSteps > 0) {
            double d = getX() + (lerpX - getX()) / (double) lerpSteps;
            double e = getY() + (lerpY - getY()) / (double) lerpSteps;
            double f = getZ() + (lerpZ - getZ()) / (double) lerpSteps;
            double g = Mth.wrapDegrees(lerpYRot - (double) getYRot());
            setYRot(getYRot() + (float) g / lerpSteps);
            --lerpSteps;
            setPos(d, e, f);
            setRot(getYRot(), getXRot());
        } else if (level().isClientSide) {
            setDeltaMovement(getDeltaMovement().scale(0.98));
        }

        if (isEyeInFluid(FluidTags.WATER)) {
            push(0, 0.1, 0);
        }
        if (horizontalCollision) {
            pushBall(getYRot() + 180);
        }
        Vec3 vec3 = getDeltaMovement();

        double x = vec3.x;
        double y = vec3.y;
        double z = vec3.z;
        if (Math.abs(vec3.x) < 0.003) {
            x = 0.0;
        }

        if (Math.abs(vec3.y) < 0.003) {
            y = 0.0;
        }

        if (Math.abs(vec3.z) < 0.003) {
            z = 0.0;
        }
        setDeltaMovement(x, y, z);
        travel();
        pushEntities();
    }

    private void travel() {
        if (!level().isClientSide || isControlledByLocalInstance()) {
            double gravity = 0.08;
            boolean isFalling = getDeltaMovement().y <= 0.0;

            if (isInWater()) {
                double e = getY();
                float friction = 0.8f;
                float g = 0.02F;

                moveRelative(g, Vec3.ZERO);
                move(MoverType.SELF, getDeltaMovement());
                Vec3 vec3 = getDeltaMovement();

                setDeltaMovement(vec3.multiply(friction, 0.8, friction));
                double dy;
                if (isFalling && Math.abs(getDeltaMovement().y - 0.005) >= 0.003 && Math.abs(getDeltaMovement().y - gravity / 16.0) < 0.003) {
                    dy = -0.003;
                } else {
                    dy = getDeltaMovement().y - gravity / 16.0;
                }
                Vec3 fallingAdjusted = new Vec3(getDeltaMovement().x, dy, getDeltaMovement().z);
                setDeltaMovement(fallingAdjusted);
                if (horizontalCollision && isFree(fallingAdjusted.x, fallingAdjusted.y + 0.6 - getY() + e, fallingAdjusted.z)) {
                    setDeltaMovement(fallingAdjusted.x, 0.3, fallingAdjusted.z);
                }
            } else {
                BlockPos blockPos = getBlockPosBelowThatAffectsMyMovement();
                float blockFriction = level().getBlockState(blockPos).getBlock().getFriction();
                float friction = onGround() ? blockFriction * 0.91f : 0.91f;
                moveRelative(0, Vec3.ZERO);
                move(MoverType.SELF, getDeltaMovement());
                Vec3 vec36 = getDeltaMovement();
                double q = vec36.y;
                if (level().isClientSide && !level().hasChunkAt(blockPos)) {
                    if (getY() > (double) level().getMinBuildHeight()) {
                        q = -0.1;
                    } else {
                        q = 0;
                    }
                } else if (!this.isNoGravity()) {
                    q -= gravity;
                }
                setDeltaMovement(vec36.x * friction, q * 0.98, vec36.z * friction);
            }
        }
    }

    @Override
    public void lerpTo(double x, double y, double z, float yRot, float xRot, int lerpSteps, boolean teleport) {
        this.lerpX = x;
        this.lerpY = y;
        this.lerpZ = z;
        this.lerpYRot = yRot;
        setXRot(xRot < 0 ? 360 + xRot : xRot);
        this.lerpSteps = lerpSteps;
    }

    @Override
    protected boolean tickAI() {
        return true;
    }

    @Override
    public boolean hurt(DamageSource source, float amount) {
        if (source.getEntity() instanceof Prehistoric prehistoric) {
            push(prehistoric);
            stopRiding();
        } else if (source.getDirectEntity() instanceof AbstractArrow javelin) {
            playSound(attackNoise, 1, getVoicePitch());
            push(javelin);
            return true;
        }
        return super.hurt(source, amount);
    }

    @Nullable
    @Override
    public ItemStack getPickResult() {
        return new ItemStack(ModItems.TOY_BALLS.get(getColor()).get());
    }

    public DyeColor getColor() {
        return DyeColor.byId(entityData.get(COLOR_ID));
    }

    public void setColor(DyeColor color) {
        entityData.set(COLOR_ID, color.getId());
    }

    @Override
    public void addAdditionalSaveData(CompoundTag compound) {
        compound.putInt("color", getColor().getId());
    }

    @Override
    public void readAdditionalSaveData(CompoundTag compound) {
        setColor(DyeColor.byId(compound.getInt("color")));
    }
}
