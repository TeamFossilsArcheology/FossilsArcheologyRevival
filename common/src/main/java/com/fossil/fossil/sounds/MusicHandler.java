package com.fossil.fossil.sounds;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.sounds.SoundEvent;

public class MusicHandler {

    public static void startMusic(SoundEvent soundEvent) {
        SoundInstance soundInstance = SimpleSoundInstance.forMusic(soundEvent);
        if (!Minecraft.getInstance().getSoundManager().isActive(soundInstance)) {
            Minecraft.getInstance().getSoundManager().play(soundInstance);
        }
    }

    public static void stopMusic(SoundEvent soundEvent) {
        SoundInstance soundInstance = SimpleSoundInstance.forMusic(soundEvent);
        if (Minecraft.getInstance().getSoundManager().isActive(soundInstance)) {
            Minecraft.getInstance().getSoundManager().stop(soundInstance);
        }
    }
}
