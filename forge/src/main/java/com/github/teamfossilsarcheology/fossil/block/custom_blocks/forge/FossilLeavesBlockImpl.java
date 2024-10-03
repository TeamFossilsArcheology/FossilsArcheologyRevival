package com.github.teamfossilsarcheology.fossil.block.custom_blocks.forge;

import com.github.teamfossilsarcheology.fossil.block.custom_blocks.FossilLeavesBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;

public class FossilLeavesBlockImpl {

    public static FossilLeavesBlock get(BlockBehaviour.Properties properties) {
        return new FossilLeavesBlock(properties) {

            @Override
            public boolean isFlammable(BlockState state, BlockGetter world, BlockPos pos, Direction face) {
                return true;
            }

            @Override
            public int getFlammability(BlockState state, BlockGetter world, BlockPos pos, Direction face) {
                return 60;
            }

            @Override
            public int getFireSpreadSpeed(BlockState state, BlockGetter world, BlockPos pos, Direction face) {
                return 30;
            }
        };
    }
}
