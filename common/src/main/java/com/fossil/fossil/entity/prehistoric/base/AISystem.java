package com.fossil.fossil.entity.prehistoric.base;

import net.minecraft.nbt.CompoundTag;

public interface AISystem {

    void serverTick();

    void saveAdditional(CompoundTag arg);

    void load(CompoundTag arg);
}
