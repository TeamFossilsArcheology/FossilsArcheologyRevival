package com.fossil.fossil.recipe;

import net.minecraft.world.item.ItemStack;

public class WorktableRecipe {
    private final ItemStack item;
    private final ItemStack output;
    private final ItemStack fuel;

    public WorktableRecipe(ItemStack item, ItemStack output, ItemStack fuel) {
        this.item = item;
        this.output = output;
        this.fuel = fuel;
    }


    public ItemStack getItem() {
        return item;
    }

    public ItemStack getOutput() {
        return output.copy();
    }

    public ItemStack getFuel() {
        return fuel;
    }
}
