package com.fossil.fossil.network.debug;

import com.fossil.fossil.entity.prehistoric.base.PrehistoricAnimatable;
import dev.architectury.networking.NetworkManager;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.Entity;

import java.util.function.Supplier;

public class AnimationMessage {
    private final String controller;
    private final int entityId;
    private final String animation;

    public AnimationMessage(FriendlyByteBuf buf) {
        this(buf.readUtf(), buf.readInt(), buf.readUtf());
    }

    public AnimationMessage(String controller, int entityId, String animation) {
        this.controller = controller;
        this.entityId = entityId;
        this.animation = animation;
    }

    public void write(FriendlyByteBuf buf) {
        buf.writeUtf(controller);
        buf.writeInt(entityId);
        buf.writeUtf(animation);
    }

    public void apply(Supplier<NetworkManager.PacketContext> contextSupplier) {
        Entity entity = contextSupplier.get().getPlayer().level.getEntity(entityId);
        if (entity instanceof PrehistoricAnimatable prehistoric) {
            contextSupplier.get().queue(() -> prehistoric.addActiveAnimation(controller, prehistoric.getAllAnimations().get(animation)));
        }
    }
}
