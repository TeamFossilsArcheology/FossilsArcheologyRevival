package com.github.teamfossilsarcheology.fossil.world.feature.tree;

import com.github.teamfossilsarcheology.fossil.block.ModBlocks;
import com.github.teamfossilsarcheology.fossil.block.custom_blocks.TempskyaLeafBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;

public class TempskyaTreeFeature extends CustomTreeFeature {

    @Override
    protected boolean placeTree(FeaturePlaceContext<NoneFeatureConfiguration> context) {
        //Redo this correctly after 1.18
        WorldGenLevel level = context.level();
        BlockPos pos = context.origin();
        RandomSource random = context.random();
        int treeHeight = random.nextInt(4) + 2;
        int m = getMaxFreeTreeHeight(level, treeHeight + 2, pos);
        if (m < treeHeight + 2) {
            return false;
        }
        BlockState log = ModBlocks.TEMPSKYA_LOG.get().defaultBlockState();
        BlockState leaves = ModBlocks.TEMPSKYA_LEAF.get().defaultBlockState();

        for (int i = 0; i < treeHeight; i++) {
            level.setBlock(pos.above(i), log, 3);
            for (Direction direction : Direction.Plane.HORIZONTAL) {
                if (random.nextInt(3) == 0) {
                    BlockPos leafPos = pos.above(i).relative(direction);
                    BlockState stateAtPos = level.getBlockState(leafPos);
                    if (stateAtPos.canBeReplaced()) {
                        level.setBlock(leafPos, leaves.setValue(TempskyaLeafBlock.FACING, direction), 3);
                    }
                }
            }
        }
        level.setBlock(pos.above(treeHeight), ModBlocks.TEMPSKYA_TOP.get().defaultBlockState(), 3);
        return true;
    }
}
