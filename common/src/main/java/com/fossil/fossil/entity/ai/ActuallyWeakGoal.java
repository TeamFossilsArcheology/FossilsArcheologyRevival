package com.fossil.fossil.entity.ai;

import com.fossil.fossil.entity.prehistoric.base.Prehistoric;
import net.minecraft.world.entity.ai.goal.Goal;

import java.util.EnumSet;

/**
 * Exists just to block all other goals
 */
public class ActuallyWeakGoal extends Goal {
    private final Prehistoric dino;

    public ActuallyWeakGoal(Prehistoric dino) {
        this.dino = dino;
        setFlags(EnumSet.of(Flag.MOVE, Flag.JUMP, Flag.LOOK));
    }
    @Override
    public boolean canUse() {
        return dino.isActuallyWeak();
    }
}
