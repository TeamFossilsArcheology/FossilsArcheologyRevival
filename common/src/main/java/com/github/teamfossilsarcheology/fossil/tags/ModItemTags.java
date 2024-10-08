package com.github.teamfossilsarcheology.fossil.tags;

import com.github.teamfossilsarcheology.fossil.Fossil;
import com.github.teamfossilsarcheology.fossil.item.ModItems;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

public class ModItemTags {
    private static final ResourceKey<? extends Registry<Item>> key = ModItems.ITEMS.getRegistrar().key();

    public static final TagKey<Item> ANCIENT_WOOD_LOGS = TagKey.create(key, Fossil.location("ancient_wood_logs"));
    public static final TagKey<Item> CALAMITES_LOGS = TagKey.create(key, Fossil.location("calamites_logs"));
    public static final TagKey<Item> CORDAITES_LOGS = TagKey.create(key, Fossil.location("cordaites_logs"));
    public static final TagKey<Item> MUTANT_TREE_LOGS = TagKey.create(key, Fossil.location("mutant_tree_logs"));
    public static final TagKey<Item> PALM_LOGS = TagKey.create(key, Fossil.location("palm_logs"));
    public static final TagKey<Item> SIGILLARIA_LOGS = TagKey.create(key, Fossil.location("sigillaria_logs"));
    public static final TagKey<Item> TEMPSKYA_LOGS = TagKey.create(key, Fossil.location("tempskya_logs"));
    public static final TagKey<Item> FOSSIL_SEEDS = TagKey.create(key, Fossil.location("fossil_seeds"));
    public static final TagKey<Item> RESTORED_SEEDS = TagKey.create(key, Fossil.location("restored_seeds"));
    public static final TagKey<Item> FOSSIL_SAPLINGS = TagKey.create(key, Fossil.location("fossil_saplings"));
    public static final TagKey<Item> FIGURINES = TagKey.create(key, Fossil.location("figurines"));
    public static final TagKey<Item> DINO_EGGS = TagKey.create(key, Fossil.location("dino_eggs"));
    public static final TagKey<Item> COOKABLE_EGGS = TagKey.create(key, Fossil.location("cookable_eggs"));
    public static final TagKey<Item> SIFTER_INPUTS = TagKey.create(key, Fossil.location("sifter_inputs"));
    public static final TagKey<Item> DNA = TagKey.create(key, Fossil.location("dna"));
    public static final TagKey<Item> EMBRYOS = TagKey.create(key, Fossil.location("embryos"));
    public static final TagKey<Item> DNA_INSECTS = TagKey.create(key, Fossil.location("dna_insects"));
    public static final TagKey<Item> DNA_LIMBLESS = TagKey.create(key, Fossil.location("dna_limbless"));
    public static final TagKey<Item> DNA_PLANTS = TagKey.create(key, Fossil.location("dna_plants"));
    public static final TagKey<Item> ARM_BONES = TagKey.create(key, Fossil.location("bones_arm"));
    public static final TagKey<Item> FOOT_BONES = TagKey.create(key, Fossil.location("bones_foot"));
    public static final TagKey<Item> LEG_BONES = TagKey.create(key, Fossil.location("bones_leg"));
    public static final TagKey<Item> RIBCAGE_BONES = TagKey.create(key, Fossil.location("bones_ribcage"));
    public static final TagKey<Item> SKULL_BONES = TagKey.create(key, Fossil.location("bones_skull"));
    public static final TagKey<Item> TAIL_BONES = TagKey.create(key, Fossil.location("bones_tail"));
    public static final TagKey<Item> UNIQUE_BONES = TagKey.create(key, Fossil.location("bones_unique"));
    public static final TagKey<Item> VERTEBRAE_BONES = TagKey.create(key, Fossil.location("bones_vertebrae"));
    public static final TagKey<Item> ALL_BONES = TagKey.create(key, Fossil.location("bones_all"));
    public static final TagKey<Item> UNCOOKED_MEAT = TagKey.create(key, Fossil.location("meat_uncooked"));
    public static final TagKey<Item> FILTER_BONES = TagKey.create(key, Fossil.location("filter_bones"));
    public static final TagKey<Item> FILTER_DNA = TagKey.create(key, Fossil.location("filter_dna"));
    public static final TagKey<Item> FILTER_EGGS = TagKey.create(key, Fossil.location("filter_eggs"));
    public static final TagKey<Item> FILTER_MEAT = TagKey.create(key, Fossil.location("filter_meat"));
    public static final TagKey<Item> FILTER_PLANTS = TagKey.create(key, Fossil.location("filter_plants"));
    public static final TagKey<Item> FILTER_TREES = TagKey.create(key, Fossil.location("filter_trees"));
    public static final TagKey<Item> FILTER_VASES = TagKey.create(key, Fossil.location("filter_vases"));
    public static final TagKey<Item> FILTER_UNBREAKABLE = TagKey.create(key, Fossil.location("filter_unbreakable"));

    //REI Support
    public static final TagKey<Item> FISH_EGGS = TagKey.create(key, Fossil.location("fish_eggs"));
    public static final TagKey<Item> BONES_DNA = TagKey.create(key, Fossil.location("bones_dna"));
    public static final TagKey<Item> MEAT_DNA = TagKey.create(key, Fossil.location("meat_dna"));
    public static final TagKey<Item> DINO_DNA = TagKey.create(key, Fossil.location("dino_dna"));
    public static final TagKey<Item> FISH_DNA = TagKey.create(key, Fossil.location("fish_dna"));
    public static final TagKey<Item> EMBRYO_DNA = TagKey.create(key, Fossil.location("embryo_dna"));
}
