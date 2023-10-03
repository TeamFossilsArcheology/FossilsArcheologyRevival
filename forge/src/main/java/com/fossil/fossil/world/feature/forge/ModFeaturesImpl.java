package com.fossil.fossil.world.feature.forge;

import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.IForgeRegistry;

import static com.fossil.fossil.world.feature.ModFeatures.*;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModFeaturesImpl {
    public static void register() {
        ASH_DISK.feature().setRegistryName(ASH_DISK.location());
        MOAI_STATUE.feature().setRegistryName(MOAI_STATUE.location());
        VOLCANO_CONE.feature().setRegistryName(VOLCANO_CONE.location());
    }

    @SubscribeEvent
    public static void registerFeatures(RegistryEvent.Register<Feature<?>> event) {
        IForgeRegistry<Feature<?>> registry = event.getRegistry();
        registry.register(ASH_DISK.feature());
        registry.register(MOAI_STATUE.feature());
        registry.register(VOLCANO_CONE.feature());
    }
}
