package com.fossil.fossil.entity.ai;

import com.fossil.fossil.entity.prehistoric.base.PrehistoricSwimming;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.material.Material;

import java.util.Random;

import static com.fossil.fossil.entity.prehistoric.base.PrehistoricSwimming.MAX_TIME_ON_LAND;

public class EnterWaterGoal extends Goal {
    private final PrehistoricSwimming dino;
    private final double speedModifier;
    private BlockPos shelterPos;

    public EnterWaterGoal(PrehistoricSwimming dino, double speedModifier) {
        this.dino = dino;
        this.speedModifier = speedModifier;
    }

    @Override
    public boolean canUse() {
        if (dino.isInWater() || !shouldEnterWater(dino) || dino.isImmobile()) {
            return false;
        }
        if (dino.getTarget() != null && !dino.getTarget().isInWater()) {
            return false;
        }
        return findPossibleShelter();
    }

    @Override
    public boolean canContinueToUse() {
        return !dino.getNavigation().isDone();
    }

    @Override
    public void start() {
        dino.getNavigation().moveTo(shelterPos.getX(), shelterPos.getY(), shelterPos.getZ(), speedModifier);
    }

    @Override
    public void tick() {
        super.tick();
    }

    private boolean findPossibleShelter() {
        Random random = dino.getRandom();
        Level level = dino.level;
        BlockPos original = dino.blockPosition();
        BlockPos.MutableBlockPos mutable = original.mutable();

        for (int i = 0; i < 10; i++) {
            mutable.move(random.nextInt(20) - 10, random.nextInt(6) - 3, random.nextInt(20) - 10);
            if (level.getBlockState(mutable).getMaterial() == Material.WATER) {
                shelterPos = mutable.immutable();
                return true;
            }
        }
        return false;
    }

    private static boolean shouldEnterWater(PrehistoricSwimming dino) {
        if (!dino.isAmphibious()) {
            return true;
        }
        return dino.timeInWater == 0 && dino.timeOnLand > MAX_TIME_ON_LAND;
    }
}
