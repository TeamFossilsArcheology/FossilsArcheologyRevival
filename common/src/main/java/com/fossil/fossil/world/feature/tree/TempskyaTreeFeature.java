package com.fossil.fossil.world.feature.tree;

import com.fossil.fossil.block.ModBlocks;
import com.fossil.fossil.block.custom_blocks.TempskyaLeafBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.LevelSimulatedReader;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.TreeFeature;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;

import java.util.Random;

public class TempskyaTreeFeature extends Feature<NoneFeatureConfiguration> {

    public TempskyaTreeFeature() {
        super(NoneFeatureConfiguration.CODEC);
    }

    @Override
    public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> context) {
        //Redo this correctly after 1.18
        WorldGenLevel level = context.level();
        BlockPos pos = context.origin();
        Random random = context.random();
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
                    level.setBlock(pos.above(i).relative(direction), leaves.setValue(TempskyaLeafBlock.FACING, direction), 3);
                }
            }
        }
        level.setBlock(pos.above(treeHeight), ModBlocks.TEMPSKYA_TOP.get().defaultBlockState(), 3);
        return true;
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
