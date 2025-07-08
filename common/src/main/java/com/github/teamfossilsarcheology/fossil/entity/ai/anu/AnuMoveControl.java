package com.github.teamfossilsarcheology.fossil.entity.ai.anu;

import com.eliotlash.mclib.utils.MathHelper;
import com.github.teamfossilsarcheology.fossil.entity.monster.AnuBoss;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.phys.Vec3;

public class AnuMoveControl extends MoveControl {
    private final AnuBoss anu;

    public AnuMoveControl(AnuBoss mob) {
        super(mob);
        this.anu = mob;
    }

    @Override
    public void tick() {
        if (anu.phaseSystem.getCurrentPhase().isFlying()) {
            if (operation == Operation.MOVE_TO) {
                mob.setNoGravity(true);
                //TODO: Change
                double x = wantedX - mob.getX();
                double y = wantedY - mob.getY();
                double z = wantedZ - mob.getZ();
                double dist = x * x + y * y + z * z;
                if (dist < 0.1 || mob.horizontalCollision) {
                    mob.setYya(0);
                    mob.setZza(0);
                    operation = Operation.WAIT;
                    return;
                }
                Vec3 current = mob.getDeltaMovement();
                double newX = (Math.signum(x) * 0.5 - current.x) * 0.05;
                double newY = (Math.signum(y) * 0.7 - current.y) * 0.1;
                double newZ = (Math.signum(z) * 0.5 - current.z) * 0.05;
                Vec3 next = current.add(newX, newY, newZ);
                mob.setDeltaMovement(new Vec3(next.x, Double.isNaN(next.y) ? 0 : next.y, next.z));
                float angle = (float) (Math.atan2(next.z, next.x) * Mth.RAD_TO_DEG) - 90;
                float rotation = MathHelper.wrapDegrees(angle - mob.getYRot());
                mob.setYRot(mob.getYRot() + rotation);
            }
        } else {
            mob.setNoGravity(false);
            super.tick();
        }
    }
}
