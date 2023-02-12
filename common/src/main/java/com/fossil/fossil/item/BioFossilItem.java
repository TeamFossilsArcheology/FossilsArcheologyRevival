package com.fossil.fossil.item;

import net.minecraft.world.item.Item;

public class BioFossilItem extends Item {

    private final boolean isTar;

    public BioFossilItem(boolean isTar) {
        super(new Properties().tab(ModTabs.FAITEMTAB));
        this.isTar = isTar;
    }
}