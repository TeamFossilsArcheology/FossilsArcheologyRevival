package com.github.teamfossilsarcheology.fossil.fabric.mixin;

import com.github.teamfossilsarcheology.fossil.compat.carryon.CarryOnCompat;
import com.llamalad7.mixinextras.sugar.Local;
import com.llamalad7.mixinextras.sugar.ref.LocalRef;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import tschipp.carryon.common.carry.PickupHandler;

import java.util.UUID;
import java.util.function.Function;

@Mixin(PickupHandler.class)
public abstract class PickupHandlerMixin {
    @Inject(method = "tryPickupEntity", require = 0, at = @At(value = "INVOKE", target = "Lnet/minecraft/server/level/ServerPlayer;getGameProfile()Lcom/mojang/authlib/GameProfile;", ordinal = 0))
    private static void overridePickUp(ServerPlayer player, Entity entity, Function<Entity, Boolean> pickupCallback, CallbackInfoReturnable<Boolean> cir, @Local(name = "owner") LocalRef<UUID> owner) {
        CarryOnCompat.overridePickUp(owner, entity, player);
    }
}
