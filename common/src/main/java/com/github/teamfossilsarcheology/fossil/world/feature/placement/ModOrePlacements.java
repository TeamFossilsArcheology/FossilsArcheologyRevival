package com.github.teamfossilsarcheology.fossil.world.feature.placement;

import com.github.teamfossilsarcheology.fossil.FossilMod;
import com.github.teamfossilsarcheology.fossil.world.feature.ModOreFeatures;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.placement.*;

import java.util.List;

public class ModOrePlacements {
    public static final ResourceKey<PlacedFeature> ORE_AMBER = createKey("ore_amber");
    public static final ResourceKey<PlacedFeature> ORE_AMBER_BURIED = createKey("ore_amber_buried");
    public static final ResourceKey<PlacedFeature> ORE_FOSSIL_BLOCK_UPPER = createKey("ore_fossil_block_no_sandstone_upper");
    public static final ResourceKey<PlacedFeature> ORE_FOSSIL_BLOCK_MIDDLE = createKey("ore_fossil_block_middle");
    public static final ResourceKey<PlacedFeature> ORE_FOSSIL_BLOCK_DEEP = createKey("ore_fossil_block_deep");
    public static final ResourceKey<PlacedFeature> ORE_PERMAFROST_BLOCK = createKey("ore_permafrost_block");
    public static final ResourceKey<PlacedFeature> ORE_VOLCANIC_ROCK = createKey("ore_volcanic_rock");

    private static ResourceKey<PlacedFeature> createKey(String name) {
        return ResourceKey.create(Registries.PLACED_FEATURE, FossilMod.location(name));
    }

    public static void bootstrap(BootstapContext<PlacedFeature> context) {
        HolderGetter<ConfiguredFeature<?, ?>> features = context.lookup(Registries.CONFIGURED_FEATURE);
        Holder<ConfiguredFeature<?, ?>> amberOre = features.getOrThrow(ModOreFeatures.ORE_AMBER);
        Holder<ConfiguredFeature<?, ?>> buriedAmberOre = features.getOrThrow(ModOreFeatures.ORE_AMBER_BURIED);
        Holder<ConfiguredFeature<?, ?>> fossilBlockOre = features.getOrThrow(ModOreFeatures.ORE_FOSSIL_BLOCK);
        Holder<ConfiguredFeature<?, ?>> permafrostBlockOre = features.getOrThrow(ModOreFeatures.ORE_PERMAFROST_BLOCK);
        Holder<ConfiguredFeature<?, ?>> volcanicRockOre = features.getOrThrow(ModOreFeatures.ORE_VOLCANIC_ROCK);

        PlacementUtils.register(context, ORE_AMBER, amberOre,
                commonOrePlacement(2, HeightRangePlacement.triangle(VerticalAnchor.absolute(-10), VerticalAnchor.absolute(80))));
        PlacementUtils.register(context, ORE_AMBER_BURIED, buriedAmberOre,
                commonOrePlacement(5, HeightRangePlacement.triangle(VerticalAnchor.absolute(-10), VerticalAnchor.absolute(80))));

        PlacementUtils.register(context, ORE_FOSSIL_BLOCK_UPPER, fossilBlockOre,
                commonOrePlacement(13, HeightRangePlacement.triangle(VerticalAnchor.absolute(76), VerticalAnchor.top())));
        PlacementUtils.register(context, ORE_FOSSIL_BLOCK_MIDDLE, fossilBlockOre,
                commonOrePlacement(13, HeightRangePlacement.triangle(VerticalAnchor.absolute(0), VerticalAnchor.absolute(75))));
        PlacementUtils.register(context, ORE_FOSSIL_BLOCK_DEEP, fossilBlockOre,
                commonOrePlacement(13, HeightRangePlacement.triangle(VerticalAnchor.aboveBottom(0), VerticalAnchor.absolute(0))));
        PlacementUtils.register(context, ORE_PERMAFROST_BLOCK, permafrostBlockOre,
                commonOrePlacement(12, HeightRangePlacement.uniform(VerticalAnchor.aboveBottom(100), VerticalAnchor.aboveBottom(256))));
        PlacementUtils.register(context, ORE_VOLCANIC_ROCK, volcanicRockOre,
                commonOrePlacement(2, HeightRangePlacement.uniform(VerticalAnchor.bottom(), VerticalAnchor.absolute(0))));

    }

    private static List<PlacementModifier> orePlacement(PlacementModifier placementModifier, PlacementModifier placementModifier2) {
        return List.of(placementModifier, InSquarePlacement.spread(), placementModifier2, BiomeFilter.biome());
    }

    private static List<PlacementModifier> commonOrePlacement(int count, PlacementModifier placementModifier) {
        return orePlacement(CountPlacement.of(count), placementModifier);
    }
}
