package com.github.teamfossilsarcheology.fossil.item;

import com.github.teamfossilsarcheology.fossil.FossilMod;
import com.github.teamfossilsarcheology.fossil.entity.prehistoric.base.PrehistoricEntityInfo;
import dev.architectury.core.item.ArchitecturySpawnEggItem;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public class CustomSpawnEggItem extends ArchitecturySpawnEggItem {
    private final Component name;

    public CustomSpawnEggItem(PrehistoricEntityInfo info) {
        super(info.entitySupplier, info.backgroundEggColor, info.highlightEggColor, new Item.Properties().arch$tab(ModTabs.FA_MOB_ITEM_TAB));
        this.name = Component.translatable("item." + FossilMod.MOD_ID + ".spawn_egg_generic", info.displayName().get());
    }

    @Override
    public @NotNull Component getName(ItemStack stack) {
        return name;
    }
}
