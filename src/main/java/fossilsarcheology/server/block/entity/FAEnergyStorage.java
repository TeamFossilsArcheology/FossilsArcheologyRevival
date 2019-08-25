package fossilsarcheology.server.block.entity;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.energy.IEnergyStorage;

public class FAEnergyStorage implements IEnergyStorage, INBTSerializable<NBTTagCompound> {

    public int energy;
    protected int capacity;
    protected int maxReceive;
    protected int maxExtract;

    public FAEnergyStorage(int capacity) {
        this(capacity, capacity, capacity, 0);
    }

    public FAEnergyStorage(int capacity, int maxTransfer) {
        this(capacity, maxTransfer, maxTransfer, 0);
    }

    public FAEnergyStorage(int capacity, int maxReceive, int maxExtract) {
        this(capacity, maxReceive, maxExtract, 0);
    }

    public FAEnergyStorage(int capacity, int maxReceive, int maxExtract, int energy) {
        this.capacity = capacity;
        this.maxReceive = maxReceive;
        this.maxExtract = maxExtract;
        this.energy = Math.max(0, Math.min(capacity, energy));
    }

    @Override
    public int receiveEnergy(int maxReceive, boolean simulate) {
        if (!canReceive())
            return 0;

        int energyReceived = Math.min(capacity - energy, Math.min(this.maxReceive, maxReceive));
        if (!simulate)
            energy += energyReceived;
        return energyReceived;
    }

    @Override
    public int extractEnergy(int maxExtract, boolean simulate) {
        if (!canExtract())
            return 0;

        int energyExtracted = Math.min(energy, Math.min(this.maxExtract, maxExtract));
        if (!simulate)
            energy -= energyExtracted;
        return energyExtracted;
    }

    @Override
    public int getEnergyStored() {
        return energy;
    }

    @Override
    public int getMaxEnergyStored() {
        return capacity;
    }

    @Override
    public boolean canExtract() {
        return this.maxExtract > 0;
    }

    @Override
    public boolean canReceive() {
        return this.maxReceive > 0;
    }

    @Override
    public NBTTagCompound serializeNBT() {
        NBTTagCompound tag = new NBTTagCompound();
        tag.setInteger("EnergyStored", energy);
        return tag;
    }

    @Override
    public void deserializeNBT(NBTTagCompound nbt) {
        energy = nbt.getInteger("EnergyStored");
    }
}
