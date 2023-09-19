package com.fossil.fossil.entity.ai.anu;

import com.fossil.fossil.entity.monster.AnuBoss;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;

import java.util.EnumSet;

public class FireballAttackGoal extends Goal {
    private static final int MAX_ATTACK_TIME = 20;
    private static final int MIN_ATTACK_TIME = 10;

    private final AnuBoss anu;
    private final double speedModifier;
    private final float attackRadius;
    private final float attackRadiusSqr;
    private LivingEntity target;
    private int seeTime;
    private int attackTime;

    public FireballAttackGoal(AnuBoss anu, double speedModifier, float attackRadius) {
        this.anu = anu;
        this.speedModifier = speedModifier;
        this.attackRadius = attackRadius;
        this.attackRadiusSqr = attackRadius * attackRadius;
        setFlags(EnumSet.of(Flag.MOVE, Goal.Flag.LOOK));
    }

    @Override
    public boolean canUse() {
        if (anu.getTarget() == null) {
            return false;
        }
        return anu.getAttackMode() == AnuBoss.AttackMode.FLIGHT;
    }

    @Override
    public boolean canContinueToUse() {
        return canUse() || !anu.getNavigation().isDone();
    }

    @Override
    public void start() {
        target = anu.getTarget();
    }

    @Override
    public void stop() {
        target = null;
        seeTime = 0;
        attackTime = -1;
    }

    @Override
    public void tick() {
        double dist = anu.distanceToSqr(target.getX(), target.getBoundingBox().minY, target.getZ());
        boolean hasLineOfSight = anu.getSensing().hasLineOfSight(target);
        if (hasLineOfSight) {
            seeTime++;
        } else {
            seeTime = 0;
        }
        if (dist <= attackRadiusSqr && seeTime >= 20) {
            anu.getNavigation().stop();
        } else {
            anu.getNavigation().moveTo(target, speedModifier);
        }
        anu.getLookControl().setLookAt(target, 30, 30);

        if (--attackTime == 0) {
            if (dist > attackRadiusSqr || !hasLineOfSight) {
                return;
            }
            anu.performRangedAttack(target, 0);
            float distanceFactor = (float) (Math.sqrt(dist) / attackRadius);
            attackTime = (int) Mth.lerp(distanceFactor, MIN_ATTACK_TIME, MAX_ATTACK_TIME);
        } else if (attackTime < 0) {
            float distanceFactor = (float) (Math.sqrt(dist) / attackRadius);
            attackTime = (int) Mth.lerp(distanceFactor, MIN_ATTACK_TIME, MAX_ATTACK_TIME);
        }
    }
}
