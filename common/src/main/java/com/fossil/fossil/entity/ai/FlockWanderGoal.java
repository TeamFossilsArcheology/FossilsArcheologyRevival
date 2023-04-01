package com.fossil.fossil.entity.ai;

import com.fossil.fossil.entity.prehistoric.base.PrehistoricFlocking;
import com.mojang.datafixers.DataFixUtils;
import net.minecraft.world.entity.ai.goal.FollowFlockLeaderGoal;
import net.minecraft.world.entity.ai.goal.Goal;

import java.util.EnumSet;
import java.util.function.Predicate;


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
        if (!entity.shouldWander || entity.isImmobile()) {
            return false;
        }
        if (entity.isGroupLeader()) {
            return false;
        } else if (entity.hasGroupLeader()) {
            return true;
        } else if (nextStartTick > 0) {
            nextStartTick--;
            return false;
        } else {
            nextStartTick = nextStartTick(entity);
            Predicate<PrehistoricFlocking> canJoin = flocking -> flocking.canGroupGrow() || !flocking.hasGroupLeader();
            var potentialFlock = entity.level.getEntitiesOfClass(entity.getClass(), entity.getBoundingBox().inflate(entity.getFlockDistance()),
                    canJoin);
            var newGroupLeader = DataFixUtils.orElse(potentialFlock.stream().findFirst(), entity);
            newGroupLeader.addFollowers(potentialFlock.stream().filter(flocking -> !flocking.hasGroupLeader()));
            return entity.hasGroupLeader();
        }
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
    public void stop() {
        entity.leaveGroup();
    }

    @Override
    public void tick() {
        if (--timeToRecalcPath > 0) {
            return;
        }
        timeToRecalcPath = adjustedTickDelay(25);
        entity.pathToGroupLeader(speedModifier);
    }
}
