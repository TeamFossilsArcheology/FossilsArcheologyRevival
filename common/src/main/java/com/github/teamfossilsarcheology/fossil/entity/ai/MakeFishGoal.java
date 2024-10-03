package com.github.teamfossilsarcheology.fossil.entity.ai;

import com.github.teamfossilsarcheology.fossil.entity.animation.AnimationLogic;
import com.github.teamfossilsarcheology.fossil.entity.prehistoric.base.PrehistoricSwimming;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public class MakeFishGoal extends Goal {
    protected final PrehistoricSwimming dino;
    protected int tick;

    public MakeFishGoal(PrehistoricSwimming dino) {
        this.dino = dino;
    }

    @Override
    public boolean canUse() {
        if (!dino.isHungry()) {
            return false;
        }
        if (dino.getRandom().nextInt(205) != 0) {
            return false;
        }
        return dino.isInWater();
    }

    @Override
    public boolean canContinueToUse() {
        if (!dino.isHungry()) {
            return false;
        }
        return dino.isInWater();
    }

    @Override
    public void start() {
        tick = 0;
        dino.getAnimationLogic().triggerAnimation(AnimationLogic.IDLE_CTRL, dino.nextEatingAnimation(), AnimationLogic.Category.NONE);
    }

    @Override
    public void tick() {
        tick++;
        if (tick == 20) {
            ItemStack itemStack;
            if (dino.getRandom().nextInt(2) == 0) {
                itemStack = new ItemStack(Items.COD, 1 + dino.getRandom().nextInt(2));
            } else {
                itemStack = new ItemStack(Items.SALMON, 1 + dino.getRandom().nextInt(2));
            }
            dino.spawnAtLocation(itemStack, 1);
        }
    }
}
