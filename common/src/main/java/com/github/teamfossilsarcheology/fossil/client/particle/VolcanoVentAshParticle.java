package com.github.teamfossilsarcheology.fossil.client.particle;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.util.Mth;
import org.jetbrains.annotations.NotNull;

public class VolcanoVentAshParticle extends TextureSheetParticle {
    private final double yEnd;
    private boolean reached;

    protected VolcanoVentAshParticle(ClientLevel level, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
        super(level, x, y, z, xSpeed, ySpeed, zSpeed);
        xd *= 0.10000000149011612;
        yd = level.random.nextDouble() + 0.2;
        zd *= 0.10000000149011612;
        yEnd = y + 6 + level.random.nextDouble() * 4;
        lifetime = 120;
        hasPhysics = false;
    }

    @Override
    public float getQuadSize(float scaleFactor) {
        return this.quadSize * Mth.clamp((this.age + scaleFactor) / this.lifetime * 32, 0, 1);
    }

    @Override
    public void tick() {
        super.tick();
        if (reached || y >= yEnd) {
            yd *= 0.9f;
            if (!reached) {
                xd += (level.random.nextInt(3) - 1) * (0.05 + level.random.nextDouble() * 0.05);
                zd += (level.random.nextInt(3) - 1) * (0.05 + level.random.nextDouble() * 0.05);
            }
            reached = true;
        }
    }

    @Override
    public @NotNull ParticleRenderType getRenderType() {
        return ParticleRenderType.PARTICLE_SHEET_OPAQUE;
    }

    public static class Provider implements ParticleProvider<SimpleParticleType> {
        private final SpriteSet sprite;

        public Provider(SpriteSet spriteSet) {
            this.sprite = spriteSet;
        }

        @Override
        public Particle createParticle(SimpleParticleType type, ClientLevel level, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
            VolcanoVentAshParticle particle = new VolcanoVentAshParticle(level, x, y, z, xSpeed, ySpeed, zSpeed);
            particle.pickSprite(sprite);
            return particle;
        }
    }
}
