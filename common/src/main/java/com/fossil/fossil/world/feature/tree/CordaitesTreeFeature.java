package com.fossil.fossil.world.feature.tree;

import com.fossil.fossil.block.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.LevelSimulatedReader;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.TreeFeature;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraft.world.level.material.Material;

import java.util.Random;

public class CordaitesTreeFeature extends Feature<NoneFeatureConfiguration> {

    public CordaitesTreeFeature() {
        super(NoneFeatureConfiguration.CODEC);
    }

    @Override
    public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> context) {
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
            level.setBlock(pos.relative(direction), log.setValue(RotatedPillarBlock.AXIS, direction.getAxis()), 3);
            level.setBlock(pos.relative(direction).above(treeHeight), leaves, 3);
        }
        level.setBlock(pos.above(treeHeight), leaves, 3);
        level.setBlock(pos.above(treeHeight + 1), leaves, 3);
        for (int i = 0; i < treeHeight; ++i) {
            level.setBlock(pos.above(i), log, 3);
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
        return level.isEmptyBlock(pos) || level.getBlockState(pos).getMaterial().isReplaceable() || level.getBlockState(pos).getMaterial() == Material.LEAVES;
    }

    private static void placeBranch(WorldGenLevel level, Random random, BlockPos pos, Direction direction, int length) {
        BlockState log = ModBlocks.CORDAITES_LOG.get().defaultBlockState();
        BlockState leaves = ModBlocks.CORDAITES_LEAVES.get().defaultBlockState();
        int yOffset = 0;
        for (int i = 1; i <= length; i++) {
            level.setBlock(pos.relative(direction, i).above(yOffset), log.setValue(RotatedPillarBlock.AXIS, direction.getAxis()), 3);
            if (i == length) {
                level.setBlock(pos.relative(direction, i + 1).above(yOffset), leaves, 3);
                level.setBlock(pos.relative(direction, i).above(yOffset).relative(direction.getCounterClockWise()), leaves, 3);
                level.setBlock(pos.relative(direction, i).above(yOffset).relative(direction.getClockWise()), leaves, 3);
                level.setBlock(pos.relative(direction, i + 1).above(yOffset).relative(direction.getCounterClockWise()), leaves, 3);
                level.setBlock(pos.relative(direction, i + 1).above(yOffset).relative(direction.getClockWise()), leaves, 3);
                level.setBlock(pos.relative(direction, i + 2).above(yOffset + 1), leaves, 3);
            }
            if (i > 2 && random.nextBoolean()) {
                yOffset++;
            }
        }
    }

    private int getMaxFreeTreeHeight(LevelSimulatedReader level, int trunkHeight, BlockPos topPosition) {
        BlockPos.MutableBlockPos mutableBlockPos = new BlockPos.MutableBlockPos();
        for (int i = 0; i <= trunkHeight + 1; ++i) {
            //int j = config.minimumSize.getSizeAtHeight(trunkHeight, i);
            int j = 0;
            for (int k = -j; k <= j; ++k) {
                for (int l = -j; l <= j; ++l) {
                    mutableBlockPos.setWithOffset(topPosition, k, i, l);
                    if (TreeFeature.isFree(level, mutableBlockPos) && !isVine(level, mutableBlockPos)) continue;
                    return i - 2;
                }
            }
        }
        return trunkHeight;
    }

    private static boolean isVine(LevelSimulatedReader level, BlockPos pos) {
        return level.isStateAtPosition(pos, blockState -> blockState.is(Blocks.VINE));
    }
}
