package com.fossil.fossil.tags;

import com.fossil.fossil.Fossil;
import com.fossil.fossil.item.ModItems;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

public class ModItemTags {
    private static final ResourceKey<? extends Registry<Item>> key = ModItems.ITEMS.getRegistrar().key();

    public static final TagKey<Item> CALAMITES_LOGS = TagKey.create(key, new ResourceLocation(Fossil.MOD_ID, "calamites_logs"));
    public static final TagKey<Item> CORDAITES_LOGS = TagKey.create(key, new ResourceLocation(Fossil.MOD_ID, "cordaites_logs"));
    public static final TagKey<Item> PALM_LOGS = TagKey.create(key, new ResourceLocation(Fossil.MOD_ID, "palm_logs"));
    public static final TagKey<Item> SIGILLARIA_LOGS = TagKey.create(key, new ResourceLocation(Fossil.MOD_ID, "sigillaria_logs"));
    public static final TagKey<Item> TEMPSKYA_LOGS = TagKey.create(key, new ResourceLocation(Fossil.MOD_ID, "tempskya_logs"));
    public static final TagKey<Item> FOSSIL_SEEDS = TagKey.create(key, new ResourceLocation(Fossil.MOD_ID, "fossil_seeds"));
    public static final TagKey<Item> DINO_EGG = TagKey.create(key, new ResourceLocation(Fossil.MOD_ID, "dino_egg"));
    public static final TagKey<Item> DNA = TagKey.create(key, new ResourceLocation(Fossil.MOD_ID, "dna"));
    public static final TagKey<Item> DNA_INSECTS = TagKey.create(key, new ResourceLocation(Fossil.MOD_ID, "dna_insects"));
    public static final TagKey<Item> DNA_LIMBLESS = TagKey.create(key, new ResourceLocation(Fossil.MOD_ID, "dna_limbless"));
    public static final TagKey<Item> DNA_PLANTS = TagKey.create(key, new ResourceLocation(Fossil.MOD_ID, "dna_plants"));
    public static final TagKey<Item> FOOT_BONES = TagKey.create(key, new ResourceLocation(Fossil.MOD_ID, "bones_foot"));
    public static final TagKey<Item> LEG_BONES = TagKey.create(key, new ResourceLocation(Fossil.MOD_ID, "bones_leg"));
    public static final TagKey<Item> RIBCAGE_BONES = TagKey.create(key, new ResourceLocation(Fossil.MOD_ID, "bones_ribcage"));
    public static final TagKey<Item> SKULL_BONES = TagKey.create(key, new ResourceLocation(Fossil.MOD_ID, "bones_skull"));
    public static final TagKey<Item> VERTEBRAE_BONES = TagKey.create(key, new ResourceLocation(Fossil.MOD_ID, "bones_vertebrae"));
}
