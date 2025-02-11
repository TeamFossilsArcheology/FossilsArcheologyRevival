package com.github.teamfossilsarcheology.fossil.entity.animation;

import com.github.teamfossilsarcheology.fossil.FossilMod;
import com.google.common.collect.ImmutableMap;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.mojang.logging.LogUtils;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimpleJsonResourceReloadListener;
import net.minecraft.util.profiling.ProfilerFiller;
import org.slf4j.Logger;

import java.util.Map;

public class AnimationCategoryLoader extends SimpleJsonResourceReloadListener {
    private static final Logger LOGGER = LogUtils.getLogger();
    private static final Gson GSON = new GsonBuilder().disableHtmlEscaping().create();
    private Map<ResourceLocation, Map<AnimationCategory, AnimationHolder>> animations = new Object2ObjectOpenHashMap<>();
    public static final AnimationCategoryLoader INSTANCE = new AnimationCategoryLoader(GSON);
    private static final AnimationHolder EMPTY = new AnimationHolder();

    private AnimationCategoryLoader(Gson gson) {
        super(gson, "animations");
    }

    @Override
    protected void apply(Map<ResourceLocation, JsonElement> jsons, ResourceManager resourceManager, ProfilerFiller profiler) {
        AnimationInfoLoader<? extends AnimationInfo> animationInfoLoader;
        if (!ServerAnimationInfoLoader.INSTANCE.getAnimationInfos().isEmpty()) {
            animationInfoLoader = ServerAnimationInfoLoader.INSTANCE;
        } else {
            animationInfoLoader = ClientAnimationInfoLoader.INSTANCE;
        }
        Map<ResourceLocation, ? extends BakedAnimationInfo<? extends AnimationInfo>> allAnimations = animationInfoLoader.getAnimationInfos();
        ImmutableMap.Builder<ResourceLocation, Map<AnimationCategory, AnimationHolder>> builder = ImmutableMap.builder();
        for (Map.Entry<ResourceLocation, JsonElement> fileEntry : jsons.entrySet()) {
            if (!(fileEntry.getValue() instanceof JsonObject) || !fileEntry.getKey().getNamespace().equals(FossilMod.MOD_ID)) {
                continue;
            }
            Map<AnimationCategory, AnimationHolder> map = new Object2ObjectOpenHashMap<>();
            ResourceLocation path = FossilMod.location("animations/" + fileEntry.getKey().getPath() + ".json");
            if (!allAnimations.containsKey(path)) {
                continue;
            }
            AnimationCategory backup = null;
            for (AnimationCategory category : AnimationCategory.CATEGORIES) {
                for (Map.Entry<String, ? extends AnimationInfo> entry : allAnimations.get(path).animations().entrySet()) {
                    if (category.canMapAnimation(entry.getKey())) {
                        map.computeIfAbsent(category, cat -> new AnimationHolder()).add(entry.getValue());
                        backup = category;
                    }
                }
            }
            if (backup == null) {
                LOGGER.error("Mob has no animations that match any of our categories in {}", path);
                throw new RuntimeException("Mob has no animations that match any of our categories in " + path);
            }

            //Add backup animations to prevent crashes
            for (AnimationCategory category : AnimationCategory.CATEGORIES) {
                if (!map.containsKey(category)) {
                    //LOGGER.info("Animation missing: {} in {}. Replaced with {} or {}", category.name(), path, category.backup(), backup);
                    map.put(category, map.containsKey(category.backup()) ? map.get(category.backup()) : map.get(backup));
                }
            }
            builder.put(path, map);
        }
        animations = builder.build();
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
