package com.fossil.fossil.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class FigurineBlockEntity extends BlockEntity {
    public FigurineBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(ModBlockEntities.FIGURINE.get(), blockPos, blockState);
    }
}
