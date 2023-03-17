package com.fossil.fossil.entity.ai;

import com.fossil.fossil.block.ModBlocks;
import com.fossil.fossil.block.entity.FeederBlockEntity;
import com.fossil.fossil.entity.prehistoric.base.Prehistoric;
import com.fossil.fossil.util.FoodMappings;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.ai.goal.MoveToBlockGoal;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class EatFeedersAndBlocksGoal extends MoveToBlockGoal {

    private final Prehistoric entity;
    private int feedingTicks;

    public EatFeedersAndBlocksGoal(Prehistoric entity) {
        super(entity, 1, 35);
        this.entity = entity;
    }

    @Override
    public void start() {
        feedingTicks = 0;
        super.start();
    }

    @Override
    public void stop() {
        entity.shouldWander = true;
        entity.setCurrentAnimation(entity.nextIdleAnimation());
        super.stop();
    }

    @Override
    public boolean canUse() {
        if (entity.getHunger() <= 0.25 * entity.getMaxHunger()) {
            nextStartTick = 0;
        } else if (entity.getHunger() >= entity.getMaxHunger()) {
            return false;
        }
        //Movement Soft Blocked
        //Should Wander = false
        return super.canUse();
    }

    @Override
    public boolean canContinueToUse() {
        if (this.entity.getHunger() >= this.entity.getMaxHunger()) {
            return false;
        }
        //Movement Soft Blocked
        return super.canContinueToUse();
    }

    @Override
    public double acceptedDistance() {
        return 2;
    }

    @Override
    public void tick() {
        super.tick();
        if (isReachedTarget()) {
            //if shouldTarget (feeder has food & raytrace, plant is food)
            //if distance for some reason
            BlockState blockState = entity.level.getBlockState(blockPos);
            if (blockState.is(ModBlocks.FEEDER.get())) {
                FeederBlockEntity feeder = (FeederBlockEntity) entity.level.getBlockEntity(blockPos);
                //hunger < maxhunger && !feeder.isEmpty(entity)
                feedingTicks++;
                feeder.feedDinosaur(entity);
                entity.setHealth(Math.min(entity.getMaxHealth(), entity.getHealth() + feedingTicks / 4.0f));
                entity.doFoodEffect();
                entity.setCurrentAnimation(entity.nextEatingAnimation());
            } else if (FoodMappings.getFoodAmount(blockState.getBlock(), entity.type.diet) > 0) {
                int foodAmount = FoodMappings.getFoodAmount(blockState.getBlock(), entity.type.diet);
                entity.setHunger(Math.min(entity.getMaxHunger(), entity.getHunger() + foodAmount));
                entity.setHunger((int) Math.min(entity.getMaxHealth(), entity.getHealth() + foodAmount / 10f));
                entity.playSound(SoundEvents.GENERIC_EAT, 1, 1);
                entity.level.destroyBlock(blockPos, false);
                entity.setCurrentAnimation(entity.nextEatingAnimation());
            } else {
                //stop?
            }
        }
    }

    @Override
    protected boolean isValidTarget(LevelReader level, BlockPos pos) {
        BlockEntity blockEntity = level.getBlockEntity(pos);
        if (blockEntity instanceof FeederBlockEntity feeder) {
            return !feeder.isEmpty(entity.type) && entity.rayTraceFeeder(pos, false);
        }
        return false;//Plant
    }
}
