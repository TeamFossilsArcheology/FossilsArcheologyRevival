package com.fossil.fossil.entity.ai.navigation;

import com.fossil.fossil.entity.prehistoric.base.Prehistoric;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.ai.navigation.GroundPathNavigation;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.pathfinder.Path;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.level.pathfinder.PathFinder;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

/*
 * Parts of the code are based off of "Mowzie's Mobs"
 * found at https://www.curseforge.com/minecraft/mc-mods/mowzies-mobs
 * with source code at https://github.com/BobMowzie/MowziesMobs/blob/master/src/main/java/com/bobmowzie/mowziesmobs/server/ai/MMPathNavigateGround.java
 */
public class PrehistoricPathNavigation extends GroundPathNavigation {
    @Nullable
    private BlockPos pathToPosition;

    public PrehistoricPathNavigation(Prehistoric prehistoric, Level level) {
        super(prehistoric, level);
    }

    @Override
    protected @NotNull PathFinder createPathFinder(int maxVisitedNodes) {
        nodeEvaluator = new PrehistoricNodeEvaluator();
        return new PrehistoricPathFinder(nodeEvaluator, maxVisitedNodes);
    }

    @Override
    protected void followThePath() {
        Path path = Objects.requireNonNull(this.path);
        Vec3 entityPos = getTempMobPos();
        int pathLength = path.getNodeCount();
        for (int i = path.getNextNodeIndex(); i < path.getNodeCount(); i++) {
            if (path.getNode(i).y != Math.floor(entityPos.y)) {
                pathLength = i;
                break;
            }
        }
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

    @Override
    public Path createPath(BlockPos blockPos, int i) {
        pathToPosition = blockPos;
        return super.createPath(blockPos, i);
    }

    @Override
    public Path createPath(Entity entity, int i) {
        pathToPosition = entity.blockPosition();
        return super.createPath(entity, i);
    }

    @Override
    public boolean moveTo(Entity entity, double d) {
        Path path = createPath(entity, 0);
        if (path != null) {
            return moveTo(path, d);
        }
        pathToPosition = entity.blockPosition();
        speedModifier = d;
        return true;
    }

    @Override
    public void tick() {
        super.tick();
        if (isDone()) {
            //Directly move to end if close enough or above and close enough horizontally
            if (pathToPosition != null) {
                if (pathToPosition.closerToCenterThan(mob.position(), mob.getBbWidth()) || mob.getY() > pathToPosition.getY() && new BlockPos(pathToPosition.getX(), mob.getY(), pathToPosition.getZ()).closerToCenterThan(mob.position(), mob.getBbWidth())) {
                    pathToPosition = null;
                } else {
                    mob.getMoveControl().setWantedPosition(pathToPosition.getX(), pathToPosition.getY(), pathToPosition.getZ(), speedModifier);
                }
            }
            return;
        }
        if (getTargetPos() != null)
            mob.getLookControl().setLookAt(getTargetPos().getX(), getTargetPos().getY(), getTargetPos().getZ());
    }

    private boolean tryShortcut(Path path, Vec3 entityPos, int pathLength, Vec3 base, Vec3 max) {
        for (int i = pathLength - 1; i > path.getNextNodeIndex(); i--) {
            final Vec3 vec = path.getEntityPosAtNode(mob, i).subtract(entityPos);
            if (Util.isNoCollisionOnPath(vec, base, max, PathComputationType.LAND, mob, nodeEvaluator)) {
                path.setNextNodeIndex(i);
                return true;
            }
        }
        return false;
    }
}
