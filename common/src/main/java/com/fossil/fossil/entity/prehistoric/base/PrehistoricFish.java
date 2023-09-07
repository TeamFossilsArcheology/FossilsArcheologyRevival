package com.fossil.fossil.entity.prehistoric.base;

import com.fossil.fossil.entity.animation.AnimationLogic;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.SmoothSwimmingLookControl;
import net.minecraft.world.entity.ai.control.SmoothSwimmingMoveControl;
import net.minecraft.world.entity.animal.AbstractFish;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
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

import static com.fossil.fossil.entity.animation.AnimationLogic.ServerAnimationInfo;

public abstract class PrehistoricFish extends AbstractFish implements PrehistoricAnimatable, PrehistoricDebug {
    private static final EntityDataAccessor<CompoundTag> ACTIVE_ANIMATIONS = SynchedEntityData.defineId(PrehistoricFish.class, EntityDataSerializers.COMPOUND_TAG);
    public static final EntityDataAccessor<CompoundTag> DEBUG = SynchedEntityData.defineId(PrehistoricFish.class, EntityDataSerializers.COMPOUND_TAG);
    private int absoluteEggCooldown = 0;

    public PrehistoricFish(EntityType<? extends PrehistoricFish> entityType, Level level) {
        super(entityType, level);
        this.moveControl = new SmoothSwimmingMoveControl(this, 85, 10, 0.02f, 0.1f, true);
        this.lookControl = new SmoothSwimmingLookControl(this, 10);
    }

    public static AttributeSupplier.@NotNull Builder createAttributes() {
        return AbstractFish.createAttributes().add(Attributes.MOVEMENT_SPEED, 0.25);
    }

    public static boolean canSpawn(Level level, BlockPos pos) {
        return pos.getY() < level.getSeaLevel() && level.getFluidState(pos.below()).is(FluidTags.WATER) && level.getBlockState(pos.above()).is(Blocks.WATER);
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        entityData.define(ACTIVE_ANIMATIONS, new CompoundTag());
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
        }
    }

    @Override
    public CompoundTag getDebugTag() {
        return entityData.get(DEBUG);
    }

    @Override
    protected @NotNull InteractionResult mobInteract(Player player, InteractionHand hand) {
        ItemStack itemStack = player.getItemInHand(hand);
        if (itemStack.isEmpty() && isAlive()) {
            playSound(SoundEvents.ITEM_PICKUP, 1, random.nextFloat() + 0.8f);
            if (!level.isClientSide) {
                spawnAtLocation(new ItemStack(type().foodItem), 0.1f);
            }
            discard();
            return InteractionResult.sidedSuccess(level.isClientSide);
        }
        return super.mobInteract(player, hand);
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
    public boolean shouldStartEatAnimation() {
        return false;
    }

    @Override
    public void setStartEatAnimation(boolean start) {

    }

    @Override
    public void registerControllers(AnimationData data) {
        data.addAnimationController(new AnimationController<>(this, "Movement/Idle/Eat", 4, event -> {
            AnimationController<PrehistoricFish> controller = event.getController();
            if (!isInWater()) {
                addActiveAnimation(controller.getName(), nextFloppingAnimation());
                event.getController().setAnimation(new AnimationBuilder().addAnimation(nextFloppingAnimation().animationId));
            } else if (event.isMoving()) {
                addActiveAnimation(controller.getName(), nextMovingAnimation());
            } else {
                addActiveAnimation(controller.getName(), nextIdleAnimation());
            }
            AnimationLogic.ActiveAnimationInfo activeAnimation = getActiveAnimation(controller.getName());
            if (activeAnimation != null) {
                controller.setAnimation(new AnimationBuilder().addAnimation(activeAnimation.animationId()));
            }
            return PlayState.CONTINUE;
        }));
        //data.addAnimationController(new AnimationController<>(this, "Walk", 4, animations::walkPredicate));
    }

    public @Nullable AnimationLogic.ActiveAnimationInfo getActiveAnimation(String controller) {
        CompoundTag animationTag = entityData.get(ACTIVE_ANIMATIONS).getCompound(controller);
        if (animationTag.contains("Animation")) {
            return new AnimationLogic.ActiveAnimationInfo(animationTag.getString("Animation"), animationTag.getLong("EndTick"));
        }
        return null;
    }

    public void addActiveAnimation(String controller, ServerAnimationInfo animation) {
        CompoundTag allAnimations = new CompoundTag().merge(entityData.get(ACTIVE_ANIMATIONS));
        CompoundTag animationTag = new CompoundTag();
        animationTag.putString("Animation", animation.animationId);
        animationTag.putLong("EndTick", level.getGameTime() + animation.length);
        allAnimations.put(controller, animationTag);
        entityData.set(ACTIVE_ANIMATIONS, allAnimations);
    }

    @Override
    public @NotNull ServerAnimationInfo nextEatingAnimation() {
        return nextIdleAnimation();
    }

    public abstract @NotNull ServerAnimationInfo nextFloppingAnimation();

    public @Nullable ServerAnimationInfo nextTurningAnimation() {
        return null;
    }

    public void disableCustomAI(byte type, boolean disableAI) {
        setNoAi(disableAI);
    }
}
