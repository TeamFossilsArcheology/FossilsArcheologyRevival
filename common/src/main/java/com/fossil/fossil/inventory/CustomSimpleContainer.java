package com.fossil.fossil.inventory;

import com.fossil.fossil.block.entity.CustomBlockEntity;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.ItemStack;

public class CustomSimpleContainer extends SimpleContainer implements CustomBlockEntity {
    private final int ingredientsSize;

    public CustomSimpleContainer(int size, int ingredientsSize) {
        super(size);
        this.ingredientsSize = ingredientsSize;
    }

    public CustomSimpleContainer(int ingredientsSize, ItemStack... itemStacks) {
        super(itemStacks);
        this.ingredientsSize = ingredientsSize;
    }

    @Override
    public int getIngredientsSize() {
        return ingredientsSize;
    }
}
