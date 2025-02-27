package com.github.teamfossilsarcheology.fossil.forge.mixin;

import org.spongepowered.asm.mixin.Mixin;
import software.bernie.geckolib.core.animatable.GeoAnimatable;
import software.bernie.geckolib.core.animation.AnimationController;

@Mixin(AnimationController.class)
public class AnimationControllerMixin<T extends GeoAnimatable> {

    /*@Shadow
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
    }*/
}
