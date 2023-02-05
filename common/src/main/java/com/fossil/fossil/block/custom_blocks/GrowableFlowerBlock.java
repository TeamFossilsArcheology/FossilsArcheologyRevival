package com.fossil.fossil.block.custom_blocks;

import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;

import java.util.Random;

public class GrowableFlowerBlock extends ShortFlowerBlock {

    private final RegistrySupplier<TallFlowerBlock> tallFlower;

    public GrowableFlowerBlock(Properties properties, RegistrySupplier<TallFlowerBlock> tallFlower) {
        super(properties);
        this.tallFlower = tallFlower;
    }

    @Override
    public void performBonemeal(ServerLevel level, Random random, BlockPos pos, BlockState state) {
        level.setBlock(pos, tallFlower.get().defaultBlockState().setValue(TallFlowerBlock.HALF, DoubleBlockHalf.LOWER), 2);
        level.setBlock(pos.above(), tallFlower.get().defaultBlockState().setValue(TallFlowerBlock.HALF, DoubleBlockHalf.UPPER), 2);
    }
}
