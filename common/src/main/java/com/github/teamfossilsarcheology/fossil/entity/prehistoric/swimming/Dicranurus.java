package com.github.teamfossilsarcheology.fossil.entity.prehistoric.swimming;

import com.github.teamfossilsarcheology.fossil.Fossil;
import com.github.teamfossilsarcheology.fossil.entity.prehistoric.base.PrehistoricEntityInfo;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;

public class Dicranurus extends Trilobite {

    public Dicranurus(EntityType<Dicranurus> entityType, Level level) {
        super(entityType, level);
        textureLocation = Fossil.location("textures/entity/dicranurus/dicranurus.png");
    }

    @Override
    public PrehistoricEntityInfo info() {
        return PrehistoricEntityInfo.DICRANURUS;
    }
}