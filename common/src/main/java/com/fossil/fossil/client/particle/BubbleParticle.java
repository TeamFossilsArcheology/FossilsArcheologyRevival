package com.fossil.fossil.client.particle;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.core.particles.SimpleParticleType;
import org.jetbrains.annotations.NotNull;

public class BubbleParticle extends RisingParticle {
    protected BubbleParticle(ClientLevel clientLevel, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
        super(clientLevel, x, y, z, xSpeed, ySpeed, zSpeed);
        this.setSize(0.02f, 0.02f);
        this.quadSize *= this.random.nextFloat() * 0.6f;
        this.lifetime = (int) (18 / (Math.random() * 0.4 + 0.2));
    }

    @Override
    public void tick() {
        super.tick();
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
        private final SpriteSet sprite;

        public Provider(SpriteSet spriteSet) {
            this.sprite = spriteSet;
        }

        @Override
        public Particle createParticle(SimpleParticleType type, ClientLevel level, double x, double y, double z, double xSpeed, double ySpeed,
                                       double zSpeed) {
            BubbleParticle bubbleParticle = new BubbleParticle(level, x, y, z, xSpeed, ySpeed, zSpeed);
            bubbleParticle.pickSprite(this.sprite);
            return bubbleParticle;
        }
    }
}
