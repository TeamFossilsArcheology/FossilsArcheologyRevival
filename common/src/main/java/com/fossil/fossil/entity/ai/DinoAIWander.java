package com.fossil.fossil.entity.ai;

import com.fossil.fossil.entity.prehistoric.base.Prehistoric;
import net.minecraft.world.entity.ai.goal.RandomStrollGoal;
import net.minecraft.world.entity.ai.util.DefaultRandomPos;
import net.minecraft.world.entity.animal.FlyingAnimal;
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
        Vec3 randomPos = null;
        int verticalDistance = 7;
        if (mob instanceof FlyingAnimal) verticalDistance = 10;

        if (mob.isInWater()) randomPos = DefaultRandomPos.getPos(mob, 30, 8);
        if (randomPos == null) randomPos = DefaultRandomPos.getPos(mob, 10, verticalDistance);

        return randomPos;
    }
}