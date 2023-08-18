package com.fossil.fossil.entity.ai.navigation;

import net.minecraft.world.entity.Mob;
import net.minecraft.world.level.PathNavigationRegion;
import net.minecraft.world.level.pathfinder.SwimNodeEvaluator;

public class LargeSwimNodeEvaluator extends SwimNodeEvaluator {
    public LargeSwimNodeEvaluator(boolean allowBreaching) {
        super(allowBreaching);
    }

    @Override
    public void prepare(PathNavigationRegion level, Mob mob) {
        super.prepare(level, mob);
        this.entityHeight = 1;
    }
}
