package com.fossil.fossil.entity.ai.navigation;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.navigation.WaterBoundPathNavigation;
import net.minecraft.world.level.pathfinder.AmphibiousNodeEvaluator;
import net.minecraft.world.level.pathfinder.PathFinder;
import org.jetbrains.annotations.NotNull;

public class AmphibiousPathNavigation extends WaterBoundPathNavigation {
    public AmphibiousPathNavigation(Mob mob) {
        super(mob, mob.level);
    }

    @Override
    protected boolean canUpdatePath() {
        return true;
    }

    @Override
    protected @NotNull PathFinder createPathFinder(int maxVisitedNodes) {
        nodeEvaluator = new AmphibiousNodeEvaluator(true);
        return new PathFinder(nodeEvaluator, maxVisitedNodes);
    }

    @Override
    public boolean isStableDestination(BlockPos pos) {
        return !level.getBlockState(pos.below()).isAir();
    }
}
