package com.fossil.fossil.entity.ai;

import com.fossil.fossil.entity.prehistoric.base.Prehistoric;
import com.fossil.fossil.entity.prehistoric.base.PrehistoricLeaping;
import net.minecraft.world.Difficulty;
import net.minecraft.world.entity.ai.goal.LeapAtTargetGoal;
import net.minecraft.world.entity.player.Player;

import java.util.EnumSet;

public class DinoLeapAtTargetGoal<T extends Prehistoric & PrehistoricLeaping> extends LeapAtTargetGoal {
    private final T dino;

    public DinoLeapAtTargetGoal(T dino) {
        super(dino, 0.4f);
        this.dino = dino;
        this.setFlags(EnumSet.of(Flag.JUMP, Flag.MOVE, Flag.LOOK));
    }

    @Override
    public boolean canUse() {
        if (dino.isImmobile() || !dino.useLeapAttack()) {
            return false;
        }
        if (dino.level.getDifficulty() == Difficulty.PEACEFUL && dino.getTarget() instanceof Player) {
            return false;
        }
        return super.canUse();
    }

    @Override
    public void start() {
        super.start();
        dino.lookAt(dino.getTarget(), 100, 100);
        dino.setCurrentAnimation(dino.nextLeapAnimation());
    }
}
