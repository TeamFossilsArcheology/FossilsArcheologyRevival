package com.fossil.fossil.block.custom_blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;

import java.util.Random;

public class PermafrostBlock extends Block {

    public PermafrostBlock(Properties properties) {
        super(properties);
    }

    @Override
    public void randomTick(BlockState state, ServerLevel level, BlockPos pos, Random random) {
        spread(state, level, pos, random);
    }

    private void spread(BlockState state, ServerLevel level, BlockPos pos, Random random) {
        if (level.getBrightness(LightLayer.BLOCK, pos) <= 11 - state.getLightBlock(level, pos) && (!level.canSeeSky(pos.above()) || !level.isDay())) {
            int runs = 0;
            while (runs < 20) {
                int offsetX = random.nextInt(3) - 1;
                int offsetY = random.nextInt(3) - 1;
                int offsetZ = random.nextInt(3) - 1;
                BlockPos offsetPos = pos.offset(offsetX, offsetY, offsetZ);
                BlockState offsetState = level.getBlockState(offsetPos);
                Block offsetBlock = offsetState.getBlock();
                if (offsetBlock != Blocks.WATER) {
                    if (offsetBlock != Blocks.LAVA && offsetBlock != Blocks.FIRE) {
                        runs++;
                        continue;
                    }
                    level.setBlock(pos, Blocks.STONE.defaultBlockState(), 3);
                }
                level.setBlock(offsetPos, Blocks.ICE.defaultBlockState(), 3);
                return;
            }
        } else {
            level.setBlock(pos, Blocks.DIRT.defaultBlockState(), 3);
        }
    }

    @Override
    public SoundType getSoundType(BlockState p_49963_) {
        return SoundType.GRASS;
    }
}