package com.github.teamfossilsarcheology.fossil.network.debug;

import com.github.teamfossilsarcheology.fossil.entity.prehistoric.base.PrehistoricAnimatable;
import com.github.teamfossilsarcheology.fossil.util.Version;
import dev.architectury.networking.NetworkChannel;
import dev.architectury.networking.NetworkManager;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.Entity;

import java.util.function.Supplier;

public class S2CCancelAnimationMessage {
    private final int entityId;
    private final String controller;

    private S2CCancelAnimationMessage(FriendlyByteBuf buf) {
        this(buf.readInt(), buf.readUtf());
    }

    public S2CCancelAnimationMessage(int entityId, String controller) {
        this.entityId = entityId;
        this.controller = controller;
    }

    private void write(FriendlyByteBuf buf) {
        buf.writeInt(entityId);
        buf.writeUtf(controller);
    }

    private void apply(Supplier<NetworkManager.PacketContext> contextSupplier) {
        contextSupplier.get().queue(() -> {
            Entity entity = contextSupplier.get().getPlayer().level().getEntity(entityId);
            if (entity instanceof PrehistoricAnimatable<?> prehistoric && Version.debugEnabled()) {
                prehistoric.getAnimationLogic().cancelAnimation(controller);
            }
        });
    }

    public static void register(NetworkChannel channel) {
        channel.register(S2CCancelAnimationMessage.class, S2CCancelAnimationMessage::write, S2CCancelAnimationMessage::new, S2CCancelAnimationMessage::apply);
    }
}
