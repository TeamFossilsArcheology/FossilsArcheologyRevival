package com.fossil.fossil.world.feature.tree;

import com.fossil.fossil.block.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.LevelSimulatedReader;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.TreeFeature;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;

import java.util.Random;

public class SigillariaTreeFeature extends Feature<NoneFeatureConfiguration> {

    public SigillariaTreeFeature() {
        super(NoneFeatureConfiguration.CODEC);
    }

    @Override
    public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> context) {
        //Redo this correctly after 1.18
        WorldGenLevel level = context.level();
        BlockPos pos = context.origin();
        Random random = context.random();
        int treeHeight = random.nextInt(7) + 15;
        int m = getMaxFreeTreeHeight(level, treeHeight, pos);
        if (m < treeHeight) {
            return false;
        }
        BlockState log = ModBlocks.SIGILLARIA_LOG.get().defaultBlockState();

        for (Direction direction : Direction.Plane.HORIZONTAL) {
            level.setBlock(pos.relative(direction, 2), log.setValue(RotatedPillarBlock.AXIS, direction.getAxis()), 19);
        }
        boolean twins = random.nextInt(4) != 0;
        for (int i = 0; i < treeHeight; i++) {
            level.setBlock(pos.above(i), log, 3);
            if (i < (treeHeight - (twins ? -2 : 4)) * 0.65) {
                for (Direction direction : Direction.Plane.HORIZONTAL) {
                    level.setBlock(pos.above(i).relative(direction), log, 3);
                }
            }
        }

        BlockPos top = pos.above(treeHeight);
        if (twins) {
            Direction direction = Direction.from2DDataValue(random.nextInt(4));
            float bushWidth = random.nextInt(2) + 2;
            int secondHeight = 3 + random.nextInt(2) + (int) bushWidth;
            for (int i = 0; i <= secondHeight; i++) {
                BlockPos offsetLeft = top.relative(direction, i).above(i);
                BlockPos offsetRight = top.relative(direction.getOpposite(), i).above(i);
                level.setBlock(offsetLeft, log, 3);
                level.setBlock(offsetRight, log, 3);
                if (i == secondHeight) {
                    int bushHeight = random.nextInt(2) + 6;
                    genCone(level, offsetLeft.above(bushHeight / 2).relative(direction, -1), bushWidth, bushHeight, random);
                    genCone(level, offsetRight.above(bushHeight / 2).relative(direction.getOpposite(), -1), bushWidth, bushHeight, random);
                }
            }
        } else {
            genCone(level, top.below(2), random.nextInt(2) + 2, random.nextInt(2) + 6, random);
        }
        return false;
    }

    private void genCone(WorldGenLevel level, BlockPos pos, float size, float height, Random random) {
        BlockState log = ModBlocks.SIGILLARIA_LOG.get().defaultBlockState();
        BlockState leaves = ModBlocks.SIGILLARIA_LEAVES.get().defaultBlockState();
        float f = (size + height + size) * 0.333f + 0.5f;
        for (BlockPos blockpos : BlockPos.betweenClosed(pos.offset(-size, -height, -size), pos.offset(size, height, size))) {
            int distanceX = Math.abs(blockpos.getX() - pos.getX());
            int distanceZ = Math.abs(blockpos.getZ() - pos.getZ());
            int distanceY = Math.abs(blockpos.getY() - pos.getY());
            if (blockpos.distSqr(pos) <= (double) (f * f)) {
                if (distanceX * distanceX + distanceZ * distanceZ < (f * f * (0.5f + random.nextFloat() * 0.5f)) * ((1 - distanceY % 2) + 0.25f)) {
                    level.setBlock(blockpos, leaves, 3);
                }
            }
        }
        for (BlockPos blockpos : BlockPos.betweenClosed(pos.below((int) height), pos.above((int) height - 4))) {
            if (blockpos.distSqr(pos) <= (double) (f * f)) {
                level.setBlock(blockpos, log, 3);
            }
        }
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
