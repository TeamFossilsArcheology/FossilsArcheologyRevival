package com.github.teamfossilsarcheology.fossil.forge.compat.jade;

import com.github.teamfossilsarcheology.fossil.block.entity.FeederBlockEntity;
import mcp.mobius.waila.api.BlockAccessor;
import mcp.mobius.waila.api.IComponentProvider;
import mcp.mobius.waila.api.IServerDataProvider;
import mcp.mobius.waila.api.ITooltip;
import mcp.mobius.waila.api.config.IPluginConfig;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;

public enum FeederStatusProvider implements IComponentProvider, IServerDataProvider<BlockEntity> {
    INSTANCE;

    @Override
    public void appendTooltip(ITooltip tooltip, BlockAccessor accessor, IPluginConfig iPluginConfig) {
        if (accessor.getServerData().contains(FeederBlockEntity.MEAT)) {
            tooltip.add(new TranslatableComponent("fossil.jade.meat", accessor.getServerData().getInt(FeederBlockEntity.MEAT)));
        }
        if (accessor.getServerData().contains(FeederBlockEntity.PLANT)) {
            tooltip.add(new TranslatableComponent("fossil.jade.plant", accessor.getServerData().getInt(FeederBlockEntity.PLANT)));
        }
    }

    @Override
    public void appendServerData(CompoundTag data, ServerPlayer serverPlayer, Level level, BlockEntity blockEntity, boolean b) {
        FeederBlockEntity feeder = (FeederBlockEntity) blockEntity;
        data.putInt(FeederBlockEntity.MEAT, feeder.getMeat());
        data.putInt(FeederBlockEntity.PLANT, feeder.getPlant());
    }
}
