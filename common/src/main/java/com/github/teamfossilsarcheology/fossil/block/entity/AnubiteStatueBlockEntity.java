package com.github.teamfossilsarcheology.fossil.block.entity;

import com.github.teamfossilsarcheology.fossil.block.custom_blocks.AnubiteStatueBlock;
import com.github.teamfossilsarcheology.fossil.block.custom_blocks.SarcophagusBlock;
import com.github.teamfossilsarcheology.fossil.config.FossilConfig;
import com.github.teamfossilsarcheology.fossil.entity.ModEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class AnubiteStatueBlockEntity extends BlockEntity {
    private int cooldown;

    public AnubiteStatueBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(ModBlockEntities.ANUBITE_STATUE.get(), blockPos, blockState);
    }

    public static void serverTick(Level level, BlockPos pos, BlockState state, AnubiteStatueBlockEntity blockEntity) {
        if (FossilConfig.isEnabled(FossilConfig.ANUBITE_HAS_COOLDOWN)) {
            if (blockEntity.cooldown <= 0) {
                Player player = level.getNearestPlayer(pos.getX(), pos.getY(), pos.getZ(), 5, true);
                if (player != null) {
                    level.explode(null, pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5, 5f, true, Explosion.BlockInteraction.NONE);
                    BlockPos spawnPos = pos.offset(state.getValue(AnubiteStatueBlock.FACING).getNormal());
                    ModEntities.ANUBITE.get().spawn((ServerLevel) level, null, null, spawnPos, MobSpawnType.EVENT, false, false);
                    blockEntity.cooldown = FossilConfig.getInt(FossilConfig.ANUBITE_COOLDOWN);
                    level.setBlockAndUpdate(pos, state.setValue(SarcophagusBlock.LIT, false));
                }
            }
        } else {
            Player player = level.getNearestPlayer(pos.getX(), pos.getY(), pos.getZ(), 5, true);
            if (player != null) {
                level.explode(null, pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5, 5f, true, Explosion.BlockInteraction.NONE);
                BlockPos spawnPos = pos.offset(state.getValue(AnubiteStatueBlock.FACING).getNormal());
                ModEntities.ANUBITE.get().spawn((ServerLevel) level, null, null, spawnPos, MobSpawnType.EVENT, false, false);
                level.removeBlock(pos, false);
            }
        }
        if (blockEntity.cooldown > 0) {
            blockEntity.cooldown--;
            if (blockEntity.cooldown == 0) {
                level.setBlockAndUpdate(pos, state.setValue(SarcophagusBlock.LIT, true));
            }
        }
    }
}
