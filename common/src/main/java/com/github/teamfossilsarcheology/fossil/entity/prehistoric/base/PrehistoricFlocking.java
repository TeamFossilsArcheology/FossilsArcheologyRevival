package com.github.teamfossilsarcheology.fossil.entity.prehistoric.base;

import com.github.teamfossilsarcheology.fossil.entity.ai.FlockBuildGoal;
import com.github.teamfossilsarcheology.fossil.entity.ai.FlockWanderGoal;
import com.github.teamfossilsarcheology.fossil.entity.util.Util;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.util.LandRandomPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import java.util.stream.Stream;

public abstract class PrehistoricFlocking extends Prehistoric {
    protected int groupSize = 1;
    private PrehistoricFlocking groupLeader;

    protected PrehistoricFlocking(EntityType<? extends Prehistoric> entityType, Level level) {
        super(entityType, level);
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        goalSelector.addGoal(0, new FlockBuildGoal(this));
        goalSelector.addGoal(Util.WANDER + 1, new FlockWanderGoal(this, 1));
    }

    public void leaveGroup() {
        groupLeader.decreaseGroupSize();
        groupLeader = null;
    }

    public boolean hasGroupLeader() {
        return groupLeader != null && groupLeader.isAlive();
    }

    public void increaseGroupSize() {
        groupSize++;
    }

    public void decreaseGroupSize() {
        groupSize--;
    }

    public boolean canGroupGrow() {
        return isGroupLeader() && groupSize < getMaxGroupSize();
    }

    public boolean isGroupLeader() {
        return groupSize > 1;
    }

    public boolean inRangeOfGroupLeader() {
        return distanceToSqr(groupLeader) <= 121;
    }

    public boolean isPartOfSameFlock(PrehistoricFlocking other) {
        return groupLeader != null && (other.groupLeader == this || groupLeader == other || other.groupLeader == groupLeader);
    }

    public void pathToGroupLeader(double speed) {
        if (hasGroupLeader()) {
            Vec3 vec;
            if (distanceTo(groupLeader) < 7) {
                vec = LandRandomPos.getPos(this, 5, 7);
            } else {
                vec = LandRandomPos.getPos(groupLeader, 5, 7);
            }
            if (vec != null) {
                getNavigation().moveTo(vec.x, vec.y, vec.z, speed);
            }
        }
    }

    public PrehistoricFlocking startFollowing(PrehistoricFlocking groupLeader) {
        this.groupLeader = groupLeader;
        groupLeader.increaseGroupSize();
        return groupLeader;
    }

    /**
     * Adds followers from a stream until the group size limit has been reached.
     */
    public void addFollowers(Stream<? extends PrehistoricFlocking> followers) {
        followers.limit(getMaxGroupSize() - groupSize).filter(dino -> dino != this).forEach(dino -> dino.startFollowing(this));
    }

    @Override
    public void aiStep() {
        super.aiStep();
        if (isGroupLeader() && level.random.nextInt(200) == 1) {
            if (level.getEntitiesOfClass(getClass(), getBoundingBox().inflate(getFlockDistance())).size() <= 1) {
                groupSize = 1;
            }
        }
    }

    protected abstract int getMaxGroupSize();

    public int getFlockDistance() {
        return 32;
    }
}
