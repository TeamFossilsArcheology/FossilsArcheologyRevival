package com.fossil.fossil.entity.ai;

import com.fossil.fossil.entity.prehistoric.base.Prehistoric;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.AvoidEntityGoal;

import java.util.function.Predicate;

public class AnimalFearGoal extends AvoidEntityGoal<Prehistoric> {

    public AnimalFearGoal(PathfinderMob mob, Class<Prehistoric> avoidClass, float maxDist, double walkSpeedModifier, double sprintSpeedModifier,
                          Predicate<LivingEntity> avoidPredicate) {
        super(mob, avoidClass, maxDist, walkSpeedModifier, sprintSpeedModifier, avoidPredicate);
    }

    @Override
    public boolean canUse() {
        boolean canUse = super.canUse();
        if (canUse && toAvoid != null && toAvoid.isTame()) {
            return false;
        }
        return canUse;
    }
}
