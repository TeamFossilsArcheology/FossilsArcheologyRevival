package com.github.teamfossilsarcheology.fossil.client.gui.debug.navigation;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

public class SweepPathNavigation extends PlayerPathNavigation {
    @Nullable
    private BlockPos pathToPosition;

    public SweepPathNavigation(Player player, Level level) {
        super(player, level, "Sweep");
    }

    @Override
    protected PlayerPathFinder createPathFinder(int maxVisitedNodes) {
        nodeEvaluator = new PlayerNodeEvaluator();
        return new DebugPathFinder(nodeEvaluator, maxVisitedNodes, false);
    }

    @Override
    protected void followThePath() {
        PlayerPath path = Objects.requireNonNull(this.path);
        path.sweepNextNodeIndex = path.getDebugNodeIndex();
        Vec3 entityPos = sweepStartPos;
        int pathLength = path.getNodeCount();
        for (int i = path.getDebugNodeIndex(); i < path.getNodeCount(); i++) {
            if (path.getNode(i).y != Math.floor(entityPos.y)) {
                pathLength = i;
                break;
            }
        }
        final Vec3 base = entityPos.add(-PathingRenderer.getBbWidth() * 0.5F, 0.0F, -PathingRenderer.getBbWidth() * 0.5F);
        final Vec3 max = base.add(PathingRenderer.getBbWidth(), PathingRenderer.getBbHeight(), PathingRenderer.getBbWidth());
        if (tryShortcut(path, entityPos, pathLength, base, max)) {
            if (isAt(path, 0.5F) || atElevationChange(path) && isAt(path, PathingRenderer.getBbWidth() * 0.5F)) {
                path.setSweepNodeIndex(path.sweepNextNodeIndex + 1);
            }
        }
        //doStuckDetection(entityPos);
    }

    @Override
    public void setSweepStartPos(Vec3 vec) {
        super.setSweepStartPos(vec);
        if (path != null) {
            followThePath();
            tick();
        }
    }

    @Override
    public PlayerPath createPath(BlockPos blockPos, int i) {
        pathToPosition = blockPos;
        return super.createPath(blockPos, i);
    }

    @Override
    public PlayerPath createPath(Entity entity, int i) {
        pathToPosition = entity.blockPosition();
        return super.createPath(entity, i);
    }

    @Override
    public void tick() {
        Vec3 vec3 = path.getNextEntityPos(player);
        setNextWantedPosition(vec3.x, getGroundY(vec3), vec3.z);
        vec3 = path.getSweepEntityPos(player);
        setSweepWantedPosition(vec3.x, getGroundY(vec3), vec3.z);
        if (isDone()) {
            if (pathToPosition != null) {
                if (pathToPosition.closerToCenterThan(player.position(), PathingRenderer.getBbWidth()) || player.getY() > (double) pathToPosition.getY() && new BlockPos(pathToPosition.getX(), player.getY(), pathToPosition.getZ()).closerToCenterThan(player.position(), PathingRenderer.getBbWidth())) {
                    pathToPosition = null;
                } else {
                    setNextWantedPosition(pathToPosition.getX(), pathToPosition.getY(), pathToPosition.getZ());
                    setSweepWantedPosition(pathToPosition.getX(), pathToPosition.getY(), pathToPosition.getZ());
                }
            }
        }
    }

    private boolean isAt(PlayerPath path, float threshold) {
        Vec3 entityPos = sweepStartPos;
        //final Vec3 pathPos = path.getNextEntityPos(player);
        final Vec3 pathPos = path.getSweepEntityPos(player);
        return Mth.abs((float) (entityPos.x - pathPos.x)) < threshold
                && Mth.abs((float) (entityPos.z - pathPos.z)) < threshold
                && Math.abs(entityPos.y - pathPos.y) <= 1.0D;
    }

    private boolean atElevationChange(PlayerPath path) {
        //final int curr = path.getDebugNodeIndex();
        final int curr = path.sweepNextNodeIndex;
        final int end = Math.min(path.getNodeCount(), curr + Mth.ceil(PathingRenderer.getBbWidth() * 0.5F) + 1);
        final int currY = path.getNode(curr).y;
        for (int i = curr + 1; i < end; i++) {
            if (path.getNode(i).y != currY) {
                return true;
            }
        }
        return false;
    }

    private boolean tryShortcut(PlayerPath path, Vec3 entityPos, int pathLength, Vec3 base, Vec3 max) {
        for (int i = pathLength; --i > path.getDebugNodeIndex(); ) {
            final Vec3 vec = path.getEntityPosAtNode(i).subtract(entityPos);
            if (!isCollisionOnPath(vec, base, max)) {
                path.setSweepNodeIndex(i);
                return false;
            }
        }
        return true;
    }
    static final float EPSILON = 1.0E-8F;

