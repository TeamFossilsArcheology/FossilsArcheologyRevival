package com.fossil.fossil.entity.ai;

import com.fossil.fossil.entity.prehistoric.base.Pterosaurs;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.ai.goal.Goal;

public class FindAirTargetGoal extends Goal {
    //TODO: Implement
    protected final Pterosaurs dino;
    private BlockPos shelterPos;

    public FindAirTargetGoal(Pterosaurs dino) {
        this.dino = dino;
    }

    @Override
    public boolean canUse() {
        return false;
    }
}
