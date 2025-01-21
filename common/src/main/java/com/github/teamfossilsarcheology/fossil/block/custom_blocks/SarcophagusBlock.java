package com.github.teamfossilsarcheology.fossil.block.custom_blocks;

import com.github.teamfossilsarcheology.fossil.advancements.ModTriggers;
import com.github.teamfossilsarcheology.fossil.block.entity.AnuBarrierBlockEntity;
import com.github.teamfossilsarcheology.fossil.block.entity.ModBlockEntities;
import com.github.teamfossilsarcheology.fossil.block.entity.SarcophagusBlockEntity;
import com.github.teamfossilsarcheology.fossil.entity.monster.AnuBoss;
import com.github.teamfossilsarcheology.fossil.item.ModItems;
import com.github.teamfossilsarcheology.fossil.world.dimension.ModDimensions;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Vec3i;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashSet;
import java.util.Set;

/**
 * @see SarcophagusBlockEntity
 * @see com.github.teamfossilsarcheology.fossil.item.SarcophagusBlockItem
 * @see com.github.teamfossilsarcheology.fossil.client.renderer.blockentity.SarcophagusRenderer
 */
public class SarcophagusBlock extends BaseEntityBlock {
    public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;
    public static final IntegerProperty LAYER = IntegerProperty.create("layer", 0, 2);
    public static final BooleanProperty LIT = BlockStateProperties.LIT;
    private static final VoxelShape SHAPE = Block.box(1, 0, -1, 16, 49, 17);


    public SarcophagusBlock(Properties properties) {
        super(properties);
        registerDefaultState(stateDefinition.any().setValue(FACING, Direction.NORTH).setValue(LAYER, 0).setValue(LIT, false));
    }

    @Override
    public @NotNull InteractionResult use(BlockState blockState, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        if (blockState.getValue(LAYER) != 0) {
            BlockPos below = pos.below(blockState.getValue(LAYER));
            return level.getBlockState(below).use(level, player, hand, hit.withPosition(below));
        }
        if (level.getBlockEntity(pos) instanceof SarcophagusBlockEntity blockEntity) {
            if (blockEntity.getState() == SarcophagusBlockEntity.STATE_LOCKED) {
                ItemStack itemStack = player.getItemInHand(hand);
                if (itemStack.is(ModItems.SCARAB_GEM.get())) {
                    blockEntity.setState(SarcophagusBlockEntity.STATE_UNLOCKED);
                    level.blockEvent(pos, blockState.getBlock(), SarcophagusBlockEntity.STATE_EVENT, blockEntity.getState());
                    level.setBlockAndUpdate(pos, blockState.setValue(SarcophagusBlock.LIT, true));
                    level.setBlockAndUpdate(pos.above(), level.getBlockState(pos.above()).setValue(SarcophagusBlock.LIT, true));
                    level.setBlockAndUpdate(pos.above(2), level.getBlockState(pos.above(2)).setValue(SarcophagusBlock.LIT, true));
                    if (!player.getAbilities().instabuild) {
                        itemStack.shrink(1);
                    }
                    if (!level.isClientSide) {
                        ModTriggers.OPEN_SARCOPHAGUS_TRIGGER.trigger((ServerPlayer) player);
                    }
                }
            } else if (blockEntity.getState() == SarcophagusBlockEntity.STATE_UNLOCKED) {
                blockEntity.setState(SarcophagusBlockEntity.STATE_OPENING);
                blockEntity.setDoorTimer(1);
                level.blockEvent(pos, blockState.getBlock(), SarcophagusBlockEntity.STATE_EVENT, blockEntity.getState());
                if (!level.isClientSide) {
                    enableBarriers((ServerLevel) level, pos);
                }
            }
            return InteractionResult.sidedSuccess(level.isClientSide);
        }
        return super.use(blockState, level, pos, player, hand, hit);
    }

