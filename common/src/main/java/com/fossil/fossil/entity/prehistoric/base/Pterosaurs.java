package com.fossil.fossil.entity.prehistoric.base;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.animal.FlyingAnimal;
import net.minecraft.world.level.Level;

public abstract class Pterosaurs extends Prehistoric implements FlyingAnimal {

    public Pterosaurs(EntityType<? extends Pterosaurs> entityType, Level level, boolean isMultiPart) {
        super(entityType, level, isMultiPart);
    }

    public abstract ServerAnimationInfo getTakeOffAnimation();
}
