package com.fossil.fossil.entity.ai.control;

import com.fossil.fossil.entity.prehistoric.base.Prehistoric;
import com.fossil.fossil.entity.util.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.VoxelShape;

public class SmoothTurningMoveControl extends MoveControl {
    public static Vec3 wanted;

    public SmoothTurningMoveControl(Prehistoric prehistoric) {
        super(prehistoric);
    }

    @Override
    public void setWantedPosition(double x, double y, double z, double speed) {
        //TODO: Remove for release because bad
        wanted = new Vec3(x, y, z);
        super.setWantedPosition(x, y, z, speed);
    }

    @Override
    public void tick() {
        if (operation == Operation.MOVE_TO) {
            operation = Operation.WAIT;
            double x = wantedX - mob.getX();
            double y = wantedY - mob.getY();
            double z = wantedZ - mob.getZ();
            double horizontalDist = x * x + z * z;
            double dist = horizontalDist + y * y;
            if (dist < 2.500000277905201E-7) {
                mob.setZza(0);
                return;
            }
            if (horizontalDist > 0.12) {
                //Prevents spinning if mob overshoots the wanted position
                float newYRot = Util.yawToYRot(Mth.atan2(z, x) * Mth.RAD_TO_DEG);
                float turn = ((Prehistoric) mob).getMaxTurnDistancePerTick();
                mob.setYRot(rotlerp(mob.getYRot(), newYRot, turn));
            }
            mob.setSpeed((float) (speedModifier * mob.getAttributeValue(Attributes.MOVEMENT_SPEED)));
            BlockPos blockPos = mob.blockPosition();
            BlockState blockState = mob.level.getBlockState(blockPos);
            VoxelShape voxelShape = blockState.getCollisionShape(mob.level, blockPos);
            double distance = Math.max(1, Mth.square(Math.min(1.8, mob.getBbWidth() / 2 + 0.1)));
            Vec3 move = mob.getDeltaMovement();
            if (y > mob.maxUpStep && (horizontalDist < distance || move.horizontalDistance() < 2.5E-7) || !voxelShape.isEmpty() && mob.getY() < voxelShape.max(Direction.Axis.Y) + blockPos.getY() && !blockState.is(BlockTags.DOORS) && !blockState.is(BlockTags.FENCES)) {
                mob.getJumpControl().jump();
                operation = Operation.JUMPING;
            }
        } else if (operation == Operation.JUMPING) {
            mob.setSpeed((float) (speedModifier * mob.getAttributeValue(Attributes.MOVEMENT_SPEED)));
            if (mob.isOnGround()) {
                operation = Operation.WAIT;
            }
        } else {
            super.tick();
        }
    }
}
