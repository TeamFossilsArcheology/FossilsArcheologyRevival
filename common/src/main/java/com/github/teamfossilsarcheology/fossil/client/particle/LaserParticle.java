package com.github.teamfossilsarcheology.fossil.client.particle;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.core.particles.SimpleParticleType;
import org.jetbrains.annotations.Nullable;

public class LaserParticle extends TextureSheetParticle {
    protected LaserParticle(ClientLevel level, double x, double y, double z) {
        super(level, x, y, z);

        this.lifetime = 2;

        this.scale(0.5f);

        this.rCol = 1.0f;
        this.gCol = 0.0f;
        this.bCol = 0.0f;
    }

    @Override
    public int getLightColor(float partialTick) {
        return 0xF000F0; // full brightness
    }

    @Override
    public ParticleRenderType getRenderType() {
        return ParticleRenderType.PARTICLE_SHEET_OPAQUE;
    }

    public static class Provider implements ParticleProvider<SimpleParticleType> {
        private final SpriteSet spriteSet;

        public Provider(SpriteSet spriteSet) {
            this.spriteSet = spriteSet;
        }

        @Override
        public Particle createParticle(SimpleParticleType type, ClientLevel level,
                                       double x, double y, double z,
                                       double dx, double dy, double dz) {
            LaserParticle particle = new LaserParticle(level, x, y, z);
            particle.pickSprite(this.spriteSet);
            return particle;
        }
    }
}
