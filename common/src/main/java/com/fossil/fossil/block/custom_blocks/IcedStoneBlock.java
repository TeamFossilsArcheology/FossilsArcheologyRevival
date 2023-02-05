package com.fossil.fossil.block.custom_blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

import java.util.Random;

public class IcedStoneBlock extends Block {
    public IcedStoneBlock(Properties properties) {
        super(properties);
    }

    @Override
    public void randomTick(BlockState state, ServerLevel level, BlockPos pos, Random random) {
        if (level.getBrightness(LightLayer.BLOCK, pos) > 11 - state.getLightBlock(level, pos)) {
            this.turnIntoRock(level, pos);
        }
    }

    protected void turnIntoRock(ServerLevel level, BlockPos pos) {
        if (level.dimensionType().ultraWarm()) {
            level.removeBlock(pos, false);
            return;
        }
        level.setBlockAndUpdate(pos, Blocks.STONE.defaultBlockState());
        level.neighborChanged(pos, Blocks.STONE, pos);
    }
}
