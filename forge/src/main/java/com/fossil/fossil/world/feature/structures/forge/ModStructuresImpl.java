package com.fossil.fossil.world.feature.structures.forge;

import net.minecraft.world.level.levelgen.feature.StructureFeature;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.IForgeRegistry;

import static com.fossil.fossil.world.feature.structures.ModStructures.*;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModStructuresImpl {
    public static void register() {
        HELL_BOAT.feature().setRegistryName(HELL_BOAT.location());
        ANU_CASTLE.feature().setRegistryName(ANU_CASTLE.location());
        TREASURE_ROOM.feature().setRegistryName(TREASURE_ROOM.location());
    }

    @SubscribeEvent
    public static void registerFeatures(RegistryEvent.Register<StructureFeature<?>> event) {
        IForgeRegistry<StructureFeature<?>> registry = event.getRegistry();
        registry.register(HELL_BOAT.feature());
        registry.register(ANU_CASTLE.feature());
        registry.register(TREASURE_ROOM.feature());
    }
}
