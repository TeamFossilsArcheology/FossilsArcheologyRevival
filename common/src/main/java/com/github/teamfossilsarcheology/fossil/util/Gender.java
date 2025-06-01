package com.github.teamfossilsarcheology.fossil.util;

import net.minecraft.network.chat.Component;
import net.minecraft.util.RandomSource;

import java.util.Locale;

public enum Gender implements DinopediaInfo {
    MALE,
    FEMALE;
    private final Component name = Component.translatable("pedia.fossil.gender." + name().toLowerCase(Locale.ROOT));
    private final Component description = Component.translatable("pedia.fossil.gender." + name().toLowerCase(Locale.ROOT) + ".desc");

    public static Gender random(RandomSource random) {
        return values()[random.nextInt(2)];
    }

    public Component getName() {
        return name;
    }

    @Override
    public Component getDescription() {
        return description;
    }
}
