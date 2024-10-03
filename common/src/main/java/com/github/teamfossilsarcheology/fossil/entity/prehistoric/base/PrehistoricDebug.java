package com.github.teamfossilsarcheology.fossil.entity.prehistoric.base;

import net.minecraft.nbt.CompoundTag;

public interface PrehistoricDebug {
    CompoundTag getDebugTag();

    /**
     * Disables specific parts of the AI e.g. goals
     */
    void disableCustomAI(byte type, boolean disableAI);
}
