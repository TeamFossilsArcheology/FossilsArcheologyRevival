package com.github.teamfossilsarcheology.fossil.compat.geckolib;

import org.apache.commons.lang3.tuple.Pair;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.keyframe.AnimationPoint;
import software.bernie.geckolib3.core.keyframe.BoneAnimationQueue;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.molang.MolangParser;
import software.bernie.geckolib3.core.processor.IBone;
import software.bernie.geckolib3.core.snapshot.BoneSnapshot;
import software.bernie.geckolib3.core.snapshot.DirtyTracker;
import software.bernie.geckolib3.core.util.MathUtil;

import java.util.List;
import java.util.Map;

public class AnimationProcessorOverride {

    /**
     * Replaces parts of  {@link software.bernie.geckolib3.core.processor.AnimationProcessor#tickAnimation(IAnimatable, int, double, AnimationEvent, MolangParser, boolean) AnimationProcessor#tickAnimation}
     * with the goal of making animations additive across controllers
     */
    public static <T extends IAnimatable> void tickAdditiveAnimations(double seekTime, AnimationEvent<T> event, boolean reloadAnimations,
                                                                      List<IBone> modelRendererList, MolangParser parser, boolean crashWhenCantFindBone,
                                                                      AnimationData manager, Map<String, DirtyTracker> modelTracker,
                                                                      Map<String, Pair<IBone, BoneSnapshot>> boneSnapshots) {
        for (AnimationController<T> controller : manager.getAnimationControllers().values()) {
            if (reloadAnimations) {
                controller.markNeedsReload();
                controller.getBoneAnimationQueues().clear();
            }

            controller.isJustStarting = manager.isFirstTick;

            // Set current controller to animation test event
            event.setController(controller);

            // Process animations and add new values to the point queues
            controller.process(seekTime, event, modelRendererList, boneSnapshots, parser, crashWhenCantFindBone);
        }
        //TODO: Do some bug testing to make sure this doesnt cause issues

        //FA: Moved bone iteration to outer loop to have an easier time adding the animations
        for (IBone bone : modelRendererList) {
            DirtyTracker dirtyTracker = modelTracker.get(bone.getName());
            if (dirtyTracker == null) {
                continue;
            }
            //FA: These are only true for the first animation. When false the previous bone value will be added to the new one
            boolean firstRot = true;
            boolean firstPos = true;
            boolean firstScale = true;
            BoneSnapshot snapshot = boneSnapshots.get(bone.getName()).getRight();
            BoneSnapshot initialSnapshot = bone.getInitialSnapshot();
            for (AnimationController<T> controller : manager.getAnimationControllers().values()) {
                // Loop through every single bone and lerp each property
                BoneAnimationQueue boneAnimation = controller.getBoneAnimationQueues().get(bone.getName());

                AnimationPoint rXPoint = boneAnimation.rotationXQueue().poll();
                AnimationPoint rYPoint = boneAnimation.rotationYQueue().poll();
                AnimationPoint rZPoint = boneAnimation.rotationZQueue().poll();

                AnimationPoint pXPoint = boneAnimation.positionXQueue().poll();
                AnimationPoint pYPoint = boneAnimation.positionYQueue().poll();
                AnimationPoint pZPoint = boneAnimation.positionZQueue().poll();

                AnimationPoint sXPoint = boneAnimation.scaleXQueue().poll();
                AnimationPoint sYPoint = boneAnimation.scaleYQueue().poll();
                AnimationPoint sZPoint = boneAnimation.scaleZQueue().poll();

                // If there's any rotation points for this bone
                if (rXPoint != null && rYPoint != null && rZPoint != null) {
                    bone.setRotationX(MathUtil.lerpValues(rXPoint, controller.easingType, controller.customEasingMethod)
                            + (firstRot ? initialSnapshot.rotationValueX : bone.getRotationX()));
                    bone.setRotationY(MathUtil.lerpValues(rYPoint, controller.easingType, controller.customEasingMethod)
                            + (firstRot ? initialSnapshot.rotationValueY : bone.getRotationY()));
                    bone.setRotationZ(MathUtil.lerpValues(rZPoint, controller.easingType, controller.customEasingMethod)
                            + (firstRot ? initialSnapshot.rotationValueZ : bone.getRotationZ()));
                    snapshot.rotationValueX = bone.getRotationX();
                    snapshot.rotationValueY = bone.getRotationY();
                    snapshot.rotationValueZ = bone.getRotationZ();
                    snapshot.isCurrentlyRunningRotationAnimation = true;
                    dirtyTracker.hasRotationChanged = true;
                    firstRot = false;
                }

                // If there's any position points for this bone
                if (pXPoint != null && pYPoint != null && pZPoint != null) {
                    bone.setPositionX(
                            MathUtil.lerpValues(pXPoint, controller.easingType, controller.customEasingMethod) + (firstPos ? 0 : bone.getPositionX()));
                    bone.setPositionY(
                            MathUtil.lerpValues(pYPoint, controller.easingType, controller.customEasingMethod) + (firstPos ? 0 : bone.getPositionY()));
                    bone.setPositionZ(
                            MathUtil.lerpValues(pZPoint, controller.easingType, controller.customEasingMethod) + (firstPos ? 0 : bone.getPositionZ()));
                    snapshot.positionOffsetX = bone.getPositionX();
                    snapshot.positionOffsetY = bone.getPositionY();
                    snapshot.positionOffsetZ = bone.getPositionZ();
                    snapshot.isCurrentlyRunningPositionAnimation = true;
                    dirtyTracker.hasPositionChanged = true;
                    firstPos = false;
                }

                // If there's any scale points for this bone
                if (sXPoint != null && sYPoint != null && sZPoint != null) {
                    bone.setScaleX(MathUtil.lerpValues(sXPoint, controller.easingType, controller.customEasingMethod) + (firstScale ? 0 : bone.getScaleX()));
                    bone.setScaleY(MathUtil.lerpValues(sYPoint, controller.easingType, controller.customEasingMethod) + (firstScale ? 0 : bone.getScaleY()));
                    bone.setScaleZ(MathUtil.lerpValues(sZPoint, controller.easingType, controller.customEasingMethod) + (firstScale ? 0 : bone.getScaleZ()));
                    snapshot.scaleValueX = bone.getScaleX();
                    snapshot.scaleValueY = bone.getScaleY();
                    snapshot.scaleValueZ = bone.getScaleZ();
                    snapshot.isCurrentlyRunningScaleAnimation = true;
                    dirtyTracker.hasScaleChanged = true;
                    firstScale = false;
                }
            }
        }
    }
}
