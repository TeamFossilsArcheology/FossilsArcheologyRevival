package com.github.teamfossilsarcheology.fossil.network;

import com.github.teamfossilsarcheology.fossil.entity.prehistoric.base.PrehistoricFlying;
import dev.architectury.networking.NetworkManager;
import dev.architectury.utils.Env;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;

import java.util.function.Supplier;

public class C2SRiderForceFlyingMessage {
    private final int entityId;
    private final boolean flying;

    public C2SRiderForceFlyingMessage(FriendlyByteBuf buf) {
        this.entityId = buf.readInt();
        this.flying = buf.readBoolean();
    }

    public C2SRiderForceFlyingMessage(int entityId, boolean flying) {
        this.entityId = entityId;
        this.flying = flying;
    }

    public void write(FriendlyByteBuf buf) {
        buf.writeInt(entityId);
        buf.writeBoolean(flying);
    }

    public void apply(Supplier<NetworkManager.PacketContext> contextSupplier) {
        if (contextSupplier.get().getEnvironment() == Env.CLIENT) return;
        contextSupplier.get().queue(() -> {
            Player player = contextSupplier.get().getPlayer();
            Entity entity = player.level.getEntity(entityId);
            if (entity instanceof PrehistoricFlying mob) {
                Entity rider = entity.getControllingPassenger();
                if (rider != null && rider.getId() == player.getId()) {
                    if (flying) {
                        if (mob.hasTakeOffAnimation()) {
                            mob.startTakeOff();
                        } else {
                            mob.setFlying(true);
                        }
                    } else {
                        mob.setFlying(false);
                    }
                }
            }
        });
    }
}
