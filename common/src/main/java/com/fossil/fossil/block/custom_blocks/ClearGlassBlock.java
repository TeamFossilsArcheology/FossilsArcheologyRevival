package com.fossil.fossil.block.custom_blocks;

import com.fossil.fossil.block.IDinoUnbreakable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.AbstractGlassBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.PipeBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Map;

public class ClearGlassBlock extends AbstractGlassBlock implements IDinoUnbreakable {
    public static final BooleanProperty NORTH = BlockStateProperties.NORTH;
    public static final BooleanProperty EAST = BlockStateProperties.EAST;
    public static final BooleanProperty SOUTH = BlockStateProperties.SOUTH;
    public static final BooleanProperty WEST = BlockStateProperties.WEST;
    public static final BooleanProperty UP = BlockStateProperties.UP;
    public static final BooleanProperty DOWN = BlockStateProperties.DOWN;
    protected static final Map<Direction, BooleanProperty> PROPERTY_BY_DIRECTION = PipeBlock.PROPERTY_BY_DIRECTION;

    public ClearGlassBlock(Properties properties) {
        super(properties);
        registerDefaultState(
                getStateDefinition().any().setValue(NORTH, false).setValue(EAST, false).setValue(SOUTH, false).setValue(WEST, false).setValue(UP,
                        false).setValue(DOWN, false));
    }


    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        BlockPos self = context.getClickedPos();
        Level level = context.getLevel();
        return super.getStateForPlacement(context).setValue(NORTH, connectsTo(level, self.north())).setValue(EAST,
                connectsTo(level, self.east())).setValue(SOUTH, connectsTo(level, self.south())).setValue(WEST,
                connectsTo(level, self.west())).setValue(UP, connectsTo(level, self.above())).setValue(DOWN, connectsTo(level, self.below()));
    }

    protected boolean connectsTo(Level level, BlockPos other) {
        return level.getBlockState(other).getBlock() == this;
    }

    @SuppressWarnings("deprecation")
    @Override
    public @NotNull BlockState updateShape(BlockState state, Direction direction, BlockState neighborState, LevelAccessor level, BlockPos currentPos,
                                           BlockPos neighborPos) {
        return state.setValue(PROPERTY_BY_DIRECTION.get(direction), neighborState.getBlock() == this);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(NORTH, EAST, SOUTH, WEST, UP, DOWN);
    }
}
