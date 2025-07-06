package com.github.teamfossilsarcheology.fossil.entity.ai.navigation;

import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.navigation.FlyingPathNavigation;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.level.pathfinder.FlyNodeEvaluator;
import net.minecraft.world.level.pathfinder.Path;
import net.minecraft.world.level.pathfinder.PathFinder;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.EnumSet;
import java.util.Set;

public class FlightPathNavigation extends FlyingPathNavigation {
    public FlightPathNavigation(Mob mob, Level level) {
        super(mob, level);
    }

    @Override
    protected @NotNull PathFinder createPathFinder(int maxVisitedNodes) {
        nodeEvaluator = new FlightNodeEvaluator();
        return new PathFinder(nodeEvaluator, maxVisitedNodes);
    }

    @Nullable
    @Override
    protected Path createPath(Set<BlockPos> targets, int regionOffset, boolean offsetUpward, int accuracy, float followRange) {
        return CenteredPath.createFromPath(super.createPath(targets, regionOffset, offsetUpward, accuracy, followRange));
    }

    private static class FlightNodeEvaluator extends FlyNodeEvaluator {
        @Override
        public BlockPathTypes getBlockPathTypes(BlockGetter level, int x, int y, int z, EnumSet<BlockPathTypes> nodeTypeEnum, BlockPathTypes nodeType, BlockPos pos) {
            float width = Math.max(0, entityWidth - 2);
            int widthEachSide = Mth.ceil(width / 2.0f) + 1;
            for (int i = 0; i < widthEachSide; ++i) {
                for (int j = 0; j < entityHeight; ++j) {
                    for (int k = 0; k < widthEachSide; ++k) {
                        BlockPathTypes blockPathType = this.getBlockPathType(level, x + i, y + j, z + k);
                        blockPathType = this.evaluateBlockPathType(level, pos, blockPathType);
                        nodeTypeEnum.add(blockPathType);
                        if (i == 0 && j == 0 && k == 0) {
                            nodeType = blockPathType;
                        } else if (i != 0 || k != 0) {
                            blockPathType = this.getBlockPathType(level, x - i, y + j, z - k);
                            blockPathType = this.evaluateBlockPathType(level, pos, blockPathType);
                            nodeTypeEnum.add(blockPathType);
                        }
                    }
                }
            }
            return nodeType;
        }
    }
}
