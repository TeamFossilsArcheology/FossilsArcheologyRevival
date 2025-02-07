package com.github.teamfossilsarcheology.fossil.entity.prehistoric.base;

import com.github.teamfossilsarcheology.fossil.util.DinopediaInfo;
import net.minecraft.network.chat.TranslatableComponent;

import java.util.Locale;

public enum OrderType implements DinopediaInfo {
    STAY, FOLLOW, WANDER;
    private final TranslatableComponent name = new TranslatableComponent("pedia.fossil.order." + name().toLowerCase(Locale.ROOT));
    private final TranslatableComponent description = new TranslatableComponent("pedia.fossil.order." + name().toLowerCase(Locale.ROOT) + ".desc");

    @Override
    public TranslatableComponent getName() {
        return name;
    }

    @Override
    public TranslatableComponent getDescription() {
        return description;
    }
}
