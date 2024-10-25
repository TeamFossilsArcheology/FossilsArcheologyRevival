package com.github.teamfossilsarcheology.fossil.entity.prehistoric.swimming;

import com.github.teamfossilsarcheology.fossil.FossilMod;
import com.github.teamfossilsarcheology.fossil.entity.prehistoric.base.PrehistoricEntityInfo;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;

public class Scotoharpes extends Trilobite {

    public Scotoharpes(EntityType<Scotoharpes> entityType, Level level) {
        super(entityType, level);
        textureLocation = FossilMod.location("textures/entity/scotoharpes/scotoharpes.png");
    }

    @Override
    public PrehistoricEntityInfo info() {
        return PrehistoricEntityInfo.SCOTOHARPES;
    }
}