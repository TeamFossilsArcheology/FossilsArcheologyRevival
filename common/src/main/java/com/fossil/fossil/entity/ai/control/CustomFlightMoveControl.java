package com.fossil.fossil.entity.ai.control;

import com.fossil.fossil.entity.prehistoric.base.PrehistoricFlying;
import com.fossil.fossil.network.MessageHandler;
import com.fossil.fossil.network.debug.MarkMessage;
import com.fossil.fossil.util.Version;
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
            targets[0] = (int) x;
            targets[1] = (int) y;
            targets[2] = (int) z;
            MessageHandler.DEBUG_CHANNEL.sendToPlayers(((ServerLevel) mob.level).getPlayers(serverPlayer -> true),
                    new MarkMessage(targets, blocks, false));
        }
    }

    public void setOperation(Operation operation) {
        this.operation = operation;
    }

    @Override
    public void tick() {
        if (!mob.isFlying()) {
            mob.setNoGravity(false);
            super.tick();
        } else if (operation == Operation.MOVE_TO) {
            mob.setNoGravity(true);
            Vec3 offset = flyingWanted.subtract(mob.position());
            double dist = offset.length();
            if (dist > Math.min(1.5, mob.getBbWidth())) {
                float initialYRot = mob.getYRot();
                float initialXRot = mob.getXRot();
                float initialYaw = Mth.wrapDegrees(initialYRot + 90);

                float targetYaw = (float) Mth.wrapDegrees(Mth.atan2(offset.z, offset.x) * Mth.RAD_TO_DEG);//atan2 always returns between -180 and 180 so no wrapdegrees needed
                float targetPitch = (float) (Mth.atan2(-offset.y, offset.horizontalDistance()) * Mth.RAD_TO_DEG);

                float newYaw = Mth.approachDegrees(initialYaw, targetYaw, 4);
                float newPitch = Mth.approachDegrees(initialXRot, targetPitch, 4);

                mob.setYRot(Mth.wrapDegrees(newYaw - 90));
                mob.yBodyRot = mob.getYRot();
                mob.setXRot(newPitch);
                if (Mth.degreesDifferenceAbs(initialYRot, mob.getYRot()) < 3) {
                    //Slows down before reaching the target to prevent overshooting
                    float limit = dist > 15 ? 2.2f : (dist > 5 ? 1.8f : 1.2f);
                    speedModifier = Mth.approach((float) speedModifier, limit, (float) (0.05 * (1.8 / speedModifier)));
                } else {
                    speedModifier = Mth.approach((float) speedModifier, 0.2f, 0.025f);
                }

                double targetXMove = speedModifier * Mth.cos(newYaw * Mth.DEG_TO_RAD) * Math.abs(offset.x / dist);
                double targetYMove = speedModifier * Mth.sin(-targetPitch * Mth.DEG_TO_RAD) * Math.abs(offset.y / dist);
                double targetZMove = speedModifier * Mth.sin(newYaw * Mth.DEG_TO_RAD) * Math.abs(offset.z / dist);
                Vec3 move = mob.getDeltaMovement();
                double newX = Mth.approach((float) move.x, (float) targetXMove, 0.01f);
                double newY = Mth.approach((float) move.y, (float) targetYMove, 0.01f);
                double newZ = Mth.approach((float) move.z, (float) targetZMove, 0.01f);
                mob.setDeltaMovement(newX, newY, newZ);
            } else {
                if (!shouldLandAtTarget || mob.isOnGround()) {
                    mob.onReachAirTarget(new BlockPos(flyingWanted));
                    operation = Operation.WAIT;
                    if (shouldLandAtTarget) {
                        mob.setFlying(false);
                    }
                }
            }
            if (mob.horizontalCollision) {
                //TODO: Make work in enclosures
                //operation = Operation.WAIT;
            }
        } else {
            mob.setNoGravity(false);
        }
    }
}
