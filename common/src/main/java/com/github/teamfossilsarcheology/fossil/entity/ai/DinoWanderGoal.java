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
 * Uses Levy flight (mu=2) for unbiased spatial coverage -- most steps are short,
 * occasional long jumps, preventing terrain-driven clustering.
 * MIN_DIST=6 and MAX_DIST=48 ensure mobs roam meaningfully without bias.
 *
 * Includes stuck recovery: if the mob fails to advance its path node index for
 * STUCK_TIMEOUT ticks, the goal stops itself so a fresh target is picked next tick.
 * This handles cases like a mob wandering to a cliff edge where the pathfinder
 * generates an unreachable node below -- without this the mob freezes indefinitely.
 */
public class DinoWanderGoal extends RandomStrollGoal {

    // Levy flight exponent: mu=2 is the theoretical optimum for animal foraging
    private static final float LEVY_MU = 2.0f;
    // Minimum step of 6 blocks ensures mobs don't just shuffle in place.
    // Maximum of 48 allows occasional long exploratory jumps across the enclosure.
    private static final float MIN_DIST = 6.0f;
    private static final float MAX_DIST = 48.0f;

    // How many ticks without path progress before we give up and pick a new target.
    // 100 ticks = 5 seconds, enough to rule out brief hesitation on tricky terrain.
    private static final int STUCK_TIMEOUT = 100;

    private int lastNodeIndex = -1;
    private int ticksAtSameNode = 0;

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

    @Override
    public void start() {
        super.start();
        lastNodeIndex = -1;
        ticksAtSameNode = 0;
    }

    @Override
    public void tick() {
        super.tick();

        // Stuck recovery: track path node progress.
        // If the current node index hasn't changed for STUCK_TIMEOUT ticks,
        // abandon this goal so canUse() re-evaluates and picks a fresh Levy target.
        if (mob.getNavigation().getPath() != null) {
            int currentNode = mob.getNavigation().getPath().getNextNodeIndex();
            if (currentNode == lastNodeIndex) {
                ticksAtSameNode++;
                if (ticksAtSameNode >= STUCK_TIMEOUT) {
                    stop();
                    return;
                }
            } else {
                lastNodeIndex = currentNode;
                ticksAtSameNode = 0;
            }
        }
    }

    @Nullable
    @Override
    protected Vec3 getPosition() {
        int verticalDistance = 7;

        if (mob.isInWater()) {
            Vec3 randomPos = LandRandomPos.getPos(mob, 30, 8);
            return randomPos == null ? LandRandomPos.getPos(mob, 10, verticalDistance) : randomPos;
        }

        // Levy flight step size using power-law inverse transform sampling.
        // distance = MIN_DIST * (uniform^(1/(1-mu))) gives a heavy-tailed distribution:
        // most steps are near MIN_DIST, with rare long jumps up to MAX_DIST.
        // No directional bias -- angle is uniformly random over full 360 degrees.
        float u = Math.max(mob.getRandom().nextFloat(), 1e-6f);
        float distance = MIN_DIST * (float) Math.pow(u, 1.0f / (1.0f - LEVY_MU));
        distance = Math.min(distance, MAX_DIST);

        float angle = mob.getRandom().nextFloat() * (float) (Math.PI * 2);

        double targetX = mob.getX() + Math.cos(angle) * distance;
        double targetZ = mob.getZ() + Math.sin(angle) * distance;

        BlockPos targetBlock = BlockPos.containing(targetX, mob.getY(), targetZ);
        BlockPos groundPos = findGround(targetBlock, verticalDistance);
        if (groundPos == null) return null;

        return Vec3.atBottomCenterOf(groundPos);
    }

    private BlockPos findGround(BlockPos pos, int searchRange) {
        LevelReader level = mob.level();
        int mobY = mob.getBlockY();
        for (int dy = 0; dy >= -searchRange; dy--) {
            BlockPos check = pos.offset(0, dy, 0);
            // Don't return ground more than 4 blocks below the mob's current Y —
            // prevents routing to ground beneath a platform the mob is standing on.
            if (mobY - check.getY() > 4) break;
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