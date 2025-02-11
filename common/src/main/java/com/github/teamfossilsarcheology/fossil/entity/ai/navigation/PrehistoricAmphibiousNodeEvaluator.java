package com.github.teamfossilsarcheology.fossil.entity.ai.navigation;

import com.google.common.collect.Maps;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.Mth;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.pathfinder.*;
import net.minecraft.world.phys.AABB;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.EnumMap;

public class PrehistoricAmphibiousNodeEvaluator extends WalkNodeEvaluator {

    @Override
    public @NotNull Node getStart() {
        if (mob.isInWater()) {
            return getNode(Mth.floor(mob.getBoundingBox().minX), Mth.floor(mob.getBoundingBox().minY + 0.5), Mth.floor(mob.getBoundingBox().minZ));
        }
        BlockPos.MutableBlockPos mutableBlockPos = new BlockPos.MutableBlockPos();
        int i = this.mob.getBlockY();
        BlockState blockState = this.level.getBlockState(mutableBlockPos.set(this.mob.getX(), i, this.mob.getZ()));
        if (!this.mob.canStandOnFluid(blockState.getFluidState())) {
            if (this.canFloat() && this.mob.isInWater()) {
                while (true) {
                    if (!blockState.is(Blocks.WATER) && blockState.getFluidState() != Fluids.WATER.getSource(false)) {
                        i--;
                        break;
                    }

                    blockState = this.level.getBlockState(mutableBlockPos.set(this.mob.getX(), (++i), this.mob.getZ()));
                }
            } else if (this.mob.isOnGround()) {
                i = Mth.floor(this.mob.getY() + 0.5);
            } else {
                BlockPos blockPos = this.mob.blockPosition();

                while (
                        (this.level.getBlockState(blockPos).isAir() || this.level.getBlockState(blockPos).isPathfindable(this.level, blockPos, PathComputationType.LAND))
                                && blockPos.getY() > this.mob.level.getMinBuildHeight()
                ) {
                    blockPos = blockPos.below();
                }

                i = blockPos.above().getY();
            }
        } else {
            while (this.mob.canStandOnFluid(blockState.getFluidState())) {
                blockState = this.level.getBlockState(mutableBlockPos.set(this.mob.getX(), (double)(++i), this.mob.getZ()));
            }
            i--;
        }

        BlockPos blockPos = this.mob.blockPosition();
        BlockPathTypes blockPathTypes = this.getCachedBlockType(this.mob, blockPos.getX(), i, blockPos.getZ());
        if (this.mob.getPathfindingMalus(blockPathTypes) < 0.0F) {
            AABB aABB = this.mob.getBoundingBox();
            if (this.hasPositiveMalus(mutableBlockPos.set(aABB.minX, i, aABB.minZ))
                    || this.hasPositiveMalus(mutableBlockPos.set(aABB.minX, i, aABB.maxZ))
                    || this.hasPositiveMalus(mutableBlockPos.set(aABB.maxX, i, aABB.minZ))
                    || this.hasPositiveMalus(mutableBlockPos.set(aABB.maxX, i, aABB.maxZ))) {
                Node node = this.getNode(mutableBlockPos);
                BlockPos nodePos = node.asBlockPos();
                node.type = this.getCachedBlockType(this.mob, nodePos.getX(), nodePos.getY(), nodePos.getZ());
                node.costMalus = this.mob.getPathfindingMalus(node.type);
                return node;
            }
        }

        Node node2 = this.getNode(blockPos.getX(), i, blockPos.getZ());
        BlockPos node2Pos = node2.asBlockPos();
        node2.type = this.getCachedBlockType(this.mob, node2Pos.getX(), node2Pos.getY(), node2Pos.getZ());
        node2.costMalus = this.mob.getPathfindingMalus(node2.type);
        return node2;
    }

    @Override
    public @NotNull Target getGoal(double x, double y, double z) {
        return new Target(getNode(Mth.floor(x), Mth.floor(y + 0.5), Mth.floor(z)));
    }

    @Override
    public int getNeighbors(Node[] nodes, Node node) {
        int x = node.x;
        int y = node.y;
        int z = node.z;
        BlockPathTypes type = getCachedBlockType(mob, x, y, z);
        if (type == BlockPathTypes.WATER) {
            //SwimNodeEvaluator code
            int i = 0;
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
            return i;
        } else {
            //AmphibiousNodeEvaluator code
            int i = super.getNeighbors(nodes, node);
            BlockPathTypes typeAbove = getCachedBlockType(mob, x, y + 1, z);
            int j = mob.getPathfindingMalus(type) > 0 && typeAbove != BlockPathTypes.STICKY_HONEY ? Mth.floor(Math.max(1, mob.maxUpStep)) : 0;
            double floorLevel = getFloorLevel(new BlockPos(x, y, z));
            Node nodeAbove = findAcceptedNode(x, y + 1, z, Math.max(0, j - 1), floorLevel, Direction.UP, type);
            Node nodeBelow = findAcceptedNode(x, y - 1, z, j, floorLevel, Direction.DOWN, type);
            if (isNeighborValid(nodeAbove, node)) {
                nodes[i++] = nodeAbove;
            }
            if (isNeighborValid(nodeBelow, node) && type != BlockPathTypes.TRAPDOOR) {
                nodes[i++] = nodeBelow;
            }
            return i;
        }
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
        return true;
    }

    @Override
    public @NotNull BlockPathTypes getBlockPathType(BlockGetter level, int x, int y, int z) {
        BlockPos.MutableBlockPos mutableBlockPos = new BlockPos.MutableBlockPos();
        BlockPathTypes type = getBlockPathTypeRaw(level, mutableBlockPos.set(x, y, z));
        if (type == BlockPathTypes.WATER) {
            for (Direction direction : Direction.values()) {
                BlockPathTypes neighbourType = getBlockPathTypeRaw(level, mutableBlockPos.set(x, y, z).move(direction));
                if (neighbourType != BlockPathTypes.BLOCKED) continue;
                return BlockPathTypes.WATER_BORDER;
            }
            return BlockPathTypes.WATER;
        }
        return getBlockPathTypeStatic(level, mutableBlockPos);
    }
}
