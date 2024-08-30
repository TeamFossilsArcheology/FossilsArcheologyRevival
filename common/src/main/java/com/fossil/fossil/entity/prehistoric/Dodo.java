package com.fossil.fossil.entity.prehistoric;

import com.fossil.fossil.Fossil;
import com.fossil.fossil.entity.ai.DelayedAttackGoal;
import com.fossil.fossil.entity.prehistoric.base.Prehistoric;
import com.fossil.fossil.entity.prehistoric.base.PrehistoricEntityInfo;
import com.fossil.fossil.entity.util.Util;
import com.fossil.fossil.sounds.ModSounds;
import com.fossil.fossil.util.Gender;
import com.fossil.fossil.util.Version;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
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
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib3.core.builder.Animation;
import software.bernie.geckolib3.core.manager.AnimationFactory;
import software.bernie.geckolib3.util.GeckoLibUtil;

import java.time.LocalDate;
import java.time.Month;

public class Dodo extends Prehistoric {
    private static final EntityDataAccessor<Integer> DATA_VARIANT = SynchedEntityData.defineId(Dodo.class, EntityDataSerializers.INT);
    public static final String ANIMATIONS = "dodo.animation.json";
    public static final String ATTACK1 = "animation.dodo.bite1";
    public static final String ATTACK2 = "animation.dodo.bite2";
    public static final String EAT = "animation.dodo.eat";
    public static final String FALL = "animation.dodo.jump/fall";
    public static final String IDLE = "animation.dodo.idle";
    public static final String RUN = "animation.dodo.run";
    public static final String SIT = "animation.dodo.sit";
    public static final String SLEEP1 = "animation.dodo.sleep1";
    public static final String SLEEP2 = "animation.dodo.sleep2";
    public static final String SWIM = "animation.dodo.swim";
    public static final String WALK = "animation.dodo.walk";

    private final AnimationFactory factory = GeckoLibUtil.createFactory(this);

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
        goalSelector.addGoal(Util.ATTACK, new DelayedAttackGoal(this, 1, false));
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
        textureLocation = new ResourceLocation(Fossil.MOD_ID, builder.toString());
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
    public @NotNull Animation nextAttackAnimation() {
        return getAllAnimations().get(random.nextInt(2) == 0 ? ATTACK1 : ATTACK2);
    }

    @Override
    public @NotNull Animation nextEatingAnimation() {
        return getAllAnimations().get(EAT);
    }

    @Override
    public @NotNull Animation nextIdleAnimation() {
        return getAllAnimations().get(IDLE);
    }

    @Override
    public @NotNull Animation nextSittingAnimation() {
        return getAllAnimations().get(SIT);
    }

    @Override
    public @NotNull Animation nextSleepingAnimation() {
        return getAllAnimations().get(random.nextInt(2) == 0 ? SLEEP1 : SLEEP2);
    }

    @Override
    public @NotNull Animation nextMovingAnimation() {
        if (isInWater()) {
            return getAllAnimations().get(SWIM);
        }
        return getAllAnimations().get(RUN);
    }

    @Override
    public @NotNull Animation nextSprintingAnimation() {
        if (isInWater()) {
            return getAllAnimations().get(SWIM);
        }
        return getAllAnimations().get(RUN);
    }

    @Override
    public AnimationFactory getFactory() {
        return factory;
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