package com.fossil.fossil.world.feature;

import net.minecraft.core.Holder;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.placement.HeightRangePlacement;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;

public class ModPlacedFeatures {
    public static final Holder<PlacedFeature> FOSSIL_BLOCK_PLACED = PlacementUtils.register("fossil_block_placed",
            ModConfiguredFeatures.FOSSIL_BLOCK, ModOrePlacement.commonOrePlacement(17, // VeinsPerChunk
                    HeightRangePlacement.triangle(VerticalAnchor.aboveBottom(-0), VerticalAnchor.aboveBottom(256))));

    public static final Holder<PlacedFeature> VOLCANIC_ROCK_PLACED = PlacementUtils.register("volcanic_rock_placed",
            ModConfiguredFeatures.VOLCANIC_ROCK, ModOrePlacement.commonOrePlacement(2,
                    HeightRangePlacement.uniform(VerticalAnchor.bottom(), VerticalAnchor.absolute(0))));

    public static final Holder<PlacedFeature> PERMAFROST_BLOCK_PLACED = PlacementUtils.register("permafrost_block_placed",
            ModConfiguredFeatures.PERMAFROST_BLOCK, ModOrePlacement.commonOrePlacement(3, // VeinsPerChunk
                    HeightRangePlacement.triangle(VerticalAnchor.aboveBottom(100), VerticalAnchor.aboveBottom(256))));
}
