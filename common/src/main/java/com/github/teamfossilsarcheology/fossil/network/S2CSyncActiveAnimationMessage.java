package com.github.teamfossilsarcheology.fossil.network;

import com.github.teamfossilsarcheology.fossil.entity.animation.AnimationCategory;
import com.github.teamfossilsarcheology.fossil.entity.animation.AnimationLogic;
import com.github.teamfossilsarcheology.fossil.entity.prehistoric.base.PrehistoricAnimatable;
import dev.architectury.networking.NetworkManager;
import dev.architectury.utils.Env;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.Entity;

import java.util.function.Supplier;

/**
 * Sync active animation from server to clients
 */
public class S2CSyncActiveAnimationMessage {
    private final int entityId;
    private final String controller;
    private final String animationName;
    private final AnimationCategory category;
    private final double ticks;
    private final double speed;
    private final boolean loop;

    public S2CSyncActiveAnimationMessage(FriendlyByteBuf buf) {
        this.entityId = buf.readInt();
        this.controller = buf.readUtf();
        this.animationName = buf.readUtf();
        String name = buf.readUtf();
        this.category = AnimationCategory.CATEGORIES.stream().filter(category1 -> category1.name().equals(name)).findFirst().orElse(AnimationCategory.NONE);
        this.ticks = buf.readDouble();
        this.speed = buf.readDouble();
        this.loop = buf.readBoolean();
    }

    public S2CSyncActiveAnimationMessage(Entity entity, String controller, AnimationLogic.ActiveAnimationInfo activeAnimationInfo) {
        this.entityId = entity.getId();
        this.controller = controller;
        this.animationName = activeAnimationInfo.animationName();
        this.category = activeAnimationInfo.category();
        this.ticks = activeAnimationInfo.transitionLength();
        this.speed = activeAnimationInfo.speed();
        this.loop = activeAnimationInfo.loop();
    }

    public void write(FriendlyByteBuf buf) {
        buf.writeInt(entityId);
        buf.writeUtf(controller);
        buf.writeUtf(animationName);
        buf.writeUtf(category.name());
        buf.writeDouble(ticks);
        buf.writeDouble(speed);
        buf.writeBoolean(loop);
    }

    public void apply(Supplier<NetworkManager.PacketContext> contextSupplier) {
        if (contextSupplier.get().getEnvironment() == Env.SERVER) return;
        contextSupplier.get().queue(() -> {
            Entity entity = contextSupplier.get().getPlayer().level.getEntity(entityId);
            if (entity instanceof PrehistoricAnimatable<?> prehistoric) {
                if (prehistoric.getAllAnimations().containsKey(animationName)) {
                    double endTick = entity.level.getGameTime() + prehistoric.getAnimation(animationName).animation.animationLength;
                    AnimationLogic.ActiveAnimationInfo activeAnimationInfo = new AnimationLogic.ActiveAnimationInfo(
                            animationName, endTick, category, true, ticks, speed, loop
                    );
                    prehistoric.getAnimationLogic().addNextAnimation(controller, activeAnimationInfo);
                }
            }
        });
    }
}
