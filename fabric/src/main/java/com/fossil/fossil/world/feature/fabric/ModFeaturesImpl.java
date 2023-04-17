package com.fossil.fossil.world.feature.fabric;

import net.minecraft.core.Registry;

import static com.fossil.fossil.world.feature.ModFeatures.*;

public class ModFeaturesImpl {
    public static void register() {
        Registry.register(Registry.FEATURE, ASH_DISK.location(), ASH_DISK.feature());
        Registry.register(Registry.FEATURE, HELL_BOAT.location(), HELL_BOAT.feature());
        Registry.register(Registry.FEATURE, VOLCANO_CONE.location(), VOLCANO_CONE.feature());
    }
}
