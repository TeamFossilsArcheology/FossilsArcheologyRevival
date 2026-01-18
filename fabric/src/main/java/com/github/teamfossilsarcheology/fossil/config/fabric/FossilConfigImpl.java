package com.github.teamfossilsarcheology.fossil.config.fabric;

import com.github.teamfossilsarcheology.fossil.config.FossilConfig;
import eu.midnightdust.lib.config.MidnightConfig;
import it.unimi.dsi.fastutil.objects.Object2BooleanOpenHashMap;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;

import java.lang.reflect.Field;
import java.util.Map;

/**
 * {@link com.github.teamfossilsarcheology.fossil.config.FossilConfig}
 */
@SuppressWarnings({"unused", "java:S1444"})
public class FossilConfigImpl extends MidnightConfig {


    @MidnightConfig.Comment
    public static MidnightConfig.Comment generate;
    @MidnightConfig.Entry
    public static boolean generateHellShips = true;
    @MidnightConfig.Entry
    public static boolean generateMoai = true;
    @MidnightConfig.Entry
    public static boolean generateFossils = true;
    @MidnightConfig.Entry
    public static boolean generatePermafrost = true;
    @MidnightConfig.Entry
    public static boolean generateVolcanicRock = true;
    @MidnightConfig.Entry
    public static boolean generateVolcanoBiome = true;
    @MidnightConfig.Entry(min = 1, max = 1000000)
    public static int volcanoBiomeRarity = 7;
    @MidnightConfig.Comment
    public static MidnightConfig.Comment spawn;
    @MidnightConfig.Entry
    public static boolean fishArePersistent = true;
    @MidnightConfig.Entry
    public static boolean spawnAlligatorGar = true;
    @MidnightConfig.Entry(min = 0, max = 1000000)
    public static int alligatorGarSpawnWeight = 4;
    @MidnightConfig.Entry
    public static boolean spawnCoelacanth = true;
    @MidnightConfig.Entry(min = 0, max = 1000000)
    public static int coelacanthSpawnWeight = 3;
    @MidnightConfig.Entry
    public static boolean spawnNautilus = true;
    @MidnightConfig.Entry(min = 0, max = 1000000)
    public static int nautilusSpawnWeight = 2;
    @MidnightConfig.Entry
    public static boolean spawnSturgeon = true;
    @MidnightConfig.Entry(min = 0, max = 1000000)
    public static int sturgeonSpawnWeight = 5;
    @MidnightConfig.Entry
    public static boolean spawnTarSlimes = true;
    @MidnightConfig.Entry(min = 1, max = 1000000)
    public static int tarSlimesSpawnRate = 75;
    @MidnightConfig.Comment
    public static MidnightConfig.Comment mob;
    @MidnightConfig.Entry
    public static boolean featheredDilo = false;
    @MidnightConfig.Entry
    public static boolean featheredCerato = false;
    @MidnightConfig.Entry
    public static boolean featheredDryo = false;
    @MidnightConfig.Entry
    public static boolean healingDinos = true;
    @MidnightConfig.Entry
    public static boolean enableHunger = true;
    @MidnightConfig.Entry
    public static boolean enableStarvation = false;
    @MidnightConfig.Entry
    public static boolean breedingDinos = true;
    @MidnightConfig.Entry
    public static boolean eggsLikeChickens = false;
    @MidnightConfig.Entry
    public static boolean whipToTameDino = true;
    @MidnightConfig.Entry(min = 1, max = 1000000000)
    public static int pregnancyDuration = 10000;
    @MidnightConfig.Entry
    public static boolean dinosBreakBlocks = true;
    @MidnightConfig.Entry(min = 0, max = 100)
    public static double blockBreakHardness = 5;
    @MidnightConfig.Entry
    public static boolean dinosEatBlocks = true;
    @MidnightConfig.Entry
    public static boolean dinosEatModdedMobs = true;
    @MidnightConfig.Entry
    public static boolean animalsFearDinos = true;
    @MidnightConfig.Entry(min = 1, max = 512)
    public static int flyingTargetMaxHeight = 128;
    @MidnightConfig.Entry
    public static boolean anuBlockPlacing = true;
    @MidnightConfig.Entry
    public static boolean anubiteHasCooldown = false;
    @MidnightConfig.Entry(min = 1200, max = 1000000000)
    public static int anubiteCooldown = 72000;
    @MidnightConfig.Comment
    public static MidnightConfig.Comment machine;
    @MidnightConfig.Entry(min = 1, max = 100)
    public static int cultureVatFailChance = 10;
    @MidnightConfig.Entry
    public static boolean machinesRequireEnergy = false;
    @MidnightConfig.Entry(min = 1, max = 1000000)
    public static int machineMaxEnergy = 1000;
    @MidnightConfig.Entry(min = 1, max = 1000000)
    public static int machineTransferRate = 10;
    @MidnightConfig.Entry(min = 1, max = 1000000)
    public static int machineEnergyUsage = 1;
    @MidnightConfig.Comment
    public static MidnightConfig.Comment other;
    @MidnightConfig.Entry
    public static boolean customMainMenu = true;
    @MidnightConfig.Entry
    public static boolean helmetOverlays = true;
    @MidnightConfig.Entry(min = 1, max = 1000000)
    public static int fernTickRate = 2;
    @MidnightConfig.Entry(min = 1, max = FossilConfig.VERSION_VALUE)
    public static int version = 0;
    public static final Map<String, Field> MAPPED_ENTRIES = new Object2ObjectOpenHashMap<>();
    private static final Map<String, Integer> INTEGER_OVERRIDES = new Object2IntOpenHashMap<>();
    private static final Map<String, Boolean> BOOLEAN_OVERRIDES = new Object2BooleanOpenHashMap<>();

    public static boolean isEnabled(String field) {
        if (BOOLEAN_OVERRIDES.containsKey(field)) {
            return BOOLEAN_OVERRIDES.get(field);
        }
        try {
            return (boolean) MAPPED_ENTRIES.get(field).get(null);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    public static int getInt(String field) {
        if (INTEGER_OVERRIDES.containsKey(field)) {
            return INTEGER_OVERRIDES.get(field);
        }
        try {
            return (int) MAPPED_ENTRIES.get(field).get(null);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    public static double getDouble(String field) {
        try {
            return (double) MAPPED_ENTRIES.get(field).get(null);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    public static void overrideEntries(Map<String, Integer> ints, Map<String, Boolean> bools) {
        INTEGER_OVERRIDES.putAll(ints);
        BOOLEAN_OVERRIDES.putAll(bools);
    }
}
