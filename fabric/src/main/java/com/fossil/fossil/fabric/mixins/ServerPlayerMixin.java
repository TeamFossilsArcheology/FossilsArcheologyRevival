package com.fossil.fossil.fabric.mixins;

import com.fossil.fossil.entity.prehistoric.parts.MultiPart;
import com.fossil.fossil.world.effect.ComfyBedEffect;
import com.fossil.fossil.world.effect.ModEffects;
import com.mojang.authlib.GameProfile;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * Fabric has no PartEntity so we mixin our own
 *
 * @see ServerLevelMixin
 */
@Mixin(ServerPlayer.class)
public abstract class ServerPlayerMixin extends Player {

    @Shadow
    private Entity camera;

    private ServerPlayerMixin(Level level, BlockPos blockPos, float f, GameProfile gameProfile) {
        super(level, blockPos, f, gameProfile);
    }

    @Shadow
    public abstract Entity getCamera();

    @Shadow
    public abstract @NotNull ServerLevel getLevel();

    @Redirect(method = "setCamera", at = @At(value = "FIELD", target = "Lnet/minecraft/server/level/ServerPlayer;camera:Lnet/minecraft/world/entity/Entity;", opcode = Opcodes.PUTFIELD))
    public void setCameraNoMultiPart(ServerPlayer instance, Entity entityToSpectate) {
        Entity entity = getCamera();
        if (entity instanceof MultiPart part) {
            camera = part.getParent();
        }
        if (camera == null) {
            camera = entityToSpectate == null ? this.getCamera() : entityToSpectate;
        }
    }

    @Inject(method = "stopSleepInBed", at = @At("HEAD"))
    public void applyMobEffect(boolean wakeImmediatly, boolean updateLevelForSleepingPlayers, CallbackInfo ci) {
        if (ComfyBedEffect.canApply(getSleepingPos(), getLevel())) {
            addEffect(new MobEffectInstance(ModEffects.COMFY_BED.get(), 24000, 0));
        }
    }
}
