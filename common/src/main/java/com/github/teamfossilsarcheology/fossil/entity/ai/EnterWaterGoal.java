package com.github.teamfossilsarcheology.fossil.entity.ai;

import com.github.teamfossilsarcheology.fossil.entity.prehistoric.base.PrehistoricSwimming;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.level.Level;

import java.util.EnumSet;
import java.util.Random;

import static com.github.teamfossilsarcheology.fossil.entity.prehistoric.base.PrehistoricSwimming.MAX_TIME_ON_LAND;

/**
 * Will move an amphibious mob to a random spot in water if it has spent too much time on land
 */
public class EnterWaterGoal extends Goal {
    protected final PrehistoricSwimming dino;
    private final int searchRange;
    private final float speedModifier;
    private BlockPos shelterPos;

    public EnterWaterGoal(PrehistoricSwimming dino, float speedModifier) {
        this(dino, 20, speedModifier);
    }

    public EnterWaterGoal(PrehistoricSwimming dino, int searchRange, float speedModifier) {
        this.dino = dino;
        this.searchRange = searchRange;
        this.speedModifier = speedModifier;
        setFlags(EnumSet.of(Flag.MOVE));
    }

    @Override
    public boolean canUse() {
        if (dino.isInWater() || !dino.isAmphibious()) {
            return false;
        }
        if (dino.timeInWater != 0 || dino.timeOnLand <= MAX_TIME_ON_LAND) {
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

    private boolean findPossibleShelter() {
        Random random = dino.getRandom();
        Level level = dino.level;
        BlockPos original = dino.blockPosition();
        BlockPos.MutableBlockPos mutable = original.mutable();

        for (int i = 0; i < 10; i++) {
            mutable.move(random.nextInt(searchRange) - 10, random.nextInt(6) - 3, random.nextInt(searchRange) - 10);
            if (level.getFluidState(mutable).is(FluidTags.WATER)) {
                shelterPos = mutable.immutable();
                return true;
            }
        }
        return false;
    }
}
