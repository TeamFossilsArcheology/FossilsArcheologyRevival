package com.fossil.fossil.world.feature.placement;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.placement.PlacementContext;
import net.minecraft.world.level.levelgen.placement.PlacementModifier;
import net.minecraft.world.level.levelgen.placement.PlacementModifierType;

import java.util.Random;
import java.util.stream.Stream;

//TODO: Breaks in Forge because the PLACEMENT_MODIFIERS registry is already frozen
public class BelowSurfacePlacement extends PlacementModifier {

    private static final PlacementModifierType<BelowSurfacePlacement> FOSSIL_HEIGHTMAP = Registry.register(Registry.PLACEMENT_MODIFIERS,
            "fossil_heightmap", () -> BelowSurfacePlacement.CODEC);
    public static final Codec<BelowSurfacePlacement> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            (Heightmap.Types.CODEC.fieldOf("fossil_heightmap")).forGetter(heightmapPlacement -> heightmapPlacement.heightmap)).apply(instance,
            BelowSurfacePlacement::new));
    private final Heightmap.Types heightmap;

    private BelowSurfacePlacement(Heightmap.Types types) {
        this.heightmap = types;
    }

    public static BelowSurfacePlacement onHeightmap(Heightmap.Types heightmap) {
        return new BelowSurfacePlacement(heightmap);
    }

    @Override
    public Stream<BlockPos> getPositions(PlacementContext context, Random random, BlockPos pos) {
        int j = pos.getZ();
        int i = pos.getX();
        int k = context.getHeight(this.heightmap, i, j) - (5 + random.nextInt(12));
        if (k > context.getMinBuildHeight()) {
            return Stream.of(new BlockPos(i, k, j));
        }
        return Stream.empty();
    }

    @Override
    public PlacementModifierType<?> type() {
        return FOSSIL_HEIGHTMAP;
    }
}
