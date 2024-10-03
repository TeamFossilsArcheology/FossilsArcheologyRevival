package com.github.teamfossilsarcheology.fossil.entity.ai;

import com.github.teamfossilsarcheology.fossil.entity.prehistoric.base.Prehistoric;
import com.github.teamfossilsarcheology.fossil.entity.prehistoric.base.PrehistoricEntityInfoAI;
import net.minecraft.world.entity.ai.goal.target.OwnerHurtByTargetGoal;

public class DinoOwnerHurtByTargetGoal extends OwnerHurtByTargetGoal {
    public DinoOwnerHurtByTargetGoal(Prehistoric dino) {
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
