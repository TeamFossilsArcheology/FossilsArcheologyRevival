package com.fossil.fossil.entity.animation;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.mojang.logging.LogUtils;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimpleJsonResourceReloadListener;
import net.minecraft.util.profiling.ProfilerFiller;
import org.slf4j.Logger;
import software.bernie.geckolib3.core.builder.ILoopType;
import software.bernie.geckolib3.util.AnimationUtils;
import software.bernie.geckolib3.util.json.JsonAnimationUtils;

import java.util.List;
import java.util.Map;

/**
 * Loads dino animation server information from data/animations files
 */
public class AnimationManager extends SimpleJsonResourceReloadListener {
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();
    private static final Logger LOGGER = LogUtils.getLogger();
    public static final AnimationManager ANIMATIONS = new AnimationManager();
    private ImmutableMap<String, ImmutableList<Animation>> animations = ImmutableMap.of();

    public AnimationManager() {
        super(GSON, "animations");
    }

    @Override
    protected void apply(Map<ResourceLocation, JsonElement> jsons, ResourceManager resourceManager, ProfilerFiller profiler) {
        ImmutableMap.Builder<String, ImmutableList<Animation>> builder = ImmutableMap.builder();
        for (Map.Entry<ResourceLocation, JsonElement> fileEntry : jsons.entrySet()) {
            if (!(fileEntry.getValue() instanceof JsonObject)) {
                continue;
            }
            try {
                ImmutableList.Builder<Animation> innerBuilder = ImmutableList.builder();
                for (Map.Entry<String, JsonElement> animationEntry : JsonAnimationUtils.getAnimations((JsonObject) fileEntry.getValue())) {
                    JsonObject animationJsonObject = animationEntry.getValue().getAsJsonObject();

                    JsonElement lengthElement = animationJsonObject.get("animation_length");
                    double animationLength = lengthElement == null ? -1 : AnimationUtils.convertSecondsToTicks(lengthElement.getAsDouble());

                    JsonElement loopElement = animationJsonObject.get("loop");
                    ILoopType loop = loopElement == null ? ILoopType.EDefaultLoopTypes.PLAY_ONCE :
                            "true".equals(loopElement.getAsString()) ? ILoopType.EDefaultLoopTypes.LOOP : ILoopType.EDefaultLoopTypes.valueOf(loopElement.getAsString().toUpperCase());

                    innerBuilder.add(new Animation(animationEntry.getKey(), animationLength, loop));
                }
                builder.put(fileEntry.getKey().getPath() + ".json", innerBuilder.build());
            } catch (Exception e) {
                LOGGER.error("Could not load animations {} due to: {}", fileEntry, e);
            }
        }
        animations = builder.build();
    }

    public List<Animation> getAnimation(String animationFile) {
        return animations.get(animationFile);
    }

    public record Animation(String animationId, double animationLength, ILoopType loop) {
    }
}
