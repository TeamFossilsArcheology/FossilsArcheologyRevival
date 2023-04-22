package com.fossil.fossil.entity.ai;

import com.fossil.fossil.entity.prehistoric.base.Prehistoric;
import com.fossil.fossil.util.FoodMappings;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.state.BlockState;

/**
 * A Goal that will move the entity to the closest plant if the entity is hungry, it can eat the plant and the entity can see it. Afterwards it will
 * destroy the plant and feed the entity.
 */
public class EatPlantGoal extends MoveToFoodGoal {
    public EatPlantGoal(Prehistoric entity) {
        super(entity, 1, 32);
    }

    @Override
    public void tick() {
        super.tick();
        if (isReachedTarget()) {
            BlockState blockState = entity.level.getBlockState(targetPos);
            int foodAmount = FoodMappings.getFoodAmount(blockState.getBlock(), entity.type().diet);
            entity.setHunger(Math.min(entity.getMaxHunger(), entity.getHunger() + foodAmount));
            entity.setHealth((int) Math.min(entity.getMaxHealth(), entity.getHealth() + foodAmount / 10f));
            entity.playSound(SoundEvents.GENERIC_EAT, 1, 1);
            entity.level.destroyBlock(targetPos, false);
            entity.setCurrentAnimation(entity.nextEatingAnimation());
        }
    }

    @Override
    protected boolean isValidTarget(LevelReader level, BlockPos pos) {
        if (!super.isValidTarget(level, pos)) {
            return false;
        }
        return FoodMappings.getFoodAmount(level.getBlockState(pos).getBlock(), entity.type().diet) > 0 && entity.canSeeFood(pos);
    }
}
