package com.fossil.fossil.forge.capabilities.mammal;

import com.fossil.fossil.entity.prehistoric.base.PrehistoricEntityType;
import net.minecraft.nbt.CompoundTag;

public class MammalCap implements IMammalCap {
    private int embryoProgress;
    private PrehistoricEntityType embryo;

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
            setEmbryo(PrehistoricEntityType.valueOf(tag.getString("embryo")));
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
    public PrehistoricEntityType getEmbryo() {
        return embryo;
    }

    @Override
    public void setEmbryo(PrehistoricEntityType embryo) {
        this.embryo = embryo;
    }
}
