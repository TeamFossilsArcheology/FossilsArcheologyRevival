package com.github.teamfossilsarcheology.fossil.block.entity;

import net.minecraft.nbt.Tag;

public interface CommonEnergyStorage {

    int getEnergy();

    void extractEnergy(int maxExtract);

    void load(Tag tag);

    Tag save();
}
