package com.github.teamfossilsarcheology.fossil.entity.ai.navigation;

import com.github.teamfossilsarcheology.fossil.entity.prehistoric.base.Prehistoric;
import com.github.teamfossilsarcheology.fossil.entity.prehistoric.base.SwimmingAnimal;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.ai.navigation.WaterBoundPathNavigation;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.pathfinder.Path;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.level.pathfinder.PathFinder;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

/*
 * Parts of the code are based off of "Mowzie's Mobs"
 * found at https://www.curseforge.com/minecraft/mc-mods/mowzies-mobs
 * with source code at https://github.com/BobMowzie/MowziesMobs/blob/master/src/main/java/com/bobmowzie/mowziesmobs/server/ai/MMPathNavigateGround.java
 */
public class AmphibiousPathNavigation<T extends Prehistoric & SwimmingAnimal> extends WaterBoundPathNavigation {
    public AmphibiousPathNavigation(T mob, Level level) {
        super(mob, level);
    }

    @Override
    protected @NotNull PathFinder createPathFinder(int maxVisitedNodes) {
        nodeEvaluator = new PrehistoricAmphibiousNodeEvaluator();
        return new WaterPathFinder(nodeEvaluator, maxVisitedNodes, mob);
    }

    @Override
    protected boolean canUpdatePath() {
        return ((SwimmingAnimal) mob).isAmphibious() || super.canUpdatePath();
    }

    @Override
    protected void followThePath() {
        Path path = Objects.requireNonNull(this.path);
        Vec3 entityPos = getTempMobPos();
        int pathLength = path.getNodeCount();
        final Vec3 base = entityPos.add(-mob.getBbWidth() * 0.5F, 0, -mob.getBbWidth() * 0.5F);
        final Vec3 max = base.add(mob.getBbWidth(), mob.getBbHeight(), mob.getBbWidth());
        if (!tryShortcut(path, new Vec3(mob.getX(), mob.getY(), mob.getZ()), pathLength, base, max)) {
            if (mob.isInWater() || NavUtil.isAt(mob, path, Math.min(mob.getBbWidth() * 0.75f, 0.5f), 0.5f)) {
                //TODO: Maybe instead check for ledge (0=(?,0,?), 1=(?,1,?), 2=(?,1,?)
                if (NavUtil.isAt(mob, path, Math.min(mob.getBbWidth() * 0.75f, 0.5f), 1) || NavUtil.atElevationChange(mob, path) && NavUtil.isAt(mob, path, mob.getBbWidth() * 0.75F, 1)) {
                    path.advance();
                    if (!path.isDone()) {
                        mob.getLookControl().setLookAt(path.getNextEntityPos(mob));
                    }
                }
            }
        }
        doStuckDetection(entityPos);
    }

    private boolean tryShortcut(Path path, Vec3 entityPos, int pathLength, Vec3 base, Vec3 max) {
        for (int i = pathLength; --i > path.getNextNodeIndex(); ) {
            final Vec3 vec = path.getEntityPosAtNode(mob, i).subtract(entityPos);
            if (mob.isInWater()) {
                if (NavUtil.isNoCollisionOnPath(vec, base, max, PathComputationType.WATER, mob, nodeEvaluator)) {
                    path.setNextNodeIndex(i);
                    return true;
                }
            } else {
                if (NavUtil.isNoCollisionOnPath(vec, base, max, PathComputationType.LAND, mob, nodeEvaluator)) {
                    path.setNextNodeIndex(i);
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public boolean isStableDestination(BlockPos pos) {
        if (((SwimmingAnimal) mob).isAmphibious()) {
            return !level.isEmptyBlock(pos.below());
        }
        return super.isStableDestination(pos);
    }
}
