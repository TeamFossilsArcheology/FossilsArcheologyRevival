package com.fossil.fossil.entity.prehistoric.base;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;

public abstract class PrehistoricSwimming extends Prehistoric {

    public PrehistoricSwimming(EntityType<? extends Prehistoric> entityType, Level level, boolean isMultiPart) {
        super(entityType, level, isMultiPart);
    }
}
