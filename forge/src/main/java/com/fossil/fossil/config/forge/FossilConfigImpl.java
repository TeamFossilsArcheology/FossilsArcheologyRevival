package com.fossil.fossil.config.forge;

public class FossilConfigImpl {
    public static boolean isEnabled(String field) {
        return ForgeConfig.mappedBools.get(field).get();
    }

    public static int getInt(String field) {
        return ForgeConfig.mappedInts.get(field).get();
    }
}
