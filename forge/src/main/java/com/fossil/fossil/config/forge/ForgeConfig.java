package com.fossil.fossil.config.forge;

import com.fossil.fossil.Fossil;
import net.minecraftforge.common.ForgeConfigSpec;

public class ForgeConfig {

    public static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
    public static final ForgeConfigSpec SPEC;
    public static final ForgeConfigSpec.BooleanValue GENERATE_PREHISTORIC_TREES;
    public static final ForgeConfigSpec.BooleanValue GENERATE_HELL_SHIPS;
    public static final ForgeConfigSpec.BooleanValue GENERATE_ACADEMY;
    public static final ForgeConfigSpec.BooleanValue GENERATE_TEMPLE;
    public static final ForgeConfigSpec.BooleanValue GENERATE_FOSSILS;
    public static final ForgeConfigSpec.BooleanValue GENERATE_PERMAFROST;
    public static final ForgeConfigSpec.BooleanValue GENERATE_VOLCANIC_ROCK;
    public static final ForgeConfigSpec.BooleanValue GENERATE_AZTEC_WEAPON_SHOPS;
    public static final ForgeConfigSpec.BooleanValue GENERATE_MOAI;
    public static final ForgeConfigSpec.BooleanValue GENERATE_TAR_SITES;
    public static final ForgeConfigSpec.BooleanValue GENERATE_FOSSIL_SITES;
    public static final ForgeConfigSpec.BooleanValue GENERATE_VOLCANO_BIOME;
    public static final ForgeConfigSpec.IntValue VOLCANO_BIOME_RARITY;
    public static final ForgeConfigSpec.IntValue FOSSIL_ORE_RARITY;
    public static final ForgeConfigSpec.IntValue PERMAFROST_RARITY;
    public static final ForgeConfigSpec.IntValue HELL_SHIP_SPACING;
    public static final ForgeConfigSpec.IntValue HELL_SHIP_SEPERATION;
    public static final ForgeConfigSpec.IntValue TAR_SITE_RARITY;
    public static final ForgeConfigSpec.IntValue FOSSIL_SITE_RARITY;
    public static final ForgeConfigSpec.IntValue MOAI_RARITY;
    public static final ForgeConfigSpec.IntValue AZTEC_WEAPON_SHOP_RARITY;
    public static final ForgeConfigSpec.IntValue TEMPLE_RARITY;
    public static final ForgeConfigSpec.IntValue ACADEMY_RARITY;
    public static final ForgeConfigSpec.BooleanValue SPAWN_TAR_SLIMES;
    public static final ForgeConfigSpec.BooleanValue SPAWN_NAUTILUS;
    public static final ForgeConfigSpec.BooleanValue SPAWN_COELACANTH;
    public static final ForgeConfigSpec.BooleanValue SPAWN_ALLIGATOR_GAR;
    public static final ForgeConfigSpec.BooleanValue SPAWN_STURGEON;
    public static final ForgeConfigSpec.IntValue TAR_SLIMES_SPAWN_RATE;
    public static final ForgeConfigSpec.IntValue NAUTILUS_SPAWN_RATE;
    public static final ForgeConfigSpec.IntValue COELACANTH_SPAWN_RATE;
    public static final ForgeConfigSpec.IntValue ALLIGATOR_GAR_SPAWN_RATE;
    public static final ForgeConfigSpec.IntValue STURGEON_SPAWN_RATE;
    public static final ForgeConfigSpec.BooleanValue HEALING_DINOS;
    public static final ForgeConfigSpec.BooleanValue STARVING_DINOS;
    public static final ForgeConfigSpec.BooleanValue BREEDING_DINOS;
    public static final ForgeConfigSpec.BooleanValue EGGS_LIKE_CHICKENS;
    public static final ForgeConfigSpec.BooleanValue WHIP_TO_TAME_DINO;
    public static final ForgeConfigSpec.BooleanValue DINO_UPDATE_DELAY;
    public static final ForgeConfigSpec.BooleanValue PREGNANCY_DURATION;
    public static final ForgeConfigSpec.BooleanValue DINOS_BREAK_BLOCKS;
    public static final ForgeConfigSpec.BooleanValue DINOS_EAT_MODDED_MOBS;
    public static final ForgeConfigSpec.BooleanValue ANIMALS_FEAR_DINOS;
    public static final ForgeConfigSpec.BooleanValue CUSTOM_MAIN_MENU;
    public static final ForgeConfigSpec.BooleanValue FEATHERED_DEINONYCHUS;
    public static final ForgeConfigSpec.BooleanValue FEATHERED_GALLIMIMUS;
    public static final ForgeConfigSpec.BooleanValue FEATHERED_COMPSOGNATHUS;
    public static final ForgeConfigSpec.BooleanValue QUILLED_TRICERATOPS;
    public static final ForgeConfigSpec.BooleanValue FEATHERED_VELOCIRAPTOR;
    public static final ForgeConfigSpec.BooleanValue FEATHERED_THERIZINOSAURUS;
    public static final ForgeConfigSpec.BooleanValue FEATHERED_DRYOSAURUS;
    public static final ForgeConfigSpec.BooleanValue FEATHERED_ORNITHOLESTES;
    public static final ForgeConfigSpec.BooleanValue HELMET_OVERLAYS;
    public static final ForgeConfigSpec.IntValue FLYING_TARGET_MAX_HEIGHT;
    public static final ForgeConfigSpec.BooleanValue MACHINES_REQUIRE_ENERGY;
    public static final ForgeConfigSpec.IntValue MACHINE_MAX_ENERGY;
    public static final ForgeConfigSpec.IntValue MACHINE_TRANSFER_RATE;
    public static final ForgeConfigSpec.IntValue MACHINE_ENERGY_USAGE;
    public static final ForgeConfigSpec.IntValue FERN_TICK_RATE;

