package com.github.teamfossilsarcheology.fossil.client;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.Resource;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimplePreparableReloadListener;

import java.util.Map;

public abstract class ClientResourceLoader<T> extends SimplePreparableReloadListener<T> {
    protected final String directory;
    protected final String suffix;

    protected ClientResourceLoader(String directory, String suffix) {
        this.directory = directory;
        this.suffix = suffix;
    }

    /**
     * Returns a collection of paths limited to the directory in the namespace of the mod
     */
    protected Map<ResourceLocation, Resource> listResources(ResourceManager resourceManager) {
        return resourceManager.listResources(directory, fileName -> fileName.toString().endsWith(suffix));
        //TODO: Find out if loading other mods with invalid file names is still an issue
        /*return resourceManager.listPacks().filter(packResources -> packResources.getNamespaces(PackType.CLIENT_RESOURCES).contains(modId))
                .flatMap(packResources -> packResources.getResources(PackType.CLIENT_RESOURCES, modId, directory, s -> s.getPath().endsWith(suffix)).stream())
                .distinct().toList();*/
    }
}
