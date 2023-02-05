package com.fossil.fossil.block.custom_blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.DoublePlantBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.Random;

public class TallFlowerBlock extends DoublePlantBlock implements BonemealableBlock {
    private final VoxelShape shape;
    public TallFlowerBlock(Properties properties, VoxelShape shape) {
        super(properties);
        this.shape = shape;
        //TODO: Shape
    }

    @Override
    public boolean isValidBonemealTarget(BlockGetter level, BlockPos pos, BlockState state, boolean isClient) {
        return true;
    }

    @Override
    public boolean isBonemealSuccess(Level level, Random random, BlockPos pos, BlockState state) {
        return true;
    }

    @Override
    public void performBonemeal(ServerLevel level, Random random, BlockPos pos, BlockState state) {
        int maxTries = random.nextInt(2);
        int tries = 0;
        int timeout = 0;
        while (tries < maxTries && timeout < 101) {
            timeout++;
            BlockPos tryPos = pos.offset(random.nextInt(10) - 4, random.nextInt(8) - 4, random.nextInt(10) - 4);
            if (level.isEmptyBlock(tryPos.above()) && level.isEmptyBlock(tryPos.above(2)) && canPlant(level, tryPos)) {
                tries++;
                level.setBlock(tryPos.above(), defaultBlockState().setValue(HALF, DoubleBlockHalf.LOWER), 2);
                level.setBlock(tryPos.above(2), defaultBlockState().setValue(HALF, DoubleBlockHalf.UPPER), 2);
            }
        }
    }

    private boolean canPlant(Level level, BlockPos blockPos) {
        return this.mayPlaceOn(level.getBlockState(blockPos), level, blockPos);
    }
}
