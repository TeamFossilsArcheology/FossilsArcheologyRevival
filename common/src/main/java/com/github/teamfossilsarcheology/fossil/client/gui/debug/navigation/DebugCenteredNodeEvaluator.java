package com.github.teamfossilsarcheology.fossil.client.gui.debug.navigation;

import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.level.pathfinder.Node;
import net.minecraft.world.level.pathfinder.WalkNodeEvaluator;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import java.util.EnumSet;

public class DebugCenteredNodeEvaluator extends PlayerNodeEvaluator {

    /**
     * @implNote This implementation tries the block at the center of the mob as well as the ones checked by {@link WalkNodeEvaluator}
     */
    @Override
    public Node getStart() {
        int i = Mth.floor(PathingDebug.pos1.getY() + 0.5);
        BlockPathTypes blockPathTypes = getCachedBlockType(player, PathingDebug.pos1.getX(), i, PathingDebug.pos1.getZ());
        if (PathingDebug.getPathfindingMalus(blockPathTypes) < 0.0f) {
            AABB aABB = PathingRenderer.getBigHitbox().move(PathingDebug.pos1);
            Vec3 center = aABB.setMinY(i).setMaxY(i).getCenter();
            BlockPos.MutableBlockPos mutableBlockPos = new BlockPos.MutableBlockPos();
            if (hasPositiveMalus(mutableBlockPos.set(center.x, center.y, center.z)) || hasPositiveMalus(
                    mutableBlockPos.set(aABB.minX, i, aABB.maxZ)) || hasPositiveMalus(
                    mutableBlockPos.set(aABB.maxX, i, aABB.minZ)) || hasPositiveMalus(
                    mutableBlockPos.set(aABB.maxX, i, aABB.maxZ))) {
                Node node = getNode(mutableBlockPos);
                BlockPos nodePos = node.asBlockPos();
                node.type = getCachedBlockType(player, nodePos.getX(), nodePos.getY(), nodePos.getZ());
                node.costMalus = PathingDebug.getPathfindingMalus(node.type);
                return node;
            }
        }
        Node node2 = getNode(PathingDebug.pos1.getX(), i, PathingDebug.pos1.getZ());
        BlockPos node2Pos = node2.asBlockPos();
        node2.type = getCachedBlockType(player, node2Pos.getX(), node2Pos.getY(), node2Pos.getZ());
        node2.costMalus = PathingDebug.getPathfindingMalus(node2.type);
        return node2;
    }

    /**
     * @implNote This implementation checks neighbours around the target instead of shifted to a positive x and z
     */
    @Override
    public BlockPathTypes getBlockPathTypes(BlockGetter level, int x, int y, int z, int xSize, int ySize, int zSize, boolean canOpenDoors,
                                            boolean canEnterDoors, EnumSet<BlockPathTypes> nodeTypeEnum, BlockPathTypes nodeType, BlockPos pos) {
        float width = Math.max(0, xSize - 2);
        int widthEachSide = xSize - 1;
        for (int i = -widthEachSide + 1; i < widthEachSide; ++i) {
            for (int j = 0; j < ySize; ++j) {
                for (int k = -widthEachSide + 1; k < widthEachSide; ++k) {
                    BlockPathTypes blockPathType = getBlockPathType(level, x + i, y + j, z + k);
                    blockPathType = evaluateBlockPathType(level, canOpenDoors, canEnterDoors, pos, blockPathType);
                    nodeTypeEnum.add(blockPathType);
                }
            }
        }
        return nodeType;
    }

    @Override
    public int getBlockedNeighbors(Node[] nodes, Node start, BlockPos[] neighborsX) {
        float width = Math.max(0, PathingRenderer.getBbWidth() - 2);
        int widthEachSide = entityWidth - 1;
        double d = getFloorLevel(new BlockPos(start.x, start.y, start.z));
        int count = 0;
        for (int i = -widthEachSide + 1; i < widthEachSide; ++i) {
            for (int j = 0; j < entityHeight; ++j) {
                for (int k = -widthEachSide + 1; k < widthEachSide; ++k) {
                    Node node = findBlockedNode(start.x + i, start.y + j, start.z + k, d);
                    if (node != null) nodes[count++] = node;
                }
            }
        }
        return count;
    }
}
