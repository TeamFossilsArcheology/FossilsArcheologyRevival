package com.fossil.fossil.config;

import dev.architectury.injectables.annotations.ExpectPlatform;
import net.minecraft.resources.ResourceLocation;

public class FossilConfig {
    public static final String GENERATE_PREHISTORIC_TREES = "generatePrehistoricTrees";
    public static final String GENERATE_HELL_SHIPS = "generateHellShips";
    public static final String GENERATE_ACADEMY = "generateAcademy";
    public static final String GENERATE_TEMPLE = "generateTemple";
    public static final String GENERATE_FOSSILS = "generateFossils";
    public static final String GENERATE_PERMAFROST = "generatePermafrost";
    public static final String GENERATE_VOLCANIC_ROCK = "generateVolcanicRock";
    public static final String GENERATE_AZTEC_WEAPON_SHOPS = "generateAztecWeaponShops";
    public static final String GENERATE_MOAI = "generateMoai";
    public static final String GENERATE_TAR_PITS = "generateTarPits";
    public static final String GENERATE_VOLCANO_BIOME = "generateVolcanoBiome";
    public static final String VOLCANO_BIOME_RARITY = "volcanoBiomeRarity";
    public static final String FOSSIL_ORE_RARITY = "fossilOreRarity";
    public static final String PERMAFROST_RARITY = "permafrostRarity";
    public static final String HELL_SHIP_SPACING = "hellShipSpacing";
    public static final String HELL_SHIP_SEPERATION = "hellShipSeperation";
    public static final String TAR_PIT_RARITY = "tarPitRarity";
    public static final String MOAI_RARITY = "moaiRarity";
    public static final String SPAWN_TAR_SLIMES = "spawnTarSlimes";
    public static final String SPAWN_NAUTILUS = "spawnNautilus";
    public static final String SPAWN_COELACANTH = "spawnCoelacanth";
    public static final String SPAWN_ALLIGATOR_GAR = "spawnAlligatorGar";
    public static final String SPAWN_STURGEON = "spawnSturgeon";
    public static final String TAR_SLIMES_SPAWN_RATE = "tarSlimesSpawnRate";
    public static final String ALLIGATOR_GAR_SPAWN_WEIGHT = "alligatorGarSpawnWeight";
    public static final String COELACANTH_SPAWN_WEIGHT = "coelacanthSpawnWeight";
    public static final String NAUTILUS_SPAWN_WEIGHT = "nautilusSpawnWeight";
    public static final String STURGEON_SPAWN_WEIGHT = "sturgeonSpawnWeight";
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
    public static final String CUSTOM_MAIN_MENU = "customMainMenu";
    public static final String HELMET_OVERLAYS = "helmetOverlays";
    public static final String FLYING_TARGET_MAX_HEIGHT = "flyingTargetMaxHeight";
    public static final String CULTURE_VAT_FAIL_CHANCE = "cultureVatFailChance";
    public static final String MACHINES_REQUIRE_ENERGY = "machinesRequireEnergy";
    public static final String MACHINE_MAX_ENERGY = "machineMaxEnergy";
    public static final String MACHINE_TRANSFER_RATE = "machineTransferRate";
    public static final String MACHINE_ENERGY_USAGE = "machineEnergyUsage";
    public static final String FERN_TICK_RATE = "fernTickRate";
    public static final String ANU_BLOCK_PLACING = "anuBlockPlacing";

    public static boolean isStructurePoolEnabled(ResourceLocation field) {
        switch (field.getPath()) {
            case "aztec_weapon_shop" -> {
                return isEnabled(GENERATE_AZTEC_WEAPON_SHOPS);
            }
            case "aztec_temple" -> {
                return isEnabled(GENERATE_TEMPLE);
            }
            case "egyptian_academy" -> {
                return isEnabled(GENERATE_ACADEMY);
            }
            default -> {
                return true;
            }
        }
    }

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
}
