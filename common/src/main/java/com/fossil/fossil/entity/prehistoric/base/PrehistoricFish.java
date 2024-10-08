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
import net.minecraft.world.entity.*;
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
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib3.core.builder.Animation;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.resource.GeckoLibCache;

import java.util.List;
import java.util.Map;
import java.util.Random;

public abstract class PrehistoricFish extends AbstractFish implements PrehistoricAnimatable<PrehistoricFish>, PrehistoricDebug {
    public static final EntityDataAccessor<CompoundTag> DEBUG = SynchedEntityData.defineId(PrehistoricFish.class, EntityDataSerializers.COMPOUND_TAG);
    private static final EntityDataAccessor<Boolean> BABY = SynchedEntityData.defineId(PrehistoricFish.class, EntityDataSerializers.BOOLEAN);
    private final ResourceLocation animationLocation;
    private final AnimationLogic<PrehistoricFish> animationLogic = new AnimationLogic<>(this);

    private int absoluteEggCooldown = 0;
    private int age;

    protected PrehistoricFish(EntityType<? extends PrehistoricFish> entityType, Level level) {
        super(entityType, level);
        this.animationLocation = new ResourceLocation(Fossil.MOD_ID, "animations/" + EntityType.getKey(entityType).getPath() + ".animation.json");
        this.moveControl = new SmoothSwimmingMoveControl(this, 85, 10, 0.2f, 0.1f, true);
        this.lookControl = new SmoothSwimmingLookControl(this, 10);
    }

    public static AttributeSupplier.@NotNull Builder createAttributes() {
        return Mob.createMobAttributes().add(Attributes.MAX_HEALTH, 20).add(Attributes.MOVEMENT_SPEED, 0.25);
    }

    public static boolean canSpawn(EntityType<? extends PrehistoricFish> entityType, LevelAccessor level, MobSpawnType spawnType, BlockPos pos, Random rand) {
        return pos.getY() < level.getSeaLevel() && level.getFluidState(pos.below()).is(FluidTags.WATER) && level.getBlockState(pos.above()).is(Blocks.WATER);
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
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
    public abstract PrehistoricEntityInfo info();

    @Override
    public void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        compound.putInt("AbsoluteEggCooldown", absoluteEggCooldown);
    }

    @Override
    public void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        absoluteEggCooldown = compound.getInt("AbsoluteEggCooldown");
    }

    @Override
    protected @NotNull SoundEvent getFlopSound() {
        return SoundEvents.COD_FLOP;//TODO: Flops
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
    public void travel(Vec3 travelVector) {
        if (isEffectiveAi() && isInWater()) {
            moveRelative(getSpeed(), travelVector);
            move(MoverType.SELF, getDeltaMovement());
            setDeltaMovement(getDeltaMovement().scale(0.9));
        } else {
            super.travel(travelVector);
        }
    }

    @Override
    protected void customServerAiStep() {
        super.customServerAiStep();
        setSprinting(getMoveControl().getSpeedModifier() >= 1.25);
    }

    @Override
    public void tick() {
        super.tick();
        if (!level.isClientSide) {
            if (absoluteEggCooldown > 0) {
                absoluteEggCooldown--;
            }
            if (isInWater() && getAge() >= 0 && absoluteEggCooldown <= 0) {
                PrehistoricFish closestMate = getClosestMate();
                if (closestMate != null) {
                    absoluteEggCooldown = 48000 + random.nextInt(48000);
                    closestMate.absoluteEggCooldown = 48000 + random.nextInt(48000);
                    level.addFreshEntity(new ItemEntity(level, getX(), getY(), getZ(), new ItemStack(info().eggItem)));
                }
            }
        }
    }

    private @Nullable PrehistoricFish getClosestMate() {
        List<? extends PrehistoricFish> sameTypes = level.getEntitiesOfClass(getClass(), getBoundingBox().inflate(2, 2, 2), fish -> fish != this && fish.getAge() > 0);
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
        //TODO: Maybe remove because bucket of water exists
        if (itemStack.isEmpty() && isAlive() && !isBaby()) {
            playSound(SoundEvents.ITEM_PICKUP, 1, random.nextFloat() + 0.8f);
            if (!level.isClientSide) {
                spawnAtLocation(new ItemStack(info().foodItem), 0.1f);
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
        return AnimationInfoManager.ANIMATIONS.getServerAnimations(animationLocation);
    }

    @Override
    public void registerControllers(AnimationData data) {
        data.addAnimationController(new AnimationController<>(this, AnimationLogic.IDLE_CTRL, 4, animationLogic::fishPredicate));
    }

    @Override
    public AnimationLogic<PrehistoricFish> getAnimationLogic() {
        return animationLogic;
    }

    @Override
    public @NotNull Animation nextEatingAnimation() {
        return nextIdleAnimation();
    }

    public abstract @NotNull Animation nextBeachedAnimation();

    @Override
    public @NotNull Animation nextSittingAnimation() {
        return nextIdleAnimation();
    }

    @Override
    public @NotNull Animation nextSleepingAnimation() {
        return nextIdleAnimation();
    }

    public @Nullable Animation nextTurningAnimation() {
        return null;
    }

    public void disableCustomAI(byte type, boolean disableAI) {
        setNoAi(disableAI);
    }
}
