package com.fossil.fossil.entity.ai;

import com.fossil.fossil.entity.prehistoric.base.OrderType;
import com.fossil.fossil.entity.prehistoric.base.Prehistoric;
import com.fossil.fossil.entity.prehistoric.base.PrehistoricEntityInfoAI;
import net.minecraft.world.entity.ai.goal.Goal;

import java.util.EnumSet;

public class DinoSleepGoal extends Goal {
    private final Prehistoric dino;

    public DinoSleepGoal(Prehistoric dino) {
        this.dino = dino;
        setFlags(EnumSet.of(Flag.MOVE, Flag.JUMP, Flag.LOOK));
    }

    @Override
    public boolean canUse() {
        if (dino.wantsToSleep() && canSleep()) {
            if (dino.aiActivityType() == PrehistoricEntityInfoAI.Activity.BOTH) {
                if (dino.getRandom().nextInt(1200) == 1) {
                    return true;
                }
            } else if (dino.aiActivityType() != PrehistoricEntityInfoAI.Activity.NO_SLEEP) {
                if (dino.getRandom().nextInt(200) == 1) {
                    return true;
                }
            }
        }
        return dino.isSleeping();
    }

    /**
     * @return whether something is preventing the mob from sleeping
     */
    protected boolean canSleep() {
        if (dino.hasTarget() || dino.getLastHurtByMob() != null || dino.isInWater()) {
            return false;
        }
        return dino.getCurrentOrder() != OrderType.FOLLOW;
    }

    @Override
    public boolean canContinueToUse() {
        if (!canSleep()) {
            return false;
        }
        if (!dino.wantsToSleep() && dino.ticksSlept > 100 && dino.getRandom().nextInt(100) == 1) {
            return false;
        }
        return dino.isSleeping();
    }

    @Override
    public void start() {
        dino.startSleeping(dino.blockPosition());
        dino.ticksSlept = 0;
    }

    @Override
    public void stop() {
        dino.setSleeping(false);
    }

    @Override
    public void tick() {
        dino.ticksSlept++;
    }
}
