package com.fossil.fossil.block.custom_blocks;

import com.fossil.fossil.block.IDinoUnbreakable;
import com.fossil.fossil.block.entity.BubbleBlowerBlockEntity;
import com.fossil.fossil.block.entity.ModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Random;

public class BubbleBlowerBlock extends BaseEntityBlock implements IDinoUnbreakable {
    public static final BooleanProperty ACTIVE = BooleanProperty.create("active");
    public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;

    public BubbleBlowerBlock(Properties properties) {
        super(properties);
        registerDefaultState(getStateDefinition().any().setValue(FACING, Direction.NORTH).setValue(ACTIVE, false));
    }

    @Override
    public void neighborChanged(BlockState state, Level level, BlockPos pos, Block block, BlockPos fromPos, boolean isMoving) {
        if (level.isClientSide) {
            return;
        }
        boolean bl = state.getValue(ACTIVE);
        if (bl != level.hasNeighborSignal(pos)) {
            if (bl) {
                level.scheduleTick(pos, this, 4);
            } else {
                level.setBlock(pos, state.setValue(ACTIVE, true), 2);
            }
        }
    }

    @Override
    public void tick(BlockState state, ServerLevel level, BlockPos pos, Random random) {
        if (state.getValue(ACTIVE) && !level.hasNeighborSignal(pos)) {
            level.setBlock(pos, state.setValue(ACTIVE, false), 2);
        }
    }

    @Override
    public void animateTick(BlockState state, Level level, BlockPos pos, Random random) {
        if (state.getValue(ACTIVE)) {
            int x = pos.getX();
            int y = pos.getY();
            int z = pos.getZ();
            level.playLocalSound(x, y, z, SoundEvents.ITEM_PICKUP, SoundSource.NEUTRAL, 0.5f, random.nextFloat() * 0.7f + 0.4f, false);
            switch (state.getValue(FACING)) {
                case NORTH -> {
                    for (int i = 0; i < 4; i++) {
                        level.addParticle(ModBlockEntities.BUBBLE.get(), x + random.nextFloat(), y + random.nextFloat(), z - 0.1f, 0.0, 0.04, 0.0);
                    }
                }
                case EAST -> {
                    for (int i = 0; i < 4; i++) {
                        level.addParticle(ModBlockEntities.BUBBLE.get(), x + 1.1f, y + random.nextFloat(), z + random.nextFloat(), 0, 0.04, 0);
                    }
                }
                case SOUTH -> {
                    for (int i = 0; i < 4; i++) {
                        level.addParticle(ModBlockEntities.BUBBLE.get(), x + random.nextFloat(), y + random.nextFloat(), z + 1.1f, 0, 0.04, 0);
                    }
                }
                case WEST -> {
                    for (int i = 0; i < 4; i++) {
                        level.addParticle(ModBlockEntities.BUBBLE.get(), x - 0.1f, y + random.nextFloat(), z + random.nextFloat(), 0, 0.04, 0);
                    }
                }
            }
        }
    }

    @Override
    public @NotNull RenderShape getRenderShape(BlockState state) {
        return RenderShape.MODEL;
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new BubbleBlowerBlockEntity(pos, state);
    }


    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return super.getStateForPlacement(context).setValue(FACING, context.getHorizontalDirection().getOpposite());
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING, ACTIVE);
    }

    @Override
    public BlockState rotate(BlockState state, Rotation rotation) {
        return state.setValue(FACING, rotation.rotate(state.getValue(FACING)));
    }

    @Override
    public BlockState mirror(BlockState state, Mirror mirror) {
        return state.rotate(mirror.getRotation(state.getValue(FACING)));
    }
}
