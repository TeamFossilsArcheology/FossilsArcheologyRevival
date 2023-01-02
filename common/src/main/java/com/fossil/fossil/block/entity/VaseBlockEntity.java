package com.fossil.fossil.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class VaseBlockEntity extends BlockEntity {
    public VaseBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(ModBlockEntities.VASE.get(), blockPos, blockState);
    }
}
