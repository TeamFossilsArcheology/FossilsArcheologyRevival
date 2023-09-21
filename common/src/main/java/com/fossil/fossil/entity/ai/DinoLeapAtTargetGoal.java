package com.fossil.fossil.entity.ai;

import com.fossil.fossil.entity.prehistoric.base.PrehistoricLeaping;
import net.minecraft.world.Difficulty;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.player.Player;

import java.util.EnumSet;

public class DinoLeapAtTargetGoal<T extends PrehistoricLeaping> extends Goal {
    private final T dino;
    private LivingEntity target;

    public DinoLeapAtTargetGoal(T dino) {
        this.dino = dino;
        this.setFlags(EnumSet.of(Flag.JUMP, Flag.MOVE, Flag.LOOK));
    }

    @Override
    public boolean canUse() {
        if (dino.isImmobile() || dino.isPassenger() || !dino.isOnGround()) {
            return false;
        }
        if (dino.level.getDifficulty() == Difficulty.PEACEFUL && dino.getTarget() instanceof Player) {
            return false;
        }
        target = dino.getTarget();
        if (target == null || dino.distanceToSqr(target) >= 20) {
            return false;
        }
        return dino.useLeapAttack();
    }

    @Override
    public void start() {
        dino.lookAt(target, 100, 100);
        dino.startLeaping();
    }
}
