package com.github.teamfossilsarcheology.fossil.world.feature.tree;

import net.minecraft.core.BlockPos;

import java.util.List;

public class TreeBranchLayouts {

    public static final List<BlockPos> SIGILLARIA_SMALL_VARIANT_A = List.of(
            // Layer -1
            new BlockPos(0, -1, -1),
            new BlockPos(-1, -1, 0),
            new BlockPos(1, -1, 0),
            new BlockPos(0, -1, 1),

            // Layer 0
            new BlockPos(-1, 0, -1),
            new BlockPos(0, 0, -1),
            new BlockPos(1, 0, -1),
            new BlockPos(-1, 0, 0),
            new BlockPos(1, 0, 0),
            new BlockPos(-1, 0, 1),
            new BlockPos(0, 0, 1),
            new BlockPos(1, 0, 1),

            // Layer 1
            new BlockPos(0, 1, 0),

            // Layer 2
            new BlockPos(0, 2, -1),
            new BlockPos(-1, 2, 0),
            new BlockPos(0, 2, 0),
            new BlockPos(1, 2, 0),
            new BlockPos(0, 2, 1)
    );

    public static final List<BlockPos> SIGILLARIA_SMALL_VARIANT_B = List.of(
            // Layer -2
            new BlockPos(0, -2, -1),
            new BlockPos(-1, -2, 0),
            new BlockPos(1, -2, 0),
            new BlockPos(0, -2, 1),

            // Layer -1
            new BlockPos(-1, -1, -1),
            new BlockPos(0, -1, -1),
            new BlockPos(1, -1, -1),
            new BlockPos(-1, -1, 0),
            new BlockPos(1, -1, 0),
            new BlockPos(-1, -1, 1),
            new BlockPos(0, -1, 1),
            new BlockPos(1, -1, 1),

            // Layer 0
            new BlockPos(0, 0, -1),
            new BlockPos(-1, 0, 0),
            new BlockPos(1, 0, 0),
            new BlockPos(0, 0, 1),

            // Layer 1
            new BlockPos(-1, 1, -1),
            new BlockPos(0, 1, -1),
            new BlockPos(1, 1, -1),
            new BlockPos(-1, 1, 0),
            new BlockPos(0, 1, 0),
            new BlockPos(1, 1, 0),
            new BlockPos(-1, 1, 1),
            new BlockPos(0, 1, 1),
            new BlockPos(1, 1, 1)
    );

    public static final List<BlockPos> SIGILLARIA_LEAVES_A = List.of(
            // Layer -6
            new BlockPos(0, -6, -1),
            new BlockPos(-1, -6, 0),
            new BlockPos(1, -6, 0),
            new BlockPos(0, -6, 1),

            // Layer -5
            new BlockPos(0, -5, -2),
            new BlockPos(-1, -5, -1),
            new BlockPos(0, -5, -1),
            new BlockPos(1, -5, -1),
            new BlockPos(-2, -5, 0),
            new BlockPos(-1, -5, 0),
            new BlockPos(1, -5, 0),
            new BlockPos(2, -5, 0),
            new BlockPos(-1, -5, 1),
            new BlockPos(0, -5, 1),
            new BlockPos(1, -5, 1),
            new BlockPos(0, -5, 2),

            // Layer -4
            new BlockPos(0, -4, -1),
            new BlockPos(-1, -4, 0),
            new BlockPos(1, -4, 0),
            new BlockPos(0, -4, 1),

            // Layer -3
            new BlockPos(0, -3, -2),
            new BlockPos(-1, -3, -1),
            new BlockPos(0, -3, -1),
            new BlockPos(1, -3, -1),
            new BlockPos(-2, -3, 0),
            new BlockPos(-1, -3, 0),
            new BlockPos(1, -3, 0),
            new BlockPos(2, -3, 0),
            new BlockPos(-1, -3, 1),
            new BlockPos(0, -3, 1),
            new BlockPos(1, -3, 1),
            new BlockPos(0, -3, 2),

            // Layer -2
            new BlockPos(0, -2, -1),
            new BlockPos(-1, -2, 0),
            new BlockPos(1, -2, 0),
            new BlockPos(0, -2, 1),

            // Layer -1
            new BlockPos(0, -1, -2),
            new BlockPos(-1, -1, -1),
            new BlockPos(0, -1, -1),
            new BlockPos(1, -1, -1),
            new BlockPos(-2, -1, 0),
            new BlockPos(-1, -1, 0),
            new BlockPos(1, -1, 0),
            new BlockPos(2, -1, 0),
            new BlockPos(-1, -1, 1),
            new BlockPos(0, -1, 1),
            new BlockPos(1, -1, 1),
            new BlockPos(0, -1, 2),

            // Layer 0
            new BlockPos(0, 0, -1),
            new BlockPos(-1, 0, 0),
            new BlockPos(1, 0, 0),
            new BlockPos(0, 0, 1),

            // Layer 1
            new BlockPos(-1, 1, -1),
            new BlockPos(0, 1, -1),
            new BlockPos(1, 1, -1),
            new BlockPos(-1, 1, 0),
            new BlockPos(1, 1, 0),
            new BlockPos(-1, 1, 1),
            new BlockPos(0, 1, 1),
            new BlockPos(1, 1, 1),

            // Layer 2
            new BlockPos(0, 2, 0),

            // Layer 3
            new BlockPos(0, 3, -1),
            new BlockPos(-1, 3, 0),
            new BlockPos(0, 3, 0),
            new BlockPos(1, 3, 0),
            new BlockPos(0, 3, 1)
    );

