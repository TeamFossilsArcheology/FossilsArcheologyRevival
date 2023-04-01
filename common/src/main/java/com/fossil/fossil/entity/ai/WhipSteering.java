package com.fossil.fossil.entity.ai;

import com.fossil.fossil.entity.prehistoric.base.Prehistoric;
import com.fossil.fossil.entity.prehistoric.base.PrehistoricEntityTypeAI;
import com.fossil.fossil.item.ModItems;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;

public class WhipSteering {
    private static final int FOLLOW_TIME_WITHOUT_WHIP = 120;
    private static final float PLAYER_SPEED = 0.98f;
    private int lastSeenWhipTicks = -1;

    public Vec3 travel(Prehistoric prehistoric, LivingEntity rider, Vec3 travelVector) {
        if (rider.getItemInHand(InteractionHand.MAIN_HAND).is(ModItems.WHIP.get())) {
            lastSeenWhipTicks = 0;
        } else {
            lastSeenWhipTicks++;
        }

        if (lastSeenWhipTicks >= FOLLOW_TIME_WITHOUT_WHIP || !prehistoric.rideableUnderWater() || !prehistoric.isInWater()) {
            return travelVector;
        }
        Vec3 newTravelVector = travelVector;
        float speedX = rider.xxa / PLAYER_SPEED * (rider.isSprinting() ? 4 : 1);
        float speedZ = rider.zza / PLAYER_SPEED * (rider.isSprinting() ? 4 : 1);
        Vec3 look = rider.getLookAngle();
        double dir = (speedZ / (speedX * 2 + (speedX < 0 ? -2 : 2))) - Math.min(speedX, 0);
        if (dir != 0) {
            look.yRot((float) (Math.PI * dir));
        }
        float playerSpeed = Math.max(Math.abs(speedX), Math.abs(speedZ));
        if (playerSpeed > 0) {
            float moveX = -Mth.sin(rider.getYRot() / 180 * Mth.PI) * Mth.cos(rider.getXRot() / 180 * Mth.PI);
            float moveZ = Mth.cos(rider.getYRot() / 180 * Mth.PI) * Mth.cos(rider.getXRot() / 180 * Mth.PI);
            newTravelVector = new Vec3(moveX, travelVector.y, moveZ);
            //TODO: Figure out if this is needed
        }
        if (Math.abs(look.y) > 0.4 && (prehistoric.aiMovingType() == PrehistoricEntityTypeAI.Moving.AQUATIC
                || prehistoric.aiMovingType() == PrehistoricEntityTypeAI.Moving.SEMIAQUATIC)) {
            double waterAddition = look.y > 0 ? Math.min(0.15, look.y) : Math.max(-0.15, look.y);
            newTravelVector = new Vec3(travelVector.x, waterAddition, travelVector.z);
        }
        prehistoric.setDeltaMovement(prehistoric.getDeltaMovement().x, newTravelVector.y, prehistoric.getDeltaMovement().z);
        return newTravelVector;
    }
}
