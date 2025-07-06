package com.github.teamfossilsarcheology.fossil.compat.geckolib;

import com.github.teamfossilsarcheology.fossil.mixin.AnimatableManagerAccessor;
import com.github.teamfossilsarcheology.fossil.mixin.AnimationControllerAccessor;
import software.bernie.geckolib.core.animatable.GeoAnimatable;
import software.bernie.geckolib.core.animatable.model.CoreGeoBone;
import software.bernie.geckolib.core.animatable.model.CoreGeoModel;
import software.bernie.geckolib.core.animation.*;
import software.bernie.geckolib.core.keyframe.AnimationPoint;
import software.bernie.geckolib.core.keyframe.BoneAnimationQueue;
import software.bernie.geckolib.core.state.BoneSnapshot;

import java.util.Map;

public class AnimationProcessorOverride {

    /**
     * Replaces parts of  {@link AnimationProcessor#tickAnimation}
     * with the goal of making animations additive across controllers
     */
    public static <T extends GeoAnimatable> void tickAdditiveAnimations(T animatable, CoreGeoModel<T> model, double animTime, AnimationState<T> state, boolean reloadAnimations,
                                                                        Map<String, CoreGeoBone> bones, boolean crashWhenCantFindBone,
                                                                        AnimatableManager<T> manager, Map<String, BoneSnapshot> boneSnapshots) {
        for (AnimationController<T> controller : manager.getAnimationControllers().values()) {
            if (reloadAnimations) {
                controller.forceAnimationReset();
                controller.getBoneAnimationQueues().clear();
            }

            ((AnimationControllerAccessor<?>) controller).setIsJustStarting(((AnimatableManagerAccessor) manager).isFirstTick());

            state.withController(controller);
            // Process animations and add new values to the point queues
            controller.process(model, state, bones, boneSnapshots, animTime, crashWhenCantFindBone);
        }

        //FA: Moved bone iteration to outer loop to have an easier time adding the animations
        for (CoreGeoBone bone : bones.values()) {
            //FA: These are only true for the first animation. When false the previous bone value will be added to the new one
            boolean firstRot = true;
            boolean firstPos = true;
            boolean firstScale = true;
            BoneSnapshot snapshot = boneSnapshots.get(bone.getName());
            BoneSnapshot initialSnapshot = bone.getInitialSnapshot();
            for (AnimationController<T> controller : manager.getAnimationControllers().values()) {
                if (controller.getBoneAnimationQueues().isEmpty()) {
                    continue;
                }
                BoneAnimationQueue boneAnimation = controller.getBoneAnimationQueues().get(bone.getName());

                AnimationPoint rotXPoint = boneAnimation.rotationXQueue().poll();
                AnimationPoint rotYPoint = boneAnimation.rotationYQueue().poll();
                AnimationPoint rotZPoint = boneAnimation.rotationZQueue().poll();

                AnimationPoint posXPoint = boneAnimation.positionXQueue().poll();
                AnimationPoint posYPoint = boneAnimation.positionYQueue().poll();
                AnimationPoint posZPoint = boneAnimation.positionZQueue().poll();

                AnimationPoint scaleXPoint = boneAnimation.scaleXQueue().poll();
                AnimationPoint scaleYPoint = boneAnimation.scaleYQueue().poll();
                AnimationPoint scaleZPoint = boneAnimation.scaleZQueue().poll();
                EasingType easingType = ((AnimationControllerAccessor<T>) controller).getOverrideEasingTypeFunction().apply(animatable);

                // If there's any rotation points for this bone
                if (rotXPoint != null && rotYPoint != null && rotZPoint != null) {
                    bone.setRotX((float) EasingType.lerpWithOverride(rotXPoint, easingType)
                            + (firstRot ? initialSnapshot.getRotX() : bone.getRotX()));
                    bone.setRotY((float) EasingType.lerpWithOverride(rotYPoint, easingType)
                            + (firstRot ? initialSnapshot.getRotY() : bone.getRotY()));
                    bone.setRotZ((float) EasingType.lerpWithOverride(rotZPoint, easingType)
                            + (firstRot ? initialSnapshot.getRotZ() : bone.getRotZ()));
                    snapshot.updateRotation(bone.getRotX(), bone.getRotY(), bone.getRotZ());
                    snapshot.startRotAnim();
                    bone.markRotationAsChanged();
                    firstRot = false;
                }

                // If there's any position points for this bone
                if (posXPoint != null && posYPoint != null && posZPoint != null) {
                    bone.setPosX((float) EasingType.lerpWithOverride(posXPoint, easingType) + (firstPos ? 0 : bone.getPosX()));
                    bone.setPosY((float) EasingType.lerpWithOverride(posYPoint, easingType) + (firstPos ? 0 : bone.getPosY()));
                    bone.setPosZ((float) EasingType.lerpWithOverride(posZPoint, easingType) + (firstPos ? 0 : bone.getPosZ()));
                    snapshot.updateOffset(bone.getPosX(), bone.getPosY(), bone.getPosZ());
                    snapshot.startPosAnim();
                    bone.markPositionAsChanged();
                    firstPos = false;
                }

                // If there's any scale points for this bone
                if (scaleXPoint != null && scaleYPoint != null && scaleZPoint != null) {
                    bone.setScaleX((float) EasingType.lerpWithOverride(scaleXPoint, easingType) * (firstScale ? 1 : bone.getScaleX()));
                    bone.setScaleY((float) EasingType.lerpWithOverride(scaleYPoint, easingType) * (firstScale ? 1 : bone.getScaleY()));
                    bone.setScaleZ((float) EasingType.lerpWithOverride(scaleZPoint, easingType) * (firstScale ? 1 : bone.getScaleZ()));
                    snapshot.updateScale(bone.getScaleX(), bone.getScaleY(), bone.getScaleZ());
                    snapshot.startScaleAnim();
                    bone.markScaleAsChanged();
                    firstScale = false;
                }
            }
        }
    }
}
