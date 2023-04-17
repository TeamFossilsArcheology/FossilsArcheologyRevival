package com.fossil.fossil.fabric.world.biome;

import com.fossil.fossil.world.biome.ModBiomes;
import com.fossil.fossil.world.biome.ModOverworldBiomes;
import net.minecraft.data.BuiltinRegistries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.biome.Biome;

public class FabricModBiomes {
    public static void register() {
        registerBiome(ModBiomes.VOLCANO_KEY, ModOverworldBiomes.volcano());
    }

    private static void registerBiome(ResourceKey<Biome> key, Biome biome) {
        BuiltinRegistries.register(BuiltinRegistries.BIOME, key, biome);
    }
}
