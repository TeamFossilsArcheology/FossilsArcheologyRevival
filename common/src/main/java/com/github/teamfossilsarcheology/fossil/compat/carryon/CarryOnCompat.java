package com.github.teamfossilsarcheology.fossil.compat.carryon;

import com.github.teamfossilsarcheology.fossil.entity.prehistoric.base.Prehistoric;
import com.llamalad7.mixinextras.sugar.ref.LocalRef;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;

import java.util.UUID;

public class CarryOnCompat {

    public static void overridePickUp(LocalRef<UUID> owner, Entity entity, ServerPlayer player) {
        if (entity instanceof Prehistoric) {
            boolean isOperator = player.level().getServer().getProfilePermissions(player.getGameProfile()) == player.level().getServer().getOperatorUserPermissionLevel();
            if (player.isCreative() || isOperator) {
                owner.set(null);
            }
        }
    }
}
