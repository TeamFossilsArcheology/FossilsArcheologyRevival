package com.github.teamfossilsarcheology.fossil.client.gui.debug.navigation;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.VoxelShape;

public class DebugMoveControl {
    public Operation operation = Operation.WAIT;
    protected double wantedX;
    protected double wantedY;
    protected double wantedZ;
    public double speedModifier = 1;
    private final Player player;
    public Vec3 move = Vec3.ZERO;
    public DebugMoveControl(Player player) {
        this.player = player;
    }
    public void setWantedPosition(double x, double y, double z, double speed) {
        this.wantedX = x;
        this.wantedY = y;
        this.wantedZ = z;
        this.speedModifier = speed;
        if (this.operation != Operation.JUMPING) {
            this.operation = Operation.MOVE_TO;
        }
    }
    public float zza;
    public float yRot;
    public float speed;
    public boolean jump;
    public void tick() {
        if (operation == Operation.MOVE_TO) {
            operation = Operation.WAIT;
            double x = wantedX - player.getX();
            double y = wantedY - player.getY();
            double z = wantedZ - player.getZ();
            double dist = x * x + y * y + z * z;
            if (dist < 2.500000277905201E-7) {
                zza = 0;
                return;
            }
            float newYRot = (float)(Mth.atan2(z, x) * Mth.RAD_TO_DEG) - 90;
            float turn =  Mth.clamp(90 - PathingRenderer.getBbWidth() * 20, 10, 90);
            yRot = (rotlerp(player.getYRot(), newYRot, turn));
            speed = ((float)(speedModifier * player.getAttributeValue(Attributes.MOVEMENT_SPEED)));
            zza = speed;
            BlockPos blockPos = player.blockPosition();
            BlockState blockState = player.level().getBlockState(blockPos);
            VoxelShape voxelShape = blockState.getCollisionShape(player.level(), blockPos);
            if (y > player.maxUpStep() && x * x + z * z < Math.max(1, Mth.square(PathingRenderer.getBbWidth())) || !voxelShape.isEmpty() && player.getY() < voxelShape.max(Direction.Axis.Y) + blockPos.getY() && !blockState.is(BlockTags.DOORS) && !blockState.is(BlockTags.FENCES)) {
                operation = Operation.JUMPING;
            }
        } else if (operation == Operation.JUMPING) {
            speed = ((float)(speedModifier * player.getAttributeValue(Attributes.MOVEMENT_SPEED)));
            zza = speed;
            if (player.onGround()) {
                operation = Operation.WAIT;
            }
        } else {
            zza = (0.0f);
        }
        move = getInputVector(new Vec3(0, 0, zza), speed, yRot);
    }

    private static Vec3 getInputVector(Vec3 relative, float motionScaler, float facing) {
        double d = relative.lengthSqr();
        if (d < 1.0E-7) {
            return Vec3.ZERO;
        }
        Vec3 vec3 = (d > 1.0 ? relative.normalize() : relative).scale(motionScaler);
        float f = Mth.sin(facing * ((float)Math.PI / 180));
        float g = Mth.cos(facing * ((float)Math.PI / 180));
        return new Vec3(vec3.x * (double)g - vec3.z * (double)f, vec3.y, vec3.z * (double)g + vec3.x * (double)f);
    }
    protected float rotlerp(float sourceAngle, float targetAngle, float maximumChange) {
        float g;
        float f = Mth.wrapDegrees(targetAngle - sourceAngle);
        if (f > maximumChange) {
            f = maximumChange;
        }
        if (f < -maximumChange) {
            f = -maximumChange;
        }
        if ((g = sourceAngle + f) < 0.0f) {
            g += 360.0f;
        } else if (g > 360.0f) {
            g -= 360.0f;
        }
        return g;
    }

    public enum Operation {
        WAIT,
        MOVE_TO,
        STRAFE,
        JUMPING

    }
}