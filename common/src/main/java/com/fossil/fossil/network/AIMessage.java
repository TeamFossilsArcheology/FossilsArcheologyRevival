package com.fossil.fossil.network;

import com.fossil.fossil.entity.prehistoric.base.Prehistoric;
import dev.architectury.networking.NetworkManager;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.Entity;

import java.util.function.Supplier;

public class AIMessage {
    private final int id;
    private final boolean disableAI;
    private final byte type;

    public AIMessage(FriendlyByteBuf buf) {
        this(buf.readInt(), buf.readBoolean(), buf.readByte());
    }

    public AIMessage(int id, boolean disableAI, byte type) {
        this.id = id;
        this.disableAI = disableAI;
        this.type = type;
    }

    public void write(FriendlyByteBuf buf) {
        buf.writeInt(id);
        buf.writeBoolean(disableAI);
        buf.writeByte(type);
    }

    public void apply(Supplier<NetworkManager.PacketContext> contextSupplier) {
        Entity entity = contextSupplier.get().getPlayer().level.getEntity(id);
        contextSupplier.get().queue(() -> {
            if (entity instanceof Prehistoric prehistoric) {
                prehistoric.disableCustomAI(type, disableAI);
            }
        });
    }
}
