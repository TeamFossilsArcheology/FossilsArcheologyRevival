package com.fossil.fossil.network;

import com.fossil.fossil.entity.data.EntityDataManager;
import dev.architectury.networking.NetworkManager;
import net.minecraft.network.FriendlyByteBuf;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class SyncEntityInfoMessage {
    private final Map<String, EntityDataManager.Data> data;

    public SyncEntityInfoMessage(FriendlyByteBuf buf) {
        data = buf.readMap(HashMap::new, FriendlyByteBuf::readUtf, EntityDataManager.Data::readBuf);
    }

    public SyncEntityInfoMessage(Map<String, EntityDataManager.Data> data) {
        this.data = data;
    }

    public void write(FriendlyByteBuf buf) {
        buf.writeMap(data, (buffer, key) -> buf.writeUtf(key), EntityDataManager.Data::writeBuf);
    }

    public void apply(Supplier<NetworkManager.PacketContext> contextSupplier) {
        contextSupplier.get().queue(() -> EntityDataManager.ENTITY_DATA.replaceData(data));
    }
}
