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

public class CalamitesTreeFeature extends Feature<NoneFeatureConfiguration> {

    public CalamitesTreeFeature() {
        super(NoneFeatureConfiguration.CODEC);
    }

    @Override
    public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> context) {
        //Redo this correctly after 1.18
        WorldGenLevel level = context.level();
        BlockPos pos = context.origin();
        int treeHeight = context.random().nextInt(15) + 15;
        int m = getMaxFreeTreeHeight(level, treeHeight, pos);
        if (m < treeHeight) {
            return false;
        }
        BlockState log = ModBlocks.CALAMITES_LOG.get().defaultBlockState();

        BlockPos canopyCenter = pos.above();
        int minWidth = 2;
        int maxWidth = 4;
        float widthStep = (float) (maxWidth - minWidth) / treeHeight;
        while (canopyCenter.getY() < pos.above(treeHeight - 1).getY()) {
            int difference = pos.above(treeHeight).getY() - canopyCenter.getY();
            float canopyWidth = minWidth + (widthStep * difference);
            canopyCenter = canopyCenter.above(4);
            genCircle(level, canopyCenter, canopyWidth - 2, false);
            genCircle(level, canopyCenter.above(), canopyWidth - 1, false);
            if (difference > 4) {
                genCircle(level, canopyCenter.above(2), canopyWidth, true);
                genCircle(level, canopyCenter.above(3), canopyWidth + 1, true);
                level.setBlock(canopyCenter.north(), log.setValue(RotatedPillarBlock.AXIS, Direction.Axis.Z), 3);
                level.setBlock(canopyCenter.west(), log.setValue(RotatedPillarBlock.AXIS, Direction.Axis.X), 3);
                level.setBlock(canopyCenter.east(), log.setValue(RotatedPillarBlock.AXIS, Direction.Axis.X), 3);
                level.setBlock(canopyCenter.south(), log.setValue(RotatedPillarBlock.AXIS, Direction.Axis.Z), 3);
            } else {
                level.setBlock(canopyCenter, log, 3);
                level.setBlock(canopyCenter.above(), log, 3);
                level.setBlock(canopyCenter.below(), log, 3);
            }
        }
        for (int i = 0; i < treeHeight; ++i) {
            level.setBlock(pos.above(i), log, 3);
        }
        return true;
    }

    private static void genCircle(WorldGenLevel level, BlockPos pos, float size, boolean spikes) {
        BlockState leaves = ModBlocks.CALAMITES_LEAVES.get().defaultBlockState();
        float f = size;
        for (BlockPos blockpos : BlockPos.betweenClosed(pos.offset(-size, 0, -size), pos.offset(size, 0, size))) {
            int distanceX = Math.abs(blockpos.getX() - pos.getX());
            int distanceZ = Math.abs(blockpos.getZ() - pos.getZ());
            boolean corner = blockpos.getX() == pos.getX() || blockpos.getZ() == pos.getZ() || distanceX == distanceZ;
            if (spikes) {
                if (corner && blockpos.distSqr(pos) > (double) (f - 1) * (f - 1) && blockpos.distSqr(pos) <= (double) (f * f)) {
                    level.setBlock(blockpos, leaves, 3);
                }
            } else {
                if (blockpos.distSqr(pos) <= (double) (f * f)) {
                    level.setBlock(blockpos, leaves, 3);
                }
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
