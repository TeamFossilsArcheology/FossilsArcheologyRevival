package com.fossil.fossil.entity.ai;

import com.fossil.fossil.entity.prehistoric.base.Prehistoric;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.util.DefaultRandomPos;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

import java.util.EnumSet;

public class DinoAIWander extends Goal {
    protected final Prehistoric dinosaur;
    protected final double speed;
    protected double x;
    protected double y;
    protected double z;
    protected int interval;
    protected boolean forceTrigger;

    public DinoAIWander(Prehistoric dinosaur, double speed) {
        this(dinosaur, speed, 30);
    }

    public DinoAIWander(Prehistoric dinosaur, double speed, int interval) {
        this.dinosaur = dinosaur;
        this.speed = speed;
        this.interval = interval;
        setFlags(EnumSet.of(Goal.Flag.MOVE));
    }

    /**
     * Returns whether the EntityAIBase should begin execution.
     */
    @Override
    public boolean canUse() {
        if (!dinosaur.shouldWander || dinosaur.isImmobile()) {
            return false;
        }
        /*if(dinosaur instanceof EntityPrehistoricFlocking && !((EntityPrehistoricFlocking) dinosaur).isGroupLeader() && ((EntityPrehistoricFlocking) dinosaur).hasGroupLeader()){
            return false;
        }*/
        if (!this.forceTrigger) {
            if (dinosaur.getNoActionTime() >= 100) {
                return false;
            }

            if (dinosaur.getRandom().nextInt(this.interval) != 0) {
                return false;
            }
        }

        Vec3 position = this.getPosition();

        if (position == null) {
            return false;
        } else {
            this.x = position.x;
            this.y = position.y;
            this.z = position.z;
            this.forceTrigger = false;
            this.dinosaur.getNavigation().moveTo(this.x, this.y, this.z, this.speed);
            return true;
        }
    }

    @Nullable
    protected Vec3 getPosition() {
        if (dinosaur.isInWater()) {
            Vec3 randomPos = DefaultRandomPos.getPos(dinosaur, 30, 8);
            return randomPos == null ? DefaultRandomPos.getPos(dinosaur, 10, 7) : randomPos;
        } else {
            return DefaultRandomPos.getPos(dinosaur, 10, 7);
        }
    }

    @Override
    public boolean canContinueToUse() {
        return !this.dinosaur.getNavigation().isDone();
    }

    @Override
    public void start() {
        this.dinosaur.getNavigation().moveTo(this.x, this.y, this.z, this.speed);
    }

    /**
     * Makes task to bypass chance
     */
    public void trigger() {
        this.forceTrigger = true;
    }

    /**
     * Changes task random possibility for execution
     */
    public void setInterval(int interval) {
        this.interval = interval;
    }
}