package com.fossil.fossil.world.feature.forge;

import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.IForgeRegistry;

import static com.fossil.fossil.world.feature.ModFeatures.ASH_DISK;
import static com.fossil.fossil.world.feature.ModFeatures.VOLCANO_CONE;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModFeaturesImpl {
    public static void register() {
        ASH_DISK.feature().setRegistryName(ASH_DISK.location());
        VOLCANO_CONE.feature().setRegistryName(VOLCANO_CONE.location());
    }

    @SubscribeEvent
    public static void registerFeatures(RegistryEvent.Register<Feature<?>> event) {
        IForgeRegistry<Feature<?>> registry = event.getRegistry();
        registry.register(ASH_DISK.feature());
        registry.register(VOLCANO_CONE.feature());
    }
}
