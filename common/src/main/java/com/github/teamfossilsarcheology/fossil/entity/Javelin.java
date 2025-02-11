package com.github.teamfossilsarcheology.fossil.entity;

import com.github.teamfossilsarcheology.fossil.item.ModItems;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.Tiers;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;

public class Javelin extends AbstractArrow {
    private static final EntityDataAccessor<Integer> TIER_ID = SynchedEntityData.defineId(Javelin.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Boolean> ANCIENT = SynchedEntityData.defineId(Javelin.class, EntityDataSerializers.BOOLEAN);
    private int itemDamage;
    private boolean lightning;

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
        setPierceLevel((byte) 1);
        setBaseDamage(getDamage(tier, ancient));
    }

    private static double getDamage(Tier tier, boolean ancient) {
        if (ancient) {
            return 5;
        } else if (tier instanceof Tiers tiers) {
            switch (tiers) {
                case WOOD -> {
                    return 2;
                }
                case STONE -> {
                    return 2.5;
                }
                case GOLD -> {
                    return 3.5;
                }
                case IRON -> {
                    return 3;
                }
                case DIAMOND -> {
                    return 4;
                }
            }
        }
        return 2;
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        entityData.define(ANCIENT, false);
        entityData.define(TIER_ID, 0);
    }

    @Override
    public void tick() {
        if (isAncient() && inGround && !lightning) {
            if (random.nextInt(100) < 30) {
                if (level instanceof ServerLevel) {
                    LightningBolt lightningBolt = EntityType.LIGHTNING_BOLT.create(level);
                    lightningBolt.moveTo(Vec3.atBottomCenterOf(blockPosition()));
                    lightningBolt.setCause(getOwner() instanceof ServerPlayer ? (ServerPlayer) getOwner() : null);
                    level.addFreshEntity(lightningBolt);
                }
            }
            lightning = true;
        }
        super.tick();
    }

    @Override
    protected void onHitBlock(BlockHitResult result) {
        super.onHitBlock(result);
        setPierceLevel((byte) 1);
    }

    @Override
    protected void tickDespawn() {

    }

    public Tier getTier() {
        if (!entityData.get(ANCIENT)) {
            return Tiers.values()[entityData.get(TIER_ID)];
        }
        return Tiers.WOOD;
    }

    public void setTier(Tiers tier) {
        entityData.set(TIER_ID, tier.ordinal());
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
