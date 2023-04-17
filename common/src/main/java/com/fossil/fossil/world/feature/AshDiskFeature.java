package com.fossil.fossil.world.feature;

import com.fossil.fossil.block.ModBlocks;
import com.fossil.fossil.world.feature.configuration.AshDiskConfiguration;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;

/**
 * Custom implementation of the {@link net.minecraft.world.level.levelgen.feature.BaseDiskFeature} that has no options for height but
 * instead a boolean to enable a few additional random blocks
 */
public class AshDiskFeature extends Feature<AshDiskConfiguration> {
    public AshDiskFeature() {
        super(AshDiskConfiguration.CODEC);
    }

    @Override
    public boolean place(FeaturePlaceContext<AshDiskConfiguration> context) {
        WorldGenLevel level = context.level();
        BlockPos blockPos = context.origin();
        BlockState rock = ModBlocks.VOLCANIC_ROCK.get().defaultBlockState();
        while (level.isEmptyBlock(blockPos) && blockPos.getY() > level.getMinBuildHeight() + 2) {
            blockPos = blockPos.below();
        }
        if (!level.getBlockState(blockPos).is(ModBlocks.VOLCANIC_ROCK.get())) {
            return false;
        }
        AshDiskConfiguration config = context.config();
        boolean placed = false;
        int radius = config.radius().sample(context.random());
        for (int x = blockPos.getX() - radius - (int)(context.random().nextFloat() * radius); x <= blockPos.getX() + radius + (int)(context.random().nextFloat() * radius); ++x) {
            for (int z = blockPos.getZ() - radius - (int)(context.random().nextFloat() * radius); z <= blockPos.getZ() + radius + (int)(context.random().nextFloat() * radius); ++z) {
                int zOffset = z - blockPos.getZ();
                int xOffset = x - blockPos.getX();
                if (xOffset * xOffset + zOffset * zOffset > radius * radius) continue;
                for (int y = blockPos.getY() - 1; y <= blockPos.getY() + 1; y++) {
                    BlockPos newBlockPos = new BlockPos(x, y, z);
                    BlockState stateToReplace = level.getBlockState(newBlockPos);
                    Block blockToReplace = stateToReplace.getBlock();
                    if (!rock.is(blockToReplace)) continue;
                    placed = true;
                    float chance = context.random().nextFloat();
                    if (config.magma()) {
                        level.setBlock(newBlockPos, Blocks.MAGMA_BLOCK.defaultBlockState(), 2);
                    } else if (chance < 0.8) {
                        level.setBlock(newBlockPos, ModBlocks.VOLCANIC_ASH.get().defaultBlockState(), 2);
                    } else if (chance < 0.85) {
                        level.setBlock(newBlockPos, ModBlocks.FOSSIL.get().defaultBlockState(), 2);
                    } else {
                        level.setBlock(newBlockPos, Blocks.STONE.defaultBlockState(), 2);
                    }
                }
            }
        }
        return placed;
    }
}
