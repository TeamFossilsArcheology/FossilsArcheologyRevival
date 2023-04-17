package com.fossil.fossil.forge.world.biome;

import com.fossil.fossil.world.biome.ModBiomes;
import com.fossil.fossil.world.biome.ModOverworldBiomes;
import com.fossil.fossil.world.feature.placement.ModPlacedFeatures;
import net.minecraft.world.level.biome.Biome;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.IForgeRegistry;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class ForgeModBiomes {
    @SubscribeEvent
    public static void registerBiomes(RegistryEvent.Register<Biome> event) {
        ModPlacedFeatures.register();
        IForgeRegistry<Biome> registry = event.getRegistry();
        registry.register(ModOverworldBiomes.volcano().setRegistryName(ModBiomes.VOLCANO_KEY.location()));
    }
}
