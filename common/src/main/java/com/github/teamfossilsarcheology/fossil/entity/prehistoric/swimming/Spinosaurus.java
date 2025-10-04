package com.github.teamfossilsarcheology.fossil.entity.prehistoric.swimming;

import com.github.teamfossilsarcheology.fossil.entity.ai.DinoHurtByTargetGoal;
import com.github.teamfossilsarcheology.fossil.entity.ai.DinoOwnerHurtByTargetGoal;
import com.github.teamfossilsarcheology.fossil.entity.ai.DinoOwnerHurtTargetGoal;
import com.github.teamfossilsarcheology.fossil.entity.ai.LeaveWaterGoal;
import com.github.teamfossilsarcheology.fossil.entity.animation.AnimationCategory;
import com.github.teamfossilsarcheology.fossil.entity.animation.AnimationInfo;
import com.github.teamfossilsarcheology.fossil.entity.animation.AnimationLogic;
import com.github.teamfossilsarcheology.fossil.entity.animation.PausableAnimationController;
import com.github.teamfossilsarcheology.fossil.entity.prehistoric.base.Prehistoric;
import com.github.teamfossilsarcheology.fossil.entity.prehistoric.base.PrehistoricEntityInfo;
import com.github.teamfossilsarcheology.fossil.entity.prehistoric.base.PrehistoricSwimming;
import com.github.teamfossilsarcheology.fossil.entity.util.Util;
import com.github.teamfossilsarcheology.fossil.item.ModItems;
import com.github.teamfossilsarcheology.fossil.sounds.ModSounds;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.core.object.PlayState;

import java.util.Optional;

import static software.bernie.geckolib.core.animation.Animation.LoopType.LOOP;
import static software.bernie.geckolib.core.animation.Animation.LoopType.PLAY_ONCE;

public class Spinosaurus extends PrehistoricSwimming {
    public static final String GRAB = "animation.spinosaurus.grab";
    public static final String IDLE = "animation.spinosaurus.idle";
    public static final String IDLE_WATER = "animation.spinosaurus.idle_water";
    public static final String IDLE_WATER_FLOAT = "animation.spinosaurus.idle_water_float";
    public static final String SWIM_FLOATING = "animation.spinosaurus.swim_floating";
    public static final String SWIM_UNDERWATER = "animation.spinosaurus.swim_underwater";
    public static final String SWIM_GROUND = "animation.spinosaurus.swim_ground";
    private final SpinoAnimationLogic animationLogic = new SpinoAnimationLogic(this);

    public Spinosaurus(EntityType<Spinosaurus> entityType, Level level) {
        super(entityType, level);
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        goalSelector.addGoal(Util.WANDER + 1, new LeaveWaterGoal<>(this, 1));
        targetSelector.addGoal(1, new DinoOwnerHurtByTargetGoal(this));
        targetSelector.addGoal(2, new DinoOwnerHurtTargetGoal(this));
        targetSelector.addGoal(3, new DinoHurtByTargetGoal(this));
    }

    @Override
    public PrehistoricEntityInfo info() {
        return PrehistoricEntityInfo.SPINOSAURUS;
    }

    @Override
    public Item getOrderItem() {
        return ModItems.SKULL_STICK.get();
    }

    @Override
    public float getTargetScale() {
        return 1.5f;
    }

    @Override
    public float grabTargetSize() {
        return getBbWidth();
    }

    @Override
    public @NotNull AnimationInfo nextGrabbingAnimation() {
        return getAllAnimations().get(GRAB);
    }

    @Nullable
    @Override
    protected SoundEvent getAmbientSound() {
        return ModSounds.SPINOSAURUS_AMBIENT.get();
    }

    @Nullable
    @Override
    protected SoundEvent getHurtSound(DamageSource damageSource) {
        return ModSounds.SPINOSAURUS_HURT.get();
    }

    @Nullable
    @Override
    protected SoundEvent getDeathSound() {
        return ModSounds.SPINOSAURUS_DEATH.get();
    }

    @Override
    public AnimationLogic<Prehistoric> getAnimationLogic() {
        return animationLogic;
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllerRegistrar) {
        var controller = new PausableAnimationController<>(this, AnimationLogic.IDLE_CTRL, 5, animationLogic::spinoPredicate);
        registerEatingListeners(controller);
        controllerRegistrar.add(controller);
        controllerRegistrar.add(new PausableAnimationController<>(
                this, AnimationLogic.ATTACK_CTRL, 0, getAnimationLogic()::grabAttackPredicate));
    }

    static class SpinoAnimationLogic extends AnimationLogic<Prehistoric> {

        public SpinoAnimationLogic(PrehistoricSwimming entity) {
            super(entity);
        }

        public PlayState spinoPredicate(AnimationState<PrehistoricSwimming> state) {
            if (isBlocked()) return PlayState.STOP;
            AnimationController<PrehistoricSwimming> controller = state.getController();
            if (tryNextAnimation(state, controller)) {
                return PlayState.CONTINUE;
            }
            Optional<ActiveAnimationInfo> activeAnimation = getActiveAnimation(controller.getName());
            if (activeAnimation.isPresent() && tryForcedAnimation(state, activeAnimation.get())) {
                return PlayState.CONTINUE;
            }
            double animationSpeed = 1;
            if (state.getAnimatable().isBeached()) {
                addActiveAnimation(controller.getName(), AnimationCategory.BEACHED);
            } else if (entity.isSleeping()) {
                addActiveAnimation(controller.getName(), AnimationCategory.SLEEP);
            } else if (state.isMoving()) {
                if (entity.isInWater()) {
                    boolean onGround = (entity.getY() - Math.floor(entity.getY()) < 0.05) && entity.level().getFluidState(entity.blockPosition().below()).isEmpty();
                    if (entity.isEyeInFluid(FluidTags.WATER)) {
                        if (onGround) {
                            addActiveAnimation(controller.getName(), entity.getAnimation(SWIM_GROUND).animation, AnimationCategory.SWIM, false);
                        } else {
                            addActiveAnimation(controller.getName(), entity.getAnimation(SWIM_UNDERWATER).animation, AnimationCategory.SWIM, false);
                        }
                    } else {
                        if (onGround) {
                            animationSpeed = addMovementAnimation(state, false);
                        } else {
                            addActiveAnimation(controller.getName(), entity.getAnimation(SWIM_FLOATING).animation, AnimationCategory.SWIM, false);
                        }
                    }
                } else {
                    animationSpeed = addMovementAnimation(state, false);
                }
            } else if (state.getAnimatable().isWeak()) {
                addActiveAnimation(controller.getName(), AnimationCategory.KNOCKOUT);
            } else {
                if (entity.isInWater()) {
                    if (entity.isEyeInFluid(FluidTags.WATER)) {
                        addActiveAnimation(controller.getName(), entity.getAnimation(IDLE_WATER).animation, AnimationCategory.IDLE, false);
                    } else {
                        addActiveAnimation(controller.getName(), entity.getAnimation(IDLE_WATER_FLOAT).animation, AnimationCategory.IDLE, false);
                    }
                } else {
                    addActiveAnimation(controller.getName(), entity.getAnimation(IDLE).animation, AnimationCategory.IDLE, false);
                }
            }
            setAnimationSpeed(controller, animationSpeed, state.getAnimationTick());
            Optional<ActiveAnimationInfo> newAnimation = getActiveAnimation(controller.getName());
            if (newAnimation.isPresent()) {
                controller.setAnimation(RawAnimation.begin().then(newAnimation.get().animationName(), newAnimation.get().loop() ? LOOP : PLAY_ONCE));
            }
            return PlayState.CONTINUE;
        }
    }
}