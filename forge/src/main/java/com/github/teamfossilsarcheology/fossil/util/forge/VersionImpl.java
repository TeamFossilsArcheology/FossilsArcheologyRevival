package com.github.teamfossilsarcheology.fossil.util.forge;

import com.github.teamfossilsarcheology.fossil.Fossil;
import net.minecraftforge.fml.ModList;

public class VersionImpl {
    public static String getVersion() {
        return ModList.get().getModFileById(Fossil.MOD_ID).versionString();
    }
}
