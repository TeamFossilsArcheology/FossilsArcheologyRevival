package com.fossil.fossil.entity;

import com.fossil.fossil.entity.prehistoric.base.Prehistoric;
import com.fossil.fossil.util.Version;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientboundAddEntityPacket;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.NotNull;

public abstract class ToyBase extends Entity {

    public final int moodBonus;
    protected final SoundEvent attackNoise;

    protected ToyBase(EntityType<? extends ToyBase> type, Level level, int moodBonus, SoundEvent attackNoise) {
        super(type, level);
        this.moodBonus = moodBonus;
        this.attackNoise = attackNoise;
    }

    protected boolean skipAI() {
        return true;
    }

    @Override
    public void baseTick() {
        if (!skipAI()) {
            super.baseTick();
        }
    }

    @Override
    public void tick() {
        if (!skipAI()) {
            super.tick();
        }
    }

    @Override
    public boolean hurt(DamageSource source, float amount) {
        if (source.getEntity() != null) {
            if (source.getDirectEntity() instanceof Player player) {
                if (player.getAbilities().instabuild) {
                    discard();
                    return true;
                }
                if (!player.getAbilities().mayBuild) {
                    return false;
                }
                Block.popResource(level, blockPosition(), getPickResult());
                discard();
                playSound(attackNoise, 1, getVoicePitch());
                return true;
            } else if (source.getEntity() instanceof Prehistoric prehistoric) {
                prehistoric.moodSystem.useToy(moodBonus);
                playSound(attackNoise, 1, getVoicePitch());
                return false;
            } else if (source == DamageSource.CRAMMING || source.isFire()) {
                Block.popResource(level, blockPosition(), getPickResult());
                discard();
            }
        }
        return source != DamageSource.OUT_OF_WORLD;
    }

    @Override
    public void remove(RemovalReason reason) {
        if (Version.debugEnabled() && reason == RemovalReason.KILLED) {
            return;
        }
        super.remove(reason);
    }

    @Override
    public boolean canBeCollidedWith() {
        return true;
    }

    @Override
    public boolean isPickable() {
        return !isRemoved();
    }

    public float getVoicePitch() {
        return (random.nextFloat() - random.nextFloat()) * 0.2f + 1;
    }

    @Override
    public @NotNull Packet<?> getAddEntityPacket() {
        return new ClientboundAddEntityPacket(this);
    }
}
