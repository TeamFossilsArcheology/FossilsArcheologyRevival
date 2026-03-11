package com.github.teamfossilsarcheology.fossil.entity.ai.navigation;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.Mth;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.level.pathfinder.Node;
import net.minecraft.world.level.pathfinder.WalkNodeEvaluator;
import org.jetbrains.annotations.Nullable;

public class PrehistoricNodeEvaluator extends WalkNodeEvaluator {

    @Nullable
    @Override
    protected Node findAcceptedNode(int x, int y, int z, int i, double d, Direction direction, BlockPathTypes blockPathTypes) {
        double e = getFloorLevel(new BlockPos(x, y, z));
        if ((e - d > entityHeight) && e - d > 1.125) {
            return null;
        }
        Node node = super.findAcceptedNode(x, y, z, i, e, direction, blockPathTypes);
        if (node != null && cornersOverAir(x, y, z)) {
            // Penalize heavily rather than reject — mob won't get stuck but
            // will strongly prefer nodes where its full footprint is supported.
            node.costMalus += 10.0f;
        }
        return node;
    }

    private boolean cornersOverAir(int x, int y, int z) {
        float halfWidth = mob.getBbWidth() / 2.0f;
        double cx = x + 0.5;
        double cz = z + 0.5;
        int x0 = Mth.floor(cx - halfWidth);
        int x1 = Mth.floor(cx + halfWidth);
        int z0 = Mth.floor(cz - halfWidth);
        int z1 = Mth.floor(cz + halfWidth);
        return !hasSolidGround(x0, y, z0)
            || !hasSolidGround(x0, y, z1)
            || !hasSolidGround(x1, y, z0)
            || !hasSolidGround(x1, y, z1);
    }

    private boolean hasSolidGround(int x, int y, int z) {
        for (int dy = 1; dy <= 3; dy++) {
            if (level.getBlockState(new BlockPos(x, y - dy, z)).isSolid()) {
                return true;
            }
        }
        return false;
    }

    @Override
    public BlockPathTypes getBlockPathType(BlockGetter level, int x, int y, int z) {
        BlockPathTypes type = super.getBlockPathType(level, x, y, z);
        if (type == BlockPathTypes.FENCE) {
            return BlockPathTypes.BLOCKED;
        }
        return type;
    }
}