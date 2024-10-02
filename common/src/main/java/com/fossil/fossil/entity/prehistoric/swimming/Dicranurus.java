package com.fossil.fossil.entity.prehistoric.swimming;

import com.fossil.fossil.Fossil;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;

public class Dicranurus extends Trilobite {

    public Dicranurus(EntityType<Dicranurus> entityType, Level level) {
        super(entityType, level);
        textureLocation = Fossil.location("textures/entity/dicranurus/dicranurus.png");
    }
}