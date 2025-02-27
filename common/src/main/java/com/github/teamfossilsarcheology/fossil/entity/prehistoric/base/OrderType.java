package com.github.teamfossilsarcheology.fossil.entity.prehistoric.base;

import com.github.teamfossilsarcheology.fossil.util.DinopediaInfo;
import net.minecraft.network.chat.Component;

import java.util.Locale;

public enum OrderType implements DinopediaInfo {
    STAY, FOLLOW, WANDER;
    private final Component name = Component.translatable("pedia.fossil.order." + name().toLowerCase(Locale.ROOT));
    private final Component description = Component.translatable("pedia.fossil.order." + name().toLowerCase(Locale.ROOT) + ".desc");

    @Override
    public Component getName() {
        return name;
    }

    @Override
    public Component getDescription() {
        return description;
    }
}
