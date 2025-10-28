package com.github.teamfossilsarcheology.fossil.entity;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collections;
import java.util.UUID;

public class LaserPointEntity extends LivingEntity {
    @Nullable
    private UUID ownerUUID;
    private int lastUpdateTick = 0;

    public LaserPointEntity(EntityType<? extends LivingEntity> entityType, Level level) {
        super(entityType, level);
        this.noPhysics = true;
        this.setHealth(this.getMaxHealth());
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes().add(Attributes.MAX_HEALTH, 1.0D);
    }

    @Override
    public void tick() {
        super.tick();
        if (!level().isClientSide()) {
            // making sure the entity didn't get abandoned somehow (maybe it could happen by the player dropping the laser pointer?)
            if (getOwner() == null || (this.tickCount - lastUpdateTick) > 10) {
                this.discard();
            }
        }
    }

    public void updatePosition(double x, double y, double z) {
        this.setPos(x, y, z);
        this.lastUpdateTick = this.tickCount;
    }

    public void setOwner(@Nullable Player player) {
        this.ownerUUID = player == null ? null : player.getUUID();
    }

    @Nullable
    public Player getOwner() {
        if (ownerUUID != null && level() instanceof ServerLevel serverLevel) {
            return serverLevel.getServer().getPlayerList().getPlayer(this.ownerUUID);
        }
        return null;
    }

    @Override
    public boolean isInvulnerableTo(DamageSource source) {
        return !source.is(DamageTypeTags.BYPASSES_INVULNERABILITY);
    }

    @Override
    public boolean hurt(DamageSource source, float amount) {
        return false;
    }

    @Override
    public @NotNull Iterable<ItemStack> getArmorSlots() {
        return Collections.emptyList();
    }

    @Override
    public @NotNull ItemStack getItemBySlot(EquipmentSlot slot) {
        return ItemStack.EMPTY;
    }

    @Override
    public void setItemSlot(EquipmentSlot slot, ItemStack stack) {

    }

    @Override
    public boolean fireImmune() {
        return true;
    }

    @Override
    public boolean isPickable() {
        return true;
    }

    @Override
    public @NotNull HumanoidArm getMainArm() {
        return HumanoidArm.RIGHT;
    }


    @Override
    public void readAdditionalSaveData(CompoundTag compound) {
        if (compound.hasUUID("Owner")) {
            this.ownerUUID = compound.getUUID("Owner");
        }
    }

    @Override
    public void addAdditionalSaveData(CompoundTag compound) {
        if (this.ownerUUID != null) {
            compound.putUUID("Owner", this.ownerUUID);
        }
    }
}
