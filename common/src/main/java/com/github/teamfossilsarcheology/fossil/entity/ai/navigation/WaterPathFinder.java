package com.github.teamfossilsarcheology.fossil.entity.ai.navigation;

import net.minecraft.world.entity.Mob;
import net.minecraft.world.level.pathfinder.Node;
import net.minecraft.world.level.pathfinder.NodeEvaluator;
import net.minecraft.world.level.pathfinder.Target;

import java.util.Set;

public class WaterPathFinder extends PrehistoricPathFinder {
    private static final int FACE = 10;
    private static final int EDGE = 14;
    private static final int CORNER = 17;

    public WaterPathFinder(NodeEvaluator nodeEvaluator, int maxVisitedNodes, Mob mob) {
        super(nodeEvaluator, maxVisitedNodes, mob);
    }

    @Override
    protected float getBestH(Node node, Set<Target> targets, boolean fudge) {
        float f = Float.MAX_VALUE;
        for (Target target : targets) {
            float g = dist(node, target);
            target.updateBest(g, node);
            f = Math.min(g, f);
        }
        return f;
    }

    /*
     * Calculations from here https://gamedev.stackexchange.com/a/185692. I think it's a bit more greedy but also seems
     * to work better underwater. TODO: Maybe try different costs for nodes on land?
     */
    public float dist(Node f, Node l) {
        float xDiff = Math.abs(l.x - f.x);
        float yDiff = Math.abs(l.y - f.y);
        float zDiff = Math.abs(l.z - f.z);
        float min = Math.min(Math.min(xDiff, yDiff), zDiff);
        float max = Math.max(Math.max(xDiff, yDiff), zDiff);
        float tripleAxis = min;
        float doubleAxis = xDiff + yDiff + zDiff - max - 2 * min;
        float singleAxis = max - doubleAxis - tripleAxis;
        return FACE * singleAxis + EDGE * doubleAxis + CORNER * tripleAxis;
    }
}
