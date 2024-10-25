package com.github.teamfossilsarcheology.fossil.client;

import com.github.teamfossilsarcheology.fossil.FossilMod;
import com.google.common.collect.ImmutableSet;
import com.mojang.datafixers.util.Pair;
import net.minecraft.resources.ResourceLocation;
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
    private static final int PATH_SUFFIX_LENGTH = PATH_SUFFIX.length();
    private final Set<String> babyTextures = new HashSet<>();
    private final Set<String> teenTextures = new HashSet<>();


    @Override
    protected @NotNull Pair<Set<String>, Set<String>> prepare(ResourceManager resourceManager, ProfilerFiller profiler) {
        ImmutableSet.Builder<String> babySetBuilder = ImmutableSet.builder();
        ImmutableSet.Builder<String> teenSetBuilder = ImmutableSet.builder();
        int i = DIRECTORY.length() + 1;

        for (ResourceLocation resourceLocation : resourceManager.listResources(DIRECTORY, string -> string.endsWith(PATH_SUFFIX))) {
            if (!resourceLocation.getNamespace().equals(FossilMod.MOD_ID)) {
                continue;
            }
            String path = resourceLocation.getPath();
            String entityName = path.substring(i, path.length() - PATH_SUFFIX_LENGTH);
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
