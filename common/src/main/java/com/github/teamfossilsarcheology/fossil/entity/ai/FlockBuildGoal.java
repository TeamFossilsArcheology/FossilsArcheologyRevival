package com.github.teamfossilsarcheology.fossil.entity.ai;

import com.github.teamfossilsarcheology.fossil.entity.prehistoric.base.PrehistoricFlocking;
import com.mojang.datafixers.DataFixUtils;
import net.minecraft.world.entity.ai.goal.Goal;

import java.util.function.Predicate;

public class FlockBuildGoal extends Goal {
    private final PrehistoricFlocking entity;

    public FlockBuildGoal(PrehistoricFlocking entity) {
        this.entity = entity;
    }

    @Override
    public boolean canUse() {
        return !entity.isGroupLeader();
    }

    @Override
    public boolean canContinueToUse() {
        return entity.hasGroupLeader() && entity.inRangeOfGroupLeader();
    }

    @Override
    public void start() {
        Predicate<PrehistoricFlocking> canJoin = other -> other.canGroupGrow() || !other.hasGroupLeader();
        var potentialFlock = entity.level.getEntitiesOfClass(entity.getClass(), entity.getBoundingBox().inflate(entity.getFlockDistance()),
                canJoin);
        var newGroupLeader = DataFixUtils.orElse(potentialFlock.stream().findFirst(), entity);
        newGroupLeader.addFollowers(potentialFlock.stream().filter(flocking -> !flocking.hasGroupLeader()));
    }

    @Override
    public void stop() {
        if (entity.hasGroupLeader()) {
            entity.leaveGroup();
        }
    }
}
