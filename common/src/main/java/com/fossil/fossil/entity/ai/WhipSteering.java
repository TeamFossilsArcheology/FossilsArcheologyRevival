package com.fossil.fossil.entity.ai;

import com.fossil.fossil.entity.prehistoric.base.Prehistoric;
import com.fossil.fossil.entity.prehistoric.base.PrehistoricSwimming;
import com.fossil.fossil.item.ModItems;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.phys.Vec3;

public class WhipSteering {
    private static final int FOLLOW_TIME_WITHOUT_WHIP = 120;
    private int lastSeenWhipTicks = -1;
    private final Prehistoric dino;

    public WhipSteering(Prehistoric dino) {
        this.dino = dino;
    }

    public boolean trySteer(LivingEntity rider) {
        if (rider.getItemInHand(InteractionHand.MAIN_HAND).is(ModItems.WHIP.get())) {
            lastSeenWhipTicks = 0;
        } else {
            lastSeenWhipTicks++;
        }

        return lastSeenWhipTicks < FOLLOW_TIME_WITHOUT_WHIP;
    }

    public void slowWaterTravel(Vec3 travelVector) {
        boolean movement = Math.abs(travelVector.x) > 0 || Math.abs(travelVector.z) > 0;
        double downwardMovement = dino.isEyeInFluid(FluidTags.WATER) ? 0 : -0.15;
        if (movement) {
            dino.setDeltaMovement(0, Math.max(dino.getDeltaMovement().y, 0) + downwardMovement, 0);
        } else {
            dino.setDeltaMovement(dino.getDeltaMovement().x / 2, downwardMovement, dino.getDeltaMovement().z / 2);
        }
        double upwardMovement = dino.getFluidHeight(FluidTags.WATER) > dino.getFluidJumpThreshold() ? 0.15 : 0;
        dino.moveRelative(dino.getSpeed(), new Vec3(travelVector.x, upwardMovement, travelVector.z));
        if (dino.horizontalCollision) {
            dino.setDeltaMovement(dino.getDeltaMovement().add(0, 0.1, 0));
        }
        dino.move(MoverType.SELF, dino.getDeltaMovement());
    }

    public void waterTravel(Vec3 travelVector, LocalPlayer rider) {
        Vec3 look = rider.getLookAngle();
        boolean movement = Math.abs(travelVector.x) > 0 || Math.abs(travelVector.z) > 0;
        double downwardMovement = !rider.input.jumping && look.y < -0.4 ? -0.15 : 0;
        if (movement) {
            dino.setDeltaMovement(0, downwardMovement, 0);
        } else {
            dino.setDeltaMovement(dino.getDeltaMovement().x / 2, downwardMovement, dino.getDeltaMovement().z / 2);
        }
        double upwardMovement = rider.input.jumping ? dino.getJumpStrength() * 0.15 : 0;
        float speed = dino instanceof PrehistoricSwimming swimming ? swimming.swimSpeed() : dino.getSpeed();
        dino.moveRelative(speed, new Vec3(travelVector.x, upwardMovement, travelVector.z));
        dino.move(MoverType.SELF, dino.getDeltaMovement());
    }
}
