package com.fossil.fossil.block.entity;

import dev.architectury.injectables.annotations.ExpectPlatform;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

/**
 * We split this into forge and fabric because forge containers use item handlers instead of the vanilla Container class
 */
public interface CultivateBlockEntity {

    @ExpectPlatform
    static BlockEntity get(BlockPos pos, BlockState state) {
        throw new AssertionError();
    }

    void serverTick(Level level, BlockPos pos, BlockState state);

    static void serverTick(Level level, BlockPos pos, BlockState state, BlockEntity blockEntity) {
        ((CultivateBlockEntity)blockEntity).serverTick(level, pos, state);
    }
}
