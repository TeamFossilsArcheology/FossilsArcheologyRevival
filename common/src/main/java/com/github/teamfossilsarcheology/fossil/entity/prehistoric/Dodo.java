package com.github.teamfossilsarcheology.fossil.entity.prehistoric;

import com.github.teamfossilsarcheology.fossil.FossilMod;
import com.github.teamfossilsarcheology.fossil.entity.animation.AnimationCategory;
import com.github.teamfossilsarcheology.fossil.entity.prehistoric.base.Prehistoric;
import com.github.teamfossilsarcheology.fossil.entity.prehistoric.base.PrehistoricEntityInfo;
import com.github.teamfossilsarcheology.fossil.sounds.ModSounds;
import com.github.teamfossilsarcheology.fossil.util.Gender;
import com.github.teamfossilsarcheology.fossil.util.Version;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib3.core.builder.Animation;

import java.time.LocalDate;
import java.time.Month;

public class Dodo extends Prehistoric {
    private static final EntityDataAccessor<Integer> DATA_VARIANT = SynchedEntityData.defineId(Dodo.class, EntityDataSerializers.INT);

    public Dodo(EntityType<Dodo> entityType, Level level) {
        super(entityType, level);
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        entityData.define(DATA_VARIANT, Variant.DODO.ordinal());
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
    }

    @Override
    public void refreshTexturePath() {
        if (!level.isClientSide) {
            return;
        }
        StringBuilder builder = new StringBuilder();
        builder.append("textures/entity/dodo/dodo");
        if (getVariant() == Variant.FESTIVE) {
            builder.append("_festive");
        } else {
            if (isBaby()) builder.append("_baby");
            if (isTeen() || isAdult()) {
                if (getGender() == Gender.MALE) {
                    builder.append("_male");
                } else {
                    builder.append("_female");
                }
            }
        }
        if (isSleeping()) builder.append("_sleeping");
        builder.append(".png");
        textureLocation = FossilMod.location(builder.toString());
    }

    @Override
    public void aiStep() {
        super.aiStep();
        Vec3 movement = getDeltaMovement();
        if (!isOnGround() && movement.y < 0) {
            setDeltaMovement(movement.x, movement.y * 0.6, movement.z);
        }
    }

    @Override
    public PrehistoricEntityInfo info() {
        return PrehistoricEntityInfo.DODO;
    }

    @Override
    public Item getOrderItem() {
        return Items.STICK;
    }

    public void setVariant(Variant variant) {
        entityData.set(DATA_VARIANT, variant.ordinal());
    }

    public Variant getVariant() {
        return Variant.values()[entityData.get(DATA_VARIANT)];
    }

    @Nullable
    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor level, DifficultyInstance difficulty, MobSpawnType reason, @Nullable SpawnGroupData spawnData, @Nullable CompoundTag dataTag) {
        if (LocalDate.now().getMonth() == Month.DECEMBER || Version.debugEnabled()) {
            double chance = LocalDate.now().getDayOfMonth() == 24 ? 0.5 : 0.05;
            if (random.nextDouble() <= chance) {
                setVariant(Variant.FESTIVE);
            }
        }
        return super.finalizeSpawn(level, difficulty, reason, spawnData, dataTag);
    }

    @Override
    public void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        compound.putInt("Variant", getVariant().ordinal());
    }

    @Override
    public void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        setVariant(Variant.values()[compound.getInt("Variant")]);
    }

    @Override
    public void saveAdditionalSpawnData(FriendlyByteBuf buf) {
        buf.writeEnum(getVariant());
        super.saveAdditionalSpawnData(buf);
    }

    @Override
    public void loadAdditionalSpawnData(FriendlyByteBuf buf) {
        setVariant(buf.readEnum(Variant.class));
        super.loadAdditionalSpawnData(buf);
    }

    @Override
    public float getGenderedScale() {
        return getGender() == Gender.MALE ? 1.25f : super.getGenderedScale();
    }

    @Override
    public Animation getAnimation(AnimationCategory category) {
        if (category == AnimationCategory.WALK) {
            return getAnimation(AnimationCategory.SPRINT);//Walk to slow
        }
        return super.getAnimation(category);
    }

    @Nullable
    @Override
    protected SoundEvent getAmbientSound() {
        return ModSounds.DODO_AMBIENT.get();
    }

    @Nullable
    @Override
    protected SoundEvent getHurtSound(DamageSource damageSource) {
        return ModSounds.DODO_HURT.get();
    }

    @Nullable
    @Override
    protected SoundEvent getDeathSound() {
        return ModSounds.DODO_DEATH.get();
    }

    public enum Variant {
        DODO, FESTIVE;
    }
}