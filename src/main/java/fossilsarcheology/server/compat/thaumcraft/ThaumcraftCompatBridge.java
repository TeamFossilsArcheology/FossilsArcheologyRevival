package fossilsarcheology.server.compat.thaumcraft;

import net.minecraftforge.fml.common.Loader;

/**
 * Created by Joseph on 6/23/2018.
 */
public class ThaumcraftCompatBridge {

    private static final String TC_MOD_ID = "thaumcraft";

    public static void loadThaumcraftCompat() {
        if (Loader.isModLoaded(TC_MOD_ID)) {
            ThaumcraftCompat.register();
        }
    }
}