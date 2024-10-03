package com.github.teamfossilsarcheology.fossil.block.custom_blocks;

import com.github.teamfossilsarcheology.fossil.block.PrehistoricPlantInfo;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.shapes.VoxelShape;

public class EphedraBushBlock extends ShortBerryBushBlock {
    public static final IntegerProperty AGE = BlockStateProperties.AGE_1;

    public EphedraBushBlock(VoxelShape shape, PrehistoricPlantInfo info) {
        super(shape, info);
    }

    @Override
    public IntegerProperty ageProperty() {
        return AGE;
    }
}
