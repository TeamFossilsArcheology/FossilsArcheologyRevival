package com.fossil.fossil.entity.ai;

import com.fossil.fossil.entity.prehistoric.base.OrderType;
import com.fossil.fossil.entity.prehistoric.base.Prehistoric;
import com.fossil.fossil.entity.prehistoric.base.PrehistoricSwimming;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.entity.ai.behavior.BehaviorUtils;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.phys.Vec3;

import java.util.EnumSet;
import java.util.Random;

/**
 * A Goal that will move an entity to a random spot in water if it is not in combat
 */
public class EnterWaterWithoutTargetGoal extends Goal {
    private final Prehistoric dino;
    private final double speedModifier;
    private Vec3 targetPos;

    public EnterWaterWithoutTargetGoal(Prehistoric dino, double speedModifier) {
        this.dino = dino;
        this.speedModifier = speedModifier;
        setFlags(EnumSet.of(Flag.MOVE));
    }

    @Override
    public boolean canUse() {
        if (!dino.isInWater() || dino.isVehicle()) {
            return false;
        }

        if (dino instanceof PrehistoricSwimming && dino.getCurrentOrder() != OrderType.WANDER) {
            return false;
        }
        if (dino.getRandom().nextFloat() < 0.5) {
            return findWaterTarget();
        }
        return false;
    }

    @Override
    public boolean canContinueToUse() {
        return !dino.getNavigation().isDone() && !dino.isVehicle();
    }

    @Override
    public void start() {
        dino.getNavigation().moveTo(targetPos.x, targetPos.y, targetPos.z, speedModifier);
    }

    public boolean findWaterTarget() {
        targetPos = BehaviorUtils.getRandomSwimmablePos(dino, 10, 7);
        if (targetPos != null && dino.level.getFluidState(new BlockPos(targetPos)).is(FluidTags.WATER)) {
            return true;
        }
        BlockPos.MutableBlockPos mutableBlockPos = new BlockPos.MutableBlockPos();
        Random random = dino.getRandom();
        if (dino.getTarget() == null || dino.getTarget().isDeadOrDying()) {
            for (int i = 0; i < 20; i++) {
                mutableBlockPos.setWithOffset(dino.blockPosition(), random.nextInt(16) - 7, random.nextInt(8) - 4, random.nextInt(16) - 7);
                if (dino.level.getFluidState(mutableBlockPos).is(FluidTags.WATER)) {//TODO: Raytrace
                    targetPos = Vec3.atCenterOf(mutableBlockPos);
                    return true;
                }
            }
        }
        return false;
    }
}
