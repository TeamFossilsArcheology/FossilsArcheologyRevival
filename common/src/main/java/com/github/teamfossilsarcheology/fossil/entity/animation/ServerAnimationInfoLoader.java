package com.github.teamfossilsarcheology.fossil.entity.animation;

import com.github.teamfossilsarcheology.fossil.FossilMod;
import com.google.common.collect.ImmutableMap;
import com.google.common.reflect.TypeToken;
import com.google.gson.*;
import it.unimi.dsi.fastutil.objects.Object2ObjectMaps;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.util.GsonHelper;
import net.minecraft.util.profiling.ProfilerFiller;
import software.bernie.geckolib.core.animation.Animation;
import software.bernie.geckolib.core.keyframe.BoneAnimation;

import java.lang.reflect.Type;
import java.util.Map;

public class ServerAnimationInfoLoader extends AnimationInfoLoader<ServerAnimationInfo> {
    private static final Gson GSON = new GsonBuilder().registerTypeAdapter(BakedAnimationInfo.class, new Deserializer()).create();
    private static final Type TYPE = new TypeToken<BakedAnimationInfo<ServerAnimationInfo>>() {
    }.getType();
    private static final BakedAnimationInfo<ServerAnimationInfo> EMPTY = new BakedAnimationInfo<>(Object2ObjectMaps.emptyMap());
    public static final ServerAnimationInfoLoader INSTANCE = new ServerAnimationInfoLoader(GSON);
    private Map<ResourceLocation, BakedAnimationInfo<ServerAnimationInfo>> serverAnimationInfos = ImmutableMap.of();

    protected ServerAnimationInfoLoader(Gson gson) {
        super(gson, "animations");
    }

    @Override
    protected void apply(Map<ResourceLocation, JsonElement> jsons, ResourceManager resourceManager, ProfilerFiller profiler) {
        ImmutableMap.Builder<ResourceLocation, BakedAnimationInfo<ServerAnimationInfo>> builder = ImmutableMap.builder();
        for (Map.Entry<ResourceLocation, JsonElement> fileEntry : jsons.entrySet()) {
            if (!(fileEntry.getValue() instanceof JsonObject root) || !fileEntry.getKey().getNamespace().equals(FossilMod.MOD_ID)) {
                continue;
            }
            ResourceLocation path = FossilMod.location("animations/" + fileEntry.getKey().getPath() + ".json");
            builder.put(path, GSON.fromJson(GsonHelper.getAsJsonObject(root, "animations"), TYPE));
        }
        serverAnimationInfos = builder.build();
        FossilMod.LOGGER.info("Loaded {} server animations for {} entities", serverAnimationInfos.values().stream().map(info -> info.animations().size()).reduce(Integer::sum).orElse(0), serverAnimationInfos.size());
        AnimationCategoryLoader.SERVER.apply(serverAnimationInfos);
    }

    @Override
    public Map<ResourceLocation, BakedAnimationInfo<ServerAnimationInfo>> getAnimationInfos() {
        return serverAnimationInfos;
    }

    /**
     * Returns all animations for a given dino with additional server info
     *
     * @param animationFile the animation file "animations/xyz.animation.json"
     * @return a map containing all animations for a given dino
     */
    public BakedAnimationInfo<ServerAnimationInfo> getAnimations(ResourceLocation animationFile) {
        return serverAnimationInfos.getOrDefault(animationFile, EMPTY);
    }

    public static class Deserializer implements JsonDeserializer<BakedAnimationInfo<ServerAnimationInfo>> {
        @Override
        public BakedAnimationInfo<ServerAnimationInfo> deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            JsonObject root = json.getAsJsonObject();
            Map<String, ServerAnimationInfo> animations = new Object2ObjectOpenHashMap<>(root.size());
            for (Map.Entry<String, JsonElement> entry : root.entrySet()) {
                JsonObject animationObj = entry.getValue().getAsJsonObject();
                double animationLength = animationObj.has("animation_length") ? GsonHelper.getAsDouble(animationObj, "animation_length") * 20d : -1;
                double actionDelay = animationObj.has("action_delay") ? animationObj.get("action_delay").getAsDouble() * 20d : 0;
                boolean usesAttackBox = animationObj.has("uses_attack_box") && GsonHelper.getAsBoolean(animationObj, "uses_attack_box");
                Animation.LoopType loopType = Animation.LoopType.fromJson(animationObj.get("loop"));
                Animation animation = new Animation(entry.getKey(), animationLength, loopType, new BoneAnimation[]{}, new Animation.Keyframes(null, null, null));
                animations.put(entry.getKey(), new ServerAnimationInfo(animation, actionDelay, usesAttackBox));
            }
            return new BakedAnimationInfo<>(animations);
        }
    }
}
