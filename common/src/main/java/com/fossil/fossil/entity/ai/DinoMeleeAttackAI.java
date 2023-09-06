package com.fossil.fossil.entity.ai;

import com.fossil.fossil.entity.prehistoric.base.Prehistoric;
import net.minecraft.world.Difficulty;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.player.Player;

public class DinoMeleeAttackAI extends MeleeAttackGoal {
    private long attackStartTick;

    public DinoMeleeAttackAI(Prehistoric entity, double speed, boolean followTargetEvenIfNotSeen) {
        super(entity, speed, followTargetEvenIfNotSeen);
    }

    @Override
    public boolean canUse() {
        Prehistoric dinosaur = (Prehistoric) mob;
        LivingEntity target = dinosaur.getTarget();
        if (dinosaur.isImmobile()) {
            return false;
        } else if (dinosaur.level.getDifficulty() == Difficulty.PEACEFUL && target instanceof Player) {
            return false;
        }
        if (dinosaur.isFleeing()) {
            return false;
        }
        return super.canUse();
    }

    @Override
    protected void checkAndPerformAttack(LivingEntity enemy, double distToEnemySqr) {
        Prehistoric dinosaur = (Prehistoric) mob;

        double attackReach = mob.getBbWidth() * mob.getBbWidth() * 2 + enemy.getBbWidth();
        if (distToEnemySqr <= attackReach) {
            if (isTimeToAttack()) {
                resetAttackCooldown();
                attackStartTick = mob.level.getGameTime();
                mob.swing(InteractionHand.MAIN_HAND);
            }
            if (attackStartTick >= 0 && mob.level.getGameTime() >= attackStartTick + dinosaur.getAttackDelay()) {
                mob.doHurtTarget(enemy);
                attackStartTick = -1;
            }
        } else {
            attackStartTick = -1;
        }
    }
}