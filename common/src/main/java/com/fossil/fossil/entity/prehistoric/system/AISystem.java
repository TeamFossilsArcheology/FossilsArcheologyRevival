package com.fossil.fossil.entity.prehistoric.system;

import com.fossil.fossil.entity.prehistoric.base.Prehistoric;
import net.minecraft.nbt.CompoundTag;

/**
 * Generic container for some entity logic with the goal of reducing the bloat in {@link Prehistoric}
 */
public abstract class AISystem {
    protected final Prehistoric mob;

    protected AISystem(Prehistoric mob) {
        this.mob = mob;
    }

    public abstract void serverTick();

    public void clientTick() {

    }

    /**
     * Saves this system's properties to a {@link CompoundTag}
     */
    public abstract void saveAdditional(CompoundTag tag);

    /**
     * Reads this system's properties from a {@link CompoundTag}
     */
    public abstract void load(CompoundTag tag);
}
