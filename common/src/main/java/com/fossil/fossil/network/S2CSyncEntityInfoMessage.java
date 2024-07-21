package com.fossil.fossil.network;

import com.fossil.fossil.entity.data.EntityDataManager;
import dev.architectury.networking.NetworkManager;
import net.minecraft.network.FriendlyByteBuf;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class S2CSyncEntityInfoMessage {
    private final Map<String, EntityDataManager.Data> data;

    public S2CSyncEntityInfoMessage(FriendlyByteBuf buf) {
        data = buf.readMap(HashMap::new, FriendlyByteBuf::readUtf, EntityDataManager.Data::readBuf);
    }

    public S2CSyncEntityInfoMessage(Map<String, EntityDataManager.Data> data) {
        this.data = data;
    }

    public void write(FriendlyByteBuf buf) {
        buf.writeMap(data, (buffer, key) -> buf.writeUtf(key), EntityDataManager.Data::writeBuf);
    }

    public void apply(Supplier<NetworkManager.PacketContext> contextSupplier) {
        contextSupplier.get().queue(() -> EntityDataManager.ENTITY_DATA.replaceData(data));
    }
}
