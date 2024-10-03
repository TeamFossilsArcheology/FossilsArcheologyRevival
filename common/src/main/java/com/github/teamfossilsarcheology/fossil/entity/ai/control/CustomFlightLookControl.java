package com.github.teamfossilsarcheology.fossil.entity.ai.control;

import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.control.LookControl;
import net.minecraft.world.entity.animal.FlyingAnimal;

public class CustomFlightLookControl<T extends Mob & FlyingAnimal> extends LookControl {
    private final T flying;

    public CustomFlightLookControl(T flying) {
        super(flying);
        this.flying = flying;
    }

    @Override
    public void tick() {
        if (!flying.isFlying()) {
            super.tick();
        }
    }

    @Override
    protected boolean resetXRotOnTick() {
        return !flying.isFlying();
    }
}
