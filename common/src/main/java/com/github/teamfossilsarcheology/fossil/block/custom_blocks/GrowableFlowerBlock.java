package com.github.teamfossilsarcheology.fossil.block.custom_blocks;

import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.BushBlock;
import net.minecraft.world.level.block.DoublePlantBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.phys.shapes.VoxelShape;

public class GrowableFlowerBlock extends ShortFlowerBlock {

    private final RegistrySupplier<? extends BushBlock> tallFlower;

    public GrowableFlowerBlock(RegistrySupplier<? extends BushBlock> tallFlower, VoxelShape shape) {
        super(shape);
        this.tallFlower = tallFlower;
    }

    @Override
    public void performBonemeal(ServerLevel level, RandomSource random, BlockPos pos, BlockState state) {
        level.setBlock(pos, tallFlower.get().defaultBlockState().setValue(DoublePlantBlock.HALF, DoubleBlockHalf.LOWER), 2);
        level.setBlock(pos.above(), tallFlower.get().defaultBlockState().setValue(DoublePlantBlock.HALF, DoubleBlockHalf.UPPER), 2);
    }
}
