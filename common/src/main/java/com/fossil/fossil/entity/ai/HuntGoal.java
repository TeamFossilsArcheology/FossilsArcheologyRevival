package com.fossil.fossil.entity.ai;

import com.fossil.fossil.entity.ToyBase;
import com.fossil.fossil.entity.prehistoric.base.Prehistoric;
import com.fossil.fossil.entity.prehistoric.base.PrehistoricEntityTypeAI;
import com.fossil.fossil.entity.prehistoric.base.PrehistoricMoodType;
import com.fossil.fossil.util.FoodMappings;
import net.minecraft.world.Difficulty;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.AABB;

public class HuntGoal extends NearestAttackableTargetGoal<LivingEntity> {
    private final Prehistoric dino;
    public HuntGoal(Prehistoric prehistoric) {
        super(prehistoric, LivingEntity.class, true);
        this.dino = prehistoric;
    }

    @Override
    public boolean canUse() {
        if (dino.isVehicle() || dino.isImmobile() || !super.canUse() || target == null || target.getClass().equals(dino.getClass())) {
            return false;
        }
        if (target instanceof ToyBase && dino.ticksTillPlay <= 0) {
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
            if (dino.getMoodFace() == PrehistoricMoodType.HAPPY || dino.getMoodFace() == PrehistoricMoodType.CONTENT) {
                return false;
            } else if (dino.getMoodFace() == PrehistoricMoodType.ANGRY || (dino.getMoodFace() == PrehistoricMoodType.SAD && !dino.isOwnedBy(target))) {
                return true;
            } else {
                return !dino.isOwnedBy(target) && dino.canDinoHunt(target, true);
            }
        }
        if (FoodMappings.getMobFoodPoints(target, dino.type.diet) > 0 || dino.aiResponseType() == PrehistoricEntityTypeAI.Response.AGRESSIVE) {
            return !dino.isOwnedBy(target) && dino.canDinoHunt(target, true);
        }
        return false;
    }

    @Override
    protected AABB getTargetSearchArea(double targetDistance) {
        double yDist = 4;
        //TODO: If flying
        //TODO: If Swimming
        return mob.getBoundingBox().inflate(targetDistance, yDist, targetDistance);
    }
}
