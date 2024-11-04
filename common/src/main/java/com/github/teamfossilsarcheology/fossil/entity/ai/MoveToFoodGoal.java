package com.github.teamfossilsarcheology.fossil.entity.ai;

import com.github.teamfossilsarcheology.fossil.entity.prehistoric.base.Prehistoric;

/**
 * A Goal that will move the entity to a target block if the entity is hungry. If the entity is near starving the delay between goal execution will
 * be removed and the cache cleared much sooner.
 */
public abstract class MoveToFoodGoal extends CacheMoveToBlockGoal {
    protected int feedingTicks;
    protected long animEndTick;

    protected MoveToFoodGoal(Prehistoric entity, double speedModifier, int searchRange) {
        super(entity, speedModifier, searchRange);
    }

    @Override
    public void start() {
        super.start();
        feedingTicks = 0;
        animEndTick = 0;
    }

    @Override
    public boolean canUse() {
        if (!entity.isHungry()) {
            return false;
        }
        if (entity.isDeadlyHungry()) {
            nextStartTick = 0;
            clearTicks = Math.min(clearTicks / 4, 1);
        }
        return super.canUse();
    }

    @Override
    public boolean canContinueToUse() {
        if (animEndTick != 0 && entity.level.getGameTime() < animEndTick) {
            return true;
        }
        if (entity.getHunger() >= entity.getMaxHunger()) {
            return false;
        }
        return super.canContinueToUse();
    }
}
