package com.github.teamfossilsarcheology.fossil.world.feature;

import com.github.teamfossilsarcheology.fossil.FossilMod;
import com.github.teamfossilsarcheology.fossil.block.ModBlocks;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.data.worldgen.features.FeatureUtils;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;
import net.minecraft.world.level.levelgen.structure.templatesystem.BlockMatchTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.RuleTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.TagMatchTest;

import java.util.List;

public class ModOreFeatures {
    public static final ResourceKey<ConfiguredFeature<?, ?>> ORE_AMBER = createKey("ore_amber");
    public static final ResourceKey<ConfiguredFeature<?, ?>> ORE_AMBER_BURIED = createKey("ore_amber_buried");
    public static final ResourceKey<ConfiguredFeature<?, ?>> ORE_FOSSIL_BLOCK_NO_SANDSTONE = createKey("ore_fossil_block_without_sandstone");
    public static final ResourceKey<ConfiguredFeature<?, ?>> ORE_FOSSIL_BLOCK = createKey("ore_fossil_block");
    public static final ResourceKey<ConfiguredFeature<?, ?>> ORE_PERMAFROST_BLOCK = createKey("ore_permafrost_block");
    public static final ResourceKey<ConfiguredFeature<?, ?>> ORE_VOLCANIC_ROCK = createKey("ore_volcanic_rock");

    private static ResourceKey<ConfiguredFeature<?, ?>> createKey(String name) {
        return ResourceKey.create(Registries.CONFIGURED_FEATURE, FossilMod.location(name));
    }

    public static void bootstrap(BootstapContext<ConfiguredFeature<?, ?>> context) {
        RuleTest stoneOreReplaceables = new TagMatchTest(BlockTags.STONE_ORE_REPLACEABLES);
        RuleTest deepslateOreReplaceables = new TagMatchTest(BlockTags.DEEPSLATE_ORE_REPLACEABLES);

        List<OreConfiguration.TargetBlockState> amberOreTargetList = List.of(
                OreConfiguration.target(stoneOreReplaceables, ModBlocks.AMBER_ORE.get().defaultBlockState()));
        FeatureUtils.register(context, ORE_AMBER, Feature.ORE, new OreConfiguration(amberOreTargetList, 3));
        FeatureUtils.register(context, ORE_AMBER_BURIED, Feature.ORE, new OreConfiguration(amberOreTargetList, 3, 1));

        List<OreConfiguration.TargetBlockState> overworldFossilBlockNoSandstone = List.of(
                target(Blocks.CALCITE, ModBlocks.CALCITE_FOSSIL),
                target(Blocks.DRIPSTONE_BLOCK, ModBlocks.DRIPSTONE_FOSSIL),
                target(Blocks.DEEPSLATE, ModBlocks.DEEPSLATE_FOSSIL),
                OreConfiguration.target(stoneOreReplaceables, ModBlocks.STONE_FOSSIL.get().defaultBlockState()),
                target(Blocks.TUFF, ModBlocks.TUFF_FOSSIL));
        List<OreConfiguration.TargetBlockState> overworldFossilBlock = List.of(
                target(Blocks.CALCITE, ModBlocks.CALCITE_FOSSIL),
                target(Blocks.DRIPSTONE_BLOCK, ModBlocks.DRIPSTONE_FOSSIL),
                target(Blocks.DEEPSLATE, ModBlocks.DEEPSLATE_FOSSIL),
                target(Blocks.RED_SANDSTONE, ModBlocks.RED_SANDSTONE_FOSSIL),
                target(Blocks.SANDSTONE, ModBlocks.SANDSTONE_FOSSIL),
                OreConfiguration.target(stoneOreReplaceables, ModBlocks.STONE_FOSSIL.get().defaultBlockState()),
                target(Blocks.TUFF, ModBlocks.TUFF_FOSSIL));
        FeatureUtils.register(context, ORE_FOSSIL_BLOCK_NO_SANDSTONE, Feature.ORE, new OreConfiguration(overworldFossilBlockNoSandstone, 6));
        FeatureUtils.register(context, ORE_FOSSIL_BLOCK, Feature.ORE, new OreConfiguration(overworldFossilBlock, 6));

        List<OreConfiguration.TargetBlockState> overworldPermafrostBlock = List.of(
                OreConfiguration.target(new TagMatchTest(BlockTags.DIRT), ModBlocks.PERMAFROST_BLOCK.get().defaultBlockState()),
                OreConfiguration.target(new BlockMatchTest(Blocks.GRASS_BLOCK), ModBlocks.PERMAFROST_BLOCK.get().defaultBlockState()));
        FeatureUtils.register(context, ORE_PERMAFROST_BLOCK, Feature.ORE, new OreConfiguration(overworldPermafrostBlock, 5));

        List<OreConfiguration.TargetBlockState> overworldVolcanicRock = List.of(
                OreConfiguration.target(deepslateOreReplaceables, ModBlocks.VOLCANIC_ROCK.get().defaultBlockState()));
        FeatureUtils.register(context, ORE_VOLCANIC_ROCK, Feature.ORE, new OreConfiguration(overworldVolcanicRock, 24));
    }

    private static OreConfiguration.TargetBlockState target(Block target, RegistrySupplier<? extends Block> block) {
        return OreConfiguration.target(new BlockMatchTest(target), block.get().defaultBlockState());
    }
}
