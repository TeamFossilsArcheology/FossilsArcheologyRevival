package com.github.teamfossilsarcheology.fossil.entity.ai;

import com.github.teamfossilsarcheology.fossil.entity.prehistoric.base.OrderType;
import com.github.teamfossilsarcheology.fossil.entity.prehistoric.base.PrehistoricFlocking;
import net.minecraft.world.entity.ai.goal.FollowFlockLeaderGoal;
import net.minecraft.world.entity.ai.goal.Goal;

import java.util.EnumSet;


/**
 * Custom Implementation of {@link FollowFlockLeaderGoal} for entities of {@link PrehistoricFlocking}
 */
public class FlockWanderGoal extends Goal {
    private final PrehistoricFlocking entity;
    private final double speedModifier;
    private int nextStartTick;
    private int timeToRecalcPath;

    public FlockWanderGoal(PrehistoricFlocking entity, double speedModifier) {
        this.entity = entity;
        this.speedModifier = speedModifier;
        this.nextStartTick = nextStartTick(entity);
        this.setFlags(EnumSet.of(Goal.Flag.MOVE));
    }

    protected int nextStartTick(PrehistoricFlocking taskOwner) {
        return reducedTickDelay(10 + taskOwner.getRandom().nextInt(200) % 20);
    }

    @Override
    public boolean canUse() {
        if (entity.isGroupLeader() || entity.getCurrentOrder() != OrderType.WANDER || entity.getTarget() != null) {
            return false;
        }
        if (nextStartTick > 0) {
            nextStartTick--;
            return false;
        }
        return entity.hasGroupLeader();
    }

    @Override
    public boolean canContinueToUse() {
        return entity.hasGroupLeader() && entity.inRangeOfGroupLeader();
    }

    @Override
    public void start() {
        timeToRecalcPath = 0;
    }

    @Override
    public void tick() {
        timeToRecalcPath--;
        if (timeToRecalcPath > 0) {
            return;
        }
        timeToRecalcPath = adjustedTickDelay(25);
        entity.pathToGroupLeader(speedModifier);
    }
}
