package com.github.teamfossilsarcheology.fossil.world.feature.fabric;

import com.github.teamfossilsarcheology.fossil.world.feature.ModFeatures;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;

import static com.github.teamfossilsarcheology.fossil.world.feature.ModFeatures.*;

/**
 * @see com.github.teamfossilsarcheology.fossil.world.feature.ModFeatures
 */
public class ModFeaturesImpl {
    public static void register() {
        register(ASH_DISK);
        register(CALAMITES_TREE);
        register(CORDAITES_TREE);
        register(MUTANT_TREE);
        register(PALM_TREE);
        register(SIGILLARIA_TREE);
        register(TEMPSKYA_TREE);
        register(MOAI_STATUE);
        register(VOLCANO_CONE);
        register(VOLCANO_VENT);
    }

    private static void register(ModFeatures.Tuple<?, ?> tuple) {
        Registry.register(BuiltInRegistries.FEATURE, tuple.location(), tuple.feature());
    }
}
