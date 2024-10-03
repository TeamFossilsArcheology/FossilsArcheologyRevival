package com.github.teamfossilsarcheology.fossil.block.custom_blocks;

import com.github.teamfossilsarcheology.fossil.block.entity.AnuBarrierBlockEntity;
import com.github.teamfossilsarcheology.fossil.block.entity.ModBlockEntities;
import com.github.teamfossilsarcheology.fossil.util.Version;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.EntityCollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class AnuBarrierOriginBlock extends BaseEntityBlock {
    public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;
    protected static final VoxelShape X_AXIS_AABB = Block.box(0, 0, 6, 16, 16, 10);
    protected static final VoxelShape Z_AXIS_AABB = Block.box(6, 0, 0, 10, 16, 16);

    public AnuBarrierOriginBlock(Properties properties) {
        super(properties);
        registerDefaultState(stateDefinition.any().setValue(FACING, Direction.SOUTH));
    }

    @Override
    public void neighborChanged(BlockState state, Level level, BlockPos pos, Block block, BlockPos fromPos, boolean isMoving) {
        if (level.isClientSide || !level.getBlockState(pos).is(this)) {
            return;
        }
        if (Version.debugEnabled() && block instanceof LeverBlock) {
            ((AnuBarrierBlockEntity) level.getBlockEntity(pos)).setEnabled(level.hasNeighborSignal(pos));
        }
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new AnuBarrierBlockEntity(pos, state);
    }

    @Override
    public @NotNull RenderShape getRenderShape(BlockState state) {
        return RenderShape.ENTITYBLOCK_ANIMATED;
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> blockEntityType) {
        return createTickerHelper(blockEntityType, ModBlockEntities.ANU_BARRIER.get(),
                level.isClientSide ? AnuBarrierBlockEntity::clientTick : AnuBarrierBlockEntity::serverTick);
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
    public @NotNull BlockState rotate(BlockState state, Rotation rotation) {
        return state.setValue(FACING, rotation.rotate(state.getValue(FACING)));
    }

    @Override
    public @NotNull VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        if (level.getBlockEntity(pos) instanceof AnuBarrierBlockEntity anuBarrier && anuBarrier.isEnabled()) {
            if (context instanceof EntityCollisionContext entityCollisionContext) {
                Entity entity = entityCollisionContext.getEntity();
                if (entity instanceof Player) {
                    boolean blocks = switch (state.getValue(FACING)) {
                        case NORTH -> entity.getZ() < pos.getZ() + 0.5;
                        case EAST -> entity.getX() > pos.getX() + 0.5;
                        case SOUTH -> entity.getZ() > pos.getZ() + 0.5;
                        case WEST -> entity.getX() < pos.getX() + 0.5;
                        case UP, DOWN -> true;
                    };
                    if (!blocks) {
                        return Shapes.empty();
                    }
                }
            }
            return state.getValue(FACING).getClockWise().getAxis() == Direction.Axis.Z ? Z_AXIS_AABB : X_AXIS_AABB;
        }
        if (Version.debugEnabled()) {
            return state.getValue(FACING).getClockWise().getAxis() == Direction.Axis.Z ? Z_AXIS_AABB : X_AXIS_AABB;
        }
        return Shapes.empty();
    }

    @Override
    public @NotNull VoxelShape getCollisionShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return getShape(state, level, pos, context);
    }
}
