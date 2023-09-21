package com.fossil.fossil.entity.animation;

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
import software.bernie.geckolib3.util.json.JsonAnimationUtils;

import java.util.Map;

/**
 * Loads dino animation server information from data/animations files
 */
public class AnimationInfoManager extends SimpleJsonResourceReloadListener {
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();
    public static final AnimationInfoManager ANIMATIONS = new AnimationInfoManager(GSON);
    private static final Logger LOGGER = LogUtils.getLogger();
    private ImmutableMap<String, ImmutableMap<String, ServerAnimationInfo>> animationInfos = ImmutableMap.of();

    public AnimationInfoManager(Gson gson) {
        super(gson, "animations");
    }

    @Override
    protected void apply(Map<ResourceLocation, JsonElement> jsons, ResourceManager resourceManager, ProfilerFiller profiler) {
        ImmutableMap.Builder<String, ImmutableMap<String, ServerAnimationInfo>> builder = ImmutableMap.builder();
        for (Map.Entry<ResourceLocation, JsonElement> fileEntry : jsons.entrySet()) {
            if (!(fileEntry.getValue() instanceof JsonObject)) {
                continue;
            }
            try {
                ImmutableMap.Builder<String, ServerAnimationInfo> innerBuilder = ImmutableMap.builder();
                for (Map.Entry<String, JsonElement> animationEntry : JsonAnimationUtils.getAnimations((JsonObject) fileEntry.getValue())) {
                    JsonObject animationJsonObject = animationEntry.getValue().getAsJsonObject();

                    JsonElement delayElement = animationJsonObject.get("action_delay");
                    int actionDelay = delayElement == null ? 0 : delayElement.getAsInt();
                    innerBuilder.put(animationEntry.getKey(), new ServerAnimationInfo(animationEntry.getKey(), actionDelay));
                }
                builder.put("animations/" + fileEntry.getKey().getPath() + ".json", innerBuilder.build());
            } catch (Exception e) {
                LOGGER.error("Could not load animations {} due to: {}", fileEntry, e);
            }
        }
        animationInfos = builder.build();
    }

    public Map<String, ServerAnimationInfo> getAnimation(String animationFile) {
        return animationInfos.getOrDefault(animationFile, ImmutableMap.of());
    }

    public record ServerAnimationInfo(String animationName, int actionDelay) {
    }
}
