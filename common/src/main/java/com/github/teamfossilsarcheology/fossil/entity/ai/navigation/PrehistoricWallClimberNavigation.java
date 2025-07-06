package com.github.teamfossilsarcheology.fossil.entity.ai.navigation;

import com.github.teamfossilsarcheology.fossil.entity.prehistoric.base.Prehistoric;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.pathfinder.Path;
import org.jetbrains.annotations.Nullable;

import java.util.Set;

public class PrehistoricWallClimberNavigation extends PrehistoricPathNavigation {
    //Remembers target even if no path can be found and then walks towards it.
    //The idea is that it can climb over walls that way but this does also mean that it will ignore danger like spiders do
    @Nullable
    private BlockPos pathToPosition;

    public PrehistoricWallClimberNavigation(Prehistoric mob, Level level) {
        super(mob, level);
    }

    @Override
    protected @Nullable Path createPath(Set<BlockPos> targets, int regionOffset, boolean offsetUpward, int accuracy, float followRange) {
        targets.stream().findFirst().ifPresent(blockPos -> pathToPosition = blockPos);
        return super.createPath(targets, regionOffset, offsetUpward, accuracy, followRange);
    }

    @Override
    public boolean moveTo(@Nullable Path pathentity, double speed) {
        if (pathentity != null && pathentity.getEndNode() != null) {
            //Override old pathToPosition before new path gets trimmed
            pathToPosition = pathentity.getEndNode().asBlockPos();
        }
        return super.moveTo(pathentity, speed);
    }

    public void tick() {
        if (!isDone()) {
            super.tick();
        } else {
            if (pathToPosition != null) {
                float width = mob.getBbWidth();
                if (width > 1) {
                    if (pathToPosition.closerToCenterThan(mob.position(), width) || (mob.getY() > pathToPosition.getY() && (BlockPos.containing(pathToPosition.getX(), mob.getY(), pathToPosition.getZ())).closerToCenterThan(mob.position(), width))) {
                        pathToPosition = null;
                    } else {
                        mob.getMoveControl().setWantedPosition(pathToPosition.getX(), pathToPosition.getY(), pathToPosition.getZ(), this.speedModifier);
                    }
                } else {
                    if (pathToPosition.distToCenterSqr(mob.position()) < width * 2 || (mob.getY() > pathToPosition.getY() && BlockPos.containing(pathToPosition.getX(), mob.getY(), pathToPosition.getZ()).distToCenterSqr(mob.position()) < width * 2)) {
                        pathToPosition = null;
                    } else {
                        mob.getMoveControl().setWantedPosition(pathToPosition.getX(), pathToPosition.getY(), pathToPosition.getZ(), this.speedModifier);
                    }
                }
            }

        }
    }
}
