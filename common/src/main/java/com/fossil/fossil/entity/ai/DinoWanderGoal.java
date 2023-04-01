package com.fossil.fossil.entity.ai;

import com.fossil.fossil.entity.prehistoric.base.Prehistoric;
import com.fossil.fossil.entity.prehistoric.base.PrehistoricFlocking;
import net.minecraft.world.entity.ai.goal.RandomStrollGoal;
import net.minecraft.world.entity.ai.util.DefaultRandomPos;
import net.minecraft.world.entity.animal.FlyingAnimal;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;


public class DinoWanderGoal extends RandomStrollGoal {

    public DinoWanderGoal(Prehistoric dinosaur, double speed) {
        this(dinosaur, speed, 120);
    }

    public DinoWanderGoal(Prehistoric dinosaur, double speed, int interval) {
        super(dinosaur, speed, interval);
    }

    @Override
    public boolean canUse() {
        Prehistoric dinosaur = (Prehistoric) mob;
        if (!dinosaur.shouldWander || dinosaur.isImmobile() || dinosaur.getTarget() != null) {
            return false;
        }
        if(dinosaur instanceof PrehistoricFlocking flocking && !flocking.isGroupLeader() && flocking.hasGroupLeader()){
            return false;
        }
        return super.canUse();
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