package com.fossil.fossil.entity.ai;

import com.fossil.fossil.entity.ToyBase;
import com.fossil.fossil.entity.animation.AnimationInfoManager;
import com.fossil.fossil.entity.prehistoric.base.Prehistoric;
import com.fossil.fossil.entity.prehistoric.base.PrehistoricFlying;
import com.fossil.fossil.entity.prehistoric.base.PrehistoricSwimming;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.level.pathfinder.Path;
import net.minecraft.world.phys.AABB;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

//TODO: Needs big polish. Especially the movement. Should ideally stop before walking into the toy but also be able to follow ball. Should also play multiple times with toy(though only count once towards mood)
public class PlayGoal extends Goal {
    protected static final long COOLDOWN_BETWEEN_CAN_USE_CHECKS = 20L;
    protected long lastCanUseCheck;
    private final Prehistoric dino;
    private final double speedModifier;
    @Nullable
    protected Path path;
    private ToyBase target;
    protected long attackEndTick = -1;
    protected long attackDamageTick = -1;

    public PlayGoal(Prehistoric dino, double speedModifier) {
        this.dino = dino;
        this.speedModifier = speedModifier;
    }

    @Override
    public boolean canUse() {
        long l = dino.level.getGameTime();
        if (l - lastCanUseCheck < COOLDOWN_BETWEEN_CAN_USE_CHECKS) {
            return false;
        }
        lastCanUseCheck = l;
        if (dino.isImmobile() || dino.isFleeing() || dino.getTarget() != null) {
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
        return getAttackReachSqr(target) >= dino.distanceToSqr(target);
    }

    protected double getAttackReachSqr(Entity attackTarget) {
        return dino.getBbWidth() * 2 * (dino.getBbWidth() * 2) + attackTarget.getBbWidth();
    }

    @Override
    public boolean canContinueToUse() {
        if (dino.level.getGameTime() < attackEndTick) {
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
        if (dino.distanceToSqr(currentToyTarget) > d * d) {
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
        double dist = dino.distanceToSqr(target);
        double attackReach = dino.getBbWidth() * dino.getBbWidth() * 2 + target.getBbWidth();
        if (dist > attackReach && dino.getNavigation().isDone()) {
            dino.getNavigation().moveTo(target, speedModifier);
        }
        checkAndPerformAttack(target, dist);
    }
    protected void checkAndPerformAttack(ToyBase target, double distToEnemySqr) {
        double attackReach = dino.getBbWidth() * dino.getBbWidth() * 2 + target.getBbWidth();
        long currentTime = dino.level.getGameTime();
        if (distToEnemySqr <= attackReach) {
            if (currentTime > attackEndTick + 20) {
                AnimationInfoManager.ServerAnimationInfo animation = dino.startAttack();
                attackDamageTick = (long) (currentTime + animation.actionDelay);
                attackEndTick = (long) (currentTime + animation.animationLength);
                if (attackDamageTick > attackEndTick) attackDamageTick = attackEndTick;
                dino.getNavigation().stop();
            }
            if (attackDamageTick > 0 && currentTime == attackDamageTick) {
                target.hurt(DamageSource.mobAttack(dino), 0);
                attackDamageTick = -1;
            }
        }
    }

    private ToyBase findPlayTarget() {
        double maxDist = getFollowDistance();
        double shortestDist = Double.MAX_VALUE;
        List<ToyBase> entities = dino.level.getEntitiesOfClass(ToyBase.class, getTargetSearchArea(maxDist));
        ToyBase newTarget = null;
        maxDist = Math.max(maxDist, 2);
        for (ToyBase toy : entities) {
            double currentDist = dino.distanceToSqr(toy.getX(), toy.getY(), toy.getZ());
            if (currentDist > maxDist * maxDist || currentDist > shortestDist || !dino.getSensing().hasLineOfSight(toy)) {
                continue;
            }
            shortestDist = currentDist;
            newTarget = toy;
        }
        return newTarget;
    }

    private @NotNull AABB getTargetSearchArea(double targetDistance) {
        double yDist = 4;
        if (dino instanceof PrehistoricFlying) {
            yDist = targetDistance;
        } else if (dino instanceof PrehistoricSwimming swimming) {
            yDist = swimming.canDoBreachAttack() ? 50 : targetDistance;
        }
        return dino.getBoundingBox().inflate(targetDistance, yDist, targetDistance);
    }

    protected double getFollowDistance() {
        return dino.getAttributeValue(Attributes.FOLLOW_RANGE) * 2;
    }
}
