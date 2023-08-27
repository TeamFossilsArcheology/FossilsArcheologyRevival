package com.fossil.fossil.entity.ai;

import com.fossil.fossil.entity.ToyBase;
import com.fossil.fossil.entity.prehistoric.base.*;
import com.fossil.fossil.util.FoodMappings;
import net.minecraft.world.Difficulty;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.animal.Chicken;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.AABB;
import org.jetbrains.annotations.NotNull;

/**
 * A Goal that will set the target of an entity to either a huntable creature or a toy
 */
public class HuntAndPlayGoal extends NearestAttackableTargetGoal<LivingEntity> {
    private final Prehistoric dino;

    public HuntAndPlayGoal(Prehistoric prehistoric) {
        super(prehistoric, LivingEntity.class, true, livingEntity -> livingEntity instanceof Chicken);
        this.dino = prehistoric;
    }

    @Override
    public boolean canUse() {
        if (dino.isVehicle() || dino.isImmobile() || !super.canUse() || target == null || target.getClass().equals(dino.getClass())) {
            return false;
        }
        MoodSystem moodSystem = dino.moodSystem;
        if (target instanceof ToyBase && moodSystem.getPlayingTick() <= 0) {
            return true;
        }
        double targetWidth = target.getBoundingBox().getSize();
        if (dino.getBoundingBox().getSize() * dino.getTargetScale() < targetWidth) {
            return false;
        }
        if (target instanceof Player player) {
            if (player.isCreative() || player.level.getDifficulty() == Difficulty.PEACEFUL) {
                return false;
            }
            if (moodSystem.getMoodFace() == PrehistoricMoodType.HAPPY || moodSystem.getMoodFace() == PrehistoricMoodType.CONTENT) {
                return false;
            } else if (moodSystem.getMoodFace() == PrehistoricMoodType.ANGRY || (moodSystem.getMoodFace() == PrehistoricMoodType.SAD && !dino.isOwnedBy(target))) {
                return true;
            } else {
                return !dino.isOwnedBy(target) && dino.canDinoHunt(target, true);
            }
        }
        if (FoodMappings.getMobFoodPoints(target, dino.type().diet) > 0 || dino.aiResponseType() == PrehistoricEntityTypeAI.Response.AGGRESSIVE) {
            return !dino.isOwnedBy(target) && dino.canDinoHunt(target, true);
        }
        return false;
    }

    @Override
    protected @NotNull AABB getTargetSearchArea(double targetDistance) {
        double yDist = 4;
        if (dino instanceof PrehistoricFlying) {
            yDist = targetDistance;
        } else if (dino instanceof PrehistoricSwimming swimming) {
            yDist = swimming.doesBreachAttack() ? 50 : targetDistance;
        }
        return mob.getBoundingBox().inflate(targetDistance, yDist, targetDistance);
    }
}
