package com.fossil.fossil.world.feature.tree;

import com.fossil.fossil.block.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;

public class CalamitesTreeFeature extends CustomTreeFeature {

    @Override
    protected boolean placeTree(FeaturePlaceContext<NoneFeatureConfiguration> context) {
        //Redo this correctly after 1.18
        WorldGenLevel level = context.level();
        BlockPos pos = context.origin();
        int treeHeight = context.random().nextInt(15) + 15;
        int m = getMaxFreeTreeHeight(level, treeHeight, pos);
        if (m < treeHeight) {
            return false;
        }
        BlockState log = ModBlocks.CALAMITES_LOG.get().defaultBlockState();
        BlockState leaves = ModBlocks.CALAMITES_LEAVES.get().defaultBlockState();

        BlockPos canopyCenter = pos.above();
        int minWidth = 2;
        int maxWidth = 4;
        float widthStep = (float) (maxWidth - minWidth) / treeHeight;
        while (canopyCenter.getY() < pos.above(treeHeight - 1).getY()) {
            int difference = pos.above(treeHeight).getY() - canopyCenter.getY();
            float canopyWidth = minWidth + (widthStep * difference);
            if (difference > 4) {
                canopyCenter = canopyCenter.above(4);
                genCircle(level, canopyCenter, canopyWidth - 2, false);
                genCircle(level, canopyCenter.above(), canopyWidth - 1, false);
                level.setBlock(canopyCenter.north(), log.setValue(RotatedPillarBlock.AXIS, Direction.Axis.Z), 19);
                level.setBlock(canopyCenter.west(), log.setValue(RotatedPillarBlock.AXIS, Direction.Axis.X), 19);
                level.setBlock(canopyCenter.east(), log.setValue(RotatedPillarBlock.AXIS, Direction.Axis.X), 19);
                level.setBlock(canopyCenter.south(), log.setValue(RotatedPillarBlock.AXIS, Direction.Axis.Z), 19);
                genCircle(level, canopyCenter.above(2), canopyWidth, true);
                genCircle(level, canopyCenter.above(3), canopyWidth + 1, true);
            } else {
                BlockPos.MutableBlockPos topBlocks = canopyCenter.above(difference).mutable();
                level.setBlock(topBlocks, log, 19);
                level.setBlock(topBlocks.move(Direction.UP, 1), log, 19);
                level.setBlock(topBlocks.move(Direction.UP, 1), log, 19);
                placeLeaf(level, topBlocks.north(), leaves);
                placeLeaf(level, topBlocks.west(), leaves);
                placeLeaf(level, topBlocks.east(), leaves);
                placeLeaf(level, topBlocks.south(), leaves);
                placeLeaf(level, topBlocks.move(Direction.UP, 1), leaves);
                canopyCenter = canopyCenter.above(4);
            }
        }
        for (int i = 0; i < treeHeight; ++i) {
            level.setBlock(pos.above(i), log, 19);
        }
        return true;
    }

    private void genCircle(WorldGenLevel level, BlockPos pos, float size, boolean spikes) {
        BlockState leaves = ModBlocks.CALAMITES_LEAVES.get().defaultBlockState();
        for (BlockPos blockpos : BlockPos.betweenClosed(pos.offset(-size, 0, -size), pos.offset(size, 0, size))) {
            int distanceX = Math.abs(blockpos.getX() - pos.getX());
            int distanceZ = Math.abs(blockpos.getZ() - pos.getZ());
            if (spikes) {
                boolean corner = blockpos.getX() == pos.getX() || blockpos.getZ() == pos.getZ() || distanceX == distanceZ;
                if (corner && blockpos.distSqr(pos) > (double) (size - 1) * (size - 1) && blockpos.distSqr(pos) <= size * size) {
                    placeLeaf(level, blockpos, leaves);
                }
            } else {
                if (blockpos.distSqr(pos) <= size * size) {
                    placeLeaf(level, blockpos, leaves);
                }
            }

        }
    }
}
