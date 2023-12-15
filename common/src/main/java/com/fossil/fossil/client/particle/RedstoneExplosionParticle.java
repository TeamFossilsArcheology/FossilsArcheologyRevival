package com.fossil.fossil.client.particle;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.core.particles.SimpleParticleType;
import org.jetbrains.annotations.NotNull;

public class RedstoneExplosionParticle extends TextureSheetParticle {
    private final SpriteSet sprites;

    public RedstoneExplosionParticle(ClientLevel level, double x, double y, double z, double xSpeed, SpriteSet sprites) {
        super(level, x, y, z, 0, 0, 0);
        this.lifetime = 3 + random.nextInt(4);
        float h = random.nextFloat() * 0.6f + 0.4f;
        this.rCol = h;
        this.gCol = h;
        this.bCol = h;
        this.quadSize = 2 * (1 - (float) xSpeed * 0.5f);
        this.sprites = sprites;
        this.setSpriteFromAge(sprites);
    }

    @Override
    public int getLightColor(float partialTick) {
        return 0xF000F0;
    }

    @Override
    public void tick() {
        xo = x;
        yo = y;
        zo = z;
        if (age++ >= lifetime) {
            remove();
            return;
        }
        setSpriteFromAge(sprites);
    }

    @Override
    public @NotNull ParticleRenderType getRenderType() {
        return ParticleRenderType.PARTICLE_SHEET_LIT;
    }

    public static class Type extends SimpleParticleType {
        public Type(boolean bl) {
            super(bl);
        }
    }

    public static class Provider implements ParticleProvider<SimpleParticleType> {
        private final SpriteSet sprites;

        public Provider(SpriteSet sprites) {
            this.sprites = sprites;
        }

        @Override
        public Particle createParticle(SimpleParticleType type, ClientLevel level, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
            return new RedstoneExplosionParticle(level, x, y, z, xSpeed, sprites);
        }
    }
}
