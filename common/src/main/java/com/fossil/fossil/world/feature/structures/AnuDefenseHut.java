package com.fossil.fossil.world.feature.structures;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;

public class AnuDefenseHut {


    public static void generateDefenseHutP2(Level level, BlockPos blockPos) {
        BlockPos.MutableBlockPos mutable = blockPos.mutable();
        level.setBlockAndUpdate(mutable.move(-3, 0, 0), Blocks.OBSIDIAN.defaultBlockState());
        level.setBlockAndUpdate(mutable.move(-3, 0, 1), Blocks.OBSIDIAN.defaultBlockState());
        level.setBlockAndUpdate(mutable.move(-3, 0, 2), Blocks.OBSIDIAN.defaultBlockState());
        level.setBlockAndUpdate(mutable.move(-3, 0, -1), Blocks.OBSIDIAN.defaultBlockState());
        level.setBlockAndUpdate(mutable.move(-3, 0, -2), Blocks.OBSIDIAN.defaultBlockState());
        level.setBlockAndUpdate(mutable.move(3, 0, 0), Blocks.OBSIDIAN.defaultBlockState());
        level.setBlockAndUpdate(mutable.move(3, 0, 1), Blocks.OBSIDIAN.defaultBlockState());
        level.setBlockAndUpdate(mutable.move(3, 0, 2), Blocks.OBSIDIAN.defaultBlockState());
        level.setBlockAndUpdate(mutable.move(3, 0, -1), Blocks.OBSIDIAN.defaultBlockState());
        level.setBlockAndUpdate(mutable.move(3, 0, -2), Blocks.OBSIDIAN.defaultBlockState());
        level.setBlockAndUpdate(mutable.move(0, 0, 3), Blocks.OBSIDIAN.defaultBlockState());
        level.setBlockAndUpdate(mutable.move(1, 0, 3), Blocks.OBSIDIAN.defaultBlockState());
        level.setBlockAndUpdate(mutable.move(2, 0, 3), Blocks.OBSIDIAN.defaultBlockState());
        level.setBlockAndUpdate(mutable.move(-1, 0, 3), Blocks.OBSIDIAN.defaultBlockState());
        level.setBlockAndUpdate(mutable.move(-2, 0, 3), Blocks.OBSIDIAN.defaultBlockState());
        level.setBlockAndUpdate(mutable.move(0, 0, -3), Blocks.OBSIDIAN.defaultBlockState());
        level.setBlockAndUpdate(mutable.move(1, 0, -3), Blocks.OBSIDIAN.defaultBlockState());
        level.setBlockAndUpdate(mutable.move(2, 0, -3), Blocks.OBSIDIAN.defaultBlockState());
        level.setBlockAndUpdate(mutable.move(-1, 0, -3), Blocks.OBSIDIAN.defaultBlockState());
        level.setBlockAndUpdate(mutable.move(-2, 0, -3), Blocks.OBSIDIAN.defaultBlockState());
    }

    public static void generateDefenseHutP1(Level level, BlockPos blockPos) {
        BlockPos.MutableBlockPos mutable = blockPos.mutable();
        level.setBlockAndUpdate(mutable.move(0, -1, 0), Blocks.OBSIDIAN.defaultBlockState());
        level.setBlockAndUpdate(mutable.move(1, -1, 0), Blocks.OBSIDIAN.defaultBlockState());
        level.setBlockAndUpdate(mutable.move(2, -1, 0), Blocks.OBSIDIAN.defaultBlockState());
        level.setBlockAndUpdate(mutable.move(-1, -1, 0), Blocks.OBSIDIAN.defaultBlockState());
        level.setBlockAndUpdate(mutable.move(-2, -1, 0), Blocks.OBSIDIAN.defaultBlockState());
        level.setBlockAndUpdate(mutable.move(0, -1, 1), Blocks.OBSIDIAN.defaultBlockState());
        level.setBlockAndUpdate(mutable.move(1, -1, 1), Blocks.OBSIDIAN.defaultBlockState());
        level.setBlockAndUpdate(mutable.move(2, -1, 1), Blocks.OBSIDIAN.defaultBlockState());
        level.setBlockAndUpdate(mutable.move(-1, -1, 1), Blocks.OBSIDIAN.defaultBlockState());
        level.setBlockAndUpdate(mutable.move(-2, -1, 1), Blocks.OBSIDIAN.defaultBlockState());
        level.setBlockAndUpdate(mutable.move(0, -1, 2), Blocks.OBSIDIAN.defaultBlockState());
        level.setBlockAndUpdate(mutable.move(1, -1, 2), Blocks.OBSIDIAN.defaultBlockState());
        level.setBlockAndUpdate(mutable.move(2, -1, 2), Blocks.OBSIDIAN.defaultBlockState());
        level.setBlockAndUpdate(mutable.move(-1, -1, 2), Blocks.OBSIDIAN.defaultBlockState());
        level.setBlockAndUpdate(mutable.move(-2, -1, 2), Blocks.OBSIDIAN.defaultBlockState());
        level.setBlockAndUpdate(mutable.move(0, -1, -1), Blocks.OBSIDIAN.defaultBlockState());
        level.setBlockAndUpdate(mutable.move(1, -1, -1), Blocks.OBSIDIAN.defaultBlockState());
        level.setBlockAndUpdate(mutable.move(2, -1, -1), Blocks.OBSIDIAN.defaultBlockState());
        level.setBlockAndUpdate(mutable.move(-1, -1, -1), Blocks.OBSIDIAN.defaultBlockState());
        level.setBlockAndUpdate(mutable.move(-2, -1, -1), Blocks.OBSIDIAN.defaultBlockState());
        level.setBlockAndUpdate(mutable.move(0, -1, -2), Blocks.OBSIDIAN.defaultBlockState());
        level.setBlockAndUpdate(mutable.move(1, -1, -2), Blocks.OBSIDIAN.defaultBlockState());
        level.setBlockAndUpdate(mutable.move(2, -1, -2), Blocks.OBSIDIAN.defaultBlockState());
        level.setBlockAndUpdate(mutable.move(-1, -1, -2), Blocks.OBSIDIAN.defaultBlockState());
        level.setBlockAndUpdate(mutable.move(-2, -1, -2), Blocks.OBSIDIAN.defaultBlockState());

    }
}
