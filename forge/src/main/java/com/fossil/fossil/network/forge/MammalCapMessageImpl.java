package com.fossil.fossil.network.forge;

import com.fossil.fossil.capabilities.forge.ModCapabilitiesImpl;
import com.fossil.fossil.entity.prehistoric.base.PrehistoricEntityType;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.level.Level;

public class MammalCapMessageImpl {
    public static void applyCap(Level level, int entityId, int embryoProgress, PrehistoricEntityType embryo) {
        Entity entity = level.getEntity(entityId);
        if (entity instanceof Animal animal) {
            ModCapabilitiesImpl.setEmbryoProgress(animal, embryoProgress);
            ModCapabilitiesImpl.setEmbryo(animal, embryo);
        }
    }
}