    // this one should also be the only one used for the forked trunk variant of Sigillaria
    public static final List<BlockPos> SIGILLARIA_LEAVES_B = List.of(
            // Layer -4
            new BlockPos(0, -4, -1),
            new BlockPos(-1, -4, 0),
            new BlockPos(1, -4, 0),
            new BlockPos(0, -4, 1),

            // Layer -3
            new BlockPos(0, -3, -2),
            new BlockPos(-1, -3, -1),
            new BlockPos(0, -3, -1),
            new BlockPos(1, -3, -1),
            new BlockPos(-2, -3, 0),
            new BlockPos(-1, -3, 0),
            new BlockPos(1, -3, 0),
            new BlockPos(2, -3, 0),
            new BlockPos(-1, -3, 1),
            new BlockPos(0, -3, 1),
            new BlockPos(1, -3, 1),
            new BlockPos(0, -3, 2),

            // Layer -2
            new BlockPos(0, -2, -1),
            new BlockPos(-1, -2, 0),
            new BlockPos(1, -2, 0),
            new BlockPos(0, -2, 1),

            // Layer -1
            new BlockPos(0, -1, -2),
            new BlockPos(-1, -1, -1),
            new BlockPos(0, -1, -1),
            new BlockPos(1, -1, -1),
            new BlockPos(-2, -1, 0),
            new BlockPos(-1, -1, 0),
            new BlockPos(1, -1, 0),
            new BlockPos(2, -1, 0),
            new BlockPos(-1, -1, 1),
            new BlockPos(0, -1, 1),
            new BlockPos(1, -1, 1),
            new BlockPos(0, -1, 2),

            // Layer 0
            new BlockPos(0, 0, -1),
            new BlockPos(-1, 0, 0),
            new BlockPos(1, 0, 0),
            new BlockPos(0, 0, 1),

            // Layer 1
            new BlockPos(-1, 1, -1),
            new BlockPos(0, 1, -1),
            new BlockPos(1, 1, -1),
            new BlockPos(-1, 1, 0),
            new BlockPos(1, 1, 0),
            new BlockPos(-1, 1, 1),
            new BlockPos(0, 1, 1),
            new BlockPos(1, 1, 1),

            // Layer 2
            new BlockPos(0, 2, 0),

            // Layer 3
            new BlockPos(0, 3, -1),
            new BlockPos(-1, 3, 0),
            new BlockPos(0, 3, 0),
            new BlockPos(1, 3, 0),
            new BlockPos(0, 3, 1)
    );

    public static final List<BlockPos> CALAMITES_SMALL_VARIANT_A = List.of(
            // Layer -6
            new BlockPos(0, -6, -1),
            new BlockPos(-1, -6, 0),
            new BlockPos(1, -6, 0),
            new BlockPos(0, -6, 1),

            // Layer -5
            new BlockPos(0, -5, -2),
            new BlockPos(-1, -5, -1),
            new BlockPos(0, -5, -1),
            new BlockPos(1, -5, -1),
            new BlockPos(-2, -5, 0),
            new BlockPos(-1, -5, 0),
            new BlockPos(1, -5, 0),
            new BlockPos(2, -5, 0),
            new BlockPos(-1, -5, 1),
            new BlockPos(0, -5, 1),
            new BlockPos(1, -5, 1),
            new BlockPos(0, -5, 2),

            // Layer -4

            // Layer -3
            new BlockPos(0, -3, -1),
            new BlockPos(-1, -3, 0),
            new BlockPos(1, -3, 0),
            new BlockPos(0, -3, 1),

            // Layer -2

            // Layer -1
            new BlockPos(0, -1, -1),
            new BlockPos(-1, -1, 0),
            new BlockPos(1, -1, 0),
            new BlockPos(0, -1, 1)
    );

