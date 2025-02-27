package com.github.teamfossilsarcheology.fossil.block.entity;

import com.github.teamfossilsarcheology.fossil.block.custom_blocks.AnubiteStatueBlock;
import com.github.teamfossilsarcheology.fossil.config.FossilConfig;
import com.github.teamfossilsarcheology.fossil.entity.ModEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class AnubiteStatueBlockEntity extends BlockEntity {
    private int cooldown;

    public AnubiteStatueBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(ModBlockEntities.ANUBITE_STATUE.get(), blockPos, blockState);
    }

    @Override
    protected void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
        tag.putInt("Cooldown", cooldown);
    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        cooldown = tag.getInt("Cooldown");
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

    public static void serverTick(Level level, BlockPos pos, BlockState state, AnubiteStatueBlockEntity blockEntity) {
        if (FossilConfig.isEnabled(FossilConfig.ANUBITE_HAS_COOLDOWN)) {
            if (blockEntity.cooldown <= 0) {
                Player player = level.getNearestPlayer(pos.getX(), pos.getY(), pos.getZ(), 5, true);
                if (player != null) {
                    level.explode(null, pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5, 5f, true, Level.ExplosionInteraction.MOB);
                    BlockPos spawnPos = pos.relative(state.getValue(AnubiteStatueBlock.FACING));
                    ModEntities.ANUBITE.get().spawn((ServerLevel) level, spawnPos, MobSpawnType.EVENT);
                    blockEntity.cooldown = FossilConfig.getInt(FossilConfig.ANUBITE_COOLDOWN);
                    level.setBlockAndUpdate(pos, state.setValue(AnubiteStatueBlock.LIT, false));
                }
            }
        } else {
            Player player = level.getNearestPlayer(pos.getX(), pos.getY(), pos.getZ(), 5, true);
            if (player != null) {
                level.explode(null, pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5, 5f, true, Level.ExplosionInteraction.MOB);
                BlockPos spawnPos = pos.relative(state.getValue(AnubiteStatueBlock.FACING));
                ModEntities.ANUBITE.get().spawn((ServerLevel) level, spawnPos, MobSpawnType.EVENT);
                level.removeBlock(pos, false);
            }
        }
        if (blockEntity.cooldown > 0) {
            blockEntity.cooldown--;
            if (blockEntity.cooldown == 0) {
                level.setBlockAndUpdate(pos, state.setValue(AnubiteStatueBlock.LIT, true));
            }
        }
    }

    public static void clientTick(Level level, BlockPos pos, BlockState state, AnubiteStatueBlockEntity blockEntity) {
        if (blockEntity.cooldown > 0) {
            blockEntity.cooldown--;
        }
    }

    public int getCooldown() {
        return cooldown;
    }
}
