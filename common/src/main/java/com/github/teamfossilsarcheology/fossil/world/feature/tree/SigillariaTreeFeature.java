package com.github.teamfossilsarcheology.fossil.world.feature.tree;

import com.github.teamfossilsarcheology.fossil.block.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;

import java.util.List;

public class SigillariaTreeFeature extends CustomTreeFeature {

    // TODO: this code is kinda ugly. too tired to clean it up right now though.
    // some places could use the ternary operator and others could use for loops

    boolean placeLargeVariant(FeaturePlaceContext<NoneFeatureConfiguration> context){
        WorldGenLevel level = context.level();
        BlockPos pos = context.origin();

        BlockState log = ModBlocks.SIGILLARIA_LOG.get().defaultBlockState();
        BlockState leaves = ModBlocks.SIGILLARIA_LEAVES.get().defaultBlockState();

        int treeHeight = 21 + context.random().nextInt(9) - 4; // puts it in the range [17, 25]


        int m = getMaxFreeTreeHeight(level, treeHeight, pos);
        if (m < treeHeight) {
            return false;
        }

        int thickHeight = (int) Math.floor(treeHeight * 0.35);

        for (int i = 0; i < treeHeight; ++i) {
            level.setBlock(pos.above(i), log, 19);

            // make base thick
            if (i < thickHeight) {
                BlockPos eastPos = pos.offset(1, i, 0);
                BlockState stateEast = level.getBlockState(eastPos);
                if (stateEast.canBeReplaced()) {
                    level.setBlock(eastPos, log, 19);
                }

                BlockPos westPos = pos.offset(-1, i, 0);
                BlockState stateWest = level.getBlockState(westPos);
                if (stateWest.canBeReplaced()) {
                    level.setBlock(westPos, log, 19);
                }

                BlockPos southPos = pos.offset(0, i, 1);
                BlockState stateSouth = level.getBlockState(southPos);
                if (stateSouth.canBeReplaced()) {
                    level.setBlock(southPos, log, 19);
                }

                BlockPos northPos = pos.offset(0, i, -1);
                BlockState stateNorth = level.getBlockState(northPos);
                if (stateNorth.canBeReplaced()) {
                    level.setBlock(northPos, log, 19);
                }
            }
        }

        BlockPos north = pos.north().north(); // hop two blocks because the trunk is thick
        BlockPos south = pos.south().south();
        BlockPos east  = pos.east().east();
        BlockPos west  = pos.west().west();

        level.setBlock(north, log.setValue(RotatedPillarBlock.AXIS, Direction.Axis.Z), 19);
        level.setBlock(south, log.setValue(RotatedPillarBlock.AXIS, Direction.Axis.Z), 19);
        level.setBlock(east,  log.setValue(RotatedPillarBlock.AXIS, Direction.Axis.X), 19);
        level.setBlock(west,  log.setValue(RotatedPillarBlock.AXIS, Direction.Axis.X), 19);

        List<BlockPos> leafPositions;

        // no fork
        if(context.random().nextBoolean()) {
            if (context.random().nextBoolean()) {
                leafPositions = TreeBranchLayouts.SIGILLARIA_LEAVES_A;
            } else {
                leafPositions = TreeBranchLayouts.SIGILLARIA_LEAVES_B;
            }

            for (int i = 0; i < leafPositions.size(); i++) {
                BlockPos leafPos = leafPositions.get(i);
                BlockPos worldPos = pos.above(treeHeight - 2).offset(leafPos);
                BlockState stateAtPos = level.getBlockState(worldPos);
                if (stateAtPos.canBeReplaced()) {
                    placeLeaf(level, pos.above(treeHeight - 2).offset(leafPos), leaves);
                }
            }
        }
        else{ // yes fork
            int branchLength = 4;
            int branchTrunkHeight = 7;

            boolean isZAligned = context.random().nextBoolean();

            BlockPos branchTop1;
            BlockPos branchTop2;

            if (isZAligned) {
                // north branch
                for (int i = 0; i < branchLength; i++) {
                    BlockPos forkPos = pos.above(treeHeight + i).offset(0, 0, -1 - i);
                    BlockState stateAtFork = level.getBlockState(forkPos);
                    if (stateAtFork.canBeReplaced()) {
                        level.setBlock(forkPos, log, 19);
                    }
                }

                for (int i = 0; i < branchLength - 1; i++) {
                    BlockPos forkPos = pos.above(treeHeight + i).offset(0, -1, -1 - i);
                    BlockState stateAtFork = level.getBlockState(forkPos);
                    if (stateAtFork.canBeReplaced()) {
                        level.setBlock(forkPos, log, 19);
                    }
                }

                // south branch
                for (int i = 0; i < branchLength; i++) {
                    BlockPos forkPos = pos.above(treeHeight + i).offset(0, 0, 1 + i);
                    BlockState stateAtFork = level.getBlockState(forkPos);
                    if (stateAtFork.canBeReplaced()) {
                        level.setBlock(forkPos, log, 19);
                    }
                }

                for (int i = 0; i < branchLength - 1; i++) {
                    BlockPos forkPos = pos.above(treeHeight + i).offset(0, -1, 1 + i);
                    BlockState stateAtFork = level.getBlockState(forkPos);
                    if (stateAtFork.canBeReplaced()) {
                        level.setBlock(forkPos, log, 19);
                    }
                }

                branchTop1 = pos.above(treeHeight + branchLength - 1).offset(0, branchTrunkHeight, -branchLength);
                branchTop2 = pos.above(treeHeight + branchLength - 1).offset(0, branchTrunkHeight, branchLength);
            } else {
                // west branch
                for (int i = 0; i < branchLength; i++) {
                    BlockPos forkPos = pos.above(treeHeight + i).offset(-1 - i, 0, 0);
                    BlockState stateAtFork = level.getBlockState(forkPos);
                    if (stateAtFork.canBeReplaced()) {
                        level.setBlock(forkPos, log, 19);
                    }
                }

                for (int i = 0; i < branchLength - 1; i++) {
                    BlockPos forkPos = pos.above(treeHeight + i).offset(-1 - i, -1, 0);
                    BlockState stateAtFork = level.getBlockState(forkPos);
                    if (stateAtFork.canBeReplaced()) {
                        level.setBlock(forkPos, log, 19);
                    }
                }

                // east branch
                for (int i = 0; i < branchLength; i++) {
                    BlockPos forkPos = pos.above(treeHeight + i).offset(1 + i, 0, 0);
                    BlockState stateAtFork = level.getBlockState(forkPos);
                    if (stateAtFork.canBeReplaced()) {
                        level.setBlock(forkPos, log, 19);
                    }
                }

                for (int i = 0; i < branchLength - 1; i++) {
                    BlockPos forkPos = pos.above(treeHeight + i).offset(1 + i, - 1, 0);
                    BlockState stateAtFork = level.getBlockState(forkPos);
                    if (stateAtFork.canBeReplaced()) {
                        level.setBlock(forkPos, log, 19);
                    }
                }

                branchTop1 = pos.above(treeHeight + branchLength - 1).offset(-branchLength, branchTrunkHeight, 0);
                branchTop2 = pos.above(treeHeight + branchLength - 1).offset(branchLength, branchTrunkHeight, 0);
            }

            for (int i = 0; i < branchTrunkHeight; i++) {
                BlockState stateAtFork1 = level.getBlockState(branchTop1.offset(0, -i - 1, 0));
                if (stateAtFork1.canBeReplaced()) {
                    level.setBlock(branchTop1.offset(0, -i - 1, 0), log, 19);
                }

                BlockState stateAtFork2 = level.getBlockState(branchTop2.offset(0, -i - 1, 0));
                if (stateAtFork2.canBeReplaced()) {
                    level.setBlock(branchTop2.offset(0, -i - 1, 0), log, 19);
                }
            }

            for (BlockPos leafOffset : TreeBranchLayouts.SIGILLARIA_LEAVES_B) {
                BlockPos worldPos = branchTop1.offset(leafOffset).below().below();
                BlockState stateAtPos = level.getBlockState(worldPos);
                if (stateAtPos.canBeReplaced()) {
                    placeLeaf(level, worldPos, leaves);
                }
            }

            for (BlockPos leafOffset : TreeBranchLayouts.SIGILLARIA_LEAVES_B) {
                BlockPos worldPos = branchTop2.offset(leafOffset).below().below();
                BlockState stateAtPos = level.getBlockState(worldPos);
                if (stateAtPos.canBeReplaced()) {
                    placeLeaf(level, worldPos, leaves);
                }
            }
        }

        return true;
    }

