package com.github.teamfossilsarcheology.fossil.entity.prehistoric;

import com.github.teamfossilsarcheology.fossil.FossilMod;
import com.github.teamfossilsarcheology.fossil.entity.ai.FleeBattleGoal;
import com.github.teamfossilsarcheology.fossil.entity.prehistoric.base.Prehistoric;
import com.github.teamfossilsarcheology.fossil.entity.prehistoric.base.PrehistoricEntityInfo;
import com.github.teamfossilsarcheology.fossil.entity.util.Util;
import com.github.teamfossilsarcheology.fossil.sounds.ModSounds;
import com.github.teamfossilsarcheology.fossil.util.Gender;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

public class Parasaurolophus extends Prehistoric {
    private static final EntityDataAccessor<Boolean> STANDING = SynchedEntityData.defineId(Parasaurolophus.class, EntityDataSerializers.BOOLEAN);

    private int ticksStanding;

    public Parasaurolophus(EntityType<Parasaurolophus> entityType, Level level) {
        super(entityType, level);
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        goalSelector.addGoal(Util.IMMOBILE + 3, new FleeBattleGoal(this, 1));
    }

    @Override
    public void refreshTexturePath() {
        if (!level.isClientSide) {
            return;
        }
        if ("Jackabird".equals(ChatFormatting.stripFormatting(getName().getString()))) {
            if (isSleeping()) {
                textureLocation = FossilMod.location("textures/entity/parasaurolophus/parasaurolophus_jackabird_sleeping.png");
            } else {
                textureLocation = FossilMod.location("textures/entity/parasaurolophus/parasaurolophus_jackabird.png");
            }
            return;
        }
        super.refreshTexturePath();
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        entityData.define(STANDING, false);
    }

    @Override
    public void onSyncedDataUpdated(EntityDataAccessor<?> key) {
        if (!level.isClientSide) {
            if (SLEEPING.equals(key) || SITTING.equals(key)) {
                setStanding(false);
            }
        } else if (DATA_CUSTOM_NAME.equals(key)) {
            refreshTexturePath();
        }
        super.onSyncedDataUpdated(key);
    }

    @Override
    public void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        compound.putBoolean("Standing", isStanding());
    }

    @Override
    public void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        setStanding(compound.getBoolean("Standing"));
    }

    @Override
    public PrehistoricEntityInfo info() {
        return PrehistoricEntityInfo.PARASAUROLOPHUS;
    }

    @Override
    public Item getOrderItem() {
        return Items.STICK;
    }

    @Override
    public double getPassengersRidingOffset() {
        return super.getPassengersRidingOffset() + 0.4;
    }

    @Override
    public void aiStep() {
        super.aiStep();
        if (isStanding()) {
            ticksStanding++;
        } else {
            ticksStanding = 0;
        }
        if (!level.isClientSide) {
            if (!isStanding() && !isImmobile() && random.nextInt(100) == 0) {
                setStanding(true);
            }
            if (isStanding() && ticksStanding > 800 && random.nextInt(800) == 0) {
                setStanding(false);
            }
        }
    }

    public boolean isStanding() {
        return entityData.get(STANDING);
    }

    public void setStanding(boolean standing) {
        entityData.set(STANDING, standing);
    }

    @Override
    public float getGenderedScale() {
        return getGender() == Gender.MALE ? 1.15f : super.getGenderedScale();
    }

    @Nullable
    @Override
    protected SoundEvent getAmbientSound() {
        return ModSounds.PARASAUROLOPHUS_AMBIENT.get();
    }

    @Nullable
    @Override
    protected SoundEvent getHurtSound(DamageSource damageSource) {
        return ModSounds.PARASAUROLOPHUS_HURT.get();
    }

    @Nullable
    @Override
    protected SoundEvent getDeathSound() {
        return ModSounds.PARASAUROLOPHUS_DEATH.get();
    }
}