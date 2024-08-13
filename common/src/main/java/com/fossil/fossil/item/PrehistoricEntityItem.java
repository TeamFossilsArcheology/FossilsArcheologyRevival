package com.fossil.fossil.item;

import com.fossil.fossil.entity.prehistoric.base.EntityInfo;
import net.minecraft.world.item.Item;

public abstract class PrehistoricEntityItem extends Item {
    protected final EntityInfo info;

    protected PrehistoricEntityItem(EntityInfo info) {
        super(new Properties().tab(ModTabs.FAITEMTAB));
        this.info = info;
    }

    protected PrehistoricEntityItem(Properties properties, EntityInfo info) {
        super(properties.tab(ModTabs.FAITEMTAB));
        this.info = info;
    }
}