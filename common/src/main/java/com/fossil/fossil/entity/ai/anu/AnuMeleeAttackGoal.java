package com.fossil.fossil.entity.ai.anu;

import com.fossil.fossil.entity.monster.AnuBoss;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;

public class AnuMeleeAttackGoal extends MeleeAttackGoal {
    private final AnuBoss anu;

    public AnuMeleeAttackGoal(AnuBoss anu, double speedModifier, boolean followingTargetEvenIfNotSeen) {
        super(anu, speedModifier, followingTargetEvenIfNotSeen);
        this.anu = anu;
    }

    @Override
    public boolean canUse() {
        return anu.getAttackMode() == AnuBoss.AttackMode.MELEE && super.canUse();
    }
}
