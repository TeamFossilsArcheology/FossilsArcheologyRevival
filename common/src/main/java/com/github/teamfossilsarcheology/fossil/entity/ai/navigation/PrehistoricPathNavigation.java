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
        if (Math.abs(blockPos.getY() - mob.getBlockY()) > 4) {
            return vec.y;
        }
        if (level.getBlockState(blockPos.below()).isPathfindable(level, blockPos, PathComputationType.LAND)) {
            if (blockPos.getY() >= mob.getY() + 2) {
                return vec.y - 1;
            }
            return vec.y;
        }
        return WalkNodeEvaluator.getFloorLevel(this.level, blockPos);
    }

    @Override
    protected void followThePath() {
        Path path = Objects.requireNonNull(this.path);
        Vec3 entityPos = getTempMobPos();
        int pathLength = path.getNodeCount();

        final Vec3 base = entityPos.add(-mob.getBbWidth() * 0.5F, 0, -mob.getBbWidth() * 0.5F);
        final Vec3 max = base.add(mob.getBbWidth(), mob.getBbHeight(), mob.getBbWidth());

        boolean shortcut = tryShortcut(path, new Vec3(mob.getX(), mob.getY(), mob.getZ()), pathLength, base, max);

        if (!shortcut) {
            float hThreshold = Math.max(1.0F, mob.getBbWidth() * 0.5F);
            float hThresholdElev = Math.max(2.5F, mob.getBbWidth() * 0.5F);

            int nodeIndex = path.getNextNodeIndex();
            Vec3 targetPos = path.getEntityPosAtNode(mob, nodeIndex);

            boolean isAtTarget = Math.abs(mob.getX() - targetPos.x) < hThreshold
                    && Math.abs(mob.getZ() - targetPos.z) < hThreshold
                    && Math.abs(mob.getY() - targetPos.y) <= 4;

            boolean isAtElevation = NavUtil.atElevationChange(mob, path)
                    && Math.abs(mob.getX() - targetPos.x) < hThresholdElev
                    && Math.abs(mob.getZ() - targetPos.z) < hThresholdElev
                    && Math.abs(mob.getY() - targetPos.y) <= 4;

            if (isAtTarget || isAtElevation) {
                mob.getLookControl().setLookAt(path.getNextEntityPos(mob));
                if (path instanceof CenteredPath centeredPath) {
                    centeredPath.invalidateCacheForNode(nodeIndex);
                }
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

    private boolean isGapNode(Path path, int index) {
        Vec3 targetPos = path.getEntityPosAtNode(mob, index);
        double rawX = path.getNode(index).x + 0.5;
        double rawZ = path.getNode(index).z + 0.5;
        return Math.abs(targetPos.x - rawX) > 0.01 || Math.abs(targetPos.z - rawZ) > 0.01;
    }

    private boolean isGroundedAlongPath(Vec3 start, Vec3 vec) {
        int steps = Math.max(2, (int) Math.ceil(vec.length()));
        float halfWidth = mob.getBbWidth() / 2.0f;

        double len = Math.sqrt(vec.x * vec.x + vec.z * vec.z);
        double perpX = len > 1e-6 ? -vec.z / len : 1.0;
        double perpZ = len > 1e-6 ?  vec.x / len : 0.0;

        for (int s = 1; s <= steps; s++) {
            double t = (double) s / steps;
            double cx = start.x + vec.x * t;
            double cy = start.y + vec.y * t;
            double cz = start.z + vec.z * t;

            for (float w : new float[]{0f, -halfWidth, halfWidth}) {
                double checkX = cx + perpX * w;
                double checkZ = cz + perpZ * w;
                BlockPos below = BlockPos.containing(checkX, cy - 1, checkZ);
                if (!level.getBlockState(below).isSolid()) {
                    return false;
                }
            }
        }
        return true;
    }

    private boolean tryShortcut(Path path, Vec3 entityPos, int pathLength, Vec3 base, Vec3 max) {
        int currentIndex = path.getNextNodeIndex();

        if (isGapNode(path, currentIndex)) {
            return false;
        }

        for (int i = pathLength - 1; i > currentIndex; i--) {
            if (isGapNode(path, i)) {
                continue;
            }

            boolean hasGapNodeBetween = false;
            for (int j = currentIndex + 1; j < i; j++) {
                if (isGapNode(path, j)) {
                    hasGapNodeBetween = true;
                    break;
                }
            }
            if (hasGapNodeBetween) continue;

            int currentNodeY = path.getNode(currentIndex).y;
            int candidateNodeY = path.getNode(i).y;
            if (currentNodeY - candidateNodeY > 2) continue;

            final Vec3 targetPos = path.getEntityPosAtNode(mob, i);
            final Vec3 vec = targetPos.subtract(entityPos);

            if (!isGroundedAlongPath(entityPos, vec)) continue;

            if (NavUtil.isNoCollisionOnPath(vec, base, max, PathComputationType.LAND, mob, nodeEvaluator)) {
                path.setNextNodeIndex(i);
                return true;
            }
        }
        return false;
    }
}