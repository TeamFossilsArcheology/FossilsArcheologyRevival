package com.github.teamfossilsarcheology.fossil.forge.capabilities.player;

import net.minecraft.nbt.CompoundTag;

public class FirstHatchCap implements IFirstHatchCap {
    private boolean hatchedDinosaur;

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag tag = new CompoundTag();
        tag.putBoolean("HatchedDinosaur", hatchedDinosaur);
        return tag;
    }

    @Override
    public void deserializeNBT(CompoundTag tag) {
        setHatchedDinosaur(tag.getBoolean("HatchedDinosaur"));
    }

    @Override
    public boolean hasHatchedDinosaur() {
        return hatchedDinosaur;
    }

    @Override
    public void setHatchedDinosaur(boolean hatched) {
        this.hatchedDinosaur = hatched;
    }
}
