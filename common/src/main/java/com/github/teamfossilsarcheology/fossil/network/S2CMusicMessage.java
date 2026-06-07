package com.github.teamfossilsarcheology.fossil.network;

import com.github.teamfossilsarcheology.fossil.sounds.ModMusic;
import com.github.teamfossilsarcheology.fossil.sounds.ModSounds;
import com.github.teamfossilsarcheology.fossil.sounds.MusicHandler;
import dev.architectury.networking.NetworkChannel;
import dev.architectury.networking.NetworkManager;
import dev.architectury.utils.Env;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.sounds.Music;
import net.minecraft.sounds.Musics;
import net.minecraft.sounds.SoundEvent;

import java.util.function.Supplier;

/**
 * Sends a starting {@link SoundEvent} from server to client
 */
public class S2CMusicMessage {
    private final SoundEvent soundEvent;

    private S2CMusicMessage(FriendlyByteBuf buf) {
        this(BuiltInRegistries.SOUND_EVENT.byId(buf.readVarInt()));
    }

    public S2CMusicMessage(SoundEvent soundEvent) {
        this.soundEvent = soundEvent;
    }

    private void write(FriendlyByteBuf buf) {
        buf.writeVarInt(BuiltInRegistries.SOUND_EVENT.getId(soundEvent));
    }

    private void apply(Supplier<NetworkManager.PacketContext> contextSupplier) {
        if (contextSupplier.get().getEnvironment() == Env.SERVER) return;
        contextSupplier.get().queue(() -> MusicHandler.startMusic(getMusic(soundEvent)));
    }

    public static void register(NetworkChannel channel) {
        channel.register(S2CMusicMessage.class, S2CMusicMessage::write, S2CMusicMessage::new, S2CMusicMessage::apply);
    }

    private static Music getMusic(SoundEvent soundEvent) {
        if (soundEvent == ModSounds.MUSIC_ANU.get()) {
            return ModMusic.ANU;
        } else if (soundEvent == ModSounds.MUSIC_FIRST_DINOSAUR.get()) {
            return ModMusic.FIRST_DINOSAUR;
        }
        return Musics.GAME;
    }
}
