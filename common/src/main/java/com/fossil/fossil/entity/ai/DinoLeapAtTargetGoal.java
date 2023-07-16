package com.fossil.fossil.entity.ai;

import com.fossil.fossil.entity.prehistoric.PrehistoricLeaping;
import com.fossil.fossil.entity.prehistoric.base.Prehistoric;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;

import java.util.EnumSet;

public class DinoLeapAtTargetGoal<T extends Prehistoric & PrehistoricLeaping> extends Goal {
    private final T dino;
    private LivingEntity target;

    public DinoLeapAtTargetGoal(T dino) {
        this.dino = dino;
        this.setFlags(EnumSet.of(Goal.Flag.JUMP, Goal.Flag.MOVE));
    }

    @Override
    public boolean canUse() {
        if (dino.isImmobile() || !dino.isOnGround() || dino.isVehicle() || dino.isOrderedToSit()) {
            return false;
        }
        if (!dino.useSpecialAttack()) {
            return false;
        }
        //TODO: Implement the rest when leaping dinos are implemented
        target = dino.getTarget();
        if (target == null) {
            return false;
        }
        return dino.distanceToSqr(target) <= 16.0;
    }

    @Override
    public boolean canContinueToUse() {
        return !dino.isOnGround();
    }

    @Override
    public void start() {
        dino.lookAt(target, 100, 100);
        dino.setCurrentAnimation(dino.nextLeapAnimation());
    }
}
