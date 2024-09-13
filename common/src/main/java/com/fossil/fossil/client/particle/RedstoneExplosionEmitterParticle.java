package com.fossil.fossil.client.particle;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.NoRenderParticle;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.core.particles.SimpleParticleType;

public class RedstoneExplosionEmitterParticle extends NoRenderParticle {

    public RedstoneExplosionEmitterParticle(ClientLevel level, double x, double y, double z) {
        super(level, x, y, z, 0, 0, 0);
        this.lifetime = 8;
    }

    @Override
    public void tick() {
        for (int i = 0; i < 3; ++i) {
            double d = x + (random.nextDouble() - random.nextDouble()) * 4;
            double e = y + (random.nextDouble() - random.nextDouble()) * 4;
            double f = z + (random.nextDouble() - random.nextDouble()) * 4;
            level.addParticle(ModParticles.REDSTONE_EXPLOSION.get(), d, e, f, (float) age / lifetime, 0, 0);
        }
        age++;
        if (age == lifetime) {
            remove();
        }
    }

    public static class Provider implements ParticleProvider<SimpleParticleType> {

        @Override
        public Particle createParticle(SimpleParticleType type, ClientLevel level, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
            return new RedstoneExplosionEmitterParticle(level, x, y, z);
        }
    }
}
