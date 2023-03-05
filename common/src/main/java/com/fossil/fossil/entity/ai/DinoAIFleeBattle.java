package com.fossil.fossil.entity.ai;

import com.fossil.fossil.entity.prehistoric.base.Prehistoric;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.util.DefaultRandomPos;
import net.minecraft.world.entity.animal.FlyingAnimal;
import net.minecraft.world.phys.Vec3;

public class DinoAIFleeBattle extends Goal {
    protected final Prehistoric dinosaur;
    protected double speed;
    protected Vec3 fleeTo;

    public DinoAIFleeBattle(Prehistoric dinosaur, double speedIn) {
        this.dinosaur = dinosaur;
        this.speed = speedIn;
    }
    @Override
    public boolean canUse() {
        if (dinosaur.getLastHurtByMob() == null || !dinosaur.isFleeing()) {
            return false;
        } else {
            return tryFindPlaceToFlee();
        }
    }

    protected boolean tryFindPlaceToFlee() {
        LivingEntity fleeFrom = dinosaur.getLastHurtByMob();

        int yRange = 10;
        if (dinosaur instanceof FlyingAnimal) yRange = 25;
        Vec3 fleeTo = DefaultRandomPos.getPosAway(dinosaur, 25, yRange, fleeFrom.position());

        if (fleeTo == null) {
            return false;
        } else {
            this.fleeTo = fleeTo;
            return true;
        }
    }

    @Override
    public void start() {
        dinosaur.getNavigation().moveTo(fleeTo.x, fleeTo.y, fleeTo.z, speed + 0.25);
    }

    @Override
    public boolean canContinueToUse() {
        return dinosaur.getNavigation().isDone() || dinosaur.isFleeing();
    }
}
