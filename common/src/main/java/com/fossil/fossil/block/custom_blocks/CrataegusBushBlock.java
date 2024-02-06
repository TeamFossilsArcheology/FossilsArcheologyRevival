package com.fossil.fossil.block.custom_blocks;

import com.fossil.fossil.block.PrehistoricPlantInfo;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;

public class CrataegusBushBlock extends TallBerryBushBlock {
    private static final VoxelShape EMPTY = Block.box(0, 0, 0, 0, 0, 0);
    public static final IntegerProperty AGE = BlockStateProperties.AGE_3;
    public CrataegusBushBlock(VoxelShape shape, PrehistoricPlantInfo info) {
        super(shape, info);
    }

    @Override
    public @NotNull VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        if (state.getValue(HALF) == DoubleBlockHalf.UPPER && state.getValue(AGE) == 0) {
            return EMPTY;
        }
        return super.getShape(state, level, pos, context);
    }

    @Override
    public IntegerProperty ageProperty() {
        return AGE;
    }
}
