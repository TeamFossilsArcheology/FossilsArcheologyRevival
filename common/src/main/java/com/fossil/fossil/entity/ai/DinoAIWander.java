package com.fossil.fossil.entity.ai;

import com.fossil.fossil.entity.prehistoric.base.Prehistoric;
import net.minecraft.world.entity.ai.goal.RandomStrollGoal;
import net.minecraft.world.entity.ai.util.DefaultRandomPos;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;


public class DinoAIWander extends RandomStrollGoal {

    public DinoAIWander(Prehistoric dinosaur, double speed) {
        this(dinosaur, speed, 120);
    }

    public DinoAIWander(Prehistoric dinosaur, double speed, int interval) {
        super(dinosaur, speed, interval);
    }

    /**
     * Returns whether the EntityAIBase should begin execution.
     */
    @Override
    public boolean canUse() {
        Prehistoric dinosaur = (Prehistoric) mob;
        if (!dinosaur.shouldWander || dinosaur.isImmobile() || dinosaur.getTarget() != null) {
            return false;
        }
        if (dinosaur.isSleeping()) return false;
        return super.canUse();
        /*if(dinosaur instanceof EntityPrehistoricFlocking && !((EntityPrehistoricFlocking) dinosaur).isGroupLeader() && ((EntityPrehistoricFlocking) dinosaur).hasGroupLeader()){
            return false;
        }*/
    }

    @Nullable
    @Override
    protected Vec3 getPosition() {
        if (mob.isInWater()) {
            Vec3 randomPos = DefaultRandomPos.getPos(mob, 30, 8);
            return randomPos == null ? DefaultRandomPos.getPos(mob, 10, 7) : randomPos;
        } else {
            return DefaultRandomPos.getPos(mob, 10, 7);
        }
    }

    @Override
    public void start() {
        super.start();
        ((Prehistoric)mob).setCurrentAnimation(((Prehistoric)mob).getWalkingAnimation());
    }

    @Override
    public void stop() {
        super.stop();
        ((Prehistoric)mob).setCurrentAnimation(((Prehistoric)mob).getIdleAnimation());
    }
}