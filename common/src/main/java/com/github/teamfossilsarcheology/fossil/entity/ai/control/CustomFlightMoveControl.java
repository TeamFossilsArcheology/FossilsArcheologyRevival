package com.github.teamfossilsarcheology.fossil.entity.ai.control;

import com.github.teamfossilsarcheology.fossil.entity.prehistoric.base.PrehistoricFlying;
import com.github.teamfossilsarcheology.fossil.entity.util.Util;
import com.github.teamfossilsarcheology.fossil.network.MessageHandler;
import com.github.teamfossilsarcheology.fossil.network.debug.S2CMarkMessage;
import com.github.teamfossilsarcheology.fossil.util.Version;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

public class CustomFlightMoveControl extends SmoothTurningMoveControl {
    private final PrehistoricFlying mob;
    private boolean shouldLandAtTarget;
    private Vec3 flyingWanted = Vec3.ZERO;
    private int tick;
    private int lastStuckCheck;
    private Vec3 lastStuckCheckPos = Vec3.ZERO;

    public CustomFlightMoveControl(PrehistoricFlying mob) {
        super(mob);
        this.mob = mob;
    }

    public void setFlyingTarget(double x, double y, double z, boolean shouldLandAtTarget) {
        flyingWanted = new Vec3(x, y, z);
        this.shouldLandAtTarget = shouldLandAtTarget;
        this.speedModifier = 1;
        if (Version.debugEnabled()) {
            int[] targets = new int[3];
            BlockState[] blocks = new BlockState[1];
            blocks[0] = shouldLandAtTarget ? Blocks.EMERALD_BLOCK.defaultBlockState() : Blocks.GOLD_BLOCK.defaultBlockState();
            targets[0] = Mth.floor(x);
            targets[1] = Mth.floor(y);
            targets[2] = Mth.floor(z);
            MessageHandler.DEBUG_CHANNEL.sendToPlayers(((ServerLevel) mob.level()).getPlayers(serverPlayer -> true),
                    new S2CMarkMessage(targets, blocks, false));
        }
    }

    @Override
    public void setWantedPosition(double x, double y, double z, double speed) {
        super.setWantedPosition(x, y, z, speed);
        if (mob.isUsingStuckNavigation()) {
            flyingWanted = new Vec3(x, y, z);
        }
    }

    public void setOperation(Operation operation) {
        this.operation = operation;
    }

    public boolean isShouldLandAtTarget() {
        return shouldLandAtTarget;
    }

    @Override
    public void tick() {
        if (!mob.isFlying()) {
            mob.setXRot(0);
            mob.setNoGravity(false);
            super.tick();
            return;
        }

        if (mob.isVehicle()) {
            mob.setNoGravity(true);
            return;
        }

        if (operation != Operation.MOVE_TO) {
            mob.setNoGravity(!shouldLandAtTarget);
            return;
        }

        mob.setNoGravity(!shouldLandAtTarget);
        Vec3 offset = flyingWanted.subtract(mob.position());
        double dist = offset.length();

        if (dist > Math.min(1.5, mob.getBbWidth())) {

            float currentYaw = Util.yRotToYaw(mob.getYRot());
            float targetYaw  = (float) Mth.atan2(offset.z, offset.x) * Mth.RAD_TO_DEG;

            // Fly straight toward the target.
            float newYaw = targetYaw;

            mob.setYRot(Util.yawToYRot(newYaw));
            mob.yBodyRot = mob.getYRot();

            float targetPitch = (float) Mth.atan2(-offset.y, offset.horizontalDistance()) * Mth.RAD_TO_DEG;
            float newPitch    = Mth.approachDegrees(mob.getXRot(), targetPitch, 4);
            mob.setXRot(newPitch);

            if (Mth.degreesDifferenceAbs(Util.yRotToYaw(mob.getYRot()), newYaw) < 3) {
                float limit = 1.2f;
                if (dist > 15)     limit = 2.2f;
                else if (dist > 5) limit = 1.8f;
                limit = shouldLandAtTarget ? limit * 0.8f : limit;
                speedModifier = Mth.approach((float) speedModifier, limit, (float) (0.05 * (1.8 / speedModifier)));
            } else {
                speedModifier = Mth.approach((float) speedModifier, 0.2f, 0.025f);
            }

            double targetXMove = speedModifier * Mth.cos(newYaw * Mth.DEG_TO_RAD) * Math.abs(offset.x / dist);
            double targetYMove = speedModifier * Mth.sin(-targetPitch * Mth.DEG_TO_RAD) * Math.abs(offset.y / dist);
            double targetZMove = speedModifier * Mth.sin(newYaw * Mth.DEG_TO_RAD) * Math.abs(offset.z / dist);
            Vec3 move = mob.getDeltaMovement();
            mob.setDeltaMovement(
                    Mth.approach((float) move.x, (float) targetXMove, 0.01f),
                    Mth.approach((float) move.y, (float) targetYMove, 0.01f),
                    Mth.approach((float) move.z, (float) targetZMove, 0.01f));

        } else {
            if (mob.isUsingStuckNavigation()) {
                mob.switchNavigator(false);
            }
            if (shouldLandAtTarget) {
                if (!mob.level().isEmptyBlock(mob.blockPosition().below())) {
                    mob.onReachAirTarget(BlockPos.containing(flyingWanted));
                    mob.setFlying(false);
                    mob.setXRot(0);
                    operation = Operation.WAIT;
                }
            } else {
                mob.onReachAirTarget(BlockPos.containing(flyingWanted));
                operation = Operation.WAIT;
            }
        }

        Vec3 offset2 = flyingWanted.subtract(mob.position());
        if (mob.verticalCollisionBelow && offset2.y < 0) {
            if (!mob.isUsingStuckNavigation()) {
                if (offset2.horizontalDistance() < 6 && offset2.y > -3) {
                    mob.setFlying(false);
                    operation = Operation.WAIT;
                    mob.moveTo(flyingWanted, true, false);
                } else {
                    mob.doStuckNavigation(flyingWanted);
                }
            }
        } else if (mob.horizontalCollision || mob.verticalCollision) {
            doStuckDetection(mob.position());
        }
    }

    private void doStuckDetection(Vec3 pos) {
        tick++;
        if (tick - lastStuckCheck > 10) {
            if (pos.distanceToSqr(lastStuckCheckPos) < 2.25) {
                if (mob.isUsingStuckNavigation()) {
                    mob.getNavigation().recomputePath();
                } else {
                    mob.doStuckNavigation(flyingWanted);
                }
            }
            lastStuckCheck = tick;
            lastStuckCheckPos = pos;
        }
    }
}