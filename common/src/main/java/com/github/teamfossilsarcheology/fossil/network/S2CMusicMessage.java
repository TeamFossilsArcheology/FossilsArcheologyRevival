package com.github.teamfossilsarcheology.fossil.network;

import com.github.teamfossilsarcheology.fossil.sounds.MusicHandler;
import dev.architectury.networking.NetworkManager;
import dev.architectury.utils.Env;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.sounds.SoundEvent;

import java.util.function.Supplier;

public class S2CMusicMessage {
    private final SoundEvent soundEvent;

    public S2CMusicMessage(FriendlyByteBuf buf) {
        this(BuiltInRegistries.SOUND_EVENT.byId(buf.readVarInt()));
    }

    public S2CMusicMessage(SoundEvent soundEvent) {
        this.soundEvent = soundEvent;
    }

    public void write(FriendlyByteBuf buf) {
        buf.writeVarInt(BuiltInRegistries.SOUND_EVENT.getId(soundEvent));
    }

    public void apply(Supplier<NetworkManager.PacketContext> contextSupplier) {
        if (contextSupplier.get().getEnvironment() == Env.SERVER) return;
        contextSupplier.get().queue(() -> MusicHandler.startMusic(soundEvent));
    }
}
