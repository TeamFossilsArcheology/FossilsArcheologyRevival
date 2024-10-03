package com.github.teamfossilsarcheology.fossil.item;

import com.github.teamfossilsarcheology.fossil.Fossil;
import com.github.teamfossilsarcheology.fossil.entity.prehistoric.base.EntityInfo;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

import static com.github.teamfossilsarcheology.fossil.entity.prehistoric.base.PrehistoricEntityInfo.NAUTILUS;

public class MeatItem extends PrehistoricEntityItem {
    private Component nautilusName;

    public MeatItem(EntityInfo info, boolean cooked) {
        this(info, cooked, cooked ? 0.8f : 0.3f);
    }

    public MeatItem(EntityInfo info, boolean cooked, float saturation) {
        super(new Item.Properties().food(new FoodProperties.Builder().nutrition(cooked ? 8 : 3)
                .saturationMod(saturation).build()), info, cooked ? "cooked_meat" : "meat");
        if (info == NAUTILUS && cooked) {
            nautilusName = new TranslatableComponent("item." + Fossil.MOD_ID + ".cooked_nautilus");
        }
    }

    @Override
    public @NotNull Component getName(ItemStack stack) {
        return nautilusName != null ? nautilusName : super.getName(stack);
    }
}
