package com.github.teamfossilsarcheology.fossil.entity.ai.anu;

import com.github.teamfossilsarcheology.fossil.entity.monster.AnuBoss;
import com.github.teamfossilsarcheology.fossil.sounds.ModSounds;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

public class AnuLandingPhase extends AbstractAnuPhaseInstance {
    @Nullable
    private Vec3 targetLocation;

    public AnuLandingPhase(AnuBoss anuBoss) {
        super(anuBoss);
    }

    @Override
    public void doClientTick() {
        for (int i = 0; i < 2; ++i) {
            anu.level().addParticle(ParticleTypes.SMOKE, anu.getRandomX(0.5), anu.getRandomY(), anu.getRandomZ(0.5), 0, 0, 0);
        }
    }

    @Override
    public void doServerTick() {
        if (anu.onGround()) {
            anu.phaseSystem.setPhase(AnuPhase.MELEE, 15 * 20);
            return;
        }
        if (targetLocation == null) {
            anu.phaseSystem.setPhase(AnuPhase.MELEE, 15 * 20);
            //TODO: switch to Flight nav
            //TODO: Land near Player OR fall out of sky because hurt
        }
    }

    @Override
    public void begin(int duration) {
        super.begin(duration);
        anu.setWeak(true);//TODO: Play sound effect?
    }

    @Override
    public void end() {
        targetLocation = null;
    }

    @Override
    public AnuPhase getPhase() {
        return AnuPhase.LANDING;
    }

    @Override
    public SoundEvent getAmbientSound() {
        return ModSounds.ANU_COUGH.get();
    }

    @Override
    public boolean isFlying() {
        return true;
    }
}
