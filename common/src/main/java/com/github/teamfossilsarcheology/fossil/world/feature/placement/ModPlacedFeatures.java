package com.github.teamfossilsarcheology.fossil.world.feature.placement;

import com.github.teamfossilsarcheology.fossil.FossilMod;
import com.github.teamfossilsarcheology.fossil.config.FossilConfig;
import com.github.teamfossilsarcheology.fossil.world.feature.configuration.ModConfiguredFeatures;
import dev.architectury.registry.level.biome.BiomeModifications;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.data.worldgen.features.CaveFeatures;
import net.minecraft.data.worldgen.features.MiscOverworldFeatures;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.valueproviders.ClampedNormalInt;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.placement.*;

public class ModPlacedFeatures {
    public static final ResourceKey<PlacedFeature> VOLCANO_ASH_DISK_KEY = createKey("volcano_ash_disk");
    public static final ResourceKey<PlacedFeature> VOLCANO_MAGMA_DISK_KEY = createKey("volcano_magma_disk");
    public static final ResourceKey<PlacedFeature> VOLCANO_CONE_KEY = createKey("volcano_cone");
    public static final ResourceKey<PlacedFeature> VOLCANO_VENT_KEY = createKey("volcano_vent");
    public static final ResourceKey<PlacedFeature> VOLCANO_LAVA_LAKE_KEY = createKey("volcano_lake_lava");
    public static final ResourceKey<PlacedFeature> VOLCANO_FOSSIL_KEY = createKey("volcano_fossil");
    public static final ResourceKey<PlacedFeature> MOAI_STATUE_KEY = createKey("moai_statue");

    private static ResourceKey<PlacedFeature> createKey(String name) {
        return ResourceKey.create(Registries.PLACED_FEATURE, FossilMod.location(name));
    }

    public static void bootstrap(BootstapContext<PlacedFeature> context) {
        ModOrePlacements.bootstrap(context);
        HolderGetter<ConfiguredFeature<?, ?>> holderGetter = context.lookup(Registries.CONFIGURED_FEATURE);
        Holder<ConfiguredFeature<?, ?>> volcanoAshDisk = holderGetter.getOrThrow(ModConfiguredFeatures.ASH_DISK_KEY);
        Holder<ConfiguredFeature<?, ?>> volcanoMagmaDisk = holderGetter.getOrThrow(ModConfiguredFeatures.MAGMA_DISK_KEY);
        Holder<ConfiguredFeature<?, ?>> volcanoCone = holderGetter.getOrThrow(ModConfiguredFeatures.VOLCANO_CONE_KEY);
        Holder<ConfiguredFeature<?, ?>> volcanoVent = holderGetter.getOrThrow(ModConfiguredFeatures.VOLCANO_VENT_KEY);
        Holder<ConfiguredFeature<?, ?>> volcanoLavaLake = holderGetter.getOrThrow(MiscOverworldFeatures.LAKE_LAVA);
        Holder<ConfiguredFeature<?, ?>> volcanoFossil = holderGetter.getOrThrow(CaveFeatures.FOSSIL_COAL);
        PlacementUtils.register(context, VOLCANO_ASH_DISK_KEY, volcanoAshDisk,
                CountPlacement.of(1),
                InSquarePlacement.spread(),
                PlacementUtils.HEIGHTMAP_WORLD_SURFACE,
                BiomeFilter.biome()
        );
        PlacementUtils.register(context, VOLCANO_MAGMA_DISK_KEY, volcanoMagmaDisk,
                RarityFilter.onAverageOnceEvery(3),
                InSquarePlacement.spread(),
                PlacementUtils.HEIGHTMAP_WORLD_SURFACE,
                BiomeFilter.biome()
        );
        PlacementUtils.register(context, VOLCANO_CONE_KEY, volcanoCone,
                RarityFilter.onAverageOnceEvery(30),
                PlacementUtils.HEIGHTMAP_TOP_SOLID,
                BiomeFilter.biome()
        );
        PlacementUtils.register(context, VOLCANO_VENT_KEY, volcanoVent,
                CountPlacement.of(1),
                InSquarePlacement.spread(),
                PlacementUtils.HEIGHTMAP_WORLD_SURFACE,
                BiomeFilter.biome()
        );
        PlacementUtils.register(context, VOLCANO_LAVA_LAKE_KEY, volcanoLavaLake,
                RarityFilter.onAverageOnceEvery(6),
                CountPlacement.of(6),
                InSquarePlacement.spread(),
                PlacementUtils.HEIGHTMAP_WORLD_SURFACE,
                BiomeFilter.biome()
        );
        PlacementUtils.register(context, VOLCANO_FOSSIL_KEY, volcanoFossil,
                RarityFilter.onAverageOnceEvery(10),
                InSquarePlacement.spread(),
                PlacementUtils.HEIGHTMAP_WORLD_SURFACE,
                RandomOffsetPlacement.vertical(ClampedNormalInt.of(-9, 4, -16, -5)),
                BiomeFilter.biome()
        );

        Holder<ConfiguredFeature<?, ?>> moaiStatue = holderGetter.getOrThrow(ModConfiguredFeatures.MOAI_STATUE_KEY);
        PlacementUtils.register(context, MOAI_STATUE_KEY, moaiStatue,
                RarityFilter.onAverageOnceEvery(400),
                InSquarePlacement.spread(),
                PlacementUtils.HEIGHTMAP_WORLD_SURFACE,
                BiomeFilter.biome()
        );
    }

    public static void register() {
        BiomeModifications.addProperties((context, mutable) -> {
            if (FossilConfig.isEnabled(FossilConfig.GENERATE_FOSSILS)) {
                mutable.getGenerationProperties().addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, ModOrePlacements.ORE_FOSSIL_BLOCK_UPPER);
                mutable.getGenerationProperties().addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, ModOrePlacements.ORE_FOSSIL_BLOCK_MIDDLE);
                mutable.getGenerationProperties().addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, ModOrePlacements.ORE_FOSSIL_BLOCK_DEEP);
            }
            if (FossilConfig.isEnabled(FossilConfig.GENERATE_VOLCANIC_ROCK)) {
                mutable.getGenerationProperties().addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, ModOrePlacements.ORE_VOLCANIC_ROCK);
            }
            if (FossilConfig.isEnabled(FossilConfig.GENERATE_PERMAFROST) && mutable.getClimateProperties().getTemperature() < 0.15) {
                mutable.getGenerationProperties().addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, ModOrePlacements.ORE_PERMAFROST_BLOCK);
            }
            mutable.getGenerationProperties().addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, ModOrePlacements.ORE_AMBER);
            mutable.getGenerationProperties().addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, ModOrePlacements.ORE_AMBER_BURIED);
        });
    }
}
