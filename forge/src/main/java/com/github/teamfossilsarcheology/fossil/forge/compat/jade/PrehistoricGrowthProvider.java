package com.github.teamfossilsarcheology.fossil.forge.compat.jade;

import com.github.teamfossilsarcheology.fossil.entity.prehistoric.base.Prehistoric;
import mcp.mobius.waila.api.IServerDataProvider;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;

public enum PrehistoricGrowthProvider implements IServerDataProvider<Entity> {
    INSTANCE;

    @Override
    public void appendServerData(CompoundTag tag, ServerPlayer serverPlayer, Level level, Entity entity, boolean b) {
        Prehistoric prehistoric = (Prehistoric) entity;
        int time = prehistoric.data().adultAgeInTicks() - prehistoric.getAge();
        if (time > 0) {
            tag.putInt("GrowingTime", time);
        }
    }
}
