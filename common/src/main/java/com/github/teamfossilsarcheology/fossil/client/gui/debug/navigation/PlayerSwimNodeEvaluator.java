package com.github.teamfossilsarcheology.fossil.client.gui.debug.navigation;

import com.google.common.collect.Maps;
import it.unimi.dsi.fastutil.longs.Long2ObjectMap;
import it.unimi.dsi.fastutil.longs.Long2ObjectOpenHashMap;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.PathNavigationRegion;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.level.pathfinder.Node;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.level.pathfinder.Target;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

import java.util.EnumMap;

public class PlayerSwimNodeEvaluator extends PlayerNodeEvaluator {
    private final Long2ObjectMap<BlockPathTypes> pathTypesByPosCache = new Long2ObjectOpenHashMap<>();
    @Override
    public void prepare(PathNavigationRegion level, Player p) {
        super.prepare(level, p);
        this.pathTypesByPosCache.clear();
        PathingDebug.setPathfindingMalus(BlockPathTypes.WATER, 0);
    }

    @Override
    public void done() {
        super.done();
        this.pathTypesByPosCache.clear();
    }

    @Override
    public Node getStart() {
        AABB aABB = PathingRenderer.getBigHitbox().move(Vec3.atBottomCenterOf(PathingDebug.pos1));
        return super.getNode(Mth.floor(aABB.minX), Mth.floor(aABB.minY + 0.5), Mth.floor(aABB.minZ));
    }

    @Override
    public int getNeighbors(Node[] nodes, Node node) {
        int i = 0;
        EnumMap<Direction, Node> map = Maps.newEnumMap(Direction.class);
        for (Direction direction : Direction.values()) {
            Node node2 = this.getNode(node.x + direction.getStepX(), node.y + direction.getStepY(), node.z + direction.getStepZ());
            map.put(direction, node2);
            if (!this.isNodeValid(node2)) continue;
            nodes[i++] = node2;
        }
        for (Direction direction2 : Direction.Plane.HORIZONTAL) {
            Direction direction3 = direction2.getClockWise();
            Node node3 = this.getNode(node.x + direction2.getStepX() + direction3.getStepX(), node.y, node.z + direction2.getStepZ() + direction3.getStepZ());
            if (!this.isDiagonalNodeValid(node3, map.get(direction2), map.get(direction3))) continue;
            nodes[i++] = node3;
        }
        return i;
    }

    protected boolean isNodeValid(@Nullable Node node) {
        return node != null && !node.closed;
    }

    protected boolean isDiagonalNodeValid(@Nullable Node node, @Nullable Node node2, @Nullable Node node3) {
        return this.isNodeValid(node) && node2 != null && node2.costMalus >= 0.0f && node3 != null && node3.costMalus >= 0.0f;
    }

    @Override
    @Nullable
    protected Node getNode(int x, int y, int z) {
        float f;
        Node node = null;
        BlockPathTypes blockPathTypes = this.getCachedBlockType(x, y, z);
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
    public Target getGoal(double x, double y, double z) {
        return new Target(super.getNode(Mth.floor(x), Mth.floor(y), Mth.floor(z)));
    }

    protected BlockPathTypes getCachedBlockType(int x, int y, int z) {
        return this.pathTypesByPosCache.computeIfAbsent(BlockPos.asLong(x, y, z), l -> this.getBlockPathType(this.level, x, y, z));
    }

    @Override
    public BlockPathTypes getBlockPathType(BlockGetter level, int x, int y, int z) {
        return this.getBlockPathType(level, x, y, z, player, this.entityWidth, this.entityHeight, this.entityDepth, this.canOpenDoors(), this.canPassDoors());
    }

    @Override
    public BlockPathTypes getBlockPathType(BlockGetter blockaccess, int x, int y, int z, Player entityliving, int xSize, int ySize, int zSize, boolean canBreakDoors, boolean canEnterDoors) {
        BlockPos.MutableBlockPos mutableBlockPos = new BlockPos.MutableBlockPos();
        for (int i = x; i < x + xSize; ++i) {
            for (int j = y; j < y + ySize; ++j) {
                for (int k = z; k < z + zSize; ++k) {
                    FluidState fluidState = blockaccess.getFluidState(mutableBlockPos.set(i, j, k));
                    BlockState blockState = blockaccess.getBlockState(mutableBlockPos.set(i, j, k));
                    if (fluidState.isEmpty() && blockState.isPathfindable(blockaccess, mutableBlockPos.below(), PathComputationType.WATER) && blockState.isAir()) {
                        return BlockPathTypes.BREACH;
                    }
                    if (fluidState.is(FluidTags.WATER)) continue;
                    return BlockPathTypes.BLOCKED;
                }
            }
        }
        BlockState blockState2 = blockaccess.getBlockState(mutableBlockPos);
        if (blockState2.isPathfindable(blockaccess, mutableBlockPos, PathComputationType.WATER)) {
            return BlockPathTypes.WATER;
        }
        return BlockPathTypes.BLOCKED;
    }
}
