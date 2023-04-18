package com.fossil.fossil.world.feature.structures;

import com.fossil.fossil.Fossil;
import dev.architectury.injectables.annotations.ExpectPlatform;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.feature.StructureFeature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.RangeConfiguration;

public class ModStructures {
    public static final Holder<RangeConfiguration, HellBoatFeature> HELL_BOAT = createStructure("hell_boat", new HellBoatFeature());

    public static <C extends FeatureConfiguration, F extends StructureFeature<C>> Holder<C, F> createStructure(String name, F feature) {
        return new Holder<>(new ResourceLocation(Fossil.MOD_ID, name), feature);
    }

    @ExpectPlatform
    public static void register() {
    }

    public record Holder<C extends FeatureConfiguration, F extends StructureFeature<C>>(ResourceLocation location, F feature) {

    }
}