    public static final List<BlockPos> CALAMITES_SMALL_VARIANT_B = List.of(
            // Layer -6
            new BlockPos(0, -6, -1),
            new BlockPos(-1, -6, 0),
            new BlockPos(1, -6, 0),
            new BlockPos(0, -6, 1),

            // Layer -5

            // Layer -4
            new BlockPos(0, -4, -1),
            new BlockPos(-1, -4, 0),
            new BlockPos(1, -4, 0),
            new BlockPos(0, -4, 1),

            // Layer -3
            new BlockPos(0, -3, -2),
            new BlockPos(-1, -3, -1),
            new BlockPos(0, -3, -1),
            new BlockPos(1, -3, -1),
            new BlockPos(-2, -3, 0),
            new BlockPos(-1, -3, 0),
            new BlockPos(1, -3, 0),
            new BlockPos(2, -3, 0),
            new BlockPos(-1, -3, 1),
            new BlockPos(0, -3, 1),
            new BlockPos(1, -3, 1),
            new BlockPos(0, -3, 2),

            // Layer -2

            // Layer -1
            new BlockPos(0, -1, -1),
            new BlockPos(-1, -1, 0),
            new BlockPos(1, -1, 0),
            new BlockPos(0, -1, 1)
    );

    public static final List<BlockPos> CALAMITES_SMALL_VARIANT_C = List.of(
            // Layer -6
            new BlockPos(0, -6, -1),
            new BlockPos(-1, -6, 0),
            new BlockPos(1, -6, 0),
            new BlockPos(0, -6, 1),

            // Layer -5
            new BlockPos(0, -5, -2),
            new BlockPos(-1, -5, -1),
            new BlockPos(0, -5, -1),
            new BlockPos(1, -5, -1),
            new BlockPos(-2, -5, 0),
            new BlockPos(-1, -5, 0),
            new BlockPos(1, -5, 0),
            new BlockPos(2, -5, 0),
            new BlockPos(-1, -5, 1),
            new BlockPos(0, -5, 1),
            new BlockPos(1, -5, 1),
            new BlockPos(0, -5, 2),

            // Layer -4
            new BlockPos(0, -4, -2),
            new BlockPos(-2, -4, 0),
            new BlockPos(2, -4, 0),
            new BlockPos(0, -4, 2),

            // Layer -3

            // Layer -2

            // Layer -1
            new BlockPos(0, -1, -1),
            new BlockPos(-1, -1, 0),
            new BlockPos(1, -1, 0),
            new BlockPos(0, -1, 1)
    );

    // branch sections for the medium variant numbered by size
    public static final List<BlockPos> CALAMITES_MED_SECTION_0 = List.of(
            // fallback in case it's placed 1 block above the trunk (i.e. floating)
            new BlockPos(0, 0, 0),

            // Layer 0
            new BlockPos(0, 0, -1),
            new BlockPos(-1, 0, 0),
            new BlockPos(1, 0, 0),
            new BlockPos(0, 0, 1)
    );

    public static final List<BlockPos> CALAMITES_MED_SECTION_1 = List.of(
            // Layer -1
            new BlockPos(0, -1, -1),
            new BlockPos(-1, -1, 0),
            new BlockPos(1, -1, 0),
            new BlockPos(0, -1, 1),

            // Layer 0
            new BlockPos(0, 0, -2),
            new BlockPos(-1, 0, -1),
            new BlockPos(0, 0, -1),
            new BlockPos(1, 0, -1),
            new BlockPos(-2, 0, 0),
            new BlockPos(-1, 0, 0),
            new BlockPos(1, 0, 0),
            new BlockPos(2, 0, 0),
            new BlockPos(-1, 0, 1),
            new BlockPos(0, 0, 1),
            new BlockPos(1, 0, 1),
            new BlockPos(0, 0, 2),

            // Layer 1
            new BlockPos(0, 1, -2),
            new BlockPos(-2, 1, 0),
            new BlockPos(2, 1, 0),
            new BlockPos(0, 1, 2)
    );

    public static final List<BlockPos> CALAMITES_MED_SECTION_2 = List.of(
            // Layer 0
            new BlockPos(0, 0, -2),
            new BlockPos(-1, 0, -1),
            new BlockPos(0, 0, -1),
            new BlockPos(1, 0, -1),
            new BlockPos(-2, 0, 0),
            new BlockPos(-1, 0, 0),
            new BlockPos(1, 0, 0),
            new BlockPos(2, 0, 0),
            new BlockPos(-1, 0, 1),
            new BlockPos(0, 0, 1),
            new BlockPos(1, 0, 1),
            new BlockPos(0, 0, 2),

            // Layer 1
            new BlockPos(0, 1, -3),
            new BlockPos(0, 1, -2),
            new BlockPos(-3, 1, 0),
            new BlockPos(-2, 1, 0),
            new BlockPos(2, 1, 0),
            new BlockPos(3, 1, 0),
            new BlockPos(0, 1, 2),
            new BlockPos(0, 1, 3)
    );

