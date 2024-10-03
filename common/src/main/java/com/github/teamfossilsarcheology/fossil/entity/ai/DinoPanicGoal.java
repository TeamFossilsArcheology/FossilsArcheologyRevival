package com.github.teamfossilsarcheology.fossil.entity.ai;

import com.github.teamfossilsarcheology.fossil.entity.prehistoric.base.Prehistoric;
import com.github.teamfossilsarcheology.fossil.entity.prehistoric.base.PrehistoricEntityInfoAI;
import net.minecraft.world.entity.ai.goal.PanicGoal;

public class DinoPanicGoal extends PanicGoal {

    public DinoPanicGoal(Prehistoric dino, double speedModifier) {
        super(dino, speedModifier);
    }

    @Override
    public boolean canUse() {
        if (((Prehistoric) mob).aiResponseType() != PrehistoricEntityInfoAI.Response.SCARED) {
            return false;
        }
        return super.canUse();
    }

    @Override
    public void start() {
        super.start();
        ((Prehistoric)mob).sleepSystem.setSleepDisabled(true);
        ((Prehistoric)mob).sitSystem.setSittingDisabled(true);
    }

    @Override
    public void stop() {
        super.stop();
        ((Prehistoric)mob).sleepSystem.setSleepDisabled(false);
        ((Prehistoric)mob).sitSystem.setSittingDisabled(false);
    }
}
