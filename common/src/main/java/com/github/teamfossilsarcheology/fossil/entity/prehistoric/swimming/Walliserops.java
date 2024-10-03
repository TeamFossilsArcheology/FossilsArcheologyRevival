package com.github.teamfossilsarcheology.fossil.entity.prehistoric.swimming;

import com.github.teamfossilsarcheology.fossil.Fossil;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;

public class Walliserops extends Trilobite {

    public Walliserops(EntityType<Walliserops> entityType, Level level) {
        super(entityType, level);
        textureLocation = Fossil.location("textures/entity/walliserops/walliserops.png");
    }
}