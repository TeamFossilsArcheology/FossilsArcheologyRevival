package com.fossil.fossil.entity.ai;

import com.fossil.fossil.entity.prehistoric.base.Prehistoric;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;

public class DinoLookAroundGoal extends RandomLookAroundGoal {
    private final Prehistoric prehistoric;

    public DinoLookAroundGoal(Prehistoric mob) {
        super(mob);
        this.prehistoric = mob;
    }

    @Override
    public boolean canUse() {
        if (prehistoric.isSleeping() ) { //TODO: Megalania: prehistoric instanceof EntityMegalania && this.prehistoric.getAnimation() == EntityMegalania.ANIMATION_FIGHT
            return false;
        }
        return super.canUse();
    }
}
