package com.fossil.fossil.world.dimension;

import com.fossil.fossil.Fossil;
import dev.architectury.injectables.annotations.ExpectPlatform;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.portal.PortalInfo;
import org.apache.commons.lang3.NotImplementedException;

public class ModDimensions {
    public static final ResourceKey<Level> TREASURE_ROOM = ResourceKey.create(Registry.DIMENSION_REGISTRY, new ResourceLocation(Fossil.MOD_ID, "treasure_room"));
    public static final ResourceKey<Level> ANU_LAIR = ResourceKey.create(Registry.DIMENSION_REGISTRY, new ResourceLocation(Fossil.MOD_ID, "anu_lair"));
    public static final ResourceKey<DimensionType> TREASURE_ROOM_TYPE = ResourceKey.create(Registry.DIMENSION_TYPE_REGISTRY, TREASURE_ROOM.registry());
    public static final ResourceKey<DimensionType> ANU_LAIR_TYPE = ResourceKey.create(Registry.DIMENSION_TYPE_REGISTRY, ANU_LAIR.registry());

    public static void register() {
    }

    @ExpectPlatform
    public static void changeDimension(Entity entity, ServerLevel level, PortalInfo portalInfo) {
        throw new NotImplementedException();
    }
}
