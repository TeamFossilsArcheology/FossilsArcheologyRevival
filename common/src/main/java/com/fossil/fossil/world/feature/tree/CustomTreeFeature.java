package com.fossil.fossil.world.feature.tree;

import com.fossil.fossil.block.custom_blocks.FossilLeavesBlock;
import com.mojang.datafixers.util.Pair;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.LevelSimulatedReader;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.TreeFeature;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;

import java.util.ArrayList;
import java.util.List;

public abstract class CustomTreeFeature extends Feature<NoneFeatureConfiguration> {
    private final List<Pair<BlockPos, BlockState>> placedLeaves = new ArrayList<>();

    public CustomTreeFeature() {
        super(NoneFeatureConfiguration.CODEC);
    }

    @Override
    public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> context) {
        placedLeaves.clear();
        if (placeTree(context)) {
            for (Pair<BlockPos, BlockState> leaf : placedLeaves) {
                FossilLeavesBlock.updateDistance(context.level(), leaf.getFirst(), leaf.getSecond());
            }
            return true;
        }
        return false;
    }//-239 79 47 || -238 78 47

    protected abstract boolean placeTree(FeaturePlaceContext<NoneFeatureConfiguration> context);

    protected void placeLeaf(WorldGenLevel level, BlockPos pos, BlockState state) {
        level.setBlock(pos, state, 19);
        placedLeaves.add(Pair.of(pos.immutable(), state));
    }

    //-249 85 43
    protected int getMaxFreeTreeHeight(LevelSimulatedReader level, int trunkHeight, BlockPos topPosition) {
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
