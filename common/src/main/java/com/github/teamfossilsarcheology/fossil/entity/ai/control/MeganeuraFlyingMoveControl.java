package com.github.teamfossilsarcheology.fossil.entity.ai.control;

import com.github.teamfossilsarcheology.fossil.entity.prehistoric.swimming.Meganeura;
import com.github.teamfossilsarcheology.fossil.entity.util.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.phys.Vec3;

public class MeganeuraFlyingMoveControl extends MoveControl {

    private final Meganeura meganeura;

    public MeganeuraFlyingMoveControl(Meganeura meganeura) {
        super(meganeura);
        this.meganeura = meganeura;
    }

    @Override
    public void tick() {
        if (meganeura.getAttachSystem().isAttached()) {
            mob.setNoGravity(true);
        } else if (operation == Operation.MOVE_TO) {
            mob.setNoGravity(true);
            double xDist = wantedX - mob.getX();
            double yDist = wantedY - mob.getY();
            double zDist = wantedZ - mob.getZ();
            double targetDistance = Mth.sqrt((float) (xDist * xDist + zDist * zDist + yDist * yDist));
            if (targetDistance < 2.500000277905201E-7 || (targetDistance <= mob.getBbWidth() / 2 && mob.level().isEmptyBlock(BlockPos.containing(wantedX, wantedY, wantedZ)))) {
                operation = Operation.WAIT;
                mob.setDeltaMovement(mob.getDeltaMovement().scale(0.5));
                return;
            }
            if (mob.getTarget() == null) {
                float newYRot = Util.yawToYRot(Mth.atan2(zDist, xDist) * Mth.RAD_TO_DEG);
                mob.setYRot(rotlerp(mob.getYRot(), newYRot, 25));
                mob.yBodyRot = mob.getYRot();
                Vec3 move = mob.getDeltaMovement();
                double xSpeed = move.x * 0.9 + 0.03 * xDist / targetDistance;
                double ySpeed = move.y * 0.9 + 0.03 * yDist / targetDistance;
                double zSpeed = move.z * 0.9 + 0.03 * zDist / targetDistance;
                mob.setDeltaMovement(xSpeed * speedModifier, ySpeed * speedModifier, zSpeed * speedModifier);
            } else {
                xDist = mob.getTarget().getX() - mob.getX();
                zDist = mob.getTarget().getZ() - mob.getZ();
                float newYRot = Util.yawToYRot(Mth.atan2(zDist, xDist) * Mth.RAD_TO_DEG);
                mob.setYRot(newYRot);
                mob.yBodyRot = newYRot;
            }
        } else {//TODO: Hover
            mob.setNoGravity(false);
            mob.setNoGravity(true);
        }
    }
}
