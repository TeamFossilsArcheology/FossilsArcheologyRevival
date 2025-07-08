package com.github.teamfossilsarcheology.fossil.world.feature.tree;

import com.github.teamfossilsarcheology.fossil.block.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;

public class CordaitesTreeFeature extends CustomTreeFeature {
    @Override
    protected boolean placeTree(FeaturePlaceContext<NoneFeatureConfiguration> context) {
        //Redo this correctly after 1.18
        WorldGenLevel level = context.level();
        BlockPos pos = context.origin();
        int treeHeight = context.random().nextInt(8) + 10;
        int m = getMaxFreeTreeHeight(level, treeHeight, pos);
        if (m < treeHeight) {
            return false;
        }
        BlockState log = ModBlocks.CORDAITES_LOG.get().defaultBlockState();
        BlockState leaves = ModBlocks.CORDAITES_LEAVES.get().defaultBlockState();

        for (Direction direction : Direction.Plane.HORIZONTAL) {
            level.setBlock(pos.relative(direction), log.setValue(RotatedPillarBlock.AXIS, direction.getAxis()), 19);
            placeLeaf(level, pos.relative(direction).above(treeHeight), leaves);
        }
        placeLeaf(level, pos.above(treeHeight), leaves);
        placeLeaf(level, pos.above(treeHeight + 1), leaves);
        for (int i = 0; i < treeHeight; ++i) {
            level.setBlock(pos.above(i), log, 19);
            if (i > treeHeight * 0.6) {
                int heightMinus = (int) (i - treeHeight * 0.6);
                for (Direction direction : Direction.Plane.HORIZONTAL) {
                    if (canPlaceBranch(level, pos.above(i - 1).relative(direction))) {
                        placeBranch(level, context.random(), pos.above(i), direction, Math.max(1, 2 + context.random().nextInt(2) - heightMinus));
                    }
                }
            }
        }
        return true;
    }

    private static boolean canPlaceBranch(WorldGenLevel level, BlockPos pos) {
        return level.isEmptyBlock(pos) || level.getBlockState(pos).canBeReplaced() || level.getBlockState(pos).getBlock() instanceof LeavesBlock;
    }

    private void placeBranch(WorldGenLevel level, RandomSource random, BlockPos pos, Direction direction, int length) {
        BlockState log = ModBlocks.CORDAITES_LOG.get().defaultBlockState();
        BlockState leaves = ModBlocks.CORDAITES_LEAVES.get().defaultBlockState();
        int yOffset = 0;
        for (int i = 1; i <= length; i++) {
            level.setBlock(pos.relative(direction, i).above(yOffset), log.setValue(RotatedPillarBlock.AXIS, direction.getAxis()), 19);
            if (i == length) {
                placeLeaf(level, pos.relative(direction, i + 1).above(yOffset), leaves);
                placeLeaf(level, pos.relative(direction, i).above(yOffset).relative(direction.getCounterClockWise()), leaves);
                placeLeaf(level, pos.relative(direction, i).above(yOffset).relative(direction.getClockWise()), leaves);
                placeLeaf(level, pos.relative(direction, i + 1).above(yOffset).relative(direction.getCounterClockWise()), leaves);
                placeLeaf(level, pos.relative(direction, i + 1).above(yOffset).relative(direction.getClockWise()), leaves);
                placeLeaf(level, pos.relative(direction, i + 2).above(yOffset + 1), leaves);
            }
            if (i > 2 && random.nextBoolean()) {
                yOffset++;
            }
        }
    }
}
