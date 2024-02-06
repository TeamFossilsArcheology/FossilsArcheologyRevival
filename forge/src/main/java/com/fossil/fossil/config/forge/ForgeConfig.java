package com.fossil.fossil.config.forge;

import com.fossil.fossil.Fossil;
import com.fossil.fossil.config.FossilConfig;
import net.minecraftforge.common.ForgeConfigSpec;

import java.util.HashMap;
import java.util.Map;

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
    public static final ForgeConfigSpec.BooleanValue GENERATE_TAR_PITS;
    public static final ForgeConfigSpec.BooleanValue GENERATE_VOLCANO_BIOME;
    public static final ForgeConfigSpec.IntValue VOLCANO_BIOME_RARITY;
    public static final ForgeConfigSpec.IntValue FOSSIL_ORE_RARITY;
    public static final ForgeConfigSpec.IntValue PERMAFROST_RARITY;
    public static final ForgeConfigSpec.IntValue HELL_SHIP_SPACING;
    public static final ForgeConfigSpec.IntValue HELL_SHIP_SEPERATION;
    public static final ForgeConfigSpec.IntValue TAR_POOL_RARITY;
    public static final ForgeConfigSpec.IntValue MOAI_RARITY;
    public static final ForgeConfigSpec.BooleanValue SPAWN_TAR_SLIMES;
    public static final ForgeConfigSpec.BooleanValue SPAWN_NAUTILUS;
    public static final ForgeConfigSpec.BooleanValue SPAWN_COELACANTH;
    public static final ForgeConfigSpec.BooleanValue SPAWN_ALLIGATOR_GAR;
    public static final ForgeConfigSpec.BooleanValue SPAWN_STURGEON;
    public static final ForgeConfigSpec.IntValue TAR_SLIMES_SPAWN_RATE;
    public static final ForgeConfigSpec.IntValue ALLIGATOR_GAR_SPAWN_WEIGHT;
    public static final ForgeConfigSpec.IntValue COELACANTH_SPAWN_WEIGHT;
    public static final ForgeConfigSpec.IntValue NAUTILUS_SPAWN_WEIGHT;
    public static final ForgeConfigSpec.IntValue STURGEON_SPAWN_WEIGHT;
    public static final ForgeConfigSpec.BooleanValue HEALING_DINOS;
    public static final ForgeConfigSpec.BooleanValue STARVING_DINOS;
    public static final ForgeConfigSpec.BooleanValue BREEDING_DINOS;
    public static final ForgeConfigSpec.BooleanValue EGGS_LIKE_CHICKENS;
    public static final ForgeConfigSpec.BooleanValue WHIP_TO_TAME_DINO;
    public static final ForgeConfigSpec.IntValue DINO_UPDATE_DELAY;
    public static final ForgeConfigSpec.IntValue PREGNANCY_DURATION;
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
    public static final ForgeConfigSpec.BooleanValue ANU_BLOCK_PLACING;
    public static Map<String, ForgeConfigSpec.BooleanValue> mappedBools = new HashMap<>();
    public static Map<String, ForgeConfigSpec.IntValue> mappedInts = new HashMap<>();

    static {
        ANU_BLOCK_PLACING = boolEntry("True if Anu should be able to place blocks", FossilConfig.ANU_BLOCK_PLACING, true);
        FERN_TICK_RATE = intEntry("How often ferns try to grow. Higher number = less growth", FossilConfig.FERN_TICK_RATE, 2, 1, 1000000);
        CUSTOM_MAIN_MENU = boolEntry("True if Custom Main Menu is enabled", FossilConfig.CUSTOM_MAIN_MENU, true);
        HELMET_OVERLAYS = boolEntry("True if skull helmet and ancient helmet render overlays like vanilla pumpkin", FossilConfig.HELMET_OVERLAYS, true);
        //BUILDER.push("generation");
        GENERATE_PREHISTORIC_TREES = boolEntry("True if Aztec Temples are to generate naturally", FossilConfig.GENERATE_PREHISTORIC_TREES, false);
        GENERATE_HELL_SHIPS = boolEntry("True if Hell Ships are to generate naturally", FossilConfig.GENERATE_HELL_SHIPS, true);
        GENERATE_ACADEMY = boolEntry("True if Desert Academies are to generate naturally", FossilConfig.GENERATE_ACADEMY, true);
        GENERATE_TEMPLE = boolEntry("True if Aztec Temples are to generate naturally", FossilConfig.GENERATE_TEMPLE, true);
        GENERATE_FOSSILS = boolEntry("True if Fossil Ores are to generate naturally", FossilConfig.GENERATE_FOSSILS, true);
        GENERATE_PERMAFROST = boolEntry("True if Permafrost Ore is to generate naturally", FossilConfig.GENERATE_PERMAFROST, true);
        GENERATE_VOLCANIC_ROCK = boolEntry("True if Volcanic Rock is to generate naturally", FossilConfig.GENERATE_VOLCANIC_ROCK, true);
        GENERATE_AZTEC_WEAPON_SHOPS = boolEntry("True if Aztec Weapon Shops are to generate naturally", FossilConfig.GENERATE_AZTEC_WEAPON_SHOPS, true);
        GENERATE_MOAI = boolEntry("True if Moai are to generate naturally", FossilConfig.GENERATE_MOAI, true);
        GENERATE_TAR_PITS = boolEntry("True if Tar Pits are to generate naturally. This only affects the small tar lakes without tent", FossilConfig.GENERATE_TAR_PITS, true);
        GENERATE_VOLCANO_BIOME = boolEntry("Whether to generate volcano biomes or not", FossilConfig.GENERATE_VOLCANO_BIOME, true);
        FOSSIL_ORE_RARITY = intEntry("Rarity of Fossil ore. Higher number = more common", FossilConfig.FOSSIL_ORE_RARITY, 38, 1, 100000000);
        PERMAFROST_RARITY = intEntry("Rarity of Permafrost ore. Higher number = more common", FossilConfig.PERMAFROST_RARITY, 4, 1, 100000000);
        HELL_SHIP_SPACING = intEntry("Maximum number of chunks between Ship Structures", FossilConfig.HELL_SHIP_SPACING, 24, 1, 100000000);
        HELL_SHIP_SEPERATION = intEntry("Minimum number of chunks between Ship Structures", FossilConfig.HELL_SHIP_SEPERATION, 5, 1, 100000000);
        TAR_POOL_RARITY = intEntry("Rarity of Tar Pools. Higher number = more rare", FossilConfig.TAR_PIT_RARITY, 900, 1, 100000000);
        MOAI_RARITY = intEntry("Rarity of Moai Structure. Higher number = more rare", FossilConfig.MOAI_RARITY, 400, 1, 100000000);
        VOLCANO_BIOME_RARITY = intEntry("Volcano Spawn Weight. Higher number = more common", FossilConfig.VOLCANO_BIOME_RARITY, 1, 1, 10000);
        //BUILDER.pop();
        //BUILDER.push("Spawn Config");
        SPAWN_TAR_SLIMES = boolEntry("True if Tar Slimes are to spawn naturally in tar pits", FossilConfig.SPAWN_TAR_SLIMES, true);
        SPAWN_NAUTILUS = boolEntry("True if Nautilus are to spawn naturally in oceans", FossilConfig.SPAWN_NAUTILUS, true);
        SPAWN_COELACANTH = boolEntry("True if Coelacanths are to spawn naturally in oceans", FossilConfig.SPAWN_COELACANTH, true);
        SPAWN_ALLIGATOR_GAR = boolEntry("True if Alligator Gars are to spawn naturally in swamps", FossilConfig.SPAWN_ALLIGATOR_GAR, true);
        SPAWN_STURGEON = boolEntry("True if Sturgeons are to spawn naturally in rivers", FossilConfig.SPAWN_STURGEON, true);
        TAR_SLIMES_SPAWN_RATE = intEntry("Tar Slime Spawn Rarity. Higher number = more rare", FossilConfig.TAR_SLIMES_SPAWN_RATE, 75, 1, 100000000);
        ALLIGATOR_GAR_SPAWN_WEIGHT = intEntry("Alligator Gar Spawn Weight. Higher number = more common", FossilConfig.ALLIGATOR_GAR_SPAWN_WEIGHT, 4, 0, 100000000);
        COELACANTH_SPAWN_WEIGHT = intEntry("Coelacanth Spawn Weight. Higher number = more common", FossilConfig.COELACANTH_SPAWN_WEIGHT, 3, 0, 100000000);
        NAUTILUS_SPAWN_WEIGHT = intEntry("Nautilus Spawn Weight. Higher number = more common", FossilConfig.NAUTILUS_SPAWN_WEIGHT, 2, 0, 100000000);
        STURGEON_SPAWN_WEIGHT = intEntry("Sturgeon Spawn Weight. Higher number = more common", FossilConfig.STURGEON_SPAWN_WEIGHT, 5, 0, 100000000);
        //BUILDER.pop();
        //BUILDER.push("Dino Config");
        HEALING_DINOS = boolEntry("True if Dinosaurs can heal with food", FossilConfig.HEALING_DINOS, true);
        STARVING_DINOS = boolEntry("True if Dinosaurs have hunger", FossilConfig.STARVING_DINOS, true);
        BREEDING_DINOS = boolEntry("True if Dinosaurs should breed", FossilConfig.BREEDING_DINOS, true);
        EGGS_LIKE_CHICKENS = boolEntry("True if Dinosaurs should create item eggs instead of entities", FossilConfig.EGGS_LIKE_CHICKENS, true);
        WHIP_TO_TAME_DINO = boolEntry("True if Whips can be used to tame some dinosaurs", FossilConfig.WHIP_TO_TAME_DINO, true);
        FLYING_TARGET_MAX_HEIGHT = intEntry("Maximum height that flying creatures should soar to", FossilConfig.FLYING_TARGET_MAX_HEIGHT, 128, 1, 512);
        DINO_UPDATE_DELAY = intEntry("Dinosaurs will conduct expensive CPU operations like looking for plants or feeders, once every this number of ticks(with added standard deviation for servers)", FossilConfig.DINO_UPDATE_DELAY, 10, 1, 10000);
        PREGNANCY_DURATION = intEntry("How long mammal pregnancies last, in ticks", FossilConfig.PREGNANCY_DURATION, 10000, 1, 1000000000);
        DINOS_BREAK_BLOCKS = boolEntry("True if certain Dinosaurs can break blocks weaker than iron", FossilConfig.DINOS_BREAK_BLOCKS, true);
        DINOS_EAT_MODDED_MOBS = boolEntry("True if Dinosaurs can eat non-vanilla mobs", FossilConfig.DINOS_EAT_MODDED_MOBS, true);
        ANIMALS_FEAR_DINOS = boolEntry("True if vanilla animals should run away from Dinosaurs", FossilConfig.ANIMALS_FEAR_DINOS, true);
        FEATHERED_DEINONYCHUS = boolEntry("True if Deinonychus is feathered", FossilConfig.FEATHERED_DEINONYCHUS, true);
        FEATHERED_GALLIMIMUS = boolEntry("True if Gallimimus is feathered", FossilConfig.FEATHERED_GALLIMIMUS, true);
        FEATHERED_COMPSOGNATHUS = boolEntry("True if Compsognathus should be represented with plumage", FossilConfig.FEATHERED_COMPSOGNATHUS, true);
        QUILLED_TRICERATOPS = boolEntry("True if Triceratops should have quills like one of its distant relatives", FossilConfig.QUILLED_TRICERATOPS, false);
        FEATHERED_VELOCIRAPTOR = boolEntry("True if Velociraptor is feathered", FossilConfig.FEATHERED_VELOCIRAPTOR, true);
        FEATHERED_THERIZINOSAURUS = boolEntry("True if Therizinosaurus should be represented with plumage", FossilConfig.FEATHERED_THERIZINOSAURUS, true);
        FEATHERED_DRYOSAURUS = boolEntry("True if Dryosaurus should be represented with plumage", FossilConfig.FEATHERED_DRYOSAURUS, false);
        FEATHERED_ORNITHOLESTES = boolEntry("True if Ornitholestes is feathered", FossilConfig.FEATHERED_ORNITHOLESTES, true);
        //BUILDER.pop();
        //BUILDER.push("Machine Config");
        MACHINES_REQUIRE_ENERGY = boolEntry("True if machines require Redstone Flux(RF) to operate", FossilConfig.MACHINES_REQUIRE_ENERGY, false);
        MACHINE_MAX_ENERGY = intEntry("Max stored Redstone Flux(RF) machines can have", FossilConfig.MACHINE_MAX_ENERGY, 1000, 1, 1000000);
        MACHINE_TRANSFER_RATE = intEntry("Max Redstone Flux(RF) machines can transfer per tick", FossilConfig.MACHINE_TRANSFER_RATE, 10, 1, 1000000);
        MACHINE_ENERGY_USAGE = intEntry("How much Redstone Flux(RF) machines consume per tick", FossilConfig.MACHINE_ENERGY_USAGE, 1, 1, 1000000);
        //BUILDER.pop();
        SPEC = BUILDER.build();
    }

    private ForgeConfig() {
    }

    private static ForgeConfigSpec.BooleanValue boolEntry(String comment, String path, boolean defaultValue) {
        ForgeConfigSpec.BooleanValue entry = BUILDER.comment(comment).translation(Fossil.MOD_ID + ".midnightconfig." + path).define(path, defaultValue);
        mappedBools.put(path, entry);
        return entry;
    }

    private static ForgeConfigSpec.IntValue intEntry(String comment, String path, int defaultValue, int min, int max) {
        ForgeConfigSpec.IntValue entry = BUILDER.comment(comment).translation(Fossil.MOD_ID + ".midnightconfig." + path).defineInRange(path, defaultValue, min, max);
        mappedInts.put(path, entry);
        return entry;
    }
}
