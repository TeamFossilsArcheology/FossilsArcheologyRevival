package com.github.teamfossilsarcheology.fossil.entity.ai;

import com.github.teamfossilsarcheology.fossil.entity.prehistoric.base.OrderType;
import com.github.teamfossilsarcheology.fossil.entity.prehistoric.base.PrehistoricFlying;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.phys.Vec3;

import java.util.EnumSet;

public class FlyingWanderGoal extends Goal {
    protected final PrehistoricFlying dino;
    private Vec3 targetPos;
    private boolean shouldLand;
    private boolean isRotating;

    public FlyingWanderGoal(PrehistoricFlying dino) {
        this.dino = dino;
        setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK));
    }

    @Override
    public boolean canUse() {
        boolean debugLand = false;//TODO: Remove debug
        if (dino.isVehicle() || dino.getCurrentOrder() != OrderType.WANDER || !dino.isAdult()) {
            return false;
        }
        if (dino.isFlying()) {
            if (debugLand || dino.getRandom().nextInt(50) == 0) {
                BlockPos landPosition = dino.findLandPosition(false);
                if (landPosition != null) {
                    shouldLand = true;
                    targetPos = Vec3.atCenterOf(landPosition);
                    return true;
                }
                return false;
            }
            targetPos = findAirTarget();
            return targetPos != null;
        } else if (dino.onGround()) {
            boolean debug = false;
            if (debug||dino.getRandom().nextInt(250) == 0) {
                targetPos = findAirTarget();
                return targetPos != null;
            }
        }
        return false;
    }

    @Override
    public boolean canContinueToUse() {
        if (dino.isFlying()) {
            return dino.getMoveControl().hasWanted();
        }
        return isRotating || dino.isTakingOff();
    }

    @Override
    public void start() {
        dino.getLookControl().setLookAt(targetPos);
        dino.getNavigation().stop();
        if (dino.isFlying()) {
            dino.moveTo(targetPos, shouldLand, true);
        } else {
            performTakeOff();
        }
    }

    @Override
    public void stop() {
        shouldLand = false;
        isRotating = false;
        targetPos = null;
    }

    @Override
    public void tick() {
        if (!dino.isFlying() && !dino.isTakingOff() && !shouldLand) {
            performTakeOff();
        }
    }

    private void performTakeOff() {
        if (!dino.isTakingOff()) {
            Vec3 distance = targetPos.subtract(dino.position());
            float rot = (float) (Mth.atan2(distance.z, distance.x) * Mth.RAD_TO_DEG - 90);
            float diff = Mth.degreesDifference(rot, dino.yBodyRot);
            if (diff > 45 && false) {//TODO: Can break if directly aboe target I think
                isRotating = true;
                dino.getLookControl().setLookAt(targetPos);
            } else {
                isRotating = false;
                dino.moveTo(targetPos, false, true);
            }
        }
    }

    private Vec3 findAirTarget() {
        //TODO: Different flight patterns. Do circles around point, Fly straight for a bit, random etc
        return dino.generateAirTarget();
    }
}
