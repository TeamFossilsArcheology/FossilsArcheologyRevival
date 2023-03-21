package com.fossil.fossil.entity.ai.navigation;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.pathfinder.Node;
import net.minecraft.world.level.pathfinder.Path;
import net.minecraft.world.phys.Vec3;

import java.util.ArrayList;
import java.util.List;

/**
 * Overwrites the vanilla {@link Path} to make entities move in the middle of the path instead of the edges.
 *
 * @see CenteredNodeEvaluator
 */
public class CenteredPath extends Path {
    private boolean stuck;

    public CenteredPath(List<Node> nodes, BlockPos blockPos, boolean bl) {
        super(nodes, blockPos, bl);
    }

    public static CenteredPath createFromPath(Path path) {
        if (path == null) {
            return null;
        }
        List<Node> nodes = new ArrayList<>();
        for (int i = 0; i < path.getNodeCount(); i++) {
            nodes.add(path.getNode(i));
        }
        return new CenteredPath(nodes, path.getTarget(), path.canReach());
    }

    @Override
    public Vec3 getEntityPosAtNode(Entity entity, int index) {
        Node node = getNode(index);
        return new Vec3(node.x + 0.5, node.y, node.z + 0.5);
    }
}
