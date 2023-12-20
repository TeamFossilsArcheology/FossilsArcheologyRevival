package com.fossil.fossil.block.custom_blocks;

import com.fossil.fossil.config.FossilConfig;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;

import java.util.Random;

public class FernsBlock extends BushBlock implements BonemealableBlock {
    public static final int LOWER_MAX_AGE = 4;
    public static final int UPPER_MAX_AGE = 6;
    public static final IntegerProperty AGE = IntegerProperty.create("age", 0, 7);;
    private static final VoxelShape[] SHAPE_BY_AGE = new VoxelShape[]{Block.box(0, 0, 0, 16, 11, 16), Block.box(0, 0, 0, 16, 12.0,
            16), Block.box(0, 0, 0, 16, 13, 16), Block.box(0, 0, 0, 16, 14, 16), Block.box(0, 0, 0, 16, 15,
            16), Block.box(0, 0, 0, 16, 16, 16), Block.box(0, 0, 0, 16, 2, 16), Block.box(0, 0, 0, 16, 4, 16)};

    public FernsBlock() {
        super(BlockBehaviour.Properties.of(Material.PLANT).noCollission().randomTicks().instabreak().sound(SoundType.CROP));
        this.registerDefaultState(stateDefinition.any().setValue(AGE, 0));
    }

    public static boolean isUnderTree(BlockGetter level, BlockPos pos) {
        for (int i = 0; i <= 128; ++i) {
            if (level.getBlockState(pos.above(i)).is(BlockTags.LEAVES)) {
                return true;
            }
        }
        return false;
    }

    @Override
    protected boolean mayPlaceOn(BlockState state, BlockGetter level, BlockPos pos) {
        return super.mayPlaceOn(state, level, pos) && level.getBlockState(pos.above()).is(Blocks.AIR) && isUnderTree(level, pos);
    }

    public boolean isUpper(BlockState state) {
        return state.getValue(AGE) > LOWER_MAX_AGE;
    }

    @Override
    public boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
        if (isUpper(state)) {
            BlockState blockState = level.getBlockState(pos.below());
            return blockState.is(this) && !isUpper(blockState);
        }
        return super.mayPlaceOn(level.getBlockState(pos.below()), level, pos.below()) && isUnderTree(level, pos.above());
    }

    @Override
    public void randomTick(BlockState state, ServerLevel level, BlockPos pos, Random random) {
        super.randomTick(state, level, pos, random);
        tryAgeUp(state, level, pos, random);
    }

    private void tryAgeUp(BlockState state, ServerLevel level, BlockPos pos, Random random) {
        int age = state.getValue(AGE);
        if (!isUpper(state) && random.nextInt(FossilConfig.getInt(FossilConfig.FERN_TICK_RATE)) == 0) {
            age++;
            if (age == LOWER_MAX_AGE - 1) {
                if (!level.isEmptyBlock(pos.above())) {
                    age--;
                } else {
                    //Create new upper block
                    level.setBlockAndUpdate(pos.above(), defaultBlockState().setValue(AGE, LOWER_MAX_AGE + 1));
                }
            } else if (age == LOWER_MAX_AGE) {
                BlockState upperState = level.getBlockState(pos.above());
                if (upperState.is(this)) {
                    level.setBlockAndUpdate(pos.above(), upperState.setValue(AGE, UPPER_MAX_AGE));
                    age = 3;
                }
            }
            level.setBlockAndUpdate(pos, state.setValue(AGE, age));
            spread(state, level, pos, age);
        }
    }

    private void spread(BlockState state, ServerLevel level, BlockPos pos, int age) {
        if (age >= 3) {
            BlockPos.MutableBlockPos mutable = pos.mutable();
            for (int x = -1; x <= 1; x++) {
                for (int y = -1; y <= 1; ++y) {
                    for (int z = -1; z <= 1; ++z) {
                        if ((x != 0 || y != 0 || z != 0) && mayPlaceOn(state, level, mutable.setWithOffset(pos, x, y - 1, z))) {
                            level.setBlockAndUpdate(mutable, defaultBlockState().setValue(AGE, 0));
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
    public @NotNull VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return SHAPE_BY_AGE[state.getValue(AGE)];
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
        tryAgeUp(state, level, pos, random);
    }
}