    private boolean isCollisionOnPath(Vec3 pathVec, Vec3 minBounds, Vec3 maxBounds) {
        float pathLength = (float) pathVec.length();
        if (pathLength < EPSILON) {
            return false;
        }
        final float[] trailingPositions = new float[3];
        final int[] leadingEdges = new int[3];//the leading edge/block for each axis
        final int[] trailingEdges = new int[3];//the trailing edge/block for each axis
        final int[] stepDirections = new int[3];//direction for each axis should be stepped represented as -1 or 1
        final float[] stepDelta = new float[3];//how much should be added to the next step. Path length divided by axis length
        final float[] stepLength = new float[3];//how long the next step should be (always starting from the beginning)
        final float[] normedAxis = new float[3];//axis length divided by Path length
        for (Direction.Axis axis : Direction.Axis.values()) {
            float axisLength = chooseLengthForAxis(axis, pathVec);
            boolean stepDirection = axisLength >= 0.0F;
            int idx = axis.ordinal();
            stepDirections[idx] = stepDirection ? 1 : -1;
            float lead = chooseLengthForAxis(axis, stepDirection ? maxBounds : minBounds);
            trailingPositions[idx] = chooseLengthForAxis(axis, stepDirection ? minBounds : maxBounds);
            leadingEdges[idx] = leadEdgesToInt(lead, stepDirections[idx]);
            trailingEdges[idx] = trailEdgeToInt(trailingPositions[idx], stepDirections[idx]);
            normedAxis[idx] = axisLength / pathLength;
            stepDelta[idx] = Mth.abs(pathLength / axisLength);
            float dist = stepDirection ? (leadingEdges[idx] + 1 - lead) : (lead - leadingEdges[idx]);
            stepLength[idx] = stepDelta[idx] < Float.POSITIVE_INFINITY ? stepDelta[idx] * dist : Float.POSITIVE_INFINITY;
        }
        final BlockPos.MutableBlockPos pos = new BlockPos.MutableBlockPos();
        float previousStepLength = 0;
        do {
            //stepForward: pick axis with the shortest step
            Direction.Axis axis = (stepLength[0] < stepLength[1]) ? ((stepLength[0] < stepLength[2]) ? Direction.Axis.X : Direction.Axis.Z) : ((stepLength[1] < stepLength[2]) ? Direction.Axis.Y : Direction.Axis.Z);
            int idx = axis.ordinal();
            float dt = stepLength[idx] - previousStepLength;
            previousStepLength = stepLength[idx];
            leadingEdges[idx] += stepDirections[idx];
            stepLength[idx] += stepDelta[idx];
            for (Direction.Axis axis2 : Direction.Axis.values()) {
                int i = axis2.ordinal();
                trailingPositions[i] += dt * normedAxis[i];
                trailingEdges[i] = trailEdgeToInt(trailingPositions[i], stepDirections[i]);
            }
            // checkCollision
            int stepX = stepDirections[0];
            int minX = (axis == Direction.Axis.X) ? leadingEdges[0] : trailingEdges[0];
            int maxX = leadingEdges[0] + stepX;
            int stepY = stepDirections[1];
            int minY = (axis == Direction.Axis.Y) ? leadingEdges[1] : trailingEdges[1];
            int maxY = leadingEdges[1] + stepY;
            int stepZ = stepDirections[2];
            int minZ = (axis == Direction.Axis.Z) ? leadingEdges[2] : trailingEdges[2];
            int maxZ = leadingEdges[2] + stepZ;
            for (int x = minX; x != maxX; x += stepX) {
                for (int z = minZ; z != maxZ; z += stepZ) {
                    if (isCollisionAtColumn(x, minY, z, maxY, stepY, pos)) {
                        return true;
                    }
                }
            }
        } while (previousStepLength <= pathLength);
        return false;
    }

    private boolean isCollisionAtColumn(int x, int minY, int z, int maxY, int stepY, BlockPos.MutableBlockPos pos) {
        for (int y = minY; y != maxY; y += stepY) {
            BlockState block = level.getBlockState(pos.set(x, y, z));
            if (!block.isPathfindable(level, pos, PathComputationType.LAND)) {
                return true;
            }
        }
        BlockPathTypes in = nodeEvaluator.getBlockPathType(level, x, minY, z);
        float malus = PathingDebug.getPathfindingMalus(in);
        if (malus < 0.0F || malus >= 8.0F) {
            return true;
        }
        BlockPathTypes below = nodeEvaluator.getBlockPathType(level, x, minY - 1, z);
        if (below == BlockPathTypes.WATER || below == BlockPathTypes.LAVA || below == BlockPathTypes.OPEN) {
            return true;
        }
        if (in == BlockPathTypes.DAMAGE_FIRE || in == BlockPathTypes.DANGER_FIRE || in == BlockPathTypes.DAMAGE_OTHER) {
            return true;
        }
        return false;
    }

    static float chooseLengthForAxis(Direction.Axis axis, Vec3 vec) {
        return (float) axis.choose(vec.x, vec.y, vec.z);
    }

    static int leadEdgesToInt(float coord, int step) {
        return Mth.floor(coord - step * EPSILON);
    }

    static int trailEdgeToInt(float coord, int step) {
        return Mth.floor(coord + step * EPSILON);
    }
}
