package com.fossil.fossil.block.custom_blocks;

import com.fossil.fossil.block.PrehistoricPlantType;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.shapes.VoxelShape;

public class VacciniumBushBlock extends ShortBerryBushBlock {
    public static final IntegerProperty AGE = BlockStateProperties.AGE_3;

    public VacciniumBushBlock(VoxelShape shape, PrehistoricPlantType type) {
        super(shape, type);
    }

    @Override
    public IntegerProperty ageProperty() {
        return AGE;
    }
}
