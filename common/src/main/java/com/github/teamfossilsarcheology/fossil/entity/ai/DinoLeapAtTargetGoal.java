package com.github.teamfossilsarcheology.fossil.entity.ai;

import com.github.teamfossilsarcheology.fossil.entity.animation.ServerAnimationInfo;
import com.github.teamfossilsarcheology.fossil.entity.prehistoric.base.PrehistoricLeaping;
import com.github.teamfossilsarcheology.fossil.entity.prehistoric.system.LeapSystem;
import com.github.teamfossilsarcheology.fossil.entity.util.Util;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;

import java.util.EnumSet;

public class DinoLeapAtTargetGoal extends DelayedAttackGoal<PrehistoricLeaping> {
    private static final int ATTACK = 0;
    private static final int LEAP = 1;
    private int attackType = -1;
    private int lastAttackType = -1;
    private long lastLeapTick = -1;

    public DinoLeapAtTargetGoal(PrehistoricLeaping dino) {
        super(dino, 1.15, false);
        this.setFlags(EnumSet.of(Flag.JUMP, Flag.MOVE, Flag.LOOK));
    }

    @Override
    public boolean canUse() {
        if (!mob.isOnGround()) {
            return false;
        }
        return super.canUse();
    }

    @Override
    public void start() {
        super.start();
        attackType = -1;
    }

    @Override
    public void stop() {
        super.stop();
        mob.getLeapSystem().setAttackRiding(false);
        mob.stopRiding();
    }

    @Override
    protected boolean canUpdateMovement() {
        return attackType == ATTACK || attackType == -1;
    }

    protected void checkAndPerformAttack(LivingEntity enemy) {
        long currentTime = mob.level.getGameTime();
        if (attackType == ATTACK) {
            if (Util.canReachPrey(mob, enemy) && attackDamageTick > 0 && currentTime >= attackDamageTick) {
                mob.attackTarget(enemy);
                attackDamageTick = -1;
                attackType = -1;
            }
        } else if (currentTime > attackEndTick + 20) {
            if (mob.getRandom().nextInt(5) > 0 && isInRange(enemy)) {
                attackType = ATTACK;
                lastAttackType = attackType;
                ServerAnimationInfo animation = mob.startAttack();
                attackEndTick = (long) (currentTime + animation.animation.animationLength);
                attackDamageTick = Math.min((long) (currentTime + animation.actionDelay), attackEndTick);
            } else if ((lastAttackType != LEAP || currentTime > lastLeapTick + 60) && !enemy.isVehicle() && mob.distanceToSqr(enemy) < LeapSystem.JUMP_DISTANCE) {
                attackType = LEAP;
                lastAttackType = attackType;
                mob.getLeapSystem().setLeapTarget(enemy);
                lastLeapTick = currentTime;
            }
        }
    }

    public void doLeapMovement() {
        mob.lookAt(mob.getTarget(), 100, 100);
        Vec3 offset = mob.getTarget().position().subtract(mob.position()).add(0, mob.getTarget().getBbHeight(), 0);
        mob.setDeltaMovement(offset.normalize());
    }
}
