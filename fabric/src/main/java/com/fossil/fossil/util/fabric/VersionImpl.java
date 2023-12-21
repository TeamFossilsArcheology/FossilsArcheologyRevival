package com.fossil.fossil.util.fabric;

import com.fossil.fossil.Fossil;
import net.fabricmc.loader.api.FabricLoader;

public class VersionImpl {
    public static String getVersion() {
        return FabricLoader.getInstance().getModContainer(Fossil.MOD_ID).get().getMetadata().getVersion().getFriendlyString();
    }
}
