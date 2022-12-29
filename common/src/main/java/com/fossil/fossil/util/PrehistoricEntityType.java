package com.fossil.fossil.util;

import com.fossil.fossil.entity.ModEntities;
import com.fossil.fossil.entity.prehistoric.base.EntityPrehistoric;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;

public enum PrehistoricEntityType {
    TRICERATOPS(ModEntities.TRICERATOPS.get(), MobType.DINOSAUR, TimePeriod.MESOZOIC, Diet.HERBIVORE, 1);

    public static final ResourceLocation DINOSAUR_LOOT = null;
    private final EntityType<? extends EntityPrehistoric> entityType;
    public MobType mobType;
    private float eggScale = 1.0F;
    public TimePeriod timePeriod;
    public Diet diet;
    public Item eggItem;
    public Item uncultivatedEggItem;

    PrehistoricEntityType(EntityType<? extends EntityPrehistoric> entityType, MobType mobType, TimePeriod timePeriod, Diet diet, float eggScale){
        this.entityType = entityType;
        this.mobType = mobType;
        this.diet = diet;
        this.timePeriod = timePeriod;
    }


    public Entity create(Level level) {
        return null;
    }
}
