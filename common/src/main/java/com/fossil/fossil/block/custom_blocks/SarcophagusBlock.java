package com.fossil.fossil.block.custom_blocks;

import com.fossil.fossil.block.entity.ModBlockEntities;
import com.fossil.fossil.block.entity.SarcophagusBlockEntity;
import com.fossil.fossil.item.ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class SarcophagusBlock extends BaseEntityBlock {
    public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;
    public static final IntegerProperty LAYER = IntegerProperty.create("layer", 0, 2);
    private static final VoxelShape SHAPE = Block.box(0, 0, 0, 16, 43, 16);


    public SarcophagusBlock(Properties properties) {
        super(properties);
        registerDefaultState(stateDefinition.any().setValue(FACING, Direction.NORTH).setValue(LAYER, 0));
    }

    @Override
    public @NotNull InteractionResult use(BlockState blockState, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        if (level.getBlockEntity(pos) instanceof SarcophagusBlockEntity blockEntity) {
            if (blockEntity.getState() == 0) {
                ItemStack itemStack = player.getItemInHand(hand);
                if (itemStack.is(ModItems.SCARAB_GEM.get())) {
                    blockEntity.setState(SarcophagusBlockEntity.STATE_UNLOCKED);
                    if (!player.isCreative()) {
                        itemStack.shrink(1);
                    }
                }
            } else if (blockEntity.getState() == 1) {
                blockEntity.setState(SarcophagusBlockEntity.STATE_OPENING);
                blockEntity.setDoorTimer(1);
            }
            return InteractionResult.sidedSuccess(level.isClientSide);
        }
        return super.use(blockState, level, pos, player, hand, hit);
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
        level.setBlock(pos.above(), defaultBlockState().setValue(LAYER, 1), 3);
        level.setBlock(pos.above(2), defaultBlockState().setValue(LAYER, 2), 3);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING, LAYER);
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
