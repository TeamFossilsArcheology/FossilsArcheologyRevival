package com.github.teamfossilsarcheology.fossil.entity.ai;

import com.github.teamfossilsarcheology.fossil.entity.ToyBase;
import com.github.teamfossilsarcheology.fossil.entity.animation.ServerAnimationInfo;
import com.github.teamfossilsarcheology.fossil.entity.prehistoric.base.OrderType;
import com.github.teamfossilsarcheology.fossil.entity.prehistoric.base.Prehistoric;
import com.github.teamfossilsarcheology.fossil.entity.prehistoric.base.PrehistoricFlying;
import com.github.teamfossilsarcheology.fossil.entity.prehistoric.base.PrehistoricSwimming;
import com.github.teamfossilsarcheology.fossil.entity.util.Util;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.level.pathfinder.Path;
import net.minecraft.world.phys.AABB;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class PlayGoal extends Goal {
    protected static final long COOLDOWN_BETWEEN_CAN_USE_CHECKS = 20L;
    protected long lastCanUseCheck;
    protected final Prehistoric dino;
    protected final double speedModifier;
    @Nullable
    protected Path path;
    protected ToyBase target;
    protected long attackEndTick = -1;
    protected long attackDamageTick = -1;

    public PlayGoal(Prehistoric dino, double speedModifier) {
        this.dino = dino;
        this.speedModifier = speedModifier;
    }

    @Override
    public boolean canUse() {
        long l = dino.level().getGameTime();
        if (l - lastCanUseCheck < COOLDOWN_BETWEEN_CAN_USE_CHECKS) {
            return false;
        }
        lastCanUseCheck = l;
        if (dino.isFleeing() || dino.getTarget() != null || dino.getCurrentOrder() != OrderType.WANDER) {
            return false;
        }
        if (dino.moodSystem.getPlayingCooldown() > 0 || dino.moodSystem.getMood() >= 100) {
            return false;
        }
        target = findPlayTarget();
        if (target == null) {
            return false;
        }
        path = dino.getNavigation().createPath(target, 0);
        if (path != null) {
            return true;
        }
        return Util.canReachPrey(dino, target);
    }

    @Override
    public boolean canContinueToUse() {
        if (dino.level().getGameTime() < attackEndTick) {
            //Prevent the goal from ending before the animation is over
            return true;
        }
        ToyBase currentToyTarget = dino.moodSystem.getToyTarget();
        if (currentToyTarget == null) {
            currentToyTarget = target;
        }
        if (currentToyTarget == null || dino.moodSystem.getPlayingCooldown() > 0) {
            return false;
        }
        double d = getFollowDistance();
        if (dino.distanceToSqr(currentToyTarget) > d * d || dino.getCurrentOrder() != OrderType.WANDER) {
            return false;
        }
        dino.moodSystem.setToyTarget(currentToyTarget);
        return true;
    }

    @Override
    public void start() {
        dino.getNavigation().moveTo(path, speedModifier);
        dino.moodSystem.setToyTarget(target);
        attackEndTick = -1;
        attackDamageTick = -1;
    }

    @Override
    public void stop() {
        dino.moodSystem.setToyTarget(null);
        target = null;
    }

    @Override
    public boolean requiresUpdateEveryTick() {
        return true;
    }

    @Override
    public void tick() {
        dino.getLookControl().setLookAt(target, 30, 30);
        if (dino.getNavigation().isDone() && !Util.canReachPrey(dino, target)) {
            dino.getNavigation().moveTo(target, speedModifier);
        }
        checkAndPerformAttack(target);
    }

    protected void checkAndPerformAttack(ToyBase target) {
        long currentTime = dino.level().getGameTime();
        if (Util.canReachPrey(dino, target)) {
            if (currentTime > attackEndTick + 20) {
                ServerAnimationInfo animation = dino.startAttack();
                attackDamageTick = (long) (currentTime + animation.actionDelay);
                attackEndTick = (long) (currentTime + animation.animation.length());
                if (attackDamageTick > attackEndTick) attackDamageTick = attackEndTick;
                dino.getNavigation().stop();
            }
            if (attackDamageTick > 0 && currentTime == attackDamageTick) {
                target.hurt(dino.damageSources().mobAttack(dino), 0);
                attackDamageTick = -1;
            }
        }
    }

    protected ToyBase findPlayTarget() {
        return Util.getNearestEntity(ToyBase.class, dino, getTargetSearchArea(getFollowDistance()), toyBase -> true);
    }

    protected @NotNull AABB getTargetSearchArea(double targetDistance) {
        double yDist = 4;
        if (dino instanceof PrehistoricFlying || (dino instanceof PrehistoricSwimming && dino.isSwimming())) {
            yDist = targetDistance;
        }
        return dino.getBoundingBox().inflate(targetDistance, yDist, targetDistance);
    }

    protected double getFollowDistance() {
        return dino.getAttributeValue(Attributes.FOLLOW_RANGE) * 2;
    }
}
