package com.github.teamfossilsarcheology.fossil.forge.capabilities.mammal;

import com.github.teamfossilsarcheology.fossil.entity.prehistoric.base.EntityInfo;
import net.minecraft.nbt.CompoundTag;

public class MammalCap implements IMammalCap {
    private int embryoProgress;
    private EntityInfo embryo;

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag tag = new CompoundTag();
        tag.putInt("embryoProgress", embryoProgress);
        if (embryo != null) {
            tag.putString("embryo", embryo.name());
        }
        return tag;
    }

    @Override
    public void deserializeNBT(CompoundTag tag) {
        setEmbryoProgress(tag.getInt("embryoProgress"));
        try {
            setEmbryo(EntityInfo.fromNbt(tag.getString("embryo")));
        } catch (IllegalArgumentException e) {
            setEmbryo(null);
        }
    }

    @Override
    public int getEmbryoProgress() {
        return embryoProgress;
    }

    @Override
    public void setEmbryoProgress(int progress) {
        this.embryoProgress = progress;
    }

    @Override
    public EntityInfo getEmbryo() {
        return embryo;
    }

    @Override
    public void setEmbryo(EntityInfo embryo) {
        this.embryo = embryo;
    }
}
