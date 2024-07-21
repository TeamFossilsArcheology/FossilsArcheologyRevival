package com.fossil.fossil.network;

import com.fossil.fossil.entity.prehistoric.base.Prehistoric;
import dev.architectury.networking.NetworkManager;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.Entity;

import java.util.function.Supplier;

/**
 * Activate a mobs attack boxes on the client
 */
public class S2CActivateAttackBoxesMessage {
    private final int entityId;
    private final double attackDuration;

    public S2CActivateAttackBoxesMessage(FriendlyByteBuf buf) {
        this.entityId = buf.readInt();
        this.attackDuration = buf.readDouble();
    }

    public S2CActivateAttackBoxesMessage(Entity entity, double attackDuration) {
        this.entityId = entity.getId();
        this.attackDuration = attackDuration;
    }

    public void write(FriendlyByteBuf buf) {
        buf.writeInt(entityId);
        buf.writeDouble(attackDuration);
    }

    public void apply(Supplier<NetworkManager.PacketContext> contextSupplier) {
        Entity entity = contextSupplier.get().getPlayer().level.getEntity(entityId);
        if (entity instanceof Prehistoric prehistoric) {
            contextSupplier.get().queue(() -> prehistoric.activateAttackBoxes(attackDuration));
        }
    }
}
