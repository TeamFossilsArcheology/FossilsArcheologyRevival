package com.fossil.fossil.world.feature.structures.fabric;

import net.minecraft.core.Registry;

import static com.fossil.fossil.world.feature.structures.ModStructures.HELL_BOAT;

public class ModStructuresImpl {
    public static void register() {
        Registry.register(Registry.STRUCTURE_FEATURE, HELL_BOAT.location(), HELL_BOAT.feature());
    }
}
