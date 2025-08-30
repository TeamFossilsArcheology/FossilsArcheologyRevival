package com.github.teamfossilsarcheology.fossil.entity.ai;

import com.github.teamfossilsarcheology.fossil.entity.animation.AnimationCategory;
import com.github.teamfossilsarcheology.fossil.entity.animation.AnimationInfo;
import com.github.teamfossilsarcheology.fossil.entity.animation.AnimationLogic;
import com.github.teamfossilsarcheology.fossil.entity.prehistoric.base.Prehistoric;
import com.github.teamfossilsarcheology.fossil.food.Diet;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.state.BlockState;

import java.util.EnumSet;

public class PassiveFoodGoal extends MoveToFoodGoal {
    public PassiveFoodGoal(Prehistoric entity) {
        super(entity, 1, 1);
        this.setFlags(EnumSet.noneOf(Flag.class));
    }

    @Override
    public boolean canUse() {
        if (entity.onGround() && entity.data().diet() == Diet.PASSIVE && entity.getHunger() < entity.getMaxHunger()) {
            BlockState state = entity.level().getBlockState(entity.blockPosition().below());
            return state.is(BlockTags.SAND) || state.is(BlockTags.DIRT);
        }
        return false;
    }

    @Override
    public void tick() {
        feedingTicks++;
        if (entity.getHunger() < entity.getMaxHunger()) {
            //Prevent overfeeding when goal is not done due to the running animation
            entity.feed(5);
        }
        entity.heal(0.1f);
        if (entity.level().getGameTime() > animEndTick) {
            AnimationInfo animationInfo = entity.nextEatingAnimation();
            entity.getAnimationLogic().triggerAnimation(AnimationLogic.IDLE_CTRL, animationInfo, AnimationCategory.EAT);
            animEndTick = (long) (entity.level().getGameTime() + animationInfo.animation.length());
        }
    }

    @Override
    protected boolean createPath() {
        return true;
    }

    @Override
    protected void moveMobToBlock() {

    }

    @Override
    protected boolean findNearestBlock() {
        return true;
    }

    @Override
    protected boolean isValidTarget(LevelReader level, BlockPos pos) {
        return true;
    }
}
