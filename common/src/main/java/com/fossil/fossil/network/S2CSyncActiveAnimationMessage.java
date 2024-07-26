package com.fossil.fossil.network;

import com.fossil.fossil.entity.animation.AnimationLogic;
import com.fossil.fossil.entity.prehistoric.base.PrehistoricAnimatable;
import dev.architectury.networking.NetworkManager;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.Entity;
import software.bernie.geckolib3.core.builder.Animation;

import java.util.function.Supplier;

/**
 * Sync active animation from server to clients
 */
public class S2CSyncActiveAnimationMessage {
    private final int entityId;
    private final String controller;
    private final String animationName;
    private final double startTick;
    private final AnimationLogic.Category category;
    private final double ticks;

    public S2CSyncActiveAnimationMessage(FriendlyByteBuf buf) {
        this.entityId = buf.readInt();
        this.controller = buf.readUtf();
        this.animationName = buf.readUtf();
        this.startTick = buf.readDouble();
        this.category = buf.readEnum(AnimationLogic.Category.class);
        this.ticks = buf.readDouble();
    }

    public S2CSyncActiveAnimationMessage(Entity entity, String controller, AnimationLogic.ActiveAnimationInfo activeAnimationInfo) {
        this.entityId = entity.getId();
        this.controller = controller;
        this.animationName = activeAnimationInfo.animationName();
        this.startTick = activeAnimationInfo.startTick();
        this.category = activeAnimationInfo.category();
        this.ticks = activeAnimationInfo.speed();
    }

    public void write(FriendlyByteBuf buf) {
        buf.writeInt(entityId);
        buf.writeUtf(controller);
        buf.writeUtf(animationName);
        buf.writeDouble(startTick);
        buf.writeEnum(category);
        buf.writeDouble(ticks);
    }

    public void apply(Supplier<NetworkManager.PacketContext> contextSupplier) {
        Entity entity = contextSupplier.get().getPlayer().level.getEntity(entityId);
        if (entity instanceof PrehistoricAnimatable<?> prehistoric) {
            contextSupplier.get().queue(() -> {
                double endTick = entity.level.getGameTime() + prehistoric.getAllAnimations().getOrDefault(animationName, new Animation()).animationLength;
                AnimationLogic.ActiveAnimationInfo activeAnimationInfo = new AnimationLogic.ActiveAnimationInfo(
                        animationName, startTick, endTick, category, true, ticks
                );
                prehistoric.getAnimationLogic().addNextAnimation(controller, activeAnimationInfo);
            });
        }
    }
}
