package com.fossil.fossil.entity.util;


import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;

public abstract class EntityToyBase extends LivingEntity {

    protected EntityToyBase(EntityType<? extends LivingEntity> type, Level level) {
        super(type, level);
    }
}
