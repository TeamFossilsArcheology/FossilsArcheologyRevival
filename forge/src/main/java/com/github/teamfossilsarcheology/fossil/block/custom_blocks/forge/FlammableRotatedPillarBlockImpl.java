package com.github.teamfossilsarcheology.fossil.block.custom_blocks.forge;

import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.ToolAction;
import net.minecraftforge.common.ToolActions;

import javax.annotation.Nullable;
import java.util.Map;

public class FlammableRotatedPillarBlockImpl {

    private static final Map<Block, Block> STRIPPABLES = new Object2ObjectOpenHashMap<>();

    public static RotatedPillarBlock get(BlockBehaviour.Properties properties) {
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
                if (ToolActions.AXE_STRIP == toolAction) {
                    Block block = STRIPPABLES.get(state.getBlock());
                    return block != null ? block.defaultBlockState().setValue(AXIS, state.getValue(AXIS)) : null;
                }
                return super.getToolModifiedState(state, context, toolAction, simulate);
            }
        };
    }

    public static void registerStripped(Block base, Block stripped) {
        STRIPPABLES.put(base, stripped);
    }
}
