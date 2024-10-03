package com.github.teamfossilsarcheology.fossil.config.fabric;

import eu.midnightdust.lib.config.MidnightConfig;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/**
 * {@link com.github.teamfossilsarcheology.fossil.config.FossilConfig}
 */
@SuppressWarnings({"unused", "java:S1444"})
public class FossilConfigImpl extends MidnightConfig {


    @MidnightConfig.Comment
    public static MidnightConfig.Comment generate;
    @MidnightConfig.Entry
    public static boolean generateAcademy = true;
    @MidnightConfig.Entry
    public static boolean generateAztecWeaponShops = true;
    @MidnightConfig.Entry
    public static boolean generateHellShips = true;
    @MidnightConfig.Entry(min = 1, max = 1000000)
    public static int hellShipSpacing = 24;
    @MidnightConfig.Entry(min = 1, max = 1000000)
    public static int hellShipSeparation = 5;
    @MidnightConfig.Entry
    public static boolean generateMoai = true;
    @MidnightConfig.Entry(min = 1, max = 1000000)
    public static int moaiRarity = 400;
    @MidnightConfig.Entry
    public static boolean generateTarPits = true;
    @MidnightConfig.Entry(min = 1, max = 1000000)
    public static int tarPitRarity = 100;
    @MidnightConfig.Entry
    public static boolean generateTemple = true;
    @MidnightConfig.Entry
    public static boolean generateFossils = true;
    @MidnightConfig.Entry(min = 1, max = 500)
    public static int fossilOreRarity = 10;
    @MidnightConfig.Entry
    public static boolean generatePermafrost = true;
    @MidnightConfig.Entry(min = 1, max = 500)
    public static int permafrostRarity = 4;
    @MidnightConfig.Entry
    public static boolean generateVolcanicRock = true;
    @MidnightConfig.Entry
    public static boolean generateVolcanoBiome = true;
    @MidnightConfig.Entry(min = 1, max = 1000000)
    public static int volcanoBiomeRarity = 7;
    @MidnightConfig.Comment
    public static MidnightConfig.Comment spawn;
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
    private static final Map<String, Field> MAPPED_ENTRIES = new HashMap<>();

    public static void initFabricConfig() {
        Field[] allFields = FossilConfigImpl.class.getDeclaredFields();
        for (Field field : allFields) {
            if (field.getAnnotation(Entry.class) != null) {
                MAPPED_ENTRIES.put(field.getName(), field);
            }
        }
    }

    public static boolean isEnabled(String field) {
        try {
            return (boolean) MAPPED_ENTRIES.get(field).get(null);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    public static int getInt(String field) {
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
}
