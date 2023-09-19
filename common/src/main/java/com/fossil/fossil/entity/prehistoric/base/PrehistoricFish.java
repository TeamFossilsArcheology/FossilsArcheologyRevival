package com.fossil.fossil.entity.prehistoric.base;

import com.fossil.fossil.Fossil;
import com.fossil.fossil.entity.animation.AnimationInfoManager;
import com.fossil.fossil.entity.animation.AnimationLogic;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.SmoothSwimmingLookControl;
import net.minecraft.world.entity.ai.control.SmoothSwimmingMoveControl;
import net.minecraft.world.entity.animal.AbstractFish;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Blocks;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.Animation;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.resource.GeckoLibCache;

import java.util.List;
import java.util.Map;
import java.util.Random;

public abstract class PrehistoricFish extends AbstractFish implements PrehistoricAnimatable, PrehistoricDebug {
    private static final EntityDataAccessor<CompoundTag> ACTIVE_ANIMATIONS = SynchedEntityData.defineId(PrehistoricFish.class, EntityDataSerializers.COMPOUND_TAG);
    private static final EntityDataAccessor<Boolean> BABY = SynchedEntityData.defineId(PrehistoricFish.class, EntityDataSerializers.BOOLEAN);
    public static final EntityDataAccessor<CompoundTag> DEBUG = SynchedEntityData.defineId(PrehistoricFish.class, EntityDataSerializers.COMPOUND_TAG);
    private final ResourceLocation animationLocation;

    private int absoluteEggCooldown = 0;
    private int age;

    public PrehistoricFish(EntityType<? extends PrehistoricFish> entityType, Level level) {
        super(entityType, level);
        this.animationLocation = new ResourceLocation(Fossil.MOD_ID, "animations/" + EntityType.getKey(entityType).getPath() + ".animation.json");
        this.moveControl = new SmoothSwimmingMoveControl(this, 85, 10, 0.02f, 0.1f, true);
        this.lookControl = new SmoothSwimmingLookControl(this, 10);
    }

    public static AttributeSupplier.@NotNull Builder createAttributes() {
        return AbstractFish.createAttributes().add(Attributes.MOVEMENT_SPEED, 0.25);
    }

    public static boolean canSpawn(EntityType<? extends PrehistoricFish> entityType, LevelAccessor level, MobSpawnType spawnType, BlockPos pos, Random rand) {
        return pos.getY() < level.getSeaLevel() && level.getFluidState(pos.below()).is(FluidTags.WATER) && level.getBlockState(pos.above()).is(Blocks.WATER);
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        entityData.define(ACTIVE_ANIMATIONS, new CompoundTag());
        entityData.define(BABY, false);
        CompoundTag tag = new CompoundTag();
        tag.putDouble("x", position().x);
        tag.putDouble("y", position().y);
        tag.putDouble("z", position().z);
        tag.putBoolean("disableGoalAI", false);
        tag.putBoolean("disableMoveAI", false);
        tag.putBoolean("disableLookAI", false);
        entityData.define(DEBUG, tag);
    }

    @NotNull
    public abstract PrehistoricEntityType type();

