package com.github.teamfossilsarcheology.fossil.entity.ai;

import com.github.teamfossilsarcheology.fossil.entity.prehistoric.base.OrderType;
import com.github.teamfossilsarcheology.fossil.entity.prehistoric.base.PrehistoricFlying;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.phys.Vec3;

import java.util.EnumSet;

public class FlyingWanderGoal extends Goal {
    protected final PrehistoricFlying dino;
    private Vec3 targetPos;
    private boolean shouldLand;

    public FlyingWanderGoal(PrehistoricFlying dino) {
        this.dino = dino;
        setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK));
    }

    @Override
    public boolean canUse() {
        if (dino.isVehicle() || dino.getCurrentOrder() != OrderType.WANDER || !dino.isAdult()) {
            return false;
        }
        if (dino.isFlying()) {
            if (dino.getRandom().nextInt(900) == 0) {
                BlockPos landPosition = dino.findLandPosition(false);
                if (landPosition != null) {
                    shouldLand = true;
                    targetPos = Vec3.atCenterOf(landPosition);
                    return true;
                }
                return false;
            }
            // Only pick a new air target occasionally — without this gate the
            // goal fires every tick and the mob changes direction constantly.
            if (dino.getRandom().nextInt(2) == 0) {
                targetPos = findAirTarget();
                return targetPos != null;
            }
            return false;
        } else if (dino.onGround()) {
            boolean debug = false;
            if (debug || dino.getRandom().nextInt(600) == 0) {
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
        return dino.isTakingOff();
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
                dino.moveTo(targetPos, false, true);
        }
    }

    private Vec3 findAirTarget() {
        return dino.generateAirTarget();
    }
}