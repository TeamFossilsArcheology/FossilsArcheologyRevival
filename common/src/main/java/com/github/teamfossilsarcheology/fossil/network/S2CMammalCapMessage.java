package com.github.teamfossilsarcheology.fossil.network;

import com.github.teamfossilsarcheology.fossil.capabilities.ModCapabilities;
import com.github.teamfossilsarcheology.fossil.entity.prehistoric.base.EntityInfo;
import dev.architectury.networking.NetworkChannel;
import dev.architectury.networking.NetworkManager;
import dev.architectury.utils.Env;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.animal.Animal;
import org.jetbrains.annotations.Nullable;

import java.util.function.Supplier;

/**
 * Only used by the forge side. Sends the current embryo status from server to client
 */
public class S2CMammalCapMessage {
    private final int entityId;
    private final int embryoProgress;
    private final EntityInfo embryo;

    private S2CMammalCapMessage(FriendlyByteBuf buf) {
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

    private void write(FriendlyByteBuf buf) {
        buf.writeInt(entityId);
        buf.writeInt(embryoProgress);
        if (embryo != null) {
            buf.writeUtf(embryo.name());
        } else {
            buf.writeUtf("null");
        }
    }

    private void apply(Supplier<NetworkManager.PacketContext> contextSupplier) {
        if (contextSupplier.get().getEnvironment() == Env.SERVER) return;
        contextSupplier.get().queue(() -> {
            Entity entity = contextSupplier.get().getPlayer().level().getEntity(entityId);
            if (entity instanceof Animal animal) {
                ModCapabilities.setEmbryoProgress(animal, embryoProgress);
                ModCapabilities.setEmbryo(animal, embryo);
            }
        });
    }

    public static void register(NetworkChannel channel) {
        channel.register(S2CMammalCapMessage.class, S2CMammalCapMessage::write, S2CMammalCapMessage::new, S2CMammalCapMessage::apply);
    }
}
