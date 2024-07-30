package com.fossil.fossil.entity.ai;

import com.fossil.fossil.entity.prehistoric.base.PrehistoricFlying;
import com.fossil.fossil.entity.prehistoric.base.PrehistoricSwimming;
import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.Vec3;

public class FlyingSitGoal extends DinoSitGoal {
    protected final PrehistoricFlying dino;
    public BlockPos targetPos;

    public FlyingSitGoal(PrehistoricFlying dino) {
        super(dino);
        this.dino = dino;
    }

    @Override
    public boolean canUse() {
        boolean canSit = super.canUse();
        if (!canSit) {
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
        return dino.findLandPosition(true);
    }
}
