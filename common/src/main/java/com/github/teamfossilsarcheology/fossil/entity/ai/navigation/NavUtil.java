package com.github.teamfossilsarcheology.fossil.entity.ai.navigation;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.level.pathfinder.NodeEvaluator;
import net.minecraft.world.level.pathfinder.Path;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.Vec3;

/*
 * Based off of "Mowzie's Mobs"
 * found at https://www.curseforge.com/minecraft/mc-mods/mowzies-mobs
 * with source code at https://github.com/BobMowzie/MowziesMobs/blob/master/src/main/java/com/bobmowzie/mowziesmobs/server/ai/MMPathNavigateGround.java
 */
public class NavUtil {

    //TODO: Could probably move all this to a parent Navigation class/interface as long as its only needed in the Navigators
    static final float EPSILON = 1.0E-8F;

    // Based off of https://github.com/andyhall/voxel-aabb-sweep/blob/d3ef85b19c10e4c9d2395c186f9661b052c50dc7/index.js
    public static boolean isNoCollisionOnPath(Vec3 pathVec, Vec3 minBounds, Vec3 maxBounds, PathComputationType type, Mob mob, NodeEvaluator nodeEvaluator) {
        float pathLength = (float) pathVec.length();
        if (pathLength < EPSILON) {
            return true;
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
                    if (isCollisionAtColumn(x, minY, z, maxY, stepY, type, mob, pos, nodeEvaluator)) {
                        return false;
                    }
                }
            }
        } while (previousStepLength <= pathLength);
        return true;
    }

    private static boolean isCollisionAtColumn(int x, int minY, int z, int maxY, int stepY, PathComputationType type, Mob mob, BlockPos.MutableBlockPos pos, NodeEvaluator nodeEvaluator) {
        if (type == PathComputationType.WATER) {
            //This should help when the column is partially outside the water
            boolean anyWater = false;
            for (int y = minY; y != maxY; y += stepY) {
                BlockState block = mob.level().getBlockState(pos.set(x, y, z));
                if (block.isPathfindable(mob.level(), pos, type)) {
                    anyWater = true;
                    break;
                }
            }
            if (!anyWater) {
                return true;
            }
            for (int y = minY; y != maxY; y += stepY) {
                BlockState block = mob.level().getBlockState(pos.set(x, y, z));
                if (!block.isPathfindable(mob.level(), pos, PathComputationType.WATER) && !block.isPathfindable(mob.level(), pos, PathComputationType.LAND)) {
                    return true;
                }
            }
        } else {
            for (int y = minY; y != maxY; y += stepY) {
                BlockState block = mob.level().getBlockState(pos.set(x, y, z));
                if (!block.isPathfindable(mob.level(), pos, type)) {
                    return true;
                }
            }
        }

        BlockPathTypes in = nodeEvaluator.getBlockPathType(mob.level(), x, minY, z);
        float malus = mob.getPathfindingMalus(in);
        if (malus < 0.0F || malus >= 8.0F) {
            return true;
        }
        if (type == PathComputationType.LAND) {
            BlockPathTypes below = nodeEvaluator.getBlockPathType(mob.level(), x, minY - 1, z);
            if (below == BlockPathTypes.WATER || below == BlockPathTypes.LAVA || below == BlockPathTypes.OPEN) {
                return true;
            }
            return in == BlockPathTypes.DAMAGE_FIRE || in == BlockPathTypes.DANGER_FIRE || in == BlockPathTypes.DAMAGE_OTHER;
        }
        return false;
    }

    private static float chooseLengthForAxis(Direction.Axis axis, Vec3 vec) {
        return (float) axis.choose(vec.x, vec.y, vec.z);
    }

    private static int leadEdgesToInt(float coord, int step) {
        return Mth.floor(coord - step * EPSILON);
    }

    private static int trailEdgeToInt(float coord, int step) {
        return Mth.floor(coord + step * EPSILON);
    }

    public static boolean isAt(Entity mob, Path path, float threshold, float verticalThreshold) {
        final Vec3 pathPos = path.getNextEntityPos(mob);
        return Math.abs(mob.getX() - pathPos.x) < threshold
                && Math.abs(mob.getZ() - pathPos.z) < threshold
                && Math.abs(mob.getY() - pathPos.y) <= verticalThreshold;
    }

    public static boolean atElevationChange(Entity mob, Path path) {
        final int curr = path.getNextNodeIndex();
        final int end = Math.min(path.getNodeCount(), curr + Mth.ceil(mob.getBbWidth() * 0.5F) + 1);
        final int currY = path.getNode(curr).y;
        for (int i = curr + 1; i < end; i++) {
            if (path.getNode(i).y != currY) {
                return true;
            }
        }
        return false;
    }
}
