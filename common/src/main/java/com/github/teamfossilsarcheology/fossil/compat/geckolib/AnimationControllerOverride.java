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
            //Current snapshot
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
            //Model snapshot
            BoneSnapshot initialSnapshot = first.get().getInitialSnapshot();
            assert boneSnapshot != null : "Bone snapshot was null";
            BoneAnimationQueue boneAnimationQueue = boneAnimationQueues.get(boneName);

            boneAnimationQueue.positionXQueue().add(new AnimationPoint(null, adjustedTick, transitionLengthTicks,
                    boneSnapshot.positionOffsetX - initialSnapshot.positionOffsetX, 0));
            boneAnimationQueue.positionYQueue().add(new AnimationPoint(null, adjustedTick, transitionLengthTicks,
                    boneSnapshot.positionOffsetY - initialSnapshot.positionOffsetY, 0));
            boneAnimationQueue.positionZQueue().add(new AnimationPoint(null, adjustedTick, transitionLengthTicks,
                    boneSnapshot.positionOffsetZ - initialSnapshot.positionOffsetZ, 0));

            boneAnimationQueue.rotationXQueue().add(new AnimationPoint(null, adjustedTick, transitionLengthTicks,
                    boneSnapshot.rotationValueX - initialSnapshot.rotationValueX, 0));
            boneAnimationQueue.rotationYQueue().add(new AnimationPoint(null, adjustedTick, transitionLengthTicks,
                    boneSnapshot.rotationValueY - initialSnapshot.rotationValueY, 0));
            boneAnimationQueue.rotationZQueue().add(new AnimationPoint(null, adjustedTick, transitionLengthTicks,
                    boneSnapshot.rotationValueY - initialSnapshot.rotationValueY, 0));

            boneAnimationQueue.scaleXQueue().add(new AnimationPoint(null, adjustedTick, transitionLengthTicks,
                    boneSnapshot.scaleValueX - initialSnapshot.scaleValueX, 1));
            boneAnimationQueue.scaleXQueue().add(new AnimationPoint(null, adjustedTick, transitionLengthTicks,
                    boneSnapshot.scaleValueY - initialSnapshot.scaleValueY, 1));
            boneAnimationQueue.scaleXQueue().add(new AnimationPoint(null, adjustedTick, transitionLengthTicks,
                    boneSnapshot.scaleValueZ - initialSnapshot.scaleValueZ, 1));
        }
    }
}
