package com.fossil.fossil.world.feature.structures;

import com.fossil.fossil.block.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

public class AnuDefenseHut {


    public static void generateDefenseHutP2(Level level, BlockPos blockPos) {
        BlockState fakeObsidian = ModBlocks.FAKE_OBSIDIAN.get().defaultBlockState();
        BlockPos.MutableBlockPos mutable = blockPos.mutable();
        level.setBlockAndUpdate(mutable.move(-3, 0, 0), fakeObsidian);
        level.setBlockAndUpdate(mutable.move(-3, 0, 1), fakeObsidian);
        level.setBlockAndUpdate(mutable.move(-3, 0, 2), fakeObsidian);
        level.setBlockAndUpdate(mutable.move(-3, 0, -1), fakeObsidian);
        level.setBlockAndUpdate(mutable.move(-3, 0, -2), fakeObsidian);
        level.setBlockAndUpdate(mutable.move(3, 0, 0), fakeObsidian);
        level.setBlockAndUpdate(mutable.move(3, 0, 1), fakeObsidian);
        level.setBlockAndUpdate(mutable.move(3, 0, 2), fakeObsidian);
        level.setBlockAndUpdate(mutable.move(3, 0, -1), fakeObsidian);
        level.setBlockAndUpdate(mutable.move(3, 0, -2), fakeObsidian);
        level.setBlockAndUpdate(mutable.move(0, 0, 3), fakeObsidian);
        level.setBlockAndUpdate(mutable.move(1, 0, 3), fakeObsidian);
        level.setBlockAndUpdate(mutable.move(2, 0, 3), fakeObsidian);
        level.setBlockAndUpdate(mutable.move(-1, 0, 3), fakeObsidian);
        level.setBlockAndUpdate(mutable.move(-2, 0, 3), fakeObsidian);
        level.setBlockAndUpdate(mutable.move(0, 0, -3), fakeObsidian);
        level.setBlockAndUpdate(mutable.move(1, 0, -3), fakeObsidian);
        level.setBlockAndUpdate(mutable.move(2, 0, -3), fakeObsidian);
        level.setBlockAndUpdate(mutable.move(-1, 0, -3), fakeObsidian);
        level.setBlockAndUpdate(mutable.move(-2, 0, -3), fakeObsidian);
    }

    public static void generateDefenseHutP1(Level level, BlockPos blockPos) {
        BlockState fakeObsidian = ModBlocks.FAKE_OBSIDIAN.get().defaultBlockState();
        BlockPos.MutableBlockPos mutable = blockPos.mutable();
        level.setBlockAndUpdate(mutable.move(0, -1, 0), fakeObsidian);
        level.setBlockAndUpdate(mutable.move(1, -1, 0), fakeObsidian);
        level.setBlockAndUpdate(mutable.move(2, -1, 0), fakeObsidian);
        level.setBlockAndUpdate(mutable.move(-1, -1, 0), fakeObsidian);
        level.setBlockAndUpdate(mutable.move(-2, -1, 0), fakeObsidian);
        level.setBlockAndUpdate(mutable.move(0, -1, 1), fakeObsidian);
        level.setBlockAndUpdate(mutable.move(1, -1, 1), fakeObsidian);
        level.setBlockAndUpdate(mutable.move(2, -1, 1), fakeObsidian);
        level.setBlockAndUpdate(mutable.move(-1, -1, 1), fakeObsidian);
        level.setBlockAndUpdate(mutable.move(-2, -1, 1), fakeObsidian);
        level.setBlockAndUpdate(mutable.move(0, -1, 2), fakeObsidian);
        level.setBlockAndUpdate(mutable.move(1, -1, 2), fakeObsidian);
        level.setBlockAndUpdate(mutable.move(2, -1, 2), fakeObsidian);
        level.setBlockAndUpdate(mutable.move(-1, -1, 2), fakeObsidian);
        level.setBlockAndUpdate(mutable.move(-2, -1, 2), fakeObsidian);
        level.setBlockAndUpdate(mutable.move(0, -1, -1), fakeObsidian);
        level.setBlockAndUpdate(mutable.move(1, -1, -1), fakeObsidian);
        level.setBlockAndUpdate(mutable.move(2, -1, -1), fakeObsidian);
        level.setBlockAndUpdate(mutable.move(-1, -1, -1), fakeObsidian);
        level.setBlockAndUpdate(mutable.move(-2, -1, -1), fakeObsidian);
        level.setBlockAndUpdate(mutable.move(0, -1, -2), fakeObsidian);
        level.setBlockAndUpdate(mutable.move(1, -1, -2), fakeObsidian);
        level.setBlockAndUpdate(mutable.move(2, -1, -2), fakeObsidian);
        level.setBlockAndUpdate(mutable.move(-1, -1, -2), fakeObsidian);
        level.setBlockAndUpdate(mutable.move(-2, -1, -2), fakeObsidian);
    }
}
