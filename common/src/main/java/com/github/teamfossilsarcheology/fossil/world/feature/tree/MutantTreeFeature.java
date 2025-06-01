package com.github.teamfossilsarcheology.fossil.world.feature.tree;

import com.github.teamfossilsarcheology.fossil.block.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.VineBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;

import java.util.ArrayList;
import java.util.List;

public class MutantTreeFeature extends CustomTreeFeature {
    @Override
    protected boolean placeTree(FeaturePlaceContext<NoneFeatureConfiguration> context) {
        WorldGenLevel level = context.level();
        BlockPos pos = context.origin();
        int treeHeight = context.random().nextInt(3) + 8;
        int foliageHeight = 4;
        int m = getMaxFreeTreeHeight(level, treeHeight, pos);
        if (m < treeHeight) {
            return false;
        }
        BlockState log = ModBlocks.MUTANT_TREE_LOG.get().defaultBlockState();
        BlockState leaves = ModBlocks.MUTANT_TREE_LEAVES.get().defaultBlockState();
        BlockState vine = ModBlocks.MUTANT_TREE_VINE.get().defaultBlockState();
        //BlockState tumor = ModBlocks.MUTANT_TREE_TUMOR.get().defaultBlockState();
        for (int i = 0; i < treeHeight - 1; ++i) {
            level.setBlock(pos.above(i), log, 19);
        }
        int trunk = treeHeight - foliageHeight;
        BlockPos.MutableBlockPos mutable = new BlockPos.MutableBlockPos();
        List<BlockPos> vinePositions = new ArrayList<>();
        for (int i = 0; i < foliageHeight; i++) {
            int offset = i == 0 ? 3 : 3 - i + 1;
            for (int x = -offset; x <= offset; x++) {
                for (int z = -offset; z <= offset; z++) {
                    if (level.isEmptyBlock(mutable.setWithOffset(pos, x, trunk + i, z))) {
                        placeLeaf(level, mutable, leaves);
                        if (i == 0 && (Math.abs(x) == 3 || Math.abs(z) == 3)) {
                            vinePositions.add(mutable.immutable());
                        }
                    }
                }
            }
        }
        BlockPos belowFoliage = pos.above(trunk - 1);

        for (int x = -3; x <= 3; x++) {
            for (int z = -3; z <= 3; z++) {
                int absX = Math.abs(x);
                int absZ = Math.abs(z);
                if (level.getRandom().nextInt(6) == 0 && (absX == 2 && absZ <= 2 || absX <= 2 && absZ == 2 )) {
                    mutable.setWithOffset(belowFoliage, x, 1, z);
                    BlockPos diff = mutable.offset(-belowFoliage.getX(), -belowFoliage.getY(), -belowFoliage.getZ());
                    float steps = Math.max(Mth.abs(diff.getX()), Math.max(Mth.abs(diff.getY()), Mth.abs(diff.getZ())));
                    float dX = diff.getX() / steps;
                    float dY = diff.getY() / steps;
                    float dZ = diff.getZ() / steps;
                    for (int i = 1; i <= steps; i++) {
                        level.setBlock(mutable.setWithOffset(belowFoliage, (int) (0.5 + dX * i), (int) (dY * i), (int) (0.5 + dZ * i)), log.setValue(RotatedPillarBlock.AXIS, getLogAxis(belowFoliage, mutable)), 19);
                    }
                } else if (level.getRandom().nextInt(5) == 0 && (x != 0 || z != 0)) {
                    if (level.getBlockState(mutable.setWithOffset(belowFoliage, x, 1, z)).is(leaves.getBlock())) {
                        removeLeaf(level, mutable);
                        if (absX == 3 || absZ == 3) {
                            vinePositions.remove(mutable.immutable());
                        }
                    }
                } else if (level.getRandom().nextInt(2) == 0 && (absX == 3 || absZ == 3)) {
                    placeLeaf(level, mutable.setWithOffset(belowFoliage, x, 0, z), leaves);
                    vinePositions.remove(mutable.above());
                    vinePositions.add(mutable.immutable());
                }
            }
        }
        placeVine(level, vine, vinePositions);
        for (Direction direction : Direction.Plane.HORIZONTAL) {
            mutable.setWithOffset(belowFoliage, direction);
            for (int i = 0; i < trunk; i++) {
                if (level.isEmptyBlock(mutable) && level.isEmptyBlock(mutable.below()) && level.getRandom().nextInt(9) == 0) {
                    //level.setBlock(mutable, tumor.setValue(MutantTreeTumor.FACING, direction), 19);
                    break;
                }
                mutable.move(Direction.DOWN);
            }
        }
        return true;
    }

    private Direction.Axis getLogAxis(BlockPos pos, BlockPos otherPos) {
        Direction.Axis axis = Direction.Axis.Y;
        int xDiff = Math.abs(otherPos.getX() - pos.getX());
        int diff = Math.max(xDiff, Math.abs(otherPos.getZ() - pos.getZ()));
        if (diff > 0) {
            axis = xDiff == diff ? Direction.Axis.X : Direction.Axis.Z;
        }
        return axis;
    }

    //Copied from LeaveVineDecorator
    private void placeVine(WorldGenLevel level, BlockState state, List<BlockPos> leafPositions) {
        RandomSource random = level.getRandom();
        leafPositions.forEach(blockPos -> {
            BlockPos pos;
            if (random.nextInt(4) == 0 && isAir(level, pos = blockPos.west())) {
                placeHangingVine(level, pos, VineBlock.EAST, state);
            }
            if (random.nextInt(4) == 0 && isAir(level, pos = blockPos.east())) {
                placeHangingVine(level, pos, VineBlock.WEST, state);
            }
            if (random.nextInt(4) == 0 && isAir(level, pos = blockPos.north())) {
                placeHangingVine(level, pos, VineBlock.SOUTH, state);
            }
            if (random.nextInt(4) == 0 && isAir(level, pos = blockPos.south())) {
                placeHangingVine(level, pos, VineBlock.NORTH, state);
            }
        });
    }
    private void placeHangingVine(WorldGenLevel level, BlockPos pos, BooleanProperty sideProperty, BlockState state) {
        level.setBlock(pos, state.setValue(sideProperty, true), 19);
        pos = pos.below();
        for (int i = 4; isAir(level, pos) && i > 0; --i) {
            level.setBlock(pos, state.setValue(sideProperty, true), 19);
            pos = pos.below();
        }
    }
}
