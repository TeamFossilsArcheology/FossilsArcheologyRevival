package com.fossil.fossil.util;

import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;

public enum Diet implements DinopediaInfo {
    CARNIVORE(3),
    HERBIVORE(0),
    OMNIVORE(1),
    PISCIVORE(1),
    CARNIVORE_EGG(2),
    INSECTIVORE(0),
    PISCI_CARNIVORE(3),
    NONE(0);
    private final TranslatableComponent name = new TranslatableComponent("pedia.fossil.diet." + name().toLowerCase());
    private final TranslatableComponent description = new TranslatableComponent("pedia.fossil.diet.desc." + name().toLowerCase());

    private final int fearIndex;

    Diet(int fearIndex) {
        this.fearIndex = fearIndex;
    }

    public int getFearIndex() {
        return this.fearIndex;
    }

    @Override
    public Component getName() {
        return name;
    }

    @Override
    public Component getDescription() {
        return description;
    }

}
