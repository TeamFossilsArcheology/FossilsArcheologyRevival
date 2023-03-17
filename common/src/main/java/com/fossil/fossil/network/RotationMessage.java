package com.fossil.fossil.network;

import dev.architectury.networking.NetworkManager;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.Mob;

import java.util.function.Supplier;

public class RotationMessage {
    private final int id;
    private final double rotY;

    public RotationMessage(FriendlyByteBuf buf) {
        this(buf.readInt(), buf.readDouble());
    }

    public RotationMessage(int id, double rotY) {
        this.id = id;
        this.rotY = rotY;
    }

    public void write(FriendlyByteBuf buf) {
        buf.writeInt(id);
        buf.writeDouble(rotY);
    }

    public void apply(Supplier<NetworkManager.PacketContext> contextSupplier) {
        Entity entity = contextSupplier.get().getPlayer().level.getEntity(id);
        contextSupplier.get().queue(() -> {
            entity.setYBodyRot((float) rotY);
            entity.setYHeadRot((float) rotY);
        });
    }
}
