package com.fossil.fossil.entity.ai;

import com.fossil.fossil.entity.prehistoric.base.PrehistoricSwimming;
import net.minecraft.world.Difficulty;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;

import java.util.EnumSet;

public class BreachAttackGoal extends Goal {
    private final PrehistoricSwimming dino;
    private final double speedModifier;
    private Vec3 targetPos;
    private long lastCanUseCheck;
    private long lastBreachEnd;
    private boolean breachTargetReached;
    private static final long COOLDOWN_BETWEEN_CAN_USE_CHECKS = 20;

    public BreachAttackGoal(PrehistoricSwimming dino, double speed) {
        this.dino = dino;
        this.speedModifier = speed;
        this.setFlags(EnumSet.of(Goal.Flag.MOVE));
    }

    @Override
    public boolean canUse() {
        long l = dino.level.getGameTime();
        if (l - lastCanUseCheck < COOLDOWN_BETWEEN_CAN_USE_CHECKS && l - lastBreachEnd < 120) {
            return false;
        }
        lastCanUseCheck = l;
        LivingEntity target = dino.getTarget();
        if (cannotTargetEntity(target)) {
            return false;
        } else if (dino.isImmobile() || dino.isFleeing() || !dino.isInWater()) {
            return false;
        } else if (dino.level.getDifficulty() == Difficulty.PEACEFUL && target instanceof Player) {
            return false;
        }
        targetPos = target.position();
        return true;
    }

    private boolean cannotTargetEntity(LivingEntity entity) {
        return entity == null || !entity.isAlive() || dino.isEntitySubmerged(entity) || !PrehistoricSwimming.isOverWater(entity) || dino.hasPassenger(entity);
    }

    @Override
    public boolean canContinueToUse() {
        LivingEntity target = dino.getTarget();
        if (target == null || !target.isAlive() || dino.isEntitySubmerged(target) || !PrehistoricSwimming.isOverWater(target)) {
            return false;
        }
        return !breachTargetReached || (!dino.isInWater() && !dino.isOnGround());
    }

    @Override
    public void start() {
        dino.getNavigation().stop();
        dino.getMoveControl().setWantedPosition(targetPos.x, targetPos.y, targetPos.z, speedModifier);
        dino.setBreaching(true);
    }

    @Override
    public void stop() {
        breachTargetReached = false;
        targetPos = null;
        lastBreachEnd = dino.level.getGameTime();
        dino.setBreaching(false);
    }

    @Override
    public void tick() {
        LivingEntity target = dino.getTarget();
        if (target == null) {
            return;
        }

        if (dino.canReachPrey(target)) {
            dino.setBreaching(false);
            breachTargetReached = true;
            boolean tooBig = !PrehistoricSwimming.isEntitySmallerThan(target, 2 * dino.getScale() / dino.data().maxScale());
            if (tooBig || dino.getRandom().nextInt(5) > 0) {
                dino.swing(InteractionHand.MAIN_HAND);
                dino.attackTarget(target);
            } else {
                dino.startGrabAttack(target);
            }
        }
    }
}
