package com.github.teamfossilsarcheology.fossil.block.entity;

import dev.architectury.injectables.annotations.ExpectPlatform;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.ConcretePowderBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;


/**
 * We split this into forge and fabric because forge containers use item handlers instead of the vanilla {@link net.minecraft.world.Container} class
 */
public interface SifterBlockEntity {
    @ExpectPlatform
    static BlockEntity get(BlockPos pos, BlockState state) {
        throw new AssertionError();
    }

    static void serverTick(Level level, BlockPos pos, BlockState state, BlockEntity blockEntity) {
        ((SifterBlockEntity) blockEntity).serverTick(level, pos, state);
    }

    static EnumSiftType getSiftTypeFromStack(ItemStack stack) {
        if (stack.getItem() instanceof BlockItem) {
            BlockState blockState = ((BlockItem) stack.getItem()).getBlock().defaultBlockState();
            if (blockState.getMaterial() == Material.SAND && !(blockState.getBlock() instanceof ConcretePowderBlock)) {
                return EnumSiftType.SAND;
            }
            if (blockState.getMaterial() == Material.DIRT) {
                return EnumSiftType.GROUND;
            }
        }
        return EnumSiftType.NONE;
    }

    void serverTick(Level level, BlockPos pos, BlockState state);

    enum EnumSiftType {
        NONE, GROUND, SAND
    }
}
