package com.github.teamfossilsarcheology.fossil.item;

import com.github.teamfossilsarcheology.fossil.FossilMod;
import com.github.teamfossilsarcheology.fossil.entity.prehistoric.base.EntityInfo;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public abstract class PrehistoricEntityItem extends Item {
    protected final EntityInfo info;
    private final Component name;

    protected PrehistoricEntityItem(EntityInfo info, String category) {
        this(new Properties(), info, category);
    }

    protected PrehistoricEntityItem(Properties properties, EntityInfo info, String category) {
        super(properties.arch$tab(ModTabs.FA_MOB_ITEM_TAB));
        this.info = info;
        this.name = Component.translatable("item." + FossilMod.MOD_ID + "." + category, info.displayName().get());
    }

    public EntityInfo getInfo() {
        return info;
    }

    @Override
    public @NotNull Component getName(ItemStack stack) {
        return name;
    }
}