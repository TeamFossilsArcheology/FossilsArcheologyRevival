package fossilsarcheology.server.compat.crafttweaker;

import net.minecraftforge.fml.common.Loader;

public class CraftTweakerCompatBridge {
    private static final String COMPAT_MOD_ID = "crafttweaker";

    public static void loadTweakerCompat() {
        if (Loader.isModLoaded(COMPAT_MOD_ID)) {
            CraftTweakerCompat.preInit();
        }
    }
}
