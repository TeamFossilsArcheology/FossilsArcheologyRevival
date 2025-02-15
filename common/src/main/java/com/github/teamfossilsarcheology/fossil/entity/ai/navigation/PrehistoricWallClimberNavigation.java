package com.github.teamfossilsarcheology.fossil.entity.ai.navigation;

import com.github.teamfossilsarcheology.fossil.entity.prehistoric.base.Prehistoric;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.pathfinder.Path;
import org.jetbrains.annotations.Nullable;

public class PrehistoricWallClimberNavigation extends PrehistoricPathNavigation {
    @Nullable
    private BlockPos pathToPosition;

    public PrehistoricWallClimberNavigation(Prehistoric mob, Level level) {
        super(mob, level);
    }

    public Path createPath(BlockPos pos, int accuracy) {
        pathToPosition = pos;
        return super.createPath(pos, accuracy);
    }

    public Path createPath(Entity entity, int accuracy) {
        pathToPosition = entity.blockPosition();
        return super.createPath(entity, accuracy);
    }

    public boolean moveTo(Entity entity, double speed) {
        Path path = createPath(entity, 0);
        if (path != null) {
            return moveTo(path, speed);
        } else {
            pathToPosition = entity.blockPosition();
            speedModifier = speed;
            return true;
        }
    }

    public void tick() {
        if (!isDone()) {
            super.tick();
        } else {
            if (pathToPosition != null) {
                float width = mob.getBbWidth();
                if (width > 1) {
                    if (pathToPosition.closerToCenterThan(mob.position(), width) || (mob.getY() > pathToPosition.getY() && (new BlockPos(pathToPosition.getX(), mob.getY(), pathToPosition.getZ())).closerToCenterThan(mob.position(), width))) {
                        pathToPosition = null;
                    } else {
                        mob.getMoveControl().setWantedPosition(pathToPosition.getX(), pathToPosition.getY(), pathToPosition.getZ(), this.speedModifier);
                    }
                } else {
                    if (pathToPosition.distToCenterSqr(mob.position()) < width * 2 || (mob.getY() > pathToPosition.getY() && new BlockPos(pathToPosition.getX(), mob.getY(), pathToPosition.getZ()).distToCenterSqr(mob.position()) < width * 2)) {
                        pathToPosition = null;
                    } else {
                        mob.getMoveControl().setWantedPosition(pathToPosition.getX(), pathToPosition.getY(), pathToPosition.getZ(), this.speedModifier);
                    }
                }
            }

        }
    }
}
