package com.github.teamfossilsarcheology.fossil.entity.ai;

import com.github.teamfossilsarcheology.fossil.entity.prehistoric.base.OrderType;
import com.github.teamfossilsarcheology.fossil.entity.prehistoric.base.Prehistoric;
import com.github.teamfossilsarcheology.fossil.entity.prehistoric.base.PrehistoricFlocking;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.ai.goal.RandomStrollGoal;
import net.minecraft.world.entity.ai.util.LandRandomPos;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

/**
 * Random stroll goal for non-flying/swimming mobs.
 * Uses Lévy flight (μ=2) for unbiased spatial coverage meaning most steps are short,
 * occasional long jumps, preventing terrain-driven clustering.
 */
public class DinoWanderGoal extends RandomStrollGoal {

    // Lévy flight exponent: μ=2 is the theoretical optimum for animal foraging
    private static final float LEVY_MU = 2.0f;
    // Minimum and maximum step size in blocks
    private static final float MIN_DIST = 2.0f;
    private static final float MAX_DIST = 24.0f;

    public DinoWanderGoal(Prehistoric dinosaur, double speed) {
        this(dinosaur, speed, 120);
    }

    public DinoWanderGoal(Prehistoric dinosaur, double speed, int interval) {
        super(dinosaur, speed, interval);
    }

    @Override
    public boolean canUse() {
        Prehistoric dinosaur = (Prehistoric) mob;
        if (dinosaur.getCurrentOrder() != OrderType.WANDER || dinosaur.hasTarget()) {
            return false;
        }
        if (dinosaur instanceof PrehistoricFlocking flocking && !flocking.isGroupLeader() && flocking.hasGroupLeader()) {
            return false;
        }
        return super.canUse();
    }

    @Nullable
    @Override
    protected Vec3 getPosition() {
        int verticalDistance = 7;

        if (mob.isInWater()) {
            Vec3 randomPos = LandRandomPos.getPos(mob, 30, 8);
            return randomPos == null ? LandRandomPos.getPos(mob, 10, verticalDistance) : randomPos;
        }

        // Lévy flight step size using power-law inverse transform sampling
        // distance = MIN_DIST * (uniform^(1/(1-mu))) this gives heavy-tailed distribution and therefore no more South-East bias
        float u = Math.max(mob.getRandom().nextFloat(), 1e-6f); // avoid log(0)
        float distance = MIN_DIST * (float) Math.pow(u, 1.0f / (1.0f - LEVY_MU));
        distance = Math.min(distance, MAX_DIST); // truncates to prevent absurd jumps

        // Uniform random angle means no directional bias
        float angle = mob.getRandom().nextFloat() * (float) (Math.PI * 2);

        double targetX = mob.getX() + Math.cos(angle) * distance;
        double targetZ = mob.getZ() + Math.sin(angle) * distance;

        BlockPos targetBlock = BlockPos.containing(targetX, mob.getY(), targetZ);
        BlockPos groundPos = findGround(targetBlock, verticalDistance);
        BlockPos finalPos = groundPos != null ? groundPos : targetBlock;

        Vec3 result = Vec3.atBottomCenterOf(finalPos);
    }

    private BlockPos findGround(BlockPos pos, int searchRange) {
        LevelReader level = mob.level();

        for (int dy = 0; dy >= -searchRange; dy--) {
            BlockPos check = pos.offset(0, dy, 0);
            BlockPos below = check.below();
            if (level.getBlockState(below).isSolid() &&
                level.getBlockState(check).isPathfindable(level, check, PathComputationType.LAND)) {
                return check;
            }
        }
        for (int dy = 1; dy <= searchRange; dy++) {
            BlockPos check = pos.offset(0, dy, 0);
            BlockPos below = check.below();
            if (level.getBlockState(below).isSolid() &&
                level.getBlockState(check).isPathfindable(level, check, PathComputationType.LAND)) {
                return check;
            }
        }
        return null;
    }
}