package com.github.teamfossilsarcheology.fossil.client.gui.debug.navigation;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.pathfinder.Node;
import net.minecraft.world.phys.Vec3;

import java.util.ArrayList;
import java.util.List;

public class DebugCenteredPath extends PlayerPath {
    public List<Direction> offsets;
    public DebugCenteredPath(List<Node> list, BlockPos blockPos, boolean bl) {
        super(list, blockPos, bl);
        List<Direction> offsets = new ArrayList<>();
    }

    public static DebugCenteredPath createFromPath(PlayerPath path) {
        if (path == null) {
            return null;
        }
        List<Node> nodes = new ArrayList<>();
        for (int i = 0; i < path.getNodeCount(); i++) {
            nodes.add(path.getNode(i));
        }
        DebugCenteredPath newPath = new DebugCenteredPath(nodes, path.getTarget(), path.canReach());
        newPath.setDebug(path.getOpenSet(), path.getClosedSet(), path.targetNodes);
        return newPath;
    }

    @Override
    public Vec3 getEntityPosAtNode(int index) {
        Node node = getNode(index);
        double offset = PathingRenderer.getBbWidth() - Math.floor(PathingRenderer.getBbWidth());
        return new Vec3(node.x + 0.5 - offset, node.y, node.z + 0.5);
    }
}
