package com.github.teamfossilsarcheology.fossil.compat.geckolib;

import com.eliotlash.mclib.math.Constant;
import com.eliotlash.mclib.math.IValue;
import software.bernie.geckolib.core.animatable.model.CoreGeoBone;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.AnimationProcessor;
import software.bernie.geckolib.core.keyframe.*;
import software.bernie.geckolib.core.state.BoneSnapshot;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class AnimationControllerOverride {
    private static final Keyframe<IValue> ZERO_KEYFRAME = new Keyframe<>(0.0, new Constant(0), new Constant(0));
    private static final AnimationPoint POINT = new AnimationPoint(ZERO_KEYFRAME, 0, 0, 0, 0);
    private static final AnimationPoint SCALE_POINT = new AnimationPoint(ZERO_KEYFRAME, 0, 0, 1, 1);

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
        for (String boneName : previousBones) {
            if (!bones.containsKey(boneName)) {
                continue;
            }
            CoreGeoBone bone = bones.get(boneName);

            BoneAnimationQueue boneAnimationQueue = boneAnimationQueues.get(boneName);

            BoneSnapshot boneSnapshot = boneSnapshots.get(boneName);
            boneAnimationQueue.addNextRotation(null, adjustedTick, transitionLength, boneSnapshot, bone.getInitialSnapshot(),
                    POINT, POINT, POINT);
            boneAnimationQueue.addNextPosition(null, adjustedTick, transitionLength, boneSnapshot,
                    POINT, POINT, POINT);
            boneAnimationQueue.addNextScale(null, adjustedTick, transitionLength, boneSnapshot,
                    SCALE_POINT, SCALE_POINT, SCALE_POINT);
        }
    }
}
