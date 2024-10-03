package com.github.teamfossilsarcheology.fossil.world.feature.tree;

import com.github.teamfossilsarcheology.fossil.block.ModBlocks;
import net.minecraft.core.BlockPos;
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

        for (int i = 0; i < treeHeight; ++i) {
            level.setBlock(pos.above(i), log, 19);
        }

        int y = treeHeight - 16;
        placeLeaf(level, pos.offset(0, y + 16, 0), leaves);
        //East
        placeLeaf(level, pos.offset(1, y + 15, 0), leaves);
        placeLeaf(level, pos.offset(2, y + 15, 0), leaves);
        placeLeaf(level, pos.offset(3, y + 15, 0), leaves);
        placeLeaf(level, pos.offset(4, y + 15, 0), leaves);
        placeLeaf(level, pos.offset(5, y + 14, 0), leaves);
        //South
        placeLeaf(level, pos.offset(0, y + 15, 1), leaves);
        placeLeaf(level, pos.offset(0, y + 15, 2), leaves);
        placeLeaf(level, pos.offset(0, y + 15, 3), leaves);
        placeLeaf(level, pos.offset(0, y + 15, 4), leaves);
        placeLeaf(level, pos.offset(0, y + 14, 5), leaves);
        //West
        placeLeaf(level, pos.offset(-1, y + 15, 0), leaves);
        placeLeaf(level, pos.offset(-2, y + 15, 0), leaves);
        placeLeaf(level, pos.offset(-3, y + 15, 0), leaves);
        placeLeaf(level, pos.offset(-4, y + 15, 0), leaves);
        placeLeaf(level, pos.offset(-5, y + 14, 0), leaves);
        //North
        placeLeaf(level, pos.offset(0, y + 15, -1), leaves);
        placeLeaf(level, pos.offset(0, y + 15, -2), leaves);
        placeLeaf(level, pos.offset(0, y + 15, -3), leaves);
        placeLeaf(level, pos.offset(0, y + 15, -4), leaves);
        placeLeaf(level, pos.offset(0, y + 14, -5), leaves);
        //SouthWest
        placeLeaf(level, pos.offset(-1, y + 15, 1), leaves);
        placeLeaf(level, pos.offset(-2, y + 15, 2), leaves);
        placeLeaf(level, pos.offset(-3, y + 14, 3), leaves);
        //NorthWest
        placeLeaf(level, pos.offset(-1, y + 15, -1), leaves);
        placeLeaf(level, pos.offset(-2, y + 15, -2), leaves);
        placeLeaf(level, pos.offset(-3, y + 14, -3), leaves);
        //SouthEast
        placeLeaf(level, pos.offset(1, y + 15, 1), leaves);
        placeLeaf(level, pos.offset(2, y + 15, 2), leaves);
        placeLeaf(level, pos.offset(3, y + 14, 3), leaves);
        //NorthEast
        placeLeaf(level, pos.offset(1, y + 15, -1), leaves);
        placeLeaf(level, pos.offset(2, y + 15, -2), leaves);
        placeLeaf(level, pos.offset(3, y + 14, -3), leaves);

        return true;
    }
}
