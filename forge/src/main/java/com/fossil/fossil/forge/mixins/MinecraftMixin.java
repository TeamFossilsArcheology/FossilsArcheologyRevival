package com.fossil.fossil.forge.mixins;

import com.fossil.fossil.forge.MultiPartEntityHitResult;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.MultiPlayerGameMode;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.HitResult;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import javax.annotation.Nullable;

@Mixin(Minecraft.class)
public class MinecraftMixin {

    @Shadow
    @Nullable
    public HitResult hitResult;

    @Redirect(method = "startAttack", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/multiplayer/MultiPlayerGameMode;attack(Lnet/minecraft/world/entity/player/Player;Lnet/minecraft/world/entity/Entity;)V"))
    private void modifyPartEntity(MultiPlayerGameMode gameMode, Player player, Entity targetEntity) {
        if (hitResult instanceof MultiPartEntityHitResult entityHitResult && entityHitResult.fossilsArcheologyRevival$getMultiPart() != null) {
            gameMode.attack(player, entityHitResult.fossilsArcheologyRevival$getMultiPart().getEntity());
        } else {
            gameMode.attack(player, targetEntity);
        }
    }

}
