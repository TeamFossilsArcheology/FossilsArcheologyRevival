package com.github.teamfossilsarcheology.fossil.network.debug;

import com.github.teamfossilsarcheology.fossil.entity.animation.AnimationCategory;
import com.github.teamfossilsarcheology.fossil.entity.prehistoric.base.PrehistoricAnimatable;
import com.github.teamfossilsarcheology.fossil.util.Version;
import dev.architectury.networking.NetworkManager;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.Entity;

import java.util.function.Supplier;

public class C2SForceAnimationMessage {
    private final String controller;
    private final int entityId;
    private final String animation;
    private final double speed;
    private final double transitionLength;
    private final boolean loop;

    public C2SForceAnimationMessage(FriendlyByteBuf buf) {
        this(buf.readUtf(), buf.readInt(), buf.readUtf(), buf.readDouble(), buf.readDouble(), buf.readBoolean());
    }

    public C2SForceAnimationMessage(String controller, int entityId, String animation, double speed, double transitionLength, boolean loop) {
        this.controller = controller;
        this.entityId = entityId;
        this.animation = animation;
        this.speed = speed;
        this.transitionLength = transitionLength;
        this.loop = loop;
    }

    public void write(FriendlyByteBuf buf) {
        buf.writeUtf(controller);
        buf.writeInt(entityId);
        buf.writeUtf(animation);
        buf.writeDouble(speed);
        buf.writeDouble(transitionLength);
        buf.writeBoolean(loop);
    }

    public void apply(Supplier<NetworkManager.PacketContext> contextSupplier) {
        contextSupplier.get().queue(() -> {
            Entity entity = contextSupplier.get().getPlayer().level.getEntity(entityId);
            if (entity instanceof PrehistoricAnimatable<?> animatable && Version.debugEnabled()) {
                animatable.getAnimationLogic().forceAnimation(controller, animatable.getAllAnimations().get(animation), AnimationCategory.IDLE, speed, transitionLength, loop);
            }
        });
    }
}
