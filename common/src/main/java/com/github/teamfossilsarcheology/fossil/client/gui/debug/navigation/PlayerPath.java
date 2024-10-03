package com.github.teamfossilsarcheology.fossil.client.gui.debug.navigation;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.pathfinder.Node;
import net.minecraft.world.level.pathfinder.Target;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class PlayerPath {

    private Node[] openSet = new Node[0];
    private Node[] closedSet = new Node[0];
    public final List<Node> nodes;
    @Nullable
    public Set<Target> targetNodes;
    private int nextNodeIndex;
    public int sweepNextNodeIndex;
    private final BlockPos target;
    private final float distToTarget;
    private final boolean reached;

    public PlayerPath(List<Node> list, BlockPos blockPos, boolean bl) {
        nodes = list;
        blockPoses = list.stream().map(node -> new BlockPos(node.x, node.y, node.z)).collect(Collectors.toList());
        target = blockPos;
        distToTarget = list.isEmpty() ? Float.MAX_VALUE : nodes.get(nodes.size() - 1).distanceManhattan(target);
        reached = bl;
    }

    public final List<BlockPos> blockPoses;

    public boolean removeForcedNode(BlockPos pos) {
        int idx = blockPoses.indexOf(pos);
        if (idx != -1) {
            nodes.remove(idx);
            blockPoses.remove(idx);
            return true;
        }
        return false;
    }
    public boolean addForcedNode(BlockPos pos) {
        if (!blockPoses.contains(pos)) {
            nodes.add(new Node(pos.getX(), pos.getY(), pos.getZ()));
            blockPoses.add(pos);
            return true;
        }
        return false;
        /*if (blockPoses.contains(pos)) {
            return false;
        }
        if (nodes.isEmpty()) {
            blockPoses.clear();
            nodes.add()
            blockPoses = list.stream().map(node -> new BlockPos(node.x, node.y, node.z)).collect(Collectors.toList());
            return true;
        }
        int closest = 0;
        Node closestPos = nodes.get(0);
        for (int i = 0; i < nodes.size(); i++) {
            if (nodes.get(i).distanceTo(pos) < closestPos.distanceTo(pos)) {
                closestPos = nodes.get(i);
                closest = i;
            }
        }
        int insertIdx = closest + 1;
        if (closest > 0) {
            //if
        } else if (closest == getNodeCount() - 1) {
            //if between insert at getNodeCount() - 2
            //else insert at getNodeCount() - 1
        } else {
            //if between insert at 1
            //else insert at 0
        }

        target = blockPoses.get(blockPoses.size() - 1);
        distToTarget = nodes.get(nodes.size() - 1).distanceManhattan(target);*/
    }


    /**
     * Directs this path to the next point in its array
     */
    public void advance() {
        ++nextNodeIndex;
    }

    public boolean notStarted() {
        return nextNodeIndex <= 0;
    }

    /**
     * Returns true if this path has reached the end
     */
    public boolean isDone() {
        return nextNodeIndex >= nodes.size();
    }

    /**
     * returns the last PathPoint of the Array
     */
    @Nullable
    public Node getEndNode() {
        if (!nodes.isEmpty()) {
            return nodes.get(nodes.size() - 1);
        }
        return null;
    }

    /**
     * return the PathPoint located at the specified PathIndex, usually the current one
     */
    public Node getNode(int index) {
        return nodes.get(index);
    }

    public void truncateNodes(int length) {
        if (nodes.size() > length) {
            nodes.subList(length, nodes.size()).clear();
        }
    }

    public void replaceNode(int index, Node point) {
        nodes.set(index, point);
    }

    public int getNodeCount() {
        return nodes.size();
    }

    public int getNextNodeIndex() {
        return nextNodeIndex;
    }

    public int getDebugNodeIndex() {
        return Math.min(getNodeCount() - 1, PathingRenderer.pathIndex);
    }

    public void setSweepNodeIndex(int currentPathIndex) {
        sweepNextNodeIndex = Math.min(getNodeCount() - 1, currentPathIndex);
    }

    /**
     * Gets the vector of the PathPoint associated with the given index.
     */
    public Vec3 getEntityPosAtNode(int index) {
        Node node = nodes.get(index);
        double d = node.x + ((int) (PathingRenderer.getBbWidth() + 1d)) * 0.5;
        double e = node.y;
        double f = node.z + ((int) (PathingRenderer.getBbWidth() + 1d)) * 0.5;
        return new Vec3(d, e, f);
    }

    public BlockPos getNodePos(int i) {
        return nodes.get(i).asBlockPos();
    }

    public Vec3 getSweepEntityPos(Entity entity) {
        return getEntityPosAtNode(sweepNextNodeIndex);
    }

    /**
     * @return the current {@code PathEntity} target node as a {@code Vec3D}
     */
    public Vec3 getNextEntityPos(Entity entity) {
        return getEntityPosAtNode(Math.min(getNodeCount() - 1, PathingRenderer.pathIndex));
        //return getEntityPosAtNode(entity, nextNodeIndex);
    }

    public BlockPos getNextNodePos() {
        return nodes.get(nextNodeIndex).asBlockPos();
    }

    public Node getNextNode() {
        return nodes.get(nextNodeIndex);
    }

    @Nullable
    public Node getPreviousNode() {
        return nextNodeIndex > 0 ? nodes.get(nextNodeIndex - 1) : null;
    }

    /**
     * Returns true if the EntityPath are the same. Non instance related equals.
     */
    public boolean sameAs(@Nullable PlayerPath pathentity) {
        if (pathentity == null) {
            return false;
        }
        if (pathentity.nodes.size() != nodes.size()) {
            return false;
        }
        for (int i = 0; i < nodes.size(); ++i) {
            Node node = nodes.get(i);
            Node node2 = pathentity.nodes.get(i);
            if (node.x == node2.x && node.y == node2.y && node.z == node2.z) continue;
            return false;
        }
        return true;
    }

    void setDebug(Node[] openSet, Node[] closedSet, Set<Target> targetNodes) {
        this.openSet = openSet;
        this.closedSet = closedSet;
        this.targetNodes = targetNodes;
    }

    public Node[] getOpenSet() {
        return this.openSet;
    }

    public Node[] getClosedSet() {
        return this.closedSet;
    }

    public boolean canReach() {
        return reached;
    }

    public String toString() {
        return "Path(length=" + nodes.size() + ")";
    }

    public BlockPos getTarget() {
        return target;
    }

    public float getDistToTarget() {
        return distToTarget;
    }
}
