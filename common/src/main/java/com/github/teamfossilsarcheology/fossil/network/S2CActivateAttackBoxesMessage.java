package com.github.teamfossilsarcheology.fossil.network;

import com.github.teamfossilsarcheology.fossil.entity.prehistoric.base.Prehistoric;
import dev.architectury.networking.NetworkChannel;
import dev.architectury.networking.NetworkManager;
import dev.architectury.utils.Env;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.Entity;

import java.util.function.Supplier;

/**
 * Activate a mobs attack boxes on the client. Ideally only sent to the player that is being attack
 */
public class S2CActivateAttackBoxesMessage {
    private final int entityId;
    private final double attackDuration;

    private S2CActivateAttackBoxesMessage(FriendlyByteBuf buf) {
        this.entityId = buf.readInt();
        this.attackDuration = buf.readDouble();
    }

    public S2CActivateAttackBoxesMessage(Entity entity, double attackDuration) {
        this.entityId = entity.getId();
        this.attackDuration = attackDuration;
    }

    private void write(FriendlyByteBuf buf) {
        buf.writeInt(entityId);
        buf.writeDouble(attackDuration);
    }

    private void apply(Supplier<NetworkManager.PacketContext> contextSupplier) {
        if (contextSupplier.get().getEnvironment() == Env.SERVER) return;
        contextSupplier.get().queue(() -> {
            Entity entity = contextSupplier.get().getPlayer().level().getEntity(entityId);
            if (entity instanceof Prehistoric prehistoric) {
                prehistoric.getEntityHitboxData().getAttackBoxData().activateAttackBoxes(contextSupplier.get().getPlayer().level(), attackDuration);
            }
        });
    }

    public static void register(NetworkChannel channel) {
        channel.register(S2CActivateAttackBoxesMessage.class, S2CActivateAttackBoxesMessage::write, S2CActivateAttackBoxesMessage::new, S2CActivateAttackBoxesMessage::apply);
    }
}
