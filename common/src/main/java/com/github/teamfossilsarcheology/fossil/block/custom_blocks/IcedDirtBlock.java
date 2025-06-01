package com.github.teamfossilsarcheology.fossil.block.custom_blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

public class IcedDirtBlock extends Block {
    public IcedDirtBlock(Properties properties) {
        super(properties);
    }

    @Override
    public void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        if (level.getBrightness(LightLayer.BLOCK, pos) > 11 - state.getLightBlock(level, pos)) {
            melt(level, pos);
        }
    }

    protected void melt(ServerLevel level, BlockPos pos) {
        if (level.dimensionType().ultraWarm()) {
            level.removeBlock(pos, false);
            return;
        }
        level.setBlockAndUpdate(pos, Blocks.DIRT.defaultBlockState());
        level.neighborChanged(pos, Blocks.DIRT, pos);
    }
}
