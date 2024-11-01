package com.github.teamfossilsarcheology.fossil.entity.animation;

import com.github.teamfossilsarcheology.fossil.FossilMod;
import com.google.common.reflect.TypeToken;
import com.google.gson.*;
import it.unimi.dsi.fastutil.objects.Object2ObjectMaps;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.util.GsonHelper;
import net.minecraft.util.profiling.ProfilerFiller;
import software.bernie.geckolib3.core.builder.Animation;
import software.bernie.geckolib3.core.builder.ILoopType;

import java.lang.reflect.Type;
import java.util.Map;

public class ServerAnimationInfoLoader extends AnimationInfoLoader<ServerAnimationInfo> {
    private static final Gson GSON = new GsonBuilder().registerTypeAdapter(BakedAnimationInfo.class, new Deserializer()).create();
    private static final Type TYPE = new TypeToken<BakedAnimationInfo<ServerAnimationInfo>>() {
    }.getType();
    private static final BakedAnimationInfo<ServerAnimationInfo> EMPTY = new BakedAnimationInfo<>(Object2ObjectMaps.emptyMap());
    public static final ServerAnimationInfoLoader INSTANCE = new ServerAnimationInfoLoader(GSON);
    private final Map<ResourceLocation, BakedAnimationInfo<ServerAnimationInfo>> serverAnimationInfos = new Object2ObjectOpenHashMap<>();

    protected ServerAnimationInfoLoader(Gson gson) {
        super(gson);
    }

    @Override
    protected void apply(Map<ResourceLocation, JsonElement> jsons, ResourceManager resourceManager, ProfilerFiller profiler) {
        serverAnimationInfos.clear();
        for (Map.Entry<ResourceLocation, JsonElement> fileEntry : jsons.entrySet()) {
            if (!(fileEntry.getValue() instanceof JsonObject root) || !fileEntry.getKey().getNamespace().equals(FossilMod.MOD_ID)) {
                continue;
            }
            ResourceLocation path = FossilMod.location("animations/" + fileEntry.getKey().getPath() + ".json");
            serverAnimationInfos.put(path, GSON.fromJson(GsonHelper.getAsJsonObject(root, "animations"), TYPE));
        }
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
                double blockSpeed = animationObj.has("blocks_per_second") ? GsonHelper.getAsDouble(animationObj, "blocks_per_second") * 20d : 0;
                boolean usesAttackBox = animationObj.has("uses_attack_box") && GsonHelper.getAsBoolean(animationObj, "uses_attack_box");
                ILoopType loopType = ILoopType.fromJson(animationObj.get("loop"));
                Animation animation = new Animation();
                animation.animationName = entry.getKey();
                animation.animationLength = animationLength;
                animation.loop = loopType;
                animations.put(entry.getKey(), new ServerAnimationInfo(
                        animation,
                        actionDelay, blockSpeed, usesAttackBox));
            }
            return new BakedAnimationInfo<>(animations);
        }
    }
}
