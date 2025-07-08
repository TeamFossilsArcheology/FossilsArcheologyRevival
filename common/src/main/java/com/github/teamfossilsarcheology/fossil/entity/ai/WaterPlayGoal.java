package com.github.teamfossilsarcheology.fossil.entity.ai;

import com.github.teamfossilsarcheology.fossil.entity.ToyBall;
import com.github.teamfossilsarcheology.fossil.entity.ToyBase;
import com.github.teamfossilsarcheology.fossil.entity.animation.ServerAnimationInfo;
import com.github.teamfossilsarcheology.fossil.entity.prehistoric.base.Prehistoric;
import com.github.teamfossilsarcheology.fossil.entity.prehistoric.base.SwimmingAnimal;
import com.github.teamfossilsarcheology.fossil.entity.util.Util;
import net.minecraft.world.entity.Entity;

import static com.github.teamfossilsarcheology.fossil.entity.prehistoric.base.PrehistoricEntityInfoAI.Attacking;

public class WaterPlayGoal<T extends Prehistoric & SwimmingAnimal> extends PlayGoal {
    private static final int ATTACK = 0;
    private static final int GRAB = 1;
    private static final int GRAB_DURATION = 55;
    private final T swimming;
    private int attackType = -1;
    private long grabStartTick = -1;

    public WaterPlayGoal(T dino, double speedModifier) {
        super(dino, speedModifier);
        this.swimming = dino;
    }

    @Override
    public boolean canContinueToUse() {
        if (attackType == GRAB && !swimming.isDoingGrabAttack()) {
            return false;
        }
        return super.canContinueToUse();
    }

    @Override
    public void start() {
        super.start();
        attackType = -1;
        grabStartTick = -1;
    }

    @Override
    protected ToyBase findPlayTarget() {
        if (dino.isInWater()) {
            return Util.getNearestEntity(ToyBall.class, dino, getTargetSearchArea(getFollowDistance()), Entity::isInWater);
        } else {
            return Util.getNearestEntity(ToyBase.class, dino, getTargetSearchArea(getFollowDistance()), toyBase -> true);
        }
    }

    @Override
    public void tick() {
        if (attackType != GRAB) {
            dino.getLookControl().setLookAt(target, 30, 30);
            if (dino.getNavigation().isDone() && !Util.canReachPrey(dino, target)) {
                dino.getNavigation().moveTo(target, speedModifier);
            }
        }
        checkAndPerformAttack(target);
    }

    @Override
    protected void checkAndPerformAttack(ToyBase target) {
        long currentTime = dino.level().getGameTime();
        if (attackType == GRAB) {
            for (Entity passenger : swimming.getPassengers()) {
                if (passenger instanceof ToyBase toy && currentTime == grabStartTick + GRAB_DURATION) {
                    swimming.stopGrabAttack(passenger);
                    swimming.moodSystem.setToyTarget(null);
                    swimming.moodSystem.useToy(toy.moodBonus);
                }
            }
        } else if (attackType == ATTACK) {
            if (attackDamageTick > 0 && currentTime >= attackDamageTick) {
                target.hurt(dino.damageSources().mobAttack(dino), 0);
                attackDamageTick = -1;
                attackType = -1;
            }
        } else if (currentTime > attackEndTick + 20 && Util.canReachPrey(dino, target)) {
            boolean tooBig = Util.isEntityLargerThan(target, 2 * swimming.getScale() / swimming.data().maxScale());
            if (swimming.aiAttackType() != Attacking.GRAB || tooBig || swimming.getRandom().nextInt(5) > 0) {
                attackType = ATTACK;
                ServerAnimationInfo animationInfo = dino.startAttack();
                attackDamageTick = (long) (currentTime + animationInfo.actionDelay);
                attackEndTick = (long) (currentTime + animationInfo.animation.length());
                if (attackDamageTick > attackEndTick) attackDamageTick = attackEndTick;
            } else {
                attackType = GRAB;
                swimming.startGrabAttack(target);
                attackEndTick = 1;
                grabStartTick = currentTime;
            }
        }
    }
}
