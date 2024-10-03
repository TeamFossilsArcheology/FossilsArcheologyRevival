package com.github.teamfossilsarcheology.fossil.forge.capabilities.player;

import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.util.INBTSerializable;

public interface IFirstHatchCap extends INBTSerializable<CompoundTag> {
    boolean hasHatchedDinosaur();

    void setHatchedDinosaur(boolean hatched);
}
