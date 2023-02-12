package com.fossil.fossil.block.custom_blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.DirectionProperty;

import java.util.Random;

public class PermafrostBlock extends Block {
    public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;

    public PermafrostBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH));
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

    public BlockState getStateForPlacement(BlockPlaceContext p_51377_) {
        return this.defaultBlockState().setValue(FACING, p_51377_.getHorizontalDirection().getOpposite());
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> p_51385_) {
        p_51385_.add(FACING);
    }

    @Override
    public SoundType getSoundType(BlockState p_49963_) {
        return SoundType.GRASS;
    }
}