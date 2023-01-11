package com.fossil.fossil.entity.prehistoric.base;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;

public interface Mammal {
    Entity createChild(ServerLevel level);
}
