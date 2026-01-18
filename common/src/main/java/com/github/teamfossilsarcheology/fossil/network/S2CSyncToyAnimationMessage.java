package com.github.teamfossilsarcheology.fossil.network;

import com.github.teamfossilsarcheology.fossil.entity.ToyTetheredLog;
import dev.architectury.networking.NetworkChannel;
import dev.architectury.networking.NetworkManager;
import dev.architectury.utils.Env;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.Entity;

import java.util.function.Supplier;

/**
 * Signals the start of a toy animation
 */
public class S2CSyncToyAnimationMessage {
    private final int entityId;
    private final float animationX;
    private final float animationZ;

    private S2CSyncToyAnimationMessage(FriendlyByteBuf buf) {
        this.entityId = buf.readInt();
        this.animationX = buf.readFloat();
        this.animationZ = buf.readFloat();
    }

    public S2CSyncToyAnimationMessage(int entityId, float animationX, float animationZ) {
        this.entityId = entityId;
        this.animationX = animationX;
        this.animationZ = animationZ;
    }

    private void write(FriendlyByteBuf buf) {
        buf.writeInt(entityId);
        buf.writeFloat(animationX);
        buf.writeFloat(animationZ);
    }

    private void apply(Supplier<NetworkManager.PacketContext> contextSupplier) {
        if (contextSupplier.get().getEnvironment() == Env.SERVER) return;
        contextSupplier.get().queue(() -> {
            Entity entity = contextSupplier.get().getPlayer().level().getEntity(entityId);
            if (entity instanceof ToyTetheredLog toy) {
                toy.startAnimation(animationX, animationZ);
            }
        });
    }

    public static void register(NetworkChannel channel) {
        channel.register(S2CSyncToyAnimationMessage.class, S2CSyncToyAnimationMessage::write, S2CSyncToyAnimationMessage::new, S2CSyncToyAnimationMessage::apply);
    }
}
