package com.github.teamfossilsarcheology.fossil.entity.ai.navigation;

import it.unimi.dsi.fastutil.longs.Long2ObjectMap;
import it.unimi.dsi.fastutil.longs.Long2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.objects.Object2BooleanFunction;
import it.unimi.dsi.fastutil.objects.Object2BooleanMap;
import it.unimi.dsi.fastutil.objects.Object2BooleanOpenHashMap;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.pathfinder.*;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.EnumSet;
import java.util.Set;

/**
 * WalkNodeEvaluator but for the sea floor
 */
public class TrilobiteNodeEvaluator extends NodeEvaluator {
    private final Long2ObjectMap<BlockPathTypes> pathTypesByPosCache = new Long2ObjectOpenHashMap<>();
    private final Object2BooleanMap<AABB> collisionCache = new Object2BooleanOpenHashMap<>();

    @Override
    public @NotNull Node getStart() {
        BlockPos.MutableBlockPos mutableBlockPos = mob.blockPosition().mutable();
        int i = mob.getBlockY();
        if (mob.isInWater()) {
            while (mutableBlockPos.getY() > level.getMinBuildHeight() && level.getBlockState(mutableBlockPos).isPathfindable(level, mutableBlockPos, PathComputationType.WATER)) {
                mutableBlockPos.move(0, -1, 0);
            }
            i = mutableBlockPos.above().getY();
        } else {
            Node node = getNode(mutableBlockPos.getX(), i, mutableBlockPos.getZ());
            node.type = getBlockPathType(mob, node.asBlockPos());
            node.costMalus = mob.getPathfindingMalus(node.type);
            return node;
        }
        BlockPos blockPos = mob.blockPosition();
        BlockPathTypes blockPathTypes = getCachedBlockType(mob, blockPos.getX(), i, blockPos.getZ());
        if (mob.getPathfindingMalus(blockPathTypes) < 0.0F) {
            AABB aABB = mob.getBoundingBox();
            if (hasPositiveMalus(mutableBlockPos.set(aABB.minX, i, aABB.minZ))
                    || hasPositiveMalus(mutableBlockPos.set(aABB.minX, i, aABB.maxZ))
                    || hasPositiveMalus(mutableBlockPos.set(aABB.maxX, i, aABB.minZ))
                    || hasPositiveMalus(mutableBlockPos.set(aABB.maxX, i, aABB.maxZ))) {
                Node node = getNode(mutableBlockPos);
                node.type = getBlockPathType(mob, node.asBlockPos());
                node.costMalus = mob.getPathfindingMalus(node.type);
                return node;
            }
        }

        Node node = getNode(blockPos.getX(), i, blockPos.getZ());
        node.type = getBlockPathType(mob, node.asBlockPos());
        node.costMalus = mob.getPathfindingMalus(node.type);
        return node;
    }

    public final boolean hasPositiveMalus(BlockPos pos) {
        BlockPathTypes blockPathTypes = getBlockPathType(mob, pos);
        return mob.getPathfindingMalus(blockPathTypes) >= 0.0F;
    }

    @Override
    public @NotNull Target getGoal(double x, double y, double z) {
        return new Target(this.getNode(Mth.floor(x), Mth.floor(y), Mth.floor(z)));
    }

    @Override
    public int getNeighbors(Node[] nodes, Node node) {
        int i = 0;
        int j = 0;
        BlockPathTypes blockPathTypes = getCachedBlockType(mob, node.x, node.y + 1, node.z);
        BlockPathTypes blockPathTypes2 = getCachedBlockType(mob, node.x, node.y, node.z);
        if (mob.getPathfindingMalus(blockPathTypes) >= 0.0F && blockPathTypes2 != BlockPathTypes.STICKY_HONEY) {
            j = Mth.floor(Math.max(1.0F, mob.maxUpStep()));
        }

        double d = getFloorLevel(new BlockPos(node.x, node.y, node.z));
        Node node2 = findAcceptedNode(node.x, node.y, node.z + 1, j, d, Direction.SOUTH, blockPathTypes2);
        if (isNeighborValid(node2, node)) {
            nodes[i++] = node2;
        }

        Node node3 = findAcceptedNode(node.x - 1, node.y, node.z, j, d, Direction.WEST, blockPathTypes2);
        if (isNeighborValid(node3, node)) {
            nodes[i++] = node3;
        }

        Node node4 = findAcceptedNode(node.x + 1, node.y, node.z, j, d, Direction.EAST, blockPathTypes2);
        if (isNeighborValid(node4, node)) {
            nodes[i++] = node4;
        }

        Node node5 = findAcceptedNode(node.x, node.y, node.z - 1, j, d, Direction.NORTH, blockPathTypes2);
        if (isNeighborValid(node5, node)) {
            nodes[i++] = node5;
        }

        Node node6 = findAcceptedNode(node.x - 1, node.y, node.z - 1, j, d, Direction.NORTH, blockPathTypes2);
        if (isDiagonalValid(node, node3, node5, node6)) {
            nodes[i++] = node6;
        }

        Node node7 = findAcceptedNode(node.x + 1, node.y, node.z - 1, j, d, Direction.NORTH, blockPathTypes2);
        if (isDiagonalValid(node, node4, node5, node7)) {
            nodes[i++] = node7;
        }

        Node node8 = findAcceptedNode(node.x - 1, node.y, node.z + 1, j, d, Direction.SOUTH, blockPathTypes2);
        if (isDiagonalValid(node, node3, node2, node8)) {
            nodes[i++] = node8;
        }

        Node node9 = findAcceptedNode(node.x + 1, node.y, node.z + 1, j, d, Direction.SOUTH, blockPathTypes2);
        if (isDiagonalValid(node, node4, node2, node9)) {
            nodes[i++] = node9;
        }

        return i;
    }

