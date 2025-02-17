package com.github.teamfossilsarcheology.fossil.util.forge;

import com.github.teamfossilsarcheology.fossil.FossilMod;
import net.minecraftforge.fml.loading.FMLLoader;

public class VersionImpl {
    public static String getVersion() {
        return FMLLoader.getLoadingModList().getModFileById(FossilMod.MOD_ID).versionString();
    }
}
