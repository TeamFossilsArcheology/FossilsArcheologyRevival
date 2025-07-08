package com.github.teamfossilsarcheology.fossil.entity.ai;

import com.github.teamfossilsarcheology.fossil.entity.prehistoric.base.OrderType;
import com.github.teamfossilsarcheology.fossil.entity.prehistoric.base.Prehistoric;
import com.github.teamfossilsarcheology.fossil.entity.prehistoric.base.SwimmingAnimal;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.ai.behavior.BehaviorUtils;
import net.minecraft.world.entity.ai.goal.RandomSwimmingGoal;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

/**
 * Will move the mob to a random spot in water if it is not in combat
 */
public class DinoRandomSwimGoal<T extends Prehistoric & SwimmingAnimal> extends RandomSwimmingGoal {
    private final T dino;

    public DinoRandomSwimGoal(T dino, double speedModifier) {
        super(dino, speedModifier, 10);
        this.dino = dino;
    }

    @Override
    public boolean canUse() {
        if (!dino.isInWater() || !dino.canSwim() || dino.getTarget() != null || dino.getCurrentOrder() != OrderType.WANDER) {
            return false;
        }
        return super.canUse();
    }

    @Nullable
    @Override
    public Vec3 getPosition() {
        Vec3 targetPos = BehaviorUtils.getRandomSwimmablePos(dino, 10, 7);
        if (targetPos != null && dino.level().getFluidState(BlockPos.containing(targetPos)).is(FluidTags.WATER)) {
            return targetPos;
        }
        BlockPos.MutableBlockPos mutableBlockPos = new BlockPos.MutableBlockPos();
        RandomSource random = dino.getRandom();
        if (dino.getTarget() == null || dino.getTarget().isDeadOrDying()) {
            for (int i = 0; i < 20; i++) {
                mutableBlockPos.setWithOffset(dino.blockPosition(), random.nextInt(16) - 7, random.nextInt(8) - 4, random.nextInt(16) - 7);
                if (dino.level().getFluidState(mutableBlockPos).is(FluidTags.WATER)) {
                    targetPos = Vec3.atCenterOf(mutableBlockPos);
                    return targetPos;
                }
            }
        }
        return null;
    }
}
