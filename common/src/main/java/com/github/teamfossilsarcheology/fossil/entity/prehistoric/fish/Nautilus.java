package com.github.teamfossilsarcheology.fossil.entity.prehistoric.fish;

import com.github.teamfossilsarcheology.fossil.entity.animation.AnimationCategory;
import com.github.teamfossilsarcheology.fossil.entity.animation.AnimationInfo;
import com.github.teamfossilsarcheology.fossil.entity.animation.AnimationLogic;
import com.github.teamfossilsarcheology.fossil.entity.animation.PausableAnimationController;
import com.github.teamfossilsarcheology.fossil.entity.prehistoric.base.Prehistoric;
import com.github.teamfossilsarcheology.fossil.entity.prehistoric.base.PrehistoricEntityInfo;
import com.github.teamfossilsarcheology.fossil.entity.prehistoric.base.PrehistoricFish;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import software.bernie.geckolib3.core.AnimationState;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;

import java.util.List;

public class Nautilus extends PrehistoricFish {
    public static final String ANIMATIONS = "nautilus.animation.json";
    public static final String IDLE = "animation.nautilus.idle";
    public static final String SWIM_BACKWARDS = "animation.nautilus.swim_backwards";
    public static final String SWIM_FORWARDS = "animation.nautilus.swim_forwards";
    public static final String SHELL_RETRACT = "animation.nautilus.shell_retract";
    public static final String SHELL_HOLD = "animation.nautilus.shell_hold";
    public static final String SHELL_EMERGE = "animation.nautilus.shell_emerge";
    public static final String BEACHED = "animation.nautilus.land";
    private static final EntityDataAccessor<Boolean> IS_IN_SHELL = SynchedEntityData.defineId(Nautilus.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDimensions SHELL_DIMENSIONS = EntityDimensions.fixed(1, 0.5f);
    private float ticksUntilShellUpdate = 0;
    private float ticksInShell = -1;

    public Nautilus(EntityType<Nautilus> entityType, Level level) {
        super(entityType, level);
    }

    private static boolean getsScaredBy(Entity entity) {
        if (entity instanceof Player player && !player.isCreative() && !player.isSpectator()) {
            return true;
        }
        if (entity instanceof Prehistoric prehistoric) {
            return prehistoric.data().diet().isCarnivore();
        }
        if (entity instanceof Nautilus) {
            return false;
        }
        return entity.getBbWidth() >= 1.2;
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        entityData.define(IS_IN_SHELL, false);
    }

    @Override
    public void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        compound.putBoolean("InShell", isInShell());
    }

