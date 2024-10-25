package com.github.teamfossilsarcheology.fossil.entity.animation;

import com.github.teamfossilsarcheology.fossil.FossilMod;
import com.google.common.collect.ImmutableMap;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimplePreparableReloadListener;
import net.minecraft.util.profiling.ProfilerFiller;
import org.jetbrains.annotations.NotNull;
import software.bernie.geckolib3.file.AnimationFileLoader;
import software.bernie.geckolib3.geo.raw.pojo.Converter;
import software.bernie.geckolib3.geo.raw.pojo.RawGeoModel;
import software.bernie.geckolib3.geo.raw.tree.RawGeometryTree;
import software.bernie.geckolib3.geo.render.GeoBuilder;
import software.bernie.geckolib3.geo.render.built.GeoModel;

import java.io.IOException;
import java.util.Map;

/**
 * Loads different instances of the geo models to prevent the skeletons from playing geckolib animations
 */
public class SkeletonGeoModelLoader extends SimplePreparableReloadListener<Map<ResourceLocation, GeoModel>> {
    public static final SkeletonGeoModelLoader INSTANCE = new SkeletonGeoModelLoader();
    private static final String DIRECTORY = "geo/entity";
    private static final String PATH_SUFFIX = ".json";
    private Map<ResourceLocation, GeoModel> geoModels = ImmutableMap.of();

    public SkeletonGeoModelLoader() {
    }

    @Override
    protected @NotNull Map<ResourceLocation, GeoModel> prepare(ResourceManager resourceManager, ProfilerFiller profiler) {
        Map<ResourceLocation, GeoModel> map = new Object2ObjectOpenHashMap<>();
        for (ResourceLocation resourceLocation : resourceManager.listResources(DIRECTORY, string -> string.endsWith(PATH_SUFFIX))) {
            try {
                if (resourceLocation.getNamespace().equals(FossilMod.MOD_ID)) {
                    RawGeoModel rawModel = Converter.fromJsonString(AnimationFileLoader.getResourceAsString(resourceLocation, resourceManager));
                    RawGeometryTree rawGeometryTree = RawGeometryTree.parseHierarchy(rawModel);
                    map.put(resourceLocation, GeoBuilder.getGeoBuilder(FossilMod.MOD_ID).constructGeoModel(rawGeometryTree));
                }
            } catch (IOException e) {
                FossilMod.LOGGER.error(String.format("Error parsing %S", resourceLocation), e);
                throw new RuntimeException(e);
            }
        }
        return map;
    }

    @Override
    protected void apply(Map<ResourceLocation, GeoModel> files, ResourceManager resourceManager, ProfilerFiller profiler) {
        ImmutableMap.Builder<ResourceLocation, GeoModel> mapBuilder = ImmutableMap.builder();
        mapBuilder.putAll(files);
        geoModels = mapBuilder.build();
    }

    public GeoModel getSkeletonModel(ResourceLocation file) {
        return geoModels.get(file);
    }
}
