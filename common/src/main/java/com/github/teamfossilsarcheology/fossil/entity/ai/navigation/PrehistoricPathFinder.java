package com.github.teamfossilsarcheology.fossil.entity.ai.navigation;

import com.github.teamfossilsarcheology.fossil.util.Version;
import com.google.common.collect.Lists;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.util.profiling.metrics.MetricCategory;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.level.PathNavigationRegion;
import net.minecraft.world.level.pathfinder.*;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class PrehistoricPathFinder extends PathFinder {
    private static final float FUDGING = 1.5f;
    private static final boolean DEBUG = Version.debugEnabled();
    private final Node[] neighbors = new Node[32];
    private final int maxVisitedNodes;
    private final NodeEvaluator nodeEvaluator;
    private final BinaryHeap openSet = new BinaryHeap();
    protected final List<Node> closedSet = new ArrayList<>();
    protected final Mob mob;

    public PrehistoricPathFinder(NodeEvaluator nodeEvaluator, int maxVisitedNodes, Mob mob) {
        super(nodeEvaluator, maxVisitedNodes);
        this.nodeEvaluator = nodeEvaluator;
        this.maxVisitedNodes = maxVisitedNodes;
        this.mob = mob;
    }

    /**
     * Finds a path to one of the specified positions and post-processes it or returns null if no path could be found within given accuracy
     */
    @Override
    @Nullable
    public Path findPath(PathNavigationRegion region, Mob mob, Set<BlockPos> targetPositions, float maxRange, int accuracy, float searchDepthMultiplier) {
        this.openSet.clear();
        closedSet.clear();
        this.nodeEvaluator.prepare(region, mob);
        Node node = nodeEvaluator.getStart();
        Map<Target, BlockPos> map = targetPositions.stream().collect(Collectors.toMap(blockPos -> nodeEvaluator.getGoal(blockPos.getX(), blockPos.getY(), blockPos.getZ()), Function.identity()));
        PatchedPath path = findPatchedPath(region.getProfiler(), node, map, maxRange, accuracy, searchDepthMultiplier);
        if (DEBUG && path != null) path.setDebug(openSet.getHeap(), closedSet.toArray(Node[]::new), map.keySet());
        this.nodeEvaluator.done();
        return path;
    }

    protected PatchedPath findPatchedPath(ProfilerFiller profiler, Node start, Map<Target, BlockPos> targetPos, float maxRange, int accuracy, float searchDepthMultiplier) {
        profiler.push("find_path");
        profiler.markForCharting(MetricCategory.PATH_FINDING);
        Set<Target> set = targetPos.keySet();
        start.g = 0.0f;
        start.f = start.h = getBestH(start, set, false);
        openSet.clear();
        openSet.insert(start);
        int i = 1;
        int maxNodes = (int) (maxVisitedNodes * searchDepthMultiplier);
        while (!openSet.isEmpty() && i < maxNodes) {
            Node node = openSet.pop();
            closedSet.add(node);

            node.closed = true;
            for (Target target : set) {
                if (node.distanceManhattan(target) > accuracy) continue;
                target.setReached();
                return reconstructPath(target, targetPos.get(target), true);
            }
            if (node.distanceTo(start) >= maxRange) continue;
            int neighborCount = nodeEvaluator.getNeighbors(neighbors, node);
            for (int l = 0; l < neighborCount; ++l) {
                Node neighbor = neighbors[l];
                float f = node.distanceTo(neighbor);
                neighbor.walkedDistance = node.walkedDistance + f;
                float g = node.g + f + neighbor.costMalus;
                if (neighbor.walkedDistance >= maxRange || neighbor.inOpenSet() && g >= neighbor.g) continue;
                neighbor.cameFrom = node;
                neighbor.g = g;
                neighbor.h = getBestH(neighbor, set, true);
                if (neighbor.inOpenSet()) {
                    openSet.changeCost(neighbor, neighbor.g + neighbor.h);
                } else {
                    neighbor.f = neighbor.g + neighbor.h;
                    openSet.insert(neighbor);
                }
            }
            i++;
        }
        Optional<PatchedPath> path = set.stream().map(target -> reconstructPath(target, targetPos.get(target), false))
                .min(Comparator.comparingDouble(PatchedPath::getDistToTarget).thenComparingInt(PatchedPath::getNodeCount));
        profiler.pop();
        return path.orElse(null);
    }

    protected float getBestH(Node node, Set<Target> targets, boolean fudge) {
        float f = Float.MAX_VALUE;
        for (Target target : targets) {
            float g = node.distanceTo(target);
            target.updateBest(g, node);
            f = Math.min(g, f);
        }
        return f * (fudge ? FUDGING : 1);
    }

    /**
     * Converts a recursive path point structure into a path
     */
    private PatchedPath reconstructPath(Target target, BlockPos targetPos, boolean reachesTarget) {
        Node end = target.getBestNode();
        ArrayList<Node> list = Lists.newArrayList();
        Node node = end;
        list.add(0, node);
        while (node.cameFrom != null) {
            node = node.cameFrom;
            list.add(0, node);
        }
        //This should help some of the smaller mobs reach their target
        if (mob.getBbWidth() < 1 && reachesTarget) {
            list.add(target);
        }
        return new PatchedPath(list, targetPos, reachesTarget);
    }

    public static class PatchedPath extends Path {
        public PatchedPath(List<Node> list, BlockPos blockPos, boolean bl) {
            super(list, blockPos, bl);
        }

        @Override
        public @NotNull Vec3 getEntityPosAtNode(Entity entity, int index) {
            Node point = getNode(index);
            double d0 = point.x + Mth.floor(entity.getBbWidth() + 1) * 0.5;
            double d1 = point.y;
            double d2 = point.z + Mth.floor(entity.getBbWidth() + 1) * 0.5;
            return new Vec3(d0, d1, d2);
        }
    }
}
