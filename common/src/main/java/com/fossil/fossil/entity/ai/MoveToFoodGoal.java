package com.fossil.fossil.entity.ai;

import com.fossil.fossil.entity.prehistoric.base.Prehistoric;

/**
 * A Goal that will move the entity to a target block if the entity is hungry. If the entity is near starving the delay between goal execution will
 * be removed and the cache cleared much sooner.
 */
public abstract class MoveToFoodGoal extends CacheMoveToBlockGoal {
    protected int feedingTicks;

    public MoveToFoodGoal(Prehistoric entity, double speedModifier, int searchRange) {
        super(entity, speedModifier, searchRange);
    }

    @Override
    public boolean canUse() {
        if (entity.getHunger() <= 0.25 * entity.getMaxHunger()) {
            nextStartTick = 0;
            clearTicks = Math.min(clearTicks / 4, 1);
        } else if (entity.getHunger() >= entity.getMaxHunger()) {
            return false;
        }
        return super.canUse();
    }

    @Override
    public boolean canContinueToUse() {
        if (entity.getHunger() >= entity.getMaxHunger()) {
            return false;
        }
        return super.canContinueToUse();
    }
}
