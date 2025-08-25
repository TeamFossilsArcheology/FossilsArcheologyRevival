package com.github.teamfossilsarcheology.fossil.config.fabric;

import com.github.teamfossilsarcheology.fossil.FossilMod;
import com.github.teamfossilsarcheology.fossil.config.FossilConfig;
import eu.midnightdust.lib.config.MidnightConfig;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.function.Predicate;

/**
 * Simple way to forcefully update user configs
 */
public class FabricConfigFix {
    private static final Map<Integer, Set<Update<?>>> FIXES = new HashMap<>();

    static {
    }

    private static <T> void add(int version, String name, T newValue, Predicate<T> predicate) {
        FIXES.computeIfAbsent(version, k -> new HashSet<>()).add(new Update<>(name, newValue, predicate));
    }

    private static <T> void add(int version, String name, T newValue) {
        add(version, name, newValue, t -> true);
    }

    public static void fixConfig(Map<String, Field> fields) {
        Field version = fields.get(FossilConfig.VERSION);
        try {
            if (version != null && (Integer) version.get(null) < FossilConfig.VERSION_VALUE) {
                FossilMod.LOGGER.info("Config version is outdated: {} -> {}", version.get(null), FossilConfig.VERSION_VALUE);
                for (Update<?> update : FIXES.getOrDefault(FossilConfig.VERSION_VALUE, new HashSet<>())) {
                    Field field = fields.get(update.name);
                    if (field == null) continue;
                    Object oldValue = field.get(null);
                    if (!update.canReplace(oldValue)) continue;
                    FossilMod.LOGGER.info("Updating {}: {} -> {}", update.name, oldValue, update.newValue);
                    field.set(null, update.newValue);
                }
                version.set(null, FossilConfig.VERSION_VALUE);
                MidnightConfig.write(FossilMod.MOD_ID);
            }
        } catch (IllegalAccessException e) {
            FossilMod.LOGGER.error("Error while trying to update config", e);
        }
    }

    private record Update<T>(String name, T newValue, Predicate<T> predicate) {
        public boolean canReplace(Object oldValue) {
            return oldValue.getClass().isAssignableFrom(newValue.getClass()) && predicate.test((T) oldValue);
        }
    }
}
