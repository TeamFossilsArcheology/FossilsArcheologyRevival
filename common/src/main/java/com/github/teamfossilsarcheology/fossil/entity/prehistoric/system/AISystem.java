package com.github.teamfossilsarcheology.fossil.entity.prehistoric.system;

import com.github.teamfossilsarcheology.fossil.entity.prehistoric.base.Prehistoric;
import net.minecraft.nbt.CompoundTag;

/**
 * Generic container for some entity logic with the goal of reducing the bloat in {@link Prehistoric}
 */
public abstract class AISystem {
    protected final Prehistoric mob;
    protected boolean disabled;

    protected AISystem(Prehistoric mob) {
        this.mob = mob;
    }

    public abstract void serverTick();

    public void clientTick() {

    }

    public boolean isDisabled() {
        return disabled;
    }

    public void setDisabled(boolean disabled) {
        this.disabled = disabled;
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
