package com.fossil.fossil.entity.ai.anu;

import com.fossil.fossil.entity.monster.AnuBoss;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;

import java.util.EnumSet;

public class FireballAttackGoal extends Goal {

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
        setFlags(EnumSet.of(Goal.Flag.LOOK));
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
        if (hasLineOfSight != seeTime > 0) {
            seeTime = 0;
        }
        if (hasLineOfSight) {
            seeTime++;
        } else {
            seeTime--;
        }
        if (dist <= attackRadiusSqr && seeTime >= 20) {
            anu.getNavigation().stop();
        } else {
            anu.getNavigation().moveTo(target, speedModifier);
        }
        anu.getLookControl().setLookAt(target, 30, 30);

        if (--attackTime <= 0) {
            if (dist > attackRadiusSqr || !hasLineOfSight) {
                return;
            }
            float f = Mth.sqrt((float) dist) / attackRadius;
            double f1 = Math.max(0.1, Math.min(1, f));
            anu.performRangedAttack(target, (float) f1);
            attackTime = Mth.floor(f * 10 + 10);
        }
    }
}
