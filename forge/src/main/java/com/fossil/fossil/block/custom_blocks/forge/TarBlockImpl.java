package com.fossil.fossil.block.custom_blocks.forge;

import com.fossil.fossil.block.custom_blocks.TarBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FlowingFluid;

import java.util.function.Supplier;

public class TarBlockImpl {
    public static TarBlock get(Supplier<? extends FlowingFluid> fluid, BlockBehaviour.Properties properties) {
        return new TarBlock(fluid, properties) {
            @Override
            public boolean isFlammable(BlockState state, BlockGetter world, BlockPos pos, Direction face) {
                return true;
            }

            @Override
            public int getFlammability(BlockState state, BlockGetter world, BlockPos pos, Direction face) {
                return 30;
            }

            @Override
            public int getFireSpreadSpeed(BlockState state, BlockGetter world, BlockPos pos, Direction face) {
                return 30;
            }
        };
    }
}
