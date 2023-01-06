package com.fossil.fossil.world.feature;

import com.fossil.fossil.block.ModBlocks;
import dev.architectury.event.events.common.LifecycleEvent;
import dev.architectury.registry.level.biome.BiomeModifications;
import net.minecraft.core.Holder;
import net.minecraft.data.worldgen.features.FeatureUtils;
import net.minecraft.data.worldgen.features.OreFeatures;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;
import net.minecraft.world.level.levelgen.placement.HeightRangePlacement;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;

import java.util.List;

public class ModPlacedFeatures {
    public static void register() {
        LifecycleEvent.SETUP.register(() -> {
            final List<OreConfiguration.TargetBlockState> OVERWORLD_FOSSIL_BLOCK = List.of(
                    OreConfiguration.target(OreFeatures.STONE_ORE_REPLACEABLES, ModBlocks.FOSSIL.get().defaultBlockState()));
            final List<OreConfiguration.TargetBlockState> OVERWORLD_VOLCANIC_ROCK = List.of(
                    OreConfiguration.target(OreFeatures.DEEPSLATE_ORE_REPLACEABLES, ModBlocks.VOLCANIC_ROCK.get().defaultBlockState()));

            final List<OreConfiguration.TargetBlockState> OVERWORLD_PERMAFROST_BLOCK = List.of(
                    OreConfiguration.target(OreFeatures.STONE_ORE_REPLACEABLES, ModBlocks.PERMAFROST_BLOCK.get().defaultBlockState()));

            final Holder<ConfiguredFeature<OreConfiguration, ?>> FOSSIL_BLOCK = FeatureUtils.register("fossil_block",
                    Feature.ORE, new OreConfiguration(OVERWORLD_FOSSIL_BLOCK, 8));
            final Holder<ConfiguredFeature<OreConfiguration, ?>> VOLCANIC_ROCK = FeatureUtils.register("volcanic_rock",
                    Feature.ORE, new OreConfiguration(OVERWORLD_VOLCANIC_ROCK, 24));

            final Holder<ConfiguredFeature<OreConfiguration, ?>> PERMAFROST_BLOCK = FeatureUtils.register("permafrost_block",
                    Feature.ORE, new OreConfiguration(OVERWORLD_PERMAFROST_BLOCK, 5));

            final Holder<PlacedFeature> FOSSIL_BLOCK_PLACED = PlacementUtils.register("fossil_block_placed",
                    FOSSIL_BLOCK, ModOrePlacement.commonOrePlacement(17, // VeinsPerChunk
                            HeightRangePlacement.triangle(VerticalAnchor.aboveBottom(-0), VerticalAnchor.aboveBottom(256))));

            final Holder<PlacedFeature> VOLCANIC_ROCK_PLACED = PlacementUtils.register("volcanic_rock_placed",
                    VOLCANIC_ROCK, ModOrePlacement.commonOrePlacement(2,
                            HeightRangePlacement.uniform(VerticalAnchor.bottom(), VerticalAnchor.absolute(0))));

            final Holder<PlacedFeature> PERMAFROST_BLOCK_PLACED = PlacementUtils.register("permafrost_block_placed",
                    PERMAFROST_BLOCK, ModOrePlacement.commonOrePlacement(3, // VeinsPerChunk
                            HeightRangePlacement.triangle(VerticalAnchor.aboveBottom(100), VerticalAnchor.aboveBottom(256))));

            BiomeModifications.addProperties((context, mutable) -> {
                mutable.getGenerationProperties().addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, FOSSIL_BLOCK_PLACED);
                mutable.getGenerationProperties().addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, VOLCANIC_ROCK_PLACED);
                mutable.getGenerationProperties().addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, PERMAFROST_BLOCK_PLACED);
            });
        });

    }
}
