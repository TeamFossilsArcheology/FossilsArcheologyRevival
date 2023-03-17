package com.fossil.fossil.network;

import com.fossil.fossil.entity.prehistoric.base.Prehistoric;
import dev.architectury.networking.NetworkManager;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.Entity;

import java.util.function.Supplier;

public class AnimationMessage {
    private final int id;
    private final String animation;

    public AnimationMessage(FriendlyByteBuf buf) {
        this(buf.readInt(), buf.readUtf());
    }

    public AnimationMessage(int id, String animation) {
        this.id = id;
        this.animation = animation;
    }

    public void write(FriendlyByteBuf buf) {
        buf.writeInt(id);
        buf.writeUtf(animation);
    }

    public void apply(Supplier<NetworkManager.PacketContext> contextSupplier) {
        Entity entity = contextSupplier.get().getPlayer().level.getEntity(id);
        if (entity instanceof Prehistoric prehistoric) {
            contextSupplier.get().queue(() -> {
                prehistoric.setCurrentAnimation(prehistoric.getAllAnimations().get(animation));
            });
        }
    }
}
