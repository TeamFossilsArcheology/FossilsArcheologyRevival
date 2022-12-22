package com.fossil.fossil.world.gen;

import com.fossil.fossil.world.feature.ModPlacedFeatures;
import net.minecraft.core.Holder;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraftforge.event.world.BiomeLoadingEvent;

import java.util.List;

public class ModOreGeneration {
    public static void generateOres(final BiomeLoadingEvent event) {
        List<Holder<PlacedFeature>> base =
                event.getGeneration().getFeatures(GenerationStep.Decoration.UNDERGROUND_ORES);

        base.add(ModPlacedFeatures.FOSSIL_BLOCK_PLACED);
        base.add(ModPlacedFeatures.VOLCANIC_ROCK_PLACED);
        base.add(ModPlacedFeatures.PERMAFROST_BLOCK_PLACED);
    }
}
