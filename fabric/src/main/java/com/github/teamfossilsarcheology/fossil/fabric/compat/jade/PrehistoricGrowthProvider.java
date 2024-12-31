package com.github.teamfossilsarcheology.fossil.fabric.compat.jade;

import com.github.teamfossilsarcheology.fossil.FossilMod;
import com.github.teamfossilsarcheology.fossil.entity.prehistoric.base.Prehistoric;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import snownee.jade.api.IServerDataProvider;

public enum PrehistoricGrowthProvider implements IServerDataProvider<Entity> {
    INSTANCE;
    private static final ResourceLocation ID = FossilMod.location("prehistoric_growth");

    @Override
    public void appendServerData(CompoundTag tag, ServerPlayer serverPlayer, Level level, Entity entity, boolean b) {
        Prehistoric prehistoric = (Prehistoric) entity;
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
