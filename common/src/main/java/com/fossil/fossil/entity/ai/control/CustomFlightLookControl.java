package com.fossil.fossil.entity.ai.control;

import com.fossil.fossil.entity.prehistoric.base.PrehistoricFlying;
import net.minecraft.world.entity.ai.control.LookControl;

public class CustomFlightLookControl extends LookControl {
    private final PrehistoricFlying flying;

    public CustomFlightLookControl(PrehistoricFlying flying) {
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
