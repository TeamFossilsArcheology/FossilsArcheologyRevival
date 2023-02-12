package com.fossil.fossil.item;

import com.fossil.fossil.entity.prehistoric.base.PrehistoricEntityType;
import net.minecraft.world.item.Item;

public class DNAItem extends Item {
    private final PrehistoricEntityType type;
    public DNAItem(PrehistoricEntityType type) {
        super(new Properties().tab(ModTabs.FAITEMTAB));
        this.type = type;
    }
}
