package com.github.teamfossilsarcheology.fossil.config.forge;

import com.github.teamfossilsarcheology.fossil.Fossil;
import com.github.teamfossilsarcheology.fossil.config.FossilConfig;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import net.minecraftforge.common.ForgeConfigSpec;

import java.util.Map;
//TODO: Use MidnightLib in the future
public class ForgeConfig {

    public static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
    public static final ForgeConfigSpec SPEC;
    public static final ForgeConfigSpec.BooleanValue GENERATE_ACADEMY;
    public static final ForgeConfigSpec.BooleanValue GENERATE_AZTEC_WEAPON_SHOPS;
    public static final ForgeConfigSpec.BooleanValue GENERATE_HELL_SHIPS;
    public static final ForgeConfigSpec.IntValue HELL_SHIP_SPACING;
    public static final ForgeConfigSpec.IntValue HELL_SHIP_SEPARATION;
    public static final ForgeConfigSpec.BooleanValue GENERATE_MOAI;
    public static final ForgeConfigSpec.IntValue MOAI_RARITY;
    public static final ForgeConfigSpec.BooleanValue GENERATE_TAR_PITS;
    public static final ForgeConfigSpec.IntValue Tar_PIT_RARITY;
    public static final ForgeConfigSpec.BooleanValue GENERATE_TEMPLE;
    public static final ForgeConfigSpec.BooleanValue GENERATE_FOSSILS;
    public static final ForgeConfigSpec.IntValue FOSSIL_ORE_RARITY;
    public static final ForgeConfigSpec.BooleanValue GENERATE_PERMAFROST;
    public static final ForgeConfigSpec.IntValue PERMAFROST_RARITY;
    public static final ForgeConfigSpec.BooleanValue GENERATE_VOLCANIC_ROCK;
    public static final ForgeConfigSpec.BooleanValue GENERATE_VOLCANO_BIOME;
    public static final ForgeConfigSpec.IntValue VOLCANO_BIOME_RARITY;

    public static final ForgeConfigSpec.BooleanValue SPAWN_ALLIGATOR_GAR;
    public static final ForgeConfigSpec.IntValue ALLIGATOR_GAR_SPAWN_WEIGHT;
    public static final ForgeConfigSpec.BooleanValue SPAWN_COELACANTH;
    public static final ForgeConfigSpec.IntValue COELACANTH_SPAWN_WEIGHT;
    public static final ForgeConfigSpec.BooleanValue SPAWN_NAUTILUS;
    public static final ForgeConfigSpec.IntValue NAUTILUS_SPAWN_WEIGHT;
    public static final ForgeConfigSpec.BooleanValue SPAWN_STURGEON;
    public static final ForgeConfigSpec.IntValue STURGEON_SPAWN_WEIGHT;
    public static final ForgeConfigSpec.BooleanValue SPAWN_TAR_SLIMES;
    public static final ForgeConfigSpec.IntValue TAR_SLIMES_SPAWN_RATE;

    public static final ForgeConfigSpec.BooleanValue HEALING_DINOS;
    public static final ForgeConfigSpec.BooleanValue ENABLE_HUNGER;
    public static final ForgeConfigSpec.BooleanValue ENABLE_STARVATION;
    public static final ForgeConfigSpec.BooleanValue BREEDING_DINOS;
    public static final ForgeConfigSpec.BooleanValue EGGS_LIKE_CHICKENS;
    public static final ForgeConfigSpec.BooleanValue WHIP_TO_TAME_DINO;
    public static final ForgeConfigSpec.IntValue PREGNANCY_DURATION;
    public static final ForgeConfigSpec.BooleanValue DINOS_EAT_BLOCKS;
    public static final ForgeConfigSpec.BooleanValue DINOS_BREAK_BLOCKS;
    public static final ForgeConfigSpec.DoubleValue BLOCK_BREAK_HARDNESS;
    public static final ForgeConfigSpec.BooleanValue DINOS_EAT_MODDED_MOBS;
    public static final ForgeConfigSpec.BooleanValue ANIMALS_FEAR_DINOS;
    public static final ForgeConfigSpec.IntValue FLYING_TARGET_MAX_HEIGHT;
    public static final ForgeConfigSpec.BooleanValue ANU_BLOCK_PLACING;

