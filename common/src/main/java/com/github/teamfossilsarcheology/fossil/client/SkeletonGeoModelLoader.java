package com.github.teamfossilsarcheology.fossil.client;

import com.github.teamfossilsarcheology.fossil.FossilMod;
import com.google.common.collect.ImmutableMap;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.util.profiling.ProfilerFiller;
import org.jetbrains.annotations.NotNull;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.loading.FileLoader;
import software.bernie.geckolib.loading.json.raw.Model;
import software.bernie.geckolib.loading.object.BakedModelFactory;
import software.bernie.geckolib.loading.object.GeometryTree;

import java.util.Map;

/**
 * Loads different instances of the geo models to prevent the skeletons from playing geckolib animations
 */
public class SkeletonGeoModelLoader extends ResourceLoader<Map<ResourceLocation, BakedGeoModel>> {
    public static final SkeletonGeoModelLoader INSTANCE = new SkeletonGeoModelLoader();
    private Map<ResourceLocation, BakedGeoModel> geoModels = ImmutableMap.of();

    public SkeletonGeoModelLoader() {
        super(PackType.CLIENT_RESOURCES, "geo/entity", ".json");
    }

    @Override
    protected @NotNull Map<ResourceLocation, BakedGeoModel> prepare(ResourceManager resourceManager, ProfilerFiller profiler) {
        Map<ResourceLocation, BakedGeoModel> map = new Object2ObjectOpenHashMap<>();
        for (ResourceLocation resourceLocation : listResources(resourceManager).keySet()) {
            Model model = FileLoader.loadModelFile(resourceLocation, resourceManager);
            map.put(resourceLocation, BakedModelFactory.getForNamespace(resourceLocation.getNamespace()).constructGeoModel(GeometryTree.fromModel(model)));
        }
        return map;
    }

    @Override
    protected void apply(Map<ResourceLocation, BakedGeoModel> files, ResourceManager resourceManager, ProfilerFiller profiler) {
        ImmutableMap.Builder<ResourceLocation, BakedGeoModel> mapBuilder = ImmutableMap.builder();
        mapBuilder.putAll(files);
        geoModels = mapBuilder.build();
        FossilMod.LOGGER.info("Loaded {} skeleton models", geoModels.size());
    }

    public BakedGeoModel getSkeletonModel(ResourceLocation file) {
        return geoModels.get(file);
    }
}
