package com.fossil.fossil.entity.ai;

import com.fossil.fossil.entity.prehistoric.base.Prehistoric;
import com.fossil.fossil.entity.prehistoric.base.PrehistoricSwimming;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;

public class GrabMeleeAttackGoal extends DinoMeleeAttackGoal {
    private static int ATTACK = 0;
    private static int GRAB = 1;
    private int attackType = -1;
    private long attackStartTick;
    public GrabMeleeAttackGoal(PrehistoricSwimming entity, double speed, boolean followTargetEvenIfNotSeen) {
        super(entity, speed, followTargetEvenIfNotSeen);
    }

    @Override
    public boolean canUse() {
        //TODO: Currently used so that this does not conflict with BreachGoal. Needs better solution
        Prehistoric dinosaur = (Prehistoric) mob;
        LivingEntity target = dinosaur.getTarget();
        if (target != null && target.isInWater()) {
            return super.canUse();
        }
        return false;
    }

    @Override
    public boolean canContinueToUse() {
        return attackType != GRAB && super.canContinueToUse();
    }

    @Override
    public void stop() {
        super.stop();
        attackType = -1;
    }

    @Override
    protected void checkAndPerformAttack(LivingEntity enemy, double distToEnemySqr) {
        PrehistoricSwimming dino = (PrehistoricSwimming) mob;
        boolean canReachEnemy = dino.canReachPrey(enemy);
        boolean isTimeToAttack = isTimeToAttack();
        boolean tooBig = !PrehistoricSwimming.isEntitySmallerThan(enemy, 2 * dino.getScale() / dino.data().maxScale());
        if (canReachEnemy) {
            if (isTimeToAttack) {
                if (tooBig || dino.getRandom().nextInt(5) > 0) {
                    attackType = ATTACK;
                    resetAttackCooldown();
                    attackStartTick = mob.level.getGameTime();
                    mob.swing(InteractionHand.MAIN_HAND);
                } else {
                    attackType = GRAB;
                    dino.destroyBoat(enemy);
                    dino.startGrabAttack(enemy);
                }
            }
            double attackDelay = dino.getAnimationLogic().getActionDelay("Attack");
            if (attackStartTick >= 0 && attackDelay > -1 && mob.level.getGameTime() > attackStartTick + attackDelay) {
                dino.attackTarget(enemy);
                dino.destroyBoat(enemy);
                attackStartTick = -1;
            }
        } else {
            attackStartTick = -1;
        }
    }
}
