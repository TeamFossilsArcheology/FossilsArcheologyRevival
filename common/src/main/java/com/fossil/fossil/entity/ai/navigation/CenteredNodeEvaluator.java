package com.fossil.fossil.entity.ai.navigation;

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
 *
 * @see CenteredPath
 */
public class CenteredNodeEvaluator extends WalkNodeEvaluator {

    /**
     * @implNote This implementation tries the block at the center of the mob as well as the ones checked by {@link WalkNodeEvaluator}
     */
    @Override
    public Node getStart() {
        //Unchanged original code
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
        } else if (this.mob.isOnGround()) {
            i = Mth.floor(this.mob.getY() + 0.5);
        } else {
            blockPos = this.mob.blockPosition();
            while ((this.level.getBlockState(blockPos).isAir() || this.level.getBlockState(blockPos).isPathfindable(this.level, blockPos,
                    PathComputationType.LAND)) && blockPos.getY() > this.mob.level.getMinBuildHeight()) {
                blockPos = blockPos.below();
            }
            i = blockPos.above().getY();
        }
        blockPos = this.mob.blockPosition();
        BlockPathTypes blockPathTypes = getCachedBlockType(this.mob, blockPos.getX(), i, blockPos.getZ());
        //Changed code
        if (this.mob.getPathfindingMalus(blockPathTypes) < 0.0f) {
            AABB aABB = this.mob.getBoundingBox();
            Vec3 center = aABB.setMinY(i).setMaxY(i).getCenter();
            if (hasPositiveMalus(mutableBlockPos.set(center.x, center.y, center.z)) || hasPositiveMalus(
                    mutableBlockPos.set(aABB.minX, i, aABB.maxZ)) || hasPositiveMalus(
                    mutableBlockPos.set(aABB.maxX, (double) i, aABB.minZ)) || this.hasPositiveMalus(
                    mutableBlockPos.set(aABB.maxX, (double) i, aABB.maxZ))) {
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
     * @implNote This implementation checks neighbours around the target instead of shifted to a positive x and z
     */
    @Override
    public BlockPathTypes getBlockPathTypes(BlockGetter level, int x, int y, int z, int xSize, int ySize, int zSize, boolean canOpenDoors,
                                            boolean canEnterDoors, EnumSet<BlockPathTypes> nodeTypeEnum, BlockPathTypes nodeType, BlockPos pos) {
        float width = Math.max(0, xSize - 2);
        int widthEachSide = Mth.ceil(width / 2.0f)+1;
        for (int i = 0; i < widthEachSide; ++i) {
            for (int j = 0; j < ySize; ++j) {
                for (int k = 0; k < widthEachSide; ++k) {
                    BlockPathTypes blockPathType = this.getBlockPathType(level, x+i, y+j, z+k);
                    blockPathType = this.evaluateBlockPathType(level, canOpenDoors, canEnterDoors, pos, blockPathType);
                    nodeTypeEnum.add(blockPathType);
                    if (i == 0 && j == 0 && k == 0) {
                        nodeType = blockPathType;
                    } else if (i != 0 || k != 0) {
                        blockPathType = this.getBlockPathType(level, x-i, y+j, z-k);
                        blockPathType = this.evaluateBlockPathType(level, canOpenDoors, canEnterDoors, pos, blockPathType);
                        nodeTypeEnum.add(blockPathType);
                    }
                }
            }
        }
        return nodeType;
    }

}
