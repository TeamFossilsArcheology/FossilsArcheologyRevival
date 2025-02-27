package com.github.teamfossilsarcheology.fossil.tags;

import com.github.teamfossilsarcheology.fossil.FossilMod;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;

public class ModBlockTags {
    private static final ResourceKey<? extends Registry<Block>> key = Registries.BLOCK;
    public static final TagKey<Block> ANCIENT_WOOD_LOGS = TagKey.create(key, FossilMod.location("ancient_wood_logs"));
    public static final TagKey<Block> CALAMITES_LOGS = TagKey.create(key, FossilMod.location("calamites_logs"));
    public static final TagKey<Block> CORDAITES_LOGS = TagKey.create(key, FossilMod.location("cordaites_logs"));
    public static final TagKey<Block> MUTANT_TREE_LOGS = TagKey.create(key, FossilMod.location("mutant_tree_logs"));
    public static final TagKey<Block> PALM_LOGS = TagKey.create(key, FossilMod.location("palm_logs"));
    public static final TagKey<Block> SIGILLARIA_LOGS = TagKey.create(key, FossilMod.location("sigillaria_logs"));
    public static final TagKey<Block> TEMPSKYA_LOGS = TagKey.create(key, FossilMod.location("tempskya_logs"));
    public static final TagKey<Block> FIGURINES = TagKey.create(key, FossilMod.location("figurines"));
    public static final TagKey<Block> UNBREAKABLE = TagKey.create(key, FossilMod.location("unbreakable"));
    public static final TagKey<Block> MOOD_BONUS = TagKey.create(key, FossilMod.location("mood_bonus"));
    public static final TagKey<Block> PLANTS = TagKey.create(key, FossilMod.location("plants"));
    public static final TagKey<Block> PLANTABLE_ON_SAND = TagKey.create(key, FossilMod.location("plantable_on_sand"));
}
