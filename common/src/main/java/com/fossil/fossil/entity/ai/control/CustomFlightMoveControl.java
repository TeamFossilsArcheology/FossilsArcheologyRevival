package com.fossil.fossil.entity.ai.control;

import com.fossil.fossil.entity.prehistoric.base.PrehistoricFlying;
import com.fossil.fossil.network.MessageHandler;
import com.fossil.fossil.network.debug.MarkMessage;
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

    public void setWantedPosition(double x, double y, double z, boolean shouldLandAtTarget) {
        flyingWanted = new Vec3(x, y, z);
        this.shouldLandAtTarget = shouldLandAtTarget;

        int[] targets = new int[3];
        BlockState[] blocks = new BlockState[1];
        blocks[0] = shouldLandAtTarget ? Blocks.EMERALD_BLOCK.defaultBlockState() : Blocks.GOLD_BLOCK.defaultBlockState() ;
        targets[0] = (int) x;
        targets[1] = (int) y;
        targets[2] = (int) z;
        MessageHandler.DEBUG_CHANNEL.sendToPlayers(((ServerLevel) mob.level).getPlayers(serverPlayer -> true),
                new MarkMessage(targets, blocks, false));
    }

    public void setOperation(Operation operation) {
        this.operation = operation;
    }

    @Override
    public void tick() {
        if (!mob.isFlying()) {
            super.tick();
        } else if (operation == Operation.MOVE_TO) {
            double bbLength = mob.getBbWidth();
            double maxDist = Math.min(3, bbLength * bbLength);
            if (mob.distanceToSqr(flyingWanted) > maxDist){
                Vec3 offset = flyingWanted.subtract(mob.position());
                Vec3 move = mob.getDeltaMovement();
                move = move.add((Math.signum(offset.x) * 0.5 - move.x) * 0.2, (Math.signum(offset.y) * 0.5 - move.y) * 0.2, (Math.signum(offset.z) * 0.5 - move.z) * 0.2);
                mob.setDeltaMovement(move);
                float angle = (float) (Mth.atan2(move.z, move.x) * Mth.RAD_TO_DEG - 90);
                float rotation = Mth.wrapDegrees(angle - mob.getYRot());
                mob.setZza(0.5f);
                if (Math.abs(move.x) > 0.12 || Math.abs(move.z) > 0.12) {
                    mob.setYRot(mob.getYRot() + rotation);
                }
            } else {
                mob.onReachAirTarget(new BlockPos(flyingWanted));
                operation = Operation.WAIT;
                if (shouldLandAtTarget) {//TODO: and isOnGround
                    mob.setFlying(false);
                }
            }
            if (mob.horizontalCollision) {
                //TODO: Make work in enclosures
                //operation = Operation.WAIT;
            }
        } else {

        }
    }
}
