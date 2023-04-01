package com.fossil.fossil.entity.ai;

import com.fossil.fossil.entity.prehistoric.base.Prehistoric;
import com.fossil.fossil.item.ModItems;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.phys.Vec3;

public class RidingInWaterGoal extends Goal {
    private static final float PLAYER_SPEED = 0.98f;
    private static final int FOLLOW_TIME_WITHOUT_WHIP = 120;
    private final Prehistoric dino;
    private Player rider;
    private int lastSeenWhipTicks;

    public RidingInWaterGoal(Prehistoric dino) {
        this.dino = dino;
    }

    private boolean hasEquipped(Item item) {
        return rider.getItemInHand(InteractionHand.MAIN_HAND).is(item);
    }

    @Override
    public boolean canUse() {
        if (dino.getControllingPassenger() instanceof Player player) {
            rider = player;
            return hasEquipped(ModItems.WHIP.get());
        }
        return false;
    }

    @Override
    public boolean canContinueToUse() {
        if (lastSeenWhipTicks >= FOLLOW_TIME_WITHOUT_WHIP) {
            return false;
        }
        return super.canContinueToUse();
    }

    @Override
    public void start() {
        lastSeenWhipTicks = 0;
        dino.getNavigation().stop();
    }

    @Override
    public void stop() {
        rider = null;
    }

    @Override
    public void tick() {
        //TODO: Wont be called because goals arent ticked while the entity is immobile
        if (rider != null) {
            if (hasEquipped(ModItems.WHIP.get())) {
                lastSeenWhipTicks = 0;
            } else {
                lastSeenWhipTicks++;
            }
            float speedX = rider.xxa / PLAYER_SPEED * (rider.isSprinting() ? 4 : 1);
            float speedZ = rider.zza / PLAYER_SPEED * (rider.isSprinting() ? 4 : 1);
            float playerSpeed = Math.max(Math.abs(speedX), Math.abs(speedZ));
            Vec3 look = rider.getLookAngle();
            double dir = (speedZ/(speedX * 2 + (speedX < 0 ? -2 : 2)))- Math.min(speedX, 0);
            if (dir != 0) {
                look.yRot((float) (Math.PI * dir));
            }
            if (dino.rideableUnderWater() && dino.isInWater()) {
                if (playerSpeed > 0) {
                    float moveX = -Mth.sin(rider.getYRot() / 180 * Mth.PI) * Mth.cos(rider.getXRot() / 180 * Mth.PI);
                    float moveZ = Mth.cos(rider.getYRot() / 180 * Mth.PI) * Mth.cos(rider.getXRot() / 180 * Mth.PI);
                    dino.setDeltaMovement(moveX, dino.getDeltaMovement().y, moveZ);
                }
                if (Math.abs(look.y) > 0.4) {
                    double waterAddition = look.y > 0 ? Math.min(0.15, look.y) : Math.max(-0.15, look.y);
                    dino.setDeltaMovement(dino.getDeltaMovement().x, waterAddition, dino.getDeltaMovement().z);
                }
            }
        }
    }
}
