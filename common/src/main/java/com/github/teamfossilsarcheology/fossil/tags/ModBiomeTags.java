package com.github.teamfossilsarcheology.fossil.tags;

import com.github.teamfossilsarcheology.fossil.FossilMod;
import net.minecraft.core.Registry;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.biome.Biome;

public class ModBiomeTags {
    public static final TagKey<Biome> HAS_ANU_CASTLE = TagKey.create(Registry.BIOME_REGISTRY, FossilMod.location("has_anu_castle"));
    public static final TagKey<Biome> HAS_TREASURE_ROOM = TagKey.create(Registry.BIOME_REGISTRY, FossilMod.location("has_treasure_room"));
}
