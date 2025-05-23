package com.github.teamfossilsarcheology.fossil.world.feature.placement;

import com.github.teamfossilsarcheology.fossil.FossilMod;
import com.github.teamfossilsarcheology.fossil.config.FossilConfig;
import com.github.teamfossilsarcheology.fossil.world.feature.ModOreFeatures;
import dev.architectury.registry.level.biome.BiomeModifications;
import net.minecraft.core.Registry;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.placement.*;

import java.util.List;

public class ModPlacedFeatures {
    public static final ResourceKey<PlacedFeature> MOAI_STATUE_KEY = createKey("moai_statue");

    private static ResourceKey<PlacedFeature> createKey(String name) {
        return ResourceKey.create(Registry.PLACED_FEATURE_REGISTRY, FossilMod.location(name));
    }

    public static void register() {
        //Features that depend on ModConfiguredFeatures can't be called before the block registries have been initialized

        var upperFossilBlockPlaced = PlacementUtils.register("fossil_block_no_sandstone_placed_upper", ModOreFeatures.FOSSIL_BLOCK_NO_SANDSTONE,
                commonOrePlacement(FossilConfig.FOSSIL_ORE_RARITY, // VeinsPerChunk
                        HeightRangePlacement.triangle(VerticalAnchor.absolute(76), VerticalAnchor.top())));
        var middleFossilBlockPlaced = PlacementUtils.register("fossil_block_placed_middle", ModOreFeatures.FOSSIL_BLOCK,
                commonOrePlacement(FossilConfig.FOSSIL_ORE_RARITY, // VeinsPerChunk
                        HeightRangePlacement.triangle(VerticalAnchor.absolute(0), VerticalAnchor.absolute(75))));
        var deepFossilBlockPlaced = PlacementUtils.register("fossil_block_placed_deep", ModOreFeatures.FOSSIL_BLOCK,
                commonOrePlacement(FossilConfig.FOSSIL_ORE_RARITY, // VeinsPerChunk
                        HeightRangePlacement.uniform(VerticalAnchor.aboveBottom(0), VerticalAnchor.absolute(0))));

        var volcanicRockPlaced = PlacementUtils.register("volcanic_rock_placed", ModOreFeatures.VOLCANIC_ROCK,
                commonOrePlacement(2, HeightRangePlacement.uniform(VerticalAnchor.bottom(), VerticalAnchor.absolute(0))));

        var permafrostBlockPlaced = PlacementUtils.register("permafrost_block_placed", ModOreFeatures.PERMAFROST_BLOCK,
                commonOrePlacement(FossilConfig.PERMAFROST_RARITY, // VeinsPerChunk
                        HeightRangePlacement.uniform(VerticalAnchor.aboveBottom(100), VerticalAnchor.aboveBottom(256))));
        var oreAmber = PlacementUtils.register("amber_ore_placed", ModOreFeatures.ORE_AMBER,
                commonOrePlacement(2, HeightRangePlacement.triangle(VerticalAnchor.absolute(-10), VerticalAnchor.absolute(80))));
        var oreAmberBuried = PlacementUtils.register("ore_amber_buried", ModOreFeatures.ORE_AMBER_BURIED,
                commonOrePlacement(5, HeightRangePlacement.triangle(VerticalAnchor.absolute(-10), VerticalAnchor.absolute(80))));
        BiomeModifications.addProperties((context, mutable) -> {
            if (FossilConfig.isEnabled(FossilConfig.GENERATE_FOSSILS)) {
                mutable.getGenerationProperties().addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, upperFossilBlockPlaced);
                mutable.getGenerationProperties().addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, middleFossilBlockPlaced);
                mutable.getGenerationProperties().addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, deepFossilBlockPlaced);
            }
            if (FossilConfig.isEnabled(FossilConfig.GENERATE_VOLCANIC_ROCK)) {
                mutable.getGenerationProperties().addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, volcanicRockPlaced);
            }
            if (FossilConfig.isEnabled(FossilConfig.GENERATE_PERMAFROST) && mutable.getClimateProperties().getTemperature() < 0.15) {
                mutable.getGenerationProperties().addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, permafrostBlockPlaced);
            }
            mutable.getGenerationProperties().addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, oreAmber);
            mutable.getGenerationProperties().addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, oreAmberBuried);
        });
    }

    private static List<PlacementModifier> orePlacement(PlacementModifier placementModifier, PlacementModifier placementModifier2) {
        return List.of(placementModifier, InSquarePlacement.spread(), placementModifier2, BiomeFilter.biome());
    }

    private static List<PlacementModifier> commonOrePlacement(String configEntry, PlacementModifier placementModifier) {
        return orePlacement(new LazyCountPlacement(configEntry), placementModifier);
    }

    private static List<PlacementModifier> commonOrePlacement(int count, PlacementModifier placementModifier) {
        return orePlacement(CountPlacement.of(count), placementModifier);
    }

    private static List<PlacementModifier> rareOrePlacement(int rarity, PlacementModifier placementModifier) {
        return orePlacement(RarityFilter.onAverageOnceEvery(rarity), placementModifier);
    }
}
