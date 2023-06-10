package com.fossil.fossil.network.debug;

import dev.architectury.networking.NetworkManager;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.Entity;

import java.util.function.Supplier;

public class RotationMessage {
    public static final byte Y_ROT = 0;
    public static final byte X_ROT = 1;
    private final int entityId;
    private final double rotY;
    private final byte flag;

    public RotationMessage(FriendlyByteBuf buf) {
        this(buf.readInt(), buf.readDouble(), buf.readByte());
    }

    public RotationMessage(int entityId, double rotY, byte flag) {
        this.entityId = entityId;
        this.rotY = rotY;
        this.flag = flag;
    }

    public void write(FriendlyByteBuf buf) {
        buf.writeInt(entityId);
        buf.writeDouble(rotY);
        buf.writeByte(flag);
    }

    public void apply(Supplier<NetworkManager.PacketContext> contextSupplier) {
        Entity entity = contextSupplier.get().getPlayer().level.getEntity(entityId);
        contextSupplier.get().queue(() -> {
            switch (flag) {
                case 0 -> {
                    if (entity != null) {
                        entity.setYBodyRot((float) rotY);
                        entity.setYHeadRot((float) rotY);
                    }
                }
                case 1 -> {

                }
            }
        });
    }
}
