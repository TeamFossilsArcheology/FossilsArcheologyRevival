package com.github.teamfossilsarcheology.fossil.forge.mixin;

import com.github.teamfossilsarcheology.fossil.client.model.PrehistoricAnimatableModel;
import com.github.teamfossilsarcheology.fossil.compat.geckolib.AnimationProcessorOverride;
import com.github.teamfossilsarcheology.fossil.entity.prehistoric.base.PrehistoricAnimatable;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;
import org.apache.commons.lang3.tuple.Pair;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.IAnimatableModel;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.molang.MolangParser;
import software.bernie.geckolib3.core.processor.AnimationProcessor;
import software.bernie.geckolib3.core.processor.IBone;
import software.bernie.geckolib3.core.snapshot.BoneSnapshot;
import software.bernie.geckolib3.core.snapshot.DirtyTracker;

import java.util.List;
import java.util.Map;

@Mixin(AnimationProcessor.class)
public class AnimationProcessorMixin<T extends IAnimatable> {

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
    }
}
