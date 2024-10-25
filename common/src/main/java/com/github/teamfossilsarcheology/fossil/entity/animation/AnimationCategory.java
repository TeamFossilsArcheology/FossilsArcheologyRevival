package com.github.teamfossilsarcheology.fossil.entity.animation;

import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public record AnimationCategory(String name, @Nullable AnimationCategory backup, boolean canBeReplaced, float chance, int transitionLength,
                                Predicate<String> predicate) {
    public static final List<AnimationCategory> CATEGORIES = new ArrayList<>();
    public static final AnimationCategory NONE = register("none", null, s -> s.contains("placeholder"));
    public static final AnimationCategory IDLE = register("idle", null);

    public static final AnimationCategory ATTACK = register("attack", IDLE);
    public static final AnimationCategory EAT = register("eat", IDLE);
    public static final AnimationCategory BEACHED = register("beached", IDLE, false, 0, 5);
    public static final AnimationCategory KNOCKOUT = register("knockout", IDLE, false, 0, 15);
    public static final AnimationCategory SLEEP = register("sleep", IDLE, true, 0.05f, 20);
    public static final AnimationCategory SIT = register("sit", SLEEP, true, 0.2f, 20);

    public static final AnimationCategory FLY = register("fly", IDLE, s -> s.contains("fly") && !s.contains("fast"));
    public static final AnimationCategory FLY_FAST = register("fly_fast", FLY, s -> s.contains("fly") && s.contains("fast"));
    public static final AnimationCategory WALK = register("walk", IDLE);
    public static final AnimationCategory SPRINT = register("sprint", WALK, s -> s.contains("sprint") || s.contains("run"));
    public static final AnimationCategory SWIM = register("swim", IDLE, s -> s.contains("swim") && !s.contains("fast"));
    public static final AnimationCategory SWIM_FAST = register("swim_fast", FLY, s -> s.contains("swim") && s.contains("fast"));

    public boolean canMapAnimation(String key) {
        return predicate.test(key) && !key.contains("!");
    }

    public static AnimationCategory register(String name, @Nullable AnimationCategory backup) {
        return register(name, backup, true, 1, 5, s -> s.contains(name));
    }

    public static AnimationCategory register(String name, @Nullable AnimationCategory backup, Predicate<String> test) {
        return register(name, backup, true, 1, 5, test);
    }

    public static AnimationCategory register(String name, @Nullable AnimationCategory backup, boolean canBeReplaced, float chance, int transitionLength) {
        return register(name, backup, canBeReplaced, chance, transitionLength, s -> s.contains(name));
    }

    public static AnimationCategory register(String name, @Nullable AnimationCategory backup, boolean canBeReplaced, float chance, int transitionLength, Predicate<String> test) {
        AnimationCategory animationCategory = new AnimationCategory(name, backup, canBeReplaced, chance, transitionLength, test);
        CATEGORIES.add(animationCategory);
        return animationCategory;
    }
}
