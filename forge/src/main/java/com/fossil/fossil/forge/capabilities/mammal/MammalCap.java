package com.fossil.fossil.forge.capabilities.mammal;

import com.fossil.fossil.entity.prehistoric.base.PrehistoricEntityType;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class MammalCap implements IMammalCap {
    private int embryoProgress;
    private PrehistoricEntityType embryo;

    @NotNull
    @Override
    public <T> LazyOptional<T> getCapability(@NotNull Capability<T> capability, @Nullable Direction arg) {
        return null;
    }

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
        this.embryoProgress=progress;
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
