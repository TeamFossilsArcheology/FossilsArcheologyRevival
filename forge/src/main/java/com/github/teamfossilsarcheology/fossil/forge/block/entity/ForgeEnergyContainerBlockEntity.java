package com.github.teamfossilsarcheology.fossil.forge.block.entity;

import com.github.teamfossilsarcheology.fossil.config.FossilConfig;
import com.github.teamfossilsarcheology.fossil.forge.energy.FAEnergyStorage;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public abstract class ForgeEnergyContainerBlockEntity extends ForgeContainerBlockEntity {
    protected final FAEnergyStorage energyStorage = new FAEnergyStorage(FossilConfig.getInt(FossilConfig.MACHINE_MAX_ENERGY), FossilConfig.getInt(FossilConfig.MACHINE_TRANSFER_RATE), FossilConfig.getInt(FossilConfig.MACHINE_ENERGY_USAGE), 0) {
        @Override
        protected void onChange() {
            setChanged();
        }
    };
    private LazyOptional<IEnergyStorage> lazyEnergyStorage = LazyOptional.empty();


    protected ForgeEnergyContainerBlockEntity(BlockEntityType<?> blockEntityType, BlockPos blockPos, BlockState blockState) {
        super(blockEntityType, blockPos, blockState);
    }

    @Override
    public void onLoad() {
        super.onLoad();
        lazyEnergyStorage = LazyOptional.of(() -> energyStorage);
    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        energyStorage.deserializeNBT(tag.get("Energy"));
    }

    @Override
    protected void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
        tag.put("Energy", energyStorage.serializeNBT());
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if (!remove && side != null && cap == CapabilityEnergy.ENERGY && FossilConfig.isEnabled(FossilConfig.MACHINES_REQUIRE_ENERGY)) {
            return lazyEnergyStorage.cast();
        }
        return super.getCapability(cap, side);
    }

    @Override
    public void invalidateCaps() {
        super.invalidateCaps();
        lazyEnergyStorage.invalidate();
    }
}
