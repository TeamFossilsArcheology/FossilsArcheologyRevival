package com.github.teamfossilsarcheology.fossil.entity.prehistoric.base;

import com.github.teamfossilsarcheology.fossil.entity.animation.AnimationCategory;
import com.github.teamfossilsarcheology.fossil.entity.animation.AnimationHolder;
import com.github.teamfossilsarcheology.fossil.entity.animation.AnimationInfoLoader;
import com.github.teamfossilsarcheology.fossil.entity.animation.AnimationLogic;
import net.minecraft.world.entity.Mob;
import org.jetbrains.annotations.NotNull;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.builder.Animation;

import java.util.Map;

public interface PrehistoricAnimatable<T extends Mob & PrehistoricAnimatable<T>> extends IAnimatable {
    default Animation getRandomAnimation(AnimationCategory category, T mob) {
        var animations = getAnimations().get(category);
        return animations.getRandomAnimation(mob);
    }

    Map<AnimationCategory, AnimationHolder> getAnimations();

    Map<String, Animation> getAllAnimations();

    Animation getAnimation(AnimationCategory category);

    Map<String, AnimationInfoLoader.ServerAnimationInfo> getServerAnimationInfos();

    AnimationLogic<T> getAnimationLogic();

    default @NotNull Animation nextEatingAnimation() {
        return getAnimation(AnimationCategory.EAT);
    }

    default @NotNull Animation nextIdleAnimation() {
        return getAnimation(AnimationCategory.IDLE);
    }

    default @NotNull Animation nextSittingAnimation() {
        return getAnimation(AnimationCategory.SIT);
    }

    default @NotNull Animation nextSleepingAnimation() {
        return getAnimation(AnimationCategory.SLEEP);
    }

    default @NotNull Animation nextSprintingAnimation() {
        return getAnimation(AnimationCategory.SPRINT);
    }

    default @NotNull Animation nextSwimmingAnimation() {
        return getAnimation(AnimationCategory.SWIM);
    }

    default @NotNull Animation nextSwimFastAnimation() {
        return getAnimation(AnimationCategory.SWIM_FAST);
    }

    default @NotNull Animation nextWalkingAnimation() {
        return getAnimation(AnimationCategory.WALK);
    }
}
