package com.fossil.fossil.entity.ai;

import com.fossil.fossil.entity.prehistoric.base.Prehistoric;
import com.fossil.fossil.entity.util.EntityToyBase;
import net.minecraft.world.Difficulty;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.pathfinder.Path;

import java.util.EnumSet;

public class DinoMeleeAttackAI extends Goal {
    private final Prehistoric entity;
    private final double speed;
    private final boolean memory;
    private int attackAfter = 20;
    private Path currentPath;

    public DinoMeleeAttackAI(Prehistoric entity, double speed, boolean memory) {
        this.entity = entity;
        this.speed = speed;
        this.memory = memory;
        // Move = 0001;
        // Look = 0010;
        setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
    }

    @Override
    public boolean canUse() {
        LivingEntity target = entity.getTarget();
        if (target == null || !target.isAlive()) {
            return false;
        } else if (entity.isImmobile()) {
            return false;
        } else if (entity.level.getDifficulty() == Difficulty.PEACEFUL && target instanceof Player) {
            return false;
        }
        if (entity.isFleeing()) {
            return false;
        }
        currentPath = entity.getNavigation().createPath(target, 0);
        return currentPath != null;
    }

    @Override
    public boolean canContinueToUse() {
        LivingEntity target = this.entity.getTarget();
        return target != null && (target.isAlive() && (!memory ? !entity.getNavigation().isDone() : entity.isWithinRestriction(target.blockPosition())));
    }

    @Override
    public void start() {
        if (entity.getControllingPassenger() == null) {
            entity.getNavigation().moveTo(currentPath, speed);
        }
    }

    @Override
    public void stop() {
        entity.getNavigation().stop();
    }

    @Override
    public void tick() {
        LivingEntity target = entity.getTarget();
        if (target == null) {
            return;
        }
        if (target instanceof EntityToyBase && entity.ticksTillPlay > 0) {
            entity.setTarget(null);
            return;
        }
        if (entity.getControllingPassenger() == null) {
            entity.getNavigation().moveTo(target, speed);
        }
        attackAfter = Math.max(attackAfter - 1, 0);
        if (entity.getAttackBounds().intersects(target.getBoundingBox())) {
            if (attackAfter == 0) {
                entity.doHurtTarget(target);
                attackAfter = 20;
            } else {
            }
        } else {
            entity.getLookControl().setLookAt(target, 30.0F, 30.0F);
        }
    }
}