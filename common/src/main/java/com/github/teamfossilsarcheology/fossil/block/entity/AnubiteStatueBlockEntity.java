package com.github.teamfossilsarcheology.fossil.block.entity;

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

    public AnubiteStatueBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(ModBlockEntities.ANUBITE_STATUE.get(), blockPos, blockState);
    }

    public static void serverTick(Level level, BlockPos pos, BlockState state, AnubiteStatueBlockEntity blockEntity) {
        Player player = level.getNearestPlayer(pos.getX(), pos.getY(), pos.getZ(), 5, true);
        if (player != null) {
            level.explode(null, pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5, 5f, true, Explosion.BlockInteraction.BREAK);
            ModEntities.ANUBITE.get().spawn((ServerLevel) level, null, null, pos, MobSpawnType.EVENT, false, false);
            level.removeBlock(pos, false);
        }
    }
}