    static {
        FERN_TICK_RATE = intEntry("How often ferns try to grow. Higher number = less growth", "fernTickRate", 2, 1, 1000000);
        CUSTOM_MAIN_MENU = boolEntry("True if Custom Main Menu is enabled", "customMainMenu", true);
        HELMET_OVERLAYS = boolEntry("True if skull helmet and ancient helmet render overlays like vanilla pumpkin", "helmetOverlays", true);
        //BUILDER.push("generation");
        GENERATE_PREHISTORIC_TREES = boolEntry("True if Aztec Temples are to generate naturally", "generatePrehistoricTrees", false);
        GENERATE_HELL_SHIPS = boolEntry("True if Hell Ships are to generate naturally", "generateHellShips", true);
        GENERATE_ACADEMY = boolEntry("True if Desert Academies are to generate naturally", "generateAcademy", true);
        GENERATE_TEMPLE = boolEntry("True if Aztec Temples are to generate naturally", "generateTemple", true);
        GENERATE_FOSSILS = boolEntry("True if Fossil Ores are to generate naturally", "generateFossils", true);
        GENERATE_PERMAFROST = boolEntry("True if Permafrost Ore is to generate naturally", "generatePermafrost", true);
        GENERATE_VOLCANIC_ROCK = boolEntry("True if Volcanic Rock is to generate naturally", "generateVolcanicRock", true);
        GENERATE_AZTEC_WEAPON_SHOPS = boolEntry("True if Aztec Weapon Shops are to generate naturally", "generateAztecWeaponShops", true);
        GENERATE_MOAI = boolEntry("True if Moai are to generate naturally", "generateMoai", true);
        GENERATE_TAR_SITES = boolEntry("True if Tar Dig Sites are to generate naturally", "generateMoai", true);
        GENERATE_FOSSIL_SITES = boolEntry("True if Fossil Dig Sites are to generate naturally", "generateFossilSites", true);
        GENERATE_VOLCANO_BIOME = boolEntry("Whether to generate volcano biomes or not", "generateVolcanoBiome", true);
        FOSSIL_ORE_RARITY = intEntry("Rarity of Fossil ore. Higher number = more common", "fossilOreRarity", 38, 1, 100000000);
        PERMAFROST_RARITY = intEntry("Rarity of Permafrost ore. Higher number = more common", "permafrostRarity", 4, 1, 100000000);
        HELL_SHIP_SPACING = intEntry("Maximum number of chunks between Ship Structures", "hellShipSpacing", 24, 1, 100000000);
        HELL_SHIP_SEPERATION = intEntry("Minimum number of chunks between Ship Structures", "hellShipSeperation", 5, 1, 100000000);
        TAR_SITE_RARITY = intEntry("Rarity of Tar Dig Site Structure. Higher number = more rare", "tarSiteRarity", 100, 1, 100000000);
        FOSSIL_SITE_RARITY = intEntry("Rarity of Fossil Dig Site Structure. Higher number = more rare", "fossilSiteRarity", 900, 1, 100000000);
        MOAI_RARITY = intEntry("Rarity of Moai Structure. Higher number = more rare", "moaiRarity", 400, 1, 100000000);
        AZTEC_WEAPON_SHOP_RARITY = intEntry("Rarity of Aztec Weapon Shop Structure. Higher number = more rare", "aztecWeaponShopRarity", 400, 1, 100000000);
        TEMPLE_RARITY = intEntry("Rarity of Aztec Temple Structure. Higher number = more rare", "templeRarity", 500, 1, 100000000);
        ACADEMY_RARITY = intEntry("Rarity of Desert Academy Structure. Higher number = more rare", "academyRarity", 3500, 1, 100000000);
        VOLCANO_BIOME_RARITY = intEntry("Volcano Spawn Weight. Higher number = more common", "volcanoBiomeRarity", 1, 1, 10000);
        //BUILDER.pop();
        //BUILDER.push("Spawn Config");
        SPAWN_TAR_SLIMES = boolEntry("True if Tar Slimes are to spawn naturally in tar pits", "spawnTarSlimes", true);
        SPAWN_NAUTILUS = boolEntry("True if Nautilus are to spawn naturally in oceans", "spawnNautilus", true);
        SPAWN_COELACANTH = boolEntry("True if Coelacanths are to spawn naturally in oceans", "spawnCoelacanth", true);
        SPAWN_ALLIGATOR_GAR = boolEntry("True if Alligator Gars are to spawn naturally in swamps", "spawnAlligatorGar", true);
        SPAWN_STURGEON = boolEntry("True if Sturgeons are to spawn naturally in rivers", "spawnSturgeon", true);
        TAR_SLIMES_SPAWN_RATE = intEntry("True if Tar Slimes are to spawn naturally in tar pits", "tarSlimesSpawnRate", 75, 1, 100000000);
        NAUTILUS_SPAWN_RATE = intEntry("True if Nautilus are to spawn naturally in oceans", "nautilusSpawnRate", 6, 1, 100000000);
        COELACANTH_SPAWN_RATE = intEntry("True if Coelacanths are to spawn naturally in oceans", "coelacanthSpawnRate", 4, 1, 100000000);
        ALLIGATOR_GAR_SPAWN_RATE = intEntry("True if Alligator Gars are to spawn naturally in swamps", "alligatorGarSpawnRate", 3, 1, 100000000);
        STURGEON_SPAWN_RATE = intEntry("True if Sturgeons are to spawn naturally in rivers", "sturgeonSpawnRate", 4, 1, 100000000);
        //BUILDER.pop();
        //BUILDER.push("Dino Config");
        HEALING_DINOS = boolEntry("True if Dinosaurs can heal with food", "healingDinos", true);
        STARVING_DINOS = boolEntry("True if Dinosaurs have hunger", "starvingDinos", true);
        BREEDING_DINOS = boolEntry("True if Dinosaurs should breed", "breedingDinos", true);
        EGGS_LIKE_CHICKENS = boolEntry("True if Dinosaurs should create item eggs instead of entities", "eggsLikeChickens", true);
        WHIP_TO_TAME_DINO = boolEntry("True if Whips can be used to tame some dinosaurs", "whipToTameDino", true);
        FLYING_TARGET_MAX_HEIGHT = intEntry("Maximum height that flying creatures should soar to", "flyingTargetMaxHeight", 128, 1, 512);
        DINO_UPDATE_DELAY = boolEntry("Dinosaurs will conduct expensive CPU operations like looking for plants or feeders, once every this number of ticks(with added standard deviation for servers)", "dinoUpdateDelay", true);
        PREGNANCY_DURATION = boolEntry("How long mammal pregnancies last, in ticks", "pregnancyDuration", true);
        DINOS_BREAK_BLOCKS = boolEntry("True if certain Dinosaurs can break blocks weaker than iron", "dinosBreakBlocks", true);
        DINOS_EAT_MODDED_MOBS = boolEntry("True if Dinosaurs can eat non-vanilla mobs", "dinosEatModdedMobs", true);
        ANIMALS_FEAR_DINOS = boolEntry("True if vanilla animals should run away from Dinosaurs", "animalsFearDinos", true);
        FEATHERED_DEINONYCHUS = boolEntry("True if Deinonychus is feathered", "featheredDeinonychus", true);
        FEATHERED_GALLIMIMUS = boolEntry("True if Gallimimus is feathered", "featheredGallimimus", true);
        FEATHERED_COMPSOGNATHUS = boolEntry("True if Compsognathus should be represented with plumage", "featheredCompsognathus", true);
        QUILLED_TRICERATOPS = boolEntry("True if Triceratops should have quills like one of its distant relatives", "quilledTriceratops", false);
        FEATHERED_VELOCIRAPTOR = boolEntry("True if Velociraptor is feathered", "featheredVelociraptor", true);
        FEATHERED_THERIZINOSAURUS = boolEntry("True if Therizinosaurus should be represented with plumage", "featheredTherizinosaurus", true);
        FEATHERED_DRYOSAURUS = boolEntry("True if Dryosaurus should be represented with plumage", "featheredDryosaurus", false);
        FEATHERED_ORNITHOLESTES = boolEntry("True if Ornitholestes is feathered", "featheredOrnitholestes", true);
        //BUILDER.pop();
        //BUILDER.push("Machine Config");
        MACHINES_REQUIRE_ENERGY = boolEntry("True if machines require Redstone Flux(RF) to operate", "machinesRequireEnergy", false);
        MACHINE_MAX_ENERGY = intEntry("Max stored Redstone Flux(RF) machines can have", "machineMaxEnergy", 1000, 1, 1000000);
        MACHINE_TRANSFER_RATE = intEntry("Max Redstone Flux(RF) machines can transfer per tick", "machineTransferRate", 10, 1, 1000000);
        MACHINE_ENERGY_USAGE = intEntry("How much Redstone Flux(RF) machines consume per tick", "machineEnergyUsage", 1, 1, 1000000);
        //BUILDER.pop();
        SPEC = BUILDER.build();
    }

    private static ForgeConfigSpec.BooleanValue boolEntry(String comment, String path, boolean defaultValue) {
        return BUILDER.comment(comment).translation(Fossil.MOD_ID + ".midnightconfig." + path).define(path, defaultValue);
    }

    private static ForgeConfigSpec.IntValue intEntry(String comment, String path, int defaultValue, int min, int max) {
        return BUILDER.comment(comment).translation(Fossil.MOD_ID + ".midnightconfig." + path).defineInRange(path, defaultValue, min, max);
    }

    private ForgeConfig() {
    }

    public static boolean isRuleEnabled(ForgeConfigSpec.BooleanValue rule) {
        return Boolean.TRUE.equals(rule.get());
    }
}
