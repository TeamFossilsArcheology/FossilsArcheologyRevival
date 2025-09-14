package com.github.teamfossilsarcheology.fossil.entity.prehistoric.system;

import com.github.teamfossilsarcheology.fossil.entity.prehistoric.base.OrderType;
import com.github.teamfossilsarcheology.fossil.entity.prehistoric.base.PrehistoricEntityInfoAI;
import com.github.teamfossilsarcheology.fossil.entity.prehistoric.base.PrehistoricFlying;
import com.github.teamfossilsarcheology.fossil.entity.prehistoric.base.PrehistoricSwimming;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

public class FlyingSleepSystem extends SleepSystem {
    private final PrehistoricFlying mob;
    private BlockPos landingPos;
    private long nextTryTick = -1;

    public FlyingSleepSystem(PrehistoricFlying mob) {
        super(mob);
        this.mob = mob;
    }

    @Override
    protected void trySleeping() {
        if (nextTryTick == -1) {
            if (!mob.isFlying() && !mob.isTakingOff()) {
                super.trySleeping();
                return;
            }
            if (mob.aiActivityType() == PrehistoricEntityInfoAI.Activity.BOTH) {
                if (mob.getRandom().nextInt(1200) == 0 && wantsToSleep() && canSleep()) {
                    trySleepingOrLanding();
                }
            } else if (mob.aiActivityType() != PrehistoricEntityInfoAI.Activity.NO_SLEEP) {
                if (mob.getRandom().nextInt(200) == 0 && wantsToSleep() && canSleep()) {
                    trySleepingOrLanding();
                }
            }
        } else if (nextTryTick == mob.level().getGameTime()) {
            trySleepingOrLanding();
        }
    }

    private void trySleepingOrLanding() {
        if (mob.isFlying() || mob.isTakingOff()) {
            landingPos = findGroundTarget();
            if (landingPos != null) {
                mob.moveTo(Vec3.atCenterOf(landingPos), true, true);
                nextTryTick = mob.level().getGameTime() + 100;
            } else {
                nextTryTick = mob.level().getGameTime() + 20;
            }
        } else {
            setSleeping(true);
            nextTryTick = -1;
        }
    }

    @Override
    protected boolean canSleep() {
        if (PrehistoricSwimming.isOverWater(mob)) {
            return false;
        }
        if (isDisabled() || mob.hasTarget() || mob.getLastHurtByMob() != null || mob.getCurrentOrder() == OrderType.FOLLOW || mob.isVehicle()) {
            return false;
        }
        if (mob.isDeadlyHungry()) {
            return false;
        }
        return !mob.isInWater();
    }

    @Override
    public void serverTick() {
        super.serverTick();
        if (landingPos != null) {
            if (mob.isFlying()) {
                mob.moveTo(Vec3.atCenterOf(landingPos), true, true);
            } else {
                landingPos = null;
            }
        }
    }

    @Override
    public void setSleeping(boolean sleeping) {
        super.setSleeping(sleeping);
        if (sleeping) {
            landingPos = null;
        }
    }

    private BlockPos findGroundTarget() {
        if (!mob.level().isEmptyBlock(mob.blockPosition().below())) {
            return mob.blockPosition().below();
        }
        if (!mob.level().isEmptyBlock(mob.blockPosition().below(2))) {
            return mob.blockPosition().below(2);
        }
        BlockPos sleepPos = findTreePosition(16);
        if (sleepPos == null) {
            sleepPos = mob.findLandPosition(false);
        }
        return sleepPos;
    }

    @Nullable
    private BlockPos findTreePosition(int radius) {
        int dx = 0;
        int dz = -1;
        //TODO: This will not work in enclosures/underground
        BlockPos.MutableBlockPos mutable = new BlockPos.MutableBlockPos();
        BlockPos.MutableBlockPos mutable2 = new BlockPos.MutableBlockPos();
        //Check each block in a spiral movement
        for (int i = 0, x = 0, z = 0; i < Math.pow(radius * 2, 2); i++) {
            if (-radius < x && x <= radius && -radius < z && z <= radius) {
                int bX = x + mob.blockPosition().getX();
                int bZ = z + mob.blockPosition().getZ();
                int height = mob.level().getHeight(Heightmap.Types.MOTION_BLOCKING, bX, bZ);
                int leaves = mob.level().getHeight(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, bX, bZ);
                if (height > mob.getY()) continue;
                mutable.set(bX, height - 1, bZ);
                if (mob.level().getBlockState(mutable).is(BlockTags.LEAVES)) {
                    //Looking for leaves that are a few blocks above the ground
                    if (height > leaves + 3) {
                        return mutable.immutable();
                    }
                    //Looking for leaves that are above the stem of the tree
                    if (mob.level().getBlockState(mutable2.set(bX, leaves, bZ)).is(BlockTags.LOGS)) {
                        for (Direction dir : Direction.Plane.HORIZONTAL) {
                            if (mob.level().getHeight(Heightmap.Types.MOTION_BLOCKING, bX + dir.getStepX(), bZ + dir.getStepZ()) < height) {
                                return mutable.immutable();
                            }
                        }
                    }
                }
            }
            if (x == z || (x < 0 && x == -z) || (x > 0 && x == 1 - z)) {
                int t = dx;
                dx = -dz;
                dz = t;
            }
            x += dx;
            z += dz;
        }
        return null;
    }
}
