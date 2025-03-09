package com.github.teamfossilsarcheology.fossil.entity.ai;

import com.github.teamfossilsarcheology.fossil.entity.prehistoric.base.PrehistoricFlying;
import net.minecraft.world.phys.Vec3;

/**
 * Will only be used if the mob is not flying because the flying movement does not make good use of the navigator
 */
public class FlyingEatFromFeederGoal extends EatFromFeederGoal {

    public FlyingEatFromFeederGoal(PrehistoricFlying entity) {
        super(entity, 2);
    }

    @Override
    public boolean canUse() {
        if (((PrehistoricFlying)entity).isFlying() || ((PrehistoricFlying)entity).isTakingOff()) {
            nextStartTick = 0;
            return false;
        }
        return super.canUse();
    }

    @Override
    protected void moveMobToBlock() {
        if (path.getNodeCount() >= 16 || path.getEndNode() == null || path.getEndNode().distanceTo(getMoveToTarget()) > 1) {
            ((PrehistoricFlying)entity).moveTo(Vec3.atCenterOf(targetPos).add(0, 1, 0), true, true);
        } else {
            super.moveMobToBlock();
        }
    }

    @Override
    protected boolean createPath() {
        path = entity.getNavigation().createPath(getMoveToTarget(), 1, 32);
        return path != null;
    }

    @Override
    public boolean canContinueToUse() {
        return !((PrehistoricFlying)entity).isFlying() && !((PrehistoricFlying)entity).isTakingOff() && super.canContinueToUse();
    }
}
