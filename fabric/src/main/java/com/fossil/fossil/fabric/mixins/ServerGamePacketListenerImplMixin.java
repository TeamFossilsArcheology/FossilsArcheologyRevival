package com.fossil.fossil.fabric.mixins;

import com.fossil.fossil.entity.prehistoric.base.Prehistoric;
import com.fossil.fossil.entity.prehistoric.parts.MultiPart;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.network.protocol.game.ServerboundInteractPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.network.ServerGamePacketListenerImpl;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.util.Optional;

@Mixin(ServerGamePacketListenerImpl.class)
public class ServerGamePacketListenerImplMixin {

    @Shadow
    public ServerPlayer player;

    @Inject(method = "handleInteract", locals = LocalCapture.CAPTURE_FAILHARD, at = @At(value = "INVOKE", target = "Lnet/minecraft/server/level/ServerPlayer;distanceToSqr(Lnet/minecraft/world/entity/Entity;)D"))
    private void replaceHurtEntity(ServerboundInteractPacket packet, CallbackInfo ci, ServerLevel serverLevel, Entity target) {
        if (!(target instanceof Prehistoric) && !(target instanceof MultiPart)) {
            return;
        }
        //Copy forges extended attack and interact range for larger entities
        if (player.distanceToSqr(target) >= 36) {
            packet.dispatch(new ServerboundInteractPacket.Handler() {
                private void performInteraction(InteractionHand hand, ServerGamePacketListenerImpl.EntityInteraction interaction) {
                    if (!isCloseEnough(target, player.isCreative() ? 6.5 : 6)) {
                        return;
                    }
                    ItemStack itemstack = player.getItemInHand(hand).copy();
                    InteractionResult interactionresult = interaction.run(player, target, hand);
                    if (interactionresult.consumesAction()) {
                        CriteriaTriggers.PLAYER_INTERACTED_WITH_ENTITY.trigger(player, itemstack, target);
                        if (interactionresult.shouldSwing()) {
                            player.swing(hand, true);
                        }
                    }
                }

                @Override
                public void onInteraction(InteractionHand hand) {
                    performInteraction(hand, Player::interactOn);
                }

                @Override
                public void onInteraction(InteractionHand hand, Vec3 interactionLocation) {
                    this.performInteraction(hand, (player1, target, hand1) -> target.interactAt(player1, interactionLocation, hand1));
                }

                @Override
                public void onAttack() {
                    if (isCloseEnough(target, player.isCreative() ? 9 : 6)) {
                        player.attack(target);
                    }
                }
            });
        }
    }

    @Unique
    private boolean isCloseEnough(Entity entity, double dist) {
        Vec3 eye = player.getEyePosition();
        Vec3 targetCenter = entity.getPosition(1).add(0, entity.getBbHeight() / 2, 0);
        Optional<Vec3> hit = entity.getBoundingBox().clip(eye, targetCenter);
        return (hit.isPresent() ? eye.distanceToSqr(hit.get()) : player.distanceToSqr(entity)) < dist * dist;
    }
}
