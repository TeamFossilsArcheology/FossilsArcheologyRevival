package com.github.teamfossilsarcheology.fossil.compat.geckolib;

import org.apache.commons.lang3.tuple.Pair;
import software.bernie.geckolib3.core.ConstantValue;
import software.bernie.geckolib3.core.builder.Animation;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.keyframe.AnimationPoint;
import software.bernie.geckolib3.core.keyframe.BoneAnimationQueue;
import software.bernie.geckolib3.core.keyframe.KeyFrame;
import software.bernie.geckolib3.core.keyframe.KeyFrameLocation;
import software.bernie.geckolib3.core.molang.MolangParser;
import software.bernie.geckolib3.core.processor.IBone;
import software.bernie.geckolib3.core.snapshot.BoneSnapshot;
import software.bernie.shadowed.eliotlash.mclib.math.IValue;

import java.util.*;
import java.util.stream.Collectors;

public class AnimationControllerOverride {
    private static final KeyFrame<IValue> ZERO_KEYFRAME = new KeyFrame<>(0.0, new ConstantValue(0), new ConstantValue(0));

    /**
     * Adds transition logic to {@link software.bernie.geckolib3.core.controller.AnimationController#process(double, AnimationEvent, List, Map, MolangParser, boolean) AnimationController#process}
     * with the goal of adding a transition animation for bones that have no animation points in the new animation.
     */
    public static void fixTransitions(List<IBone> modelRendererList, Animation currentAnimation, Map<String, BoneSnapshot> boneSnapshots,
                                      double adjustedTick, boolean firstTick, Map<String, Pair<IBone, BoneSnapshot>> boneSnapshotCollection,
                                      double transitionLengthTicks, Map<String, BoneAnimationQueue> boneAnimationQueues) {
        Set<String> previousBones = new HashSet<>(boneSnapshots.keySet());
        Set<String> newBones = currentAnimation.boneAnimations.stream().map(boneAnimation -> boneAnimation.boneName).collect(Collectors.toSet());
        previousBones.removeAll(newBones);
        if (firstTick) {
            for (String previousBone : previousBones) {
                boneSnapshots.put(previousBone, new BoneSnapshot(boneSnapshotCollection.get(previousBone).getRight()));
            }
        }
        for (String boneName : previousBones) {
            BoneSnapshot boneSnapshot = boneSnapshots.get(boneName);
            Optional<IBone> first = Optional.empty();
            for (IBone bone : modelRendererList) {
                if (bone.getName().equals(boneName)) {
                    first = Optional.of(bone);
                    break;
                }
            }
            if (first.isEmpty()) {
                continue;
            }
            BoneSnapshot initialSnapshot = first.get().getInitialSnapshot();
            assert boneSnapshot != null : "Bone snapshot was null";
            BoneSnapshot boneAnimation = boneSnapshots.get(boneName);
            BoneAnimationQueue boneAnimationQueue = boneAnimationQueues.get(boneName);

            KeyFrameLocation<KeyFrame<IValue>> location = new KeyFrameLocation<>(ZERO_KEYFRAME, 0);
            AnimationPoint point = new AnimationPoint(location.currentFrame, location.currentTick, 0, 0, 0);
            if (boneAnimation.positionOffsetX != 0 || boneAnimation.positionOffsetY != 0 || boneAnimation.positionOffsetZ != 0) {
                boneAnimationQueue.positionXQueue().add(new AnimationPoint(null, adjustedTick, transitionLengthTicks,
                        boneSnapshot.positionOffsetX - initialSnapshot.positionOffsetX, point.animationStartValue));
                boneAnimationQueue.positionYQueue().add(new AnimationPoint(null, adjustedTick, transitionLengthTicks,
                        boneSnapshot.positionOffsetY - initialSnapshot.positionOffsetY, point.animationStartValue));
                boneAnimationQueue.positionZQueue().add(new AnimationPoint(null, adjustedTick, transitionLengthTicks,
                        boneSnapshot.positionOffsetZ - initialSnapshot.positionOffsetZ, point.animationStartValue));
            }
            if (boneAnimation.rotationValueX != 0 || boneAnimation.rotationValueY != 0 || boneAnimation.rotationValueZ != 0) {
                boneAnimationQueue.rotationXQueue().add(new AnimationPoint(null, adjustedTick, transitionLengthTicks,
                        boneSnapshot.rotationValueX - initialSnapshot.rotationValueX, point.animationStartValue));
                boneAnimationQueue.rotationYQueue().add(new AnimationPoint(null, adjustedTick, transitionLengthTicks,
                        boneSnapshot.rotationValueY - initialSnapshot.rotationValueY, point.animationStartValue));
                boneAnimationQueue.rotationZQueue().add(new AnimationPoint(null, adjustedTick, transitionLengthTicks,
                        boneSnapshot.rotationValueY - initialSnapshot.rotationValueY, point.animationStartValue));
            }
            if (boneAnimation.scaleValueX != 1 || boneAnimation.scaleValueY != 1 || boneAnimation.scaleValueZ != 1) {
                boneAnimationQueue.scaleXQueue().add(new AnimationPoint(null, adjustedTick, transitionLengthTicks,
                        boneSnapshot.scaleValueX - initialSnapshot.scaleValueX, point.animationStartValue));
                boneAnimationQueue.scaleXQueue().add(new AnimationPoint(null, adjustedTick, transitionLengthTicks,
                        boneSnapshot.scaleValueY - initialSnapshot.scaleValueY, point.animationStartValue));
                boneAnimationQueue.scaleXQueue().add(new AnimationPoint(null, adjustedTick, transitionLengthTicks,
                        boneSnapshot.scaleValueZ - initialSnapshot.scaleValueZ, point.animationStartValue));
            }
        }
    }
}
