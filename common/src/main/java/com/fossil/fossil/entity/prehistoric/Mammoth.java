package com.fossil.fossil.entity.prehistoric;

import com.fossil.fossil.Fossil;
import com.fossil.fossil.entity.ai.*;
import com.fossil.fossil.entity.prehistoric.base.PrehistoricEntityType;
import com.fossil.fossil.entity.prehistoric.base.PrehistoricFlocking;
import com.fossil.fossil.sounds.ModSounds;
import com.fossil.fossil.util.Gender;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Shearable;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib3.core.builder.Animation;
import software.bernie.geckolib3.core.manager.AnimationFactory;
import software.bernie.geckolib3.util.GeckoLibUtil;

public class Mammoth extends PrehistoricFlocking implements Shearable {
    public static final String ANIMATIONS = "mammoth.animation.json";
    public static final String ATTACK = "animation.mammoth.attack1";
    public static final String EAT = "animation.mammoth.eating";
    public static final String FALL = "animation.mammoth.jump/fall";
    public static final String IDLE = "animation.mammoth.idle1";
    public static final String IDLE2 = "animation.mammoth.idle2";
    public static final String RUN = "animation.mammoth.run";
    public static final String SLEEP = "animation.mammoth.rest/sleep";
    public static final String WALK = "animation.mammoth.walk";
    private static final EntityDataAccessor<Boolean> SHEARED = SynchedEntityData.defineId(Mammoth.class, EntityDataSerializers.BOOLEAN);

    private final AnimationFactory factory = GeckoLibUtil.createFactory(this);
    private int woolRegenTicks;

    public Mammoth(EntityType<Mammoth> entityType, Level level) {
        super(entityType, level);
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        goalSelector.addGoal(0, new FleeBattleGoal(this, 1));
        goalSelector.addGoal(1, new DinoMeleeAttackGoal(this, 1, false));
        goalSelector.addGoal(1, new FloatGoal(this));
        goalSelector.addGoal(2, new FlockWanderGoal(this, 1));
        goalSelector.addGoal(3, new DinoWanderGoal(this, 1));
        goalSelector.addGoal(6, new DinoFollowOwnerGoal(this, 1, 10, 2, false));
        goalSelector.addGoal(7, new DinoLookAroundGoal(this));
        targetSelector.addGoal(1, new DinoOwnerHurtByTargetGoal(this));
        targetSelector.addGoal(2, new DinoOwnerHurtTargetGoal(this));
        targetSelector.addGoal(3, new DinoHurtByTargetGoal(this));
    }

    @Override
    public void refreshTexturePath() {
        if (!level.isClientSide) {
            return;
        }
        StringBuilder builder = new StringBuilder();
        builder.append("textures/entity/mammoth/mammoth");
        if (hasBabyTexture && isBaby()) builder.append("_baby");
        if (isAdult()) {
            if (getGender() == Gender.MALE) {
                builder.append("_male");
            } else {
                builder.append("_female");
            }
        }
        if (isSleeping()) builder.append("_sleeping");
        if (isSheared()) builder.append("_shaved");
        builder.append(".png");
        String path = builder.toString();
        textureLocation = new ResourceLocation(Fossil.MOD_ID, path);
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        entityData.define(SHEARED, false);
    }

    @Override
    public void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        compound.putBoolean("Sheared", isSheared());
        compound.putInt("WoolRegenTicks", woolRegenTicks);
    }

    @Override
    public void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        setSheared(compound.getBoolean("Sheared"));
        woolRegenTicks = compound.getInt("WoolRegenTicks");
    }

    @Override
    public PrehistoricEntityType type() {
        return PrehistoricEntityType.MAMMOTH;
    }

    @Override
    protected int getMaxGroupSize() {
        return 7;
    }

    @Override
    public void aiStep() {
        boolean tooWarm = level.getBiome(blockPosition()).value().shouldSnowGolemBurn(blockPosition());
        if (tooWarm && getEffect(MobEffects.WEAKNESS) != null && !isSheared()) {
            addEffect(new MobEffectInstance(MobEffects.WEAKNESS, 60, 1));
        }
        super.aiStep();
    }

    @Override
    public void doFoodEffect(Item item) {
        super.doFoodEffect(item);
        if (isSheared()) {
            woolRegenTicks++;
            if (woolRegenTicks >= 5) {
                setSheared(false);
                woolRegenTicks = 0;
            }
        }
    }

    @Override
    public Item getOrderItem() {
        return Items.STICK;
    }

    @Override
    public double getPassengersRidingOffset() {
        double d = 0.35;
        return super.getPassengersRidingOffset() + d;
    }

    @Override
    public void shear(SoundSource source) {
        level.playSound(null, this, SoundEvents.SHEEP_SHEAR, source, 1, 1);
        setSheared(true);
        int maxWool = 1 + random.nextInt(20);
        for (int i = 0; i < maxWool; i++) {
            ItemEntity itemEntity = spawnAtLocation(Blocks.BROWN_WOOL, 1);
            if (itemEntity == null) continue;
            itemEntity.setDeltaMovement(itemEntity.getDeltaMovement().add((random.nextFloat() - random.nextFloat()) * 0.1, random.nextFloat() * 0.05, (random.nextFloat() - random.nextFloat()) * 0.1));
        }
    }

    @Override
    public boolean readyForShearing() {
        return !isSheared() && !isBaby();
    }

    public boolean isSheared() {
        return entityData.get(SHEARED);
    }

    public void setSheared(boolean sheared) {
        entityData.set(SHEARED, sheared);
    }

    @Override
    public float getGenderedScale() {
        return getGender() == Gender.MALE ? 1.15f : super.getGenderedScale();
    }

    @Override
    public float getTargetScale() {
        return 1.5f;
    }

    @Override
    public @NotNull Animation nextAttackAnimation() {
        return getAllAnimations().get(ATTACK);
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
    public @NotNull Animation nextSleepingAnimation() {
        return getAllAnimations().get(SLEEP);
    }

    @Override
    public @NotNull Animation nextMovingAnimation() {
        if (isInWater()) {
            return getAllAnimations().get(IDLE);
        }
        return getAllAnimations().get(WALK);
    }

    @Override
    public @NotNull Animation nextSprintingAnimation() {
        if (isInWater()) {
            return getAllAnimations().get(IDLE);
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
        return ModSounds.MAMMOTH_AMBIENT.get();
    }

    @Nullable
    @Override
    protected SoundEvent getHurtSound(DamageSource damageSource) {
        return ModSounds.MAMMOTH_HURT.get();
    }

    @Nullable
    @Override
    protected SoundEvent getDeathSound() {
        return ModSounds.MAMMOTH_DEATH.get();
    }
}