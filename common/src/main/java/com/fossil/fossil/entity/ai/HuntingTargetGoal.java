package com.fossil.fossil.entity.ai;

import com.fossil.fossil.entity.prehistoric.base.Prehistoric;
import com.fossil.fossil.entity.prehistoric.base.PrehistoricFlying;
import com.fossil.fossil.entity.prehistoric.base.PrehistoricMoodType;
import com.fossil.fossil.entity.prehistoric.base.PrehistoricSwimming;
import com.fossil.fossil.util.FoodMappings;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.target.TargetGoal;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.AABB;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.EnumSet;

/**
 * A Goal that will set the target of an entity to either a huntable creature or a toy
 */
public class HuntingTargetGoal extends TargetGoal {
    private static final int DEFAULT_RANDOM_INTERVAL = 10;
    private final int randomInterval = reducedTickDelay(DEFAULT_RANDOM_INTERVAL);
    private final Prehistoric dino;
    private final TargetingConditions huntTargetConditions = TargetingConditions.forCombat().range(getFollowDistance()).selector(this::canTarget);
    @Nullable
    private LivingEntity target;

    public HuntingTargetGoal(Prehistoric prehistoric) {
        super(prehistoric, true, true);
        this.dino = prehistoric;
        this.setFlags(EnumSet.of(Flag.TARGET));
    }

    @Override
    public void start() {
        mob.setTarget(target);
        dino.moodSystem.setToyTarget(null);
        super.start();
    }

    @Override
    public void stop() {
        super.stop();
        target = null;
    }

    @Override
    public boolean canUse() {
        if (randomInterval > 0 && mob.getRandom().nextInt(randomInterval) != 0 || dino.isImmobile()) {
            return false;
        }
        if (dino.isHungry()) {
            target = findHuntingTarget();
            if (target != null) {
                if (target instanceof Player player) {
                    return canTargetPlayer(player);
                }
                return true;
            }
        }
        return false;
    }

    private boolean canTargetPlayer(Player player) {
        if (dino.moodSystem.getMoodFace() == PrehistoricMoodType.HAPPY || dino.moodSystem.getMoodFace() == PrehistoricMoodType.CONTENT) {
            return false;
        } else if (dino.moodSystem.getMoodFace() == PrehistoricMoodType.ANGRY || dino.moodSystem.getMoodFace() == PrehistoricMoodType.SAD) {
            return true;
        } else {
            //Calm and can hunt
            return FoodMappings.getMobFoodPoints(player, dino.info().diet) > 0 && dino.getBbWidth() * dino.getTargetScale() >= player.getBbWidth();
        }
    }

    @Override
    public boolean canContinueToUse() {
        if (!super.canContinueToUse()) {
            return false;
        }
        if (target instanceof Player player) {
            return !player.isCreative() && canTargetPlayer(player);
        }
        if (dino instanceof PrehistoricSwimming swimming && !swimming.canDoBreachAttack()) {
            return target.isInWater() || swimming.canHuntMobsOnLand();
        }
        return true;
    }

    private boolean canTarget(LivingEntity target) {
        if (target instanceof Player player) {
            return !player.isCreative();
        }
        boolean canTarget = false;
        if (dino instanceof PrehistoricSwimming swimming) {
            if (swimming.canDoBreachAttack() && PrehistoricSwimming.isOverWater(target)) {
                canTarget = true;
            } else {
                canTarget = target.isInWater() || swimming.canHuntMobsOnLand();
            }
        }
        boolean isFood = FoodMappings.getMobFoodPoints(target, dino.info().diet) > 0;
        boolean smallEnough = dino.getBoundingBox().getSize() * dino.getTargetScale() >= target.getBoundingBox().getSize();
        //System.out.println(dino.info().name() + " " + target.getType().getDescriptionId() + " " + isFood + " " + smallEnough);
        return canTarget && (isFood
                && smallEnough
                && !target.getClass().equals(dino.getClass()));
    }

    /**
     * If the returned entity is a player the following will be true:
     * {@code !isCreative() && (!isOwned() || mood == ANGRY) && difficulty != PEACEFUL}
     * If it's anything else the following will be true:
     * {@code getClass() != getClass() && target.size < acceptedSize}
     */
    private LivingEntity findHuntingTarget() {
        return mob.level.getNearestEntity(mob.level.getEntitiesOfClass(LivingEntity.class, getTargetSearchArea(getFollowDistance()), livingEntity -> true),
                huntTargetConditions, mob, mob.getX(), mob.getEyeY(), mob.getZ());
    }

    private @NotNull AABB getTargetSearchArea(double targetDistance) {
        double yDist = 4;
        if (dino instanceof PrehistoricFlying) {
            yDist = targetDistance;
        } else if (dino instanceof PrehistoricSwimming swimming) {
            yDist = swimming.canDoBreachAttack() ? 50 : targetDistance;
        }
        return mob.getBoundingBox().inflate(targetDistance, yDist, targetDistance);
    }
}
