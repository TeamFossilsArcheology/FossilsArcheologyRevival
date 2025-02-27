package com.github.teamfossilsarcheology.fossil.forge.mixin;

import org.spongepowered.asm.mixin.Mixin;
import software.bernie.geckolib.core.animatable.GeoAnimatable;
import software.bernie.geckolib.core.animation.AnimationProcessor;

@Mixin(AnimationProcessor.class)
public class AnimationProcessorMixin<T extends GeoAnimatable> {
/*
    @Shadow
    @Final
    private IAnimatableModel animatedModel;

    @Shadow
    public boolean reloadAnimations;

    @Shadow
    @Final
    private List<IBone> modelRendererList;

    @Inject(method = "tickAnimation", remap = false, at = @At(value = "INVOKE", target = "Lsoftware/bernie/geckolib3/core/manager/AnimationData;getAnimationControllers()Ljava/util/Map;"))
    public void tickAdditiveAnimations(IAnimatable entity, int uniqueID, double seekTime, AnimationEvent<T> event, MolangParser parser, boolean crashWhenCantFindBone, CallbackInfo ci,
                                       @Local AnimationData manager, @Local(name = "modelTracker") Map<String, DirtyTracker> modelTracker, @Local(name = "boneSnapshots") Map<String, Pair<IBone, BoneSnapshot>> boneSnapshots) {
        if (entity instanceof PrehistoricAnimatable<?>) {
            AnimationProcessorOverride.tickAdditiveAnimations(seekTime, event, reloadAnimations, modelRendererList, parser, crashWhenCantFindBone, manager, modelTracker, boneSnapshots);
        }
    }

    @ModifyExpressionValue(method = "tickAnimation", remap = false, at = @At(value = "INVOKE", target = "Lsoftware/bernie/geckolib3/core/manager/AnimationData;getAnimationControllers()Ljava/util/Map;"))
    public Map<String, AnimationController<T>> cancelDefaultAnimations(Map<String, AnimationController<T>> original) {
        if (animatedModel instanceof PrehistoricAnimatableModel<?>) {
            return Map.of();
        }
        return original;
    }*/
}
