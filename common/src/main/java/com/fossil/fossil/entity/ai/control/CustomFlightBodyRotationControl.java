package com.fossil.fossil.entity.ai.control;

import com.fossil.fossil.entity.prehistoric.base.PrehistoricFlying;
import net.minecraft.world.entity.ai.control.BodyRotationControl;

public class CustomFlightBodyRotationControl extends BodyRotationControl {
    private final PrehistoricFlying flying;

    public CustomFlightBodyRotationControl(PrehistoricFlying flying) {
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
