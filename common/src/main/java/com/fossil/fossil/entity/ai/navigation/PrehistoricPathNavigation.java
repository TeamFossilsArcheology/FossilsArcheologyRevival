package com.fossil.fossil.entity.ai.navigation;

import com.fossil.fossil.entity.prehistoric.base.Prehistoric;
import net.minecraft.world.entity.ai.navigation.GroundPathNavigation;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.pathfinder.PathFinder;

public class PrehistoricPathNavigation extends GroundPathNavigation {
    public PrehistoricPathNavigation(Prehistoric prehistoric, Level level) {
        super(prehistoric, level);
    }

    @Override
    protected PathFinder createPathFinder(int maxVisitedNodes) {
        nodeEvaluator = new CenteredNodeEvaluator();
        return new PathFinder(this.nodeEvaluator, maxVisitedNodes);
    }
}
