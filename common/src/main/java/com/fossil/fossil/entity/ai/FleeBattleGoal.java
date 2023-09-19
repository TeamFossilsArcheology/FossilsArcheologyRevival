package com.fossil.fossil.entity.ai;

import com.fossil.fossil.entity.prehistoric.base.Prehistoric;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.util.DefaultRandomPos;
import net.minecraft.world.entity.animal.FlyingAnimal;
import net.minecraft.world.phys.Vec3;

import java.util.EnumSet;

/**
 * A Goal that will move a fleeing entity to a random spot away from its attacker
 */
public class FleeBattleGoal extends Goal {
    private final Prehistoric dino;
    private final double speedModifier;
    private Vec3 fleeTo;

    public FleeBattleGoal(Prehistoric dino, double speedModifier) {
        this.dino = dino;
        this.speedModifier = speedModifier;
        setFlags(EnumSet.of(Flag.MOVE));
    }

    @Override
    public boolean canUse() {
        if (dino.getLastHurtByMob() == null || !dino.isFleeing()) {
            return false;
        } else {
            return tryFindPlaceToFlee(dino.getLastHurtByMob());
        }
    }

    protected boolean tryFindPlaceToFlee(LivingEntity fleeFrom) {
        int yRange = dino instanceof FlyingAnimal ? 25 : 10;
        Vec3 fleeTo = DefaultRandomPos.getPosAway(dino, 25, yRange, fleeFrom.position());

        if (fleeTo == null) {
            return false;
        } else {
            this.fleeTo = fleeTo;
            return true;
        }
    }

    @Override
    public void start() {
        dino.getNavigation().moveTo(fleeTo.x, fleeTo.y, fleeTo.z, speedModifier + 0.25);
    }

    @Override
    public void stop() {
        fleeTo = null;
    }

    @Override
    public boolean canContinueToUse() {
        return !dino.getNavigation().isDone() && dino.isFleeing();
    }
}
