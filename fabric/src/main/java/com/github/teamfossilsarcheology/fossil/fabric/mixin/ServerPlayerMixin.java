package com.github.teamfossilsarcheology.fossil.fabric.mixin;

import com.github.teamfossilsarcheology.fossil.world.effect.ComfyBedEffect;
import com.github.teamfossilsarcheology.fossil.world.effect.ModEffects;
import com.mojang.authlib.GameProfile;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerPlayer.class)
public abstract class ServerPlayerMixin extends Player {

    private ServerPlayerMixin(Level level, BlockPos pos, float yRot, GameProfile gameProfile) {
        super(level, pos, yRot, gameProfile);
    }

    @Inject(method = "stopSleepInBed", at = @At("HEAD"))
    public void applyMobEffect(boolean wakeImmediatly, boolean updateLevelForSleepingPlayers, CallbackInfo ci) {
        if (ComfyBedEffect.canApply(getSleepingPos(), level())) {
            addEffect(new MobEffectInstance(ModEffects.COMFY_BED.get(), 24000, 0));
        }
    }
}
