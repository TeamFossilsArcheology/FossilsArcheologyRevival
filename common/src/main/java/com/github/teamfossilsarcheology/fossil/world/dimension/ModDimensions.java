package com.github.teamfossilsarcheology.fossil.world.dimension;

import com.github.teamfossilsarcheology.fossil.FossilMod;
import dev.architectury.injectables.annotations.ExpectPlatform;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.dimension.BuiltinDimensionTypes;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.portal.PortalInfo;
import net.minecraft.world.phys.Vec3;
import org.apache.commons.lang3.NotImplementedException;

import java.util.OptionalLong;

public class ModDimensions {
    public static final ResourceKey<Level> TREASURE_ROOM = ResourceKey.create(Registries.DIMENSION, FossilMod.location("treasure_room"));
    public static final ResourceKey<Level> ANU_LAIR = ResourceKey.create(Registries.DIMENSION, FossilMod.location("anu_lair"));
    public static final ResourceKey<DimensionType> TREASURE_ROOM_TYPE = ResourceKey.create(Registries.DIMENSION_TYPE, TREASURE_ROOM.location());
    public static final ResourceKey<DimensionType> ANU_LAIR_TYPE = ResourceKey.create(Registries.DIMENSION_TYPE, ANU_LAIR.location());
    public static final DimensionType CUSTOM = new DimensionType(
            OptionalLong.of(6000), // fixed_time
            false, // has_skylight
            false, // has_ceiling
            true, // ultrawarm
            false, // natural
            1, // coordinate_scale
            false, // bed_works
            false, // respawn_anchor_works
            0, // min_y
            256, // height
            256, // logical_height
            BlockTags.INFINIBURN_OVERWORLD, // infiniburn
            BuiltinDimensionTypes.END_EFFECTS, // effects
            0, // ambient_light
            new DimensionType.MonsterSettings(true, false, ConstantInt.of(0), 0));

    public static void register() {
    }

    public static void bootstrap(BootstapContext<DimensionType> context) {
        context.register(ANU_LAIR_TYPE, CUSTOM);
        context.register(TREASURE_ROOM_TYPE, CUSTOM);
    }

    /**
     * Teleports the entity to the anu dimension at a fixed point and places an obsidian platform below the entity
     *
     * @param server
     * @param entity
     */
    public static void teleportToAnuLair(MinecraftServer server, Entity entity) {
        ServerLevel anuLair = server.getLevel(ModDimensions.ANU_LAIR);
        Vec3 spawnPoint = new Vec3(70.5, 63, -17.5);
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
