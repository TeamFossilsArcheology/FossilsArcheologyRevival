package com.fossil.fossil.entity.prehistoric.base;

import com.fossil.fossil.util.DinopediaInfo;
import net.minecraft.network.chat.TranslatableComponent;

public enum OrderType implements DinopediaInfo {
    STAY, FOLLOW, WANDER;
    private final TranslatableComponent name = new TranslatableComponent("pedia.fossil.order." + name().toLowerCase());
    private final TranslatableComponent description = new TranslatableComponent("pedia.fossil.order.desc." + name().toLowerCase());

    @Override
    public TranslatableComponent getName() {
        return name;
    }

    @Override
    public TranslatableComponent getDescription() {
        return description;
    }
}
