package com.fossil.fossil.entity.prehistoric;

import com.fossil.fossil.sounds.ModSounds;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.animal.horse.AbstractChestedHorse;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

public class Quagga extends AbstractChestedHorse {
    public static final String ANIMATIONS = "quagga.animation.json";

    public Quagga(EntityType<Quagga> entityType, Level level) {
        super(entityType, level);
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
    }

    @Nullable
    @Override
    protected SoundEvent getAmbientSound() {
        super.getAmbientSound();
        return ModSounds.QUAGGA_AMBIENT.get();
    }

    @Nullable
    @Override
    protected SoundEvent getHurtSound(DamageSource damageSource) {
        super.getHurtSound(damageSource);
        return ModSounds.QUAGGA_HURT.get();
    }

    @Nullable
    @Override
    protected SoundEvent getDeathSound() {
        return ModSounds.QUAGGA_DEATH.get();
    }
}