    protected boolean isNeighborValid(@Nullable Node node, Node node2) {
        return node != null && !node.closed && (node.costMalus >= 0.0F || node2.costMalus < 0.0F);
    }

    protected boolean isDiagonalValid(Node node, @Nullable Node node2, @Nullable Node node3, @Nullable Node node4) {
        if (node4 == null || node3 == null || node2 == null) {
            return false;
        } else if (node4.closed) {
            return false;
        } else if (node3.y > node.y || node2.y > node.y) {
            return false;
        } else {
            boolean bl = node3.type == BlockPathTypes.FENCE && node2.type == BlockPathTypes.FENCE && mob.getBbWidth() < 0.5;
            return node4.costMalus >= 0.0F && (node3.y < node.y || node3.costMalus >= 0.0F || bl) && (node2.y < node.y || node2.costMalus >= 0.0F || bl);
        }
    }

    protected double getFloorLevel(BlockPos pos) {
        return getFloorLevel(level, pos);
    }

    public static double getFloorLevel(BlockGetter level, BlockPos pos) {
        BlockPos blockPos = pos.below();
        VoxelShape voxelShape = level.getBlockState(blockPos).getCollisionShape(level, blockPos);
        return blockPos.getY() + (voxelShape.isEmpty() ? 0 : voxelShape.max(Direction.Axis.Y));
    }

    @Nullable
    protected Node findAcceptedNode(int x, int y, int z, int i, double d, Direction direction, BlockPathTypes original) {
        Node node = null;
        BlockPos.MutableBlockPos mutableBlockPos = new BlockPos.MutableBlockPos();
        double e = getFloorLevel(mutableBlockPos.set(x, y, z));
        if (e - d > 1.125) {
            return null;
        } else {
            BlockPathTypes type = getCachedBlockType(mob, x, y, z);
            float f = mob.getPathfindingMalus(type);
            double g = mob.getBbWidth() / 2.0;
            if (f >= 0.0F) {
                node = getNode(x, y, z);
                node.type = type;
                node.costMalus = Math.max(node.costMalus, f);
            }

            if (type != BlockPathTypes.WALKABLE) {
                if ((node == null || node.costMalus < 0.0F) && i > 0) {
                    node = findAcceptedNode(x, y + 1, z, i - 1, d, direction, original);
                    if (node != null && (node.type == BlockPathTypes.OPEN || node.type == BlockPathTypes.WALKABLE) && mob.getBbWidth() < 1.0F) {
                        double h = (x - direction.getStepX()) + 0.5;
                        double j = (z - direction.getStepZ()) + 0.5;
                        AABB aABB = new AABB(
                                h - g,
                                getFloorLevel(level, mutableBlockPos.set(h, (y + 1), j)) + 0.001,
                                j - g,
                                h + g,
                                mob.getBbHeight() + getFloorLevel(level, mutableBlockPos.set(node.x, node.y, node.z)) - 0.002,
                                j + g
                        );
                        if (hasCollisions(aABB)) {
                            node = null;
                        }
                    }
                }

                if (type == BlockPathTypes.WATER) {
                    if (this.getCachedBlockType(this.mob, x, y - 1, z) != BlockPathTypes.WATER) {
                        return node;
                    }

                    while (y > this.mob.level().getMinBuildHeight()) {
                        type = this.getCachedBlockType(this.mob, x, --y, z);
                        if (type != BlockPathTypes.WATER) {
                            return node;
                        }

                        node = this.getNode(x, y, z);
                        node.type = type;
                        node.costMalus = Math.max(node.costMalus, this.mob.getPathfindingMalus(type));
                    }
                }

                if (type == BlockPathTypes.OPEN) {
                    int k = 0;
                    int l = y;

                    while (type == BlockPathTypes.OPEN) {
                        if (--y < this.mob.level().getMinBuildHeight()) {
                            Node node2 = this.getNode(x, l, z);
                            node2.type = BlockPathTypes.BLOCKED;
                            node2.costMalus = -1.0F;
                            return node2;
                        }

                        if (k++ >= this.mob.getMaxFallDistance()) {
                            Node node2 = this.getNode(x, y, z);
                            node2.type = BlockPathTypes.BLOCKED;
                            node2.costMalus = -1.0F;
                            return node2;
                        }

                        type = this.getCachedBlockType(this.mob, x, y, z);
                        f = this.mob.getPathfindingMalus(type);
                        if (type != BlockPathTypes.OPEN && f >= 0.0F) {
                            node = this.getNode(x, y, z);
                            node.type = type;
                            node.costMalus = Math.max(node.costMalus, f);
                            break;
                        }

                        if (f < 0.0F) {
                            Node node2 = this.getNode(x, y, z);
                            node2.type = BlockPathTypes.BLOCKED;
                            node2.costMalus = -1.0F;
                            return node2;
                        }
                    }
                }
            }
            return node;
        }
    }

