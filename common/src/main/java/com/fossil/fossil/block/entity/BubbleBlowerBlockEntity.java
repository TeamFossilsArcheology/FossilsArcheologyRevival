package com.fossil.fossil.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class BubbleBlowerBlockEntity extends BlockEntity {
    public BubbleBlowerBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(ModBlockEntities.BUBBLE_BLOWER.get(), blockPos, blockState);
    }
}
