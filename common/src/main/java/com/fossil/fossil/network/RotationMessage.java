package com.fossil.fossil.network;

import dev.architectury.networking.NetworkManager;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.Mob;

import java.util.function.Supplier;

public class RotationMessage {
    public static final byte Y_ROT = 0;
    public static final byte X_ROT = 1;
    private final int id;
    private final double rotY;
    private final byte flag;

    public RotationMessage(FriendlyByteBuf buf) {
        this(buf.readInt(), buf.readDouble(), buf.readByte());
    }

    public RotationMessage(int id, double rotY, byte flag) {
        this.id = id;
        this.rotY = rotY;
        this.flag = flag;
    }

    public void write(FriendlyByteBuf buf) {
        buf.writeInt(id);
        buf.writeDouble(rotY);
        buf.writeByte(flag);
    }

    public void apply(Supplier<NetworkManager.PacketContext> contextSupplier) {
        Entity entity = contextSupplier.get().getPlayer().level.getEntity(id);
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
