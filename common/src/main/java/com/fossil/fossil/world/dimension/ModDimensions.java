package com.fossil.fossil.world.dimension;

import com.fossil.fossil.Fossil;
import dev.architectury.injectables.annotations.ExpectPlatform;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.portal.PortalInfo;
import net.minecraft.world.phys.Vec3;
import org.apache.commons.lang3.NotImplementedException;

public class ModDimensions {
    public static final ResourceKey<Level> TREASURE_ROOM = ResourceKey.create(Registry.DIMENSION_REGISTRY, Fossil.location("treasure_room"));
    public static final ResourceKey<Level> ANU_LAIR = ResourceKey.create(Registry.DIMENSION_REGISTRY, Fossil.location("anu_lair"));
    public static final ResourceKey<DimensionType> TREASURE_ROOM_TYPE = ResourceKey.create(Registry.DIMENSION_TYPE_REGISTRY, TREASURE_ROOM.registry());
    public static final ResourceKey<DimensionType> ANU_LAIR_TYPE = ResourceKey.create(Registry.DIMENSION_TYPE_REGISTRY, ANU_LAIR.registry());

    public static void register() {
    }

    /**
     * Teleports the entity to the anu dimension at a fixed point and places an obsidian platform below the entity
     *
     * @param server
     * @param entity
     */
    public static void teleportToAnuLair(MinecraftServer server, Entity entity) {
        ServerLevel anuLair = server.getLevel(ModDimensions.ANU_LAIR);
        Vec3 spawnPoint = new Vec3(70, 63, -17);
        int x = (int) spawnPoint.x;
        int y = (int) (spawnPoint.y - 2);
        int z = (int) spawnPoint.z;
        BlockPos.betweenClosed(x - 2, y + 1, z - 2, x + 2, y + 3, z + 2).forEach(blockPos -> anuLair.setBlockAndUpdate(blockPos, Blocks.AIR.defaultBlockState()));
        BlockPos.betweenClosed(x - 2, y, z - 2, x + 2, y, z + 2).forEach(blockPos -> anuLair.setBlockAndUpdate(blockPos, Blocks.OBSIDIAN.defaultBlockState()));
        changeDimension(entity, anuLair, new PortalInfo(spawnPoint, entity.getDeltaMovement(), 0, 90));
    }

    @ExpectPlatform
    public static void changeDimension(Entity entity, ServerLevel level, PortalInfo portalInfo) {
        throw new NotImplementedException();
    }
}
