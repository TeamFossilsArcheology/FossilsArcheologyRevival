package com.github.teamfossilsarcheology.fossil.block.entity;

import com.github.teamfossilsarcheology.fossil.block.ModBlocks;
import com.github.teamfossilsarcheology.fossil.block.custom_blocks.AnuBarrierOriginBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class AnuBarrierBlockEntity extends BlockEntity {
    public static final int MAX_SIZE = 20;
    public static final int STATE_DISABLED = 0;
    public static final int STATE_ENABLED = 1;
    public static final int STATE_GROWING = 2;
    public static final int STATE_SHRINKING = 3;
    public static final float DURATION = 20f * 3f;
    public int radius = 1;
    public int height = 1;
    private int state = STATE_DISABLED;
    private int barrierTimer;

    public AnuBarrierBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(ModBlockEntities.ANU_BARRIER.get(), blockPos, blockState);
    }

    public static void serverTick(Level level, BlockPos pos, BlockState state, AnuBarrierBlockEntity blockEntity) {
        tick(blockEntity);
    }

    public static void clientTick(Level level, BlockPos pos, BlockState state, AnuBarrierBlockEntity blockEntity) {
        tick(blockEntity);
    }

    private static void tick(AnuBarrierBlockEntity blockEntity) {
        if (blockEntity.state == STATE_GROWING) {
            blockEntity.barrierTimer++;
            if (blockEntity.barrierTimer > DURATION) {
                blockEntity.state = STATE_ENABLED;
                blockEntity.setChanged();
            }
        } else if (blockEntity.state == STATE_SHRINKING) {
            blockEntity.barrierTimer--;
            if (blockEntity.barrierTimer < 0) {
                blockEntity.state = STATE_DISABLED;
                blockEntity.setChanged();
            }
        }
    }

    @Override
    protected void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
        tag.putInt("Height", height);
        tag.putInt("Radius", radius);
        tag.putInt("State", state);
        tag.putInt("Timer", barrierTimer);
    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        height = tag.getInt("Height");
        radius = tag.getInt("Radius");
        state = tag.getInt("State");
        barrierTimer = tag.getInt("Timer");
    }

    @Override
    public @NotNull CompoundTag getUpdateTag() {
        return saveWithoutMetadata();
    }

    @Nullable
    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    private void spawnBarrier(BlockPos pos, BlockState state) {
        Direction direction = state.getValue(AnuBarrierOriginBlock.FACING).getClockWise();
        radius = 0;
        height = 1;
        while (radius < MAX_SIZE) {
            BlockPos pos1 = pos.relative(direction, radius + 1);
            if (level.getBlockState(pos1).isSolidRender(level, pos1)) {
                break;
            }
            radius++;
        }
        while (height < MAX_SIZE) {
            if (!level.isEmptyBlock(pos.above(height))) {
                break;
            }
            height++;
        }
        BlockPos.MutableBlockPos mutable = pos.mutable();
        for (int y = 0; y < height; y++) {
            for (int x = -radius; x <= radius; x++) {
                if (level.isEmptyBlock(mutable.set(pos.getX() + direction.getStepX() * x, pos.getY() + y, pos.getZ() + direction.getStepZ() * x))) {
                    level.setBlock(mutable, ModBlocks.ANU_BARRIER_FACE.get().withPropertiesOf(state), 18);
                }
            }
        }
    }

    private void destroyBarrier(BlockPos pos, BlockState state) {
        Direction direction = state.getValue(AnuBarrierOriginBlock.FACING).getClockWise();
        BlockPos.MutableBlockPos mutable = pos.mutable();
        for (int y = 0; y < height; y++) {
            for (int x = -radius; x <= radius; x++) {
                BlockState barrierState = level.getBlockState(mutable.set(pos.getX() + direction.getStepX() * x, pos.getY() + y, pos.getZ() + direction.getStepZ() * x));
                if (barrierState.is(ModBlocks.ANU_BARRIER_FACE.get())) {
                    level.setBlock(mutable, Blocks.AIR.defaultBlockState(), 18);
                }
            }
        }
    }

    public boolean isEnabled() {
        return state == STATE_GROWING || state == STATE_ENABLED;
    }

    public void setEnabled(boolean enabled) {
        if (enabled) {
            enable();
        } else {
            disable();
        }
    }

    public void enable() {
        if (!isEnabled()) {
            spawnBarrier(getBlockPos(), getBlockState());
        }
        state = STATE_GROWING;
        barrierTimer = 0;
        level.sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(), 18);
    }

    public void disable() {
        state = STATE_SHRINKING;
        destroyBarrier(getBlockPos(), getBlockState());
        level.sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(), 18);
    }

    public int getState() {
        return state;
    }

    public int getBarrierTimer() {
        return barrierTimer;
    }
}
