package com.github.teamfossilsarcheology.fossil.entity;

import com.github.teamfossilsarcheology.fossil.item.ModItems;
import com.github.teamfossilsarcheology.fossil.network.MessageHandler;
import com.github.teamfossilsarcheology.fossil.network.S2CSyncToyAnimationMessage;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.properties.WoodType;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class ToyTetheredLog extends ToyBase {
    private static final EntityDataAccessor<String> WOOD_TYPE = SynchedEntityData.defineId(ToyTetheredLog.class, EntityDataSerializers.STRING);
    public int animationTick;
    public boolean animationPlaying;
    public float animationX;
    public float animationZ;

    public ToyTetheredLog(EntityType<ToyTetheredLog> type, Level level) {
        super(type, level, 30, SoundEvents.WOOD_HIT);
    }

    @Override
    protected void defineSynchedData() {
        entityData.define(WOOD_TYPE, WoodType.OAK.name());
    }

    public void startAnimation(float animationX, float animationZ) {
        animationTick = 0;
        animationPlaying = true;
        this.animationX = animationX;
        this.animationZ = animationZ;
    }

    @Override
    public boolean hurt(DamageSource source, float amount) {
        boolean hurt = super.hurt(source, amount);
        if (!hurt && source.getDirectEntity() != null && !level().isClientSide) {
            Vec3 direction = source.getDirectEntity().position().vectorTo(position());
            double dist = direction.horizontalDistance();
            AABB area = getBoundingBox().inflate(16, 16, 16);
            List<ServerPlayer> players = ((ServerLevel)level()).getPlayers(serverPlayer -> area.contains(serverPlayer.getX(), serverPlayer.getY(), serverPlayer.getZ()));
            MessageHandler.SYNC_CHANNEL.sendToPlayers(players, new S2CSyncToyAnimationMessage(getId(), (float) (direction.z / dist), (float) (direction.x / dist)));
        }
        return hurt;
    }

    @Override
    public void tick() {
        super.tick();
        setDeltaMovement(0, 0, 0);
        if (!isAttachedToBlock()) {
            if (!level().isClientSide) {
                Block.popResource(level(), blockPosition(), getPickResult());
            }
            discard();
            playSound(attackNoise, 1, getVoicePitch());
        }
        if (level().isClientSide) {
            if (animationPlaying) {
                animationTick++;
            }
            if (animationTick >= 40) {
                animationPlaying = false;
                animationTick = 0;
            }
        }
    }

    private boolean isAttachedToBlock() {
        return !level().isEmptyBlock(blockPosition().above(2));
    }

    @Nullable
    @Override
    public ItemStack getPickResult() {
        return new ItemStack(ModItems.TOY_TETHERED_LOGS.get(getWoodTypeName()).get());
    }

    public void setWoodType(String woodType) {
        entityData.set(WOOD_TYPE, woodType);
    }

    public String getWoodTypeName() {
        return entityData.get(WOOD_TYPE);
    }

    @Override
    public void addAdditionalSaveData(CompoundTag compound) {
        compound.putString("woodType", getWoodTypeName());
    }

    @Override
    public void readAdditionalSaveData(CompoundTag compound) {
        String woodType = compound.getString("woodType");
        if (woodType.isBlank()) {
            woodType = WoodType.OAK.name();
        }
        setWoodType(woodType);
    }

    @Override
    public float getVoicePitch() {
        return super.getVoicePitch() * 0.2f;
    }
}
