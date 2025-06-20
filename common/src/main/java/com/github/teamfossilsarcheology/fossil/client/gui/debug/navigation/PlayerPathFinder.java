package com.github.teamfossilsarcheology.fossil.client.gui.debug.navigation;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.PathNavigationRegion;
import net.minecraft.world.level.pathfinder.BinaryHeap;
import net.minecraft.world.level.pathfinder.Node;
import net.minecraft.world.level.pathfinder.Target;
import org.jetbrains.annotations.Nullable;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class PlayerPathFinder {
    protected static final float FUDGING = 1.5f;
    protected final Node[] neighbors = new Node[32];
    protected final int maxVisitedNodes;
    public final PlayerNodeEvaluator nodeEvaluator;
    protected final BinaryHeap openSet = new BinaryHeap();
    protected final List<Node> closedSet = new ArrayList<>();

    public PlayerPathFinder(PlayerNodeEvaluator nE, int i) {
        nodeEvaluator = nE;
        maxVisitedNodes = i;
    }

    /**
     * Finds a path to one of the specified positions and post-processes it or returns null if no path could be found within given accuracy
     */
    @Nullable
    public PlayerPath findPath(PathNavigationRegion region, Player player, Set<BlockPos> targetPositions, float maxRange, int accuracy, float searchDepthMultiplier) {
        openSet.clear();
        closedSet.clear();
        nodeEvaluator.prepare(region, player);
        Node node = nodeEvaluator.getStart();
        Map<Target, BlockPos> map = targetPositions.stream().collect(Collectors.toMap(blockPos -> nodeEvaluator.getGoal(blockPos.getX(), blockPos.getY(), blockPos.getZ()), Function.identity()));
        PlayerPath path = findPath(node, map, maxRange, accuracy, searchDepthMultiplier);
        nodeEvaluator.done();
        if (path != null) path.setDebug(openSet.getHeap(), closedSet.toArray(Node[]::new), map.keySet());
        return path;
    }

    @Nullable
    protected PlayerPath findPath(Node node, Map<Target, BlockPos> targetPos, float maxRange, int accuracy, float searchDepthMultiplier) {
        Set<Target> set = targetPos.keySet();
        node.g = 0.0f;
        node.f = node.h = getBestH(node, set);
        openSet.clear();
        openSet.insert(node);
        int i = 1;
        Set<Target> set3 = Sets.newHashSetWithExpectedSize(set.size());
        int j = (int)(maxVisitedNodes * searchDepthMultiplier);
        while (!openSet.isEmpty() && i < j) {
            Node node2 = openSet.pop();
            closedSet.add(node2);
            System.out.println(node2 + " f: " + node2.f + " g: " + node2.g + " h: " + node2.h);
            node2.closed = true;
            for (Target target2 : set) {
                if (node2.distanceManhattan(target2) > accuracy) continue;
                target2.setReached();
                set3.add(target2);
            }
            if (!set3.isEmpty()) break;
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
                node3.h = getBestH(node3, set) * FUDGING;
                if (node3.inOpenSet()) {
                    openSet.changeCost(node3, node3.g + node3.h);
                    continue;
                }
                node3.f = node3.g + node3.h;
                System.out.println("Adding " + node3 + " f: " + node3.f + " g: " + node3.g + " h: " + node3.h);
                openSet.insert(node3);
            }
            i++;
        }
        Optional<PlayerPath> optional = !set3.isEmpty() ? set3.stream().map(target -> reconstructPath(target.getBestNode(), targetPos.get(target), true)).min(Comparator.comparingInt(PlayerPath::getNodeCount)) : set.stream().map(target -> reconstructPath(target.getBestNode(), targetPos.get(target), false)).min(Comparator.comparingDouble(PlayerPath::getDistToTarget).thenComparingInt(PlayerPath::getNodeCount));
        return optional.orElse(null);
    }

    float getBestH(Node node, Set<Target> targets) {
        float f = Float.MAX_VALUE;
        for (Target target : targets) {
            float g = node.distanceTo(target);
            target.updateBest(g, node);
            f = Math.min(g, f);
        }
        return f;
    }

    /**
     * Converts a recursive path point structure into a path
     */
    PlayerPath reconstructPath(Node point, BlockPos targetPos, boolean reachesTarget) {
        ArrayList<Node> list = Lists.newArrayList();
        Node node = point;
        list.add(0, node);
        while (node.cameFrom != null) {
            node = node.cameFrom;
            list.add(0, node);
        }
        return new PlayerPath(list, targetPos, reachesTarget);
    }
}
