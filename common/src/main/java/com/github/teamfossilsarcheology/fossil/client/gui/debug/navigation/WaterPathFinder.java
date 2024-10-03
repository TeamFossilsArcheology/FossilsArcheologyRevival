package com.github.teamfossilsarcheology.fossil.client.gui.debug.navigation;

import com.github.teamfossilsarcheology.fossil.client.gui.debug.PathingScreen;
import com.google.common.collect.Sets;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.PathNavigationRegion;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.level.pathfinder.Node;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.level.pathfinder.Target;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class WaterPathFinder extends PlayerPathFinder {

    public WaterPathFinder(PlayerNodeEvaluator nE, int i) {
        super(nE, i);
    }

    @Override
    public @Nullable PlayerPath findPath(PathNavigationRegion region, Player player, Set<BlockPos> targetPositions, float maxRange, int accuracy, float searchDepthMultiplier) {
        openSet.clear();
        closedSet.clear();
        nodeEvaluator.prepare(region, player);
        Node node = nodeEvaluator.getStart();
        Map<Target, BlockPos> map = targetPositions.stream().collect(Collectors.toMap(blockPos -> nodeEvaluator.getGoal(blockPos.getX(), blockPos.getY(), blockPos.getZ()), Function.identity()));
        //TODO: Lets ignore for now that there could be multiple targets
        long start = System.nanoTime();
        if (targetPositions.size() == 1 && PathingDebug.pos3 != null) {
            Map.Entry<Target, BlockPos> target = map.entrySet().stream().toList().get(0);
            double d = target.getKey().x + ((int) (PathingRenderer.getBbWidth() + 1d)) * 0.5;
            double e = target.getKey().y;
            double f = target.getKey().z + ((int) (PathingRenderer.getBbWidth() + 1d)) * 0.5;
            Vec3 vec = new Vec3(d, e, f).subtract(PathingDebug.pos3);
            final Vec3 base = PathingDebug.pos3.add(-PathingRenderer.getBbWidth() * 0.5F, 0.0F, -PathingRenderer.getBbWidth() * 0.5F);
            final Vec3 max = base.add(PathingRenderer.getBbWidth(), PathingRenderer.getBbHeight(), PathingRenderer.getBbWidth());
            if (sweep(vec, base, max)) {
                PlayerPath path = new DebugLargePath(List.of(node, target.getKey()), target.getValue(), false);
                path.setDebug(openSet.getHeap(), closedSet.toArray(Node[]::new), map.keySet());
                return path;
            }
        }
        System.out.println("Sweep time: " + (System.nanoTime() - start));
        start = System.nanoTime();
        PlayerPath path = findPath(node, map, maxRange, accuracy, searchDepthMultiplier);
        System.out.println("Path time: " + (System.nanoTime() - start));
        nodeEvaluator.done();
        if (path != null) {
            path = DebugLargePath.createFromPath(path);
            path.setDebug(openSet.getHeap(), closedSet.toArray(Node[]::new), map.keySet());
        }
        return path;
    }

    Direction getDirection(Node pos, Set<Target> targets) {
        for (Target target : targets) {
            int xDiff = target.x - pos.x;
            if (xDiff != 0) {
                return xDiff > 0 ? Direction.EAST : Direction.WEST;
            }
            int zDiff = target.z - pos.z;
            if (zDiff != 0) {
                return zDiff > 0 ? Direction.SOUTH : Direction.NORTH;
            }
            int yDiff = target.y - pos.y;
            if (yDiff != 0) {
                return yDiff > 0 ? Direction.UP : Direction.DOWN;
            }
        }
        return Direction.DOWN;
    }

    @Nullable
    protected PlayerPath findPath(Node node, Map<Target, BlockPos> targetPos, float maxRange, int accuracy, float searchDepthMultiplier) {
        Set<Target> set = targetPos.keySet();
        node.g = 0.0f;
        node.f = node.h = getBestH(node, set);
        openSet.clear();
        openSet.insert(node);
        Target targetN = set.stream().findFirst().get();
        System.out.println("Distance " + node.distanceTo(targetN) + " dist: " + dist(node, targetN));
        int i = 1;
        Set<Target> set3 = Sets.newHashSetWithExpectedSize(set.size());
        int j = (int)(maxVisitedNodes * searchDepthMultiplier);
        while (!openSet.isEmpty() && i < j) {
            System.out.println(i);
            Node node2 = openSet.pop();
            closedSet.add(node2);
            System.out.println(node2 + " f: " + node2.f + " g: " + node2.g + " h: " + node2.h + " walkDistance: " + node2.walkedDistance);

            node2.closed = true;
            for (Target target2 : set) {
                if (node2.distanceManhattan(target2) > accuracy) continue;
                target2.setReached();
                set3.add(target2);
            }
            if (!set3.isEmpty()) break;
            //TODO: Slows down search if the target is outside maxRange. Maybe cancel? Would be greedy but maybe not with horizontalDistance
            if (node2.distanceTo(node) >= maxRange) continue;
            int k = nodeEvaluator.getNeighbors(neighbors, node2);
            for (int l = 0; l < k; ++l) {
                Node node3 = neighbors[l];
                float f = node2.distanceTo(node3);
                node3.walkedDistance = node2.walkedDistance + f;
                float g = node2.g + f + node3.costMalus;
                if (node3.walkedDistance >= maxRange || node3.inOpenSet() && g >= node3.g) continue;
                node3.cameFrom = node2;
                node3.g = g;
                node3.h = getBestH(node3, set);
                //node3.h = getBestH(node3, set) * FUDGING;
                if (node3.inOpenSet()) {
                    openSet.changeCost(node3, node3.g + node3.h);
                    continue;
                }
                node3.f = node3.g + node3.h;
                System.out.println("Adding " + node3 + " f: " + node3.f + " g: " + node3.g + " h: " + node3.h+ " walkDistance: " + node3.walkedDistance);
                openSet.insert(node3);
            }
            i++;
        }
        Optional<PlayerPath> optional = !set3.isEmpty() ? set3.stream().map(target -> reconstructPath(target.getBestNode(), targetPos.get(target), true)).min(Comparator.comparingInt(PlayerPath::getNodeCount)) : set.stream().map(target -> reconstructPath(target.getBestNode(), targetPos.get(target), false)).min(Comparator.comparingDouble(PlayerPath::getDistToTarget).thenComparingInt(PlayerPath::getNodeCount));
        return optional.orElse(null);
    }

    @Override
    public float getBestH(Node node, Set<Target> targets) {
        float f = Float.MAX_VALUE;
        for (Target target : targets) {
            float g = dist(node, target);
            //float g = node.distanceTo(target);
            target.updateBest(g, node);
            f = Math.min(g, f);
        }
        return f;
    }
    public float dist(Node f, Node l) {
        float xDiff = Math.abs(l.x - f.x);
        float yDiff = Math.abs(l.y - f.y);
        float zDiff = Math.abs(l.z - f.z);
        float min = Math.min(Math.min(xDiff, yDiff), zDiff);
        float max = Math.max(Math.max(xDiff, yDiff), zDiff);
        float tripleAxis = min;
        float doubleAxis = xDiff + yDiff + zDiff - max - 2*min;
        float singleAxis = max - doubleAxis - tripleAxis;
        return PathingScreen.face * singleAxis + PathingScreen.edge * doubleAxis + PathingScreen.corner * tripleAxis;
    }
    public static List<BlockPos> sweeped;

    static final float EPSILON = 1.0E-8F;

    // Based off of
    // https://github.com/andyhall/voxel-aabb-sweep/blob/d3ef85b19c10e4c9d2395c186f9661b052c50dc7/index.js
    private boolean sweep(Vec3 vec, Vec3 base, Vec3 max) {
        sweeped = new ArrayList<>();
        float t = 0.0F;
        //NEW: the value of t at which the ray crosses the first voxel boundary
        float max_t = (float) vec.length();
        if (max_t < EPSILON)
            return true;
        final float[] trails = new float[3];
        final int[] leadEdge = new int[3];
        final int[] tri = new int[3];
        final int[] step = new int[3];
        final float[] tDelta = new float[3];
        final float[] tNext = new float[3];
        final float[] normed = new float[3];

        for (Direction.Axis axis : Direction.Axis.values()) {
            float value = (float) axis.choose(vec.x, vec.y, vec.z);
            boolean dir = value >= 0.0F;
            int i = axis.ordinal();
            step[i] = dir ? 1 : -1;
            float lead = (float) (dir ? axis.choose(max.x, max.y, max.z) : axis.choose(base.x, base.y, base.z));
            trails[i] = (float) (dir ? axis.choose(base.x, base.y, base.z) : axis.choose(max.x, max.y, max.z));
            leadEdge[i] = leadEdgeToInt(lead, step[i]);
            tri[i] = trailEdgeToInt(trails[i], step[i]);
            normed[i] = value / max_t;
            tDelta[i] = Mth.abs(max_t / value);
            float dist = dir ? (leadEdge[i] + 1 - lead) : (lead - leadEdge[i]);
            tNext[i] = tDelta[i] < Float.POSITIVE_INFINITY ? tDelta[i] * dist : Float.POSITIVE_INFINITY;
        }
        for (int i = 0; i < 3; i++) {
            //New: x y z value of vec
            float value = element(vec, i);
            boolean dir = value >= 0.0F;
            //New: step contains direction step/normal
            step[i] = dir ? 1 : -1;
            //New: lead is bounding box min/max xyz based on direction
            float lead = element(dir ? max : base, i);
            //New: trails is bounding box max/min xyz based on direction
            trails[i] = element(dir ? base : max, i);
            //New: move lead and trail closer to player if exactly on boundary (eg lead: 4 -> 3, lead 4.1 -> 4)
            leadEdge[i] = leadEdgeToInt(lead, step[i]);
            tri[i] = trailEdgeToInt(trails[i], step[i]);
            //New: 2, 0, 2 -> 1, 0, 0 | 2, 0, 2 -> 0.707, 0, 0.707
            normed[i] = value / max_t;
            //New: 2, 0, 0 -> 1, 0, 0 | 2, 0, 2 -> 1.414, 0, 1.414
            tDelta[i] = Mth.abs(max_t / value);
            //New: lead(4): 3+1-4=0, lead(4.1): 4+1-4.1=0.9
            float dist = dir ? (leadEdge[i] + 1 - lead) : (lead - leadEdge[i]);
            //New: 1, 0, 0 -> 0|0.999, 0, 0 | 2, 0, 2 -> 0|1.999, 0, 0|1.999
            tNext[i] = tDelta[i] < Float.POSITIVE_INFINITY ? tDelta[i] * dist : Float.POSITIVE_INFINITY;
        }
        final BlockPos.MutableBlockPos pos = new BlockPos.MutableBlockPos();
        do {
            // stepForward
            Direction.Axis axis = (tNext[0] < tNext[1]) ? ((tNext[0] < tNext[2]) ? Direction.Axis.X : Direction.Axis.Z) : ((tNext[1] < tNext[2]) ? Direction.Axis.Y : Direction.Axis.Z);
            int idx = axis.ordinal();
            float dt = tNext[idx] - t;
            t = tNext[idx];
            leadEdge[idx] += step[idx];
            tNext[idx] += tDelta[idx];
            for (Direction.Axis axis2 : Direction.Axis.values()) {
                int i = axis2.ordinal();
                trails[i] += dt * normed[i];
                tri[i] = trailEdgeToInt(trails[i], step[i]);
            }
            // checkCollision
            int stepx = step[0];
            int x0 = (idx == 0) ? leadEdge[0] : tri[0];
            int x1 = leadEdge[0] + stepx;
            int stepy = step[1];
            int y0 = (idx == 1) ? leadEdge[1] : tri[1];
            int y1 = leadEdge[1] + stepy;
            int stepz = step[2];
            int z0 = (idx == 2) ? leadEdge[2] : tri[2];
            int z1 = leadEdge[2] + stepz;
            for (int x = x0; x != x1; x += stepx) {
                for (int z = z0; z != z1; z += stepz) {
                    for (int y = y0; y != y1; y += stepy) {
                        //TODO: Use PathRenderer#renderLines to visualize these rays
                        BlockState block = nodeEvaluator.level.getBlockState(pos.set(x, y, z));
                        sweeped.add(pos.immutable());
                        if (!block.isPathfindable(nodeEvaluator.level, pos, PathComputationType.WATER)){
                            System.out.format("%s, %s", block.getBlock(), pos);
                            System.out.println();
                            return false;
                        }
                    }
                    BlockPathTypes below = nodeEvaluator.getBlockPathType(nodeEvaluator.level, x, y0 - 1, z);
                    if (below == BlockPathTypes.LAVA || below == BlockPathTypes.OPEN){
                        System.out.format("below: %s", below);
                        System.out.println();
                        return false;
                    }
                    BlockPathTypes in = nodeEvaluator.getBlockPathType(nodeEvaluator.level, x, y0, z);
                    float malus = PathingDebug.getPathfindingMalus(in);
                    if (malus < 0.0F || malus > 8.0F) {
                        System.out.format("in: %s, %s", in, malus);
                        System.out.println();
                        return false;
                    }
                    if (in == BlockPathTypes.DAMAGE_FIRE || in == BlockPathTypes.DANGER_FIRE || in == BlockPathTypes.DAMAGE_OTHER)
                        return false;
                }
            }
        } while (t <= max_t);
        return true;
    }

    static int leadEdgeToInt(float coord, int step) {
        return Mth.floor(coord - step * EPSILON);
    }

    static int trailEdgeToInt(float coord, int step) {
        return Mth.floor(coord + step * EPSILON);
    }

    static float element(Vec3 v, int i) {
        return switch (i) {
            case 0 -> (float) v.x;
            case 1 -> (float) v.y;
            case 2 -> (float) v.z;
            default -> 0.0F;
        };
    }
}
