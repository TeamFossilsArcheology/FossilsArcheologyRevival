package com.fossil.fossil.entity;

import com.fossil.fossil.entity.prehistoric.base.Prehistoric;
import com.fossil.fossil.item.ModItems;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

public class ToyBall extends ToyBase {
    private static final EntityDataAccessor<Integer> COLOR_ID = SynchedEntityData.defineId(ToyBall.class, EntityDataSerializers.INT);

    public ToyBall(EntityType<ToyBall> type, Level level) {
        super(type, level, 15, SoundEvents.SLIME_ATTACK);
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        entityData.define(COLOR_ID, DyeColor.WHITE.getId());
    }

    @Override
    public void push(Entity entity) {
        if (entity != null && !(entity instanceof ToyBase)) {
            setRot(entity.getYRot(), getXRot());
            push(-Mth.sin((float) (getYRot() * Math.PI / 180.0f)) * 0.5f, 0.1, Mth.cos((float) (getYRot() * Math.PI / 180.0f)) * 0.5f);
        }
    }

    @Override
    public void aiStep() {
        super.aiStep();
        if (isEyeInFluid(FluidTags.WATER)) {
            push(0, 0.1, 0);
        }
        if (horizontalCollision) {
            setRot(getYRot() + 180, getXRot());
            push(-Mth.sin((float) (getYRot() * Math.PI / 180.0f)) * 0.5f, 0.1, Mth.cos((float) (getYRot() * Math.PI / 180.0f)) * 0.5f);
        }
    }

    @Override
    public void tick() {
        super.tick();
        if (Math.abs(getDeltaMovement().x) > 0.01 || Math.abs(getDeltaMovement().z) > 0.01) {
            setRot(getYRot(), getXRot() + 7);
        }
    }

    @Override
    public boolean hurt(DamageSource source, float amount) {
        if (source.getEntity() instanceof Prehistoric prehistoric) {
            prehistoric.useToy(moodBonus);
            playSound(attackNoise, getSoundVolume(), getVoicePitch());
            setRot(prehistoric.getYRot(), getXRot());
            push(-Mth.sin((float) (getYRot() * Math.PI / 180.0f)) * 0.5f, 0.1, Mth.cos((float) (getYRot() * Math.PI / 180.0f)) * 0.5f);
            stopRiding();
        }
        return super.hurt(source, amount);
    }

    @Nullable
    @Override
    public ItemStack getPickResult() {
        return new ItemStack(ModItems.TOY_BALLS.get(getColor()).get());
    }

    public void setColor(DyeColor color) {
        entityData.set(COLOR_ID, color.getId());
    }

    public DyeColor getColor() {
        return DyeColor.byId(entityData.get(COLOR_ID));
    }

    @Override
    public void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        compound.putInt("color", getColor().getId());
    }

    @Override
    public void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        setColor(DyeColor.byId(compound.getInt("color")));
    }
}
