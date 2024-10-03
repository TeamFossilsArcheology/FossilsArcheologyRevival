package com.github.teamfossilsarcheology.fossil.network;

import com.github.teamfossilsarcheology.fossil.entity.animation.AnimationLogic;
import com.github.teamfossilsarcheology.fossil.entity.prehistoric.base.PrehistoricAnimatable;
import dev.architectury.networking.NetworkManager;
import dev.architectury.utils.Env;
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
    private final AnimationLogic.Category category;
    private final double ticks;
    private final boolean loop;

    public S2CSyncActiveAnimationMessage(FriendlyByteBuf buf) {
        this.entityId = buf.readInt();
        this.controller = buf.readUtf();
        this.animationName = buf.readUtf();
        this.category = buf.readEnum(AnimationLogic.Category.class);
        this.ticks = buf.readDouble();
        this.loop = buf.readBoolean();
    }

    public S2CSyncActiveAnimationMessage(Entity entity, String controller, AnimationLogic.ActiveAnimationInfo activeAnimationInfo) {
        this.entityId = entity.getId();
        this.controller = controller;
        this.animationName = activeAnimationInfo.animationName();
        this.category = activeAnimationInfo.category();
        this.ticks = activeAnimationInfo.transitionLength();
        this.loop = activeAnimationInfo.loop();
    }

    public void write(FriendlyByteBuf buf) {
        buf.writeInt(entityId);
        buf.writeUtf(controller);
        buf.writeUtf(animationName);
        buf.writeEnum(category);
        buf.writeDouble(ticks);
        buf.writeBoolean(loop);
    }

    public void apply(Supplier<NetworkManager.PacketContext> contextSupplier) {
        if (contextSupplier.get().getEnvironment() == Env.SERVER) return;
        contextSupplier.get().queue(() -> {
            Entity entity = contextSupplier.get().getPlayer().level.getEntity(entityId);
            if (entity instanceof PrehistoricAnimatable<?> prehistoric) {
                double endTick = entity.level.getGameTime() + prehistoric.getAllAnimations().getOrDefault(animationName, new Animation()).animationLength;
                AnimationLogic.ActiveAnimationInfo activeAnimationInfo = new AnimationLogic.ActiveAnimationInfo(
                        animationName, endTick, category, true, ticks, loop
                );
                prehistoric.getAnimationLogic().addNextAnimation(controller, activeAnimationInfo);
            }
        });
    }
}
