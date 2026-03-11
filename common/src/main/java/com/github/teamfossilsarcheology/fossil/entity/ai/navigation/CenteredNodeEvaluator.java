package com.github.teamfossilsarcheology.fossil.entity.ai.navigation;

import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.level.pathfinder.Node;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.level.pathfinder.WalkNodeEvaluator;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import java.util.EnumSet;

/**
 * Overwrites the vanilla {@link WalkNodeEvaluator} to make entities move in the middle of the path instead of the edges.
 * Collision checks are done symmetrically around the node center (±halfWidth on X and Z) so they
 * match where {@link CenteredPath} actually sends the mob (node.x + 0.5, node.z + 0.5).
 *
 * We use mob.getBbWidth() directly instead of vanilla's entityWidth (which is Mth.floor(getBbWidth() + 1))
 * to avoid over-inflating the pathfinding width, which caused the pathfinder to reject gaps the mob
 * can physically walk through.
 *
 * @see CenteredPath
 */
public class CenteredNodeEvaluator extends WalkNodeEvaluator {

    /**
     * @implNote This implementation tries the block at the center of the mob as well as the ones checked by {@link WalkNodeEvaluator}
     */
    @Override
    public Node getStart() {
        BlockPos blockPos;
        BlockPos.MutableBlockPos mutableBlockPos = new BlockPos.MutableBlockPos();
        int i = this.mob.getBlockY();
        BlockState blockState = this.level.getBlockState(mutableBlockPos.set(this.mob.getX(), i, this.mob.getZ()));
        if (this.mob.canStandOnFluid(blockState.getFluidState())) {
            while (this.mob.canStandOnFluid(blockState.getFluidState())) {
                blockState = this.level.getBlockState(mutableBlockPos.set(this.mob.getX(), ++i, this.mob.getZ()));
            }
            --i;
        } else if (this.canFloat() && this.mob.isInWater()) {
            while (blockState.is(Blocks.WATER) || blockState.getFluidState() == Fluids.WATER.getSource(false)) {
                blockState = this.level.getBlockState(mutableBlockPos.set(this.mob.getX(), ++i, this.mob.getZ()));
            }
            --i;
        } else if (this.mob.onGround()) {
            i = Mth.floor(this.mob.getY() + 0.5);
        } else {
            blockPos = this.mob.blockPosition();
            while ((this.level.getBlockState(blockPos).isAir() || this.level.getBlockState(blockPos).isPathfindable(this.level, blockPos,
                    PathComputationType.LAND)) && blockPos.getY() > this.mob.level().getMinBuildHeight()) {
                blockPos = blockPos.below();
            }
            i = blockPos.above().getY();
        }
        blockPos = this.mob.blockPosition();
        BlockPathTypes blockPathTypes = getCachedBlockType(this.mob, blockPos.getX(), i, blockPos.getZ());
        if (this.mob.getPathfindingMalus(blockPathTypes) < 0.0f) {
            AABB aABB = this.mob.getBoundingBox();
            Vec3 center = aABB.setMinY(i).setMaxY(i).getCenter();
            if (canStartAt(mutableBlockPos.set(center.x, center.y, center.z)) || canStartAt(
                    mutableBlockPos.set(aABB.minX, i, aABB.maxZ)) || canStartAt(
                    mutableBlockPos.set(aABB.maxX, i, aABB.minZ)) || canStartAt(
                    mutableBlockPos.set(aABB.maxX, i, aABB.maxZ))) {
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

    /**
     * Uses mob.getBbWidth() directly instead of vanilla's entityWidth (Mth.floor(getBbWidth() + 1))
     * to get accurate collision checks that match the mob's actual size.
     * Checks symmetrically around the node center (±halfWidth on X and Z) so checks align with
     * where CenteredPath walks the mob (node.x + 0.5, node.z + 0.5).
     */
    @Override
    public BlockPathTypes getBlockPathTypes(BlockGetter level, int x, int y, int z, EnumSet<BlockPathTypes> nodeTypeEnum, BlockPathTypes nodeType, BlockPos pos) {
        // Use actual bounding box dimensions instead of vanilla's over-inflated entityWidth.
        // Example: mob with getBbWidth()=2.0 → halfWidth=1 → checks 3x3 (accurate)
        // Vanilla would give entityWidth=3 → halfWidth=2 → checks 5x5 (rejects passable gaps)
        int halfWidth = Mth.ceil(mob.getBbWidth() / 2.0f);
        int height = Mth.ceil(mob.getBbHeight());

        for (int i = -halfWidth; i <= halfWidth; i++) {
            for (int j = 0; j < height; j++) {
                for (int k = -halfWidth; k <= halfWidth; k++) {
                    BlockPathTypes blockPathType = this.getBlockPathType(level, x + i, y + j, z + k);
                    blockPathType = this.evaluateBlockPathType(level, pos, blockPathType);
                    nodeTypeEnum.add(blockPathType);
                    if (i == 0 && j == 0 && k == 0) {
                        nodeType = blockPathType;
                    }
                }
            }
        }
        return nodeType;
    }
}