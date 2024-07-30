package com.fossil.fossil.entity.ai;

import com.fossil.fossil.entity.prehistoric.base.OrderType;
import com.fossil.fossil.entity.prehistoric.base.Prehistoric;
import com.fossil.fossil.entity.prehistoric.base.PrehistoricSwimming;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.entity.ai.behavior.BehaviorUtils;
import net.minecraft.world.entity.ai.goal.RandomSwimmingGoal;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

import java.util.Random;

/**
 * Will move the mob to a random spot in water if it is not in combat
 */
public class DinoRandomSwimGoal extends RandomSwimmingGoal {
    private final Prehistoric dino;

    public DinoRandomSwimGoal(PrehistoricSwimming dino, double speedModifier) {
        super(dino, speedModifier, 40);
        this.dino = dino;
    }

    @Override
    public boolean canUse() {
        if (!dino.isInWater() || dino.getTarget() != null || dino.getCurrentOrder() != OrderType.WANDER) {
            return false;
        }
        return super.canUse();
    }

    @Nullable
    @Override
    public Vec3 getPosition() {
        Vec3 targetPos = BehaviorUtils.getRandomSwimmablePos(dino, 10, 7);
        if (targetPos != null && dino.level.getFluidState(new BlockPos(targetPos)).is(FluidTags.WATER)) {
            return targetPos;
        }
        BlockPos.MutableBlockPos mutableBlockPos = new BlockPos.MutableBlockPos();
        Random random = dino.getRandom();
        if (dino.getTarget() == null || dino.getTarget().isDeadOrDying()) {
            for (int i = 0; i < 20; i++) {
                mutableBlockPos.setWithOffset(dino.blockPosition(), random.nextInt(16) - 7, random.nextInt(8) - 4, random.nextInt(16) - 7);
                if (dino.level.getFluidState(mutableBlockPos).is(FluidTags.WATER)) {//TODO: Raytrace
                    targetPos = Vec3.atCenterOf(mutableBlockPos);
                    return targetPos;
                }
            }
        }
        return null;
    }
}
