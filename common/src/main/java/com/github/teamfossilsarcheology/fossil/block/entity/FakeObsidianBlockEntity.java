package com.github.teamfossilsarcheology.fossil.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class FakeObsidianBlockEntity extends BlockEntity {
    private int age;

    public FakeObsidianBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(ModBlockEntities.FAKE_OBSIDIAN.get(), blockPos, blockState);
    }

    public static void serverTick(Level level, BlockPos pos, BlockState blockState, FakeObsidianBlockEntity blockEntity) {
        if (level.random.nextInt(200) == 0) {
            blockEntity.age++;
            if (blockEntity.age >= 9) {
                level.removeBlock(pos, false);
            }
        }
    }
}
