package com.fossil.fossil.entity.ai;

import com.fossil.fossil.entity.prehistoric.base.PrehistoricFlying;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.phys.Vec3;

import java.util.EnumSet;

public class FlyingWanderGoal extends Goal {
    protected final PrehistoricFlying dino;
    public Vec3 targetPos;
    private boolean land;

    public FlyingWanderGoal(PrehistoricFlying dino) {
        this.dino = dino;
        setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK));
    }

    @Override
    public boolean canUse() {
        boolean debugLand = false;
        if (dino.isFlying()) {
            if (debugLand || dino.getRandom().nextInt(50) == 0) {//TODO: Will only land if no predator is nearby?
                BlockPos landPosition = dino.findLandPosition(false);
                if (landPosition != null) {
                    land = true;
                    targetPos = Vec3.atCenterOf(landPosition);
                    return true;
                }
                return false;
            }
            targetPos = findAirTarget();
            return targetPos != null;
        }

        if (!dino.isOnGround() || dino.isImmobile() || !dino.isAdult()) {
            return false;
        }
        boolean debug = false;
        if (debug||dino.getRandom().nextInt(250) == 0) {
            targetPos = findAirTarget();
            return targetPos != null;
        }
        return false;
    }

    @Override
    public boolean canContinueToUse() {
        if (dino.isFlying()) {
            return dino.getMoveControl().hasWanted();
        }
        return !dino.isTakingOff();
    }

    @Override
    public void start() {
        dino.getLookControl().setLookAt(targetPos);
        dino.getNavigation().stop();
        if (dino.isFlying()) {
            dino.moveTo(targetPos, land);
        }
    }

    @Override
    public void stop() {
        land = false;
        targetPos = null;
    }

    @Override
    public void tick() {
        if (dino.isFlying() || dino.isTakingOff() || land) {
            return;
            //continue roam
        } else if(!dino.isTakingOff()) {
            Vec3 distance = targetPos.subtract(dino.position());
            float rot = (float) (Mth.atan2(distance.z, distance.x) * Mth.RAD_TO_DEG - 90);
            float diff = Mth.degreesDifference(rot, dino.yBodyRot);
            if (diff > 45) {
                dino.getLookControl().setLookAt(targetPos);
            } else {
                dino.startTakeOff();
                dino.moveTo(targetPos, false);
            }
        }
    }

    private Vec3 findAirTarget() {
        return dino.generateAirTarget();
    }
}
