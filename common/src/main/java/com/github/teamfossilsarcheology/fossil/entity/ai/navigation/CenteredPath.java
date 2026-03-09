package com.github.teamfossilsarcheology.fossil.entity.ai.navigation;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.pathfinder.Node;
import net.minecraft.world.level.pathfinder.Path;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CenteredPath extends Path {

    private final Map<Integer, Vec3> steerCache = new HashMap<>();

    public CenteredPath(List<Node> nodes, BlockPos blockPos, boolean bl) {
        super(nodes, blockPos, bl);
    }

    public static CenteredPath createFromPath(Path path) {
        if (path == null) return null;
        List<Node> nodes = new ArrayList<>();
        for (int i = 0; i < path.getNodeCount(); i++) nodes.add(path.getNode(i));
        return new CenteredPath(nodes, path.getTarget(), path.canReach());
    }

    @Override
    public @NotNull Vec3 getEntityPosAtNode(Entity entity, int index) {
        Node node = getNode(index);
        double baseX = node.x + 0.5;
        double baseZ = node.z + 0.5;
        Vec3 nodeCenter = new Vec3(baseX, node.y, baseZ);

        if (steerCache.containsKey(index)) {
            return steerCache.get(index);
        }

        Vec3 gapCenter = findGapCenter(entity, nodeCenter, index);
        Vec3 result = (gapCenter != null) ? gapCenter : nodeCenter;

        steerCache.put(index, result);
        return result;
    }

    public void invalidateCacheForNode(int index) {
        steerCache.remove(index);
    }

    private Vec3 findGapCenter(Entity entity, Vec3 nodePos, int index) {
        Level level = entity.level();

        Vec3 prevPos;
        if (index > 0) {
            Node prevNode = getNode(index - 1);
            prevPos = new Vec3(prevNode.x + 0.5, prevNode.y, prevNode.z + 0.5);
        } else {
            prevPos = entity.position();
        }

        double toX = nodePos.x - prevPos.x;
        double toZ = nodePos.z - prevPos.z;
        double length = Math.sqrt(toX * toX + toZ * toZ);
        if (length < 1e-6) return null;

        double perpX = -toZ / length;
        double perpZ = toX / length;

        float bbWidth = entity.getBbWidth();
        int height = (int) Math.ceil(entity.getBbHeight());
        float searchRange = bbWidth * 2.0f + 1.0f;

        double leftDist = raycastToWall(level, nodePos.x, nodePos.y, nodePos.z, -perpX, -perpZ, searchRange, height);
        double rightDist = raycastToWall(level, nodePos.x, nodePos.y, nodePos.z, perpX, perpZ, searchRange, height);

        double gapWidth = leftDist + rightDist;

        if (leftDist >= searchRange || rightDist >= searchRange) {
            return null;
        }

        if (gapWidth < bbWidth + 0.01f) {
            return null;
        }

        double midOffset = (rightDist - leftDist) / 2.0;
        double centeredX = nodePos.x + perpX * midOffset;
        double centeredZ = nodePos.z + perpZ * midOffset;
        return new Vec3(centeredX, nodePos.y, centeredZ);
    }

    private double raycastToWall(Level level, double startX, double startY, double startZ,
                                  double dirX, double dirZ, float maxDist, int height) {
        int steps = (int) Math.ceil(maxDist / 0.25);
        for (int i = 1; i <= steps; i++) {
            double dist = i * 0.25;
            double checkX = startX + dirX * dist;
            double checkZ = startZ + dirZ * dist;
            for (int dy = 0; dy < height; dy++) {
                if (level.getBlockState(BlockPos.containing(checkX, startY + dy, checkZ)).isSolid()) {
                    return dist;
                }
            }
        }
        return maxDist;
    }
}