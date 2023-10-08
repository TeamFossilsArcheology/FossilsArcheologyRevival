package com.fossil.fossil.config.fabric;

import eu.midnightdust.lib.config.MidnightConfig;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public class FossilConfigImpl extends MidnightConfig {


    @MidnightConfig.Comment
    public static MidnightConfig.Comment generate;
    @MidnightConfig.Entry
    public static boolean generatePrehistoricTrees = false;
    @MidnightConfig.Entry
    public static boolean generateHellShips = true;
    @MidnightConfig.Entry
    public static boolean generateAcademy = true;
    @MidnightConfig.Entry
    public static boolean generateTemple = true;
    @MidnightConfig.Entry
    public static boolean generateFossils = true;
    @MidnightConfig.Entry
    public static boolean generatePermafrost = true;
    @MidnightConfig.Entry
    public static boolean generateVolcanicRock = true;
    @MidnightConfig.Entry
    public static boolean generateAztecWeaponShops = true;
    @MidnightConfig.Entry
    public static boolean generateMoai = true;
    @MidnightConfig.Entry
    public static boolean generateTarPit = true;
    @MidnightConfig.Entry
    public static boolean generateVolcanoBiome = true;
    @MidnightConfig.Entry(min = 1, max = 1000000)
    public static int fossilOreRarity = 38;
    @MidnightConfig.Entry(min = 1, max = 1000000)
    public static int permafrostRarity = 4;
    @MidnightConfig.Entry(min = 1, max = 1000000)
    public static int hellShipSpacing = 24;
    @MidnightConfig.Entry(min = 1, max = 1000000)
    public static int hellShipSeperation = 5;
    @MidnightConfig.Entry(min = 1, max = 1000000)
    public static int tarPitRarity = 100;
    @MidnightConfig.Entry(min = 1, max = 1000000)
    public static int moaiRarity = 400;
    @MidnightConfig.Entry(min = 1, max = 1000000)
    public static int volcanoBiomeRarity = 1;
    @MidnightConfig.Entry
    public static boolean spawnTarSlimes = true;
    @MidnightConfig.Entry
    public static boolean spawnNautilus = true;
    @MidnightConfig.Entry
    public static boolean spawnCoelacanth = true;
    @MidnightConfig.Entry
    public static boolean spawnAlligatorGar = true;
    @MidnightConfig.Entry
    public static boolean spawnSturgeon = true;
    @MidnightConfig.Entry(min = 1, max = 1000000)
    public static int tarSlimesSpawnRate = 75;
    @MidnightConfig.Entry(min = 0, max = 1000000)
    public static int alligatorGarSpawnWeight = 4;
    @MidnightConfig.Entry(min = 0, max = 1000000)
    public static int coelacanthSpawnWeight = 3;
    @MidnightConfig.Entry(min = 0, max = 1000000)
    public static int nautilusSpawnWeight = 2;
    @MidnightConfig.Entry(min = 0, max = 1000000)
    public static int sturgeonSpawnWeight = 5;
    @MidnightConfig.Entry
    public static boolean healingDinos = true;
    @MidnightConfig.Entry
    public static boolean starvingDinos = true;
    @MidnightConfig.Entry
    public static boolean breedingDinos = true;
    @MidnightConfig.Entry
    public static boolean eggsLikeChickens = true;
    @MidnightConfig.Entry
    public static boolean whipToTameDino = true;
    @MidnightConfig.Entry(min = 1, max = 10000)
    public static int dinoUpdateDelay = 10;
    @MidnightConfig.Entry(min = 1, max = 1000000000)
    public static int pregnancyDuration = 10000;
    @MidnightConfig.Entry
    public static boolean dinosBreakBlocks = true;
    @MidnightConfig.Entry
    public static boolean dinosEatModdedMobs = true;
    @MidnightConfig.Entry
    public static boolean animalsFearDinos = true;
    @MidnightConfig.Entry
    public static boolean customMainMenu = true;
    @MidnightConfig.Entry
    public static boolean featheredDeinonychus = true;
    @MidnightConfig.Entry
    public static boolean featheredGallimimus = true;
    @MidnightConfig.Entry
    public static boolean featheredCompsognathus = true;
    @MidnightConfig.Entry
    public static boolean quilledTriceratops = false;
    @MidnightConfig.Entry
    public static boolean featheredVelociraptor = true;
    @MidnightConfig.Entry
    public static boolean featheredTherizinosaurus = true;
    @MidnightConfig.Entry
    public static boolean featheredDryosaurus = false;
    @MidnightConfig.Entry
    public static boolean featheredOrnitholestes = true;
    @MidnightConfig.Entry
    public static boolean helmetOverlays = true;
    @MidnightConfig.Entry(min = 1, max = 512)
    public static int flyingTargetMaxHeight = 128;
    @MidnightConfig.Entry
    public static boolean machinesRequireEnergy = false;
    @MidnightConfig.Entry(min = 1, max = 1000000)
    public static int machineMaxEnergy = 1000;
    @MidnightConfig.Entry(min = 1, max = 1000000)
    public static int machineTransferRate = 10;
    @MidnightConfig.Entry(min = 1, max = 1000000)
    public static int machineEnergyUsage = 1;
    @MidnightConfig.Entry(min = 1, max = 1000000)
    public static int fernTickRate = 2;
    public static Map<String, Field> mappedEntries = new HashMap<>();

    static {
    }

    public static void initFabricConfig() {
        Field[] allFields = FossilConfigImpl.class.getDeclaredFields();
        for (Field field : allFields) {
            if (field.getAnnotation(Entry.class) != null) {
                mappedEntries.put(field.getName(), field);
            }
        }
    }

    public static boolean isEnabled(String field) {
        try {
            return (boolean) mappedEntries.get(field).get(null);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    public static int getInt(String field) {
        try {
            return (int) mappedEntries.get(field).get(null);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
}
