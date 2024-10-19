package com.github.teamfossilsarcheology.fossil.entity.ai;

import com.github.teamfossilsarcheology.fossil.config.FossilConfig;
import com.github.teamfossilsarcheology.fossil.entity.animation.AnimationCategory;
import com.github.teamfossilsarcheology.fossil.entity.animation.AnimationLogic;
import com.github.teamfossilsarcheology.fossil.entity.prehistoric.base.Prehistoric;
import com.github.teamfossilsarcheology.fossil.entity.util.Util;
import com.github.teamfossilsarcheology.fossil.util.FoodMappings;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.LevelReader;
import software.bernie.geckolib3.core.builder.Animation;

/**
 * A Goal that will move the entity to the closest block if the entity is hungry, it can eat the plant and the entity can see it. Afterwards it will
 * destroy the block and feed the entity.
 */
public class EatBlockGoal extends MoveToFoodGoal {
    public EatBlockGoal(Prehistoric entity) {
        super(entity, 1, 32);
    }

    @Override
    public boolean canUse() {
        if (!FossilConfig.isEnabled(FossilConfig.DINOS_EAT_BLOCKS)) {
            return false;
        }
        return super.canUse();
    }

    @Override
    public void tick() {
        super.tick();
        if (isReachedTarget()) {
            int foodAmount = FoodMappings.getFoodAmount(entity.level.getBlockState(targetPos).getBlock(), entity.data().diet());
            entity.feed(foodAmount);
            entity.heal(foodAmount / 10f);
            entity.level.destroyBlock(targetPos, false);
            if (entity.level.getGameTime() > animEndTick) {
                Animation anim = entity.nextEatingAnimation();
                entity.getAnimationLogic().triggerAnimation(AnimationLogic.IDLE_CTRL, anim, AnimationCategory.EAT);
                animEndTick = (long) (entity.level.getGameTime() + anim.animationLength);
            }
        }
    }

    @Override
    protected boolean isValidTarget(LevelReader level, BlockPos pos) {
        if (!super.isValidTarget(level, pos)) {
            return false;
        }
        return FoodMappings.getFoodAmount(level.getBlockState(pos).getBlock(), entity.data().diet()) > 0 && Util.canSeeFood(entity, pos);
    }
}
