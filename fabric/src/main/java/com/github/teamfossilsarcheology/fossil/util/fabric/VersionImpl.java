package com.github.teamfossilsarcheology.fossil.util.fabric;

import com.github.teamfossilsarcheology.fossil.FossilMod;
import net.fabricmc.loader.api.FabricLoader;

public class VersionImpl {
    public static String getVersion() {
        return FabricLoader.getInstance().getModContainer(FossilMod.MOD_ID).get().getMetadata().getVersion().getFriendlyString();
    }
}
