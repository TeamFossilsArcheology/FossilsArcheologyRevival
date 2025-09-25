package com.github.teamfossilsarcheology.fossil.forge;

import net.minecraftforge.fml.loading.FMLLoader;

public class FossilMixinPluginImpl {
    public static boolean isModLoaded(String mod) {
        return FMLLoader.getLoadingModList().getModFileById(mod) != null;
    }
}
