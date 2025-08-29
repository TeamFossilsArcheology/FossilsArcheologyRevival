package com.github.teamfossilsarcheology.fossil.client;

import com.github.teamfossilsarcheology.fossil.FossilMod;
import com.google.common.collect.ImmutableSet;
import com.mojang.datafixers.util.Pair;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.util.profiling.ProfilerFiller;
import org.jetbrains.annotations.NotNull;

import java.util.HashSet;
import java.util.Set;

public class OptionalTextureLoader extends ResourceLoader<Pair<Set<String>, Set<String>>> {
    public static final OptionalTextureLoader INSTANCE = new OptionalTextureLoader();
    private final Set<String> babyTextures = new HashSet<>();
    private final Set<String> teenTextures = new HashSet<>();

    public OptionalTextureLoader() {
        super(PackType.CLIENT_RESOURCES, "textures/entity", ".png");
    }


    @Override
    protected @NotNull Pair<Set<String>, Set<String>> prepare(ResourceManager resourceManager, ProfilerFiller profiler) {
        ImmutableSet.Builder<String> babySetBuilder = ImmutableSet.builder();
        ImmutableSet.Builder<String> teenSetBuilder = ImmutableSet.builder();
        for (ResourceLocation resourceLocation : listResources(resourceManager).keySet()) {
            String path = resourceLocation.getPath();
            String entityName = path.split("/")[2];
            if (path.contains("baby")) {
                babySetBuilder.add(entityName);
            } else if (path.contains("teen")) {
                teenSetBuilder.add(entityName);
            }
        }
        return Pair.of(babySetBuilder.build(), teenSetBuilder.build());
    }

    @Override
    protected void apply(Pair<Set<String>, Set<String>> object, ResourceManager resourceManager, ProfilerFiller profiler) {
        babyTextures.clear();
        babyTextures.addAll(object.getFirst());
        FossilMod.LOGGER.info("Loaded {} optional baby textures", babyTextures.size());
        teenTextures.clear();
        teenTextures.addAll(object.getSecond());
        FossilMod.LOGGER.info("Loaded {} optional teen textures", teenTextures.size());
    }

    public boolean hasBabyTexture(String entityName) {
        return babyTextures.contains(entityName);
    }

    public boolean hasTeenTexture(String entityName) {
        return teenTextures.contains(entityName);
    }
}
