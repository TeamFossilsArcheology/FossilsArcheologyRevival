package com.fossil.fossil.world.biome;

import com.fossil.fossil.Fossil;
import dev.architectury.event.events.common.LifecycleEvent;
import net.minecraft.core.Registry;
import net.minecraft.data.BuiltinRegistries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biome;

public class ModBiomes {
    public static final ResourceKey<Biome> VOLCANO = registerBiomeResource("volcano");

    public static void register() {
        LifecycleEvent.SETUP.register(() -> {
            registerBiome(VOLCANO, ModOverworldBiomes.volcano());
        });
    }

    private static void registerBiome(ResourceKey<Biome> key, Biome biome) {
        BuiltinRegistries.register(BuiltinRegistries.BIOME, key, biome);
    }

    private static ResourceKey<Biome> registerBiomeResource(String name) {
        return ResourceKey.create(Registry.BIOME_REGISTRY, new ResourceLocation(Fossil.MOD_ID, name));
    }
}
