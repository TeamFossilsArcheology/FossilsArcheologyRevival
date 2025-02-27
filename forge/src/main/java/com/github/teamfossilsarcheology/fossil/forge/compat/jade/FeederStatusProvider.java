package com.github.teamfossilsarcheology.fossil.forge.compat.jade;

import com.github.teamfossilsarcheology.fossil.FossilMod;
import com.github.teamfossilsarcheology.fossil.block.entity.FeederBlockEntity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import snownee.jade.api.BlockAccessor;
import snownee.jade.api.IBlockComponentProvider;
import snownee.jade.api.IServerDataProvider;
import snownee.jade.api.ITooltip;
import snownee.jade.api.config.IPluginConfig;

public enum FeederStatusProvider implements IBlockComponentProvider, IServerDataProvider<BlockEntity> {
    INSTANCE;
    private static final ResourceLocation ID = FossilMod.location("feeder_status");

    @Override
    public void appendTooltip(ITooltip tooltip, BlockAccessor accessor, IPluginConfig iPluginConfig) {
        if (accessor.getServerData().contains(FeederBlockEntity.MEAT)) {
            tooltip.add(Component.translatable("fossil.jade.meat", accessor.getServerData().getInt(FeederBlockEntity.MEAT)));
        }
        if (accessor.getServerData().contains(FeederBlockEntity.PLANT)) {
            tooltip.add(Component.translatable("fossil.jade.plant", accessor.getServerData().getInt(FeederBlockEntity.PLANT)));
        }
    }

    @Override
    public void appendServerData(CompoundTag data, ServerPlayer serverPlayer, Level level, BlockEntity blockEntity, boolean b) {
        FeederBlockEntity feeder = (FeederBlockEntity) blockEntity;
        data.putInt(FeederBlockEntity.MEAT, feeder.getMeat());
        data.putInt(FeederBlockEntity.PLANT, feeder.getPlant());
    }

    @Override
    public ResourceLocation getUid() {
        return ID;
    }
}
