package com.fossil.fossil.world.feature.fabric;

import net.minecraft.core.Registry;

import static com.fossil.fossil.world.feature.ModFeatures.ASH_DISK;
import static com.fossil.fossil.world.feature.ModFeatures.VOLCANO_CONE;

public class ModFeaturesImpl {
    public static void register() {
        Registry.register(Registry.FEATURE, ASH_DISK.location(), ASH_DISK.feature());
        Registry.register(Registry.FEATURE, VOLCANO_CONE.location(), VOLCANO_CONE.feature());
    }
}
