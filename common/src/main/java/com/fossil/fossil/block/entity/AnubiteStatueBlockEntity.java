package com.fossil.fossil.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class AnubiteStatueBlockEntity extends BlockEntity {

    public AnubiteStatueBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(ModBlockEntities.ANUBITE_STATUE.get(), blockPos, blockState);
    }
}