    public static final List<BlockPos> CALAMITES_MED_SECTION_3 = List.of(
            // Layer -1
            new BlockPos(0, -1, -2),
            new BlockPos(-1, -1, -1),
            new BlockPos(0, -1, -1),
            new BlockPos(1, -1, -1),
            new BlockPos(-2, -1, 0),
            new BlockPos(-1, -1, 0),
            new BlockPos(1, -1, 0),
            new BlockPos(2, -1, 0),
            new BlockPos(-1, -1, 1),
            new BlockPos(0, -1, 1),
            new BlockPos(1, -1, 1),
            new BlockPos(0, -1, 2),

            // Layer 0
            new BlockPos(0, 0, -3),
            new BlockPos(-2, 0, -2),
            new BlockPos(-1, 0, -2),
            new BlockPos(0, 0, -2),
            new BlockPos(1, 0, -2),
            new BlockPos(2, 0, -2),
            new BlockPos(-2, 0, -1),
            new BlockPos(-1, 0, -1),
            new BlockPos(0, 0, -1),
            new BlockPos(1, 0, -1),
            new BlockPos(2, 0, -1),
            new BlockPos(-3, 0, 0),
            new BlockPos(-2, 0, 0),
            new BlockPos(-1, 0, 0),
            new BlockPos(1, 0, 0),
            new BlockPos(2, 0, 0),
            new BlockPos(3, 0, 0),
            new BlockPos(-2, 0, 1),
            new BlockPos(-1, 0, 1),
            new BlockPos(0, 0, 1),
            new BlockPos(1, 0, 1),
            new BlockPos(2, 0, 1),
            new BlockPos(-2, 0, 2),
            new BlockPos(-1, 0, 2),
            new BlockPos(0, 0, 2),
            new BlockPos(1, 0, 2),
            new BlockPos(2, 0, 2),
            new BlockPos(0, 0, 3),

            // Layer 1
            new BlockPos(0, 1, -3),
            new BlockPos(-2, 1, -2),
            new BlockPos(0, 1, -2),
            new BlockPos(2, 1, -2),
            new BlockPos(-3, 1, 0),
            new BlockPos(-2, 1, 0),
            new BlockPos(2, 1, 0),
            new BlockPos(3, 1, 0),
            new BlockPos(-2, 1, 2),
            new BlockPos(0, 1, 2),
            new BlockPos(2, 1, 2),
            new BlockPos(0, 1, 3),

            // Layer 2
            new BlockPos(0, 2, -3),
            new BlockPos(-3, 2, 0),
            new BlockPos(3, 2, 0),
            new BlockPos(0, 2, 3)
    );

    public static final List<BlockPos> CALAMITES_LARGE_SECTION_0 = List.of(
            // Layer -1
            new BlockPos(0, -1, -1),
            new BlockPos(-1, -1, 0),
            new BlockPos(1, -1, 0),
            new BlockPos(0, -1, 1),

            // Layer 0
            new BlockPos(0, 0, -2),
            new BlockPos(-1, 0, -1),
            new BlockPos(0, 0, -1),
            new BlockPos(1, 0, -1),
            new BlockPos(-2, 0, 0),
            new BlockPos(-1, 0, 0),
            new BlockPos(1, 0, 0),
            new BlockPos(2, 0, 0),
            new BlockPos(-1, 0, 1),
            new BlockPos(0, 0, 1),
            new BlockPos(1, 0, 1),
            new BlockPos(0, 0, 2),

            // Layer 1
            new BlockPos(0, 1, -2),
            new BlockPos(-2, 1, 0),
            new BlockPos(2, 1, 0),
            new BlockPos(0, 1, 2)
    );

    public static final List<BlockPos> CALAMITES_LARGE_SECTION_1 = List.of(
            // Layer -1
            new BlockPos(0, -1, -1),
            new BlockPos(-1, -1, 0),
            new BlockPos(1, -1, 0),
            new BlockPos(0, -1, 1),

            // Layer 0
            new BlockPos(-1, 0, -2),
            new BlockPos(0, 0, -2),
            new BlockPos(1, 0, -2),
            new BlockPos(-2, 0, -1),
            new BlockPos(0, 0, -1),
            new BlockPos(2, 0, -1),
            new BlockPos(-2, 0, 0),
            new BlockPos(-1, 0, 0),
            new BlockPos(1, 0, 0),
            new BlockPos(2, 0, 0),
            new BlockPos(-2, 0, 1),
            new BlockPos(0, 0, 1),
            new BlockPos(2, 0, 1),
            new BlockPos(-1, 0, 2),
            new BlockPos(0, 0, 2),
            new BlockPos(1, 0, 2),

            // Layer 1
            new BlockPos(0, 1, -3),
            new BlockPos(0, 1, -2),
            new BlockPos(-3, 1, 0),
            new BlockPos(-2, 1, 0),
            new BlockPos(2, 1, 0),
            new BlockPos(3, 1, 0),
            new BlockPos(0, 1, 2),
            new BlockPos(0, 1, 3)
    );

