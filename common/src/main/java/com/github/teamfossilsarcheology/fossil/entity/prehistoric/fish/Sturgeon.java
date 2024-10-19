package com.github.teamfossilsarcheology.fossil.entity.prehistoric.fish;

import com.github.teamfossilsarcheology.fossil.entity.prehistoric.base.PrehistoricEntityInfo;
import com.github.teamfossilsarcheology.fossil.entity.prehistoric.base.PrehistoricFish;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public class Sturgeon extends PrehistoricFish {

    public Sturgeon(EntityType<Sturgeon> entityType, Level level) {
        super(entityType, level);
    }

    @Override
    public @NotNull PrehistoricEntityInfo info() {
        return PrehistoricEntityInfo.STURGEON;
    }
}
