package com.github.teamfossilsarcheology.fossil.client;

import com.github.teamfossilsarcheology.fossil.FossilMod;
import com.google.common.collect.ImmutableSet;
import com.mojang.datafixers.util.Pair;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimplePreparableReloadListener;
import net.minecraft.util.profiling.ProfilerFiller;
import org.jetbrains.annotations.NotNull;

import java.util.HashSet;
import java.util.Set;

public class OptionalTextureLoader extends SimplePreparableReloadListener<Pair<Set<String>, Set<String>>> {
    public static final OptionalTextureLoader INSTANCE = new OptionalTextureLoader();
    private static final String DIRECTORY = "textures/entity";
    private static final String PATH_SUFFIX = ".png";
    private final Set<String> babyTextures = new HashSet<>();
    private final Set<String> teenTextures = new HashSet<>();


    @Override
    protected @NotNull Pair<Set<String>, Set<String>> prepare(ResourceManager resourceManager, ProfilerFiller profiler) {
        ImmutableSet.Builder<String> babySetBuilder = ImmutableSet.builder();
        ImmutableSet.Builder<String> teenSetBuilder = ImmutableSet.builder();
        resourceManager.listPacks().filter(packResources -> packResources.getNamespaces(PackType.CLIENT_RESOURCES).contains(FossilMod.MOD_ID)).forEach(packResources -> {
            for (ResourceLocation resourceLocation : packResources.getResources(PackType.CLIENT_RESOURCES, FossilMod.MOD_ID, DIRECTORY, Integer.MAX_VALUE, s -> s.endsWith(PATH_SUFFIX))) {
                String path = resourceLocation.getPath();
                String entityName = path.split("/")[2];
                if (path.contains("baby")) {
                    babySetBuilder.add(entityName);
                } else if (path.contains("teen")) {
                    teenSetBuilder.add(entityName);
                }
            }
        });
        return Pair.of(babySetBuilder.build(), teenSetBuilder.build());
    }

    @Override
    protected void apply(Pair<Set<String>, Set<String>> object, ResourceManager resourceManager, ProfilerFiller profiler) {
        babyTextures.clear();
        babyTextures.addAll(object.getFirst());
        teenTextures.clear();
        teenTextures.addAll(object.getSecond());
    }

    public boolean hasBabyTexture(String entityName) {
        return babyTextures.contains(entityName);
    }

    public boolean hasTeenTexture(String entityName) {
        return teenTextures.contains(entityName);
    }
}
