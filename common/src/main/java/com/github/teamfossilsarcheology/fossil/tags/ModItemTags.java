package com.github.teamfossilsarcheology.fossil.tags;

import com.github.teamfossilsarcheology.fossil.FossilMod;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

public class ModItemTags {
    private static final ResourceKey<? extends Registry<Item>> key = Registries.ITEM;

    public static final TagKey<Item> ANCIENT_WOOD_LOGS = TagKey.create(key, FossilMod.location("ancient_wood_logs"));
    public static final TagKey<Item> CALAMITES_LOGS = TagKey.create(key, FossilMod.location("calamites_logs"));
    public static final TagKey<Item> CORDAITES_LOGS = TagKey.create(key, FossilMod.location("cordaites_logs"));
    public static final TagKey<Item> MUTANT_TREE_LOGS = TagKey.create(key, FossilMod.location("mutant_tree_logs"));
    public static final TagKey<Item> PALM_LOGS = TagKey.create(key, FossilMod.location("palm_logs"));
    public static final TagKey<Item> SIGILLARIA_LOGS = TagKey.create(key, FossilMod.location("sigillaria_logs"));
    public static final TagKey<Item> TEMPSKYA_LOGS = TagKey.create(key, FossilMod.location("tempskya_logs"));
    public static final TagKey<Item> FOSSIL_SEEDS = TagKey.create(key, FossilMod.location("fossil_seeds"));
    public static final TagKey<Item> RESTORED_SEEDS = TagKey.create(key, FossilMod.location("restored_seeds"));
    public static final TagKey<Item> FOSSIL_SAPLINGS = TagKey.create(key, FossilMod.location("fossil_saplings"));
    public static final TagKey<Item> FOSSILS = TagKey.create(key, FossilMod.location("fossils"));
    public static final TagKey<Item> FIGURINES = TagKey.create(key, FossilMod.location("figurines"));
    public static final TagKey<Item> BIRD_EGGS = TagKey.create(key, FossilMod.location("bird_eggs"));
    public static final TagKey<Item> DINO_EGGS = TagKey.create(key, FossilMod.location("dino_eggs"));
    public static final TagKey<Item> ALL_EGGS = TagKey.create(key, FossilMod.location("all_eggs"));
    public static final TagKey<Item> COOKABLE_EGGS = TagKey.create(key, FossilMod.location("cookable_eggs"));
    public static final TagKey<Item> SIFTER_INPUTS = TagKey.create(key, FossilMod.location("sifter_inputs"));
    public static final TagKey<Item> DNA = TagKey.create(key, FossilMod.location("dna"));
    public static final TagKey<Item> EMBRYOS = TagKey.create(key, FossilMod.location("embryos"));
    public static final TagKey<Item> DNA_INSECTS = TagKey.create(key, FossilMod.location("dna_insects"));
    public static final TagKey<Item> DNA_LIMBLESS = TagKey.create(key, FossilMod.location("dna_limbless"));
    public static final TagKey<Item> DNA_PLANTS = TagKey.create(key, FossilMod.location("dna_plants"));
    public static final TagKey<Item> ARM_BONES = TagKey.create(key, FossilMod.location("bones_arm"));
    public static final TagKey<Item> FOOT_BONES = TagKey.create(key, FossilMod.location("bones_foot"));
    public static final TagKey<Item> LEG_BONES = TagKey.create(key, FossilMod.location("bones_leg"));
    public static final TagKey<Item> RIBCAGE_BONES = TagKey.create(key, FossilMod.location("bones_ribcage"));
    public static final TagKey<Item> SKULL_BONES = TagKey.create(key, FossilMod.location("bones_skull"));
    public static final TagKey<Item> TAIL_BONES = TagKey.create(key, FossilMod.location("bones_tail"));
    public static final TagKey<Item> UNIQUE_BONES = TagKey.create(key, FossilMod.location("bones_unique"));
    public static final TagKey<Item> VERTEBRAE_BONES = TagKey.create(key, FossilMod.location("bones_vertebrae"));
    public static final TagKey<Item> ALL_BONES = TagKey.create(key, FossilMod.location("bones_all"));
    public static final TagKey<Item> UNCOOKED_MEAT = TagKey.create(key, FossilMod.location("meat_uncooked"));

    public static final TagKey<Item> FILTER_BONES = TagKey.create(key, FossilMod.location("filter_bones"));
    public static final TagKey<Item> FILTER_DNA = TagKey.create(key, FossilMod.location("filter_dna"));
    public static final TagKey<Item> FILTER_EGGS = TagKey.create(key, FossilMod.location("filter_eggs"));
    public static final TagKey<Item> FILTER_MEAT = TagKey.create(key, FossilMod.location("filter_meat"));
    public static final TagKey<Item> FILTER_BUCKETS = TagKey.create(key, FossilMod.location("filter_buckets"));

    public static final TagKey<Item> FILTER_PLANTS = TagKey.create(key, FossilMod.location("filter_plants"));
    public static final TagKey<Item> FILTER_OTHER = TagKey.create(key, FossilMod.location("filter_other"));
    public static final TagKey<Item> FILTER_TREES = TagKey.create(key, FossilMod.location("filter_trees"));
    public static final TagKey<Item> FILTER_VASES = TagKey.create(key, FossilMod.location("filter_vases"));
    public static final TagKey<Item> FILTER_UNBREAKABLE = TagKey.create(key, FossilMod.location("filter_unbreakable"));
    public static final TagKey<Item> FILTER_MACHINES = TagKey.create(key, FossilMod.location("filter_machines"));
    public static final TagKey<Item> FILTER_BUILDING_BLOCKS = TagKey.create(key, FossilMod.location("filter_building_blocks"));

    public static final TagKey<Item> FILTER_PARK = TagKey.create(key, FossilMod.location("filter_park"));
    public static final TagKey<Item> FILTER_TOOLS = TagKey.create(key, FossilMod.location("filter_tools"));

    //REI Support
    public static final TagKey<Item> FISH_EGGS = TagKey.create(key, FossilMod.location("fish_eggs"));
    public static final TagKey<Item> BONES_DNA = TagKey.create(key, FossilMod.location("bones_dna"));
    public static final TagKey<Item> MEAT_DNA = TagKey.create(key, FossilMod.location("meat_dna"));
    public static final TagKey<Item> DINO_DNA = TagKey.create(key, FossilMod.location("dino_dna"));
    public static final TagKey<Item> FISH_DNA = TagKey.create(key, FossilMod.location("fish_dna"));
    public static final TagKey<Item> EMBRYO_DNA = TagKey.create(key, FossilMod.location("embryo_dna"));
}
