package com.fossil.fossil.item;

import com.fossil.fossil.Fossil;
import com.fossil.fossil.entity.prehistoric.base.EntityInfo;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
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
        super(properties.tab(ModTabs.FAITEMTAB));
        this.info = info;
        this.name = new TranslatableComponent("item." + Fossil.MOD_ID + "." + category, info.displayName().get());
    }

    @Override
    public @NotNull Component getName(ItemStack stack) {
        return name;
    }
}