package com.github.teamfossilsarcheology.fossil.block.custom_blocks;

import com.github.teamfossilsarcheology.fossil.block.PrehistoricPlantInfo;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;

public class VacciniumBushBlock extends ShortBerryBushBlock {
    private static final VoxelShape[] SHAPES_BY_AGE = new VoxelShape[]{Block.box(0, 0, 0, 16, 7, 16),
            Block.box(0, 0, 0, 16, 16, 16), Block.box(0, 0, 0, 16, 16, 16), Block.box(0, 0, 0, 16, 16, 16)};
    public static final IntegerProperty AGE = BlockStateProperties.AGE_3;

    public VacciniumBushBlock(VoxelShape shape, PrehistoricPlantInfo info) {
        super(shape, info);
    }

    @Override
    public @NotNull VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return SHAPES_BY_AGE[state.getValue(AGE)];
    }

    @Override
    public IntegerProperty ageProperty() {
        return AGE;
    }
}
