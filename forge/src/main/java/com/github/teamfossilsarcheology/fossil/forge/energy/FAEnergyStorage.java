package com.github.teamfossilsarcheology.fossil.forge.energy;

import com.github.teamfossilsarcheology.fossil.block.entity.CommonEnergyStorage;
import net.minecraft.nbt.Tag;
import net.minecraftforge.energy.EnergyStorage;

public abstract class FAEnergyStorage extends EnergyStorage implements CommonEnergyStorage {

    protected FAEnergyStorage(int capacity, int maxReceive, int maxExtract, int energy) {
        super(capacity, maxReceive, maxExtract, energy);
    }

    @Override
    public int receiveEnergy(int maxReceive, boolean simulate) {
        int received = super.receiveEnergy(maxReceive, simulate);
        if (received != 0) {
            onChange();
        }
        return received;
    }

    @Override
    public int extractEnergy(int maxExtract, boolean simulate) {
        int extracted = super.extractEnergy(maxExtract, simulate);
        if (extracted != 0) {
            onChange();
        }
        return extracted;
    }

    protected abstract void onChange();

    @Override
    public void deserializeNBT(Tag nbt) {
        if (nbt == null) {
            energy = 0;
        } else {
            super.deserializeNBT(nbt);
        }
    }

    @Override
    public int getEnergy() {
        return getEnergyStored();
    }

    @Override
    public void extractEnergy(int maxExtract) {
        extractEnergy(maxExtract, false);
    }

    @Override
    public void load(Tag tag) {
        deserializeNBT(tag);
    }

    @Override
    public Tag save() {
        return serializeNBT();
    }
}
