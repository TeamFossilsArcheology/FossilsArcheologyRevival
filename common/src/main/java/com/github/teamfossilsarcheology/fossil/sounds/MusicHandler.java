package com.github.teamfossilsarcheology.fossil.sounds;

import net.minecraft.client.Minecraft;
import net.minecraft.sounds.Music;

public class MusicHandler {

    public static void startMusic(Music music) {
        if (!Minecraft.getInstance().getMusicManager().isPlayingMusic(music)) {
            Minecraft.getInstance().getMusicManager().startPlaying(music);
        }
    }

    public static void stopMusic(Music music) {
        if (Minecraft.getInstance().getMusicManager().isPlayingMusic(music)) {
            Minecraft.getInstance().getMusicManager().stopPlaying();
        }
    }
}
