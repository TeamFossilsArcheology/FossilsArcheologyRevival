package com.fossil.fossil.network;

import com.fossil.fossil.entity.prehistoric.base.EntityInfo;
import dev.architectury.injectables.annotations.ExpectPlatform;
import dev.architectury.networking.NetworkManager;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.function.Supplier;

public class S2CMammalCapMessage {
    private final int entityId;
    private final int embryoProgress;
    private final EntityInfo embryo;

    public S2CMammalCapMessage(FriendlyByteBuf buf) {
        this.entityId = buf.readInt();
        this.embryoProgress = buf.readInt();
        EntityInfo temp;
        try {
            temp = EntityInfo.fromNbt(buf.readUtf());
        } catch (IllegalArgumentException e) {
            temp = null;
        }
        this.embryo = temp;
    }

    public S2CMammalCapMessage(Animal animal, int embryoProgress, @Nullable EntityInfo embryo) {
        this.entityId = animal.getId();
        this.embryoProgress = embryoProgress;
        this.embryo = embryo;
    }

    @ExpectPlatform
    public static void applyCap(Level level, int entityId, int embryoProgress, EntityInfo embryo) {

    }

    public void write(FriendlyByteBuf buf) {
        buf.writeInt(entityId);
        buf.writeInt(embryoProgress);
        if (embryo != null) {
            buf.writeUtf(embryo.name());
        } else {
            buf.writeUtf("null");
        }
    }

    public void apply(Supplier<NetworkManager.PacketContext> contextSupplier) {
        contextSupplier.get().queue(() -> applyCap(contextSupplier.get().getPlayer().level, entityId, embryoProgress, embryo));
    }
}
