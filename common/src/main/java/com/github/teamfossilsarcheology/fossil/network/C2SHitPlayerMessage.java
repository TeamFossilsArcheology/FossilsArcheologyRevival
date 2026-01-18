package com.github.teamfossilsarcheology.fossil.network;

import com.github.teamfossilsarcheology.fossil.entity.prehistoric.base.Prehistoric;
import dev.architectury.networking.NetworkChannel;
import dev.architectury.networking.NetworkManager;
import dev.architectury.utils.Env;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;

import java.util.function.Supplier;

/**
 * Cause a player hit on the server side. Will only trigger for the player that sent the message
 */
public class C2SHitPlayerMessage {
    private final int entityId;
    private final int targetId;

    private C2SHitPlayerMessage(FriendlyByteBuf buf) {
        this.entityId = buf.readInt();
        this.targetId = buf.readInt();
    }

    public C2SHitPlayerMessage(Entity entity, Entity target) {
        this.entityId = entity.getId();
        this.targetId = target.getId();
    }

    private void write(FriendlyByteBuf buf) {
        buf.writeInt(entityId);
        buf.writeInt(targetId);
    }

    private void apply(Supplier<NetworkManager.PacketContext> contextSupplier) {
        if (contextSupplier.get().getEnvironment() == Env.CLIENT) return;
        contextSupplier.get().queue(() -> {
            Player player = contextSupplier.get().getPlayer();
            Entity entity = player.level().getEntity(entityId);
            if (entity instanceof Prehistoric prehistoric && player.getId() == targetId) {
                prehistoric.attackTarget(player);
            }
        });
    }

    public static void register(NetworkChannel channel) {
        channel.register(C2SHitPlayerMessage.class, C2SHitPlayerMessage::write, C2SHitPlayerMessage::new, C2SHitPlayerMessage::apply);
    }
}