    private boolean hasCollisions(AABB aABB) {
        return collisionCache.computeIfAbsent(aABB, (Object2BooleanFunction<? super AABB>) (object -> !level.noCollision(mob, aABB)));
    }

    private BlockPathTypes getBlockPathType(Mob entityliving, BlockPos pos) {
        return getCachedBlockType(entityliving, pos.getX(), pos.getY(), pos.getZ());
    }

    /**
     * Returns a cached path node type for specified position or calculates it
     */
    protected BlockPathTypes getCachedBlockType(Mob entity, int x, int y, int z) {
        return pathTypesByPosCache.computeIfAbsent(BlockPos.asLong(x, y, z), l -> getBlockPathType(level, x, y, z, entity));
    }

    @Override
    public @NotNull BlockPathTypes getBlockPathType(BlockGetter blockaccess, int x, int y, int z, Mob entityliving) {
        EnumSet<BlockPathTypes> enumSet = EnumSet.noneOf(BlockPathTypes.class);
        BlockPathTypes blockPathTypes = BlockPathTypes.BLOCKED;
        blockPathTypes = getBlockPathTypes(blockaccess, x, y, z, enumSet, blockPathTypes);
        BlockPathTypes result = BlockPathTypes.BLOCKED;

        for (BlockPathTypes tested : enumSet) {
            if (entityliving.getPathfindingMalus(tested) < 0) {
                return tested;
            }

            if (entityliving.getPathfindingMalus(tested) >= entityliving.getPathfindingMalus(result)) {
                result = tested;
            }
        }

        return blockPathTypes == BlockPathTypes.OPEN && entityliving.getPathfindingMalus(result) == 0 && entityWidth <= 1 ? BlockPathTypes.OPEN : result;
    }

    /**
     * Populates the nodeTypeEnum with all the surrounding node types and returns the center one
     */
    public BlockPathTypes getBlockPathTypes(BlockGetter level, int x, int y, int z, Set<BlockPathTypes> nodeTypeEnum, BlockPathTypes nodeType) {
        for (int i = 0; i < entityWidth; i++) {
            for (int j = 0; j < entityHeight; j++) {
                for (int k = 0; k < entityDepth; k++) {
                    int l = i + x;
                    int m = j + y;
                    int n = k + z;
                    BlockPathTypes blockPathTypes = getBlockPathType(level, l, m, n);
                    if (i == 0 && j == 0 && k == 0) {
                        nodeType = blockPathTypes;
                    }

                    nodeTypeEnum.add(blockPathTypes);
                }
            }
        }

        return nodeType;
    }

    @Override
    public @NotNull BlockPathTypes getBlockPathType(BlockGetter level, int x, int y, int z) {
        BlockPos.MutableBlockPos pos = new BlockPos.MutableBlockPos(x, y, z);
        int i = pos.getX();
        int j = pos.getY();
        int k = pos.getZ();
        BlockPathTypes type = getBlockPathTypeRaw(level, pos);
        if (type == BlockPathTypes.OPEN && j >= level.getMinBuildHeight() + 1) {
            BlockPathTypes below = getBlockPathTypeRaw(level, pos.set(i, j - 1, k));
            if (below == BlockPathTypes.DAMAGE_FIRE) {
                type = BlockPathTypes.DAMAGE_FIRE;
            } else if (below != BlockPathTypes.OPEN) {
                type = BlockPathTypes.WALKABLE;
            }
        }
        return type;
    }

    protected static BlockPathTypes getBlockPathTypeRaw(BlockGetter level, BlockPos pos) {
        FluidState fluidState = level.getFluidState(pos);
        if (fluidState.is(FluidTags.WATER)) {
            return BlockPathTypes.OPEN;
        }
        BlockState blockstate = level.getBlockState(pos);
        if (blockstate.is(Blocks.MAGMA_BLOCK)) {
            return BlockPathTypes.DAMAGE_FIRE;
        }
        return BlockPathTypes.BLOCKED;
    }
}
