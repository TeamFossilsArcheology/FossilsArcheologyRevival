package com.github.teamfossilsarcheology.fossil.block.custom_blocks;

import dev.architectury.injectables.annotations.ExpectPlatform;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;

import java.util.Random;

public class FossilLeavesBlock extends LeavesBlock {
    public FossilLeavesBlock(Properties properties) {
        super(properties);
    }

    @ExpectPlatform
    public static FossilLeavesBlock get(BlockBehaviour.Properties properties) {
        throw new AssertionError();
    }

    @Override
    public void tick(BlockState state, ServerLevel level, BlockPos pos, Random random) {
        level.setBlock(pos, updateDistance(state, level, pos), 3);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return updateDistance(this.defaultBlockState().setValue(PERSISTENT, Boolean.TRUE), context.getLevel(), context.getClickedPos());
    }

    public static BlockState updateDistance(BlockState state, LevelAccessor level, BlockPos pos) {
        int i = 7;
        BlockPos.MutableBlockPos mutableBlockPos = new BlockPos.MutableBlockPos();

        for (Direction direction : Direction.values()) {
            mutableBlockPos.setWithOffset(pos, direction);
            i = Math.min(i, getDistanceAt(level.getBlockState(mutableBlockPos)) + 1);
            if (i == 1) {
                break;
            }
        }

        return state.setValue(DISTANCE, i);
    }

    private static int getDistanceAt(BlockState neighbor) {
        if (neighbor.is(BlockTags.LOGS)) {
            return 0;
        }
        if (neighbor.getBlock() instanceof InvisibleLeavesBlock) {
            return neighbor.getValue(DISTANCE) - 1;
        }
        if (neighbor.getBlock() instanceof LeavesBlock) {
            return neighbor.getValue(DISTANCE);
        }
        return 7;
    }
}
