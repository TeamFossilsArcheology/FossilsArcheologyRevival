package com.github.teamfossilsarcheology.fossil.network.debug;

import com.github.teamfossilsarcheology.fossil.entity.prehistoric.base.PrehistoricDebug;
import com.github.teamfossilsarcheology.fossil.util.Version;
import dev.architectury.networking.NetworkManager;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.Entity;

import java.util.function.Supplier;

public class C2SDisableAIMessage {
    private final int id;
    private final boolean disableAI;
    private final byte type;

    public C2SDisableAIMessage(FriendlyByteBuf buf) {
        this(buf.readInt(), buf.readBoolean(), buf.readByte());
    }

    public C2SDisableAIMessage(int id, boolean disableAI, byte type) {
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
        contextSupplier.get().queue(() -> {
            Entity entity = contextSupplier.get().getPlayer().level.getEntity(id);
            if (entity instanceof PrehistoricDebug prehistoric && Version.debugEnabled()) {
                prehistoric.disableCustomAI(type, disableAI);
            }
        });
    }
}
