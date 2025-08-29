package com.github.teamfossilsarcheology.fossil.client;

import com.github.teamfossilsarcheology.fossil.FossilMod;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.resources.Resource;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimplePreparableReloadListener;

import java.util.Map;

public abstract class ResourceLoader<T> extends SimplePreparableReloadListener<T> {
    private final PackType packType;
    private final String modId;
    protected final String directory;
    protected final String suffix;

    protected ResourceLoader(PackType packType, String directory, String suffix) {
        this.packType = packType;
        this.modId = FossilMod.MOD_ID;
        this.directory = directory;
        this.suffix = suffix;
    }

    /**
     * Returns a collection of paths limited to the directory in the namespace of the mod
     */
    protected Map<ResourceLocation, Resource> listResources(ResourceManager resourceManager) {
        return listResources(resourceManager, suffix);
    }

    protected Map<ResourceLocation, Resource> listResources(ResourceManager resourceManager, String suffix) {
        return resourceManager.listResources(directory, fileName -> fileName.getPath().endsWith(suffix));
        //TODO: Find out if loading other mods with invalid file names is still an issue
        /*return resourceManager.listPacks().filter(packResources -> packResources.getNamespaces(packType).contains(modId))
                .flatMap(packResources -> packResources.getResources(packType, modId, directory, s -> s.getPath().endsWith(suffix)).stream())
                .distinct().toList();*/
    }
}
