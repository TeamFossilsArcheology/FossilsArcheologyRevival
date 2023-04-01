package com.fossil.fossil.entity.ai;

import com.fossil.fossil.entity.prehistoric.base.Prehistoric;
import com.fossil.fossil.entity.prehistoric.base.PrehistoricEntityTypeAI;
import net.minecraft.world.entity.ai.goal.PanicGoal;

//TODO: Should this be added to all dinos with babies?
public class DinoPanicGoal extends PanicGoal {

    public DinoPanicGoal(Prehistoric dino, double speedModifier) {
        super(dino, speedModifier);
    }

    @Override
    public boolean canUse() {
        if (((Prehistoric) mob).aiResponseType() != PrehistoricEntityTypeAI.Response.SCARED) {
            return false;
        }
        return super.canUse();
    }
}
