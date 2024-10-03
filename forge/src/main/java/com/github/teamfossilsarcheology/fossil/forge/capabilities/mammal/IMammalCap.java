package com.github.teamfossilsarcheology.fossil.forge.capabilities.mammal;

import com.github.teamfossilsarcheology.fossil.entity.prehistoric.base.EntityInfo;
import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.util.INBTSerializable;
import org.jetbrains.annotations.Nullable;

public interface IMammalCap extends INBTSerializable<CompoundTag> {
    int getEmbryoProgress();

    void setEmbryoProgress(int progress);

    EntityInfo getEmbryo();

    void setEmbryo(@Nullable EntityInfo embryo);
}