    public static final List<BlockPos> CALAMITES_LARGE_SECTION_2 = List.of(
            // Layer -2
            new BlockPos(0, -2, -1),
            new BlockPos(-1, -2, 0),
            new BlockPos(1, -2, 0),
            new BlockPos(0, -2, 1),

            // Layer -1
            new BlockPos(0, -1, -4),
            new BlockPos(0, -1, -3),
            new BlockPos(0, -1, -2),
            new BlockPos(-1, -1, -1),
            new BlockPos(0, -1, -1),
            new BlockPos(1, -1, -1),
            new BlockPos(-4, -1, 0),
            new BlockPos(-3, -1, 0),
            new BlockPos(-2, -1, 0),
            new BlockPos(-1, -1, 0),
            new BlockPos(1, -1, 0),
            new BlockPos(2, -1, 0),
            new BlockPos(3, -1, 0),
            new BlockPos(4, -1, 0),
            new BlockPos(-1, -1, 1),
            new BlockPos(0, -1, 1),
            new BlockPos(1, -1, 1),
            new BlockPos(0, -1, 2),
            new BlockPos(0, -1, 3),
            new BlockPos(0, -1, 4),

            // Layer 0
            new BlockPos(0, 0, -4),
            new BlockPos(-2, 0, -2),
            new BlockPos(-1, 0, -2),
            new BlockPos(1, 0, -2),
            new BlockPos(2, 0, -2),
            new BlockPos(-2, 0, -1),
            new BlockPos(-1, 0, -1),
            new BlockPos(1, 0, -1),
            new BlockPos(2, 0, -1),
            new BlockPos(-4, 0, 0),
            new BlockPos(4, 0, 0),
            new BlockPos(-2, 0, 1),
            new BlockPos(-1, 0, 1),
            new BlockPos(1, 0, 1),
            new BlockPos(2, 0, 1),
            new BlockPos(-2, 0, 2),
            new BlockPos(-1, 0, 2),
            new BlockPos(1, 0, 2),
            new BlockPos(2, 0, 2),
            new BlockPos(0, 0, 4),

            // Layer 1
            new BlockPos(-3, 1, -3),
            new BlockPos(-2, 1, -3),
            new BlockPos(2, 1, -3),
            new BlockPos(3, 1, -3),
            new BlockPos(-3, 1, -2),
            new BlockPos(-2, 1, -2),
            new BlockPos(2, 1, -2),
            new BlockPos(3, 1, -2),
            new BlockPos(-3, 1, 2),
            new BlockPos(-2, 1, 2),
            new BlockPos(2, 1, 2),
            new BlockPos(3, 1, 2),
            new BlockPos(-3, 1, 3),
            new BlockPos(-2, 1, 3),
            new BlockPos(2, 1, 3),
            new BlockPos(3, 1, 3),

            // Layer 2
            new BlockPos(-3, 2, -3),
            new BlockPos(3, 2, -3),
            new BlockPos(-3, 2, 3),
            new BlockPos(3, 2, 3)
    );

