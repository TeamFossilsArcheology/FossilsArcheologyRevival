package com.github.teamfossilsarcheology.fossil.forge.compat.jade;

import com.github.teamfossilsarcheology.fossil.FossilMod;
import com.github.teamfossilsarcheology.fossil.entity.prehistoric.base.Prehistoric;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import snownee.jade.api.EntityAccessor;
import snownee.jade.api.IServerDataProvider;

public enum PrehistoricBreedingProvider implements IServerDataProvider<EntityAccessor> {
    INSTANCE;
    private static final ResourceLocation ID = FossilMod.location("prehistoric_breeding");

    @Override
    public void appendServerData(CompoundTag tag, EntityAccessor entityAccessor) {
        int time = ((Prehistoric) entityAccessor.getEntity()).getMatingCooldown();
        tag.putInt("BreedingCD", time);
    }

    @Override
    public ResourceLocation getUid() {
        return ID;
    }
}
