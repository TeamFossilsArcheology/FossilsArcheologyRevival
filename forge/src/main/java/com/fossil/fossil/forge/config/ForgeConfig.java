package com.fossil.fossil.forge.config;

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
    public static final ForgeConfigSpec.IntValue HELL_SHIP_RARITY;
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
    public static final ForgeConfigSpec.BooleanValue FEATHERED_TRICERATOPS;
    public static final ForgeConfigSpec.BooleanValue FEATHERED_VELOCIRAPTOR;
    public static final ForgeConfigSpec.BooleanValue FEATHERED_THERIZINOSAURUS;
    public static final ForgeConfigSpec.BooleanValue FEATHERED_DRYOSAURUS;
    public static final ForgeConfigSpec.BooleanValue FEATHERED_ORNITHOLESTES;
    public static final ForgeConfigSpec.BooleanValue HELMET_OVERLAY;
    public static final ForgeConfigSpec.IntValue FLYING_TARGET_MAX_HEIGHT;
    public static final ForgeConfigSpec.BooleanValue MACHINES_REQUIRE_ENERGY;
    public static final ForgeConfigSpec.IntValue MACHINE_MAX_ENERGY;
    public static final ForgeConfigSpec.IntValue MACHINE_TRANSFER_RATE;
    public static final ForgeConfigSpec.IntValue MACHINE_ENERGY_USAGE;
    public static final ForgeConfigSpec.IntValue FERN_TICK_RATE;

    static {
        FERN_TICK_RATE = BUILDER
                .comment("How often ferns try to grow. Higher number = less growth")
                .defineInRange("fernTickRate", 2, 1, 1000000);
        CUSTOM_MAIN_MENU = BUILDER
                .comment("True if Custom Main Menu is enabled")
                .define("customMainMenu", true);
        HELMET_OVERLAY = BUILDER
                .comment("True if skull helmet and ancient helmet render overlays like vanilla pumpkin")
                .define("helmetOverlay", true);
        BUILDER.push("generation");
        GENERATE_PREHISTORIC_TREES = BUILDER
                .comment("True if Aztec Temples are to generate naturally")
                .define("generatePrehistoricTrees", false);
        GENERATE_HELL_SHIPS = BUILDER
                .comment("True if Hell Ships are to generate naturally")
                .define("generateHellShips", true);
        GENERATE_ACADEMY = BUILDER
                .comment("True if Desert Academies are to generate naturally")
                .define("generateAcademy", true);
        GENERATE_TEMPLE = BUILDER
                .comment("True if Aztec Temples are to generate naturally")
                .define("generateTemple", true);
        GENERATE_FOSSILS = BUILDER
                .comment("True if Fossil Ores are to generate naturally")
                .define("generateFossils", true);
        GENERATE_PERMAFROST = BUILDER
                .comment("True if Permafrost Ore is to generate naturally")
                .define("generatePermafrost", true);
        GENERATE_VOLCANIC_ROCK = BUILDER
                .comment("True if Volcanic Rock is to generate naturally")
                .define("generateVolcanicRock", true);
        GENERATE_AZTEC_WEAPON_SHOPS = BUILDER
                .comment("True if Aztec Weapon Shops are to generate naturally")
                .define("generateAztecWeaponShops", true);
        GENERATE_MOAI = BUILDER
                .comment("True if Moai are to generate naturally")
                .define("generateMoai", true);
        GENERATE_TAR_SITES = BUILDER
                .comment("True if Tar Dig Sites are to generate naturally")
                .define("generateMoai", true);
        GENERATE_FOSSIL_SITES = BUILDER
                .comment("True if Fossil Dig Sites are to generate naturally")
                .define("generateFossilSites", true);
        GENERATE_VOLCANO_BIOME = BUILDER
                .comment("Whether to generate volcano biomes or not")
                .define("generateVolcanoBiome", true);
        FOSSIL_ORE_RARITY = BUILDER
                .comment("Rarity of Fossil ore. Higher number = more common")
                .defineInRange("fossilOreRarity", 38, 1, 100000000);
        PERMAFROST_RARITY = BUILDER
                .comment("Rarity of Permafrost ore. Higher number = more common")
                .defineInRange("permafrostRarity", 4, 1, 100000000);
        HELL_SHIP_RARITY = BUILDER
                .comment("Rarity of Hell Ship Structure. Higher number = more rare")
                .defineInRange("hellShipRarity", 1000, 1, 100000000);
        TAR_SITE_RARITY = BUILDER
                .comment("Rarity of Tar Dig Site Structure. Higher number = more rare")
                .defineInRange("tarSiteRarity", 900, 1, 100000000);
        FOSSIL_SITE_RARITY = BUILDER
                .comment("Rarity of Fossil Dig Site Structure. Higher number = more rare")
                .defineInRange("fossilSiteRarity", 900, 1, 100000000);
        MOAI_RARITY = BUILDER
                .comment("Rarity of Moai Structure. Higher number = more rare")
                .defineInRange("moaiRarity", 400, 1, 100000000);
        AZTEC_WEAPON_SHOP_RARITY = BUILDER
                .comment("Rarity of Aztec Weapon Shop Structure. Higher number = more rare")
                .defineInRange("aztecWeaponShopRarity", 400, 1, 100000000);
        TEMPLE_RARITY = BUILDER
                .comment("Rarity of Aztec Temple Structure. Higher number = more rare")
                .defineInRange("templeRarity", 500, 1, 100000000);
        ACADEMY_RARITY = BUILDER
                .comment("Rarity of Desert Academy Structure. Higher number = more rare")
                .defineInRange("academyRarity", 3500, 1, 100000000);
        VOLCANO_BIOME_RARITY = BUILDER
                .comment("Volcano Spawn Weight. Higher number = more common")
                .defineInRange("volcanoBiomeRarity", 1, 1, 10000);
        BUILDER.pop();
        BUILDER.push("Spawn Config");
        SPAWN_TAR_SLIMES = BUILDER
                .comment("True if Tar Slimes are to spawn naturally in tar pits")
                .define("spawnTarSlimes", true);
        SPAWN_NAUTILUS = BUILDER
                .comment("True if Nautilus are to spawn naturally in oceans")
                .define("spawnNautilus", true);
        SPAWN_COELACANTH = BUILDER
                .comment("True if Coelacanths are to spawn naturally in oceans")
                .define("spawnCoelacanth", true);
        SPAWN_ALLIGATOR_GAR = BUILDER
                .comment("True if Alligator Gars are to spawn naturally in swamps")
                .define("spawnAlligatorGar", true);
        SPAWN_STURGEON = BUILDER
                .comment("True if Sturgeons are to spawn naturally in rivers")
                .define("spawnSturgeon", true);
        TAR_SLIMES_SPAWN_RATE = BUILDER
                .comment("True if Tar Slimes are to spawn naturally in tar pits")
                .defineInRange("tarSlimesSpawnRate", 75, 1, 100000000);
        NAUTILUS_SPAWN_RATE = BUILDER
                .comment("True if Nautilus are to spawn naturally in oceans")
                .defineInRange("nautilusSpawnRate", 6, 1, 100000000);
        COELACANTH_SPAWN_RATE = BUILDER
                .comment("True if Coelacanths are to spawn naturally in oceans")
                .defineInRange("coelacanthSpawnRate", 4, 1, 100000000);
        ALLIGATOR_GAR_SPAWN_RATE = BUILDER
                .comment("True if Alligator Gars are to spawn naturally in swamps")
                .defineInRange("alligatorGarSpawnRate", 3, 1, 100000000);
        STURGEON_SPAWN_RATE = BUILDER
                .comment("True if Sturgeons are to spawn naturally in rivers")
                .defineInRange("sturgeonSpawnRate", 4, 1, 100000000);
        BUILDER.pop();
        BUILDER.push("Dino Config");
        HEALING_DINOS = BUILDER
                .comment("True if Dinosaurs can heal with food")
                .define("healingDinos", true);
        STARVING_DINOS = BUILDER
                .comment("True if Dinosaurs have hunger")
                .define("starvingDinos", true);
        BREEDING_DINOS = BUILDER
                .comment("True if Dinosaurs should breed")
                .define("breedingDinos", true);
        EGGS_LIKE_CHICKENS = BUILDER
                .comment("True if Dinosaurs should create item eggs instead of entities")
                .define("eggsLikeChickens", true);
        WHIP_TO_TAME_DINO = BUILDER
                .comment("True if Whips can be used to tame some dinosaurs")
                .define("whipToTameDino", true);
        FLYING_TARGET_MAX_HEIGHT = BUILDER
                .comment("Maximum height that flying creatures should soar to")
                .defineInRange("flyingTargetMaxHeight", 128, 1, 512);
        DINO_UPDATE_DELAY = BUILDER
                .comment("Dinosaurs will conduct expensive CPU operations like looking for plants or feeders, once every this number of ticks(with added standard deviation for servers)")
                .define("dinoUpdateDelay", true);
        PREGNANCY_DURATION = BUILDER
                .comment("How long mammal pregnancies last, in ticks")
                .define("pregnancyDuration", true);
        DINOS_BREAK_BLOCKS = BUILDER
                .comment("True if certain Dinosaurs can break blocks weaker than iron")
                .define("dinosBreakBlocks", true);
        DINOS_EAT_MODDED_MOBS = BUILDER
                .comment("True if Dinosaurs can eat non-vanilla mobs")
                .define("dinosEatModdedMobs", true);
        ANIMALS_FEAR_DINOS = BUILDER
                .comment("True if vanilla animals should run away from Dinosaurs")
                .define("animalsFearDinos", true);
        FEATHERED_DEINONYCHUS = BUILDER
                .comment("True if Deinonychus is feathered")
                .define("featheredDeinonychus", true);
        FEATHERED_GALLIMIMUS = BUILDER
                .comment("True if Gallimimus is feathered")
                .define("featheredGallimimus", true);
        FEATHERED_COMPSOGNATHUS = BUILDER
                .comment("True if Compsognathus should be represented with plumage")
                .define("featheredCompsognathus", true);
        FEATHERED_TRICERATOPS = BUILDER
                .comment("True if Triceratops should have quills like one of its distant relatives")
                .define("featheredTriceratops", true);
        FEATHERED_VELOCIRAPTOR = BUILDER
                .comment("True if Velociraptor is feathered")
                .define("featheredVelociraptor", true);
        FEATHERED_THERIZINOSAURUS = BUILDER
                .comment("True if Therizinosaurus should be represented with plumage")
                .define("featheredTherizinosaurus", true);
        FEATHERED_DRYOSAURUS = BUILDER
                .comment("True if Dryosaurus should be represented with plumage")
                .define("featheredDryosaurus", true);
        FEATHERED_ORNITHOLESTES = BUILDER
                .comment("True if Ornitholestes is feathered")
                .define("featheredOrnitholestes", true);
        BUILDER.pop();
        BUILDER.push("Machine Config");
        MACHINES_REQUIRE_ENERGY = BUILDER
                .comment("True if machines require Redstone Flux(RF) to operate")
                .define("machinesRequireEnergy", false);
        MACHINE_MAX_ENERGY = BUILDER
                .comment("Max stored Redstone Flux(RF) machines can have")
                .defineInRange("machineMaxEnergy", 1000, 1, 1000000);
        MACHINE_TRANSFER_RATE = BUILDER
                .comment("Max Redstone Flux(RF) machines can transfer per tick")
                .defineInRange("machineTransferRate", 10, 1, 1000000);
        MACHINE_ENERGY_USAGE = BUILDER
                .comment("How much Redstone Flux(RF) machines consume per tick")
                .defineInRange("machineEnergyUsage", 1, 1, 1000000);
        BUILDER.pop();
        SPEC = BUILDER.build();
    }


    private ForgeConfig() {
    }

    public static boolean isRuleEnabled(ForgeConfigSpec.BooleanValue rule) {
        return Boolean.TRUE.equals(rule.get());
    }
}
