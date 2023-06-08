package com.fossil.fossil.entity.prehistoric.base;


import com.mojang.logging.LogUtils;
import dev.architectury.extensions.network.EntitySpawnExtension;
import dev.architectury.networking.NetworkManager;
import net.minecraft.core.Holder;
import net.minecraft.core.particles.ItemParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.biome.Biome;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;

import java.util.Collections;

public class DinosaurEgg extends LivingEntity implements EntitySpawnExtension {
    private static final EntityDataAccessor<Integer> HATCHING_TIME = SynchedEntityData.defineId(DinosaurEgg.class, EntityDataSerializers.INT);
    private static final Logger LOGGER = LogUtils.getLogger();
    public static final int TOTAL_HATCHING_TIME = 3000;
    private static final TranslatableComponent EGG_HATCHED = new TranslatableComponent("entity.fossil.dinosaur_egg.hatched");

    public float scale;
    private PrehistoricEntityType prehistoricEntityType;

    public DinosaurEgg(EntityType<? extends DinosaurEgg> type, Level level) {
        super(type, level);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes().add(Attributes.MAX_HEALTH, 2D);
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        entityData.define(HATCHING_TIME, 0);
    }

    @Override
    protected @NotNull MovementEmission getMovementEmission() {
        return MovementEmission.NONE;
    }

    @Override
    public boolean isPushable() {
        return true;
    }

    @Override
    public boolean canBeCollidedWith() {
        return isAlive();
    }

    @Override
    public void tick() {
        super.tick();
        int currentHatchingTime = getHatchingTime();
        if (isTooCold() || isInWater()) {
            if (currentHatchingTime > 0) {
                setHatchingTime(currentHatchingTime - 1);
            }
        } else {
            setHatchingTime(currentHatchingTime + 1);
        }
        if (getHatchingTime() >= TOTAL_HATCHING_TIME && !level.isClientSide) {
            Player player = level.getNearestPlayer(this, 16);
            hatchEgg(level, getX(), getY(), getZ(), (ServerPlayer) player, prehistoricEntityType, true);
            for (int i = 0; i < 4; i++) {
                double x = getX() + (random.nextFloat() - 0.5) * getBbWidth();
                double y = getBoundingBox().minY + 0.1;
                double z = getZ() + (random.nextFloat() - 0.5) * getBbWidth();
                double motionX = random.nextFloat() - 0.5;
                double motionZ = random.nextFloat() - 0.5;
                level.addParticle(new ItemParticleOption(ParticleTypes.ITEM, new ItemStack(prehistoricEntityType.eggItem)), x, y, z, motionX, 0.5, motionZ);
            }
            kill();
        }
    }

    /**
     * @param level
     * @param x
     * @param y
     * @param z
     * @param player
     * @param type
     * @param hatchMessage
     */
    public static Entity hatchEgg(Level level, double x, double y, double z, @Nullable ServerPlayer player, PrehistoricEntityType type, boolean hatchMessage) {
        Entity entity = type.entity.create(level);
        if (entity instanceof Prehistoric prehistoric) {
            if (prehistoric.isTameable() && player != null && prehistoric.aiTameType() == PrehistoricEntityTypeAI.Taming.IMPRINTING) {
                prehistoric.tame(player);
                //TODO: First Hatch music
                prehistoric.setOwnerDisplayName(player.getDisplayName());
                if (hatchMessage) {
                    player.displayClientMessage(EGG_HATCHED, false);
                }
            }
            prehistoric.moveTo(Mth.floor(x), Mth.floor(y) + 1, Mth.floor(z), level.random.nextFloat() * 360, 0);
            prehistoric.finalizeSpawn((ServerLevelAccessor) level, level.getCurrentDifficultyAt(entity.blockPosition()),
                    MobSpawnType.BREEDING, new Prehistoric.PrehistoricGroupData(0), null);
            prehistoric.grow(0);
            level.addFreshEntity(entity);
        }
        return entity;
    }

    public boolean isTooCold() {
        Holder<Biome> biome = level.getBiome(blockPosition());
        level.updateSkyBrightness();
        float light = level.getBrightness(blockPosition());
        if (biome.value().warmEnoughToRain(blockPosition())) {
            return light < 0.5f;
        } else {
            return light < 0.75f;
        }
    }

    @Override
    public void kill() {
        this.remove(Entity.RemovalReason.KILLED);
    }

    @Override
    public boolean hurt(DamageSource source, float amount) {
        if (!level.isClientSide && amount > 0 && isAlive()) {
            ItemEntity itemEntity = new ItemEntity(level, getX() + 0.5, getY() + 1, getZ() + 0.5,
                    new ItemStack(getPrehistoricEntityType().eggItem), 0, 0.1, 0);
            level.addFreshEntity(itemEntity);
            level.playSound(null, blockPosition(), SoundEvents.ITEM_PICKUP, SoundSource.NEUTRAL, 0.2f,
                    ((random.nextFloat() - random.nextFloat()) * 0.7f + 1) * 2);
            kill();
        }
        return super.hurt(source, amount);//TODO: Needed?
    }

    @Override
    public @NotNull InteractionResult interact(Player player, InteractionHand hand) {
        if (player.getInventory().getSelected().isEmpty()) {
            if (!player.isCreative() && player.getInventory().add(new ItemStack(getPrehistoricEntityType().eggItem))) {
                level.playSound(null, blockPosition(), SoundEvents.ITEM_PICKUP, SoundSource.NEUTRAL, 0.2f,
                        ((random.nextFloat() - random.nextFloat()) * 0.7f + 1) * 2);
                kill();
                return InteractionResult.SUCCESS;
            }
        }
        return InteractionResult.PASS;
    }

    @Nullable
    @Override
    public ItemStack getPickResult() {
        return new ItemStack(prehistoricEntityType.eggItem);
    }

    @Override
    public void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        compound.putInt("HatchingTime", getHatchingTime());
        compound.putString("PrehistoricType", getPrehistoricEntityType().name());
    }

    @Override
    public void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        setHatchingTime(compound.getInt("HatchingTime"));
        setPrehistoricEntityType(PrehistoricEntityType.valueOf(compound.getString("PrehistoricType")));
    }

    public void setHatchingTime(int time) {
        entityData.set(HATCHING_TIME, time);
    }

    public int getHatchingTime() {
        return entityData.get(HATCHING_TIME);
    }

    public void setPrehistoricEntityType(PrehistoricEntityType prehistoricEntityType) {
        this.prehistoricEntityType = prehistoricEntityType;
    }

    public PrehistoricEntityType getPrehistoricEntityType() {
        return prehistoricEntityType;
    }

    @Override
    public @NotNull Packet<?> getAddEntityPacket() {
        return NetworkManager.createAddEntityPacket(this);
    }

    @Override
    public void saveAdditionalSpawnData(FriendlyByteBuf buf) {
        buf.writeUtf(getPrehistoricEntityType().name());
    }

    @Override
    public void loadAdditionalSpawnData(FriendlyByteBuf buf) {
        String type = buf.readUtf();
        try {
            setPrehistoricEntityType(PrehistoricEntityType.valueOf(type));
        } catch (IllegalArgumentException e) {
            LOGGER.error("Dinosaur egg " + stringUUID + " has invalid dinosaur specified: " + type);
        }
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
    public @NotNull HumanoidArm getMainArm() {
        return HumanoidArm.RIGHT;
    }
}
