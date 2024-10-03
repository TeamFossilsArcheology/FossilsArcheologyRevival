package com.github.teamfossilsarcheology.fossil.entity.ai.navigation;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
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
        return super.findAcceptedNode(x, y, z, i, e, direction, blockPathTypes);
    }
}
