package com.fossil.fossil.entity.ai;

import com.fossil.fossil.entity.prehistoric.base.Prehistoric;
import com.fossil.fossil.entity.prehistoric.base.PrehistoricEntityTypeAI;
import net.minecraft.world.entity.ai.goal.target.OwnerHurtTargetGoal;

public class DinoOwnerHurtTargetGoal extends OwnerHurtTargetGoal {
    public DinoOwnerHurtTargetGoal(Prehistoric dino) {
        super(dino);
    }

    @Override
    public boolean canUse() {
        if (mob.isBaby() || ((Prehistoric) mob).aiResponseType() == PrehistoricEntityTypeAI.Response.SCARED) {
            return false;
        }
        return super.canUse();
    }
}
