package com.github.teamfossilsarcheology.fossil.entity.prehistoric.base;

import net.minecraft.world.entity.Entity;

public interface SwimmingAnimal {
    boolean isAmphibious();

    default boolean canBreatheOnLand() {
        return isAmphibious();
    }

    boolean canSwim();

    double swimSpeed();

    int timeInWater();

    int timeOnLand();

    default boolean canHuntMobsOnLand() {
        return isAmphibious();
    }

    default boolean isDoingGrabAttack() {
        return false;
    }

    default void startGrabAttack(Entity target) {}

    default void stopGrabAttack(Entity target) {}
}
