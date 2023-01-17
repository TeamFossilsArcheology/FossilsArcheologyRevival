package com.fossil.fossil.entity.ai;

import com.fossil.fossil.entity.prehistoric.base.Prehistoric;
import com.fossil.fossil.util.DisposableTask;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.Difficulty;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.storage.ServerLevelData;
import net.minecraft.world.level.timers.TimerQueue;

import java.util.Arrays;

public class DinoMeleeAttackAI extends MeleeAttackGoal {
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
    public void start() {
        super.start();
        Prehistoric dinosaur = (Prehistoric) mob;
        dinosaur.setCurrentAnimation(dinosaur.getChasingAnimation());
    }

    @Override
    protected void checkAndPerformAttack(LivingEntity enemy, double distToEnemySqr) {
        Prehistoric dinosaur = (Prehistoric) mob;
        var attackAnimations = dinosaur.getAttackAnimationsWithDelay();
        var currentAttackAnimation = Arrays.stream(attackAnimations)
            .filter(entry -> entry.animation().equals(dinosaur.getCurrentAnimation()))
            .findAny();

        if (currentAttackAnimation.isEmpty()) {
            double distanceSqr = this.mob.getBbWidth() * this.mob.getBbWidth() * 2 + enemy.getBbWidth();
            if (distToEnemySqr > distanceSqr || !isTimeToAttack()) return;

            int index = dinosaur.getRandom().nextInt(attackAnimations.length);
            Prehistoric.AttackAnimationInfo newAnimation = attackAnimations[index];
            dinosaur.setCurrentAnimation(newAnimation.animation());

            TimerQueue<MinecraftServer> queue = ((ServerLevelData)dinosaur.level.getLevelData()).getScheduledEvents();
            long gameTime = dinosaur.level.getGameTime();
            for (int attackDelay : newAnimation.attackDelays()) {
                queue.schedule(dinosaur.getStringUUID(), gameTime + attackDelay, new DisposableTask((unused1, unused2, unused3) -> {
                    if (dinosaur.isAlive() && enemy.isAlive()) {
                        dinosaur.doHurtTarget(enemy);
                    }
                    resetAttackCooldown();
                }));
            }

            queue.schedule(dinosaur.getStringUUID(), gameTime + newAnimation.animationLength(), new DisposableTask((u1, u2, u3) -> {
                if (dinosaur.getCurrentAnimation().equals(newAnimation.animation())) {
                    dinosaur.setCurrentAnimation(dinosaur.getIdleAnimation());
                }
            }));
        }
    }
}