package com.fossil.fossil.world.feature.placement;

import com.fossil.fossil.world.feature.ModOreFeatures;
import net.minecraft.core.Holder;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.placement.*;

import java.util.List;

public class ModOrePlacement {

    public static final Holder<PlacedFeature> FOSSIL_BLOCK_PLACED = PlacementUtils.register("fossil_block_placed",
            ModOreFeatures.FOSSIL_BLOCK, ModOrePlacement.commonOrePlacement(17, // VeinsPerChunk
                    HeightRangePlacement.triangle(VerticalAnchor.aboveBottom(-0), VerticalAnchor.aboveBottom(256))));

    public static final Holder<PlacedFeature> VOLCANIC_ROCK_PLACED = PlacementUtils.register("volcanic_rock_placed",
            ModOreFeatures.VOLCANIC_ROCK, ModOrePlacement.commonOrePlacement(2,
                    HeightRangePlacement.uniform(VerticalAnchor.bottom(), VerticalAnchor.absolute(0))));

    public static final Holder<PlacedFeature> PERMAFROST_BLOCK_PLACED = PlacementUtils.register("permafrost_block_placed",
            ModOreFeatures.PERMAFROST_BLOCK, ModOrePlacement.commonOrePlacement(3, // VeinsPerChunk
                    HeightRangePlacement.triangle(VerticalAnchor.aboveBottom(100), VerticalAnchor.aboveBottom(256))));

    public static List<PlacementModifier> orePlacement(PlacementModifier placementModifier, PlacementModifier placementModifier2) {
        return List.of(placementModifier, InSquarePlacement.spread(), placementModifier2, BiomeFilter.biome());
    }

    public static List<PlacementModifier> commonOrePlacement(int count, PlacementModifier placementModifier) {
        return orePlacement(CountPlacement.of(count), placementModifier);
    }

    public static List<PlacementModifier> rareOrePlacement(int rarity, PlacementModifier placementModifier) {
        return orePlacement(RarityFilter.onAverageOnceEvery(rarity), placementModifier);
    }
}
