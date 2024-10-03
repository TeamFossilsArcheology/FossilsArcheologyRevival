package com.github.teamfossilsarcheology.fossil.fabric.block.entity;

import com.github.teamfossilsarcheology.fossil.config.FossilConfig;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import team.reborn.energy.api.base.SimpleEnergyStorage;

public abstract class FabricEnergyContainerBlockEntity extends FabricContainerBlockEntity {
    public final SimpleEnergyStorage energyStorage = new SimpleEnergyStorage(FossilConfig.getInt(FossilConfig.MACHINE_MAX_ENERGY), FossilConfig.getInt(FossilConfig.MACHINE_TRANSFER_RATE), 0) {
        @Override
        protected void onFinalCommit() {
            setChanged();
        }
    };

    protected FabricEnergyContainerBlockEntity(BlockEntityType<?> blockEntityType, BlockPos blockPos, BlockState blockState) {
        super(blockEntityType, blockPos, blockState);
    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        energyStorage.amount = tag.getLong("Energy");
    }

    @Override
    protected void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
        tag.putLong("Energy", energyStorage.amount);
    }
}
