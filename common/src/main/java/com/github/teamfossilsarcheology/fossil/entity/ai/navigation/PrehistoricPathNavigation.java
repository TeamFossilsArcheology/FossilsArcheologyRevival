package com.github.teamfossilsarcheology.fossil.entity.ai.navigation;

import com.github.teamfossilsarcheology.fossil.entity.prehistoric.base.Prehistoric;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.ai.navigation.GroundPathNavigation;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.pathfinder.Path;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.level.pathfinder.PathFinder;
import net.minecraft.world.level.pathfinder.WalkNodeEvaluator;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

/*
 * Parts of the code are based off of "Mowzie's Mobs"
 * found at https://www.curseforge.com/minecraft/mc-mods/mowzies-mobs
 * with source code at https://github.com/BobMowzie/MowziesMobs/blob/master/src/main/java/com/bobmowzie/mowziesmobs/server/ai/MMPathNavigateGround.java
 */
public class PrehistoricPathNavigation extends GroundPathNavigation {

    public PrehistoricPathNavigation(Prehistoric prehistoric, Level level) {
        super(prehistoric, level);
    }

    @Override
    protected @NotNull PathFinder createPathFinder(int maxVisitedNodes) {
        nodeEvaluator = new PrehistoricNodeEvaluator();
        return new PrehistoricPathFinder(nodeEvaluator, maxVisitedNodes, mob);
    }

    @Override
    protected double getGroundY(Vec3 vec) {
        BlockPos blockPos = BlockPos.containing(vec);
        BlockPos entityPos = mob.blockPosition();
        if (level.getBlockState(blockPos.below()).isPathfindable(level, blockPos, PathComputationType.LAND)) {
            if (blockPos.getY() >= mob.getY() + 2) {
                return vec.y - 1;
            }
            return vec.y;
        }
        return  WalkNodeEvaluator.getFloorLevel(this.level, blockPos);
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
            if (NavUtil.isAt(mob, path, 0.5F, 1) || NavUtil.atElevationChange(mob, path) && NavUtil.isAt(mob, path, mob.getBbWidth() * 0.75F, 1)) {
                mob.getLookControl().setLookAt(path.getNextEntityPos(mob));
                path.advance();
            }
        }
        doStuckDetection(entityPos);
    }

    @Override
    public boolean moveTo(Entity entity, double d) {
        Path path = createPath(entity, 0);
        if (path != null) {
            return moveTo(path, d);
        }
        speedModifier = d;
        return true;
    }

    @Override
    public void tick() {
        super.tick();
        if (getTargetPos() != null) {
            mob.getLookControl().setLookAt(getTargetPos().getX(), getTargetPos().getY(), getTargetPos().getZ());
        }
    }

    private boolean tryShortcut(Path path, Vec3 entityPos, int pathLength, Vec3 base, Vec3 max) {
        for (int i = pathLength - 1; i > path.getNextNodeIndex(); i--) {
            final Vec3 vec = path.getEntityPosAtNode(mob, i).subtract(entityPos);
            if (NavUtil.isNoCollisionOnPath(vec, base, max, PathComputationType.LAND, mob, nodeEvaluator)) {
                path.setNextNodeIndex(i);
                return true;
            }
        }
        return false;
    }
}
