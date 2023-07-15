package com.fossil.fossil.block.entity;

import net.minecraft.world.Container;

public interface CustomBlockEntity extends Container {

    /**
     * @return the amount of ingredient slots this container has
     */
    int getIngredientsSize();
}
