package com.fossil.fossil.item;

import com.fossil.fossil.entity.prehistoric.base.PrehistoricEntityType;
import net.minecraft.world.item.Item;

public class PrehistoricEntityItem extends Item {
    protected final PrehistoricEntityType type;
    public PrehistoricEntityItem(PrehistoricEntityType type) {
        super(new Properties().tab(ModTabs.FAITEMTAB));
        this.type = type;
    }
}