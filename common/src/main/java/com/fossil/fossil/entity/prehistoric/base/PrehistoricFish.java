package com.fossil.fossil.entity.prehistoric.base;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.animal.AbstractFish;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.manager.AnimationData;

import java.util.List;

public abstract class PrehistoricFish extends AbstractFish implements PrehistoricAnimatable, PrehistoricDebug {
    public static final EntityDataAccessor<CompoundTag> DEBUG = SynchedEntityData.defineId(PrehistoricFish.class, EntityDataSerializers.COMPOUND_TAG);
    private static final EntityDataAccessor<String> CURRENT_ANIMATION = SynchedEntityData.defineId(PrehistoricFish.class, EntityDataSerializers.STRING);
    protected final AnimationComponent<PrehistoricFish> animations = new AnimationComponent<>(this);
    private int absoluteEggCooldown = 0;

    public PrehistoricFish(EntityType<? extends PrehistoricFish> entityType, Level level) {
        super(entityType, level);
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(CURRENT_ANIMATION, initialAnimation().animationId);
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

    public static AttributeSupplier.@NotNull Builder createAttributes() {
        return AbstractFish.createAttributes().add(Attributes.MOVEMENT_SPEED, 0.25);
    }

    @Override
    public float getScale() {
        return super.getScale();
    }

    public float getModelScale() {
        return getScale();
    }

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
    protected @NotNull SoundEvent getFlopSound() {
        return SoundEvents.COD_FLOP;//TODO: Flops
    }

    @Override
    public @NotNull ItemStack getBucketItemStack() {
        return new ItemStack(Items.COD_BUCKET);//TODO: BUcket
    }

    @Override
    public void tick() {
        super.tick();
        if (!level.isClientSide) {
            if (absoluteEggCooldown > 0) {
                absoluteEggCooldown--;
            }
            PrehistoricFish closestMate = getClosestMate();
            if (closestMate != null && isInWater() && absoluteEggCooldown <= 0) {
                absoluteEggCooldown = 48000 + random.nextInt(48000);
                level.addFreshEntity(new ItemEntity(level, getX(), getY(), getZ(), new ItemStack(type().eggItem)));
            }
            animations.tick();
        }
    }

    @Override
    public void aiStep() {
        super.aiStep();
        if (!isInWater()) {
            setCurrentAnimation(nextFloppingAnimation());
        }
    }

    @Override
    public CompoundTag getDebugTag() {
        return entityData.get(DEBUG);
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
    public void registerControllers(AnimationData data) {
        //data.addAnimationController(new AnimationController<>(this, "controller", 4, animations::onFrame));
        data.addAnimationController(new AnimationController<>(this, "Walk", 4, event -> {
            if (!isInWater()) {
                event.getController().setAnimation(new AnimationBuilder().addAnimation(nextFloppingAnimation().animationId));
            } else if (event.isMoving()) {
                event.getController().setAnimation(new AnimationBuilder().addAnimation(nextMovingAnimation().animationId));
            } else {
                event.getController().setAnimation(new AnimationBuilder().addAnimation(nextIdleAnimation().animationId));
            }
            return PlayState.CONTINUE;
        }));
        //data.addAnimationController(new AnimationController<>(this, "Walk", 4, animations::walkPredicate));
    }

    @Override
    public Prehistoric.ServerAnimationInfo getCurrentAnimation() {
        return getAllAnimations().get(entityData.get(CURRENT_ANIMATION));
    }

    public void setCurrentAnimation(@NotNull Prehistoric.ServerAnimationInfo newAnimation) {
        if (this.entityData.get(CURRENT_ANIMATION).equals(newAnimation.animationId)) return;
        this.entityData.set(CURRENT_ANIMATION, newAnimation.animationId);
        animations.setCurrentAnimation(newAnimation);
    }

    public abstract @NotNull Prehistoric.ServerAnimationInfo nextFloppingAnimation();

    public static boolean canSpawn(Level level, BlockPos pos) {
        return pos.getY() < level.getSeaLevel() && level.getFluidState(pos.below()).is(FluidTags.WATER) && level.getBlockState(pos.above()).is(Blocks.WATER);
    }

    public void disableCustomAI(byte type, boolean disableAI) {
        setNoAi(disableAI);
    }
}
