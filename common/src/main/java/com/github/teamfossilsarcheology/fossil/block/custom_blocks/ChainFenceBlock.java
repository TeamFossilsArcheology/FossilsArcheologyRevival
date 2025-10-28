package com.github.teamfossilsarcheology.fossil.block.custom_blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

// copied IronBarsBlock but changed the constructor to use a different collisionHeight (so that you can't jump it, like a fence)
public class ChainFenceBlock extends CrossCollisionBlock {
    public ChainFenceBlock(Properties properties) {
        super(1.0F, 1.0F, 16.0F, 16.0F, 24.0F, properties);
        this.registerDefaultState(
                this.stateDefinition.any().setValue(NORTH, false).setValue(EAST, false).setValue(SOUTH, false).setValue(WEST, false).setValue(WATERLOGGED, false)
        );
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        BlockGetter blockGetter = context.getLevel();
        BlockPos blockPos = context.getClickedPos();
        FluidState fluidState = context.getLevel().getFluidState(context.getClickedPos());
        BlockPos blockPos2 = blockPos.north();
        BlockPos blockPos3 = blockPos.south();
        BlockPos blockPos4 = blockPos.west();
        BlockPos blockPos5 = blockPos.east();
        BlockState blockState = blockGetter.getBlockState(blockPos2);
        BlockState blockState2 = blockGetter.getBlockState(blockPos3);
        BlockState blockState3 = blockGetter.getBlockState(blockPos4);
        BlockState blockState4 = blockGetter.getBlockState(blockPos5);
        return this.defaultBlockState()
                .setValue(NORTH, this.attachsTo(blockState, blockState.isFaceSturdy(blockGetter, blockPos2, Direction.SOUTH)))
                .setValue(SOUTH, this.attachsTo(blockState2, blockState2.isFaceSturdy(blockGetter, blockPos3, Direction.NORTH)))
                .setValue(WEST, this.attachsTo(blockState3, blockState3.isFaceSturdy(blockGetter, blockPos4, Direction.EAST)))
                .setValue(EAST, this.attachsTo(blockState4, blockState4.isFaceSturdy(blockGetter, blockPos5, Direction.WEST)))
                .setValue(WATERLOGGED, fluidState.getType() == Fluids.WATER);
    }

    @Override
    public BlockState updateShape(BlockState state, Direction direction, BlockState neighborState, LevelAccessor level, BlockPos pos, BlockPos neighborPos) {
        if ((Boolean)state.getValue(WATERLOGGED)) {
            level.scheduleTick(pos, Fluids.WATER, Fluids.WATER.getTickDelay(level));
        }

        return direction.getAxis().isHorizontal()
                ? state.setValue(
                (Property)PROPERTY_BY_DIRECTION.get(direction), this.attachsTo(neighborState, neighborState.isFaceSturdy(level, neighborPos, direction.getOpposite()))
        )
                : super.updateShape(state, direction, neighborState, level, pos, neighborPos);
    }

    @Override
    public VoxelShape getVisualShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return Shapes.empty();
    }

    @Override
    public boolean skipRendering(BlockState state, BlockState adjacentState, Direction direction) {
        if (adjacentState.is(this)) {
            if (!direction.getAxis().isHorizontal()) {
                return true;
            }

            if ((Boolean)state.getValue((Property)PROPERTY_BY_DIRECTION.get(direction))
                    && (Boolean)adjacentState.getValue((Property)PROPERTY_BY_DIRECTION.get(direction.getOpposite()))) {
                return true;
            }
        }

        return super.skipRendering(state, adjacentState, direction);
    }

    public final boolean attachsTo(BlockState state, boolean solidSide) {
        Block block = state.getBlock();
        // TODO: create a "connects_to_chain_fences" tag instead of this instanceof chain
        return !isExceptionForConnection(state) && solidSide ||
                block instanceof IronBarsBlock ||
                block instanceof ChainFenceBlock ||
                block instanceof FenceBlock ||
                block instanceof FenceGateBlock ||
                state.is(BlockTags.WALLS);

    }


    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(NORTH, EAST, WEST, SOUTH, WATERLOGGED);
    }
}
