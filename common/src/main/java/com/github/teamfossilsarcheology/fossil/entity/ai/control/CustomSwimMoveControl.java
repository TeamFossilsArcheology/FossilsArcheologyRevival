package com.github.teamfossilsarcheology.fossil.entity.ai.control;

import com.github.teamfossilsarcheology.fossil.entity.prehistoric.base.PrehistoricSwimming;
import com.github.teamfossilsarcheology.fossil.entity.util.Util;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.SmoothSwimmingMoveControl;

public class CustomSwimMoveControl extends SmoothSwimmingMoveControl {

    private final PrehistoricSwimming mob;

    public CustomSwimMoveControl(PrehistoricSwimming mob) {
        super(mob, 85, 10, 0.1f, 0.1f, true);
        this.mob = mob;
    }


    @Override
    public void tick() {
        mob.setNoGravity(false);
        if (operation == Operation.MOVE_TO && !mob.getNavigation().isDone()) {
            if (mob.isInWater()) mob.setNoGravity(true);
            double x = wantedX - mob.getX();
            double y = wantedY - mob.getY();
            double z = wantedZ - mob.getZ();
            double horizontalDist = x * x + z * z;
            double dist = horizontalDist + y * y;
            if (dist < 0.00000025) {
                mob.setZza(0);
            } else {
                if (horizontalDist > 0.3) {
                    float h = Util.clampTo360(Util.yawToYRot(Mth.atan2(z, x) * Mth.RAD_TO_DEG));
                    float g = rotlerp(Util.clampTo360(mob.getYRot()), h, 5);
                    mob.setYRot(g);
                    mob.yBodyRot = mob.getYRot();
                    mob.yHeadRot = mob.getYRot();
                }
                float i = (float) (speedModifier * mob.getAttributeValue(Attributes.MOVEMENT_SPEED)) * 1.5f;
                if (mob.isInWater()) {
                    mob.setSpeed(i * 0.1f);
                    double horDist = Math.sqrt(x * x + z * z);
                    float k;
                    if (Math.abs(y) > 0.00001 || Math.abs(horDist) > 0.00001) {
                        k = (float) (-Mth.atan2(y, horDist) * Mth.RAD_TO_DEG) + 90;
                        k = Mth.clamp(k, 30, 150);
                        float g = rotlerp(mob.getXRot() + 90, k, 5);
                        mob.setXRot(g - 90);
                    }
                    k = Mth.cos(mob.getXRot() * Mth.DEG_TO_RAD);
                    float l = Mth.sin(mob.getXRot() * Mth.DEG_TO_RAD);
                    mob.zza = k * i;
                    mob.yya = -l * i;
                }
            }
        } else {
            if (mob.isInWater()) mob.setNoGravity(true);
            mob.setSpeed(0);
            mob.setXxa(0);
            mob.setYya(0);
            mob.setZza(0);
        }
    }
}
