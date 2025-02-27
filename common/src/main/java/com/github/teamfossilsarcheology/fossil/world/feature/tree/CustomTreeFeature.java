package com.github.teamfossilsarcheology.fossil.world.feature.tree;

import com.github.teamfossilsarcheology.fossil.block.custom_blocks.FossilLeavesBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.LevelSimulatedReader;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.TreeFeature;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;

import java.util.LinkedHashMap;
import java.util.Map;

public abstract class CustomTreeFeature extends Feature<NoneFeatureConfiguration> {
    private final Map<BlockPos, BlockState> placedLeaves = new LinkedHashMap<>();

    protected CustomTreeFeature() {
        super(NoneFeatureConfiguration.CODEC);
    }

    @Override
    public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> context) {
        placedLeaves.clear();
        if (placeTree(context)) {
            for (Map.Entry<BlockPos, BlockState> leaf : placedLeaves.entrySet()) {
                if (context.level().getBlockState(leaf.getKey()).is(leaf.getValue().getBlock())) {
                    FossilLeavesBlock.updateInitialDistance(context.level(), leaf.getKey(), leaf.getValue());
                }
            }
            return true;
        }
        return false;
    }

    protected abstract boolean placeTree(FeaturePlaceContext<NoneFeatureConfiguration> context);

    protected void placeLeaf(WorldGenLevel level, BlockPos pos, BlockState state) {
        level.setBlock(pos, state, 19);
        placedLeaves.put(pos.immutable(), state);
    }

    protected void removeLeaf(WorldGenLevel level, BlockPos pos) {
        level.removeBlock(pos, false);
        placedLeaves.remove(pos.immutable());
    }

    protected int getMaxFreeTreeHeight(LevelSimulatedReader level, int trunkHeight, BlockPos topPosition) {
        BlockPos.MutableBlockPos mutableBlockPos = new BlockPos.MutableBlockPos();
        for (int i = 0; i <= trunkHeight + 1; ++i) {
            //int j = config.minimumSize.getSizeAtHeight(trunkHeight, i);
            int j = 0;
            for (int k = -j; k <= j; ++k) {
                for (int l = -j; l <= j; ++l) {
                    mutableBlockPos.setWithOffset(topPosition, k, i, l);
                    if (TreeFeature.validTreePos(level, mutableBlockPos) && !isVine(level, mutableBlockPos)) continue;
                    return i - 2;
                }
            }
        }
        return trunkHeight;
    }

    protected boolean isAir(WorldGenLevel level, BlockPos blockPos) {
        return level.isStateAtPosition(blockPos, BlockBehaviour.BlockStateBase::isAir);
    }

    private static boolean isVine(LevelSimulatedReader level, BlockPos pos) {
        return level.isStateAtPosition(pos, blockState -> blockState.is(Blocks.VINE));
    }
}
