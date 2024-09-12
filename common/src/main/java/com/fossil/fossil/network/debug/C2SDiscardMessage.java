package com.fossil.fossil.network.debug;

import dev.architectury.networking.NetworkManager;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;

import java.util.function.Supplier;
import java.util.stream.StreamSupport;

public class C2SDiscardMessage {
    private final int entityId;

    public C2SDiscardMessage(FriendlyByteBuf buf) {
        this(buf.readInt());
    }

    public C2SDiscardMessage(int entityId) {
        this.entityId = entityId;
    }

    public void write(FriendlyByteBuf buf) {
        buf.writeInt(entityId);
    }

    public void apply(Supplier<NetworkManager.PacketContext> contextSupplier) {
        contextSupplier.get().queue(() -> {
            Player player = contextSupplier.get().getPlayer();
            if (player.level instanceof ServerLevel serverLevel) {
                if (entityId == -1) {
                    var list = StreamSupport.stream(serverLevel.getAllEntities().spliterator(), false)
                            .filter(entity -> !(entity instanceof Player)).toList();
                    list.forEach(Entity::discard);
                } else {
                    StreamSupport.stream(serverLevel.getAllEntities().spliterator(), false)
                            .filter(entity -> entity.getId() == entityId).forEach(Entity::discard);
                }
            }
        });
    }
}
