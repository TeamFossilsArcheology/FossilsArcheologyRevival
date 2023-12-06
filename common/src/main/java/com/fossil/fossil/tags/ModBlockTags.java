package com.fossil.fossil.tags;

import com.fossil.fossil.Fossil;
import com.fossil.fossil.block.ModBlocks;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;

public class ModBlockTags {
    private static final ResourceKey<? extends Registry<Block>> key = ModBlocks.BLOCKS.getRegistrar().key();
    public static final TagKey<Block> CALAMITES_LOGS = TagKey.create(key, new ResourceLocation(Fossil.MOD_ID, "calamites_logs"));
    public static final TagKey<Block> CORDAITES_LOGS = TagKey.create(key, new ResourceLocation(Fossil.MOD_ID, "cordaites_logs"));
    public static final TagKey<Block> MUTANT_TREE_LOGS = TagKey.create(key, new ResourceLocation(Fossil.MOD_ID, "mutant_tree_logs"));
    public static final TagKey<Block> PALM_LOGS = TagKey.create(key, new ResourceLocation(Fossil.MOD_ID, "palm_logs"));
    public static final TagKey<Block> SIGILLARIA_LOGS = TagKey.create(key, new ResourceLocation(Fossil.MOD_ID, "sigillaria_logs"));
    public static final TagKey<Block> TEMPSKYA_LOGS = TagKey.create(key, new ResourceLocation(Fossil.MOD_ID, "tempskya_logs"));
    public static final TagKey<Block> FIGURINES = TagKey.create(key, new ResourceLocation(Fossil.MOD_ID, "figurines"));
}
