package com.fossil.fossil.entity;

import com.fossil.fossil.item.ModItems;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.Tiers;
import net.minecraft.world.level.Level;

public class Javelin extends AbstractArrow {
    private static final EntityDataAccessor<Integer> TIER_ID = SynchedEntityData.defineId(Javelin.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Boolean> ANCIENT = SynchedEntityData.defineId(Javelin.class, EntityDataSerializers.BOOLEAN);
    private int itemDamage;

    public Javelin(EntityType<Javelin> type, Level level) {
        super(type, level);
    }

    public Javelin(Level level, LivingEntity shooter, Tier tier, boolean ancient, int itemDamage) {
        super(ModEntities.JAVELIN.get(), shooter, level);
        this.itemDamage = itemDamage;
        if (tier instanceof Tiers tiers) {
            setTier(tiers);
        }
        entityData.set(ANCIENT, ancient);
        setBaseDamage(getDamage(tier, ancient));
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        entityData.define(ANCIENT, false);
        entityData.define(TIER_ID, 0);
    }

    private static double getDamage(Tier tier, boolean ancient) {
        if (ancient) {
            return 10;
        } else if (tier instanceof Tiers tiers) {
            switch (tiers) {
                case WOOD -> {
                    return 2;
                }
                case STONE -> {
                    return 3;
                }
                case GOLD -> {
                    return 5;
                }
                case IRON -> {
                    return 4;
                }
                case DIAMOND -> {
                    return 7;
                }
            }
        }
        return 2;
    }

    @Override
    public void tick() {
        super.tick();
        inGroundTime = 0;
    }

    public void setTier(Tiers tier) {
        entityData.set(TIER_ID, tier.ordinal());
    }

    public Tier getTier() {
        if (!entityData.get(ANCIENT)) {
            return Tiers.values()[entityData.get(TIER_ID)];
        }
        return Tiers.WOOD;
    }

    public boolean isAncient() {
        return entityData.get(ANCIENT);
    }

    @Override
    public void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        compound.putBoolean("ancient", isAncient());
        if (getTier() instanceof Tiers tiers) {
            compound.putInt("Tier", tiers.ordinal());
        }
        compound.putInt("Damage", itemDamage);
    }

    @Override
    public void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        entityData.set(ANCIENT, compound.getBoolean("ancient"));
        if (!isAncient()) {
            setTier(Tiers.values()[compound.getInt("Tier")]);
        }
        itemDamage = compound.getInt("Damage");
    }

    @Override
    protected ItemStack getPickupItem() {
        if (isAncient()) {
            ItemStack stack = new ItemStack(ModItems.ANCIENT_JAVELIN.get());
            stack.setDamageValue(ModItems.ANCIENT_JAVELIN.get().getMaxDamage() - itemDamage);
            return stack;
        } else if (getTier() instanceof Tiers tiers) {
            switch (tiers) {
                case WOOD -> {
                    ItemStack stack = new ItemStack(ModItems.WOODEN_JAVELIN.get());
                    stack.setDamageValue(ModItems.WOODEN_JAVELIN.get().getMaxDamage() - itemDamage);
                    return stack;
                }
                case STONE -> {
                    ItemStack stack = new ItemStack(ModItems.STONE_JAVELIN.get());
                    stack.setDamageValue(ModItems.STONE_JAVELIN.get().getMaxDamage() - itemDamage);
                    return stack;
                }
                case GOLD -> {
                    ItemStack stack = new ItemStack(ModItems.GOLD_JAVELIN.get());
                    stack.setDamageValue(ModItems.GOLD_JAVELIN.get().getMaxDamage() - itemDamage);
                    return stack;
                }
                case IRON -> {
                    ItemStack stack = new ItemStack(ModItems.IRON_JAVELIN.get());
                    stack.setDamageValue(ModItems.IRON_JAVELIN.get().getMaxDamage() - itemDamage);
                    return stack;
                }
                case DIAMOND -> {
                    ItemStack stack = new ItemStack(ModItems.DIAMOND_JAVELIN.get());
                    stack.setDamageValue(ModItems.DIAMOND_JAVELIN.get().getMaxDamage() - itemDamage);
                    return stack;
                }
            }
        }
        return ItemStack.EMPTY;
    }
}
