package com.github.teamfossilsarcheology.fossil.util.forge;

import com.github.teamfossilsarcheology.fossil.FossilMod;
import net.minecraftforge.fml.ModList;

public class VersionImpl {
    public static String getVersion() {
        return ModList.get().getModFileById(FossilMod.MOD_ID).versionString();
    }
}