    public static final List<BlockPos> CALAMITES_LARGE_SECTION_3 = List.of(
            // Layer -2
            new BlockPos(-1, -2, -1),
            new BlockPos(0, -2, -1),
            new BlockPos(1, -2, -1),
            new BlockPos(-1, -2, 0),
            new BlockPos(1, -2, 0),
            new BlockPos(-1, -2, 1),
            new BlockPos(0, -2, 1),
            new BlockPos(1, -2, 1),

            // Layer -1
            new BlockPos(-1, -1, -2),
            new BlockPos(1, -1, -2),
            new BlockPos(-2, -1, -1),
            new BlockPos(-1, -1, -1),
            new BlockPos(0, -1, -1),
            new BlockPos(1, -1, -1),
            new BlockPos(2, -1, -1),
            new BlockPos(-1, -1, 0),
            new BlockPos(1, -1, 0),
            new BlockPos(-2, -1, 1),
            new BlockPos(-1, -1, 1),
            new BlockPos(0, -1, 1),
            new BlockPos(1, -1, 1),
            new BlockPos(2, -1, 1),
            new BlockPos(-1, -1, 2),
            new BlockPos(1, -1, 2),

            // Layer 0
            new BlockPos(0, 0, -3),
            new BlockPos(0, 0, -2),
            new BlockPos(0, 0, -1),
            new BlockPos(-3, 0, 0),
            new BlockPos(-2, 0, 0),
            new BlockPos(-1, 0, 0),
            new BlockPos(1, 0, 0),
            new BlockPos(2, 0, 0),
            new BlockPos(3, 0, 0),
            new BlockPos(0, 0, 1),
            new BlockPos(0, 0, 2),
            new BlockPos(0, 0, 3),

            // Layer 1
            new BlockPos(0, 1, -5),
            new BlockPos(-1, 1, -4),
            new BlockPos(0, 1, -4),
            new BlockPos(1, 1, -4),
            new BlockPos(-1, 1, -3),
            new BlockPos(0, 1, -3),
            new BlockPos(1, 1, -3),
            new BlockPos(-4, 1, -1),
            new BlockPos(-3, 1, -1),
            new BlockPos(3, 1, -1),
            new BlockPos(4, 1, -1),
            new BlockPos(-5, 1, 0),
            new BlockPos(-4, 1, 0),
            new BlockPos(-3, 1, 0),
            new BlockPos(3, 1, 0),
            new BlockPos(4, 1, 0),
            new BlockPos(5, 1, 0),
            new BlockPos(-4, 1, 1),
            new BlockPos(-3, 1, 1),
            new BlockPos(3, 1, 1),
            new BlockPos(4, 1, 1),
            new BlockPos(-1, 1, 3),
            new BlockPos(0, 1, 3),
            new BlockPos(1, 1, 3),
            new BlockPos(-1, 1, 4),
            new BlockPos(0, 1, 4),
            new BlockPos(1, 1, 4),
            new BlockPos(0, 1, 5),

            // Layer 2
            new BlockPos(0, 2, -5),
            new BlockPos(0, 2, -4),
            new BlockPos(-5, 2, 0),
            new BlockPos(-4, 2, 0),
            new BlockPos(4, 2, 0),
            new BlockPos(5, 2, 0),
            new BlockPos(0, 2, 4),
            new BlockPos(0, 2, 5)
    );

