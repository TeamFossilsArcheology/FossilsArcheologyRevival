package com.github.teamfossilsarcheology.fossil.client.particle;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.NoRenderParticle;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.core.particles.SimpleParticleType;

public class VolcanoVentAshEmitterParticle extends NoRenderParticle {

    protected VolcanoVentAshEmitterParticle(ClientLevel clientLevel, double x, double y, double z) {
        super(clientLevel, x, y, z);
        this.lifetime = 8;
    }

    @Override
    public void tick() {
        for (int i = 0; i < 6; ++i) {
            double d = x + (random.nextDouble() - random.nextDouble()) * 1;
            double e = y + (random.nextDouble() - random.nextDouble()) * 1;
            double f = z + (random.nextDouble() - random.nextDouble()) * 1;
            level.addParticle(ModParticles.VOLCANO_VENT_ASH.get(), d, e, f, 0, 0, 0);
        }
        age++;
        if (age == lifetime) {
            remove();
        }
    }

    public static class Provider implements ParticleProvider<SimpleParticleType> {

        @Override
        public Particle createParticle(SimpleParticleType type, ClientLevel level, double x, double y, double z, double xSpeed, double ySpeed,
                                       double zSpeed) {
            return new VolcanoVentAshEmitterParticle(level, x, y, z);
        }
    }
}
