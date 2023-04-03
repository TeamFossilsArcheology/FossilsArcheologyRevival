package com.fossil.fossil.util;

import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;

import java.util.Random;

public enum Gender implements DinopediaInfo {
    MALE,
    FEMALE;
    private final TranslatableComponent name = new TranslatableComponent("pedia.fossil.gender." + name().toLowerCase());
    private final TranslatableComponent description = new TranslatableComponent("pedia.fossil.gender.desc." + name().toLowerCase());

    public static Gender random(Random random) {
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
