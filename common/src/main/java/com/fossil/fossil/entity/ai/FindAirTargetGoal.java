package com.fossil.fossil.entity.ai;

import com.fossil.fossil.entity.prehistoric.base.PrehistoricFlying;
import com.fossil.fossil.entity.prehistoric.base.PrehistoricSwimming;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.phys.Vec3;

/**
 * A Goal that will find a visible empty block the entity can fly to
 */
public class FindAirTargetGoal extends Goal {
    protected final PrehistoricFlying dino;
    public BlockPos targetPos;

    public FindAirTargetGoal(PrehistoricFlying dino) {
        this.dino = dino;
    }

    @Override
    public boolean canUse() {
        if (!dino.isFlying() || dino.isImmobile() || dino.isBaby()) {
            return false;
        }
        if (targetPos == null || dino.distanceToSqr(targetPos.getX(), dino.getY(), targetPos.getZ()) > 3 || dino.isTargetBlocked(Vec3.atCenterOf(targetPos))) {
            targetPos = findAirTarget();
        }
        return true;
    }

    @Override
    public boolean canContinueToUse() {
        return targetPos != null && !dino.isTargetBlocked(Vec3.atCenterOf(targetPos));
    }

    private BlockPos findAirTarget() {
        if (dino.canSleep() && !PrehistoricSwimming.isOverWater(dino)) {
            for (int i = 0; i < 10; i++) {
                BlockPos pos = dino.getBlockInView();
                if (pos != null && dino.level.isEmptyBlock(pos) && !dino.isTargetBlocked(Vec3.atCenterOf(pos))) {
                    while (dino.level.isEmptyBlock(pos) && pos.getY() > 3) {
                        pos = pos.below();
                    }
                    return pos.above();
                }
            }
        }
        if (dino.getTarget() == null) {
            return dino.generateAirTarget();
        } else {
            if (dino.level.isEmptyBlock(dino.getTarget().blockPosition())) {
                return dino.getTarget().blockPosition();
            }
        }
        return dino.blockPosition();
    }
}
