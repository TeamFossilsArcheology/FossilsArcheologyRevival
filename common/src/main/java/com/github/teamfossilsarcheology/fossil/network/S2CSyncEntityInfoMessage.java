package com.github.teamfossilsarcheology.fossil.network;

import com.github.teamfossilsarcheology.fossil.entity.data.EntityDataLoader;
import dev.architectury.networking.NetworkManager;
import dev.architectury.utils.Env;
import net.minecraft.network.FriendlyByteBuf;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class S2CSyncEntityInfoMessage {
    private final Map<String, EntityDataLoader.Data> data;

    public S2CSyncEntityInfoMessage(FriendlyByteBuf buf) {
        data = buf.readMap(HashMap::new, FriendlyByteBuf::readUtf, EntityDataLoader.Data::readBuf);
    }

    public S2CSyncEntityInfoMessage(Map<String, EntityDataLoader.Data> data) {
        this.data = data;
    }

    public void write(FriendlyByteBuf buf) {
        buf.writeMap(data, (buffer, key) -> buf.writeUtf(key), EntityDataLoader.Data::writeBuf);
    }

    public void apply(Supplier<NetworkManager.PacketContext> contextSupplier) {
        if (contextSupplier.get().getEnvironment() == Env.SERVER) return;
        contextSupplier.get().queue(() -> EntityDataLoader.ENTITY_DATA.replaceData(data));
    }
}
