package com.fossil.fossil.entity.prehistoric.base;

import net.minecraft.nbt.CompoundTag;

public abstract class AISystem {
    protected final Prehistoric mob;

    protected AISystem(Prehistoric mob) {
        this.mob = mob;
    }

    public abstract void serverTick();

    public void clientTick() {

    }

    public abstract void saveAdditional(CompoundTag tag);

    public abstract void load(CompoundTag tag);
}
