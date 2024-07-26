package com.fossil.fossil.entity.ai;

import com.fossil.fossil.entity.prehistoric.base.Prehistoric;
import com.fossil.fossil.entity.prehistoric.base.PrehistoricEntityInfoAI;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;

public class DinoHurtByTargetGoal extends HurtByTargetGoal {
    public DinoHurtByTargetGoal(Prehistoric dino) {
        super(dino);
    }

    @Override
    public boolean canUse() {
        if (((Prehistoric) mob).aiResponseType() == PrehistoricEntityInfoAI.Response.SCARED) {
            return false;
        }
        return super.canUse();
    }
}
