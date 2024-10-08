package com.fossil.fossil.entity.ai;

import com.fossil.fossil.entity.prehistoric.base.PrehistoricSwimming;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.level.Level;

import java.util.EnumSet;
import java.util.Random;

import static com.fossil.fossil.entity.prehistoric.base.PrehistoricSwimming.MAX_TIME_IN_WATER;
import static com.fossil.fossil.entity.prehistoric.base.PrehistoricSwimming.MAX_TIME_ON_LAND;

/**
 * A Goal that will move an amphibious entity out of the water if it has been in there for too long
 */
public class LeaveWaterGoal extends Goal {
    private final PrehistoricSwimming dino;
    private final double speedModifier;
    private BlockPos shelterPos;

    public LeaveWaterGoal(PrehistoricSwimming dino, double speedModifier) {
        this.dino = dino;
        this.speedModifier = speedModifier;
        setFlags(EnumSet.of(Flag.MOVE));
    }

    @Override
    public boolean canUse() {
        if (!dino.isInWater() || !dino.isAmphibious()) {
            return false;
        }
        if (dino.timeInWater <= MAX_TIME_IN_WATER || dino.timeOnLand >= MAX_TIME_ON_LAND) {
            return false;
        }
        return findPossibleShelter();
    }

    @Override
    public boolean canContinueToUse() {
        return dino.isInWater();
    }

    @Override
    public void start() {
        dino.getNavigation().moveTo(shelterPos.getX(), shelterPos.getY(), shelterPos.getZ(), speedModifier);
    }

    private boolean findPossibleShelter() {
        Random random = dino.getRandom();
        Level level = dino.level;
        BlockPos original = dino.blockPosition();
        BlockPos.MutableBlockPos mutable = original.mutable();

        for (int i = 0; i < 10; i++) {
            mutable.move(random.nextInt(20) - 10, 1 + random.nextInt(6), random.nextInt(20) - 10);
            if (level.getBlockState(mutable).isSolidRender(level, mutable) && level.getBlockState(mutable.above()).isAir() && mutable.getY() >= original.getY()) {
                shelterPos = mutable.immutable();
                return true;
            }
        }
        return false;
    }
}
