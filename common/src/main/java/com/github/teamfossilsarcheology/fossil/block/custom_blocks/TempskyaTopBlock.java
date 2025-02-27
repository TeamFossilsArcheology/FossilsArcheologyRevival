package com.github.teamfossilsarcheology.fossil.block.custom_blocks;

import com.github.teamfossilsarcheology.fossil.block.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.DoublePlantBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;

public class TempskyaTopBlock extends DoublePlantBlock {
    private static final VoxelShape SHAPE = Block.box(1, 0, 1, 15, 32, 15);

    public TempskyaTopBlock(Properties properties) {
        super(properties);
    }

    @Override
    protected boolean mayPlaceOn(BlockState state, BlockGetter level, BlockPos pos) {
        return state.is(ModBlocks.TEMPSKYA_LOG.get());
    }

    @Override
    public @NotNull VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        if (state.getValue(HALF) == DoubleBlockHalf.UPPER) {
            return SHAPE.move(0, -1, 0);
        }
        return SHAPE;
    }
}
