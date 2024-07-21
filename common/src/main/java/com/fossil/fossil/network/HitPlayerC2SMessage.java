package com.fossil.fossil.network;

import com.fossil.fossil.entity.prehistoric.base.Prehistoric;
import dev.architectury.networking.NetworkManager;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;

import java.util.function.Supplier;

/**
 * Cause a player hit on the server side. Will only trigger for the player that sent the message
 */
public class HitPlayerC2SMessage {
    private final int entityId;
    private final int targetId;

    public HitPlayerC2SMessage(FriendlyByteBuf buf) {
        this.entityId = buf.readInt();
        this.targetId = buf.readInt();
    }

    public HitPlayerC2SMessage(Entity entity, Entity target) {
        this.entityId = entity.getId();
        this.targetId = target.getId();
    }

    public void write(FriendlyByteBuf buf) {
        buf.writeInt(entityId);
        buf.writeInt(targetId);
    }

    public void apply(Supplier<NetworkManager.PacketContext> contextSupplier) {
        Player player = contextSupplier.get().getPlayer();
        Entity entity = player.level.getEntity(entityId);
        if (entity instanceof Prehistoric prehistoric && player.getId() == targetId) {
            contextSupplier.get().queue(() -> prehistoric.attackTarget(player));
        }
    }
}
