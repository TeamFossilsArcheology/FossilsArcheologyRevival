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
        dinosaur.setCurrentAnimation(dinosaur.nextChasingAnimation());
    }

    @Override
    protected void checkAndPerformAttack(LivingEntity enemy, double distToEnemySqr) {
        Prehistoric dinosaur = (Prehistoric) mob;
        var currentAttackAnimation = dinosaur.getCurrentAnimation();

        if (!(currentAttackAnimation instanceof Prehistoric.ServerAttackAnimationInfo)) {
            var attackAnimations = dinosaur.nextAttackAnimation();
            double distanceSqr = this.mob.getBbWidth() * this.mob.getBbWidth() * 2 + enemy.getBbWidth();
            if (distToEnemySqr > distanceSqr || !isTimeToAttack()) return;

            resetAttackCooldown();
            dinosaur.setCurrentAnimation(attackAnimations);

            TimerQueue<MinecraftServer> queue = ((ServerLevelData)dinosaur.level.getLevelData()).getScheduledEvents();
            long gameTime = dinosaur.level.getGameTime();
            for (int attackDelay : attackAnimations.attackDelays) {
                queue.schedule(dinosaur.getStringUUID(), gameTime + attackDelay, new DisposableTask((unused1, unused2, unused3) -> {
                    if (dinosaur.isAlive() && enemy.isAlive()) {
                        dinosaur.doHurtTarget(enemy);
                    }
                }));
            }
        }
    }
}