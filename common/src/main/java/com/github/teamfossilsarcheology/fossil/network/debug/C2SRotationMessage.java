package com.github.teamfossilsarcheology.fossil.network.debug;

import com.github.teamfossilsarcheology.fossil.util.Version;
import dev.architectury.networking.NetworkManager;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;

import java.util.function.Supplier;

public class C2SRotationMessage {
    public static final byte Y_ROT = 0;
    public static final byte X_ROT = 1;
    private final int entityId;
    private final double rotation;
    private final byte flag;

    public C2SRotationMessage(FriendlyByteBuf buf) {
        this(buf.readInt(), buf.readDouble(), buf.readByte());
    }

    public C2SRotationMessage(int entityId, double rotation, byte flag) {
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
        contextSupplier.get().queue(() -> {
            if (Version.debugEnabled()) {
                Entity entity = contextSupplier.get().getPlayer().level.getEntity(entityId);
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
