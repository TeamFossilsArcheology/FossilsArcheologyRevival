package com.fossil.fossil.entity.ai;

import com.fossil.fossil.entity.prehistoric.base.OrderType;
import com.fossil.fossil.entity.prehistoric.base.Prehistoric;
import net.minecraft.world.entity.ai.goal.Goal;

import java.util.EnumSet;

public class DinoStayGoal extends Goal {
    private final Prehistoric dino;

    public DinoStayGoal(Prehistoric dino) {
        this.dino = dino;
        setFlags(EnumSet.of(Flag.MOVE));
    }

    @Override
    public boolean canUse() {
        return dino.getCurrentOrder() == OrderType.STAY;
    }
}
