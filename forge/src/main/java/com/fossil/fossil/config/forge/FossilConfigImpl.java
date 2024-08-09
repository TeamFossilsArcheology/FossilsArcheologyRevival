package com.fossil.fossil.config.forge;

/**
 * {@link com.fossil.fossil.config.FossilConfig}
 */
public class FossilConfigImpl {
    public static boolean isEnabled(String field) {
        return ForgeConfig.mappedBools.get(field).get();
    }

    public static int getInt(String field) {
        return ForgeConfig.mappedInts.get(field).get();
    }
}
