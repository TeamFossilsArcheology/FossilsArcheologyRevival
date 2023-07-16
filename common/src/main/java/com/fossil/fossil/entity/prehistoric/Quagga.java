package com.fossil.fossil.entity.prehistoric;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.animal.horse.AbstractChestedHorse;
import net.minecraft.world.level.Level;

public class Quagga extends AbstractChestedHorse {
    public static final String ANIMATIONS = "quagga.animation.json";

    public Quagga(EntityType<Quagga> entityType, Level level) {
        super(entityType, level);
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
    }
}
