package com.github.teamfossilsarcheology.fossil.entity.prehistoric.base;

import com.github.teamfossilsarcheology.fossil.entity.animation.*;
import net.minecraft.world.entity.Mob;
import org.jetbrains.annotations.NotNull;
import software.bernie.geckolib.animatable.GeoEntity;

import java.util.Map;

public interface PrehistoricAnimatable<T extends Mob & PrehistoricAnimatable<T>> extends GeoEntity {
    default AnimationInfo getRandomAnimation(AnimationCategory category) {
        var animations = getAnimations().get(category);
        return animations.getRandomAnimation((T) this);
    }

    Map<AnimationCategory, AnimationHolder> getAnimations();

    Map<String, ? extends AnimationInfo> getAllAnimations();

    AnimationInfo getAnimation(AnimationCategory category);

    default AnimationInfo getAnimation(String name) {
        return getAllAnimations().get(name);
    }

    Map<String, ServerAnimationInfo> getServerAnimationInfos();

    AnimationLogic<T> getAnimationLogic();

    default @NotNull AnimationInfo nextEatingAnimation() {
        return getAnimation(AnimationCategory.EAT);
    }

    default @NotNull AnimationInfo nextIdleAnimation() {
        return getAnimation(AnimationCategory.IDLE);
    }

    default @NotNull AnimationInfo nextSittingAnimation() {
        return getAnimation(AnimationCategory.SIT);
    }

    default @NotNull AnimationInfo nextSleepingAnimation() {
        return getAnimation(AnimationCategory.SLEEP);
    }

    default @NotNull AnimationInfo nextSprintingAnimation() {
        return getAnimation(AnimationCategory.SPRINT);
    }

    default @NotNull AnimationInfo nextSwimmingAnimation() {
        return getAnimation(AnimationCategory.SWIM);
    }

    default @NotNull AnimationInfo nextSwimFastAnimation() {
        return getAnimation(AnimationCategory.SWIM_FAST);
    }

    default @NotNull AnimationInfo nextWalkingAnimation() {
        return getAnimation(AnimationCategory.WALK);
    }
}
