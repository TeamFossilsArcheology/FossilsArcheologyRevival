package com.github.teamfossilsarcheology.fossil.block.custom_blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class AmberChunkBlock extends HorizontalDirectionalBlock {
    private static final VoxelShape NORTH_SHAPE = Shapes.or(Block.box(2, 0, 5.8, 6, 5, 10.8), Block.box(5.7, 0, 5, 11.7, 7, 11), Block.box(11, 0, 5.6, 14, 4, 9.6));
    private static final VoxelShape EAST_SHAPE = Shapes.or(Block.box(5.2, 0, 2, 10.2, 5, 6), Block.box(5, 0, 5.7, 11, 7, 11.7), Block.box(6.4, 0, 11, 10.4, 4, 14));
    private static final VoxelShape SOUTH_SHAPE = Shapes.or(Block.box(10, 0, 5.2, 14, 5, 10.2), Block.box(4.3, 0, 5, 10.3, 7, 11), Block.box(2, 0, 6.4, 5, 4, 10.4));
    private static final VoxelShape WEST_SHAPE = Shapes.or(Block.box(5.8, 0, 10, 10.8, 5, 14), Block.box(5, 0, 4.3, 11, 7, 10.3), Block.box(5.6, 0, 2, 9.6, 4, 5));

    public AmberChunkBlock(Properties properties) {
        super(properties);
        registerDefaultState(defaultBlockState().setValue(FACING, Direction.NORTH));
    }

    @Override
    public @NotNull VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return switch (state.getValue(FACING)) {
            case NORTH -> NORTH_SHAPE;
            case SOUTH -> SOUTH_SHAPE;
            case WEST -> WEST_SHAPE;
            default -> EAST_SHAPE;
        };
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return super.getStateForPlacement(context).setValue(FACING, context.getHorizontalDirection().getOpposite());
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING);
    }
}
