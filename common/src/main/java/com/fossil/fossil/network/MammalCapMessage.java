package com.fossil.fossil.network;

import com.fossil.fossil.entity.prehistoric.base.PrehistoricEntityType;
import dev.architectury.injectables.annotations.ExpectPlatform;
import dev.architectury.networking.NetworkManager;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.function.Supplier;

public class MammalCapMessage {
    private final int entityId;
    private final int embryoProgress;
    private final PrehistoricEntityType embryo;

    public MammalCapMessage(FriendlyByteBuf buf) {
        this.entityId = buf.readInt();
        this.embryoProgress = buf.readInt();
        PrehistoricEntityType temp;
        try {
            temp = PrehistoricEntityType.valueOf(buf.readUtf());
        } catch (IllegalArgumentException e) {
            temp = null;
        }
        this.embryo = temp;
    }

    public MammalCapMessage(Animal animal, int embryoProgress, @Nullable PrehistoricEntityType embryo) {
        this.entityId = animal.getId();
        this.embryoProgress = embryoProgress;
        this.embryo = embryo;
    }

    public void write(FriendlyByteBuf buf) {
        buf.writeInt(entityId);
        buf.writeInt(embryoProgress);
        if (embryo != null) {
            buf.writeUtf(embryo.name());
        } else{
            buf.writeUtf("null");
        }
    }

    public void apply(Supplier<NetworkManager.PacketContext> contextSupplier) {
        contextSupplier.get().queue(() -> syncCap(contextSupplier.get().getPlayer().level, entityId, embryoProgress, embryo));
    }

    @ExpectPlatform
    public static void syncCap(Level level, int entityId, int embryoProgress, PrehistoricEntityType embryo) {

    }
}
