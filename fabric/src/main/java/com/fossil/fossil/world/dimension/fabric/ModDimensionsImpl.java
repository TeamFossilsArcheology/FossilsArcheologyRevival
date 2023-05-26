package com.fossil.fossil.world.dimension.fabric;

import net.fabricmc.fabric.api.dimension.v1.FabricDimensions;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.portal.PortalInfo;

public class ModDimensionsImpl {
    public static void changeDimension(Entity entity, ServerLevel level, PortalInfo portalInfo) {
        FabricDimensions.teleport(entity, level, portalInfo);
    }
}
