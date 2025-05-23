package com.github.teamfossilsarcheology.fossil.entity.prehistoric.base;

import com.github.teamfossilsarcheology.fossil.util.Gender;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.animal.Bucketable;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public abstract class PrehistoricSwimmingBucketable extends PrehistoricSwimming implements Bucketable {
    private static final EntityDataAccessor<Boolean> FROM_BUCKET = SynchedEntityData.defineId(PrehistoricSwimmingBucketable.class, EntityDataSerializers.BOOLEAN);

    protected PrehistoricSwimmingBucketable(EntityType<? extends Prehistoric> entityType, Level level, ResourceLocation animationLocation) {
        super(entityType, level, animationLocation);
    }

    protected PrehistoricSwimmingBucketable(EntityType<? extends Prehistoric> entityType, Level level) {
        super(entityType, level);
    }

    @Override
    public @NotNull ItemStack getBucketItemStack() {
        return new ItemStack(info().bucketItem);
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        entityData.define(FROM_BUCKET, false);
    }

    @Override
    public @NotNull InteractionResult mobInteract(Player player, InteractionHand hand) {
        return Bucketable.bucketMobPickup(player, hand, this).orElse(super.mobInteract(player, hand));
    }

    @Override
    public void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        compound.putBoolean("FromBucket", fromBucket());
    }

    @Override
    public void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        setFromBucket(compound.getBoolean("FromBucket"));
    }

    @Override
    public boolean fromBucket() {
        return entityData.get(FROM_BUCKET);
    }

    @Override
    public void setFromBucket(boolean fromBucket) {
        entityData.set(FROM_BUCKET, fromBucket);
    }

    @Override
    public void saveToBucketTag(ItemStack bucket) {
        Bucketable.saveDefaultDataToBucketTag(this, bucket);
        CompoundTag tag = bucket.getOrCreateTag();
        if (getOwnerUUID() != null) {
            tag.putUUID("Owner", getOwnerUUID());
        }
        moodSystem.saveAdditional(tag);
        tag.putInt("MatingCooldown", getMatingCooldown());
        tag.putInt("Hunger", getHunger());
        tag.putInt("Age", getAge());
        tag.putBoolean("AgingDisabled", isAgingDisabled());
        tag.putByte("Gender", (byte) getGender().ordinal());
    }

    @Override
    public void loadFromBucketTag(CompoundTag tag) {
        Bucketable.loadDefaultDataFromBucketTag(this, tag);
        if (tag.contains("Owner")) {
            try {
                setOwnerUUID(tag.getUUID("Owner"));
                setTame(true);
            } catch (IllegalArgumentException e) {
                setTame(false);
            }
        }
        moodSystem.load(tag);
        if (tag.contains("MatingCooldown")) {
            setMatingCooldown(tag.getInt("MatingCooldown"));
        }
        if (tag.contains("Hunger")) {
            setHunger(tag.getInt("Hunger"));
        }
        if (tag.contains("Age")) {
            setAgeInTicks(tag.getInt("Age"));
        }
        if (tag.contains("AgingDisabled")) {
            setAgingDisabled(tag.getBoolean("AgingDisabled"));
        }
        if (tag.contains("Gender", Tag.TAG_BYTE)) {
            setGender(Gender.values()[tag.getByte("Gender")]);
        }
    }

    @Override
    public @NotNull SoundEvent getPickupSound() {
        return SoundEvents.BUCKET_FILL_FISH;
    }
}
