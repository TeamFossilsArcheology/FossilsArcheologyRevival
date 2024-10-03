package com.github.teamfossilsarcheology.fossil.entity.prehistoric.swimming;

import com.github.teamfossilsarcheology.fossil.Fossil;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;

public class Scotoharpes extends Trilobite {

    public Scotoharpes(EntityType<Scotoharpes> entityType, Level level) {
        super(entityType, level);
        textureLocation = Fossil.location("textures/entity/scotoharpes/scotoharpes.png");
    }
}