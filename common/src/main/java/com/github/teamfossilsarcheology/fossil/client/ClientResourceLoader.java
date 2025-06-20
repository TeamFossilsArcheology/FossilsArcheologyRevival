package com.github.teamfossilsarcheology.fossil.client;

import com.github.teamfossilsarcheology.fossil.FossilMod;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimplePreparableReloadListener;

import java.util.Collection;

public abstract class ClientResourceLoader<T> extends SimplePreparableReloadListener<T> {
    private final String modId;
    protected final String directory;
    protected final String suffix;

    protected ClientResourceLoader(String directory, String suffix) {
        this.modId = FossilMod.MOD_ID;
        this.directory = directory;
        this.suffix = suffix;
    }

    /**
     * Returns a collection of paths limited to the directory in the namespace of the mod
     */
    protected Collection<ResourceLocation> listResources(ResourceManager resourceManager) {
        return resourceManager.listPacks().filter(packResources -> packResources.getNamespaces(PackType.CLIENT_RESOURCES).contains(modId))
                .flatMap(packResources -> packResources.getResources(PackType.CLIENT_RESOURCES, modId, directory, s -> s.getPath().endsWith(suffix)).stream())
                .distinct().toList();
    }
}
