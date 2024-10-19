package com.github.teamfossilsarcheology.fossil.entity.animation;

import com.github.teamfossilsarcheology.fossil.entity.prehistoric.base.PrehistoricFlying;
import net.minecraft.world.entity.Mob;
import software.bernie.geckolib3.core.builder.Animation;

import java.util.NavigableMap;
import java.util.Random;
import java.util.TreeMap;

public class AnimationHolder {
    private final NavigableMap<Integer, Animation> animations = new TreeMap<>();
    private final NavigableMap<Integer, Animation> babyAnimations = new TreeMap<>();
    private final NavigableMap<Integer, Animation> waterAnimations = new TreeMap<>();
    private final NavigableMap<Integer, Animation> airAnimations = new TreeMap<>();

    public void add(Animation animation) {
        String name = animation.animationName.toLowerCase();
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
    }

    private static void addAnimation(NavigableMap<Integer, Animation> map, Animation animation, int weight) {
        if (!map.isEmpty()) {
            map.put(map.lastKey() + weight, animation);
        } else {
            map.put(weight, animation);
        }
    }

    public Animation getRandomAnimation(Mob entity) {
        if (!babyAnimations.isEmpty() && entity.isBaby()) {
            return getRandomAnimation(babyAnimations, entity.getRandom());
        } else if (!waterAnimations.isEmpty() && entity.isInWater()) {
            return getRandomAnimation(waterAnimations, entity.getRandom());
        } else if (entity instanceof PrehistoricFlying flying && !airAnimations.isEmpty() && flying.isFlying()) {
            return getRandomAnimation(airAnimations, entity.getRandom());
        }
        return getRandomAnimation(animations, entity.getRandom());
    }

    private static Animation getRandomAnimation(NavigableMap<Integer, Animation> map, Random random) {
        return map.ceilingEntry((int) (random.nextDouble() * map.lastKey())).getValue();
    }
}
