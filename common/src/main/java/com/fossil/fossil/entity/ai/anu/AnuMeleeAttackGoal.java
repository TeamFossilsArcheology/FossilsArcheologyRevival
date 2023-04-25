package com.fossil.fossil.entity.ai.anu;

import com.fossil.fossil.entity.Anu;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;

public class AnuMeleeAttackGoal extends MeleeAttackGoal {
    private final Anu anu;

    public AnuMeleeAttackGoal(Anu anu, double speedModifier, boolean followingTargetEvenIfNotSeen) {
        super(anu, speedModifier, followingTargetEvenIfNotSeen);
        this.anu = anu;
    }

    @Override
    public boolean canUse() {
        return anu.getAttackMode() == Anu.AttackMode.MELEE && super.canUse();
    }
}
