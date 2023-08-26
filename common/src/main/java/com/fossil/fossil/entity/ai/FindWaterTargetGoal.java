package com.fossil.fossil.entity.ai;

import com.fossil.fossil.entity.prehistoric.base.OrderType;
import com.fossil.fossil.entity.prehistoric.base.Prehistoric;
import com.fossil.fossil.entity.prehistoric.base.PrehistoricSwimming;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.level.material.Material;

import java.util.Random;

public class FindWaterTargetGoal extends Goal {
    private final Prehistoric dino;
    private final double speedModifier;
    private BlockPos targetPos;

    public FindWaterTargetGoal(Prehistoric dino, double speedModifier) {
        this.dino = dino;
        this.speedModifier = speedModifier;
    }

    @Override
    public boolean canUse() {
        if (!dino.isInWater() || dino.isVehicle()) {
            return false;
        }

        if (dino instanceof PrehistoricSwimming && dino.currentOrder != OrderType.WANDER) {
            return false;
        }
        if (dino.getRandom().nextFloat() < 0.5) {
            return findWaterTarget();
        }
        return false;
    }

    @Override
    public boolean canContinueToUse() {
        return false;
    }

    @Override
    public void start() {
        dino.getNavigation().moveTo(targetPos.getX(), targetPos.getY(), targetPos.getZ(), speedModifier);
    }

    public boolean findWaterTarget() {
        BlockPos.MutableBlockPos mutableBlockPos = new BlockPos.MutableBlockPos();
        Random random = dino.getRandom();
        if (dino.getTarget() == null || dino.getTarget().isDeadOrDying()) {
            for (int i = 0; i < 20; i++) {
                mutableBlockPos.set(random.nextInt(16) - 7, random.nextInt(8) - 4, random.nextInt(16) - 7);
                if (dino.level.getBlockState(mutableBlockPos).getMaterial() == Material.WATER) {
                    targetPos = mutableBlockPos.immutable();
                    return true;
                }
            }
        } else {
            targetPos = new BlockPos(dino.getTarget().position());
            return true;
        }
        return false;
    }
}
