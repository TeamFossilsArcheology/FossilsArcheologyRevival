package com.github.teamfossilsarcheology.fossil.world.feature;

import com.github.teamfossilsarcheology.fossil.block.ModBlocks;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.Holder;
import net.minecraft.data.worldgen.features.FeatureUtils;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;
import net.minecraft.world.level.levelgen.structure.templatesystem.BlockMatchTest;

import java.util.List;

import static net.minecraft.data.worldgen.features.OreFeatures.DEEPSLATE_ORE_REPLACEABLES;
import static net.minecraft.data.worldgen.features.OreFeatures.STONE_ORE_REPLACEABLES;

public class ModOreFeatures {
    public static final List<OreConfiguration.TargetBlockState> OVERWORLD_FOSSIL_BLOCK = List.of(
            target(Blocks.CALCITE, ModBlocks.CALCITE_FOSSIL),
            target(Blocks.DRIPSTONE_BLOCK, ModBlocks.DRIPSTONE_FOSSIL),
            target(Blocks.DEEPSLATE, ModBlocks.DEEPSLATE_FOSSIL),
            target(Blocks.RED_SANDSTONE, ModBlocks.RED_SANDSTONE_FOSSIL),
            target(Blocks.SANDSTONE, ModBlocks.SANDSTONE_FOSSIL),
            OreConfiguration.target(STONE_ORE_REPLACEABLES, ModBlocks.STONE_FOSSIL.get().defaultBlockState()),
            target(Blocks.TUFF, ModBlocks.TUFF_FOSSIL));

    public static final Holder<ConfiguredFeature<OreConfiguration, ?>> FOSSIL_BLOCK = FeatureUtils.register("ore_fossil_block",
            Feature.ORE, new OreConfiguration(OVERWORLD_FOSSIL_BLOCK, 6));

    private static final List<OreConfiguration.TargetBlockState> OVERWORLD_VOLCANIC_ROCK = List.of(
            OreConfiguration.target(DEEPSLATE_ORE_REPLACEABLES, ModBlocks.VOLCANIC_ROCK.get().defaultBlockState()));
    public static final Holder<ConfiguredFeature<OreConfiguration, ?>> VOLCANIC_ROCK = FeatureUtils.register("ore_volcanic_rock",
            Feature.ORE, new OreConfiguration(OVERWORLD_VOLCANIC_ROCK, 24));

    private static final List<OreConfiguration.TargetBlockState> OVERWORLD_PERMAFROST_BLOCK = List.of(
            OreConfiguration.target(STONE_ORE_REPLACEABLES, ModBlocks.PERMAFROST_BLOCK.get().defaultBlockState()));
    public static final Holder<ConfiguredFeature<OreConfiguration, ?>> PERMAFROST_BLOCK = FeatureUtils.register("ore_permafrost_block",
            Feature.ORE, new OreConfiguration(OVERWORLD_PERMAFROST_BLOCK, 5));

    private static final List<OreConfiguration.TargetBlockState> ORE_AMBER_TARGET_LIST = List.of(
            OreConfiguration.target(STONE_ORE_REPLACEABLES, ModBlocks.AMBER_ORE.get().defaultBlockState()));
    public static final Holder<ConfiguredFeature<OreConfiguration, ?>> ORE_AMBER = FeatureUtils.register("ore_amber",
            Feature.ORE, new OreConfiguration(ORE_AMBER_TARGET_LIST, 3));
    public static final Holder<ConfiguredFeature<OreConfiguration, ?>> ORE_AMBER_BURIED = FeatureUtils.register("ore_amber_buried",
            Feature.ORE, new OreConfiguration(ORE_AMBER_TARGET_LIST, 3, 1));

    private static OreConfiguration.TargetBlockState target(Block target, RegistrySupplier<? extends Block> block) {
        return OreConfiguration.target(new BlockMatchTest(target), block.get().defaultBlockState());
    }
}
