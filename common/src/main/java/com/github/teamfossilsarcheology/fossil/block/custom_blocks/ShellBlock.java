package com.github.teamfossilsarcheology.fossil.block.custom_blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ShellBlock extends HorizontalDirectionalBlock {
    private static final VoxelShape NORTH_SHAPE = Shapes.or(Block.box(4.5, 0, 3.5, 11.5, 15, 16), Block.box(5, 1, 0.1, 11, 8, 3.6), Block.box(4.5, 0, 0, 11.5, 3, 3.5));
    private static final VoxelShape WEST_SHAPE = Shapes.or(Block.box(3.5, 0, 4.5, 16, 15, 11.5), Block.box(0.1, 1, 5, 3.6, 8, 11), Block.box(0, 0, 4.5, 3.5, 3, 11.5));
    private static final VoxelShape SOUTH_SHAPE = Shapes.or(Block.box(4.5, 0, 0, 11.5, 15, 12.5), Block.box(5, 1, 12.4, 11, 8, 15.9), Block.box(4.5, 0, 12.5, 11.5, 3, 16));
    private static final VoxelShape EAST_SHAPE = Shapes.or(Block.box(0, 0, 4.5, 12.5, 15, 11.5), Block.box(12.4, 1, 5, 15.9, 8, 11), Block.box(12.5, 0, 4.5, 16, 3, 11.5));

    public ShellBlock(Properties properties) {
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

    @Override
    public boolean isPathfindable(BlockState state, BlockGetter level, BlockPos pos, PathComputationType type) {
        return false;
    }
}
