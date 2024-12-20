package com.github.teamfossilsarcheology.fossil.entity.ai.control;

import com.github.teamfossilsarcheology.fossil.entity.prehistoric.base.Prehistoric;
import com.github.teamfossilsarcheology.fossil.entity.prehistoric.base.PrehistoricEntityInfoAI;
import net.minecraft.world.entity.ai.control.LookControl;

public class PrehistoricLookControl extends LookControl {
    private final Prehistoric mob;

    public PrehistoricLookControl(Prehistoric mob) {
        super(mob);
        this.mob = mob;
    }

    @Override
    public void tick() {
        if (mob.aiClimbType() != PrehistoricEntityInfoAI.Climbing.ARTHROPOD || !mob.isClimbing()) {
            super.tick();
        }
    }
}
