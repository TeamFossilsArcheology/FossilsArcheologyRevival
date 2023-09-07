package com.fossil.fossil.entity.prehistoric;

import com.fossil.fossil.entity.ai.*;
import com.fossil.fossil.entity.animation.AnimationManager;
import com.fossil.fossil.entity.data.EntityDataManager;
import com.fossil.fossil.entity.prehistoric.base.PrehistoricEntityType;
import com.fossil.fossil.entity.prehistoric.base.PrehistoricFlocking;
import com.fossil.fossil.sounds.ModSounds;
import com.fossil.fossil.util.Gender;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.LazyLoadedValue;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Shearable;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib3.core.manager.AnimationFactory;
import software.bernie.geckolib3.util.GeckoLibUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.fossil.fossil.entity.animation.AnimationLogic.ServerAnimationInfo;
import static com.fossil.fossil.entity.animation.AttackAnimationLogic.ServerAttackAnimationInfo;

public class Mammoth extends PrehistoricFlocking implements Shearable {
    public static final String ANIMATIONS = "mammoth.animation.json";
    public static final String IDLE = "animation.dilophosaurus.idle";
    public static final String ATTACK1 = "animation.dilophosaurus.attack1";
    private static final LazyLoadedValue<Map<String, ServerAnimationInfo>> allAnimations = new LazyLoadedValue<>(() -> {
        Map<String, ServerAnimationInfo> newMap = new HashMap<>();
        List<AnimationManager.Animation> animations = AnimationManager.ANIMATIONS.getAnimation(ANIMATIONS);
        for (AnimationManager.Animation animation : animations) {
            ServerAnimationInfo info;
            switch (animation.animationId()) {
                case ATTACK1 -> info = new ServerAttackAnimationInfo(animation, animation.attackDelay());
                case IDLE -> info = new ServerAnimationInfo(animation);
                default -> info = new ServerAnimationInfo(animation);
            }
            newMap.put(animation.animationId(), info);
        }
        return newMap;
    });
    private static final EntityDataAccessor<Boolean> SHEARED = SynchedEntityData.defineId(Mammoth.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataManager.Data data = EntityDataManager.ENTITY_DATA.getData("mammoth");
    private final AnimationFactory factory = GeckoLibUtil.createFactory(this);
    private int woolRegenTicks;

    public Mammoth(EntityType<Mammoth> entityType, Level level) {
        super(entityType, level, false);
        hasTeenTexture = false;
    }

    @Override
    public Entity[] getCustomParts() {
        return new Entity[0];
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        goalSelector.addGoal(0, new FleeBattleGoal(this, 1));
        goalSelector.addGoal(1, new DinoMeleeAttackAI(this, 1, false));
        goalSelector.addGoal(1, new FloatGoal(this));
        goalSelector.addGoal(2, new FlockWanderGoal(this, 1));
        goalSelector.addGoal(3, new DinoWanderGoal(this, 1));
        goalSelector.addGoal(6, new DinoFollowOwnerGoal(this, 1, 10, 2, false));
        goalSelector.addGoal(7, new DinoLookAroundGoal(this));
        targetSelector.addGoal(1, new DinoOwnerHurtByTargetGoal(this));
        targetSelector.addGoal(2, new DinoOwnerHurtTargetGoal(this));
        targetSelector.addGoal(3, new HurtByTargetGoal(this));
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
        if (tooWarm && getEffect(MobEffects.WEAKNESS) != null && !isSheared() && !isSkeleton()) {
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
        return !isSheared() && !isBaby() && !isSkeleton();
    }

    public boolean isSheared() {
        return entityData.get(SHEARED);
    }

    public void setSheared(boolean sheared) {
        entityData.set(SHEARED, sheared);
    }

    @Override
    protected float getGenderedScale() {
        return getGender() == Gender.MALE ? 1.15f : super.getGenderedScale();
    }

    @Override
    public float getTargetScale() {
        return 1.5f;
    }

    @Override
    public EntityDataManager.Data data() {
        return data;
    }

    @Override
    public Map<String, ServerAnimationInfo> getAllAnimations() {
        return allAnimations.get();
    }

    @Override
    public @NotNull ServerAnimationInfo nextEatingAnimation() {
        return getAllAnimations().get(IDLE);
    }

    @Override
    public @NotNull ServerAnimationInfo nextIdleAnimation() {
        return getAllAnimations().get(IDLE);
    }

    @Override
    public @NotNull ServerAnimationInfo nextMovingAnimation() {
        return getAllAnimations().get(IDLE);
    }

    @Override
    public @NotNull ServerAnimationInfo nextChasingAnimation() {
        return getAllAnimations().get(IDLE);
    }

    @Override
    public @NotNull ServerAttackAnimationInfo nextAttackAnimation() {
        return (ServerAttackAnimationInfo) getAllAnimations().get(ATTACK1);
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