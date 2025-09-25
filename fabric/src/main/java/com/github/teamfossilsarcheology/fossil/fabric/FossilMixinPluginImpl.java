package com.github.teamfossilsarcheology.fossil.fabric;

import net.fabricmc.loader.api.FabricLoader;

public class FossilMixinPluginImpl {
    public static boolean isModLoaded(String mod) {
        return FabricLoader.getInstance().getModContainer(mod).isPresent();
    }
}
