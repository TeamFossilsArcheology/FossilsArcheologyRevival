package com.github.teamfossilsarcheology.fossil.entity.animation;

import com.github.teamfossilsarcheology.fossil.FossilMod;
import com.google.common.collect.ImmutableMap;
import com.mojang.logging.LogUtils;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import net.minecraft.resources.ResourceLocation;
import org.slf4j.Logger;

import java.util.Map;

public class AnimationCategoryLoader {
    private static final Logger LOGGER = LogUtils.getLogger();
    private static final AnimationHolder EMPTY = new AnimationHolder();
    private ImmutableMap<ResourceLocation, Map<AnimationCategory, AnimationHolder>> animations = ImmutableMap.of();
    public static final AnimationCategoryLoader CLIENT = new AnimationCategoryLoader();
    public static final AnimationCategoryLoader SERVER = new AnimationCategoryLoader();

    protected void apply(Map<ResourceLocation, ? extends BakedAnimationInfo<? extends AnimationInfo>> allAnimations) {
        ImmutableMap.Builder<ResourceLocation, Map<AnimationCategory, AnimationHolder>> builder = ImmutableMap.builder();
        for (Map.Entry<ResourceLocation, ? extends BakedAnimationInfo<? extends AnimationInfo>> fileEntry : allAnimations.entrySet()) {
            Map<AnimationCategory, AnimationHolder> map = new Object2ObjectOpenHashMap<>();
            AnimationCategory backup = null;
            for (AnimationCategory category : AnimationCategory.CATEGORIES) {
                for (Map.Entry<String, ? extends AnimationInfo> entry : fileEntry.getValue().animations().entrySet()) {
                    if (category.canMapAnimation(entry.getKey())) {
                        map.computeIfAbsent(category, cat -> new AnimationHolder()).add(entry.getValue());
                        backup = category;
                    }
                }
            }
            if (backup == null) {
                LOGGER.error("Mob has no animations that match any of our categories in {}", fileEntry.getKey());
                throw new RuntimeException("Mob has no animations that match any of our categories in " + fileEntry.getKey());
            }

            //Add backup animations to prevent crashes
            for (AnimationCategory category : AnimationCategory.CATEGORIES) {
                if (!map.containsKey(category)) {
                    //LOGGER.info("Animation missing: {} in {}. Replaced with {} or {}", category.name(), path, category.backup(), backup);
                    map.put(category, map.containsKey(category.backup()) ? map.get(category.backup()) : map.get(backup));
                }
            }
            builder.put(fileEntry.getKey(), map);
        }
        animations = builder.build();
        FossilMod.LOGGER.info("Loaded {} animation category entries for {} entities", animations.values().stream().map(Map::size).reduce(Integer::sum).orElse(0), animations.size());
    }

    public Map<AnimationCategory, AnimationHolder> getAnimations(ResourceLocation path) {
        return animations.get(path);
    }

    public AnimationHolder getAnimations(ResourceLocation path, AnimationCategory animationCategory) {
        if (animations.containsKey(path)) {
            return animations.get(path).get(animationCategory);
        }
        return EMPTY;
    }
}
