package com.fossil.fossil.network.debug;

import com.fossil.fossil.entity.prehistoric.base.PrehistoricAnimatable;
import com.fossil.fossil.util.Version;
import dev.architectury.networking.NetworkManager;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.Entity;

import java.util.function.Supplier;

public class ForceAnimationMessage {
    private final String controller;
    private final int entityId;
    private final String animation;
    private final double speed;

    public ForceAnimationMessage(FriendlyByteBuf buf) {
        this(buf.readUtf(), buf.readInt(), buf.readUtf(), buf.readDouble());
    }

    public ForceAnimationMessage(String controller, int entityId, String animation, double speed) {
        this.controller = controller;
        this.entityId = entityId;
        this.animation = animation;
        this.speed = speed;
    }

    public void write(FriendlyByteBuf buf) {
        buf.writeUtf(controller);
        buf.writeInt(entityId);
        buf.writeUtf(animation);
        buf.writeDouble(speed);
    }

    public void apply(Supplier<NetworkManager.PacketContext> contextSupplier) {
        Entity entity = contextSupplier.get().getPlayer().level.getEntity(entityId);
        if (entity instanceof PrehistoricAnimatable<?> animatable && Version.debugEnabled()) {
            contextSupplier.get().queue(() -> {
                animatable.getAnimationLogic().forceActiveAnimation(controller, animatable.getAllAnimations().get(animation), "Idle", speed);
            });
        }
    }
}
