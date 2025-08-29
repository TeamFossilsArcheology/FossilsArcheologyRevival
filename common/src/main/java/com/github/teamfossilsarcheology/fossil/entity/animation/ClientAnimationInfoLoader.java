package com.github.teamfossilsarcheology.fossil.entity.animation;

import com.github.teamfossilsarcheology.fossil.FossilMod;
import com.google.common.collect.ImmutableMap;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import it.unimi.dsi.fastutil.objects.Object2ObjectMaps;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.util.GsonHelper;
import net.minecraft.util.profiling.ProfilerFiller;
import software.bernie.geckolib.cache.GeckoLibCache;
import software.bernie.geckolib.core.animation.Animation;
import software.bernie.geckolib.loading.object.BakedAnimations;

import java.util.HashMap;
import java.util.Map;

/**
 * Loads dino animation server information from data/animations files
 */
public class ClientAnimationInfoLoader extends AnimationInfoLoader<ClientAnimationInfo> {
    private static final BakedAnimationInfo<ClientAnimationInfo> EMPTY = new BakedAnimationInfo<>(Object2ObjectMaps.emptyMap());
    public static final ClientAnimationInfoLoader INSTANCE = new ClientAnimationInfoLoader(new GsonBuilder().create());
    private Map<ResourceLocation, BakedAnimationInfo<ClientAnimationInfo>> clientAnimationInfos = ImmutableMap.of();

    public ClientAnimationInfoLoader(Gson gson) {
        super(gson, "animations/entity/data");
    }

    @Override
    protected void apply(Map<ResourceLocation, JsonElement> jsons, ResourceManager resourceManager, ProfilerFiller profiler) {
        Map<ResourceLocation, Map<String, Double>> data = new Object2ObjectOpenHashMap<>();
        for (Map.Entry<ResourceLocation, JsonElement> fileEntry : jsons.entrySet()) {
            if (!(fileEntry.getValue() instanceof JsonObject root) || !fileEntry.getKey().getNamespace().equals(FossilMod.MOD_ID)) {
                continue;
            }
            ImmutableMap.Builder<String, Double> innerBuilder = ImmutableMap.builder();
            for (Map.Entry<String, JsonElement> entry : GsonHelper.getAsJsonObject(root, "animations").getAsJsonObject().entrySet()) {
                JsonObject animationObj = entry.getValue().getAsJsonObject();
                double blockSpeed = animationObj.has("blocks_per_second") ? GsonHelper.getAsDouble(animationObj, "blocks_per_second") : 0;
                innerBuilder.put(entry.getKey(), blockSpeed);
            }
            ResourceLocation path = FossilMod.location("animations/entity/" + fileEntry.getKey().getPath() + ".animation.json");
            data.put(path, innerBuilder.build());
        }

        //Client side. Copy from geckolib
        ImmutableMap.Builder<ResourceLocation, BakedAnimationInfo<ClientAnimationInfo>> builder = ImmutableMap.builder();
        for (Map.Entry<ResourceLocation, BakedAnimations> fileEntry : GeckoLibCache.getBakedAnimations().entrySet()) {
            if (!fileEntry.getKey().getNamespace().equals(FossilMod.MOD_ID)) {
                continue;
            }
            Map<String, ClientAnimationInfo> bakedMap = new Object2ObjectOpenHashMap<>();
            for (Map.Entry<String, Animation> animationEntry : fileEntry.getValue().animations().entrySet()) {
                double blocksPerSecond = data.getOrDefault(fileEntry.getKey(), new HashMap<>()).getOrDefault(animationEntry.getKey(), 0.0);
                bakedMap.put(animationEntry.getKey(), new ClientAnimationInfo(animationEntry.getValue(), blocksPerSecond));
            }
            builder.put(fileEntry.getKey(), new BakedAnimationInfo<>(bakedMap));
        }
        clientAnimationInfos = builder.build();
        FossilMod.LOGGER.info("Loaded {} client animations for {} entities", clientAnimationInfos.values().stream().map(info -> info.animations().size()).reduce(Integer::sum).orElse(0), clientAnimationInfos.size());
        AnimationCategoryLoader.CLIENT.apply(clientAnimationInfos);
    }

    @Override
    public Map<ResourceLocation, BakedAnimationInfo<ClientAnimationInfo>> getAnimationInfos() {
        return clientAnimationInfos;
    }

    /**
     * Returns all animations for a given dino in the same format as the client receives them
     *
     * @param animationFile the animation file "animations/xyz.animation.json"
     * @return a map containing all animations for a given dino
     */
    public BakedAnimationInfo<ClientAnimationInfo> getAnimations(ResourceLocation animationFile) {
        return clientAnimationInfos.getOrDefault(animationFile, EMPTY);
    }
}
