package com.github.teamfossilsarcheology.fossil.world.feature;

import com.github.teamfossilsarcheology.fossil.block.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;

public class VolcanoConeFeature extends Feature<NoneFeatureConfiguration> {
    public VolcanoConeFeature() {
        super(NoneFeatureConfiguration.CODEC);
    }

    @Override
    public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> context) {
        WorldGenLevel level = context.level();
        RandomSource random = context.random();
        BlockPos blockPos = context.origin();
        while (level.isEmptyBlock(blockPos) && blockPos.getY() > level.getMinBuildHeight() + 2) {
            blockPos = blockPos.below();
        }

        int height = 10 + random.nextInt(15);
        BlockPos center = blockPos.above(height);
        int radiusHeight = Math.max(center.getY() - height, 0);
        int stopHeight = Math.max(center.getY() / 2, 0);
        int layer = 8;
        boolean first = true;
        while (center.getY() >= stopHeight) {
            if (center.getY() >= radiusHeight) {
                layer++;//Only increase radius up to a specific width/height. Otherwise, the blocks would be set in inaccessible chunks
            }
            final BlockState rock = ModBlocks.VOLCANIC_ROCK.get().defaultBlockState();
            for (float i = 0; i < layer * 0.5; i += 0.5f) {
                for (float j = 0; j < 2 * Math.PI * i + random.nextInt(2); j += 0.5f) {
                    BlockPos stonePos = BlockPos.containing(Math.floor(center.getX() + Math.sin(j) * i + random.nextInt(2)), center.getY(),
                            Math.floor(center.getZ() + Math.cos(j) * i + random.nextInt(2)));
                    if (level.isEmptyBlock(stonePos) || level.getBlockState(stonePos).is(Blocks.WATER)) {
                        level.setBlock(stonePos, rock, 18);
                    }
                }
            }
            final BlockState lava = Blocks.LAVA.defaultBlockState();
            boolean updateLava = center.getY() == stopHeight || first;
            for (float i = 0; i < (first ? 0.45f : Math.max(layer * 0.2, 1)); i += 0.5f) {
                float extra = i == 0 ? 3 : 1;
                for (float j = 0; j < 2 * Math.PI * extra + random.nextInt(2); j += 0.5f) {
                    BlockPos lavaPos = BlockPos.containing(Math.floor(center.getX() + Math.sin(j) * extra + random.nextInt(2)), center.getY(),
                            Math.floor(center.getZ() + Math.cos(j) * extra + random.nextInt(2)));
                    if (updateLava) {
                        level.setBlock(lavaPos, lava, 3);
                        Direction.Plane.HORIZONTAL.stream().map(lavaPos::relative)
                                .filter(fixPos -> level.getBlockState(fixPos).isAir())
                                .forEach(fixPos -> level.setBlock(lavaPos, rock, 18));
                    } else {
                        level.setBlock(lavaPos, lava, 18);
                    }
                }
                first = false;
            }
            center = center.below();
        }
        return true;
    }
}
