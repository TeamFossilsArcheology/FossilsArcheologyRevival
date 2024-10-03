package com.github.teamfossilsarcheology.fossil.config.forge;

/**
 * {@link com.github.teamfossilsarcheology.fossil.config.FossilConfig}
 */
public class FossilConfigImpl {
    public static boolean isEnabled(String field) {
        return ForgeConfig.MAPPED_BOOLS.get(field).get();
    }

    public static int getInt(String field) {
        return ForgeConfig.MAPPED_INTS.get(field).get();
    }

    public static double getDouble(String field) {
        return ForgeConfig.MAPPED_DOUBLES.get(field).get();
    }
}
