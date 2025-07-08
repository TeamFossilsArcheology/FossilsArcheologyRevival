package com.github.teamfossilsarcheology.fossil.entity.ai.control;

import com.github.teamfossilsarcheology.fossil.entity.prehistoric.base.Prehistoric;
import com.github.teamfossilsarcheology.fossil.entity.util.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.VoxelShape;

public class SmoothTurningMoveControl extends MoveControl {

    public SmoothTurningMoveControl(Prehistoric prehistoric) {
        super(prehistoric);
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
            float turnMod = 1;
            if (horizontalDist > 0.12) {
                //Prevents spinning if mob overshoots the wanted position
                float newYRot = Util.yawToYRot(Mth.atan2(z, x) * Mth.RAD_TO_DEG);
                float maxTurn = ((Prehistoric) mob).getMaxTurnDistancePerTick();
                //Turn of 180 becomes 180-maxTurn to speed up smaller mobs
                float turnDiff = Math.max(0, Mth.degreesDifferenceAbs(mob.getYRot(), newYRot) - maxTurn);
                //Slow down mob while turning
                turnMod = Mth.lerp(turnDiff / 180f, 1, 0.1f);
                mob.setYRot(rotlerp(mob.getYRot(), newYRot, maxTurn));
            }
            mob.setSpeed((float) (turnMod * speedModifier * mob.getAttributeValue(Attributes.MOVEMENT_SPEED)));
            if (mob.isInWater()) {
                mob.setSpeed(mob.getSpeed() * 2);
            }
            BlockPos blockPos = mob.blockPosition();
            BlockState blockState = mob.level().getBlockState(blockPos);
            VoxelShape voxelShape = blockState.getCollisionShape(mob.level(), blockPos);
            boolean jump = false;
            if (y > mob.maxUpStep() && y <= 1.5) {
                if (mob.horizontalCollision) {
                    jump = true;
                } else {
                    //jumpDistance: horizontal speed * time until jump done
                    double jumpDistance = Math.max(0.5, Util.attributeToSpeed(mob.getSpeed()) * 0.4);
                    if (!mob.level().isEmptyBlock(BlockPos.containing(wantedX, wantedY - 1, wantedZ))) {//TODO: More tests
                        jumpDistance += 1.3;
                    }
                    if (horizontalDist < jumpDistance) {
                        jump = true;
                    }
                }
            }
            if (!jump && !voxelShape.isEmpty() && mob.getY() < voxelShape.max(Direction.Axis.Y) + blockPos.getY() && !blockState.is(BlockTags.DOORS) && !blockState.is(BlockTags.FENCES)) {
                jump = true;
            }
            if (jump) {
                mob.getJumpControl().jump();
                operation = Operation.JUMPING;
            }
        } else if (operation == Operation.JUMPING) {
            mob.setSpeed((float) (speedModifier * mob.getAttributeValue(Attributes.MOVEMENT_SPEED)));
            if (mob.onGround()) {
                operation = Operation.WAIT;
            }
        } else {
            super.tick();
        }
    }
}
