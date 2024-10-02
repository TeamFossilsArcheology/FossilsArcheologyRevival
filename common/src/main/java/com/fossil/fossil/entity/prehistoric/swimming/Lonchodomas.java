package com.fossil.fossil.entity.prehistoric.swimming;

import com.fossil.fossil.Fossil;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;

public class Lonchodomas extends Trilobite {

    public Lonchodomas(EntityType<Lonchodomas> entityType, Level level) {
        super(entityType, level);
        textureLocation = Fossil.location("textures/entity/lonchodomas/lonchodomas.png");
    }
}