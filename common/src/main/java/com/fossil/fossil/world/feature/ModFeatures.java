package com.fossil.fossil.world.feature;

import com.fossil.fossil.world.feature.configuration.AshDiskConfiguration;
import net.minecraft.core.Registry;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;

public class ModFeatures {
    public static final AshDiskFeature ASH_DISK = register("ash_disk", new AshDiskFeature(AshDiskConfiguration.CODEC));
    public static final HellBoatFeature HELL_BOAT = register("hell_boat", new HellBoatFeature());
    public static final VolcanoConeFeature VOLCANO_CONE = register("volcano_cone", new VolcanoConeFeature());

    private static <C extends FeatureConfiguration, F extends Feature<C>> F register(String name, F value) {
        return Registry.register(Registry.FEATURE, name, value);
    }
}
