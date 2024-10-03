package com.github.teamfossilsarcheology.fossil.entity.ai.control;

import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.control.BodyRotationControl;
import net.minecraft.world.entity.animal.FlyingAnimal;

public class CustomFlightBodyRotationControl<T extends Mob & FlyingAnimal> extends BodyRotationControl {
    private final T flying;

    public CustomFlightBodyRotationControl(T flying) {
        super(flying);
        this.flying = flying;
    }

    @Override
    public void clientTick() {
        if (flying.isFlying()) {
            flying.yHeadRot = flying.yBodyRot;
            flying.yBodyRot = flying.getYRot();
        } else {
            super.clientTick();
        }
    }
}
