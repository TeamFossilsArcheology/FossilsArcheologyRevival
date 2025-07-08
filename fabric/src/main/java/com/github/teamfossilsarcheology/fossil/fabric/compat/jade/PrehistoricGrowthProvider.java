package com.github.teamfossilsarcheology.fossil.fabric.compat.jade;

import com.github.teamfossilsarcheology.fossil.FossilMod;
import com.github.teamfossilsarcheology.fossil.entity.prehistoric.base.Prehistoric;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import snownee.jade.api.EntityAccessor;
import snownee.jade.api.IServerDataProvider;

public enum PrehistoricGrowthProvider implements IServerDataProvider<EntityAccessor> {
    INSTANCE;
    private static final ResourceLocation ID = FossilMod.location("prehistoric_growth");

    @Override
    public void appendServerData(CompoundTag tag, EntityAccessor entityAccessor) {
        Prehistoric prehistoric = (Prehistoric) entityAccessor.getEntity();
        int time = prehistoric.data().adultAgeInTicks() - prehistoric.getAge();
        if (time > 0) {
            tag.putInt("GrowingTime", time);
        }
    }

    @Override
    public ResourceLocation getUid() {
        return ID;
    }
}
