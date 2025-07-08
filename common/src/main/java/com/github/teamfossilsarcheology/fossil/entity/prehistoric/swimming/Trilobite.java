package com.github.teamfossilsarcheology.fossil.entity.prehistoric.swimming;

import com.github.teamfossilsarcheology.fossil.FossilMod;
import com.github.teamfossilsarcheology.fossil.entity.ai.DinoHurtByTargetGoal;
import com.github.teamfossilsarcheology.fossil.entity.ai.DinoOwnerHurtByTargetGoal;
import com.github.teamfossilsarcheology.fossil.entity.ai.DinoOwnerHurtTargetGoal;
import com.github.teamfossilsarcheology.fossil.entity.ai.navigation.TrilobitePathNavigation;
import com.github.teamfossilsarcheology.fossil.entity.animation.AnimationCategory;
import com.github.teamfossilsarcheology.fossil.entity.animation.AnimationLogic;
import com.github.teamfossilsarcheology.fossil.entity.animation.PausableAnimationController;
import com.github.teamfossilsarcheology.fossil.entity.prehistoric.base.PrehistoricSwimmingBucketable;
import com.github.teamfossilsarcheology.fossil.item.ModItems;
import com.github.teamfossilsarcheology.fossil.sounds.ModSounds;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.core.object.PlayState;

import java.util.Optional;

import static software.bernie.geckolib.core.animation.Animation.LoopType.LOOP;
import static software.bernie.geckolib.core.animation.Animation.LoopType.PLAY_ONCE;

public abstract class Trilobite extends PrehistoricSwimmingBucketable {

    protected Trilobite(EntityType<? extends Trilobite> entityType, Level level) {
        super(entityType, level, FossilMod.location("animations/entity/trilobite.animation.json"));
        this.setPathfindingMalus(BlockPathTypes.WATER, 0);
        moveControl = new TrilobiteMoveControl();
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        targetSelector.addGoal(1, new DinoOwnerHurtByTargetGoal(this));
        targetSelector.addGoal(2, new DinoOwnerHurtTargetGoal(this));
        targetSelector.addGoal(3, new DinoHurtByTargetGoal(this));
    }

    @Override
    protected @NotNull PathNavigation createNavigation(Level level) {
        return new TrilobitePathNavigation(this, level);
    }

    @Override
    public boolean onClimbable() {
        return horizontalCollision && level().getFluidState(blockPosition().above()).is(FluidTags.WATER);
    }

    @Override
    public void travel(Vec3 travelVector) {
        super.travel(travelVector);
        if (onClimbable()) {
            setDeltaMovement(getDeltaMovement().add(0.0, 0.005, 0.0));
        } else if (level().getFluidState(blockPosition()).is(FluidTags.WATER)) {
            setDeltaMovement(getDeltaMovement().add(0.0, -0.005, 0.0));
        }
    }

    @Override
    public void refreshTexturePath() {
    }

    @Override
    public @NotNull MobType getMobType() {
        return MobType.ARTHROPOD;
    }

    @Override
    public Item getOrderItem() {
        return ModItems.MAGIC_CONCH.get();
    }

    @Nullable
    @Override
    protected SoundEvent getHurtSound(DamageSource damageSource) {
        return ModSounds.ARTHROPLEURA_HURT.get();
    }

    @Nullable
    @Override
    protected SoundEvent getDeathSound() {
        return ModSounds.ARTHROPLEURA_DEATH.get();
    }


    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllerRegistrar) {
        var ctrl = new PausableAnimationController<>(this, AnimationLogic.IDLE_CTRL, 5, state -> {
            AnimationController<Trilobite> controller = state.getController();
            if (getAnimationLogic().tryNextAnimation(state, controller)) {
                return PlayState.CONTINUE;
            }
            Optional<AnimationLogic.ActiveAnimationInfo> activeAnimation = getAnimationLogic().getActiveAnimation(controller.getName());
            if (activeAnimation.isPresent() && getAnimationLogic().tryForcedAnimation(state, activeAnimation.get())) {
                return PlayState.CONTINUE;
            }
            if (state.isMoving()) {
                getAnimationLogic().addActiveAnimation(controller.getName(), AnimationCategory.WALK);
            } else {
                getAnimationLogic().addActiveAnimation(controller.getName(), AnimationCategory.IDLE);
            }
            Optional<AnimationLogic.ActiveAnimationInfo> newAnimation = getAnimationLogic().getActiveAnimation(controller.getName());
            newAnimation.ifPresent(activeAnimationInfo -> controller.setAnimation(RawAnimation.begin().then(activeAnimationInfo.animationName(), activeAnimationInfo.loop() ? LOOP : PLAY_ONCE)));
            return PlayState.CONTINUE;
        });
        registerEatingListeners(ctrl);
        controllerRegistrar.add(ctrl);
    }

    class TrilobiteMoveControl extends MoveControl {

        public TrilobiteMoveControl() {
            super(Trilobite.this);
        }

        @Override
        public void tick() {
            if (this.operation == MoveControl.Operation.MOVE_TO) {
                this.operation = MoveControl.Operation.WAIT;
                double d = wantedX - mob.getX();
                double e = wantedZ - mob.getZ();
                double o = wantedY - mob.getY();
                double p = d * d + o * o + e * e;
                if (p < 2.5000003E-7F) {
                    mob.setZza(0.0F);
                    return;
                }

                float n = (float) (Mth.atan2(e, d) * 180.0F / (float) Math.PI) - 90.0F;
                this.mob.setYRot(rotlerp(mob.getYRot(), n, 90.0F));
                this.mob.setSpeed((float) (speedModifier * mob.getAttributeValue(Attributes.MOVEMENT_SPEED)) * 0.4f);
            } else {
                mob.setZza(0.0F);
            }
        }
    }
}
