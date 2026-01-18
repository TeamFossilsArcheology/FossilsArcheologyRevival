package com.github.teamfossilsarcheology.fossil.network.debug;

import com.github.teamfossilsarcheology.fossil.entity.animation.AnimationCategory;
import com.github.teamfossilsarcheology.fossil.entity.prehistoric.base.PrehistoricAnimatable;
import com.github.teamfossilsarcheology.fossil.util.Version;
import dev.architectury.networking.NetworkChannel;
import dev.architectury.networking.NetworkManager;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.Entity;

import java.util.function.Supplier;

public class C2SForceAnimationMessage {
    private final String controller;
    private final int entityId;
    private final String animation;
    private final double speed;
    private final int transitionLength;
    private final boolean loop;

    private C2SForceAnimationMessage(FriendlyByteBuf buf) {
        this(buf.readUtf(), buf.readInt(), buf.readUtf(), buf.readDouble(), buf.readInt(), buf.readBoolean());
    }

    public C2SForceAnimationMessage(String controller, int entityId, String animation, double speed, int transitionLength, boolean loop) {
        this.controller = controller;
        this.entityId = entityId;
        this.animation = animation;
        this.speed = speed;
        this.transitionLength = transitionLength;
        this.loop = loop;
    }

    private void write(FriendlyByteBuf buf) {
        buf.writeUtf(controller);
        buf.writeInt(entityId);
        buf.writeUtf(animation);
        buf.writeDouble(speed);
        buf.writeInt(transitionLength);
        buf.writeBoolean(loop);
    }

    private void apply(Supplier<NetworkManager.PacketContext> contextSupplier) {
        contextSupplier.get().queue(() -> {
            Entity entity = contextSupplier.get().getPlayer().level().getEntity(entityId);
            if (entity instanceof PrehistoricAnimatable<?> animatable && Version.debugEnabled()) {
                animatable.getAnimationLogic().forceAnimation(controller, animatable.getAllAnimations().get(animation), AnimationCategory.IDLE, speed, transitionLength, loop);
            }
        });
    }

    public static void register(NetworkChannel channel) {
        channel.register(C2SForceAnimationMessage.class, C2SForceAnimationMessage::write, C2SForceAnimationMessage::new, C2SForceAnimationMessage::apply);
    }
}
