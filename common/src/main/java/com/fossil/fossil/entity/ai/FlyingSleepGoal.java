package com.fossil.fossil.entity.ai;

import com.fossil.fossil.entity.prehistoric.base.PrehistoricFlying;
import com.fossil.fossil.entity.prehistoric.base.PrehistoricSwimming;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

public class FlyingSleepGoal extends DinoSleepGoal {
    protected final PrehistoricFlying dino;
    public BlockPos targetPos;

    public FlyingSleepGoal(PrehistoricFlying dino) {
        super(dino);
        this.dino = dino;
    }

    @Override
    public boolean canUse() {
        boolean canSleep = super.canUse();
        if (!canSleep) {
            return false;
        }
        if ((dino.isFlying() || dino.isTakingOff() ))  {
            if (PrehistoricSwimming.isOverWater(dino)) {
                return false;
            }
            targetPos = findGroundTarget();
            return targetPos != null;
        }
        return dino.isOnGround();
    }

    @Override
    public void start() {
        if (!dino.isFlying()) {
            super.start();
        } else {
            dino.moveTo(Vec3.atCenterOf(targetPos), true);
        }
    }

    @Override
    public void tick() {
        if (!dino.isFlying() && !dino.isSleeping()) {
            super.start();
        }
    }

    private BlockPos findGroundTarget() {
        if (!dino.level.isEmptyBlock(dino.blockPosition().below())) {
            return dino.blockPosition().below();
        }
        if (!dino.level.isEmptyBlock(dino.blockPosition().below(2))) {
            return dino.blockPosition().below(2);
        }
        BlockPos sleepPos = findTreePosition(16);
        if (sleepPos == null) {
            sleepPos = dino.findLandPosition(false);
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
                int bX = x + dino.blockPosition().getX();
                int bZ = z + dino.blockPosition().getZ();
                int height = dino.level.getHeight(Heightmap.Types.MOTION_BLOCKING, bX, bZ);
                int leaves = dino.level.getHeight(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, bX, bZ);
                if (height > dino.getY()) continue;
                mutable.set(bX, height - 1, bZ);
                if (dino.level.getBlockState(mutable).is(BlockTags.LEAVES)) {
                    //Looking for leaves that are a few blocks above the ground
                    if (height > leaves + 3) {
                        return mutable.immutable();
                    }
                    //Looking for leaves that are above the stem of the tree
                    if (dino.level.getBlockState(mutable2.set(bX, leaves, bZ)).is(BlockTags.LOGS)) {
                        for (Direction dir : Direction.Plane.HORIZONTAL) {
                            if (dino.level.getHeight(Heightmap.Types.MOTION_BLOCKING, bX + dir.getStepX(), bZ + dir.getStepZ()) < height) {
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
