package com.github.teamfossilsarcheology.fossil.config;

import dev.architectury.injectables.annotations.ExpectPlatform;
import org.apache.commons.lang3.NotImplementedException;

import java.util.Map;

public class FossilConfig {
    public static final String GENERATE_HELL_SHIPS = "generateHellShips";
    public static final String GENERATE_MOAI = "generateMoai";
    public static final String GENERATE_FOSSILS = "generateFossils";
    public static final String GENERATE_PERMAFROST = "generatePermafrost";
    public static final String GENERATE_VOLCANIC_ROCK = "generateVolcanicRock";
    public static final String GENERATE_VOLCANO_BIOME = "generateVolcanoBiome";
    public static final String VOLCANO_BIOME_RARITY = "volcanoBiomeRarity";

    public static final String FISH_ARE_PERSISTENT = "fishArePersistent";
    public static final String SPAWN_TAR_SLIMES = "spawnTarSlimes";
    public static final String TAR_SLIMES_SPAWN_RATE = "tarSlimesSpawnRate";

    public static final String FEATHERED_DILO = "featheredDilo";
    public static final String FEATHERED_CERATO = "featheredCerato";
    public static final String FEATHERED_DRYO = "featheredDryo";
    public static final String HEALING_DINOS = "healingDinos";
    public static final String ENABLE_HUNGER = "enableHunger";
    public static final String ENABLE_STARVATION = "enableStarvation";
    public static final String BREEDING_DINOS = "breedingDinos";
    public static final String EGGS_LIKE_CHICKENS = "eggsLikeChickens";
    public static final String WHIP_TO_TAME_DINO = "whipToTameDino";
    public static final String PREGNANCY_DURATION = "pregnancyDuration";
    public static final String DINOS_BREAK_BLOCKS = "dinosBreakBlocks";
    public static final String BLOCK_BREAK_HARDNESS = "blockBreakHardness";
    public static final String DINOS_EAT_BLOCKS = "dinosEatBlocks";
    public static final String DINOS_EAT_MODDED_MOBS = "dinosEatModdedMobs";
    public static final String ANIMALS_FEAR_DINOS = "animalsFearDinos";
    public static final String FLYING_TARGET_MAX_HEIGHT = "flyingTargetMaxHeight";
    public static final String ANU_BLOCK_PLACING = "anuBlockPlacing";
    public static final String ANUBITE_HAS_COOLDOWN = "anubiteHasCooldown";
    public static final String ANUBITE_COOLDOWN = "anubiteCooldown";

    public static final String CUSTOM_MAIN_MENU = "customMainMenu";
    public static final String HELMET_OVERLAYS = "helmetOverlays";
    public static final String CULTURE_VAT_FAIL_CHANCE = "cultureVatFailChance";
    public static final String MACHINES_REQUIRE_ENERGY = "machinesRequireEnergy";
    public static final String MACHINE_MAX_ENERGY = "machineMaxEnergy";
    public static final String MACHINE_TRANSFER_RATE = "machineTransferRate";
    public static final String MACHINE_ENERGY_USAGE = "machineEnergyUsage";
    public static final String FERN_TICK_RATE = "fernTickRate";
    public static final String VERSION = "version";
    public static final int VERSION_VALUE = 1;

    @ExpectPlatform
    public static boolean isEnabled(String field) {
        return false;
    }

    @ExpectPlatform
    public static int getInt(String field) {
        return 0;
    }

    @ExpectPlatform
    public static double getDouble(String field) {
        return 0;
    }

    @ExpectPlatform
    public static void overrideEntries(Map<String, Integer> ints, Map<String, Boolean> bools) {
        throw new NotImplementedException();
    }
}
