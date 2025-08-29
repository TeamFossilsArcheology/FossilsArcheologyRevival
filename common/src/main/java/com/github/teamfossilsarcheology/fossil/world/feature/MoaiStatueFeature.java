package com.github.teamfossilsarcheology.fossil.world.feature;

import com.github.teamfossilsarcheology.fossil.block.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.Half;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;

public class MoaiStatueFeature extends Feature<NoneFeatureConfiguration> {
    public MoaiStatueFeature() {
        super(NoneFeatureConfiguration.CODEC);
    }

    @Override
    public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> context) {
        WorldGenLevel level = context.level();
        BlockPos pos = context.origin();
        if (level.isWaterAt(pos.below())) {
            int floorHeight = level.getHeight(Heightmap.Types.OCEAN_FLOOR, pos.getX(), pos.getZ());
            int surfaceHeight = level.getHeight(Heightmap.Types.WORLD_SURFACE, pos.getX(), pos.getZ());
            if (surfaceHeight - floorHeight > 3) {
                return false;
            } else {
                pos = pos.atY(floorHeight);
            }
        }

        BlockState bricks = ModBlocks.ANCIENT_STONE_BRICKS.get().defaultBlockState();
        BlockState stairs = ModBlocks.ANCIENT_STONE_STAIRS.get().defaultBlockState();
        BlockState stone = ModBlocks.ANCIENT_STONE.get().defaultBlockState();

        for (int z = 0; z < 2; z++) {
            for (int y = 0; y < 14; y++) {
                for (int x = 1; x < 4; x++) {
                    level.setBlock(pos.offset(x, y, z), bricks, 2);
                }
                if (y < 5) {
                    level.setBlock(pos.offset(0, y, z), bricks, 2);
                    level.setBlock(pos.offset(4, y, z), bricks, 2);
                }
            }
        }
        level.setBlock(pos.offset(1, 0, 1), stone, 2);
        level.setBlock(pos.offset(3, 0, 1), stone, 2);
        level.setBlock(pos.offset(2, 1, 1), stone, 2);
        level.setBlock(pos.offset(1, 2, 1), stone, 2);
        level.setBlock(pos.offset(3, 2, 1), stone, 2);
        level.setBlock(pos.offset(2, 3, 1), stone, 2);
        for (int x = 1; x < 4; x++) {
            level.setBlock(pos.offset(x, 6, 2), bricks, 2);
            level.setBlock(pos.offset(x, 7, 2), bricks, 2);
            level.setBlock(pos.offset(x, 9, 2), bricks, 2);
            level.setBlock(pos.offset(x, 10, 2), bricks, 2);
        }
        level.setBlock(pos.offset(2, 11, 2), bricks, 2);
        level.setBlock(pos.offset(2, 12, 2), bricks, 2);
        level.setBlock(pos.offset(0, 9, 0), bricks, 2);
        level.setBlock(pos.offset(0, 10, 0), bricks, 2);
        level.setBlock(pos.offset(4, 9, 0), bricks, 2);
        level.setBlock(pos.offset(4, 10, 0), bricks, 2);
        level.setBlock(pos.offset(2, 9, 3), bricks, 2);
        level.setBlock(pos.offset(2, 10, 3), bricks, 2);
        level.setBlock(pos.offset(0, 8, 0), stairs.setValue(StairBlock.FACING, Direction.EAST).setValue(StairBlock.HALF, Half.TOP), 2);
        level.setBlock(pos.offset(4, 8, 0), stairs.setValue(StairBlock.FACING, Direction.WEST).setValue(StairBlock.HALF, Half.TOP), 2);
        level.setBlock(pos.offset(0, 11, 0), stairs.setValue(StairBlock.FACING, Direction.EAST).setValue(StairBlock.HALF, Half.BOTTOM), 2);
        level.setBlock(pos.offset(4, 11, 0), stairs.setValue(StairBlock.FACING, Direction.WEST).setValue(StairBlock.HALF, Half.BOTTOM), 2);
        level.setBlock(pos.offset(1, 11, 2), stairs, 2);
        level.setBlock(pos.offset(2, 11, 3), stairs, 2);
        level.setBlock(pos.offset(3, 11, 2), stairs, 2);
        level.setBlock(pos.offset(1, 13, 2), stairs, 2);
        level.setBlock(pos.offset(2, 13, 2), stairs, 2);
        level.setBlock(pos.offset(3, 13, 2), stairs, 2);

        if (level.getRandom().nextInt(3) == 0) {
            BlockState hat = Blocks.RED_TERRACOTTA.defaultBlockState();
            for (int x = 1; x < 4; x++) {
                for (int z = 0; z < 3; z++) {
                    level.setBlock(pos.offset(x, 14, z), hat, 2);
                    level.setBlock(pos.offset(x, 15, z), hat, 2);
                }
            }
            level.setBlock(pos.offset(2, 16, 2), hat, 2);
        }
        return true;
    }
}
