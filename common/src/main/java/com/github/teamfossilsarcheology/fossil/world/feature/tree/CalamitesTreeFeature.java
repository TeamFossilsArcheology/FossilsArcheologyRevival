package com.github.teamfossilsarcheology.fossil.world.feature.tree;

import com.github.teamfossilsarcheology.fossil.block.ModBlocks;
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

    // TODO: this code is kinda ugly. too tired to clean it up right now though.
    // some places could use the ternary operator and others could use for loops

    boolean placeLargeVariant(FeaturePlaceContext<NoneFeatureConfiguration> context) {
        WorldGenLevel level = context.level();
        BlockPos pos = context.origin();

        BlockState log = ModBlocks.CALAMITES_LOG.get().defaultBlockState();
        BlockState leaves = ModBlocks.CALAMITES_LEAVES.get().defaultBlockState();

        int treeHeight = 24 + context.random().nextInt(16) - 8; // puts it in the range [16, 32]

        int m = getMaxFreeTreeHeight(level, treeHeight, pos);
        if (m < treeHeight) {
            return false;
        }

        int thickHeight = (int) Math.floor(treeHeight * 0.4);

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

        int startY = 3 + context.random().nextInt(2); // 3 or 4 blocks above ground
        int sectionSpacing = 5;
        int availableHeight = treeHeight - startY;
        int sectionCount = Math.max(1, availableHeight / sectionSpacing);

        List<BlockPos> sectionLayout = new ArrayList<>();

        for (int i = 0; i < sectionCount; i++) {
            int sectionBaseY = startY + (i * sectionSpacing);

            // seems to be reversed for some reason (?) did I label the sections incorrectly?
            float heightFraction = 1 - (float) sectionBaseY / treeHeight;

            if (i == 0) {
                // bottom section
                sectionLayout = randomChoice(context, TreeBranchLayouts.CALAMITES_LARGE_SECTION_2, TreeBranchLayouts.CALAMITES_LARGE_SECTION_3);
            } else {
                if (heightFraction < 0.35f) {
                    sectionLayout = randomChoice(context, TreeBranchLayouts.CALAMITES_LARGE_SECTION_0, TreeBranchLayouts.CALAMITES_LARGE_SECTION_1);
                } else if (heightFraction < 0.6f) {
                    sectionLayout = randomChoice(context, TreeBranchLayouts.CALAMITES_LARGE_SECTION_2, TreeBranchLayouts.CALAMITES_LARGE_SECTION_3);

                    // place log branches to support leaves
                    for (Direction dir : List.of(Direction.NORTH, Direction.SOUTH, Direction.EAST, Direction.WEST)) {
                        for (int d = 1; d <= 2; d++) {
                            BlockPos logPos = pos.offset(dir.getStepX() * d, sectionBaseY, dir.getStepZ() * d);
                            BlockState stateAt = level.getBlockState(logPos);
                            if (stateAt.canBeReplaced()) {
                                level.setBlock(logPos, log.setValue(RotatedPillarBlock.AXIS,
                                        (dir == Direction.NORTH || dir == Direction.SOUTH)
                                                ? Direction.Axis.Z
                                                : Direction.Axis.X), 19);
                            }
                        }
                    }
                } else if (heightFraction < 0.85f) {
                    sectionLayout = randomChoice(context, TreeBranchLayouts.CALAMITES_LARGE_SECTION_4, TreeBranchLayouts.CALAMITES_LARGE_SECTION_5);

                    // place log branches to support leaves
                    for (Direction dir : List.of(Direction.NORTH, Direction.SOUTH, Direction.EAST, Direction.WEST)) {
                        for (int d = 1; d <= 4; d++) {
                            BlockPos logPos = pos.offset(dir.getStepX() * d, sectionBaseY, dir.getStepZ() * d);
                            BlockState stateAt = level.getBlockState(logPos);
                            if (stateAt.canBeReplaced()) {
                                level.setBlock(logPos, log.setValue(RotatedPillarBlock.AXIS,
                                        (dir == Direction.NORTH || dir == Direction.SOUTH)
                                                ? Direction.Axis.Z
                                                : Direction.Axis.X), 19);
                            }
                        }
                    }
                } else {
                    sectionLayout = TreeBranchLayouts.CALAMITES_LARGE_SECTION_0;
                }

            }

            // if the section is too close to the top of the tree, abort it so it doesn't look weird
            // (we place the top of the tree separately)
            if (Math.abs(sectionBaseY - treeHeight) <= 2) {
                sectionLayout = List.of();
            }

            for (BlockPos relPos : sectionLayout) {
                BlockPos leafPos = pos.offset(relPos.getX(), sectionBaseY + relPos.getY(), relPos.getZ());
                BlockState stateAtPos = level.getBlockState(leafPos);
                if (stateAtPos.canBeReplaced()) {
                    placeLeaf(level, leafPos, leaves);
                }
            }
        }

        // tree top
        for (BlockPos relPos : TreeBranchLayouts.CALAMITES_LARGE_SECTION_0) {
            BlockPos leafPos = pos.offset(relPos.getX(), treeHeight + relPos.getY() - 1, relPos.getZ());
            BlockState stateAtPos = level.getBlockState(leafPos);
            if (stateAtPos.canBeReplaced()) {
                placeLeaf(level, leafPos, leaves);
            }
        }

        // place one or two leaves on top
        if (context.random().nextBoolean()) {
            BlockPos leafPos = pos.above(treeHeight);
            BlockState stateAtPos = level.getBlockState(leafPos);
            if (stateAtPos.canBeReplaced()) {
                placeLeaf(level, leafPos, leaves);
            }
            if (context.random().nextBoolean()) {
                leafPos = pos.above(treeHeight + 1);
                stateAtPos = level.getBlockState(leafPos);
                if (stateAtPos.canBeReplaced()) {
                    placeLeaf(level, leafPos, leaves);
                }
            }
        }


        return true;
    }

    boolean placeMediumVariant(FeaturePlaceContext<NoneFeatureConfiguration> context) {
        WorldGenLevel level = context.level();
        BlockPos pos = context.origin();

        BlockState log = ModBlocks.CALAMITES_LOG.get().defaultBlockState();
        BlockState leaves = ModBlocks.CALAMITES_LEAVES.get().defaultBlockState();

        int treeHeight = 12 + context.random().nextInt(9) - 4; // puts it in the range [8, 16]

        int m = getMaxFreeTreeHeight(level, treeHeight, pos);
        if (m < treeHeight) {
            return false;
        }

        for (int i = 0; i < treeHeight; ++i) {
            level.setBlock(pos.above(i), log, 19);
        }

        int startY = 2 + context.random().nextInt(2); // 2 or 3 blocks above ground
        int sectionSpacing = 3;
        int availableHeight = treeHeight - startY;
        int sectionCount = Math.max(1, availableHeight / sectionSpacing);

        List<BlockPos> sectionLayout;

        int lastSectionType = -1;
        int lastSectionBaseY = -1;

        for (int i = 0; i < sectionCount; i++) {
            int sectionBaseY = startY + (i * sectionSpacing);

            if (i == 0) {
                // bottom section
                sectionLayout = randomChoice(context, TreeBranchLayouts.CALAMITES_MED_SECTION_1, TreeBranchLayouts.CALAMITES_MED_SECTION_2);
            } else {
                // middle sections

                // add extra spacing if it's type 2 or 3 because they're larger
                // and can fuse with other sections
                if (lastSectionType == 2 || lastSectionType == 3) {
                    sectionBaseY = lastSectionBaseY + sectionSpacing + 1;
                }

                float heightFraction = (float) sectionBaseY / treeHeight;

                if (heightFraction <= 0.5f) {
                    int choice = context.random().nextInt(8);
                    switch (choice) {
                        case 0, 1, 2, 3, 4, 5, 6 -> {
                            sectionLayout = TreeBranchLayouts.CALAMITES_MED_SECTION_3;
                            lastSectionType = 3;
                        }
                        default -> {
                            sectionLayout = TreeBranchLayouts.CALAMITES_MED_SECTION_2;
                            lastSectionType = 2;
                        }
                    }
                } else if (heightFraction <= 0.7f) {
                    int choice = context.random().nextInt(8);
                    switch (choice) {
                        case 0, 1, 2 -> {
                            sectionLayout = TreeBranchLayouts.CALAMITES_MED_SECTION_2; // 3/8
                            lastSectionType = 2;
                        }
                        default -> {
                            sectionLayout = TreeBranchLayouts.CALAMITES_MED_SECTION_1; // 5/8
                            lastSectionType = 1;
                        }
                    }
                } else {
                    int choice = context.random().nextInt(8);
                    switch (choice) {
                        case 0, 1, 2, 3, 4, 5, 6 -> {
                            sectionLayout = TreeBranchLayouts.CALAMITES_MED_SECTION_1;
                            lastSectionType = 1;
                        }
                        default -> {
                            sectionLayout = TreeBranchLayouts.CALAMITES_MED_SECTION_2;
                            lastSectionType = 2;
                        }
                    }
                }
            }

            // if the section is too close to the top of the tree, abort it so it doesn't look weird
            // (we place the top of the tree separately)
            if (Math.abs(sectionBaseY - treeHeight) <= 1) {
                sectionLayout = List.of();
            }

            for (BlockPos relPos : sectionLayout) {
                BlockPos leafPos = pos.offset(relPos.getX(), sectionBaseY + relPos.getY(), relPos.getZ());
                BlockState stateAtPos = level.getBlockState(leafPos);
                if (stateAtPos.canBeReplaced()) {
                    placeLeaf(level, leafPos, leaves);
                }
            }
            lastSectionBaseY = sectionBaseY;
        }

        // tree top
        for (BlockPos relPos : TreeBranchLayouts.CALAMITES_MED_SECTION_0) {
            BlockPos leafPos = pos.offset(relPos.getX(), treeHeight + relPos.getY(), relPos.getZ());
            BlockState stateAtPos = level.getBlockState(leafPos);
            if (stateAtPos.canBeReplaced()) {
                placeLeaf(level, leafPos, leaves);
            }
        }

        // place one or two leaves on top
        if (context.random().nextBoolean()) {
            BlockPos leafPos = pos.above(treeHeight + 1);
            BlockState stateAtPos = level.getBlockState(leafPos);
            if (stateAtPos.canBeReplaced()) {
                placeLeaf(level, leafPos, leaves);
            }
            if (context.random().nextBoolean()) {
                leafPos = pos.above(treeHeight + 2);
                stateAtPos = level.getBlockState(leafPos);
                if (stateAtPos.canBeReplaced()) {
                    placeLeaf(level, leafPos, leaves);
                }
            }
        }

        return true;
    }

    boolean placeSmallVariant(FeaturePlaceContext<NoneFeatureConfiguration> context) {
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

        int variant = context.random().nextInt(3);
        switch (variant) {
            case 0 -> leafPositions.addAll(TreeBranchLayouts.CALAMITES_SMALL_VARIANT_A);
            case 1 -> leafPositions.addAll(TreeBranchLayouts.CALAMITES_SMALL_VARIANT_B);
            case 2 -> leafPositions.addAll(TreeBranchLayouts.CALAMITES_SMALL_VARIANT_C);
        }

        // try to add one or two leaf blocks on top of the trunk, subtle variation :)
        if (context.random().nextBoolean()) {
            leafPositions.add(new BlockPos(0, 0, 0));
            if (context.random().nextBoolean()) {
                leafPositions.add(new BlockPos(0, 1, 0));
            }
        }


        for (int i = 0; i < leafPositions.size(); i++) {
            BlockPos leafPos = leafPositions.get(i);
            BlockPos worldPos = pos.above(treeHeight).offset(leafPos);
            BlockState stateAtPos = level.getBlockState(worldPos);
            if (stateAtPos.canBeReplaced()) {
                placeLeaf(level, pos.above(treeHeight).offset(leafPos), leaves);
            }
        }
        return true;
    }

    @Override
    protected boolean placeTree(FeaturePlaceContext<NoneFeatureConfiguration> context) {

        switch (context.random().nextInt(8)) {
            case 0, 1, 2, 3, 4 -> {
                return placeSmallVariant(context); // 62% chance
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

    private List<BlockPos> randomChoice(FeaturePlaceContext<NoneFeatureConfiguration> context, List<BlockPos> a, List<BlockPos> b) {
        return context.random().nextBoolean() ? a : b;
    }
}
