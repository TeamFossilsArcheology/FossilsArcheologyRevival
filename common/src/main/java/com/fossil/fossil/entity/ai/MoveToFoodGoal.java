package com.fossil.fossil.entity.ai;

import com.fossil.fossil.entity.prehistoric.base.Prehistoric;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

/**
 * A Goal that will move the entity to a target block if the entity is hungry. If the entity is near starving the delay between goal execution will
 * be removed and the cache cleared much sooner.
 */
public abstract class MoveToFoodGoal extends CacheMoveToBlockGoal {
    protected int feedingTicks;

    protected MoveToFoodGoal(Prehistoric entity, double speedModifier, int searchRange) {
        super(entity, speedModifier, searchRange);
    }

    @Override
    public boolean canUse() {
        if (entity.getHunger() >= entity.getMaxHunger()) {
            return false;
        }
        if (entity.getHunger() <= 0.25 * entity.getMaxHunger()) {
            nextStartTick = 0;
            clearTicks = Math.min(clearTicks / 4, 1);
        }
        return super.canUse();
    }

    @Override
    public boolean canContinueToUse() {
        if (entity.getHunger() >= entity.getMaxHunger()) {
            return false;
        }
        return super.canContinueToUse();
    }

    protected boolean canSeeFood(BlockPos position) {
        Vec3 target = new Vec3(position.getX() + 0.5, position.getY() + 0.5, position.getZ() + 0.5);
        BlockHitResult rayTrace = entity.getLevel().clip(new ClipContext(entity.position(), target, ClipContext.Block.OUTLINE, ClipContext.Fluid.NONE, entity));
        return rayTrace.getType() != HitResult.Type.MISS;
    }
}
