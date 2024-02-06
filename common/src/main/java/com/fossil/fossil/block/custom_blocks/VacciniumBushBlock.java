package com.fossil.fossil.block.custom_blocks;

import com.fossil.fossil.block.PrehistoricPlantInfo;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.shapes.VoxelShape;

public class VacciniumBushBlock extends ShortBerryBushBlock {
    public static final IntegerProperty AGE = BlockStateProperties.AGE_3;

    public VacciniumBushBlock(VoxelShape shape, PrehistoricPlantInfo info) {
        super(shape, info);
    }

    @Override
    public IntegerProperty ageProperty() {
        return AGE;
    }
}
