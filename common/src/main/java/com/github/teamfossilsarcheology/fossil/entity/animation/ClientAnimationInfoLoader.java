package com.github.teamfossilsarcheology.fossil.entity.animation;

import com.github.teamfossilsarcheology.fossil.FossilMod;
import com.google.common.collect.ImmutableMap;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import it.unimi.dsi.fastutil.objects.Object2ObjectMaps;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.util.profiling.ProfilerFiller;
import software.bernie.geckolib3.core.builder.Animation;
import software.bernie.geckolib3.file.AnimationFile;
import software.bernie.geckolib3.resource.GeckoLibCache;

import java.util.Map;

/**
 * Loads dino animation server information from data/animations files
 */
public class ClientAnimationInfoLoader extends AnimationInfoLoader<AnimationInfo> {
    private static final BakedAnimationInfo<AnimationInfo> EMPTY = new BakedAnimationInfo<>(Object2ObjectMaps.emptyMap());
    public static final ClientAnimationInfoLoader INSTANCE = new ClientAnimationInfoLoader(new GsonBuilder().create());
    private Map<ResourceLocation, BakedAnimationInfo<AnimationInfo>> clientAnimationInfos = ImmutableMap.of();

    public ClientAnimationInfoLoader(Gson gson) {
        super(gson);
    }

    @Override
    protected void apply(Map<ResourceLocation, JsonElement> jsons, ResourceManager resourceManager, ProfilerFiller profiler) {
        //Client side. Copy from geckolib
        ImmutableMap.Builder<ResourceLocation, BakedAnimationInfo<AnimationInfo>> builder = ImmutableMap.builder();
        for (Map.Entry<ResourceLocation, AnimationFile> fileEntry : GeckoLibCache.getInstance().getAnimations().entrySet()) {
            if (!fileEntry.getKey().getNamespace().equals(FossilMod.MOD_ID)) {
                continue;
            }
            Map<String, AnimationInfo> bakedMap = new Object2ObjectOpenHashMap<>();
            for (Map.Entry<String, Animation> animationEntry : fileEntry.getValue().animations().entrySet()) {
                bakedMap.put(animationEntry.getKey(), new AnimationInfo(animationEntry.getValue()));
            }
            builder.put(fileEntry.getKey(), new BakedAnimationInfo<>(bakedMap));
        }
        clientAnimationInfos = builder.build();
        AnimationCategoryLoader.INSTANCE.apply(clientAnimationInfos);
    }

    @Override
    public Map<ResourceLocation, BakedAnimationInfo<AnimationInfo>> getAnimationInfos() {
        return clientAnimationInfos;
    }

    /**
     * Returns all animations for a given dino in the same format as the client receives them
     *
     * @param animationFile the animation file "animations/xyz.animation.json"
     * @return a map containing all animations for a given dino
     */
    public BakedAnimationInfo<AnimationInfo> getAnimations(ResourceLocation animationFile) {
        return clientAnimationInfos.getOrDefault(animationFile, EMPTY);
    }
}
