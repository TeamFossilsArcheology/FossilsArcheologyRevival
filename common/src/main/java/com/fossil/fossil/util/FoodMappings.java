package com.fossil.fossil.util;


import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;

public class FoodMappings {

    public static int getBlockFoodAmount(Block block, Diet diet) {
        return 35;
    }

    public static int getItemFoodAmount(ItemStack itemstack, Diet diet) {
        return (diet == Diet.CARNIVORE_EGG && itemstack.is(Items.PORKCHOP) ? 35 : (diet == Diet.HERBIVORE && itemstack.is(Items.APPLE) ? 35 : 0));
    }
}
