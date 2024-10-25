package com.github.teamfossilsarcheology.fossil.world.biome;

import com.github.teamfossilsarcheology.fossil.FossilMod;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.biome.Biome;

/**
 * Stores the resource keys for the custom biomes
 */
public class ModBiomes {
    public static final ResourceKey<Biome> VOLCANO_KEY = resource("volcano");
    public static final ResourceKey<Biome> TREASURE_ROOM_KEY = resource("treasure_room");
    public static final ResourceKey<Biome> ANU_LAIR_KEY = resource("anu_lair");

    private static ResourceKey<Biome> resource(String name) {
        return ResourceKey.create(Registry.BIOME_REGISTRY, FossilMod.location(name));
    }
}
