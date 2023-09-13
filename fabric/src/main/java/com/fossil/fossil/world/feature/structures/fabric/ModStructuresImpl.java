package com.fossil.fossil.world.feature.structures.fabric;

import net.minecraft.core.Registry;

import static com.fossil.fossil.world.feature.structures.ModStructures.*;

public class ModStructuresImpl {
    public static void register() {
        Registry.register(Registry.STRUCTURE_FEATURE, HELL_BOAT.location(), HELL_BOAT.feature());
        Registry.register(Registry.STRUCTURE_FEATURE, ANU_CASTLE.location(), ANU_CASTLE.feature());
        Registry.register(Registry.STRUCTURE_FEATURE, TREASURE_ROOM.location(), TREASURE_ROOM.feature());
    }
}
