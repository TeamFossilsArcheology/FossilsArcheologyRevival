package com.github.teamfossilsarcheology.fossil.entity.animation;

import com.github.teamfossilsarcheology.fossil.entity.prehistoric.base.PrehistoricFlying;
import net.minecraft.world.entity.Mob;

import java.util.*;

public class AnimationHolder {
    private final NavigableMap<Double, AnimationInfo> animations = new TreeMap<>();
    private final NavigableMap<Double, AnimationInfo> babyAnimations = new TreeMap<>();
    private final NavigableMap<Double, AnimationInfo> waterAnimations = new TreeMap<>();
    private final NavigableMap<Double, AnimationInfo> airAnimations = new TreeMap<>();
    private final Set<String> lookup = new HashSet<>();

    public void add(AnimationInfo animation) {
        String name = animation.animation.animationName.toLowerCase();
        String[] parts = name.split("_");
        int weight = 1;
        if (parts.length >= 3) {
            try {
                Integer.parseInt(parts[parts.length - 2]);
                weight = Integer.parseInt(parts[parts.length - 1]);
            } catch (NumberFormatException e) {
                //No weight
            }
        }
        if (name.contains("baby")) {
            addAnimation(babyAnimations, animation, weight);
        } else if (name.contains("water")) {
            addAnimation(waterAnimations, animation, weight);
        } else if (name.contains("air")) {
            addAnimation(airAnimations, animation, weight);
        } else {
            addAnimation(animations, animation, weight);
        }
        lookup.add(animation.animation.animationName);
    }

    private static void addAnimation(NavigableMap<Double, AnimationInfo> map, AnimationInfo animation, double weight) {
        if (!map.isEmpty()) {
            map.put(map.lastKey() + weight, animation);
        } else {
            map.put(weight, animation);
        }
    }

    public boolean hasAnimation(String animationName) {
        return lookup.contains(animationName);
    }

    public AnimationInfo getRandomAnimation(Mob entity) {
        if (!babyAnimations.isEmpty() && entity.isBaby()) {
            return getRandomAnimation(babyAnimations, entity.getRandom());
        } else if (!waterAnimations.isEmpty() && entity.isInWater()) {
            return getRandomAnimation(waterAnimations, entity.getRandom());
        } else if (entity instanceof PrehistoricFlying flying && !airAnimations.isEmpty() && flying.isFlying()) {
            return getRandomAnimation(airAnimations, entity.getRandom());
        }
        return getRandomAnimation(animations, entity.getRandom());
    }

    private static AnimationInfo getRandomAnimation(NavigableMap<Double, AnimationInfo> map, Random random) {
        return map.higherEntry(random.nextDouble(map.lastKey())).getValue();
    }
}
