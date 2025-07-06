package com.github.teamfossilsarcheology.fossil.compat.geckolib;

import software.bernie.geckolib.core.animatable.model.CoreGeoBone;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.AnimationProcessor;
import software.bernie.geckolib.core.keyframe.*;
import software.bernie.geckolib.core.state.BoneSnapshot;
import software.bernie.shadowed.eliotlash.mclib.math.Constant;
import software.bernie.shadowed.eliotlash.mclib.math.IValue;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class AnimationControllerOverride {
    private static final Keyframe<IValue> ZERO_KEYFRAME = new Keyframe<>(0.0, new Constant(0), new Constant(0));
    private static final AnimationPoint POINT = new AnimationPoint(ZERO_KEYFRAME, 0, 0, 0, 0);

    /**
     * Adds transition logic to {@link AnimationController#process}
     * with the goal of adding a transition animation for bones that have no animation points in the new animation.
     */
    public static void fixTransitions(Map<String, CoreGeoBone> bones, AnimationProcessor.QueuedAnimation currentAnimation, Map<String, BoneSnapshot> boneSnapshots,
                                      double adjustedTick, boolean firstTick, Map<String, BoneSnapshot> snapshots,
                                      double transitionLength, Map<String, BoneAnimationQueue> boneAnimationQueues) {
        Set<String> previousBones = new HashSet<>(boneSnapshots.keySet());
        Set<String> newBones = Arrays.stream(currentAnimation.animation().boneAnimations()).map(BoneAnimation::boneName).collect(Collectors.toSet());
        previousBones.removeAll(newBones);
        if (firstTick) {
            for (String previousBone : previousBones) {
                boneSnapshots.put(previousBone, BoneSnapshot.copy(snapshots.get(previousBone)));
            }
        }
        Map<String, BoneAnimation> animations = Arrays.stream(currentAnimation.animation().boneAnimations()).collect(Collectors.toMap(BoneAnimation::boneName, boneAnimation -> boneAnimation));
        for (String boneName : previousBones) {
            if (!bones.containsKey(boneName) || !animations.containsKey(boneName)) {
                continue;
            }
            CoreGeoBone bone = bones.get(boneName);
            BoneAnimation boneAnimation = animations.get(boneName);

            BoneAnimationQueue boneAnimationQueue = boneAnimationQueues.get(boneName);

            KeyframeStack<Keyframe<IValue>> rotationKeyFrames = boneAnimation.rotationKeyFrames();
            KeyframeStack<Keyframe<IValue>> positionKeyFrames = boneAnimation.positionKeyFrames();
            KeyframeStack<Keyframe<IValue>> scaleKeyFrames = boneAnimation.scaleKeyFrames();
            BoneSnapshot boneSnapshot = boneSnapshots.get(boneName);
            if (!rotationKeyFrames.xKeyframes().isEmpty()) {
                boneAnimationQueue.addNextRotation(null, adjustedTick, transitionLength, boneSnapshot, bone.getInitialSnapshot(),
                        POINT, POINT, POINT);
            }

            if (!positionKeyFrames.xKeyframes().isEmpty()) {
                boneAnimationQueue.addNextPosition(null, adjustedTick, transitionLength, boneSnapshot,
                        POINT, POINT, POINT);
            }

            if (!scaleKeyFrames.xKeyframes().isEmpty()) {
                boneAnimationQueue.addNextScale(null, adjustedTick, transitionLength, boneSnapshot,
                        POINT, POINT, POINT);
            }
        }
    }
}
