package com.fossil.fossil.world.feature;

import com.fossil.fossil.Fossil;
import com.fossil.fossil.world.feature.configuration.AshDiskConfiguration;
import dev.architectury.injectables.annotations.ExpectPlatform;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;

public class ModFeatures {
    public static final Holder<AshDiskConfiguration, AshDiskFeature> ASH_DISK = create("ask_disk", new AshDiskFeature());
    public static final Holder<NoneFeatureConfiguration, VolcanoConeFeature> VOLCANO_CONE = create("volcano_cone", new VolcanoConeFeature());

    public static <C extends FeatureConfiguration, F extends Feature<C>> Holder<C, F> create(String name, F feature) {
        return new Holder<>(new ResourceLocation(Fossil.MOD_ID, name), feature);
    }

    @ExpectPlatform
    public static void register() {
    }

    public record Holder<C extends FeatureConfiguration, F extends Feature<C>>(ResourceLocation location, F feature) {

    }
}
