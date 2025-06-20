package com.github.teamfossilsarcheology.fossil.client.gui.debug.navigation;

import com.google.common.collect.Maps;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.PathNavigationRegion;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.level.pathfinder.Node;
import net.minecraft.world.level.pathfinder.Target;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.EnumMap;

public class AmphNodeEvaluator extends PlayerNodeEvaluator {
    @Override
    public void prepare(PathNavigationRegion level, Player p) {
        super.prepare(level, p);
        PathingDebug.setPathfindingMalus(BlockPathTypes.WATER, 0);
    }

    @Override
    public Node getStart() {
        AABB aABB = PathingRenderer.getBigHitbox().move(Vec3.atBottomCenterOf(PathingDebug.pos1));
        return super.getNode(Mth.floor(aABB.minX), Mth.floor(aABB.minY + 0.5), Mth.floor(aABB.minZ));
    }

    @Override
    public @NotNull Target getGoal(double x, double y, double z) {
        return new Target(super.getNode(Mth.floor(x), Mth.floor(y + 0.5), Mth.floor(z)));
    }

    @Override
    @Nullable
    protected Node getNode(int x, int y, int z) {
        if (isAmphibious()) {
            return super.getNode(x, y, z);
        }
        float f;
        Node node = null;
        BlockPathTypes blockPathTypes = getCachedBlockType(player, x, y, z);
        if (blockPathTypes == BlockPathTypes.WATER && (f = PathingDebug.getPathfindingMalus(blockPathTypes)) >= 0.0f) {
            node = super.getNode(x, y, z);
            node.type = blockPathTypes;
            node.costMalus = Math.max(node.costMalus, f);
            if (this.level.getFluidState(new BlockPos(x, y, z)).isEmpty()) {
                node.costMalus += 8.0f;
            }
        }
        return node;
    }

    @Override
    public int getNeighbors(Node[] nodes, Node node) {
        int x = node.x;
        int y = node.y;
        int z = node.z;
        BlockPathTypes type = getCachedBlockType(player, x, y, z);
        int i;
        if (type == BlockPathTypes.WATER || type == BlockPathTypes.WATER_BORDER) {
            //SwimNodeEvaluator code
            i = 0;
            EnumMap<Direction, Node> map = Maps.newEnumMap(Direction.class);
            for (Direction direction : Direction.values()) {
                Node faceNode = getNode(x + direction.getStepX(), y + direction.getStepY(), z + direction.getStepZ());
                map.put(direction, faceNode);
                if (!isNodeValid(faceNode)) continue;
                nodes[i++] = faceNode;
            }
            for (Direction direction : Direction.Plane.HORIZONTAL) {
                Direction direction2 = direction.getClockWise();
                Node diagonalNode = getNode(x + direction.getStepX() + direction2.getStepX(), y, z + direction.getStepZ() + direction2.getStepZ());
                if (!isDiagonalNodeValid(diagonalNode, map.get(direction), map.get(direction2))) continue;
                nodes[i++] = diagonalNode;
            }
        } else {
            //AmphibiousNodeEvaluator code
            i = super.getNeighbors(nodes, node);
            BlockPathTypes typeAbove = getCachedBlockType(player, x, y + 1, z);
            int j = PathingDebug.getPathfindingMalus(type) > 0 && typeAbove != BlockPathTypes.STICKY_HONEY ? Mth.floor(Math.max(1, 1)) : 0;
            double floorLevel = getFloorLevel(new BlockPos(x, y, z));
            Node nodeAbove = findAcceptedNode(x, y + 1, z, Math.max(0, j - 1), floorLevel, Direction.UP, type);
            Node nodeBelow = findAcceptedNode(x, y - 1, z, j, floorLevel, Direction.DOWN, type);
            if (isNeighborValid(nodeAbove, node)) {
                nodes[i++] = nodeAbove;
            }
            if (isNeighborValid(nodeBelow, node) && type != BlockPathTypes.TRAPDOOR) {
                nodes[i++] = nodeBelow;
            }
        }
        return i;
    }

    protected boolean isNodeValid(@Nullable Node node) {
        return node != null && !node.closed && getBlockPathType(level, node.x, node.y, node.z) != BlockPathTypes.BLOCKED;
    }

    protected boolean isDiagonalNodeValid(@Nullable Node diagonal, @Nullable Node node1, @Nullable Node node2) {
        return isNodeValid(diagonal) && node1 != null && node1.costMalus >= 0 && node2 != null && node2.costMalus >= 0;
    }

    protected boolean isVerticalDiagonalNodeValid(@Nullable Node diagonal, @Nullable Node node1, @Nullable Node node2) {
        return isNodeValid(diagonal) && node1 != null && node1.costMalus >= 0 && node2 != null && node2.costMalus >= 0;
    }

    @Override
    protected boolean isAmphibious() {
        return true;//Set to false to test purely aquatic mobs
    }

    @Override
    public @NotNull BlockPathTypes getBlockPathType(BlockGetter level, int x, int y, int z) {
        BlockPos.MutableBlockPos mutableBlockPos = new BlockPos.MutableBlockPos();
        BlockPathTypes type = getBlockPathTypeRaw(level, mutableBlockPos.set(x, y, z));
        if (type == BlockPathTypes.WATER) {
            for (Direction direction : Direction.Plane.HORIZONTAL) {
                BlockPathTypes neighbourType = getBlockPathTypeRaw(level, mutableBlockPos.set(x, y, z).move(direction));
                if (neighbourType != BlockPathTypes.BLOCKED) continue;
                return BlockPathTypes.WATER_BORDER;
            }
            return BlockPathTypes.WATER;
        }
        if (!isAmphibious()) {
            return BlockPathTypes.BLOCKED;
        }
        return getBlockPathTypeStatic(level, mutableBlockPos);
    }
}
