package com.github.teamfossilsarcheology.fossil.client;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimplePreparableReloadListener;

import java.util.Collection;

public abstract class ResourceLoader<T> extends SimplePreparableReloadListener<T> {
    private final PackType packType;
    private final String modId;
    protected final String directory;
    protected final String suffix;

    protected ResourceLoader(PackType packType, String modId, String directory, String suffix) {
        this.packType = packType;
        this.modId = modId;
        this.directory = directory;
        this.suffix = suffix;
    }

    /**
     * Returns a collection of paths limited to the directory in the namespace of the mod
     */
    protected Collection<ResourceLocation> listResources(ResourceManager resourceManager) {
        return listResources(resourceManager, suffix);
    }

    protected Collection<ResourceLocation> listResources(ResourceManager resourceManager, String suffix) {
        return resourceManager.listPacks().filter(packResources -> packResources.getNamespaces(packType).contains(modId))
                .flatMap(packResources -> packResources.getResources(packType, modId, directory, Integer.MAX_VALUE, s -> s.endsWith(suffix)).stream())
                .distinct().toList();
    }
}
