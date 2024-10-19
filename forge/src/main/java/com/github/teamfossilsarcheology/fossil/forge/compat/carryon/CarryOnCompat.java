package com.github.teamfossilsarcheology.fossil.forge.compat.carryon;


import com.github.teamfossilsarcheology.fossil.entity.prehistoric.base.Prehistoric;
import net.minecraft.server.level.ServerLevel;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import tschipp.carryon.common.handler.PickupHandler;

public class CarryOnCompat {

    @SubscribeEvent
    public static void addCustomTrades(PickupHandler.PickUpEntityEvent event) {
        if (event.target instanceof Prehistoric prehistoric && event.player.level instanceof ServerLevel serverLevel) {
            boolean isOperator = serverLevel.getServer().getProfilePermissions(event.player.getGameProfile()) == serverLevel.getServer().getOperatorUserPermissionLevel();
            if (!event.player.isCreative() && !isOperator && !event.player.getUUID().equals(prehistoric.getOwnerUUID())) {
                event.setCanceled(true);
            }
        }
    }
}
