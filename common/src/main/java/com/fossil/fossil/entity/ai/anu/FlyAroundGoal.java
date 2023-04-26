package com.fossil.fossil.entity.ai.anu;

import com.fossil.fossil.entity.AnuBoss;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.ai.goal.Goal;

import java.util.EnumSet;

public class FlyAroundGoal extends Goal {
    private AnuBoss anu;
    private BlockPos targetPos;
    public FlyAroundGoal(AnuBoss anu) {
        this.anu = anu;
        targetPos = anu.blockPosition();
        setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
    }

    @Override
    public boolean canUse() {
        if (anu.isOnGround() || !anu.level.isEmptyBlock(anu.blockPosition().below())) {
            return true;
        }
        return false;
    }

    @Override
    public boolean canContinueToUse() {
       // if ()
        if (!anu.level.isEmptyBlock(targetPos) || targetPos.getY() < anu.level.getMinBuildHeight()) {
            return false;
        }
        return true;
    }

    @Override
    public void start() {
        //targetPos = new BlockPos(anu.ra)
        super.start();
    }

    @Override
    public void stop() {
        super.stop();
    }

    @Override
    public void tick() {
        super.tick();
    }
}
