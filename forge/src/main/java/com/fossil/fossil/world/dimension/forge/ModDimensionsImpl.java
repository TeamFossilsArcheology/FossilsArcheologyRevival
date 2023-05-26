package com.fossil.fossil.world.dimension.forge;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.portal.PortalInfo;
import net.minecraftforge.common.util.ITeleporter;
import org.jetbrains.annotations.Nullable;

import java.util.function.Function;

public class ModDimensionsImpl {
    public static void changeDimension(Entity entity, ServerLevel level, PortalInfo portalInfo) {
        entity.changeDimension(level, new ModTeleporter(portalInfo));
    }

    record ModTeleporter(PortalInfo portalInfo) implements ITeleporter {
        @Nullable
        @Override
        public PortalInfo getPortalInfo(Entity entity, ServerLevel destWorld, Function<ServerLevel, PortalInfo> defaultPortalInfo) {
            return portalInfo;
        }

        @Override
        public boolean isVanilla() {
            return false;
        }

        @Override
        public boolean playTeleportSound(ServerPlayer player, ServerLevel sourceWorld, ServerLevel destWorld) {
            return false;
        }

        @Override
        public Entity placeEntity(Entity entity, ServerLevel currentWorld, ServerLevel destWorld, float yaw, Function<Boolean, Entity> repositionEntity) {
            return repositionEntity.apply(false);
        }
    }
}