    public static final ForgeConfigSpec.BooleanValue CUSTOM_MAIN_MENU;
    public static final ForgeConfigSpec.BooleanValue HELMET_OVERLAYS;
    public static final ForgeConfigSpec.IntValue CULTURE_VAT_FAIL_CHANCE;
    public static final ForgeConfigSpec.BooleanValue MACHINES_REQUIRE_ENERGY;
    public static final ForgeConfigSpec.IntValue MACHINE_MAX_ENERGY;
    public static final ForgeConfigSpec.IntValue MACHINE_TRANSFER_RATE;
    public static final ForgeConfigSpec.IntValue MACHINE_ENERGY_USAGE;
    public static final ForgeConfigSpec.IntValue FERN_TICK_RATE;
    public static final Map<String, ForgeConfigSpec.BooleanValue> MAPPED_BOOLS = new Object2ObjectOpenHashMap<>();
    public static final Map<String, ForgeConfigSpec.IntValue> MAPPED_INTS = new Object2ObjectOpenHashMap<>();
    public static final Map<String, ForgeConfigSpec.DoubleValue> MAPPED_DOUBLES = new Object2ObjectOpenHashMap<>();

    static {
        BUILDER.push("Generation Config");
        GENERATE_HELL_SHIPS = boolEntry("True if Hell Ships are to generate naturally", FossilConfig.GENERATE_HELL_SHIPS, true);
        GENERATE_ACADEMY = boolEntry("True if Desert Academies are to generate naturally", FossilConfig.GENERATE_ACADEMY, true);
        GENERATE_TEMPLE = boolEntry("True if Aztec Temples are to generate naturally", FossilConfig.GENERATE_TEMPLE, true);
        GENERATE_FOSSILS = boolEntry("True if Fossil Ores are to generate naturally", FossilConfig.GENERATE_FOSSILS, true);
        GENERATE_PERMAFROST = boolEntry("True if Permafrost Ore is to generate naturally", FossilConfig.GENERATE_PERMAFROST, true);
        GENERATE_VOLCANIC_ROCK = boolEntry("True if Volcanic Rock is to generate naturally", FossilConfig.GENERATE_VOLCANIC_ROCK, true);
        GENERATE_AZTEC_WEAPON_SHOPS = boolEntry("True if Aztec Weapon Shops are to generate naturally", FossilConfig.GENERATE_AZTEC_WEAPON_SHOPS, true);
        GENERATE_MOAI = boolEntry("True if Moai Statues are to generate naturally", FossilConfig.GENERATE_MOAI, true);
        GENERATE_TAR_PITS = boolEntry("True if Tar Pits are to generate naturally. This only affects the small tar lakes without tent", FossilConfig.GENERATE_TAR_PITS, true);
        GENERATE_VOLCANO_BIOME = boolEntry("True if Volcano Biomes are to generate naturally", FossilConfig.GENERATE_VOLCANO_BIOME, true);
        FOSSIL_ORE_RARITY = intEntry("Rarity of Fossil ore. Higher number = more tries per chunk", FossilConfig.FOSSIL_ORE_RARITY, 13, 1, 500);
        PERMAFROST_RARITY = intEntry("Rarity of Permafrost. Higher number = more tries per chunk", FossilConfig.PERMAFROST_RARITY, 7, 1, 500);
        HELL_SHIP_SPACING = intEntry("Maximum number of chunks between Ship Structures", FossilConfig.HELL_SHIP_SPACING, 24, 1, 100000000);
        HELL_SHIP_SEPARATION = intEntry("Minimum number of chunks between Ship Structures", FossilConfig.HELL_SHIP_SEPARATION, 5, 1, 100000000);
        Tar_PIT_RARITY = intEntry("Rarity of Tar Pits. Higher number = more rare", FossilConfig.TAR_PIT_RARITY, 900, 1, 100000000);
        MOAI_RARITY = intEntry("Rarity of Moai Statues. Higher number = more rare", FossilConfig.MOAI_RARITY, 400, 1, 100000000);
        VOLCANO_BIOME_RARITY = intEntry("Volcano Biome Weight. Higher number = more common", FossilConfig.VOLCANO_BIOME_RARITY, 7, 1, 10000);
        BUILDER.pop();
        BUILDER.push("Spawn Config");
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
        BUILDER.pop();
        BUILDER.push("Mob Config");
        HEALING_DINOS = boolEntry("True if Dinosaurs can be healed by hand feeding it food", FossilConfig.HEALING_DINOS, true);
        ENABLE_HUNGER = boolEntry("True if Dinosaurs have hunger", FossilConfig.ENABLE_HUNGER, true);
        ENABLE_STARVATION = boolEntry("True if Dinosaurs can die of hunger", FossilConfig.ENABLE_STARVATION, false);
        BREEDING_DINOS = boolEntry("True if Dinosaurs should breed", FossilConfig.BREEDING_DINOS, true);
        EGGS_LIKE_CHICKENS = boolEntry("True if Dinosaurs should create item eggs instead of entities", FossilConfig.EGGS_LIKE_CHICKENS, false);
        WHIP_TO_TAME_DINO = boolEntry("True if Whips can be used to tame some dinosaurs", FossilConfig.WHIP_TO_TAME_DINO, true);
        FLYING_TARGET_MAX_HEIGHT = intEntry("Maximum height that flying creatures should be able to fly to", FossilConfig.FLYING_TARGET_MAX_HEIGHT, 128, 1, 512);
        PREGNANCY_DURATION = intEntry("How long mammal pregnancies last, in ticks", FossilConfig.PREGNANCY_DURATION, 10000, 1, 1000000000);
        DINOS_BREAK_BLOCKS = boolEntry("True if certain Dinosaurs can break blocks weaker than the set hardness", FossilConfig.DINOS_BREAK_BLOCKS, true);
        BLOCK_BREAK_HARDNESS = doubleEntry("Minimum hardness that a block needs in order not to break. The default is iron(5). A value of 0.4 would be enough to break leaves and glass but not dirt", FossilConfig.BLOCK_BREAK_HARDNESS, 5, 0, 100);
        DINOS_EAT_BLOCKS = boolEntry("True if herbivores can eat plant blocks", FossilConfig.DINOS_EAT_BLOCKS, true);
        DINOS_EAT_MODDED_MOBS = boolEntry("True if Dinosaurs can eat non-vanilla mobs", FossilConfig.DINOS_EAT_MODDED_MOBS, true);
        ANIMALS_FEAR_DINOS = boolEntry("True if vanilla animals should run away from dangerous Dinosaurs", FossilConfig.ANIMALS_FEAR_DINOS, true);
        ANU_BLOCK_PLACING = boolEntry("True if Anu should be able to place blocks", FossilConfig.ANU_BLOCK_PLACING, true);
        BUILDER.pop();
        BUILDER.push("Machine Config");
        CULTURE_VAT_FAIL_CHANCE = intEntry("Percentage chance that the Culture Vat will break", FossilConfig.CULTURE_VAT_FAIL_CHANCE, 10, 0, 100);
        MACHINES_REQUIRE_ENERGY = boolEntry("True if machines require Energy to operate", FossilConfig.MACHINES_REQUIRE_ENERGY, false);
        MACHINE_MAX_ENERGY = intEntry("Max stored Energy machines can have", FossilConfig.MACHINE_MAX_ENERGY, 1000, 1, 1000000);
        MACHINE_TRANSFER_RATE = intEntry("Max Energy machines can transfer per tick", FossilConfig.MACHINE_TRANSFER_RATE, 10, 1, 1000000);
        MACHINE_ENERGY_USAGE = intEntry("How much Energy machines consume per tick", FossilConfig.MACHINE_ENERGY_USAGE, 1, 1, 1000000);
        BUILDER.pop();
        CUSTOM_MAIN_MENU = boolEntry("True if the Custom Main Menu should be enabled", FossilConfig.CUSTOM_MAIN_MENU, true);
        HELMET_OVERLAYS = boolEntry("True if skull helmet and ancient helmet should render overlays like vanilla pumpkin", FossilConfig.HELMET_OVERLAYS, true);
        FERN_TICK_RATE = intEntry("How often ferns try to grow. Higher number = less growth", FossilConfig.FERN_TICK_RATE, 2, 1, 1000000);
        SPEC = BUILDER.build();
    }

    private ForgeConfig() {
    }

    private static ForgeConfigSpec.BooleanValue boolEntry(String comment, String path, boolean defaultValue) {
        ForgeConfigSpec.BooleanValue entry = BUILDER.comment(comment).translation(Fossil.MOD_ID + ".midnightconfig." + path).define(path, defaultValue);
        MAPPED_BOOLS.put(path, entry);
        return entry;
    }

    private static ForgeConfigSpec.IntValue intEntry(String comment, String path, int defaultValue, int min, int max) {
        ForgeConfigSpec.IntValue entry = BUILDER.comment(comment).translation(Fossil.MOD_ID + ".midnightconfig." + path).defineInRange(path, defaultValue, min, max);
        MAPPED_INTS.put(path, entry);
        return entry;
    }

    private static ForgeConfigSpec.DoubleValue doubleEntry(String comment, String path, double defaultValue, double min, double max) {
        ForgeConfigSpec.DoubleValue entry = BUILDER.comment(comment).translation(Fossil.MOD_ID + ".midnightconfig." + path).defineInRange(path, defaultValue, min, max);
        MAPPED_DOUBLES.put(path, entry);
        return entry;
    }
}
