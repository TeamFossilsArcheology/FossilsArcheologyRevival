package com.github.teamfossilsarcheology.fossil.fabric.compat.jade;

import com.github.teamfossilsarcheology.fossil.FossilMod;
import com.github.teamfossilsarcheology.fossil.block.entity.FeederBlockEntity;
import com.github.teamfossilsarcheology.fossil.food.FoodType;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import snownee.jade.api.BlockAccessor;
import snownee.jade.api.IBlockComponentProvider;
import snownee.jade.api.IServerDataProvider;
import snownee.jade.api.ITooltip;
import snownee.jade.api.config.IPluginConfig;

public enum FeederStatusProvider implements IBlockComponentProvider, IServerDataProvider<BlockAccessor> {
    INSTANCE;
    private static final ResourceLocation ID = FossilMod.location("feeder_status");

    @Override
    public void appendTooltip(ITooltip tooltip, BlockAccessor accessor, IPluginConfig iPluginConfig) {
        if (accessor.getServerData().contains(FoodType.MEAT.name())) {
            tooltip.add(Component.translatable("fossil.jade.meat", accessor.getServerData().getInt(FoodType.MEAT.name()))
                    .withStyle(ChatFormatting.RED));
        }
        if (accessor.getServerData().contains(FoodType.PLANT.name())) {
            tooltip.add(Component.translatable("fossil.jade.plant", accessor.getServerData().getInt(FoodType.PLANT.name()))
                    .withStyle(ChatFormatting.GREEN));
        }
        if (accessor.getServerData().contains(FoodType.FISH.name())) {
            tooltip.add(Component.translatable("fossil.jade.fish", accessor.getServerData().getInt(FoodType.FISH.name()))
                    .withStyle(ChatFormatting.BLUE));
        }
    }

    @Override
    public void appendServerData(CompoundTag data, BlockAccessor accessor) {
        FeederBlockEntity feeder = (FeederBlockEntity) accessor.getBlockEntity();
        data.putInt(FoodType.MEAT.name(), feeder.getValue(FoodType.MEAT));
        data.putInt(FoodType.PLANT.name(), feeder.getValue(FoodType.PLANT));
        data.putInt(FoodType.FISH.name(), feeder.getValue(FoodType.FISH));
    }

    @Override
    public ResourceLocation getUid() {
        return ID;
    }
}
