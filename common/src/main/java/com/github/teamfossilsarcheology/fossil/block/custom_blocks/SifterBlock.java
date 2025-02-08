package com.github.teamfossilsarcheology.fossil.block.custom_blocks;

import com.github.teamfossilsarcheology.fossil.block.entity.ModBlockEntities;
import com.github.teamfossilsarcheology.fossil.block.entity.SifterBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

/**
 * @see SifterBlockEntity
 * @see com.github.teamfossilsarcheology.fossil.inventory.SifterMenu
 * @see com.github.teamfossilsarcheology.fossil.client.gui.SifterScreen
 */
public class SifterBlock extends CustomEntityBlock {

    public SifterBlock(Properties properties) {
        super(properties);
        registerDefaultState(getStateDefinition().any().setValue(ACTIVE, false));
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> blockEntityType) {
        return level.isClientSide ? null : createTickerHelper(blockEntityType, ModBlockEntities.SIFTER.get(), SifterBlockEntity::serverTick);
    }

    @Override
    public boolean hasAnalogOutputSignal(BlockState state) {
        return true;
    }

    @Override
    public int getAnalogOutputSignal(BlockState state, Level level, BlockPos pos) {
        return AbstractContainerMenu.getRedstoneSignalFromBlockEntity(level.getBlockEntity(pos));
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return SifterBlockEntity.get(pos, state);
    }
}
