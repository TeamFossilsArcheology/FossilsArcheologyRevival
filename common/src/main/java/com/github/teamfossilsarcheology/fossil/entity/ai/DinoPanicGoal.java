package com.github.teamfossilsarcheology.fossil.entity.ai;

import com.github.teamfossilsarcheology.fossil.entity.prehistoric.base.Prehistoric;
import com.github.teamfossilsarcheology.fossil.entity.prehistoric.base.PrehistoricEntityInfoAI;
import com.github.teamfossilsarcheology.fossil.entity.prehistoric.base.PrehistoricSwimming;
import net.minecraft.world.entity.ai.behavior.BehaviorUtils;
import net.minecraft.world.entity.ai.goal.PanicGoal;
import net.minecraft.world.entity.ai.util.DefaultRandomPos;
import net.minecraft.world.phys.Vec3;

public class DinoPanicGoal extends PanicGoal {

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
    public void start() {
        super.start();
        ((Prehistoric)mob).sleepSystem.setDisabled(true);
        ((Prehistoric)mob).sitSystem.setDisabled(true);
    }

    @Override
    public void stop() {
        super.stop();
        ((Prehistoric)mob).sleepSystem.setDisabled(false);
        ((Prehistoric)mob).sitSystem.setDisabled(false);
    }

    @Override
    protected boolean findRandomPosition() {
        Vec3 targetPos;
        if (mob instanceof PrehistoricSwimming swimming && swimming.isInWater()) {
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
            return true;
        }
    }
}
