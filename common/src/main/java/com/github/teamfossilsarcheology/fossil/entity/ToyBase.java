package com.github.teamfossilsarcheology.fossil.entity;

import com.github.teamfossilsarcheology.fossil.entity.prehistoric.base.Prehistoric;
import com.github.teamfossilsarcheology.fossil.util.Version;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundAddEntityPacket;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.properties.WoodType;
import org.jetbrains.annotations.NotNull;

import java.util.Set;

public abstract class ToyBase extends Entity {
    private static final Set<WoodType> VANILLA_WOOD_TYPES = Set.of(WoodType.OAK, WoodType.SPRUCE, WoodType.BIRCH, WoodType.ACACIA, WoodType.JUNGLE, WoodType.DARK_OAK, WoodType.CRIMSON, WoodType.WARPED, WoodType.MANGROVE, WoodType.BAMBOO, WoodType.CHERRY);

    public final int moodBonus;
    protected final SoundEvent attackNoise;

    protected ToyBase(EntityType<? extends ToyBase> type, Level level, int moodBonus, SoundEvent attackNoise) {
        super(type, level);
        this.moodBonus = moodBonus;
        this.attackNoise = attackNoise;
    }

    public static boolean isVanillaWood(WoodType woodType) {
        return VANILLA_WOOD_TYPES.contains(woodType);
    }

    protected boolean tickAI() {
        return false;
    }

    @Override
    public void baseTick() {
        if (tickAI()) {
            super.baseTick();
        }
    }

    @Override
    public void tick() {
        if (tickAI()) {
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
                Block.popResource(level(), blockPosition(), getPickResult());
                discard();
                playSound(attackNoise, 1, getVoicePitch());
                return true;
            } else if (source.getEntity() instanceof Prehistoric prehistoric) {
                prehistoric.moodSystem.useToy(moodBonus);
                playSound(attackNoise, 1, getVoicePitch());
                return false;
            } else if (source == damageSources().cramming() || source.is(DamageTypeTags.IS_FIRE)) {
                Block.popResource(level(), blockPosition(), getPickResult());
                discard();
            }
        }
        return source != damageSources().fellOutOfWorld();
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
    public @NotNull Packet<ClientGamePacketListener> getAddEntityPacket() {
        return new ClientboundAddEntityPacket(this);
    }
}
