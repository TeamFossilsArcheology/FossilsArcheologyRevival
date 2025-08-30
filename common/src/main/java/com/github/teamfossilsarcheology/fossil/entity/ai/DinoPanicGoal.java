package com.github.teamfossilsarcheology.fossil.entity.ai;

import com.github.teamfossilsarcheology.fossil.entity.prehistoric.base.Prehistoric;
import com.github.teamfossilsarcheology.fossil.entity.prehistoric.base.PrehistoricEntityInfoAI;
import com.github.teamfossilsarcheology.fossil.entity.prehistoric.base.PrehistoricFlocking;
import com.github.teamfossilsarcheology.fossil.entity.prehistoric.base.PrehistoricSwimming;
import com.github.teamfossilsarcheology.fossil.network.MessageHandler;
import com.github.teamfossilsarcheology.fossil.network.debug.S2CMarkMessage;
import com.github.teamfossilsarcheology.fossil.util.Version;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.behavior.BehaviorUtils;
import net.minecraft.world.entity.ai.goal.PanicGoal;
import net.minecraft.world.entity.ai.util.DefaultRandomPos;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

public class DinoPanicGoal extends PanicGoal {
    private LivingEntity panicReason;

    public DinoPanicGoal(Prehistoric dino, double speedModifier) {
        super(dino, speedModifier);
    }

    @Override
    public boolean canUse() {
        if (((Prehistoric) mob).aiResponseType() != PrehistoricEntityInfoAI.Response.SCARED) {
            return false;
        }
        return super.canUse();
    }

    @Override
    protected boolean shouldPanic() {
        if (mob instanceof PrehistoricFlocking flocking && flocking.getFlockAttackedTarget() != null) {
            return true;
        }
        return super.shouldPanic();
    }

    @Override
    public void start() {
        super.start();
        ((Prehistoric) mob).sleepSystem.setDisabled(true);
        ((Prehistoric) mob).sitSystem.setDisabled(true);
        if (mob instanceof PrehistoricFlocking flocking && flocking.getFlockAttackedTarget() != null) {
            panicReason = flocking.getFlockAttackedTarget();
        }
    }

    @Override
    public void stop() {
        super.stop();
        ((Prehistoric) mob).sleepSystem.setDisabled(false);
        ((Prehistoric) mob).sitSystem.setDisabled(false);
    }

    @Override
    protected boolean findRandomPosition() {
        Vec3 targetPos;
        if (panicReason != null) {
            targetPos = DefaultRandomPos.getPosAway(mob, 25, 4, panicReason.position());
        } else if (mob instanceof PrehistoricSwimming swimming && swimming.isInWater()) {
            targetPos = BehaviorUtils.getRandomSwimmablePos(mob, 10, 7);
        } else {
            targetPos = DefaultRandomPos.getPos(mob, 5, 4);
        }
        if (targetPos == null) {
            return false;
        } else {
            posX = targetPos.x;
            posY = targetPos.y;
            posZ = targetPos.z;
            if (Version.debugEnabled()) {
                int[] targets = new int[3];
                BlockState[] blocks = new BlockState[1];
                blocks[0] = Blocks.GOLD_BLOCK.defaultBlockState();
                targets[0] = Mth.floor(posX);
                targets[1] = Mth.floor(posY);
                targets[2] = Mth.floor(posZ);
                MessageHandler.DEBUG_CHANNEL.sendToPlayers(((ServerLevel) mob.level()).getPlayers(serverPlayer -> true),
                        new S2CMarkMessage(targets, blocks, false));
            }
            return true;
        }
    }
}
