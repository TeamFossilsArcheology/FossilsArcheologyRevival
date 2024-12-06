package com.github.teamfossilsarcheology.fossil.entity.ai;

import com.github.teamfossilsarcheology.fossil.entity.prehistoric.base.PrehistoricLeaping;
import net.minecraft.world.Difficulty;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.player.Player;

import java.util.EnumSet;

public class DinoOtherLeapAtTargetGoal extends MeleeAttackGoal {
    public DinoOtherLeapAtTargetGoal(PrehistoricLeaping entity) {
        super(entity, 1, false);
        this.setFlags(EnumSet.of(Flag.JUMP, Flag.MOVE, Flag.LOOK));
    }

    @Override
    public boolean canUse() {
        PrehistoricLeaping dino = (PrehistoricLeaping) mob;
        if (!dino.isOnGround()) {
            return false;
        }
        if (dino.level.getDifficulty() == Difficulty.PEACEFUL && dino.getTarget() instanceof Player) {
            return false;
        }
        return super.canUse();
    }

    @Override
    public boolean canContinueToUse() {
        if (((PrehistoricLeaping) mob).getLeapSystem().hasLeapStarted()) {
            return false;
        }
        return super.canContinueToUse();
    }

    @Override
    protected void checkAndPerformAttack(LivingEntity enemy, double distToEnemySqr) {
        PrehistoricLeaping dino = (PrehistoricLeaping) mob;
        if (dino.distanceToSqr(enemy) <= 20 && !dino.getLeapSystem().hasLeapStarted()) {
            dino.lookAt(enemy, 100, 100);
            dino.getNavigation().stop();
            dino.startLeap(enemy);
        }
    }
}
