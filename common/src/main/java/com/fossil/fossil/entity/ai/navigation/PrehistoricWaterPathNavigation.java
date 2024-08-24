package com.fossil.fossil.entity.ai.navigation;

import com.fossil.fossil.entity.prehistoric.base.PrehistoricSwimming;
import net.minecraft.world.entity.ai.navigation.WaterBoundPathNavigation;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.pathfinder.Path;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.level.pathfinder.PathFinder;
import net.minecraft.world.level.pathfinder.SwimNodeEvaluator;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

/*
 * Parts of the code are based off of "Mowzie's Mobs"
 * found at https://www.curseforge.com/minecraft/mc-mods/mowzies-mobs
 * with source code at https://github.com/BobMowzie/MowziesMobs/blob/master/src/main/java/com/bobmowzie/mowziesmobs/server/ai/MMPathNavigateGround.java
 */
public class PrehistoricWaterPathNavigation extends WaterBoundPathNavigation {
    public PrehistoricWaterPathNavigation(PrehistoricSwimming mob, Level level) {
        super(mob, level);
    }

    @Override
    protected @NotNull PathFinder createPathFinder(int maxVisitedNodes) {
        nodeEvaluator = new SwimNodeEvaluator(false);
        return new PrehistoricPathFinder(nodeEvaluator, maxVisitedNodes);
    }

    @Override
    protected void followThePath() {
        Path path = Objects.requireNonNull(this.path);
        Vec3 entityPos = getTempMobPos();
        int pathLength = path.getNodeCount();
        final Vec3 base = entityPos.add(-mob.getBbWidth() * 0.5F, 0, -mob.getBbWidth() * 0.5F);
        final Vec3 max = base.add(mob.getBbWidth(), mob.getBbHeight(), mob.getBbWidth());
        if (!tryShortcut(path, new Vec3(mob.getX(), mob.getY(), mob.getZ()), pathLength, base, max)) {
            if (Util.isAt(mob, path, 0.5F) || Util.atElevationChange(mob, path) && Util.isAt(mob, path, mob.getBbWidth() * 0.5F)) {
                mob.getLookControl().setLookAt(path.getNextEntityPos(mob));
                path.setNextNodeIndex(path.getNextNodeIndex() + 1);
            }
        }
        doStuckDetection(entityPos);
    }

    private boolean tryShortcut(Path path, Vec3 entityPos, int pathLength, Vec3 base, Vec3 max) {
        for (int i = pathLength; --i > path.getNextNodeIndex();) {
            final Vec3 vec = path.getEntityPosAtNode(mob, i).subtract(entityPos);
            if (Util.isNoCollisionOnPath(vec, base, max, PathComputationType.WATER, mob, nodeEvaluator)) {
                path.setNextNodeIndex(i);
                return true;
            }
        }
        return false;
    }
}