    @Override
    public void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        hideInShell(compound.getBoolean("InShell"));
    }

    @Override
    public @NotNull PrehistoricEntityInfo info() {
        return PrehistoricEntityInfo.NAUTILUS;
    }

    @Override
    public void travel(Vec3 travelVector) {
        if (isInShell() && isInWater()) {
            if (!level.isClientSide) {
                setDeltaMovement(0, -0.05, 0);
                move(MoverType.SELF, getDeltaMovement());
            }
        } else {
            super.travel(travelVector);
        }
    }

    @Override
    public void aiStep() {
        super.aiStep();
        if (!level.isClientSide) {
            if (ticksUntilShellUpdate > 0) {
                ticksUntilShellUpdate--;
            }
            if (ticksInShell > -1) {
                ticksInShell++;
            }
            if (isInShell() && ticksInShell > 20) {
                resetFallDistance();
            }
            if (ticksUntilShellUpdate == 0) {
                List<LivingEntity> nearbyMobs = level.getEntitiesOfClass(LivingEntity.class, getBoundingBox().inflate(2, 2, 2), Nautilus::getsScaredBy);
                if (!nearbyMobs.isEmpty()) {
                    closeShell();
                }
                if (shouldBeBeached()) {
                    closeShell();
                }
                if (ticksInShell > 20 && ticksUntilShellUpdate == 0 && isInShell() && nearbyMobs.isEmpty()) {
                    openShell();
                }
            }
        }
        if (tickCount % 20 == 0) {
            Vec3 oldPos = position();
            refreshDimensions();
            //Prevent little jump after bounding box change
            setPos(oldPos);
        }
    }

    @Override
    public @NotNull EntityDimensions getDimensions(Pose pose) {
        return shouldBeBeached() ? SHELL_DIMENSIONS : super.getDimensions(pose);
    }

    @Override
    protected @NotNull InteractionResult mobInteract(Player player, InteractionHand hand) {
        ItemStack itemStack = player.getItemInHand(hand);
        if (itemStack.is(Items.FLINT)) {
            if (!level.isClientSide) {
                openShell();
            }
            return InteractionResult.sidedSuccess(level.isClientSide);
        }
        return super.mobInteract(player, hand);
    }

    @Override
    protected void handleAirSupply(int airSupply) {
        if (!isInShell()) {
            super.handleAirSupply(airSupply);
        }
    }

    @Override
    public boolean hurt(DamageSource source, float amount) {
        if (amount > 0 && isInShell() && ticksInShell > 20 && source.getEntity() != null) {
            playSound(SoundEvents.ITEM_BREAK, 1, random.nextFloat() + 0.8f);
            if (getVehicle() != null) {
                return super.hurt(source, amount);
            }
            return false;
        }
        closeShell();
        return super.hurt(source, amount);
    }

    @Override
    public boolean isPushedByFluid() {
        return isInShell();
    }

    private boolean shouldBeBeached() {
        if (isInWater() || level.getFluidState(blockPosition()).is(FluidTags.WATER)) {
            return false;
        }
        return isOnGround() || !level.getFluidState(blockPosition().below()).is(FluidTags.WATER);
    }

    public boolean isInShell() {
        return entityData.get(IS_IN_SHELL);
    }

    public void hideInShell(boolean close) {
        if (close) {
            closeShell();
        } else {
            openShell();
        }
    }

    public void closeShell() {
        if (!entityData.get(IS_IN_SHELL)) {
            ticksInShell = 0;
        }
        entityData.set(IS_IN_SHELL, true);
        ticksUntilShellUpdate = 60;
    }

    public void openShell() {
        entityData.set(IS_IN_SHELL, false);
        ticksUntilShellUpdate = 60;
        ticksInShell = -1;
    }

    @Override
    public void registerControllers(AnimationData data) {
        data.addAnimationController(new PausableAnimationController<>(this, AnimationLogic.IDLE_CTRL, 20, event -> {
            var ctrl = event.getController();
            if (shouldBeBeached()) {
                ctrl.setAnimation(new AnimationBuilder().addAnimation(nextBeachedAnimation().animation.animationName));
            } else if (isInShell() && isInWater()) {
                return PlayState.STOP;
            } else if (event.isMoving()) {
                ctrl.setAnimation(new AnimationBuilder().addAnimation(nextWalkingAnimation().animation.animationName));
            } else {
                ctrl.setAnimation(new AnimationBuilder().addAnimation(nextIdleAnimation().animation.animationName));
            }
            return PlayState.CONTINUE;
        }));
        data.addAnimationController(new PausableAnimationController<>(this, "Shell", 4, this::shellPredicate));
    }

    private PlayState shellPredicate(AnimationEvent<Nautilus> event) {
        var ctrl = event.getController();
        var anim = ctrl.getCurrentAnimation();
        if (event.getAnimatable().isInShell()) {
            if (anim == null || anim.animationName.equals(SHELL_EMERGE) && ctrl.getAnimationState() == AnimationState.Stopped) {
                ctrl.setAnimation(new AnimationBuilder().addAnimation(SHELL_RETRACT).addAnimation(SHELL_HOLD));
            }
        } else {
            if (anim != null && anim.animationName.equals(SHELL_HOLD)) {
                ctrl.setAnimation(new AnimationBuilder().addAnimation(SHELL_EMERGE));
            }
        }
        return PlayState.CONTINUE;
    }

    @Override
    public AnimationInfo getAnimation(AnimationCategory category) {
        if (category == AnimationCategory.SWIM) {
            return getAllAnimations().get(SWIM_BACKWARDS);
        } else if (category == AnimationCategory.SWIM_FAST) {
            return getAllAnimations().get(SWIM_FORWARDS);
        }
        return super.getAnimation(category);
    }
}