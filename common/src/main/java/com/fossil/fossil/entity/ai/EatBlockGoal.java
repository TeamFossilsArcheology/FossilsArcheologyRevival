package com.fossil.fossil.entity.ai;

import com.fossil.fossil.entity.prehistoric.base.Prehistoric;
import com.fossil.fossil.util.FoodMappings;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.level.LevelReader;

/**
 * A Goal that will move the entity to the closest block if the entity is hungry, it can eat the plant and the entity can see it. Afterwards it will
 * destroy the block and feed the entity.
 */
public class EatBlockGoal extends MoveToFoodGoal {
    public EatBlockGoal(Prehistoric entity) {
        super(entity, 1, 32);
    }

    @Override
    public void tick() {
        super.tick();
        if (isReachedTarget()) {
            entity.setStartEatAnimation(true);
            int foodAmount = FoodMappings.getFoodAmount(entity.level.getBlockState(targetPos).getBlock(), entity.info().diet);
            entity.feed(foodAmount);
            entity.heal(foodAmount / 10f);
            entity.playSound(SoundEvents.GENERIC_EAT, 1, 1);
            entity.level.destroyBlock(targetPos, false);
        }
    }

    @Override
    protected boolean isValidTarget(LevelReader level, BlockPos pos) {
        if (!super.isValidTarget(level, pos)) {
            return false;
        }
        return FoodMappings.getFoodAmount(level.getBlockState(pos).getBlock(), entity.info().diet) > 0 && canSeeFood(pos);
    }
}
