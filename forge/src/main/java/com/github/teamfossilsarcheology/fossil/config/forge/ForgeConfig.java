package com.github.teamfossilsarcheology.fossil.config.forge;

import com.github.teamfossilsarcheology.fossil.FossilMod;
import com.github.teamfossilsarcheology.fossil.config.FossilConfig;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import net.minecraftforge.common.ForgeConfigSpec;

import java.util.Map;

//TODO: Use MidnightLib in the future
public class ForgeConfig {

    private static final Builder COMMON = new Builder();
    public static final ForgeConfigSpec COMMON_SPEC;
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

    public static final ForgeConfigSpec.BooleanValue FEATHERED_DILO;
    public static final ForgeConfigSpec.BooleanValue FEATHERED_DRYO;
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
    public static final ForgeConfigSpec.BooleanValue ANUBITE_HAS_COOLDOWN;
    public static final ForgeConfigSpec.IntValue ANUBITE_COOLDOWN;

    public static final ForgeConfigSpec.BooleanValue CUSTOM_MAIN_MENU;
    public static final ForgeConfigSpec.BooleanValue HELMET_OVERLAYS;
    public static final ForgeConfigSpec.IntValue CULTURE_VAT_FAIL_CHANCE;
    public static final ForgeConfigSpec.BooleanValue MACHINES_REQUIRE_ENERGY;
    public static final ForgeConfigSpec.IntValue MACHINE_MAX_ENERGY;
    public static final ForgeConfigSpec.IntValue MACHINE_TRANSFER_RATE;
    public static final ForgeConfigSpec.IntValue MACHINE_ENERGY_USAGE;
    public static final ForgeConfigSpec.IntValue FERN_TICK_RATE;
    private static final Builder CLIENT = new Builder();
    public static final ForgeConfigSpec CLIENT_SPEC;
    public static final Map<String, ForgeConfigSpec.BooleanValue> MAPPED_BOOLS = new Object2ObjectOpenHashMap<>();
    public static final Map<String, ForgeConfigSpec.IntValue> MAPPED_INTS = new Object2ObjectOpenHashMap<>();
    public static final Map<String, ForgeConfigSpec.DoubleValue> MAPPED_DOUBLES = new Object2ObjectOpenHashMap<>();

