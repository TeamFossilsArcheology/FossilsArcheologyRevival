package com.github.teamfossilsarcheology.fossil.entity.ai.navigation;

import com.github.teamfossilsarcheology.fossil.client.gui.debug.navigation.PathingDebug;
import com.github.teamfossilsarcheology.fossil.entity.prehistoric.base.SwimmingAnimal;
import com.google.common.collect.Maps;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.Mth;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.*;
import net.minecraft.world.phys.AABB;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.EnumMap;

public class PrehistoricAmphibiousNodeEvaluator extends WalkNodeEvaluator {

    @Override
    public @NotNull Node getStart() {
        if (mob.isInWater() || (mob instanceof SwimmingAnimal swimmingAnimal && !swimmingAnimal.isAmphibious())) {
            return super.getNode(Mth.floor(mob.getBoundingBox().minX), Mth.floor(mob.getBoundingBox().minY + 0.5), Mth.floor(mob.getBoundingBox().minZ));
        }
        BlockPos.MutableBlockPos mutableBlockPos = new BlockPos.MutableBlockPos();
        int i = mob.getBlockY();
        BlockState blockState = level.getBlockState(mutableBlockPos.set(mob.getX(), i, mob.getZ()));
        if (!mob.canStandOnFluid(blockState.getFluidState())) {
            if (this.mob.onGround()) {
                i = Mth.floor(mob.getY() + 0.5);
            } else {
                BlockPos blockPos = mob.blockPosition();

                while ((level.getBlockState(blockPos).isAir() || level.getBlockState(blockPos).isPathfindable(level, blockPos, PathComputationType.LAND))
                        && blockPos.getY() > mob.level().getMinBuildHeight()) {
                    blockPos = blockPos.below();
                }

                i = blockPos.above().getY();
            }
        } else {
            while (mob.canStandOnFluid(blockState.getFluidState())) {
                blockState = level.getBlockState(mutableBlockPos.set(mob.getX(), ++i, mob.getZ()));
            }
            i--;
        }

        BlockPos blockPos = mob.blockPosition();
        BlockPathTypes blockPathTypes = getCachedBlockType(mob, blockPos.getX(), i, blockPos.getZ());
        if (mob.getPathfindingMalus(blockPathTypes) < 0.0F) {
            AABB aABB = mob.getBoundingBox();
            if (canStartAt(mutableBlockPos.set(aABB.minX, i, aABB.minZ))
                    || canStartAt(mutableBlockPos.set(aABB.minX, i, aABB.maxZ))
                    || canStartAt(mutableBlockPos.set(aABB.maxX, i, aABB.minZ))
                    || canStartAt(mutableBlockPos.set(aABB.maxX, i, aABB.maxZ))) {
                Node node = getNode(mutableBlockPos);
                BlockPos nodePos = node.asBlockPos();
                node.type = getCachedBlockType(mob, nodePos.getX(), nodePos.getY(), nodePos.getZ());
                node.costMalus = mob.getPathfindingMalus(node.type);
                return node;
            }
        }

        Node node2 = getNode(blockPos.getX(), i, blockPos.getZ());
        BlockPos node2Pos = node2.asBlockPos();
        node2.type = getCachedBlockType(mob, node2Pos.getX(), node2Pos.getY(), node2Pos.getZ());
        node2.costMalus = mob.getPathfindingMalus(node2.type);
        return node2;
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
        BlockPathTypes blockPathTypes = getCachedBlockType(mob, x, y, z);
        if ((blockPathTypes == BlockPathTypes.WATER || blockPathTypes == BlockPathTypes.WATER_BORDER) && (f = PathingDebug.getPathfindingMalus(blockPathTypes)) >= 0.0f) {
            node = super.getNode(x, y, z);
            node.type = blockPathTypes;
            node.costMalus = Math.max(node.costMalus, f);
            if (level.getFluidState(new BlockPos(x, y, z)).isEmpty()) {
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
        BlockPathTypes type = getCachedBlockType(mob, x, y, z);
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
            BlockPathTypes typeAbove = getCachedBlockType(mob, x, y + 1, z);
            int j = mob.getPathfindingMalus(type) > 0 && typeAbove != BlockPathTypes.STICKY_HONEY ? Mth.floor(Math.max(1, mob.maxUpStep())) : 0;
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
        if (mob instanceof SwimmingAnimal swimmingAnimal) {
            return swimmingAnimal.isAmphibious();
        }
        return true;
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
