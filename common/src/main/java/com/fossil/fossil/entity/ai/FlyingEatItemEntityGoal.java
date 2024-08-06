package com.fossil.fossil.entity.ai;

import com.fossil.fossil.entity.prehistoric.base.Prehistoric;
import com.fossil.fossil.entity.prehistoric.base.PrehistoricFlying;

/**
 * Will only be used if the mob is not flying because the flying movement does not make good use of the navigator
 */
public class FlyingEatItemEntityGoal extends EatItemEntityGoal {
    public FlyingEatItemEntityGoal(Prehistoric entity) {
        super(entity);
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
    public boolean canContinueToUse() {
        return !((PrehistoricFlying) entity).isFlying() && !((PrehistoricFlying) entity).isTakingOff() && super.canContinueToUse();
    }
}