    private static void enableBarrier(ServerLevel level, BlockPos pos, Set<BlockPos> barriers) {
        if (level.getBlockEntity(pos) instanceof AnuBarrierBlockEntity blockEntity) {
            blockEntity.enable();
            barriers.add(pos.immutable());
        }
    }

    private static void enableBarriers(ServerLevel level, BlockPos pos) {
        if (level.dimension() == ModDimensions.ANU_LAIR) {
            Set<BlockPos> barriers = new HashSet<>();
            BlockPos.MutableBlockPos mutable = pos.mutable();
            int y = pos.getY() - 6;
            for (Direction direction : Direction.Plane.HORIZONTAL) {
                Vec3i dirVec = direction.getNormal().multiply(32);
                Vec3i offsetVec = direction.getCounterClockWise().getNormal().multiply(5);
                enableBarrier(level, mutable.set(pos.getX(), y, pos.getZ()).move(dirVec).move(offsetVec), barriers);
                enableBarrier(level, mutable.move(0, 10, 0), barriers);
                enableBarrier(level, mutable.move(direction.getClockWise().getNormal().multiply(10)), barriers);
                enableBarrier(level, mutable.move(0, -10, 0), barriers);
            }
            level.getDataStorage().set("anu_lair", AnuBoss.AnuLair.spawned(barriers));
        }
    }

    @Override
    public @NotNull BlockState updateShape(BlockState state, Direction direction, BlockState neighborState, LevelAccessor level, BlockPos currentPos, BlockPos neighborPos) {
        int layer = state.getValue(LAYER);
        if (direction.getAxis() == Direction.Axis.Y && !neighborState.is(this)) {
            if (layer == 1 || layer == 0 && direction == Direction.UP || layer == 2 && direction == Direction.DOWN) {
                return Blocks.AIR.defaultBlockState();
            }
        }
        return super.updateShape(state, direction, neighborState, level, currentPos, neighborPos);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> blockEntityType) {
        return createTickerHelper(blockEntityType, ModBlockEntities.SARCOPHAGUS.get(),
                level.isClientSide ? SarcophagusBlockEntity::clientTick : SarcophagusBlockEntity::serverTick);
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new SarcophagusBlockEntity(pos, state);
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return super.getStateForPlacement(context).setValue(FACING, context.getHorizontalDirection().getOpposite());
    }

    @Override
    public void setPlacedBy(Level level, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack stack) {
        level.setBlock(pos.above(), defaultBlockState().setValue(LAYER, 1).setValue(FACING, state.getValue(FACING)), 3);
        level.setBlock(pos.above(2), defaultBlockState().setValue(LAYER, 2).setValue(FACING, state.getValue(FACING)), 3);
    }

    @Override
    public boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
        switch (state.getValue(LAYER)) {
            case 1 -> {
                BlockState blockState = level.getBlockState(pos.below());
                return blockState.is(this) && blockState.getValue(LAYER) == 0;
            }
            case 2 -> {
                BlockState blockState = level.getBlockState(pos.below());
                return blockState.is(this) && blockState.getValue(LAYER) == 1;
            }
        }
        return super.canSurvive(state, level, pos);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING, LAYER, LIT);
    }

    @Override
    public boolean isPathfindable(BlockState state, BlockGetter level, BlockPos pos, PathComputationType type) {
        return false;
    }

    @Override
    public @NotNull BlockState rotate(BlockState state, Rotation rotation) {
        return state.setValue(FACING, rotation.rotate(state.getValue(FACING)));
    }

    @Override
    public @NotNull BlockState mirror(BlockState state, Mirror mirror) {
        return state.rotate(mirror.getRotation(state.getValue(FACING)));
    }

    @Override
    public @NotNull RenderShape getRenderShape(BlockState state) {
        return RenderShape.ENTITYBLOCK_ANIMATED;
    }

    @Override
    public @NotNull VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return SHAPE.move(0, -state.getValue(LAYER), 0);
    }
}