    public static final List<BlockPos> CALAMITES_LARGE_SECTION_4 = List.of(
            // Layer -2
            new BlockPos(-1, -2, -1),
            new BlockPos(1, -2, -1),
            new BlockPos(-1, -2, 1),
            new BlockPos(1, -2, 1),

            // Layer -1
            new BlockPos(-3, -1, -3),
            new BlockPos(-1, -1, -3),
            new BlockPos(0, -1, -3),
            new BlockPos(1, -1, -3),
            new BlockPos(3, -1, -3),
            new BlockPos(-2, -1, -2),
            new BlockPos(-1, -1, -2),
            new BlockPos(0, -1, -2),
            new BlockPos(1, -1, -2),
            new BlockPos(2, -1, -2),
            new BlockPos(-3, -1, -1),
            new BlockPos(-2, -1, -1),
            new BlockPos(-1, -1, -1),
            new BlockPos(0, -1, -1),
            new BlockPos(1, -1, -1),
            new BlockPos(2, -1, -1),
            new BlockPos(3, -1, -1),
            new BlockPos(-3, -1, 0),
            new BlockPos(-2, -1, 0),
            new BlockPos(-1, -1, 0),
            new BlockPos(1, -1, 0),
            new BlockPos(2, -1, 0),
            new BlockPos(3, -1, 0),
            new BlockPos(-3, -1, 1),
            new BlockPos(-2, -1, 1),
            new BlockPos(-1, -1, 1),
            new BlockPos(0, -1, 1),
            new BlockPos(1, -1, 1),
            new BlockPos(2, -1, 1),
            new BlockPos(3, -1, 1),
            new BlockPos(-2, -1, 2),
            new BlockPos(-1, -1, 2),
            new BlockPos(0, -1, 2),
            new BlockPos(1, -1, 2),
            new BlockPos(2, -1, 2),
            new BlockPos(-3, -1, 3),
            new BlockPos(-1, -1, 3),
            new BlockPos(0, -1, 3),
            new BlockPos(1, -1, 3),
            new BlockPos(3, -1, 3),

            // Layer 0
            new BlockPos(0, 0, -5),
            new BlockPos(-4, 0, -4),
            new BlockPos(-3, 0, -4),
            new BlockPos(-1, 0, -4),
            new BlockPos(0, 0, -4),
            new BlockPos(1, 0, -4),
            new BlockPos(3, 0, -4),
            new BlockPos(4, 0, -4),
            new BlockPos(-4, 0, -3),
            new BlockPos(-3, 0, -3),
            new BlockPos(-2, 0, -3),
            new BlockPos(-1, 0, -3),
            new BlockPos(0, 0, -3),
            new BlockPos(1, 0, -3),
            new BlockPos(2, 0, -3),
            new BlockPos(3, 0, -3),
            new BlockPos(4, 0, -3),
            new BlockPos(-3, 0, -2),
            new BlockPos(-2, 0, -2),
            new BlockPos(0, 0, -2),
            new BlockPos(2, 0, -2),
            new BlockPos(3, 0, -2),
            new BlockPos(-4, 0, -1),
            new BlockPos(-3, 0, -1),
            new BlockPos(3, 0, -1),
            new BlockPos(4, 0, -1),
            new BlockPos(-5, 0, 0),
            new BlockPos(-4, 0, 0),
            new BlockPos(-3, 0, 0),
            new BlockPos(-2, 0, 0),
            new BlockPos(2, 0, 0),
            new BlockPos(3, 0, 0),
            new BlockPos(4, 0, 0),
            new BlockPos(5, 0, 0),
            new BlockPos(-4, 0, 1),
            new BlockPos(-3, 0, 1),
            new BlockPos(3, 0, 1),
            new BlockPos(4, 0, 1),
            new BlockPos(-3, 0, 2),
            new BlockPos(-2, 0, 2),
            new BlockPos(0, 0, 2),
            new BlockPos(2, 0, 2),
            new BlockPos(3, 0, 2),
            new BlockPos(-4, 0, 3),
            new BlockPos(-3, 0, 3),
            new BlockPos(-2, 0, 3),
            new BlockPos(-1, 0, 3),
            new BlockPos(0, 0, 3),
            new BlockPos(1, 0, 3),
            new BlockPos(2, 0, 3),
            new BlockPos(3, 0, 3),
            new BlockPos(4, 0, 3),
            new BlockPos(-4, 0, 4),
            new BlockPos(-3, 0, 4),
            new BlockPos(-1, 0, 4),
            new BlockPos(0, 0, 4),
            new BlockPos(1, 0, 4),
            new BlockPos(3, 0, 4),
            new BlockPos(4, 0, 4),
            new BlockPos(0, 0, 5),

            // Layer 1
            new BlockPos(-1, 1, -5),
            new BlockPos(0, 1, -5),
            new BlockPos(1, 1, -5),
            new BlockPos(-4, 1, -4),
            new BlockPos(0, 1, -4),
            new BlockPos(4, 1, -4),
            new BlockPos(-3, 1, -3),
            new BlockPos(3, 1, -3),
            new BlockPos(-5, 1, -1),
            new BlockPos(5, 1, -1),
            new BlockPos(-5, 1, 0),
            new BlockPos(-4, 1, 0),
            new BlockPos(4, 1, 0),
            new BlockPos(5, 1, 0),
            new BlockPos(-5, 1, 1),
            new BlockPos(5, 1, 1),
            new BlockPos(-3, 1, 3),
            new BlockPos(3, 1, 3),
            new BlockPos(-4, 1, 4),
            new BlockPos(0, 1, 4),
            new BlockPos(4, 1, 4),
            new BlockPos(-1, 1, 5),
            new BlockPos(0, 1, 5),
            new BlockPos(1, 1, 5),

            // Layer 2
            new BlockPos(0, 2, -5),
            new BlockPos(-5, 2, 0),
            new BlockPos(5, 2, 0),
            new BlockPos(0, 2, 5)
    );

