package com.github.teamfossilsarcheology.fossil.network;

import com.github.teamfossilsarcheology.fossil.entity.prehistoric.base.PrehistoricFlying;
import dev.architectury.networking.NetworkChannel;
import dev.architectury.networking.NetworkManager;
import dev.architectury.utils.Env;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;

import java.util.function.Supplier;

public class C2SRiderForceFlyingMessage {
    private static final Component FLYING_DISABLED = Component.translatable("entity.fossil.flying.disabled");
    private final int entityId;
    private final boolean flying;

    private C2SRiderForceFlyingMessage(FriendlyByteBuf buf) {
        this.entityId = buf.readInt();
        this.flying = buf.readBoolean();
    }

    public C2SRiderForceFlyingMessage(int entityId, boolean flying) {
        this.entityId = entityId;
        this.flying = flying;
    }

    private void write(FriendlyByteBuf buf) {
        buf.writeInt(entityId);
        buf.writeBoolean(flying);
    }

    private void apply(Supplier<NetworkManager.PacketContext> contextSupplier) {
        if (contextSupplier.get().getEnvironment() == Env.CLIENT) return;
        contextSupplier.get().queue(() -> {
            Player player = contextSupplier.get().getPlayer();
            Entity entity = player.level().getEntity(entityId);
            if (player.getServer() != null && !player.getServer().isFlightAllowed()) {
                player.displayClientMessage(FLYING_DISABLED, false);
            } else if (entity instanceof PrehistoricFlying mob) {
                Entity rider = entity.getControllingPassenger();
                if (rider != null && rider.getId() == player.getId()) {
                    if (flying) {
                        if (mob.hasTakeOffAnimation()) {
                            mob.startTakeOff();
                        } else {
                            mob.setFlying(true);
                        }
                    } else {
                        mob.setFlying(true);
                        mob.setFlying(false);
                    }
                }
            }
        });
    }

    public static void register(NetworkChannel channel) {
        channel.register(C2SRiderForceFlyingMessage.class, C2SRiderForceFlyingMessage::write, C2SRiderForceFlyingMessage::new, C2SRiderForceFlyingMessage::apply);
    }
}
