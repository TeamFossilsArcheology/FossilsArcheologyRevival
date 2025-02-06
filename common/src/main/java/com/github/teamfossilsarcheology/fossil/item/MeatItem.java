package com.github.teamfossilsarcheology.fossil.item;

import com.github.teamfossilsarcheology.fossil.entity.prehistoric.base.EntityInfo;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;

public class MeatItem extends PrehistoricEntityItem {

    public MeatItem(EntityInfo info, boolean cooked) {
        this(info, cooked, cooked ? 0.8f : 0.3f);
    }

    public MeatItem(EntityInfo info, boolean cooked, float saturation) {
        super(new Item.Properties().food(new FoodProperties.Builder().nutrition(cooked ? 8 : 3)
                .saturationMod(saturation).build()), info, cooked ? "cooked_meat" : "meat");
    }

    protected MeatItem(EntityInfo info, boolean cooked, float saturation, String category) {
        super(new Item.Properties().food(new FoodProperties.Builder().nutrition(cooked ? 8 : 3)
                .saturationMod(saturation).build()), info, category);
    }
}