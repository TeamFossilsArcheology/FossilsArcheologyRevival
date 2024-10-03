package com.github.teamfossilsarcheology.fossil.entity.prehistoric.base;

import net.minecraft.world.entity.Shearable;

public interface PrehistoricShearable extends Shearable {

    boolean isSheared();

    void setSheared(boolean sheared);
}
