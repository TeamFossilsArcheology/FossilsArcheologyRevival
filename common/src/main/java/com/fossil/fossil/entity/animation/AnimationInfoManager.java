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
import software.bernie.geckolib3.core.builder.Animation;
import software.bernie.geckolib3.util.AnimationUtils;
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
    private ImmutableMap<String, ImmutableMap<String, Animation>> clientAnimationInfos = ImmutableMap.of();

    public AnimationInfoManager(Gson gson) {
        super(gson, "animations");
    }

    @Override
    protected void apply(Map<ResourceLocation, JsonElement> jsons, ResourceManager resourceManager, ProfilerFiller profiler) {
        ImmutableMap.Builder<String, ImmutableMap<String, ServerAnimationInfo>> builder = ImmutableMap.builder();
        ImmutableMap.Builder<String, ImmutableMap<String, Animation>> clientBuilder = ImmutableMap.builder();
        for (Map.Entry<ResourceLocation, JsonElement> fileEntry : jsons.entrySet()) {
            if (!(fileEntry.getValue() instanceof JsonObject)) {
                continue;
            }
            try {
                ImmutableMap.Builder<String, ServerAnimationInfo> innerBuilder = ImmutableMap.builder();
                ImmutableMap.Builder<String, Animation> clientInnerBuilder = ImmutableMap.builder();
                for (Map.Entry<String, JsonElement> animationEntry : JsonAnimationUtils.getAnimations((JsonObject) fileEntry.getValue())) {
                    JsonObject animationJsonObject = animationEntry.getValue().getAsJsonObject();

                    JsonElement delayElement = animationJsonObject.get("action_delay");
                    double actionDelay = delayElement == null ? 0 : AnimationUtils.convertSecondsToTicks(delayElement.getAsDouble());
                    JsonElement lengthElement = animationJsonObject.get("animation_length");
                    double animationLength = lengthElement == null ? 0 : AnimationUtils.convertSecondsToTicks(lengthElement.getAsDouble());
                    innerBuilder.put(animationEntry.getKey(), new ServerAnimationInfo(animationEntry.getKey(), actionDelay, animationLength));
                    clientInnerBuilder.put(animationEntry.getKey(), new ServerAnimationInfo(animationEntry.getKey(), actionDelay, animationLength));
                }
                builder.put("animations/" + fileEntry.getKey().getPath() + ".json", innerBuilder.build());
                clientBuilder.put("animations/" + fileEntry.getKey().getPath() + ".json", clientInnerBuilder.build());
            } catch (Exception e) {
                LOGGER.error("Could not load animations {} due to: {}", fileEntry, e);
            }
        }
        animationInfos = builder.build();
        clientAnimationInfos = clientBuilder.build();
    }

    /**
     * Returns all animations for a given dino with additional server info
     *
     * @param animationFile the animation file "animations/xyz.animation.json"
     * @return a map containing all animations for a given dino
     */
    public Map<String, ServerAnimationInfo> getServerAnimations(String animationFile) {
        return animationInfos.getOrDefault(animationFile, ImmutableMap.of());
    }

    /**
     * Returns all animations for a given dino in the same format as the client receives them
     *
     * @param animationFile the animation file "animations/xyz.animation.json"
     * @return a map containing all animations for a given dino
     */
    public Map<String, Animation> getClientAnimations(String animationFile) {
        return clientAnimationInfos.getOrDefault(animationFile, ImmutableMap.of());
    }

    /**
     * A specific animation instance available to the server
     */
    public static class ServerAnimationInfo extends Animation {
        /**
         * x ticks after the start of the animation
         */
        public final double actionDelay;

        public ServerAnimationInfo(String animationName, double actionDelay, double animationLength) {
            this.animationName = animationName;
            this.animationLength = animationLength;
            this.actionDelay = actionDelay;
        }
    }
}
