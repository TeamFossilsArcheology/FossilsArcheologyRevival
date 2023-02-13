package com.fossil.fossil.block.custom_blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BushBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.Random;

public class FernsBlock extends BushBlock {
    public static final int LOWER_MAX_AGE = 5;
    public static final int UPPER_MAX_AGE = 7;
    public static final IntegerProperty AGE = BlockStateProperties.AGE_7;
    private static final VoxelShape[] SHAPE_BY_AGE = new VoxelShape[]{Block.box(0, 0, 0, 16, 11, 16), Block.box(0, 0, 0, 16, 12.0,
            16), Block.box(0, 0, 0, 16, 13, 16), Block.box(0, 0, 0, 16, 14, 16), Block.box(0, 0, 0, 16, 15,
            16), Block.box(0, 0, 0, 16, 16, 16), Block.box(0, 0, 0, 16, 2, 16), Block.box(0, 0, 0, 16, 4, 16)};

    public FernsBlock() {
        super(BlockBehaviour.Properties.of(Material.PLANT).noCollission().randomTicks().instabreak().sound(SoundType.CROP));
        //TODO: Drops & survive state
        this.registerDefaultState(this.stateDefinition.any().setValue(AGE, 0));
    }

    public static boolean isUnderTree(BlockGetter level, BlockPos pos) {
        for (int i = 0; i <= 128; ++i) {
            if (level.getBlockState(pos.above(i)).getMaterial() == Material.LEAVES) {
                return true;
            }
        }
        return false;
    }

    @Override
    protected boolean mayPlaceOn(BlockState state, BlockGetter level, BlockPos pos) {
        return super.mayPlaceOn(state, level, pos) && level.getBlockState(pos.above()).is(Blocks.AIR) && isUnderTree(level, pos);
    }

    @Override
    public boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
        return (super.mayPlaceOn(level.getBlockState(pos.below()), level, pos.below()) || level.getBlockState(pos.below()).is(this)) && isUnderTree(level, pos.above());
    }

    @Override
    public void randomTick(BlockState state, ServerLevel level, BlockPos pos, Random random) {
        int age = state.getValue(AGE);
        int fernTickRate = 10;
        if (isUnderTree(level, pos) && age < UPPER_MAX_AGE && random.nextInt(fernTickRate) == 0) {
            if (!level.getBlockState(pos.below()).is(this) || age > LOWER_MAX_AGE) {
                age++;
                if (age == LOWER_MAX_AGE - 1) {
                    if (!level.isEmptyBlock(pos.above())) {
                        age--;
                    } else {
                        level.setBlockAndUpdate(pos.above(), defaultBlockState().setValue(AGE, age + 2));
                    }
                } else if (age == LOWER_MAX_AGE) {
                    if (!level.getBlockState(pos.above()).is(this)) {
                        age = 3;
                    }
                }
                level.setBlockAndUpdate(pos, defaultBlockState().setValue(AGE, age));
            }
        }
        spread(state, level, pos, age);
    }

    private void spread(BlockState state, ServerLevel level, BlockPos pos, int age) {
        if (age % 7 >= 3) {
            for (int x = -1; x <= 1; x++) {
                for (int y = -1; y <= 1; ++y) {
                    for (int z = -1; z <= 1; ++z) {
                        if ((x != 0 || y != 0 || z != 0) && mayPlaceOn(state, level, pos.offset(x, y - 1, z))) {
                            level.setBlockAndUpdate(pos.offset(x, y, z), defaultBlockState().setValue(AGE, 0));
                        }
                    }
                }
            }
        }
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(AGE);
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return SHAPE_BY_AGE[state.getValue(AGE)];
    }
}