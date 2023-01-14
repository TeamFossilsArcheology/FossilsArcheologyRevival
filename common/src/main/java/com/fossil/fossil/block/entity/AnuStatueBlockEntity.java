package com.fossil.fossil.block.entity;

import com.fossil.fossil.block.custom_blocks.FigurineBlock;
import com.fossil.fossil.entity.ModEntities;
import com.fossil.fossil.sounds.ModSounds;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class AnuStatueBlockEntity extends BlockEntity {

    public AnuStatueBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(ModBlockEntities.ANU_STATUE.get(), blockPos, blockState);
    }

    public static void serverTick(Level level, BlockPos pos, BlockState state, AnuStatueBlockEntity blockEntity) {
        if (hasFigurines(level, pos) && hasRedstone(level, pos)) {
            level.explode(null, pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5, 5f, true, Explosion.BlockInteraction.BREAK);
            ModEntities.ANU_STATUE.get().spawn((ServerLevel) level, null, null, null, pos.offset(0.5f, 0, 0.5f), MobSpawnType.EVENT, false, false);
            level.playSound(null, pos, ModSounds.ANU_TOTEM.get(), SoundSource.BLOCKS, 1, 1);
            level.removeBlock(pos.north().east(), false);
            level.removeBlock(pos.north().west(), false);
            level.removeBlock(pos.south().east(), false);
            level.removeBlock(pos.south().west(), false);
            level.removeBlock(pos.north(), false);
            level.removeBlock(pos.east(), false);
            level.removeBlock(pos.south(), false);
            level.removeBlock(pos.west(), false);
            level.removeBlock(pos, false);
        }
    }

    private static boolean hasFigurines(Level level, BlockPos pos) {
        BlockPos northEast = pos.north().east();
        BlockPos northWest = pos.north().west();
        BlockPos southEast = pos.south().east();
        BlockPos southWest = pos.south().west();
        boolean bl = level.getBlockState(northEast).getBlock() instanceof FigurineBlock;
        boolean bl1 = level.getBlockState(northWest).getBlock() instanceof FigurineBlock;
        boolean bl2 = level.getBlockState(southEast).getBlock() instanceof FigurineBlock;
        boolean bl3 = level.getBlockState(southWest).getBlock() instanceof FigurineBlock;
        return bl && bl1 && bl2 && bl3;
    }

    private static boolean hasRedstone(Level level, BlockPos pos) {
        BlockPos north = pos.north();
        BlockPos west = pos.west();
        BlockPos south = pos.south();
        BlockPos east = pos.east();
        boolean bl = level.getBlockState(north).is(Blocks.REDSTONE_WIRE);
        boolean bl1 = level.getBlockState(west).is(Blocks.REDSTONE_WIRE);
        boolean bl2 = level.getBlockState(south).is(Blocks.REDSTONE_WIRE);
        boolean bl3 = level.getBlockState(east).is(Blocks.REDSTONE_WIRE);
        return bl && bl1 && bl2 && bl3;
    }
}
