package com.fossil.fossil.client.particle;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.core.particles.SimpleParticleType;
import org.jetbrains.annotations.NotNull;

public class TarBubbleParticle extends TextureSheetParticle {
    private final SpriteSet sprites;

    protected TarBubbleParticle(ClientLevel clientLevel, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed,
                                SpriteSet sprites) {
        super(clientLevel, x, y, z, xSpeed, ySpeed, zSpeed);
        this.sprites = sprites;
        this.xd *= 0.10000000149011612D;
        this.yd *= 0.10000000149011612D;
        this.zd *= 0.10000000149011612D;
        this.lifetime = (int) (5 / (Math.random() * 0.8 + 0.2));
        setSpriteFromAge(sprites);
    }

    @Override
    public void tick() {
        super.tick();
        boolean popped = age > lifetime - 2;
        if (popped) {
            setSprite(sprites.get(1, 1));
        } else {
            setSprite(sprites.get(0, 1));
        }
    }

    @Override
    public @NotNull ParticleRenderType getRenderType() {
        return ParticleRenderType.PARTICLE_SHEET_OPAQUE;
    }

    public static class Type extends SimpleParticleType {
        public Type(boolean bl) {
            super(bl);
        }
    }

    public static class Provider implements ParticleProvider<SimpleParticleType> {
        private final SpriteSet sprites;

        public Provider(SpriteSet spriteSet) {
            this.sprites = spriteSet;
        }

        @Override
        public Particle createParticle(SimpleParticleType type, ClientLevel level, double x, double y, double z, double xSpeed, double ySpeed,
                                       double zSpeed) {
            return new TarBubbleParticle(level, x, y, z, xSpeed, ySpeed, zSpeed, sprites);
        }
    }
}
