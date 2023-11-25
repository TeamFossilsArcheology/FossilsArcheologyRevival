package com.fossil.fossil.block.entity;

import com.fossil.fossil.block.custom_blocks.SarcophagusBlock;
import com.fossil.fossil.entity.ModEntities;
import com.fossil.fossil.entity.monster.AnuBoss;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

public class SarcophagusBlockEntity extends BlockEntity {
    public static final int STATE_LOCKED = 0;
    public static final int STATE_UNLOCKED = 1;
    public static final int STATE_OPENING = 2;
    public static final int STATE_CLOSING = 3;
    private int state;
    private int doorTimer;

    public SarcophagusBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(ModBlockEntities.SARCOPHAGUS.get(), blockPos, blockState);
    }

    @Override
    protected void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
        tag.putInt("State", state);
        tag.putInt("Timer", doorTimer);
    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        state = tag.getInt("State");
        doorTimer = tag.getInt("Timer");
    }

    @Override
    public @NotNull CompoundTag getUpdateTag() {
        CompoundTag tag = super.getUpdateTag();
        saveAdditional(tag);
        return tag;
    }

    private static void tick(Level level, BlockPos pos, BlockState state, SarcophagusBlockEntity blockEntity) {
        if (blockEntity.state != STATE_CLOSING) {
            if (blockEntity.doorTimer > 0) {
                blockEntity.doorTimer++;
            }
            if (blockEntity.doorTimer >= 91) {
                blockEntity.setState(STATE_CLOSING);
                if (!level.isClientSide) {
                    AnuBoss anuBoss = ModEntities.ANU_BOSS.get().create(level);
                    BlockPos anuPos = pos.relative(state.getValue(SarcophagusBlock.FACING));
                    anuBoss.moveTo(anuPos, state.getValue(SarcophagusBlock.FACING).toYRot(), 0);
                    anuBoss.yHeadRot = state.getValue(SarcophagusBlock.FACING).toYRot();
                    anuBoss.finalizeSpawn((ServerLevelAccessor) level, level.getCurrentDifficultyAt(pos), MobSpawnType.NATURAL, null, null);
                    Player player = level.getNearestPlayer(pos.getX(), pos.getY(), pos.getZ(), 100, false);
                    player.displayClientMessage(AnuBoss.getRandomGreeting(level.random), false);
                    level.addFreshEntity(anuBoss);
                }
            }
        } else {
            if (blockEntity.doorTimer > 0) {
                blockEntity.doorTimer--;
            }
            if (blockEntity.doorTimer == 0) {
                blockEntity.setState(STATE_LOCKED);
            }
        }
    }

    public static void serverTick(Level level, BlockPos pos, BlockState state, SarcophagusBlockEntity blockEntity) {
        if (state.getValue(SarcophagusBlock.LAYER) == 0) {
            tick(level, pos, state, blockEntity);
        }
    }

    public static void clientTick(Level level, BlockPos pos, BlockState state, SarcophagusBlockEntity blockEntity) {
        if (state.getValue(SarcophagusBlock.LAYER) == 0) {
            tick(level, pos, state, blockEntity);
        }
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
        setChanged();
    }

    public int getDoorTimer() {
        return doorTimer;
    }

    public void setDoorTimer(int doorTimer) {
        this.doorTimer = doorTimer;
    }
}
