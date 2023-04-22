package com.fossil.fossil.world.feature.configuration;

import com.fossil.fossil.Fossil;
import com.fossil.fossil.block.ModBlocks;
import com.fossil.fossil.world.feature.ModFeatures;
import com.fossil.fossil.world.feature.structures.ModStructures;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.data.BuiltinRegistries;
import net.minecraft.data.worldgen.StructureSets;
import net.minecraft.data.worldgen.features.FeatureUtils;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BiomeTags;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.feature.*;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.RangeConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.featuresize.TwoLayersFeatureSize;
import net.minecraft.world.level.levelgen.feature.foliageplacers.BlobFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.trunkplacers.StraightTrunkPlacer;
import net.minecraft.world.level.levelgen.heightproviders.ConstantHeight;
import net.minecraft.world.level.levelgen.structure.placement.RandomSpreadStructurePlacement;
import net.minecraft.world.level.levelgen.structure.placement.RandomSpreadType;

/**
 * Calling this class before the mod blocks have been initialized will cause a crash at the moment
 */
public class ModConfiguredFeatures {

    public static final Holder<ConfiguredFeature<TreeConfiguration, ?>> CORDAITES_TREE = FeatureUtils.register("cordaites",
            Feature.TREE, new TreeConfiguration.TreeConfigurationBuilder(
                    BlockStateProvider.simple(ModBlocks.CORDAITES_LOG.get()),
                    new StraightTrunkPlacer(5, 6, 3),
                    BlockStateProvider.simple(ModBlocks.CORDAITES_LEAVES.get()),
                    new BlobFoliagePlacer(ConstantInt.of(2), ConstantInt.of(0), 4),
                    new TwoLayersFeatureSize(1, 0, 2)).build());
    public static final Holder<ConfiguredFeature<TreeConfiguration, ?>> SIGILLARIA_TREE = FeatureUtils.register("sigillaria",
            Feature.TREE, new TreeConfiguration.TreeConfigurationBuilder(
                    BlockStateProvider.simple(ModBlocks.CORDAITES_LOG.get()),
                    new StraightTrunkPlacer(5, 6, 3),
                    BlockStateProvider.simple(ModBlocks.CORDAITES_LEAVES.get()),
                    new BlobFoliagePlacer(ConstantInt.of(2), ConstantInt.of(0), 4),
                    new TwoLayersFeatureSize(1, 0, 2)).build());
    public static final Holder<ConfiguredStructureFeature<?, ?>> HELL_BOAT = register(createKey("hell_boat"), ModStructures.HELL_BOAT.feature()
            .configured(new RangeConfiguration(ConstantHeight.of(VerticalAnchor.absolute(30))), BiomeTags.IS_NETHER));
    static {
        StructureSets.register(ResourceKey.create(Registry.STRUCTURE_SET_REGISTRY, new ResourceLocation(Fossil.MOD_ID, "hell_boat")),
                HELL_BOAT, new RandomSpreadStructurePlacement(24, 5, RandomSpreadType.LINEAR, 92182587));
    }
    public static final Holder<ConfiguredFeature<LakeFeature.Configuration, ?>> TAR_PIT = register("tar_pit", Feature.LAKE,
            new LakeFeature.Configuration(BlockStateProvider.simple(ModBlocks.TAR.get()),
                    BlockStateProvider.simple(Blocks.STONE)));
    public static final Holder<ConfiguredFeature<AshDiskConfiguration, ?>> ASH_DISK = register("ash_disk",
            ModFeatures.ASH_DISK.feature(), new AshDiskConfiguration(UniformInt.of(6, 11), false));
    public static final Holder<ConfiguredFeature<AshDiskConfiguration, ?>> MAGMA_DISK = register("magma_disk",
            ModFeatures.ASH_DISK.feature(), new AshDiskConfiguration(UniformInt.of(4, 6), true));
    public static final Holder<ConfiguredFeature<NoneFeatureConfiguration, ?>> VOLCANO_CONE = register("volcano_cone",
            ModFeatures.VOLCANO_CONE.feature(), NoneFeatureConfiguration.INSTANCE);

    private static <C extends FeatureConfiguration, F extends Feature<C>> Holder<ConfiguredFeature<C, ?>> register(String name, F feature, C config) {
        return FeatureUtils.register(Fossil.MOD_ID + ":" + name, feature, config);
    }
    private static <FC extends FeatureConfiguration, F extends StructureFeature<FC>> Holder<ConfiguredStructureFeature<?, ?>> register(ResourceKey<ConfiguredStructureFeature<?, ?>> id, ConfiguredStructureFeature<FC, F> configuredStructureFeature) {
        return BuiltinRegistries.register(BuiltinRegistries.CONFIGURED_STRUCTURE_FEATURE, id, configuredStructureFeature);
    }
    private static ResourceKey<ConfiguredStructureFeature<?, ?>> createKey(String name) {
        return ResourceKey.create(Registry.CONFIGURED_STRUCTURE_FEATURE_REGISTRY, new ResourceLocation(Fossil.MOD_ID, name));
    }
}
