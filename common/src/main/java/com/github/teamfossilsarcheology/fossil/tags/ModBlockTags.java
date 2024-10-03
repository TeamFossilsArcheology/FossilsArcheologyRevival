package com.github.teamfossilsarcheology.fossil.tags;

import com.github.teamfossilsarcheology.fossil.Fossil;
import com.github.teamfossilsarcheology.fossil.block.ModBlocks;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;

public class ModBlockTags {
    private static final ResourceKey<? extends Registry<Block>> key = ModBlocks.BLOCKS.getRegistrar().key();
    public static final TagKey<Block> ANCIENT_WOOD_LOGS = TagKey.create(key, Fossil.location("ancient_wood_logs"));
    public static final TagKey<Block> CALAMITES_LOGS = TagKey.create(key, Fossil.location("calamites_logs"));
    public static final TagKey<Block> CORDAITES_LOGS = TagKey.create(key, Fossil.location("cordaites_logs"));
    public static final TagKey<Block> MUTANT_TREE_LOGS = TagKey.create(key, Fossil.location("mutant_tree_logs"));
    public static final TagKey<Block> PALM_LOGS = TagKey.create(key, Fossil.location("palm_logs"));
    public static final TagKey<Block> SIGILLARIA_LOGS = TagKey.create(key, Fossil.location("sigillaria_logs"));
    public static final TagKey<Block> TEMPSKYA_LOGS = TagKey.create(key, Fossil.location("tempskya_logs"));
    public static final TagKey<Block> FIGURINES = TagKey.create(key, Fossil.location("figurines"));
    public static final TagKey<Block> UNBREAKABLE = TagKey.create(key, Fossil.location("unbreakable"));
}
