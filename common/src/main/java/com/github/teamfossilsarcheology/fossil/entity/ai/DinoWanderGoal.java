package com.github.teamfossilsarcheology.fossil.entity.ai;

import com.github.teamfossilsarcheology.fossil.entity.prehistoric.base.OrderType;
import com.github.teamfossilsarcheology.fossil.entity.prehistoric.base.Prehistoric;
import com.github.teamfossilsarcheology.fossil.entity.prehistoric.base.PrehistoricFlocking;
import net.minecraft.world.entity.ai.goal.RandomStrollGoal;
import net.minecraft.world.entity.ai.util.DefaultRandomPos;
import net.minecraft.world.entity.ai.util.LandRandomPos;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

/**
 * Random stroll goal for non-flying/swimming mobs
 */
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
        if (dinosaur.getCurrentOrder() != OrderType.WANDER || dinosaur.hasTarget()) {
            return false;
        }
        if (dinosaur instanceof PrehistoricFlocking flocking && !flocking.isGroupLeader() && flocking.hasGroupLeader()) {
            return false;
        }
        return super.canUse();
    }

    @Nullable
    @Override
    protected Vec3 getPosition() {
        Vec3 randomPos;
        int verticalDistance = 7;

        if (mob.isInWater()) {
            randomPos = LandRandomPos.getPos(mob, 30, 8);
            return randomPos == null ? LandRandomPos.getPos(mob, 10, verticalDistance) : randomPos;
        }
        randomPos = mob.getRandom().nextFloat() > 0.001 ? LandRandomPos.getPos(mob, 10, verticalDistance) : DefaultRandomPos.getPos(mob, 10, verticalDistance);
        return randomPos;
    }
}