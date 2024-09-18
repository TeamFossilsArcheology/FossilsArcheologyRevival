package com.fossil.fossil.fabric.mixins;

import com.fossil.fossil.fabric.MultiPartEntityHitResult;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.MultiPlayerGameMode;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.HitResult;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;


@Mixin(Minecraft.class)
public class MinecraftMixin {
    //Client:
    //ProjectileUtil#getEntityHitResult(): [Vanilla: Parent][Mixin: Parent + Part]
    //Minecraft#hitResult = [Vanilla: Parent][Mixin: Parent + Part]
    //Minecraft#startAttack(hitResult): bool -> MultiPlayerGameMode#attack([Vanilla: Parent][Mixin: Part])
    //MultiPlayerGameMode#attack(Part): -> Player#attack([Vanilla: Parent][Mixin: Part][PlayerMixin: Parent])
    //PlayerMixin.part = Part
    //PlayerMixin.attack(Parent): -> [Vanilla: Parent#hurt()][PlayerMixin: Part#hurt()]

    //Server
    //MultiPlayerGameMode#attack(Part): -> ServerGamePacketListenerImpl#handleInteract([Vanilla: Parent][Mixin: Part])
    //ServerGamePacketListenerImpl#handleInteract(Part): -> Player#attack([Vanilla: Parent][Mixin: Part][PlayerMixin: Parent])
    //PlayerMixin.part = Part
    //PlayerMixin.attack(Parent): -> [Vanilla: Parent#hurt()][PlayerMixin: Part#hurt()]

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
