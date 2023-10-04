package com.fossil.fossil.world.feature.tree;

import com.fossil.fossil.block.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.LevelSimulatedReader;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.TreeFeature;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;

public class PalmTreeFeature extends Feature<NoneFeatureConfiguration> {

    public PalmTreeFeature() {
        super(NoneFeatureConfiguration.CODEC);
    }

    @Override
    public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> context) {
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
            level.setBlock(pos.above(i), log, 3);
        }

        int y = treeHeight - 15;
        level.setBlock(pos.offset(0, y + 16, 0), leaves, 3);
        level.setBlock(pos.offset(1, y + 15, 0), leaves, 3);
        level.setBlock(pos.offset(2, y + 15, 0), leaves, 3);
        level.setBlock(pos.offset(3, y + 15, 0), leaves, 3);
        level.setBlock(pos.offset(4, y + 15, 0), leaves, 3);
        level.setBlock(pos.offset(5, y + 14, 0), leaves, 3);
        level.setBlock(pos.offset(0, y + 15, 1), leaves, 3);
        level.setBlock(pos.offset(0, y + 15, 2), leaves, 3);
        level.setBlock(pos.offset(0, y + 15, 3), leaves, 3);
        level.setBlock(pos.offset(0, y + 15, 4), leaves, 3);
        level.setBlock(pos.offset(0, y + 14, 5), leaves, 3);
        level.setBlock(pos.offset(-1, y + 15, 0), leaves, 3);
        level.setBlock(pos.offset(-2, y + 15, 0), leaves, 3);
        level.setBlock(pos.offset(-3, y + 15, 0), leaves, 3);
        level.setBlock(pos.offset(-4, y + 15, 0), leaves, 3);
        level.setBlock(pos.offset(-5, y + 14, 0), leaves, 3);
        level.setBlock(pos.offset(0, y + 15, -1), leaves, 3);
        level.setBlock(pos.offset(0, y + 15, -2), leaves, 3);
        level.setBlock(pos.offset(0, y + 15, -3), leaves, 3);
        level.setBlock(pos.offset(0, y + 15, -4), leaves, 3);
        level.setBlock(pos.offset(0, y + 14, -5), leaves, 3);
        level.setBlock(pos.offset(1, y + 15, 1), leaves, 3);
        level.setBlock(pos.offset(1, y + 15, -1), leaves, 3);
        level.setBlock(pos.offset(-1, y + 15, 1), leaves, 3);
        level.setBlock(pos.offset(-1, y + 15, -1), leaves, 3);
        level.setBlock(pos.offset(2, y + 15, 2), leaves, 3);
        level.setBlock(pos.offset(2, y + 15, -2), leaves, 3);
        level.setBlock(pos.offset(-2, y + 15, 2), leaves, 3);
        level.setBlock(pos.offset(-2, y + 15, -2), leaves, 3);
        level.setBlock(pos.offset(3, y + 14, 3), leaves, 3);
        level.setBlock(pos.offset(3, y + 14, -3), leaves, 3);
        level.setBlock(pos.offset(-3, y + 14, 3), leaves, 3);
        level.setBlock(pos.offset(-3, y + 14, -3), leaves, 3);
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
