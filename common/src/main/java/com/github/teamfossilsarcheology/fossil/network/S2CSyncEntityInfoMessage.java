package com.github.teamfossilsarcheology.fossil.network;

import com.github.teamfossilsarcheology.fossil.entity.data.EntityDataLoader;
import dev.architectury.networking.NetworkChannel;
import dev.architectury.networking.NetworkManager;
import dev.architectury.utils.Env;
import net.minecraft.network.FriendlyByteBuf;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

/**
 * Sync {@link com.github.teamfossilsarcheology.fossil.entity.data.EntityDataLoader.Data entity data} on server join to client
 */
public class S2CSyncEntityInfoMessage {
    private final Map<String, EntityDataLoader.Data> data;

    private S2CSyncEntityInfoMessage(FriendlyByteBuf buf) {
        data = buf.readMap(HashMap::new, FriendlyByteBuf::readUtf, EntityDataLoader.Data::readBuf);
    }

    public S2CSyncEntityInfoMessage(Map<String, EntityDataLoader.Data> data) {
        this.data = data;
    }

    private void write(FriendlyByteBuf buf) {
        buf.writeMap(data, FriendlyByteBuf::writeUtf, EntityDataLoader.Data::writeBuf);
    }

    private void apply(Supplier<NetworkManager.PacketContext> contextSupplier) {
        if (contextSupplier.get().getEnvironment() == Env.SERVER) return;
        contextSupplier.get().queue(() -> EntityDataLoader.INSTANCE.replaceData(data));
    }

    public static void register(NetworkChannel channel) {
        channel.register(S2CSyncEntityInfoMessage.class, S2CSyncEntityInfoMessage::write, S2CSyncEntityInfoMessage::new, S2CSyncEntityInfoMessage::apply);
    }
}
