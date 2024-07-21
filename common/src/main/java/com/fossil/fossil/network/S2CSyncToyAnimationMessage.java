package com.fossil.fossil.network;

import com.fossil.fossil.entity.ToyTetheredLog;
import dev.architectury.networking.NetworkManager;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.Entity;

import java.util.function.Supplier;

public class S2CSyncToyAnimationMessage {
    private final int entityId;
    private final float animationX;
    private final float animationZ;

    public S2CSyncToyAnimationMessage(FriendlyByteBuf buf) {
        this.entityId = buf.readInt();
        this.animationX = buf.readFloat();
        this.animationZ = buf.readFloat();
    }

    public S2CSyncToyAnimationMessage(int entityId, float animationX, float animationZ) {
        this.entityId = entityId;
        this.animationX = animationX;
        this.animationZ = animationZ;
    }

    public void write(FriendlyByteBuf buf) {
        buf.writeInt(entityId);
        buf.writeFloat(animationX);
        buf.writeFloat(animationZ);
    }

    public void apply(Supplier<NetworkManager.PacketContext> contextSupplier) {
        Entity entity = contextSupplier.get().getPlayer().level.getEntity(entityId);
        if (entity instanceof ToyTetheredLog toy) {
            contextSupplier.get().queue(() -> toy.startAnimation(animationX, animationZ));
        }
    }
}
