package com.fossil.fossil.block.custom_blocks.forge;

import com.fossil.fossil.block.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.ToolAction;

import javax.annotation.Nullable;

public class ModFlammableRotatedPillarBlockImpl {

    public static Block get(BlockBehaviour.Properties properties) {
        return new RotatedPillarBlock(properties) {
            @Override
            public boolean isFlammable(BlockState state, BlockGetter world, BlockPos pos, Direction face) {
                return true;
            }

            @Override
            public int getFlammability(BlockState state, BlockGetter world, BlockPos pos, Direction face) {
                return 5;
            }

            @Override
            public int getFireSpreadSpeed(BlockState state, BlockGetter world, BlockPos pos, Direction face) {
                return 5;
            }

            @Nullable
            @Override
            public BlockState getToolModifiedState(BlockState state, UseOnContext context, ToolAction toolAction, boolean simulate) {
                ItemStack stack = context.getItemInHand();

                if(!simulate && stack.getItem() instanceof AxeItem) {
                    if(state.is(ModBlocks.CORDAITES_LOG.get())) {
                        return ModBlocks.STRIPPED_CORDAITES_LOG.get().defaultBlockState().setValue(AXIS, state.getValue(AXIS));
                    }
                    if(state.is(ModBlocks.CORDAITES_WOOD.get())) {
                        return ModBlocks.STRIPPED_CORDAITES_WOOD.get().defaultBlockState().setValue(AXIS, state.getValue(AXIS));
                    }
                }

                return super.getToolModifiedState(state, context, toolAction, simulate);
            }
        };
    }
}
