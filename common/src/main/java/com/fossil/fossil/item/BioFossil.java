package com.fossil.fossil.item;

import net.minecraft.world.item.Item;

public class BioFossil extends Item {

    private final boolean isTar;

    public BioFossil(boolean isTar) {
        super(new Properties().tab(FATabRegistry.FAITEMTAB));
        this.isTar = isTar;
    }
}