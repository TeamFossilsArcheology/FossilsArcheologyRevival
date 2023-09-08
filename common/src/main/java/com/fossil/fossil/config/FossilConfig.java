package com.fossil.fossil.config;

import dev.architectury.injectables.annotations.ExpectPlatform;

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
    public static final String GENERATE_TAR_SITES = "generateTarSites";
    public static final String GENERATE_FOSSIL_SITES = "generateFossilSites";
    public static final String GENERATE_VOLCANO_BIOME = "generateVolcanoBiome";
    public static final String VOLCANO_BIOME_RARITY = "volcanoBiomeRarity";
    public static final String FOSSIL_ORE_RARITY = "fossilOreRarity";
    public static final String PERMAFROST_RARITY = "permafrostRarity";
    public static final String HELL_SHIP_SPACING = "hellShipSpacing";
    public static final String HELL_SHIP_SEPERATION = "hellShipSeperation";
    public static final String TAR_SITE_RARITY = "tarSiteRarity";
    public static final String FOSSIL_SITE_RARITY = "fossilSiteRarity";
    public static final String MOAI_RARITY = "moaiRarity";
    public static final String AZTEC_WEAPON_SHOP_RARITY = "aztecWeaponShopRarity";
    public static final String TEMPLE_RARITY = "templeRarity";
    public static final String ACADEMY_RARITY = "academyRarity";
    public static final String SPAWN_TAR_SLIMES = "spawnTarSlimes";
    public static final String SPAWN_NAUTILUS = "spawnNautilus";
    public static final String SPAWN_COELACANTH = "spawnCoelacanth";
    public static final String SPAWN_ALLIGATOR_GAR = "spawnAlligatorGar";
    public static final String SPAWN_STURGEON = "spawnSturgeon";
    public static final String TAR_SLIMES_SPAWN_RATE = "tarSlimesSpawnRate";
    public static final String NAUTILUS_SPAWN_RATE = "nautilusSpawnRate";
    public static final String COELACANTH_SPAWN_RATE = "coelacanthSpawnRate";
    public static final String ALLIGATOR_GAR_SPAWN_RATE = "alligatorGarSpawnRate";
    public static final String STURGEON_SPAWN_RATE = "sturgeonSpawnRate";
    public static final String HEALING_DINOS = "healingDinos";
    public static final String STARVING_DINOS = "starvingDinos";
    public static final String BREEDING_DINOS = "breedingDinos";
    public static final String EGGS_LIKE_CHICKENS = "eggsLikeChickens";
    public static final String WHIP_TO_TAME_DINO = "whipToTameDino";
    public static final String DINO_UPDATE_DELAY = "dinoUpdateDelay";
    public static final String PREGNANCY_DURATION = "pregnancyDuration";
    public static final String DINOS_BREAK_BLOCKS = "dinosBreakBlocks";
    public static final String DINOS_EAT_MODDED_MOBS = "dinosEatModdedMobs";
    public static final String ANIMALS_FEAR_DINOS = "animalsFearDinos";
    public static final String CUSTOM_MAIN_MENU = "customMainMenu";
    public static final String FEATHERED_DEINONYCHUS = "featheredDeinonychus";
    public static final String FEATHERED_GALLIMIMUS = "featheredGallimimus";
    public static final String FEATHERED_COMPSOGNATHUS = "featheredCompsognathus";
    public static final String QUILLED_TRICERATOPS = "quilledTriceratops";
    public static final String FEATHERED_VELOCIRAPTOR = "featheredVelociraptor";
    public static final String FEATHERED_THERIZINOSAURUS = "featheredTherizinosaurus";
    public static final String FEATHERED_DRYOSAURUS = "featheredDryosaurus";
    public static final String FEATHERED_ORNITHOLESTES = "featheredOrnitholestes";
    public static final String HELMET_OVERLAYS = "helmetOverlays";
    public static final String FLYING_TARGET_MAX_HEIGHT = "flyingTargetMaxHeight";
    public static final String MACHINES_REQUIRE_ENERGY = "machinesRequireEnergy";
    public static final String MACHINE_MAX_ENERGY = "machineMaxEnergy";
    public static final String MACHINE_TRANSFER_RATE = "machineTransferRate";
    public static final String MACHINE_ENERGY_USAGE = "machineEnergyUsage";
    public static final String FERN_TICK_RATE = "fernTickRate";

    @ExpectPlatform
    public static boolean isEnabled(String field) {
        return false;
    }

    @ExpectPlatform
    public static int getInt(String field) {
        return 0;
    }
}
