package com.fossil.fossil.entity.prehistoric.base;

import net.minecraft.nbt.CompoundTag;

public interface PrehistoricDebug {
    CompoundTag getDebugTag();

    void disableCustomAI(byte type, boolean disableAI);
}
