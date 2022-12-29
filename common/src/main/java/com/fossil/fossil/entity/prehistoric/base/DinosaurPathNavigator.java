package com.fossil.fossil.entity.prehistoric.base;

import net.minecraft.world.entity.ai.navigation.GroundPathNavigation;
import net.minecraft.world.level.Level;

public class DinosaurPathNavigator extends GroundPathNavigation {
    public DinosaurPathNavigator(EntityPrehistoric prehistoric, Level level) {
        super(prehistoric, level);
    }
}
