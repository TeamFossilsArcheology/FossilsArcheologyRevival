package com.fossil.fossil.world.feature;

import com.fossil.fossil.block.ModBlocks;
import net.minecraft.core.Holder;
import net.minecraft.data.worldgen.features.FeatureUtils;
import net.minecraft.data.worldgen.features.OreFeatures;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;

import java.util.List;

public class ModOreFeatures {
    private static final List<OreConfiguration.TargetBlockState> OVERWORLD_FOSSIL_BLOCK = List.of(
            OreConfiguration.target(OreFeatures.STONE_ORE_REPLACEABLES, ModBlocks.FOSSIL.get().defaultBlockState()));
    private static final List<OreConfiguration.TargetBlockState> OVERWORLD_VOLCANIC_ROCK = List.of(
            OreConfiguration.target(OreFeatures.DEEPSLATE_ORE_REPLACEABLES, ModBlocks.VOLCANIC_ROCK.get().defaultBlockState()));

    private static final List<OreConfiguration.TargetBlockState> OVERWORLD_PERMAFROST_BLOCK = List.of(
            OreConfiguration.target(OreFeatures.STONE_ORE_REPLACEABLES, ModBlocks.PERMAFROST_BLOCK.get().defaultBlockState()));

    public static final Holder<ConfiguredFeature<OreConfiguration, ?>> FOSSIL_BLOCK = FeatureUtils.register("ore_fossil_block",
            Feature.ORE, new OreConfiguration(OVERWORLD_FOSSIL_BLOCK, 8));
    public static final Holder<ConfiguredFeature<OreConfiguration, ?>> VOLCANIC_ROCK = FeatureUtils.register("ore_volcanic_rock",
            Feature.ORE, new OreConfiguration(OVERWORLD_VOLCANIC_ROCK, 24));
    public static final Holder<ConfiguredFeature<OreConfiguration, ?>> PERMAFROST_BLOCK = FeatureUtils.register("ore_permafrost_block",
            Feature.ORE, new OreConfiguration(OVERWORLD_PERMAFROST_BLOCK, 5));
}
