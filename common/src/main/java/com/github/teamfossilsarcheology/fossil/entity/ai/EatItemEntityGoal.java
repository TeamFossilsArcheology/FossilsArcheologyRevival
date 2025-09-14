package com.github.teamfossilsarcheology.fossil.entity.ai;

import com.github.teamfossilsarcheology.fossil.entity.animation.AnimationCategory;
import com.github.teamfossilsarcheology.fossil.entity.animation.AnimationInfo;
import com.github.teamfossilsarcheology.fossil.entity.animation.AnimationLogic;
import com.github.teamfossilsarcheology.fossil.entity.prehistoric.base.Prehistoric;
import com.github.teamfossilsarcheology.fossil.entity.util.Util;
import com.github.teamfossilsarcheology.fossil.food.FoodMappings;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.entity.EntityTypeTest;

import java.util.List;

/**
 * A Goal that will move the entity to the closest {@link ItemEntity} if the entity is hungry and can eat the item. Afterwards it will feed the entity
 * until it is no longer hungry. The cache will contain the {@link Entity#blockPosition() blockPosition} of the {@link ItemEntity} and therefor all
 * items at the block position.
 */
public class EatItemEntityGoal extends MoveToFoodGoal {

    private ItemEntity targetItem;
    private boolean recentlyAte;

    public EatItemEntityGoal(Prehistoric entity) {
        super(entity, 1, 16);
    }

    /**
     * @implNote Will return a much shorter tick amount if the entity ate an item last execution. This way multiple different itemstacks close to each
     * other can be eaten more quickly.
     */
    @Override
    protected int nextStartTick() {
        if (recentlyAte) {
            recentlyAte = false;
            return 20;
        }
        return super.nextStartTick();
    }

    /**
     * @implNote Returns only the horizontal distance to the target {@link ItemEntity} as long as the vertical difference is not too large.
     */
    @Override
    protected boolean checkReachedTarget() {
        if (Math.abs(entity.getY() - targetItem.getY()) > 3) {
            return false;
        }
        return Util.directionVecTo(targetItem, entity).horizontalDistance() <= acceptedDistance();
    }

    @Override
    public void tick() {
        super.tick();
        if (isReachedTarget()) {
            if (entity.getHunger() < entity.getMaxHunger()) {
                entity.feed(FoodMappings.getFoodAmount(targetItem.getItem().getItem(), entity.data().diet()));
                targetItem.getItem().shrink(1);
            }
            recentlyAte = true;
            if (entity.level().getGameTime() > animEndTick) {
                AnimationInfo animationInfo = entity.nextEatingAnimation();
                entity.getAnimationLogic().triggerAnimation(AnimationLogic.IDLE_CTRL, animationInfo, AnimationCategory.EAT);
                animEndTick = (long) (entity.level().getGameTime() + animationInfo.animation.length());
            }
        }
    }

    @Override
    protected boolean isValidTarget(LevelReader level, BlockPos pos) {
        if (!super.isValidTarget(level, pos)) {
            return false;
        }
        return targetItem != null && targetItem.isAlive();
    }

    /**
     * @implNote Finds the {@link ItemEntity} closest to the entity and sets the target block to the {@link Entity#blockPosition() blockPosition} of
     * that {@link ItemEntity}. If no item has been found the cache will be set to be cleared.
     */
    @Override
    protected boolean findNearestBlock() {
        List<ItemEntity> nearbyItems = entity.level().getEntities(EntityTypeTest.forClass(ItemEntity.class),
                entity.getBoundingBox().inflate(searchRange),
                itemEntity -> FoodMappings.getFoodAmount(itemEntity.getItem().getItem(), entity.data().diet()) > 0
                        && !avoidCache.contains(itemEntity.blockPosition().asLong()));
        targetItem = nearbyItems.stream().min((o1, o2) -> Double.compare(entity.distanceToSqr(o1), entity.distanceToSqr(o2))).orElse(null);
        if (targetItem == null) {
            this.clearTicks = !avoidCache.isEmpty() ? CLEAR_TICKS : 0;
            return false;
        } else {
            setTargetPos(targetItem.blockPosition());
            return true;
        }
    }
}
