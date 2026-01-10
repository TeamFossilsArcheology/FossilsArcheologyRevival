package com.github.teamfossilsarcheology.fossil.config.forge;

import it.unimi.dsi.fastutil.objects.Object2BooleanOpenHashMap;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;

import java.util.Map;

/**
 * {@link com.github.teamfossilsarcheology.fossil.config.FossilConfig}
 */
public class FossilConfigImpl {
    private static final Map<String, Integer> INTEGER_OVERRIDES = new Object2IntOpenHashMap<>();
    private static final Map<String, Boolean> BOOLEAN_OVERRIDES = new Object2BooleanOpenHashMap<>();

    public static boolean isEnabled(String field) {
        if (BOOLEAN_OVERRIDES.containsKey(field)) {
            return BOOLEAN_OVERRIDES.get(field);
        }
        return ForgeConfig.MAPPED_BOOLS.get(field).get();
    }

    public static int getInt(String field) {
        if (INTEGER_OVERRIDES.containsKey(field)) {
            return INTEGER_OVERRIDES.get(field);
        }
        return ForgeConfig.MAPPED_INTS.get(field).get();
    }

    public static double getDouble(String field) {
        return ForgeConfig.MAPPED_DOUBLES.get(field).get();
    }

    public static void overrideEntries(Map<String, Integer> ints, Map<String, Boolean> bools) {
        INTEGER_OVERRIDES.putAll(ints);
        BOOLEAN_OVERRIDES.putAll(bools);
    }
}
