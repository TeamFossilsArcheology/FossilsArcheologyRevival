package com.github.teamfossilsarcheology.fossil.network.debug;

import com.github.teamfossilsarcheology.fossil.util.Version;
import dev.architectury.networking.NetworkChannel;
import dev.architectury.networking.NetworkManager;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.animal.horse.AbstractHorse;

import java.util.function.Supplier;

public class C2STameMessage {
    private final int id;

    private C2STameMessage(FriendlyByteBuf buf) {
        this(buf.readInt());
    }

    public C2STameMessage(int id) {
        this.id = id;
    }

    private void write(FriendlyByteBuf buf) {
        buf.writeInt(id);
    }

    private void apply(Supplier<NetworkManager.PacketContext> contextSupplier) {
        contextSupplier.get().queue(() -> {
            if (Version.debugEnabled()) {
                Entity entity = contextSupplier.get().getPlayer().level().getEntity(id);
                if (entity instanceof TamableAnimal animal) {
                    animal.tame(contextSupplier.get().getPlayer());
                } else if (entity instanceof AbstractHorse horse) {
                    horse.tameWithName(contextSupplier.get().getPlayer());
                }
            }
        });
    }

    public static void register(NetworkChannel channel) {
        channel.register(C2STameMessage.class, C2STameMessage::write, C2STameMessage::new, C2STameMessage::apply);
    }
}
