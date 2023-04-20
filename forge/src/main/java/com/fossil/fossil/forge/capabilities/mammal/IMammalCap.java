package com.fossil.fossil.forge.capabilities.mammal;

import com.fossil.fossil.entity.prehistoric.base.PrehistoricEntityType;
import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import org.jetbrains.annotations.Nullable;

public interface IMammalCap extends ICapabilitySerializable<CompoundTag> {
    int getEmbryoProgress();

    void setEmbryoProgress(int progress);

    PrehistoricEntityType getEmbryo();

    void setEmbryo(@Nullable PrehistoricEntityType type);
}
