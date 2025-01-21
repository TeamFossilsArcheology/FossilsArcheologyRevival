package com.github.teamfossilsarcheology.fossil.block.entity;

import com.github.teamfossilsarcheology.fossil.block.custom_blocks.SarcophagusBlock;
import com.github.teamfossilsarcheology.fossil.entity.ModEntities;
import com.github.teamfossilsarcheology.fossil.entity.monster.AnuBoss;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class SarcophagusBlockEntity extends BlockEntity {
    public static final int STATE_LOCKED = 0;
    public static final int STATE_UNLOCKED = 1;
    public static final int STATE_OPENING = 2;
    public static final int STATE_CLOSING = 3;
    public static final int STATE_EVENT = 1;
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
        return saveWithoutMetadata();
    }

    @Nullable
    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public boolean triggerEvent(int id, int type) {
        if (id == STATE_EVENT) {
            state = type;
            if (type == STATE_OPENING) {
                doorTimer = 1;
            }
            return true;
        } else {
            return super.triggerEvent(id, type);
        }
    }

    private static void tick(Level level, BlockPos pos, BlockState state, SarcophagusBlockEntity blockEntity) {
        if (state.getValue(SarcophagusBlock.LAYER) != 0) {
            return;
        }
        if (blockEntity.state != STATE_CLOSING) {
            if (blockEntity.doorTimer > 0) {
                blockEntity.doorTimer++;
            }
            if (blockEntity.doorTimer >= 91) {
                level.setBlockAndUpdate(pos, state.setValue(SarcophagusBlock.LIT, false));
                level.setBlockAndUpdate(pos.above(), level.getBlockState(pos.above()).setValue(SarcophagusBlock.LIT, false));
                level.setBlockAndUpdate(pos.above(2), level.getBlockState(pos.above(2)).setValue(SarcophagusBlock.LIT, false));
                blockEntity.setState(STATE_CLOSING);
                if (!level.isClientSide) {
                    AnuBoss anuBoss = ModEntities.ANU_BOSS.get().create(level);
                    BlockPos anuPos = pos.relative(state.getValue(SarcophagusBlock.FACING));
                    anuBoss.moveTo(anuPos, state.getValue(SarcophagusBlock.FACING).toYRot(), 0);
                    anuBoss.yHeadRot = state.getValue(SarcophagusBlock.FACING).toYRot();
                    anuBoss.finalizeSpawn((ServerLevelAccessor) level, level.getCurrentDifficultyAt(pos), MobSpawnType.NATURAL, null, null);
                    Vec3 spawn = anuBoss.position();
                    AABB arenaBounds = new AABB(spawn.x - AnuBoss.ARENA_RADIUS, spawn.y - 10, spawn.z - AnuBoss.ARENA_RADIUS,
                            spawn.x + AnuBoss.ARENA_RADIUS, spawn.y + 10, spawn.z + AnuBoss.ARENA_RADIUS);
                    List<Player> players = level.getNearbyPlayers(TargetingConditions.DEFAULT, anuBoss, arenaBounds);
                    players.forEach(player -> player.displayClientMessage(AnuBoss.getRandomGreeting(level.random), false));
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
    }

    public int getDoorTimer() {
        return doorTimer;
    }

    public void setDoorTimer(int doorTimer) {
        this.doorTimer = doorTimer;
    }
}
