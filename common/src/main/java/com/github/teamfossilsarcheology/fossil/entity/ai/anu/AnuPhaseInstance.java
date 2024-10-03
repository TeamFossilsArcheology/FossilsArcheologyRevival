package com.github.teamfossilsarcheology.fossil.entity.ai.anu;

import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.damagesource.DamageSource;

public interface AnuPhaseInstance {

    void doClientTick();

    void doServerTick();

    AnuPhase getPhase();

    void onHurt(DamageSource source, float amount);

    void begin(int duration);

    void end();

    SoundEvent getAmbientSound();

    boolean isFlying();
}
