package com.fossil.fossil.entity.ai.anu;

import com.fossil.fossil.entity.AnuBoss;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.AvoidEntityGoal;

public class AnuAvoidEntityGoal<T extends LivingEntity> extends AvoidEntityGoal<T> {
    private final AnuBoss anu;

    public AnuAvoidEntityGoal(AnuBoss anu, Class<T> clazz, float maxDist, double walkSpeedModifier, double sprintSpeedModifier) {
        super(anu, clazz, maxDist, walkSpeedModifier, sprintSpeedModifier);
        this.anu = anu;
    }

    @Override
    public boolean canUse() {
        return anu.getAttackMode() == AnuBoss.AttackMode.DEFENSE && super.canUse();
    }
}
