package com.fossil.fossil.network.debug;

import com.fossil.fossil.entity.prehistoric.base.Prehistoric;
import com.fossil.fossil.util.Version;
import dev.architectury.networking.NetworkManager;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.Entity;

import java.util.function.Supplier;

public class C2STameMessage {
    private final int id;

    public C2STameMessage(FriendlyByteBuf buf) {
        this(buf.readInt());
    }

    public C2STameMessage(int id) {
        this.id = id;
    }

    public void write(FriendlyByteBuf buf) {
        buf.writeInt(id);
    }

    public void apply(Supplier<NetworkManager.PacketContext> contextSupplier) {
        Entity entity = contextSupplier.get().getPlayer().level.getEntity(id);
        contextSupplier.get().queue(() -> {
            if (entity instanceof Prehistoric prehistoric && Version.debugEnabled()) {
                prehistoric.tame(contextSupplier.get().getPlayer());
            }
        });
    }
}