    boolean placeMediumVariant(FeaturePlaceContext<NoneFeatureConfiguration> context){
        WorldGenLevel level = context.level();
        BlockPos pos = context.origin();

        BlockState log = ModBlocks.SIGILLARIA_LOG.get().defaultBlockState();
        BlockState leaves = ModBlocks.SIGILLARIA_LEAVES.get().defaultBlockState();

        int treeHeight = 16 + context.random().nextInt(7) - 3; // puts it in the range [13, 19]


        int m = getMaxFreeTreeHeight(level, treeHeight, pos);
        if (m < treeHeight) {
            return false;
        }

        for (int i = 0; i < treeHeight; ++i) {
            level.setBlock(pos.above(i), log, 19);
        }

        BlockPos north = pos.north();
        BlockPos south = pos.south();
        BlockPos east  = pos.east();
        BlockPos west  = pos.west();

        level.setBlock(north, log.setValue(RotatedPillarBlock.AXIS, Direction.Axis.Z), 19);
        level.setBlock(south, log.setValue(RotatedPillarBlock.AXIS, Direction.Axis.Z), 19);
        level.setBlock(east,  log.setValue(RotatedPillarBlock.AXIS, Direction.Axis.X), 19);
        level.setBlock(west,  log.setValue(RotatedPillarBlock.AXIS, Direction.Axis.X), 19);

        List<BlockPos> leafPositions;

        if(context.random().nextBoolean()){
            leafPositions = TreeBranchLayouts.SIGILLARIA_LEAVES_A;
        }
        else{
            leafPositions = TreeBranchLayouts.SIGILLARIA_LEAVES_B;
        }

        for (int i = 0; i < leafPositions.size(); i++) {
            BlockPos leafPos = leafPositions.get(i);
            BlockPos worldPos = pos.above(treeHeight - 2).offset(leafPos);
            BlockState stateAtPos = level.getBlockState(worldPos);
            if (stateAtPos.canBeReplaced()) {
                placeLeaf(level, pos.above(treeHeight - 2).offset(leafPos), leaves);
            }
        }

        return true;
    }

