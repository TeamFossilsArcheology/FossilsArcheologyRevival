package com.github.teamfossilsarcheology.fossil.util.fabric;

import com.github.teamfossilsarcheology.fossil.Fossil;
import net.fabricmc.loader.api.FabricLoader;

public class VersionImpl {
    public static String getVersion() {
        return FabricLoader.getInstance().getModContainer(Fossil.MOD_ID).get().getMetadata().getVersion().getFriendlyString();
    }
}
