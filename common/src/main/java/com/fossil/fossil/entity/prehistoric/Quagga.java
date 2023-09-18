package com.fossil.fossil.entity.prehistoric;

import com.fossil.fossil.entity.ModEntities;
import com.fossil.fossil.sounds.ModSounds;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.horse.AbstractChestedHorse;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

public class Quagga extends AbstractChestedHorse {

    public Quagga(EntityType<Quagga> entityType, Level level) {
        super(entityType, level);
    }

    @Override
    protected void randomizeAttributes() {
        getAttribute(Attributes.MAX_HEALTH).setBaseValue(generateRandomMaxHealth());
        getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(generateRandomSpeed());
        getAttribute(Attributes.JUMP_STRENGTH).setBaseValue(generateRandomJumpStrength());
    }

    @Override
    public boolean canMate(Animal otherAnimal) {
        if (otherAnimal == this) {
            return false;
        } else if (otherAnimal instanceof Quagga quagga) {
            return canParent() && quagga.canParent();
        } else {
            return false;
        }
    }

    @Nullable
    @Override
    public AgeableMob getBreedOffspring(ServerLevel level, AgeableMob otherParent) {
        Quagga child = ModEntities.QUAGGA.get().create(level);
        setOffspringAttributes(otherParent, child);
        return child;
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