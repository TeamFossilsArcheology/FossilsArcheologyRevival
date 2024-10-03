package com.github.teamfossilsarcheology.fossil.client.gui.debug.navigation;

import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.level.pathfinder.Node;
import net.minecraft.world.phys.Vec3;

import java.util.ArrayList;
import java.util.List;

public class DebugLargePath extends PlayerPath {
    public DebugLargePath(List<Node> list, BlockPos blockPos, boolean bl) {
        super(list, blockPos, bl);
    }

    public static DebugLargePath createFromPath(PlayerPath path) {
        if (path == null) {
            return null;
        }
        List<Node> nodes = new ArrayList<>();
        for (int i = 0; i < path.getNodeCount(); i++) {
            nodes.add(path.getNode(i));
        }
        DebugLargePath newPath = new DebugLargePath(nodes, path.getTarget(), path.canReach());
        newPath.setDebug(path.getOpenSet(), path.getClosedSet(), path.targetNodes);
        return newPath;
    }

    @Override
    public Vec3 getEntityPosAtNode(int index) {
        Node node = getNode(index);
        double d0 = node.x + Mth.floor(PathingRenderer.getBbWidth() + 1) * 0.5;
        double d1 = node.y;
        double d2 = node.z + Mth.floor(PathingRenderer.getBbWidth() + 1) * 0.5;
        return new Vec3(d0, d1, d2);
    }
}
