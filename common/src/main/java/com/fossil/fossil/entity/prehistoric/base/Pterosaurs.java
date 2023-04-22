package com.fossil.fossil.entity.prehistoric.base;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.animal.FlyingAnimal;
import net.minecraft.world.level.Level;

public abstract class Pterosaurs extends Prehistoric implements FlyingAnimal {

    public Pterosaurs(EntityType<? extends Pterosaurs> entityType, Level level, boolean isCannibalistic, float minScale, float maxScale, float baseKnockBackResistance, float maxKnockBackResistance, int teenAgeDays, int adultAgeDays, double baseDamage, double maxDamage, double baseHealth, double maxHealth, double baseSpeed, double maxSpeed, double baseArmor, double maxArmor) {
        super(entityType, level, false, isCannibalistic, minScale, maxScale, baseKnockBackResistance, maxKnockBackResistance, teenAgeDays, adultAgeDays, baseDamage, maxDamage, baseHealth, maxHealth, baseSpeed, maxSpeed, baseArmor, maxArmor);
    }

    public abstract ServerAnimationInfo getTakeOffAnimation();
}
