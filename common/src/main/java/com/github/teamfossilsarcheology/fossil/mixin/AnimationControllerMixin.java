package com.github.teamfossilsarcheology.fossil.mixin;

import com.github.teamfossilsarcheology.fossil.compat.geckolib.AnimationControllerOverride;
import com.github.teamfossilsarcheology.fossil.entity.prehistoric.base.PrehistoricAnimatable;
import com.llamalad7.mixinextras.sugar.Local;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import software.bernie.geckolib.core.animatable.GeoAnimatable;
import software.bernie.geckolib.core.animatable.model.CoreGeoBone;
import software.bernie.geckolib.core.animatable.model.CoreGeoModel;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.AnimationProcessor;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.core.keyframe.BoneAnimationQueue;
import software.bernie.geckolib.core.state.BoneSnapshot;

import java.util.Map;

@Mixin(AnimationController.class)
public class AnimationControllerMixin<T extends GeoAnimatable> {

    @Shadow
    @Final
    protected Map<String, BoneAnimationQueue> boneAnimationQueues;

    @Shadow
    @Final
    protected Map<String, BoneSnapshot> boneSnapshots;

    @Shadow
    protected boolean isJustStarting;

    @Shadow
    protected double transitionLength;

    @Shadow
    protected AnimationProcessor.QueuedAnimation currentAnimation;

    @Inject(method = "process", remap = false, at = @At(value = "INVOKE", target = "Lsoftware/bernie/geckolib/core/molang/MolangParser;setValue(Ljava/lang/String;Ljava/util/function/DoubleSupplier;)V"))
    public void tickAdditiveAnimations(CoreGeoModel<T> model, AnimationState<T> state, Map<String, CoreGeoBone> bones,
                                       Map<String, BoneSnapshot> snapshots, double seekTime, boolean crashWhenCantFindBone, CallbackInfo ci,
                                       @Local(name = "adjustedTick") double adjustedTick) {
        if (state.getAnimatable() instanceof PrehistoricAnimatable<?>) {
            AnimationControllerOverride.fixTransitions(bones, currentAnimation, boneSnapshots, adjustedTick, adjustedTick == 0 || isJustStarting, snapshots, transitionLength, boneAnimationQueues);
        }
    }
}
