package com.github.teamfossilsarcheology.fossil.world.feature.tree;

import com.github.teamfossilsarcheology.fossil.block.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;

public class PalmTreeFeature extends CustomTreeFeature {

    @Override
    protected boolean placeTree(FeaturePlaceContext<NoneFeatureConfiguration> context) {
        //Redo this correctly after 1.18
        WorldGenLevel level = context.level();
        BlockPos pos = context.origin();
        int treeHeight = context.random().nextInt(10) + 10;
        int m = getMaxFreeTreeHeight(level, treeHeight, pos);
        if (m < treeHeight) {
            return false;
        }
        BlockState log = ModBlocks.PALM_LOG.get().defaultBlockState();
        BlockState leaves = ModBlocks.PALM_LEAVES.get().defaultBlockState();
        BlockState invis = ModBlocks.INVISIBLE_LEAVES.get().defaultBlockState();

        for (int i = 0; i < treeHeight; ++i) {
            level.setBlock(pos.above(i), log, 19);
        }

        placeLeaf(level, pos.offset(0, treeHeight, 0), leaves);
        final int y1 = treeHeight - 2;
        final int y2 = treeHeight - 1;
        Direction.Plane.HORIZONTAL.stream().forEach(direction -> {
            int x = direction.getStepX();
            int z = direction.getStepZ();
            placeLeaf(level, pos.offset(x, y2, z), leaves);
            placeLeaf(level, pos.offset(x * 2, y2, z * 2), leaves);
            placeLeaf(level, pos.offset(x * 3, y2, z * 3), leaves);
            placeLeaf(level, pos.offset(x * 4, y2, z * 4), leaves);
            placeLeaf(level, pos.offset(x * 5, y1, z * 5), leaves);
            placeLeaf(level, pos.offset(x * 5, y2, z * 5), invis);
            //NorthEast, etc
            Direction next = direction.getClockWise();
            x = x + next.getStepX();
            z = z + next.getStepZ();
            placeLeaf(level, pos.offset(x, y2, z), leaves);
            placeLeaf(level, pos.offset(x * 2, y2, z * 2), leaves);
            placeLeaf(level, pos.offset(x * 3, y1, z * 3), leaves);
            placeLeaf(level, pos.offset(x * 3, y2, z * 3), invis);
            placeLeaf(level, pos.offset(x * 2, y2, z * 3), invis);
            placeLeaf(level, pos.offset(x * 1, y2, z * 2), invis);
        });

        return true;
    }
}
