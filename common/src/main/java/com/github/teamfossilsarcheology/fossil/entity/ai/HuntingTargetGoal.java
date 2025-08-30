package com.github.teamfossilsarcheology.fossil.entity.ai;

import com.github.teamfossilsarcheology.fossil.entity.prehistoric.base.*;
import com.github.teamfossilsarcheology.fossil.entity.prehistoric.swimming.Trilobite;
import com.github.teamfossilsarcheology.fossil.food.FoodMappings;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.target.TargetGoal;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.npc.Villager;
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
        if (dino.isHungry() && !dino.isBaby()) {
            target = findHuntingTarget();
            return target != null;
        }
        return false;
    }

    private boolean canTargetHumanoid(LivingEntity entity) {
        if (dino.moodSystem.getMoodFace() == PrehistoricMoodType.HAPPY || dino.moodSystem.getMoodFace() == PrehistoricMoodType.CONTENT) {
            return false;
        } else if (dino.moodSystem.getMoodFace() == PrehistoricMoodType.ANGRY || dino.moodSystem.getMoodFace() == PrehistoricMoodType.SAD) {
            return true;
        } else {
            //Calm and can hunt
            //TODO: Maybe use attack damage to limit
            return FoodMappings.getMobFoodPoints(entity, dino.data().diet()) > 0 && dino.getBbWidth() * dino.getTargetScale() >= entity.getBbWidth();
        }
    }

    private boolean canTargetPlayer(Player player) {
        if (player.isCreative()) {
            return false;
        }
        return canTargetHumanoid(player);
    }

    @Override
    public boolean canContinueToUse() {
        if (!super.canContinueToUse()) {
            return false;
        }
        if (target instanceof Player player) {
            return !player.isCreative() && canTargetPlayer(player);
        }
        return true;
    }

    private boolean canTarget(LivingEntity target) {
        if (dino.aiResponseType() == PrehistoricEntityInfoAI.Response.SCARED) {
            return false;
        }
        if (target instanceof Player player) {
            return canTargetPlayer(player);
        }
        if (target instanceof Villager) {
            return canTargetHumanoid(target);
        }
        if (dino instanceof Trilobite && target instanceof Trilobite) {
            return false;
        }
        boolean canTarget = true;
        if (dino instanceof PrehistoricSwimming swimming && (!target.isInWater() && !swimming.canHuntMobsOnLand())) {
            canTarget = false;
        }
        if (target.isInWater() && dino.aiMovingType() != PrehistoricEntityInfoAI.Moving.AQUATIC && dino.aiMovingType() != PrehistoricEntityInfoAI.Moving.SEMI_AQUATIC) {
            BlockPos pos = BlockPos.containing(target.getX(), target.getBoundingBox().maxY, target.getZ());
            if (!dino.level().getFluidState(pos).isEmpty() && !dino.level().getFluidState(pos.above()).isEmpty()) {
                //TODO: The idea is that mobs that can only float dont try to attack underwater mobs but needs improvement
                canTarget = false;
            }
        }
        boolean isFood = FoodMappings.getMobFoodPoints(target, dino.data().diet()) > 0;
        boolean smallEnough = dino.getBoundingBox().getSize() * dino.getTargetScale() >= target.getBoundingBox().getSize();
        //System.out.println(dino.info().name() + " " + target.getType().getDescriptionId() + " " + isFood + " " + smallEnough);
        return canTarget && isFood && smallEnough && !target.getClass().equals(dino.getClass());
    }

    /**
     * If the returned entity is a player the following will be true:
     * {@code !isCreative() && (!isOwned() || mood == ANGRY) && difficulty != PEACEFUL}
     * If it's anything else the following will be true:
     * {@code getClass() != getClass() && target.size < acceptedSize}
     */
    private LivingEntity findHuntingTarget() {
        return mob.level().getNearestEntity(LivingEntity.class, huntTargetConditions, mob, mob.getX(), mob.getEyeY(), mob.getZ(),
                getTargetSearchArea(getFollowDistance()));
    }

    private @NotNull AABB getTargetSearchArea(double targetDistance) {
        double yDist = 4;
        if (dino instanceof PrehistoricFlying || dino instanceof PrehistoricSwimming) {
            yDist = targetDistance;
        }
        return mob.getBoundingBox().inflate(targetDistance, yDist, targetDistance);
    }
}
