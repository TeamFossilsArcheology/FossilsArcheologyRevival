package com.github.teamfossilsarcheology.fossil.world.feature.structures.forge;

import com.github.teamfossilsarcheology.fossil.Fossil;
import net.minecraft.world.level.levelgen.feature.StructureFeature;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.IForgeRegistry;

import static com.github.teamfossilsarcheology.fossil.world.feature.structures.ModStructures.*;

@Mod.EventBusSubscriber(modid = Fossil.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModStructuresImpl {
    public static void register() {
        HELL_BOAT.feature().setRegistryName(HELL_BOAT.location());
        CONFIGURABLE_STRUCTURE.feature().setRegistryName(CONFIGURABLE_STRUCTURE.location());
        ANU_CASTLE.feature().setRegistryName(ANU_CASTLE.location());
        TREASURE_ROOM.feature().setRegistryName(TREASURE_ROOM.location());
    }

    @SubscribeEvent
    public static void registerFeatures(RegistryEvent.Register<StructureFeature<?>> event) {
        IForgeRegistry<StructureFeature<?>> registry = event.getRegistry();
        registry.register(HELL_BOAT.feature());
        registry.register(CONFIGURABLE_STRUCTURE.feature());
        registry.register(ANU_CASTLE.feature());
        registry.register(TREASURE_ROOM.feature());
    }
}
