package com.fossil.fossil.entity.prehistoric;

import com.fossil.fossil.entity.prehistoric.base.Prehistoric;
import com.fossil.fossil.entity.prehistoric.base.PrehistoricEntityType;
import com.fossil.fossil.entity.prehistoric.base.PrehistoricFish;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class Nautilus extends PrehistoricFish {
    private static final EntityDataAccessor<Boolean> IS_IN_SHELL = SynchedEntityData.defineId(Nautilus.class, EntityDataSerializers.BOOLEAN);
    private float shellProgress = 0;
    private float ticksToShell = 0;

    public Nautilus(EntityType<Nautilus> entityType, Level level) {
        super(entityType, level);
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        entityData.define(IS_IN_SHELL, false);
    }

    @Override
    public void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        compound.putBoolean("InShell", isInShell());
    }

    @Override
    public void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        setInShell(compound.getBoolean("InShell"));
    }

    @Override
    public @NotNull PrehistoricEntityType type() {
        return PrehistoricEntityType.NAUTILUS;
    }

    @Override
    public void tick() {
        super.tick();
        boolean inShell = isInShell();
        if (level.isClientSide) {
            if (inShell && shellProgress < 20) {
                shellProgress += 0.5;
            } else if (!inShell && shellProgress > 0) {
                shellProgress -= 0.5;
            }
        } else {
            if (ticksToShell > 0) {
                ticksToShell--;
            }
            List<LivingEntity> nearbyMobs = level.getEntitiesOfClass(LivingEntity.class, getBoundingBox().inflate(2, 2, 2), Nautilus::isAScaryAnimal);
            if (ticksToShell == 0 && (nearbyMobs.size() > 0 || !isInWater() && isOnGround())) {
                setInShell(true);
            } else if (isInShell()) {
                setInShell(false);
            }
        }
    }

    @Override
    public boolean hurt(DamageSource source, float amount) {
        if (amount > 0 && isInShell() && source.getEntity() != null) {
            playSound(SoundEvents.ITEM_BREAK, 1, random.nextFloat() + 0.8f);
            if (getVehicle() != null) {
                return super.hurt(source, amount);
            }
            return false;
        }
        if (!isInShell()) {
            setInShell(true);
        }
        return super.hurt(source, amount);
    }

    public boolean isInShell() {
        return entityData.get(IS_IN_SHELL);
    }

    public void setInShell(boolean inShell) {
        entityData.set(IS_IN_SHELL, inShell);
    }

    private static boolean isAScaryAnimal(Entity entity) {
        if (entity instanceof Player player && !player.isCreative()) {
            return true;
        }
        if (entity instanceof Prehistoric prehistoric) {
            return prehistoric.type().diet.getFearIndex() >= 2;
        }
        return entity.getBbWidth() >= 1.2;
    }
}