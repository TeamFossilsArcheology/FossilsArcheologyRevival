package com.github.teamfossilsarcheology.fossil.entity.ai.navigation;

import net.minecraft.util.Mth;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.pathfinder.PathFinder;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

public class TrilobitePathNavigation extends PathNavigation {
    public TrilobitePathNavigation(Mob mob, Level level) {
        super(mob, level);
    }

    @Override
    protected @NotNull PathFinder createPathFinder(int maxVisitedNodes) {
        nodeEvaluator = new TrilobiteNodeEvaluator();
        return new PathFinder(nodeEvaluator, maxVisitedNodes);
    }

    @Override
    protected @NotNull Vec3 getTempMobPos() {
        return new Vec3(mob.getX(), Mth.floor(mob.getY() + 0.5), mob.getZ());
    }

    @Override
    protected boolean canUpdatePath() {
        return isInLiquid();
    }
}
