package com.github.teamfossilsarcheology.fossil.forge.mixin;

import com.github.teamfossilsarcheology.fossil.compat.geckolib.AnimationControllerOverride;
import com.github.teamfossilsarcheology.fossil.entity.prehistoric.base.PrehistoricAnimatable;
import com.llamalad7.mixinextras.sugar.Local;
import org.apache.commons.lang3.tuple.Pair;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.builder.Animation;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.keyframe.BoneAnimationQueue;
import software.bernie.geckolib3.core.molang.MolangParser;
import software.bernie.geckolib3.core.processor.IBone;
import software.bernie.geckolib3.core.snapshot.BoneSnapshot;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Mixin(AnimationController.class)
public class AnimationControllerMixin<T extends IAnimatable> {

    @Shadow
    protected Animation currentAnimation;

    @Shadow
    @Final
    private HashMap<String, BoneSnapshot> boneSnapshots;

    @Shadow
    public double transitionLengthTicks;

    @Shadow
    @Final
    private HashMap<String, BoneAnimationQueue> boneAnimationQueues;

    @Shadow
    public boolean isJustStarting;

    @Inject(method = "process", remap = false, at = @At(value = "INVOKE", target = "Lsoftware/bernie/geckolib3/core/controller/AnimationController;setAnimTime(Lsoftware/bernie/geckolib3/core/molang/MolangParser;D)V"))
    public void tickAdditiveAnimations(double tick, AnimationEvent<T> event, List<IBone> modelRendererList, Map<String, Pair<IBone, BoneSnapshot>> boneSnapshotCollection,
                                       MolangParser parser, boolean crashWhenCantFindBone, CallbackInfo ci,
                                       @Local(name = "adjustedTick") double adjustedTick) {
        if (event.getAnimatable() instanceof PrehistoricAnimatable<?>) {
            AnimationControllerOverride.fixTransitions(modelRendererList, currentAnimation, boneSnapshots, adjustedTick, adjustedTick == 0 || isJustStarting, boneSnapshotCollection, transitionLengthTicks, boneAnimationQueues);
        }
    }
}
