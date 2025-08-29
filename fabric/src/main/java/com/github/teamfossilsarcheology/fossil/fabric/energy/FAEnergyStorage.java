package com.github.teamfossilsarcheology.fossil.fabric.energy;

import com.github.teamfossilsarcheology.fossil.block.entity.CommonEnergyStorage;
import net.minecraft.nbt.LongTag;
import net.minecraft.nbt.Tag;
import team.reborn.energy.api.base.SimpleEnergyStorage;

public class FAEnergyStorage extends SimpleEnergyStorage implements CommonEnergyStorage {
    public FAEnergyStorage(long capacity, long maxInsert, long maxExtract) {
        super(capacity, maxInsert, maxExtract);
    }

    @Override
    public int getEnergy() {
        return (int) amount;
    }

    @Override
    public void extractEnergy(int maxExtract) {
        amount -= maxExtract;
    }

    @Override
    public void load(Tag tag) {
        if (!(tag instanceof LongTag longTag))
            throw new IllegalArgumentException("Can not deserialize to an instance that isn't the default implementation");
        amount = longTag.getAsInt();
    }

    @Override
    public Tag save() {
        return LongTag.valueOf(amount);
    }
}
