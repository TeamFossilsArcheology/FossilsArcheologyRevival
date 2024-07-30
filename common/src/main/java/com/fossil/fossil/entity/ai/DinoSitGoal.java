package com.fossil.fossil.entity.ai;

import com.fossil.fossil.entity.prehistoric.base.OrderType;
import com.fossil.fossil.entity.prehistoric.base.Prehistoric;
import net.minecraft.world.entity.ai.goal.Goal;

import java.util.EnumSet;

public class DinoSitGoal extends Goal {
    private final Prehistoric dino;
    private int ticksSat;

    public DinoSitGoal(Prehistoric dino) {
        this.dino = dino;
        setFlags(EnumSet.of(Flag.MOVE, Flag.JUMP));
    }

    @Override
    public boolean canUse() {
        if (!dino.isTame()) {
            return false;
        }
        if (dino.getCurrentOrder() == OrderType.STAY) {
            return true;
        }
        if (!dino.isInWater() && dino.getRandom().nextInt(1000) == 1 && !dino.hasTarget() && dino.getLastHurtByMob() != null) {
            return true;
        }
        return dino.isSitting();
    }

    @Override
    public boolean canContinueToUse() {
        if (dino.hasTarget() || dino.getLastHurtByMob() != null || dino.isInWater() || dino.getCurrentOrder() != OrderType.STAY) {
            return false;
        }
        if (ticksSat > 100 && dino.getRandom().nextInt(100) == 1) {
            return false;
        }
        return dino.isSitting();
    }

    @Override
    public void start() {
        dino.getNavigation().stop();
        dino.setSitting(true);
        ticksSat = 0;
    }

    @Override
    public void stop() {
        dino.setSitting(false);
    }

    @Override
    public void tick() {
        ticksSat++;
    }
}
