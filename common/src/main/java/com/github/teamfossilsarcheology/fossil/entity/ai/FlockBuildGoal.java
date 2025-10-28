package com.github.teamfossilsarcheology.fossil.entity.ai;

import com.github.teamfossilsarcheology.fossil.entity.prehistoric.base.Prehistoric;
import com.github.teamfossilsarcheology.fossil.entity.prehistoric.base.PrehistoricFlocking;
import net.minecraft.world.entity.ai.goal.Goal;

import java.util.function.BiConsumer;
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
        return entity.hasGroupLeader() && entity.inRangeOfGroupLeader() && entity.getRandom().nextInt(1200) > 0;
    }

    @Override
    public void start() {
        Predicate<PrehistoricFlocking> canJoin = other -> other.canGroupGrow() || (!other.isGroupLeader() && !other.hasGroupLeader());
        var potentialFlock = entity.level().getEntitiesOfClass(entity.getClass(), entity.getBoundingBox().inflate(entity.getFlockDistance(), 10, entity.getFlockDistance()), canJoin);
        if (potentialFlock.size() == 1) {
            return;
        }
        //First try to follow older mobs and never younger ones
        BiConsumer<Predicate<PrehistoricFlocking>, Boolean> canAdd = (predicate, addSelf) -> {
            var newGroupLeader = potentialFlock.stream().filter(PrehistoricFlocking::canGroupGrow).filter(predicate).findAny();
            if (newGroupLeader.isPresent()) {
                newGroupLeader.get().addFollowers(potentialFlock.stream().filter(flocking -> !flocking.hasGroupLeader() && !flocking.isGroupLeader()));
            } else if (Boolean.TRUE.equals(addSelf)) {
                entity.addFollowers(potentialFlock.stream().filter(flocking -> !flocking.hasGroupLeader() && !flocking.isGroupLeader()));
            }
        };
        if (entity.isAdult()) {
            canAdd.accept(Prehistoric::isAdult, true);
        } else if (entity.isTeen()) {
            if (potentialFlock.stream().anyMatch(Prehistoric::isAdult)) {
                canAdd.accept(Prehistoric::isAdult, false);
            } else {
                canAdd.accept(Prehistoric::isTeen, true);
            }
        } else if (entity.isBaby()) {
            if (potentialFlock.stream().anyMatch(Prehistoric::isAdult)) {
                canAdd.accept(Prehistoric::isAdult, false);
            } else if (potentialFlock.stream().anyMatch(Prehistoric::isTeen)) {
                canAdd.accept(Prehistoric::isTeen, false);
            } else {
                canAdd.accept(Prehistoric::isBaby, true);
            }
        }
    }

    @Override
    public void stop() {
        entity.leaveGroup();
    }
}
