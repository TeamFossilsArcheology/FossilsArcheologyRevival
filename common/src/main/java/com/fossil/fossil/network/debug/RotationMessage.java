package com.fossil.fossil.network.debug;

import com.fossil.fossil.util.Version;
import dev.architectury.networking.NetworkManager;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;

import java.util.function.Supplier;

public class RotationMessage {
    public static final byte Y_ROT = 0;
    public static final byte X_ROT = 1;
    private final int entityId;
    private final double rotation;
    private final byte flag;

    public RotationMessage(FriendlyByteBuf buf) {
        this(buf.readInt(), buf.readDouble(), buf.readByte());
    }

    public RotationMessage(int entityId, double rotation, byte flag) {
        this.entityId = entityId;
        this.rotation = rotation;
        this.flag = flag;
    }

    public void write(FriendlyByteBuf buf) {
        buf.writeInt(entityId);
        buf.writeDouble(rotation);
        buf.writeByte(flag);
    }

    public void apply(Supplier<NetworkManager.PacketContext> contextSupplier) {
        Entity entity = contextSupplier.get().getPlayer().level.getEntity(entityId);
        contextSupplier.get().queue(() -> {
            if (Version.debugEnabled()) {
                switch (flag) {
                    case 0 -> {
                        if (entity instanceof LivingEntity) {
                            entity.setYBodyRot((float) rotation);
                            entity.setYHeadRot((float) rotation);
                        } else {
                            entity.setYRot((float) rotation);
                        }
                    }
                    case 1 -> {
                        if (entity != null) {
                            entity.setXRot((float) rotation);
                        }
                    }
                }
            }
        });
    }
}
