package com.fossil.fossil.entity.ai.anu;

import com.fossil.fossil.entity.monster.AnuBoss;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.ai.goal.Goal;

import java.util.EnumSet;

public class FlyAroundGoal extends Goal {
    private final AnuBoss anu;
    private BlockPos targetPos;

    public FlyAroundGoal(AnuBoss anu) {
        this.anu = anu;
        setFlags(EnumSet.of(Goal.Flag.MOVE));
    }

    @Override
    public boolean canUse() {
        if (anu.getAttackMode() == AnuBoss.AttackMode.FLIGHT) {
            float targetX = Mth.randomBetween(anu.getRandom(), -30, 30);
            float targetY = Mth.randomBetween(anu.getRandom(), -5, 2);
            float targetZ = Mth.randomBetween(anu.getRandom(), -30, 30);
            targetPos = new BlockPos(anu.getSpawnPos().add(targetX, targetY, targetZ));
            if (!anu.level.isEmptyBlock(targetPos.below())) {
                targetPos = targetPos.above();
            }
            return anu.level.isEmptyBlock(targetPos);
        }
        return false;
    }

    @Override
    public boolean canContinueToUse() {
        return anu.getMoveControl().hasWanted();
    }

    @Override
    public void start() {
        anu.getMoveControl().setWantedPosition(targetPos.getX(), targetPos.getY(), targetPos.getZ(), 1);
    }

    @Override
    public void stop() {
        anu.getNavigation().stop();
    }
}
