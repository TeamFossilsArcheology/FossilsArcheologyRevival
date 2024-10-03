package com.github.teamfossilsarcheology.fossil.block.custom_blocks;

import com.github.teamfossilsarcheology.fossil.block.PrehistoricPlantInfo;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.shapes.VoxelShape;

public class CrataegusBushBlock extends TallBerryBushBlock {
    public static final IntegerProperty AGE = BlockStateProperties.AGE_3;
    private static final VoxelShape[] SHAPES_BY_AGE = new VoxelShape[]{Block.box(1, 0, 1, 15, 14, 15),
            Block.box(1, 0, 1, 15, 23, 15), Block.box(1, 0, 1, 15, 23, 15),
            Block.box(1, 0, 1, 15, 23, 15)};


    public CrataegusBushBlock(PrehistoricPlantInfo info) {
        super(info);
    }

    @Override
    public IntegerProperty ageProperty() {
        return AGE;
    }

    @Override
    protected VoxelShape[] getShapesByAge() {
        return SHAPES_BY_AGE;
    }

    protected int getCreateUpperAge() {
        return 1;
    }
}
