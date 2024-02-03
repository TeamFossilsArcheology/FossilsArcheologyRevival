package com.fossil.fossil.item;

import com.fossil.fossil.entity.prehistoric.base.EntityInfo;
import net.minecraft.world.item.Item;

public class PrehistoricEntityItem extends Item {
    protected final EntityInfo type;

    public PrehistoricEntityItem(EntityInfo type) {
        super(new Properties().tab(ModTabs.FAITEMTAB));
        this.type = type;
    }
}