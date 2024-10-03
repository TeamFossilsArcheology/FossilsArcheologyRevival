package com.github.teamfossilsarcheology.fossil.world.feature.structures;

import com.github.teamfossilsarcheology.fossil.block.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

public class AnuDefenseHut {


    public static void generateDefenseHutP2(Level level, BlockPos blockPos) {
        BlockState fakeObsidian = ModBlocks.FAKE_OBSIDIAN.get().defaultBlockState();
        BlockPos.MutableBlockPos mutable = blockPos.mutable();
        level.setBlockAndUpdate(mutable.set(blockPos).move(-3, 0, 0), fakeObsidian);
        level.setBlockAndUpdate(mutable.set(blockPos).move(-3, 0, 1), fakeObsidian);
        level.setBlockAndUpdate(mutable.set(blockPos).move(-3, 0, 2), fakeObsidian);
        level.setBlockAndUpdate(mutable.set(blockPos).move(-3, 0, -1), fakeObsidian);
        level.setBlockAndUpdate(mutable.set(blockPos).move(-3, 0, -2), fakeObsidian);
        level.setBlockAndUpdate(mutable.set(blockPos).move(3, 0, 0), fakeObsidian);
        level.setBlockAndUpdate(mutable.set(blockPos).move(3, 0, 1), fakeObsidian);
        level.setBlockAndUpdate(mutable.set(blockPos).move(3, 0, 2), fakeObsidian);
        level.setBlockAndUpdate(mutable.set(blockPos).move(3, 0, -1), fakeObsidian);
        level.setBlockAndUpdate(mutable.set(blockPos).move(3, 0, -2), fakeObsidian);
        level.setBlockAndUpdate(mutable.set(blockPos).move(0, 0, 3), fakeObsidian);
        level.setBlockAndUpdate(mutable.set(blockPos).move(1, 0, 3), fakeObsidian);
        level.setBlockAndUpdate(mutable.set(blockPos).move(2, 0, 3), fakeObsidian);
        level.setBlockAndUpdate(mutable.set(blockPos).move(-1, 0, 3), fakeObsidian);
        level.setBlockAndUpdate(mutable.set(blockPos).move(-2, 0, 3), fakeObsidian);
        level.setBlockAndUpdate(mutable.set(blockPos).move(0, 0, -3), fakeObsidian);
        level.setBlockAndUpdate(mutable.set(blockPos).move(1, 0, -3), fakeObsidian);
        level.setBlockAndUpdate(mutable.set(blockPos).move(2, 0, -3), fakeObsidian);
        level.setBlockAndUpdate(mutable.set(blockPos).move(-1, 0, -3), fakeObsidian);
        level.setBlockAndUpdate(mutable.set(blockPos).move(-2, 0, -3), fakeObsidian);
    }

    public static void generateDefenseHutP1(Level level, BlockPos blockPos) {
        BlockState fakeObsidian = ModBlocks.FAKE_OBSIDIAN.get().defaultBlockState();
        BlockPos.MutableBlockPos mutable = blockPos.mutable();
        level.setBlockAndUpdate(mutable.set(blockPos).move(0, -1, 0), fakeObsidian);
        level.setBlockAndUpdate(mutable.set(blockPos).move(1, -1, 0), fakeObsidian);
        level.setBlockAndUpdate(mutable.set(blockPos).move(2, -1, 0), fakeObsidian);
        level.setBlockAndUpdate(mutable.set(blockPos).move(-1, -1, 0), fakeObsidian);
        level.setBlockAndUpdate(mutable.set(blockPos).move(-2, -1, 0), fakeObsidian);
        level.setBlockAndUpdate(mutable.set(blockPos).move(0, -1, 1), fakeObsidian);
        level.setBlockAndUpdate(mutable.set(blockPos).move(1, -1, 1), fakeObsidian);
        level.setBlockAndUpdate(mutable.set(blockPos).move(2, -1, 1), fakeObsidian);
        level.setBlockAndUpdate(mutable.set(blockPos).move(-1, -1, 1), fakeObsidian);
        level.setBlockAndUpdate(mutable.set(blockPos).move(-2, -1, 1), fakeObsidian);
        level.setBlockAndUpdate(mutable.set(blockPos).move(0, -1, 2), fakeObsidian);
        level.setBlockAndUpdate(mutable.set(blockPos).move(1, -1, 2), fakeObsidian);
        level.setBlockAndUpdate(mutable.set(blockPos).move(2, -1, 2), fakeObsidian);
        level.setBlockAndUpdate(mutable.set(blockPos).move(-1, -1, 2), fakeObsidian);
        level.setBlockAndUpdate(mutable.set(blockPos).move(-2, -1, 2), fakeObsidian);
        level.setBlockAndUpdate(mutable.set(blockPos).move(0, -1, -1), fakeObsidian);
        level.setBlockAndUpdate(mutable.set(blockPos).move(1, -1, -1), fakeObsidian);
        level.setBlockAndUpdate(mutable.set(blockPos).move(2, -1, -1), fakeObsidian);
        level.setBlockAndUpdate(mutable.set(blockPos).move(-1, -1, -1), fakeObsidian);
        level.setBlockAndUpdate(mutable.set(blockPos).move(-2, -1, -1), fakeObsidian);
        level.setBlockAndUpdate(mutable.set(blockPos).move(0, -1, -2), fakeObsidian);
        level.setBlockAndUpdate(mutable.set(blockPos).move(1, -1, -2), fakeObsidian);
        level.setBlockAndUpdate(mutable.set(blockPos).move(2, -1, -2), fakeObsidian);
        level.setBlockAndUpdate(mutable.set(blockPos).move(-1, -1, -2), fakeObsidian);
        level.setBlockAndUpdate(mutable.set(blockPos).move(-2, -1, -2), fakeObsidian);
    }
}
