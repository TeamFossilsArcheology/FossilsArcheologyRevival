package com.github.teamfossilsarcheology.fossil.entity.ai;

import com.github.teamfossilsarcheology.fossil.entity.prehistoric.base.Prehistoric;
import com.github.teamfossilsarcheology.fossil.item.ModItems;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;

public class WhipSteering {
    private static final int FOLLOW_TIME_WITHOUT_WHIP = 120;
    private final Prehistoric dino;
    private int lastSeenWhipTicks = -1;

    public WhipSteering(Prehistoric dino) {
        this.dino = dino;
    }

    public boolean trySteering(LivingEntity rider) {
        if (rider.getItemInHand(InteractionHand.MAIN_HAND).is(ModItems.WHIP.get())) {
            lastSeenWhipTicks = 0;
        } else {
            lastSeenWhipTicks++;
        }

        return lastSeenWhipTicks < FOLLOW_TIME_WITHOUT_WHIP;
    }

    public void airTravel(Vec3 travelVector) {
        dino.moveRelative(dino.getSpeed() * 0.5f, new Vec3(travelVector.x, 0, travelVector.z));
        dino.move(MoverType.SELF, dino.getDeltaMovement());
        dino.setDeltaMovement(dino.getDeltaMovement().scale(0.9f));
    }

    public void slowWaterTravel(Vec3 travelVector) {
        boolean movement = Math.abs(travelVector.x) > 0 || Math.abs(travelVector.z) > 0;
        double downwardMovement = dino.getFluidHeight(FluidTags.WATER) > dino.getFluidJumpThreshold() * dino.getScale() ? 0 : -0.15;
        if (movement) {
            dino.setDeltaMovement(0, downwardMovement, 0);
        } else {
            dino.setDeltaMovement(dino.getDeltaMovement().x / 2, downwardMovement, dino.getDeltaMovement().z / 2);
        }
        double upwardMovement = dino.isEyeInFluid(FluidTags.WATER) ? 0.15 : 0;
        upwardMovement += dino.horizontalCollision ? 0.55 : 0;
        dino.moveRelative(dino.getSpeed(), new Vec3(travelVector.x, upwardMovement, travelVector.z));
        if (dino.horizontalCollision) {
            dino.setDeltaMovement(dino.getDeltaMovement().add(0, 0.1, 0));
        }
        dino.move(MoverType.SELF, dino.getDeltaMovement());
    }

    public void waterTravel(Vec3 travelVector, Player rider, float playerJumpPendingScale) {
        Vec3 look = rider.getLookAngle();
        boolean movement = Math.abs(travelVector.x) > 0 || Math.abs(travelVector.z) > 0;
        double downwardMovement = playerJumpPendingScale <= 0 && look.y < -0.4 ? -0.3 : 0;
        if (movement) {
            dino.setDeltaMovement(0, 0, 0);
        } else {
            dino.setDeltaMovement(dino.getDeltaMovement().x / 2, 0, dino.getDeltaMovement().z / 2);
        }
        double upwardMovement = playerJumpPendingScale > 0 ? 0.3 : 0;
        dino.moveRelative(dino.getSpeed(), new Vec3(travelVector.x, upwardMovement + downwardMovement, travelVector.z));
        dino.move(MoverType.SELF, dino.getDeltaMovement());
    }
}