    static {
        COMMON.builder.push("Generation Config");
        GENERATE_HELL_SHIPS = COMMON.boolEntry("True if Hell Ships are to generate naturally", FossilConfig.GENERATE_HELL_SHIPS, true);
        GENERATE_ACADEMY = COMMON.boolEntry("True if Desert Academies are to generate naturally", FossilConfig.GENERATE_ACADEMY, true);
        GENERATE_TEMPLE = COMMON.boolEntry("True if Aztec Temples are to generate naturally", FossilConfig.GENERATE_TEMPLE, true);
        GENERATE_FOSSILS = COMMON.boolEntry("True if Fossil Ores are to generate naturally", FossilConfig.GENERATE_FOSSILS, true);
        GENERATE_PERMAFROST = COMMON.boolEntry("True if Permafrost Ore is to generate naturally", FossilConfig.GENERATE_PERMAFROST, true);
        GENERATE_VOLCANIC_ROCK = COMMON.boolEntry("True if Volcanic Rock is to generate naturally", FossilConfig.GENERATE_VOLCANIC_ROCK, true);
        GENERATE_AZTEC_WEAPON_SHOPS = COMMON.boolEntry("True if Aztec Weapon Shops are to generate naturally", FossilConfig.GENERATE_AZTEC_WEAPON_SHOPS, true);
        GENERATE_MOAI = COMMON.boolEntry("True if Moai Statues are to generate naturally", FossilConfig.GENERATE_MOAI, true);
        GENERATE_TAR_PITS = COMMON.boolEntry("True if Tar Pits are to generate naturally. This only affects the small tar lakes without tent", FossilConfig.GENERATE_TAR_PITS, true);
        GENERATE_VOLCANO_BIOME = COMMON.boolEntry("True if Volcano Biomes are to generate naturally", FossilConfig.GENERATE_VOLCANO_BIOME, true);
        FOSSIL_ORE_RARITY = COMMON.intEntry("Rarity of Fossil ore. Higher number = more tries per chunk", FossilConfig.FOSSIL_ORE_RARITY, 13, 1, 500);
        PERMAFROST_RARITY = COMMON.intEntry("Rarity of Permafrost. Higher number = more tries per chunk", FossilConfig.PERMAFROST_RARITY, 7, 1, 500);
        HELL_SHIP_SPACING = COMMON.intEntry("Maximum number of chunks between Ship Structures", FossilConfig.HELL_SHIP_SPACING, 24, 1, 100000000);
        HELL_SHIP_SEPARATION = COMMON.intEntry("Minimum number of chunks between Ship Structures", FossilConfig.HELL_SHIP_SEPARATION, 5, 1, 100000000);
        Tar_PIT_RARITY = COMMON.intEntry("Rarity of Tar Pits. Higher number = more rare", FossilConfig.TAR_PIT_RARITY, 900, 1, 100000000);
        MOAI_RARITY = COMMON.intEntry("Rarity of Moai Statues. Higher number = more rare", FossilConfig.MOAI_RARITY, 400, 1, 100000000);
        VOLCANO_BIOME_RARITY = COMMON.intEntry("Volcano Biome Weight. Higher number = more common", FossilConfig.VOLCANO_BIOME_RARITY, 7, 1, 10000);
        COMMON.builder.pop();
        COMMON.builder.push("Spawn Config");
        SPAWN_TAR_SLIMES = COMMON.boolEntry("True if Tar Slimes are to spawn naturally in tar pits", FossilConfig.SPAWN_TAR_SLIMES, true);
        SPAWN_NAUTILUS = COMMON.boolEntry("True if Nautilus are to spawn naturally in oceans", FossilConfig.SPAWN_NAUTILUS, true);
        SPAWN_COELACANTH = COMMON.boolEntry("True if Coelacanths are to spawn naturally in oceans", FossilConfig.SPAWN_COELACANTH, true);
        SPAWN_ALLIGATOR_GAR = COMMON.boolEntry("True if Alligator Gars are to spawn naturally in swamps", FossilConfig.SPAWN_ALLIGATOR_GAR, true);
        SPAWN_STURGEON = COMMON.boolEntry("True if Sturgeons are to spawn naturally in rivers", FossilConfig.SPAWN_STURGEON, true);
        TAR_SLIMES_SPAWN_RATE = COMMON.intEntry("Tar Slime Spawn Rarity. Higher number = more rare", FossilConfig.TAR_SLIMES_SPAWN_RATE, 75, 1, 100000000);
        ALLIGATOR_GAR_SPAWN_WEIGHT = COMMON.intEntry("Alligator Gar Spawn Weight. Higher number = more common", FossilConfig.ALLIGATOR_GAR_SPAWN_WEIGHT, 4, 0, 100000000);
        COELACANTH_SPAWN_WEIGHT = COMMON.intEntry("Coelacanth Spawn Weight. Higher number = more common", FossilConfig.COELACANTH_SPAWN_WEIGHT, 3, 0, 100000000);
        NAUTILUS_SPAWN_WEIGHT = COMMON.intEntry("Nautilus Spawn Weight. Higher number = more common", FossilConfig.NAUTILUS_SPAWN_WEIGHT, 2, 0, 100000000);
        STURGEON_SPAWN_WEIGHT = COMMON.intEntry("Sturgeon Spawn Weight. Higher number = more common", FossilConfig.STURGEON_SPAWN_WEIGHT, 5, 0, 100000000);
        COMMON.builder.pop();
        COMMON.builder.push("Mob Config");
        HEALING_DINOS = COMMON.boolEntry("True if Dinosaurs can be healed by hand feeding it food", FossilConfig.HEALING_DINOS, true);
        ENABLE_HUNGER = COMMON.boolEntry("True if Dinosaurs have hunger", FossilConfig.ENABLE_HUNGER, true);
        ENABLE_STARVATION = COMMON.boolEntry("True if Dinosaurs can die of hunger", FossilConfig.ENABLE_STARVATION, false);
        BREEDING_DINOS = COMMON.boolEntry("True if Dinosaurs should breed", FossilConfig.BREEDING_DINOS, true);
        EGGS_LIKE_CHICKENS = COMMON.boolEntry("True if Dinosaurs should create item eggs instead of entities", FossilConfig.EGGS_LIKE_CHICKENS, false);
        WHIP_TO_TAME_DINO = COMMON.boolEntry("True if Whips can be used to tame some dinosaurs", FossilConfig.WHIP_TO_TAME_DINO, true);
        FLYING_TARGET_MAX_HEIGHT = COMMON.intEntry("Maximum height that flying creatures should be able to fly to", FossilConfig.FLYING_TARGET_MAX_HEIGHT, 128, 1, 512);
        PREGNANCY_DURATION = COMMON.intEntry("How long mammal pregnancies last, in ticks", FossilConfig.PREGNANCY_DURATION, 10000, 1, 1000000000);
        DINOS_BREAK_BLOCKS = COMMON.boolEntry("True if certain Dinosaurs can break blocks weaker than the set hardness", FossilConfig.DINOS_BREAK_BLOCKS, true);
        BLOCK_BREAK_HARDNESS = COMMON.doubleEntry("Minimum hardness that a block needs in order not to break. The default is iron(5). A value of 0.4 would be enough to break leaves and glass but not dirt", FossilConfig.BLOCK_BREAK_HARDNESS, 5, 0, 100);
        DINOS_EAT_BLOCKS = COMMON.boolEntry("True if herbivores can eat plant blocks", FossilConfig.DINOS_EAT_BLOCKS, true);
        DINOS_EAT_MODDED_MOBS = COMMON.boolEntry("True if Dinosaurs can eat non-vanilla mobs", FossilConfig.DINOS_EAT_MODDED_MOBS, true);
        ANIMALS_FEAR_DINOS = COMMON.boolEntry("True if vanilla animals should run away from dangerous Dinosaurs", FossilConfig.ANIMALS_FEAR_DINOS, true);
        ANU_BLOCK_PLACING = COMMON.boolEntry("True if Anu should be able to place blocks", FossilConfig.ANU_BLOCK_PLACING, true);
        ANUBITE_HAS_COOLDOWN = COMMON.boolEntry("True if the Anubite should be able to spawn again after some time", FossilConfig.ANUBITE_HAS_COOLDOWN, false);
        ANUBITE_COOLDOWN = COMMON.intEntry("How long it takes for the Anubite to respawn, in ticks", FossilConfig.ANUBITE_COOLDOWN, 72000, 1200, 1000000000);
        COMMON.builder.pop();
        COMMON.builder.push("Machine Config");
        CULTURE_VAT_FAIL_CHANCE = COMMON.intEntry("Percentage chance that the Culture Vat will break", FossilConfig.CULTURE_VAT_FAIL_CHANCE, 10, 0, 100);
        MACHINES_REQUIRE_ENERGY = COMMON.boolEntry("True if machines require Energy to operate", FossilConfig.MACHINES_REQUIRE_ENERGY, false);
        MACHINE_MAX_ENERGY = COMMON.intEntry("Max stored Energy machines can have", FossilConfig.MACHINE_MAX_ENERGY, 1000, 1, 1000000);
        MACHINE_TRANSFER_RATE = COMMON.intEntry("Max Energy machines can transfer per tick", FossilConfig.MACHINE_TRANSFER_RATE, 10, 1, 1000000);
        MACHINE_ENERGY_USAGE = COMMON.intEntry("How much Energy machines consume per tick", FossilConfig.MACHINE_ENERGY_USAGE, 1, 1, 1000000);
        COMMON.builder.pop();
        FERN_TICK_RATE = COMMON.intEntry("How often ferns try to grow. Higher number = less growth", FossilConfig.FERN_TICK_RATE, 2, 1, 1000000);
        COMMON_SPEC = COMMON.builder.build();

        FEATHERED_DILO = CLIENT.boolEntry("True if Dilophosaurus should be feathered", FossilConfig.FEATHERED_DILO, false);
        FEATHERED_DRYO = CLIENT.boolEntry("True if Dryosaurus should be feathered", FossilConfig.FEATHERED_DRYO, false);
        CUSTOM_MAIN_MENU = CLIENT.boolEntry("True if the Custom Main Menu should be enabled", FossilConfig.CUSTOM_MAIN_MENU, true);
        HELMET_OVERLAYS = CLIENT.boolEntry("True if skull helmet and ancient helmet should render overlays like vanilla pumpkin", FossilConfig.HELMET_OVERLAYS, true);
        CLIENT_SPEC = CLIENT.builder.build();
    }

    private ForgeConfig() {
    }

    private static class Builder {
        public ForgeConfigSpec.Builder builder = new ForgeConfigSpec.Builder();

        public ForgeConfigSpec.BooleanValue boolEntry(String comment, String path, boolean defaultValue) {
            ForgeConfigSpec.BooleanValue entry = builder.comment(comment).translation(FossilMod.MOD_ID + ".midnightconfig." + path).define(path, defaultValue);
            MAPPED_BOOLS.put(path, entry);
            return entry;
        }

        public ForgeConfigSpec.IntValue intEntry(String comment, String path, int defaultValue, int min, int max) {
            ForgeConfigSpec.IntValue entry = builder.comment(comment).translation(FossilMod.MOD_ID + ".midnightconfig." + path).defineInRange(path, defaultValue, min, max);
            MAPPED_INTS.put(path, entry);
            return entry;
        }

        public ForgeConfigSpec.DoubleValue doubleEntry(String comment, String path, double defaultValue, double min, double max) {
            ForgeConfigSpec.DoubleValue entry = builder.comment(comment).translation(FossilMod.MOD_ID + ".midnightconfig." + path).defineInRange(path, defaultValue, min, max);
            MAPPED_DOUBLES.put(path, entry);
            return entry;
        }
    }
}
