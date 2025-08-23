package com.github.teamfossilsarcheology.fossil.network;

import com.github.teamfossilsarcheology.fossil.entity.variant.EntityVariantLoader;
import com.github.teamfossilsarcheology.fossil.entity.variant.Variant;
import dev.architectury.networking.NetworkManager;
import dev.architectury.utils.Env;
import net.minecraft.network.FriendlyByteBuf;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class S2CSyncEntityVariantsMessage {
    private final Map<String, Map<String, Variant>> data;

    public S2CSyncEntityVariantsMessage(FriendlyByteBuf buf) {
        data = buf.readMap(HashMap::new, FriendlyByteBuf::readUtf, friendlyByteBuf -> friendlyByteBuf.readMap(HashMap::new, FriendlyByteBuf::readUtf, Variant::readBuf));
    }

    public S2CSyncEntityVariantsMessage(Map<String, Map<String, Variant>> data) {
        this.data = data;
    }

    public void write(FriendlyByteBuf buf) {
        buf.writeMap(data, FriendlyByteBuf::writeUtf, (innerBuf, map) -> innerBuf.writeMap(map, FriendlyByteBuf::writeUtf, Variant::writeBuf));
    }

    public void apply(Supplier<NetworkManager.PacketContext> contextSupplier) {
        if (contextSupplier.get().getEnvironment() == Env.SERVER) return;
        contextSupplier.get().queue(() -> EntityVariantLoader.INSTANCE.replaceVariants(data));
    }
}
