package com.github.teamfossilsarcheology.fossil.world.feature.tree;

import com.github.teamfossilsarcheology.fossil.block.ModBlocks;
import com.github.teamfossilsarcheology.fossil.block.custom_blocks.TempskyaLeafBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;

import java.util.ArrayList;
import java.util.List;

public class CalamitesTreeFeature extends CustomTreeFeature {

    boolean placeLargeVariant(FeaturePlaceContext<NoneFeatureConfiguration> context){
        return true;
    }

    boolean placeMediumVariant(FeaturePlaceContext<NoneFeatureConfiguration> context){
        WorldGenLevel level = context.level();
        BlockPos pos = context.origin();

        BlockState log = ModBlocks.CALAMITES_LOG.get().defaultBlockState();
        BlockState leaves = ModBlocks.CALAMITES_LEAVES.get().defaultBlockState();

        int treeHeight = 14 + context.random().nextInt(9) - 4; // puts it in the range [10, 18]

        int m = getMaxFreeTreeHeight(level, treeHeight, pos);
        if (m < treeHeight) {
            return false;
        }

        for (int i = 0; i < treeHeight; ++i) {
            level.setBlock(pos.above(i), log, 19);
        }

        // TODO: leaves and shit

        return true;
    }

    boolean placeSmallVariant(FeaturePlaceContext<NoneFeatureConfiguration> context){
        WorldGenLevel level = context.level();
        BlockPos pos = context.origin();

        BlockState log = ModBlocks.CALAMITES_LOG.get().defaultBlockState();
        BlockState leaves = ModBlocks.CALAMITES_LEAVES.get().defaultBlockState();

        int treeHeight = 8 + context.random().nextInt(3) - 1; // puts it in the range [7, 9]


        int m = getMaxFreeTreeHeight(level, treeHeight, pos);
        if (m < treeHeight) {
            return false;
        }

        for (int i = 0; i < treeHeight; ++i) {
            level.setBlock(pos.above(i), log, 19);
        }

        List<BlockPos> leafPositions = new ArrayList<>();

        if (treeHeight <= 9) {
            int variant = context.random().nextInt(3); // 0,1,2
            switch (variant) {
                case 0 -> leafPositions.addAll(TreeBranchLayouts.CALAMITES_SMALL_0);
                case 1 -> leafPositions.addAll(TreeBranchLayouts.CALAMITES_SMALL_1);
                case 2 -> leafPositions.addAll(TreeBranchLayouts.CALAMITES_SMALL_2);
            }

            // try to add one or two leaf blocks on top of the trunk, subtle variation :)
            if(context.random().nextBoolean()){
                leafPositions.add(new BlockPos(0, 0, 0));
                if(context.random().nextBoolean()){
                    leafPositions.add(new BlockPos(0, 1, 0));
                }
            }
        }

        for (int i = 0; i < leafPositions.size(); i++) {
            BlockPos leafPos = leafPositions.get(i);
            BlockPos worldPos = pos.above(treeHeight).offset(leafPos);  // Calculate world position
            BlockState stateAtPos = level.getBlockState(worldPos);
            if (stateAtPos.canBeReplaced()) {
                placeLeaf(level, pos.above(treeHeight).offset(leafPos), leaves);
            }
        }
        return true;
    }

    @Override
    protected boolean placeTree(FeaturePlaceContext<NoneFeatureConfiguration> context) {
        switch(context.random().nextInt(8)){
            case 0, 1, 2, 3, 4 -> {
                return placeSmallVariant(context); // 62%
            }
            case 5, 6 -> {
                return placeMediumVariant(context); // 25%
            }
            case 7 -> {
                return placeLargeVariant(context); // 12%
            }
        }

        return true;
    }
}
