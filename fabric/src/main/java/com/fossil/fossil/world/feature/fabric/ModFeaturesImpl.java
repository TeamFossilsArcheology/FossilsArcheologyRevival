package com.fossil.fossil.world.feature.fabric;

import net.minecraft.core.Registry;

import static com.fossil.fossil.world.feature.ModFeatures.*;

public class ModFeaturesImpl {
    public static void register() {
        Registry.register(Registry.FEATURE, ASH_DISK.location(), ASH_DISK.feature());
        Registry.register(Registry.FEATURE, CALAMITES_TREE.location(), CALAMITES_TREE.feature());
        Registry.register(Registry.FEATURE, CORDAITES_TREE.location(), CORDAITES_TREE.feature());
        Registry.register(Registry.FEATURE, PALM_TREE.location(), PALM_TREE.feature());
        Registry.register(Registry.FEATURE, SIGILLARIA_TREE.location(), SIGILLARIA_TREE.feature());
        Registry.register(Registry.FEATURE, MOAI_STATUE.location(), MOAI_STATUE.feature());
        Registry.register(Registry.FEATURE, VOLCANO_CONE.location(), VOLCANO_CONE.feature());
    }
}