    boolean placeSmallVariant(FeaturePlaceContext<NoneFeatureConfiguration> context){
        WorldGenLevel level = context.level();
        BlockPos pos = context.origin();

        BlockState log = ModBlocks.SIGILLARIA_LOG.get().defaultBlockState();
        BlockState leaves = ModBlocks.SIGILLARIA_LEAVES.get().defaultBlockState();

        int treeHeight = 8 + context.random().nextInt(3) - 1; // puts it in the range [7, 9]


        int m = getMaxFreeTreeHeight(level, treeHeight, pos);
        if (m < treeHeight) {
            return false;
        }

        for (int i = 0; i < treeHeight; ++i) {
            level.setBlock(pos.above(i), log, 19);
        }

        BlockPos north = pos.north();
        BlockPos south = pos.south();
        BlockPos east  = pos.east();
        BlockPos west  = pos.west();

        level.setBlock(north, log.setValue(RotatedPillarBlock.AXIS, Direction.Axis.Z), 19);
        level.setBlock(south, log.setValue(RotatedPillarBlock.AXIS, Direction.Axis.Z), 19);
        level.setBlock(east,  log.setValue(RotatedPillarBlock.AXIS, Direction.Axis.X), 19);
        level.setBlock(west,  log.setValue(RotatedPillarBlock.AXIS, Direction.Axis.X), 19);

        List<BlockPos> leafPositions;

        if(context.random().nextBoolean()){
            leafPositions = TreeBranchLayouts.SIGILLARIA_SMALL_VARIANT_A;
        }
        else{
            leafPositions = TreeBranchLayouts.SIGILLARIA_SMALL_VARIANT_B;
        }

        for (int i = 0; i < leafPositions.size(); i++) {
            BlockPos leafPos = leafPositions.get(i);
            BlockPos worldPos = pos.above(treeHeight - 1).offset(leafPos);
            BlockState stateAtPos = level.getBlockState(worldPos);
            if (stateAtPos.canBeReplaced()) {
                placeLeaf(level, pos.above(treeHeight - 1).offset(leafPos), leaves);
            }
        }

        return true;
    }

    @Override
    protected boolean placeTree(FeaturePlaceContext<NoneFeatureConfiguration> context) {
        switch(context.random().nextInt(8)){
            case 0, 1 -> {
                return placeSmallVariant(context); // 25% chance
            }
            case 2, 3, 4 -> {
                return placeMediumVariant(context); // 37%
            }
            case 5, 6, 7 -> {
                return placeLargeVariant(context); // 37%
            }
        }

        return true;
    }
}
