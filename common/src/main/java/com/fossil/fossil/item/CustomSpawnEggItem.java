package com.fossil.fossil.item;

import com.fossil.fossil.Fossil;
import com.fossil.fossil.entity.prehistoric.base.PrehistoricEntityInfo;
import dev.architectury.core.item.ArchitecturySpawnEggItem;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public class CustomSpawnEggItem extends ArchitecturySpawnEggItem {
    private final Component name;

    public CustomSpawnEggItem(PrehistoricEntityInfo info) {
        super(info.entitySupplier, info.backgroundEggColor, info.highlightEggColor, new Item.Properties().tab(ModTabs.FAITEMTAB));
        this.name = new TranslatableComponent("item." + Fossil.MOD_ID + ".spawn_egg_generic", info.displayName().get());
    }

    @Override
    public @NotNull Component getName(ItemStack stack) {
        return name;
    }
}
