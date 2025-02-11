package com.github.teamfossilsarcheology.fossil.block.custom_blocks;

import dev.architectury.injectables.annotations.ExpectPlatform;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;

public class FossilLeavesBlock extends LeavesBlock {
    public FossilLeavesBlock(Properties properties) {
        super(properties);
    }

    @ExpectPlatform
    public static FossilLeavesBlock get(BlockBehaviour.Properties properties) {
        throw new AssertionError();
    }

    public static void updateInitialDistance(WorldGenLevel level, BlockPos pos, BlockState state) {
        if (!(state.getBlock() instanceof FossilLeavesBlock)) {
            return;
        }
        int i = 7;
        BlockPos.MutableBlockPos mutableBlockPos = new BlockPos.MutableBlockPos();
        loop:
        for (int x = -1; x < 2; x++) {
            for (int y = -1; y < 2; y++) {
                for (int z = -1; z < 2; z++) {
                    if (x != 0 || y != 0 || z != 0) {
                        mutableBlockPos.setWithOffset(pos, x, y, z);
                        i = Math.min(i, getDistanceAt(level.getBlockState(mutableBlockPos)) + 1);
                        if (i == 1) break loop;
                    }
                }
            }
        }
        level.setBlock(pos, state.setValue(DISTANCE, i), 3);
    }

    private static int getDistanceAt(BlockState neighbor) {
        if (neighbor.is(BlockTags.LOGS)) {
            return 0;
        }
        if (neighbor.getBlock() instanceof LeavesBlock) {
            return neighbor.getValue(DISTANCE);
        }
        return 7;
    }
}
