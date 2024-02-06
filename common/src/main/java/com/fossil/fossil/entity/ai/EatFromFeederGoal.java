package com.fossil.fossil.entity.ai;

import com.fossil.fossil.block.entity.FeederBlockEntity;
import com.fossil.fossil.entity.prehistoric.base.Prehistoric;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.LevelReader;


/**
 * A Goal that will move the entity to the closest feeder if the entity is hungry, the feeder contains its food and the entity can see it.
 * Afterwards it will feed the entity until it is no longer hungry.
 */
public class EatFromFeederGoal extends MoveToFoodGoal {

    public EatFromFeederGoal(Prehistoric entity) {
        super(entity, 1, 32);
    }

    @Override
    public void tick() {
        super.tick();
        if (isReachedTarget()) {
            if (entity.level.getBlockEntity(targetPos) instanceof FeederBlockEntity feeder) {
                feedingTicks++;
                feeder.feedDinosaur(entity);
                entity.heal(0.1f);
                if (feedingTicks % 4 == 0) {
                    entity.doFoodEffect();
                }
                entity.setStartEatAnimation(true);
            }
        }
    }

    @Override
    protected boolean isValidTarget(LevelReader level, BlockPos pos) {
        if (!super.isValidTarget(level, pos)) {
            return false;
        }
        return level.getBlockEntity(pos) instanceof FeederBlockEntity feeder && !feeder.isEmpty(entity.info().diet) && entity.canSeeFood(pos);
    }
}