    @Override
    public void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        compound.putInt("absoluteEggCooldown", absoluteEggCooldown);
    }

    @Override
    public void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        absoluteEggCooldown = compound.getInt("absoluteEggCooldown");
    }

    @Override
    public boolean isInvulnerableTo(DamageSource source) {
        //TODO: Is needed?
        //return source == DamageSource.IN_WALL || super.isInvulnerableTo(source);
        return super.isInvulnerableTo(source);
    }

    @Override
    protected @NotNull SoundEvent getFlopSound() {
        return SoundEvents.COD_FLOP;//TODO: Flops
    }

    @Override
    public @NotNull ItemStack getBucketItemStack() {
        return new ItemStack(type().bucketItem);
    }

    @Override
    public boolean isBaby() {
        return entityData.get(BABY);
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
        entityData.set(BABY, age < 0);
    }

    @Override
    public void onSyncedDataUpdated(EntityDataAccessor<?> key) {
        if (BABY.equals(key)) {
            refreshDimensions();
        }
        super.onSyncedDataUpdated(key);
    }

    @Override
    public void tick() {
        super.tick();
        if (!level.isClientSide) {
            if (absoluteEggCooldown > 0) {
                absoluteEggCooldown--;
            }
            PrehistoricFish closestMate = getClosestMate();
            if (closestMate != null && isInWater() && getAge() >= 0 && closestMate.getAge() >= 0 && absoluteEggCooldown <= 0) {
                absoluteEggCooldown = 48000 + random.nextInt(48000);
                level.addFreshEntity(new ItemEntity(level, getX(), getY(), getZ(), new ItemStack(type().eggItem)));
            }
        }
    }

    private @Nullable PrehistoricFish getClosestMate() {
        List<? extends PrehistoricFish> sameTypes = level.getEntitiesOfClass(getClass(), getBoundingBox().inflate(2, 2, 2), fish -> fish != this);
        double shortestDistance = Double.MAX_VALUE;
        PrehistoricFish other = null;
        for (PrehistoricFish sameType : sameTypes) {
            if (distanceToSqr(sameType) > shortestDistance) {
                continue;
            }
            other = sameType;
            shortestDistance = distanceToSqr(other);
        }
        return other;
    }

    @Override
    public CompoundTag getDebugTag() {
        return entityData.get(DEBUG);
    }

    @Override
    protected @NotNull InteractionResult mobInteract(Player player, InteractionHand hand) {
        ItemStack itemStack = player.getItemInHand(hand);
        if (itemStack.isEmpty() && isAlive() && getAge() > 0) {
            playSound(SoundEvents.ITEM_PICKUP, 1, random.nextFloat() + 0.8f);
            if (!level.isClientSide) {
                spawnAtLocation(new ItemStack(type().foodItem), 0.1f);
            }
            discard();
            return InteractionResult.sidedSuccess(level.isClientSide);
        }
        return super.mobInteract(player, hand);
    }

    @Nullable
    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor level, DifficultyInstance difficulty, MobSpawnType reason, @Nullable SpawnGroupData spawnData, @Nullable CompoundTag dataTag) {
        if (spawnData instanceof Prehistoric.PrehistoricGroupData prehistoricGroupData) {
            setAge(prehistoricGroupData.ageInDays() * 24000);
        } else {
            setAge(0);
        }
        return super.finalizeSpawn(level, difficulty, reason, spawnData, dataTag);
    }

    @Override
    public boolean shouldStartEatAnimation() {
        return false;
    }

    @Override
    public void setStartEatAnimation(boolean start) {

    }

    @Override
    public Map<String, Animation> getAllAnimations() {
        return GeckoLibCache.getInstance().getAnimations().get(animationLocation).animations();
    }
    @Override
    public Map<String, AnimationInfoManager.ServerAnimationInfo> getServerAnimationInfos() {
        return AnimationInfoManager.ANIMATIONS.getAnimation(animationLocation.getPath());
    }

    @Override
    public void registerControllers(AnimationData data) {
        data.addAnimationController(new AnimationController<>(this, "Movement/Idle/Eat", 4, event -> {
            AnimationController<PrehistoricFish> controller = event.getController();
            if (!isInWater()) {
                addActiveAnimation(controller.getName(), nextFloppingAnimation());
            } else if (event.isMoving()) {
                addActiveAnimation(controller.getName(), nextMovingAnimation());
            } else {
                addActiveAnimation(controller.getName(), nextIdleAnimation());
            }
            AnimationLogic.ActiveAnimationInfo activeAnimation = getActiveAnimation(controller.getName());
            if (activeAnimation != null) {
                controller.setAnimation(new AnimationBuilder().addAnimation(activeAnimation.animationName()));
            }
            return PlayState.CONTINUE;
        }));
        //data.addAnimationController(new AnimationController<>(this, "Walk", 4, animations::walkPredicate));
    }

    public @Nullable AnimationLogic.ActiveAnimationInfo getActiveAnimation(String controller) {
        CompoundTag animationTag = entityData.get(ACTIVE_ANIMATIONS).getCompound(controller);
        if (animationTag.contains("Animation")) {
            return new AnimationLogic.ActiveAnimationInfo(animationTag.getString("Animation"), animationTag.getDouble("EndTick"));
        }
        return null;
    }

    @Override
    public void addActiveAnimation(String controller, Animation animation) {
        CompoundTag allAnimations = new CompoundTag().merge(entityData.get(ACTIVE_ANIMATIONS));
        CompoundTag animationTag = new CompoundTag();
        if (animation != null) {
            animationTag.putString("Animation", animation.animationName);
            animationTag.putDouble("EndTick", level.getGameTime() + animation.animationLength);
            allAnimations.put(controller, animationTag);
            entityData.set(ACTIVE_ANIMATIONS, allAnimations);
        } else {
            Fossil.LOGGER.error("PrehistoricFish Animation is null: " + controller);
        }
    }

    @Override
    public @NotNull Animation nextEatingAnimation() {
        return nextIdleAnimation();
    }

    public abstract @NotNull Animation nextFloppingAnimation();

    public @Nullable Animation nextTurningAnimation() {
        return null;
    }

    public void disableCustomAI(byte type, boolean disableAI) {
        setNoAi(disableAI);
    }
}
