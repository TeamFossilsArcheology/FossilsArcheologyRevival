package com.fossil.fossil.world.feature.placement;

import com.fossil.fossil.world.feature.ModOreFeatures;
import com.fossil.fossil.world.feature.configuration.ModConfiguredFeatures;
import dev.architectury.registry.level.biome.BiomeModifications;
import net.minecraft.core.Holder;
import net.minecraft.data.worldgen.features.CaveFeatures;
import net.minecraft.data.worldgen.features.MiscOverworldFeatures;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.util.valueproviders.ClampedNormalInt;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.placement.*;

import java.util.List;

public class ModPlacedFeatures {
    private static Holder<PlacedFeature> ashDiskVolcano;
    private static Holder<PlacedFeature> magmaDiskVolcano;
    private static Holder<PlacedFeature> coneVolcano;
    public static final Holder<PlacedFeature> LAKE_LAVA_VOLCANO = PlacementUtils.register("volcano_lake_lava", MiscOverworldFeatures.LAKE_LAVA,
            RarityFilter.onAverageOnceEvery(6), CountPlacement.of(6), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE,
            BiomeFilter.biome());
    public static final Holder<PlacedFeature> FOSSIL_VOLCANO = PlacementUtils.register("volcano_fossil", CaveFeatures.FOSSIL_COAL,
            RarityFilter.onAverageOnceEvery(10), InSquarePlacement.spread(), HeightmapPlacement.onHeightmap(Heightmap.Types.WORLD_SURFACE_WG),
            RandomOffsetPlacement.vertical(ClampedNormalInt.of(-9, 4, -16, -5)), BiomeFilter.biome());

    public static void register() {
        //Features that depend on ModConfiguredFeatures can't be called before the block registries have been initialized
        ashDiskVolcano = PlacementUtils.register("volcano_ash_disk", ModConfiguredFeatures.ASH_DISK,
                CountPlacement.of(1), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome());
        magmaDiskVolcano = PlacementUtils.register("volcano_magma_disk", ModConfiguredFeatures.MAGMA_DISK,
                RarityFilter.onAverageOnceEvery(3), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome());
        coneVolcano = PlacementUtils.register("volcano_cone", ModConfiguredFeatures.VOLCANO_CONE,
                RarityFilter.onAverageOnceEvery(30), PlacementUtils.HEIGHTMAP_TOP_SOLID, BiomeFilter.biome());
        var tarPitPlaced = PlacementUtils.register("tar_pit_placed", ModConfiguredFeatures.TAR_PIT, RarityFilter.onAverageOnceEvery(100),
                InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE);
        var hellBoat = PlacementUtils.register("hell_boat", ModConfiguredFeatures.HELL_BOAT, RarityFilter.onAverageOnceEvery(300),
                InSquarePlacement.spread());

        var fossilBlockPlaced = PlacementUtils.register("fossil_block_placed", ModOreFeatures.FOSSIL_BLOCK, commonOrePlacement(17, // VeinsPerChunk
                HeightRangePlacement.triangle(VerticalAnchor.aboveBottom(-0), VerticalAnchor.aboveBottom(256))));

        var volcanicRockPlaced = PlacementUtils.register("volcanic_rock_placed", ModOreFeatures.VOLCANIC_ROCK,
                commonOrePlacement(2, HeightRangePlacement.uniform(VerticalAnchor.bottom(), VerticalAnchor.absolute(0))));

        var permafrostBlockPlaced = PlacementUtils.register("permafrost_block_placed", ModOreFeatures.PERMAFROST_BLOCK,
                commonOrePlacement(3, // VeinsPerChunk
                        HeightRangePlacement.triangle(VerticalAnchor.aboveBottom(100), VerticalAnchor.aboveBottom(256))));

        BiomeModifications.addProperties((context, mutable) -> {
            mutable.getGenerationProperties().addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, fossilBlockPlaced);
            mutable.getGenerationProperties().addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, volcanicRockPlaced);
            mutable.getGenerationProperties().addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, permafrostBlockPlaced);
            if (mutable.getCategory() == Biome.BiomeCategory.SWAMP) {
                mutable.getGenerationProperties().addFeature(GenerationStep.Decoration.LAKES, tarPitPlaced);
            }
            if (mutable.getCategory() == Biome.BiomeCategory.NETHER) {
                mutable.getGenerationProperties().addFeature(GenerationStep.Decoration.SURFACE_STRUCTURES, hellBoat);
            }
        });
    }

    public static Holder<PlacedFeature> ashDiskVolcano() {
        return ashDiskVolcano;
    }

    public static Holder<PlacedFeature> magmaDiskVolcano() {
        return magmaDiskVolcano;
    }

    public static Holder<PlacedFeature> coneVolcano() {
        return coneVolcano;
    }

    private static List<PlacementModifier> orePlacement(PlacementModifier placementModifier, PlacementModifier placementModifier2) {
        return List.of(placementModifier, InSquarePlacement.spread(), placementModifier2, BiomeFilter.biome());
    }

    private static List<PlacementModifier> commonOrePlacement(int count, PlacementModifier placementModifier) {
        return orePlacement(CountPlacement.of(count), placementModifier);
    }

    private static List<PlacementModifier> rareOrePlacement(int rarity, PlacementModifier placementModifier) {
        return orePlacement(RarityFilter.onAverageOnceEvery(rarity), placementModifier);
    }
}