    public static final List<BlockPos> CALAMITES_LARGE_SECTION_5 = List.of(
            // Layer -2
            new BlockPos(-1, -2, -3),
            new BlockPos(0, -2, -3),
            new BlockPos(1, -2, -3),
            new BlockPos(0, -2, -2),
            new BlockPos(-3, -2, -1),
            new BlockPos(0, -2, -1),
            new BlockPos(3, -2, -1),
            new BlockPos(-3, -2, 0),
            new BlockPos(-2, -2, 0),
            new BlockPos(-1, -2, 0),
            new BlockPos(1, -2, 0),
            new BlockPos(2, -2, 0),
            new BlockPos(3, -2, 0),
            new BlockPos(-3, -2, 1),
            new BlockPos(0, -2, 1),
            new BlockPos(3, -2, 1),
            new BlockPos(0, -2, 2),
            new BlockPos(-1, -2, 3),
            new BlockPos(0, -2, 3),
            new BlockPos(1, -2, 3),

            // Layer -1
            new BlockPos(0, -1, -6),
            new BlockPos(-1, -1, -5),
            new BlockPos(0, -1, -5),
            new BlockPos(1, -1, -5),
            new BlockPos(-1, -1, -4),
            new BlockPos(0, -1, -4),
            new BlockPos(1, -1, -4),
            new BlockPos(0, -1, -3),
            new BlockPos(-5, -1, -1),
            new BlockPos(-4, -1, -1),
            new BlockPos(4, -1, -1),
            new BlockPos(5, -1, -1),
            new BlockPos(-6, -1, 0),
            new BlockPos(-5, -1, 0),
            new BlockPos(-4, -1, 0),
            new BlockPos(-3, -1, 0),
            new BlockPos(3, -1, 0),
            new BlockPos(4, -1, 0),
            new BlockPos(5, -1, 0),
            new BlockPos(6, -1, 0),
            new BlockPos(-5, -1, 1),
            new BlockPos(-4, -1, 1),
            new BlockPos(4, -1, 1),
            new BlockPos(5, -1, 1),
            new BlockPos(0, -1, 3),
            new BlockPos(-1, -1, 4),
            new BlockPos(0, -1, 4),
            new BlockPos(1, -1, 4),
            new BlockPos(-1, -1, 5),
            new BlockPos(0, -1, 5),
            new BlockPos(1, -1, 5),
            new BlockPos(0, -1, 6),

            // Layer 0
            new BlockPos(0, 0, -6),
            new BlockPos(0, 0, -5),
            new BlockPos(-4, 0, -4),
            new BlockPos(4, 0, -4),
            new BlockPos(-3, 0, -3),
            new BlockPos(-2, 0, -3),
            new BlockPos(2, 0, -3),
            new BlockPos(3, 0, -3),
            new BlockPos(-3, 0, -2),
            new BlockPos(-2, 0, -2),
            new BlockPos(-1, 0, -2),
            new BlockPos(1, 0, -2),
            new BlockPos(2, 0, -2),
            new BlockPos(3, 0, -2),
            new BlockPos(-2, 0, -1),
            new BlockPos(-1, 0, -1),
            new BlockPos(0, 0, -1),
            new BlockPos(1, 0, -1),
            new BlockPos(2, 0, -1),
            new BlockPos(-6, 0, 0),
            new BlockPos(-5, 0, 0),
            new BlockPos(-1, 0, 0),
            new BlockPos(1, 0, 0),
            new BlockPos(5, 0, 0),
            new BlockPos(6, 0, 0),
            new BlockPos(-2, 0, 1),
            new BlockPos(-1, 0, 1),
            new BlockPos(0, 0, 1),
            new BlockPos(1, 0, 1),
            new BlockPos(2, 0, 1),
            new BlockPos(-3, 0, 2),
            new BlockPos(-2, 0, 2),
            new BlockPos(-1, 0, 2),
            new BlockPos(1, 0, 2),
            new BlockPos(2, 0, 2),
            new BlockPos(3, 0, 2),
            new BlockPos(-3, 0, 3),
            new BlockPos(-2, 0, 3),
            new BlockPos(2, 0, 3),
            new BlockPos(3, 0, 3),
            new BlockPos(-4, 0, 4),
            new BlockPos(4, 0, 4),
            new BlockPos(0, 0, 5),
            new BlockPos(0, 0, 6),

            // Layer 1
            new BlockPos(0, 1, -6),
            new BlockPos(-4, 1, -5),
            new BlockPos(4, 1, -5),
            new BlockPos(-5, 1, -4),
            new BlockPos(-4, 1, -4),
            new BlockPos(-3, 1, -4),
            new BlockPos(3, 1, -4),
            new BlockPos(4, 1, -4),
            new BlockPos(5, 1, -4),
            new BlockPos(-4, 1, -3),
            new BlockPos(-3, 1, -3),
            new BlockPos(3, 1, -3),
            new BlockPos(4, 1, -3),
            new BlockPos(-1, 1, -1),
            new BlockPos(1, 1, -1),
            new BlockPos(-6, 1, 0),
            new BlockPos(6, 1, 0),
            new BlockPos(-1, 1, 1),
            new BlockPos(1, 1, 1),
            new BlockPos(-4, 1, 3),
            new BlockPos(-3, 1, 3),
            new BlockPos(3, 1, 3),
            new BlockPos(4, 1, 3),
            new BlockPos(-5, 1, 4),
            new BlockPos(-4, 1, 4),
            new BlockPos(-3, 1, 4),
            new BlockPos(3, 1, 4),
            new BlockPos(4, 1, 4),
            new BlockPos(5, 1, 4),
            new BlockPos(-4, 1, 5),
            new BlockPos(4, 1, 5),
            new BlockPos(0, 1, 6)
    );
}
