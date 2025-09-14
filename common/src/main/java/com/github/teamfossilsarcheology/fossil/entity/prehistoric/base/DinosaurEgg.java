package com.github.teamfossilsarcheology.fossil.entity.prehistoric.base;


import com.github.teamfossilsarcheology.fossil.FossilMod;
import com.github.teamfossilsarcheology.fossil.advancements.ModTriggers;
import com.github.teamfossilsarcheology.fossil.capabilities.ModCapabilities;
import com.github.teamfossilsarcheology.fossil.network.MessageHandler;
import com.github.teamfossilsarcheology.fossil.network.S2CMusicMessage;
import com.github.teamfossilsarcheology.fossil.sounds.ModSounds;
import com.mojang.logging.LogUtils;
import dev.architectury.extensions.network.EntitySpawnExtension;
import dev.architectury.networking.NetworkManager;
import net.minecraft.core.Holder;
import net.minecraft.core.NonNullList;
import net.minecraft.core.particles.ItemParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
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

import java.util.List;

public class DinosaurEgg extends LivingEntity implements EntitySpawnExtension {
    public static final int TOTAL_HATCHING_TIME = 3000;
    public static final ResourceLocation GOLDEN_EGG_ADV = FossilMod.location("fossil/all_eggs");
    private static final EntityDataAccessor<Integer> HATCHING_TIME = SynchedEntityData.defineId(DinosaurEgg.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Boolean> GOLDEN_EGG = SynchedEntityData.defineId(DinosaurEgg.class, EntityDataSerializers.BOOLEAN);
    private static final Logger LOGGER = LogUtils.getLogger();
    private static final Component EGG_HATCHED = Component.translatable("entity.fossil.dinosaur_egg.hatched");
    private static final List<ItemStack> ARMOR = NonNullList.withSize(1, ItemStack.EMPTY);

    private float scaleOverride = -1;

    private PrehistoricEntityInfo prehistoricEntityInfo = PrehistoricEntityInfo.TRICERATOPS;

    public DinosaurEgg(EntityType<DinosaurEgg> type, Level level) {
        super(type, level);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes().add(Attributes.MAX_HEALTH, 2);
    }

    public static Entity hatchEgg(Level level, double x, double y, double z, @Nullable ServerPlayer player, PrehistoricEntityInfo info, boolean hatchMessage) {
        Entity entity = info.entityType().create(level);
        if (entity instanceof Prehistoric prehistoric) {
            if (player != null) {
                ModTriggers.INCUBATE_EGG_TRIGGER.trigger(player, entity);
                if (prehistoric.aiTameType() == PrehistoricEntityInfoAI.Taming.IMPRINTING) {
                    prehistoric.tame(player);
                    if (!ModCapabilities.hasHatchedDinosaur(player)) {
                        MessageHandler.SYNC_CHANNEL.sendToPlayer(player, new S2CMusicMessage(ModSounds.MUSIC_FIRST_DINOSAUR.get()));
                        ModCapabilities.setHatchedDinosaur(player, true);
                    }
                    if (hatchMessage) {
                        player.displayClientMessage(EGG_HATCHED, false);
                    }
                }
            }
            prehistoric.finalizeSpawn((ServerLevelAccessor) level, level.getCurrentDifficultyAt(entity.blockPosition()),
                    MobSpawnType.BREEDING, new Prehistoric.PrehistoricGroupData(0), null);
            prehistoric.grow(0);
        }
        entity.moveTo(x, y, z, level.random.nextFloat() * 360, 0);
        level.addFreshEntity(entity);
        return entity;
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        entityData.define(HATCHING_TIME, 0);
        entityData.define(GOLDEN_EGG, false);
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
        if ((!isGoldenEgg() && isTooCold()) || isInWater()) {
            if (currentHatchingTime > 0) {
                setHatchingTime(currentHatchingTime - 1);
            }
        } else {
            setHatchingTime(currentHatchingTime + 1);
        }
        if (getHatchingTime() >= getTotalHatchingTime() && !level().isClientSide) {
            Player player = level().getNearestPlayer(this, 16);
            hatchEgg(level(), getX(), getY(), getZ(), (ServerPlayer) player, prehistoricEntityInfo, true);
            for (int i = 0; i < 4; i++) {
                double x = getX() + (random.nextFloat() - 0.5) * getBbWidth();
                double y = getBoundingBox().minY + 0.1;
                double z = getZ() + (random.nextFloat() - 0.5) * getBbWidth();
                double motionX = random.nextFloat() - 0.5;
                double motionZ = random.nextFloat() - 0.5;
                level().addParticle(new ItemParticleOption(ParticleTypes.ITEM, new ItemStack(prehistoricEntityInfo.eggItem)), x, y, z, motionX, 0.5, motionZ);
            }
            discard();
        }
    }

    public boolean isTooCold() {
        Holder<Biome> biome = level().getBiome(blockPosition());
        level().updateSkyBrightness();
        if (biome.value().warmEnoughToRain(blockPosition())) {
            return getLightLevelDependentMagicValue() < 0.5f;
        } else {
            return getLightLevelDependentMagicValue() < 0.75f;
        }
    }

    @Override
    public void kill() {
        this.remove(Entity.RemovalReason.KILLED);
    }

    @Override
    public boolean hurt(DamageSource source, float amount) {
        if (!level().isClientSide && amount > 0 && isAlive()) {
            ItemEntity itemEntity = new ItemEntity(level(), getX() + 0.5, getY() + 1, getZ() + 0.5,
                    new ItemStack(getPrehistoricEntityInfo().eggItem), 0, 0.1, 0);
            level().addFreshEntity(itemEntity);
            level().playSound(null, blockPosition(), SoundEvents.ITEM_PICKUP, SoundSource.NEUTRAL, 0.2f,
                    ((random.nextFloat() - random.nextFloat()) * 0.7f + 1) * 2);
            kill();
        }
        return super.hurt(source, amount);
    }

    @Override
    public @NotNull InteractionResult interact(Player player, InteractionHand hand) {
        if (player.getInventory().getSelected().isEmpty()) {
            if (!player.getAbilities().instabuild && player.getInventory().add(new ItemStack(getPrehistoricEntityInfo().eggItem))) {
                level().playSound(null, blockPosition(), SoundEvents.ITEM_PICKUP, SoundSource.NEUTRAL, 0.2f,
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
        return new ItemStack(prehistoricEntityInfo.eggItem);
    }

    @Override
    public void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        compound.putInt("HatchingTime", getHatchingTime());
        compound.putString("PrehistoricEntityInfo", getPrehistoricEntityInfo().name());
    }

    @Override
    public void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        setHatchingTime(compound.getInt("HatchingTime"));
        setPrehistoricEntityInfo(PrehistoricEntityInfo.valueOf(compound.getString("PrehistoricEntityInfo")));
    }

    public int getHatchingTime() {
        return entityData.get(HATCHING_TIME);
    }

    public void setHatchingTime(int time) {
        entityData.set(HATCHING_TIME, time);
    }

    public boolean isGoldenEgg() {
        return entityData.get(GOLDEN_EGG);
    }

    public void setGoldenEgg(boolean goldenEgg) {
        entityData.set(GOLDEN_EGG, goldenEgg);
    }

    public int getTotalHatchingTime() {
        return isGoldenEgg() ? (int) (TOTAL_HATCHING_TIME * 0.9) : TOTAL_HATCHING_TIME;
    }

    public PrehistoricEntityInfo getPrehistoricEntityInfo() {
        return prehistoricEntityInfo;
    }

    public void setPrehistoricEntityInfo(PrehistoricEntityInfo prehistoricEntityInfo) {
        this.prehistoricEntityInfo = prehistoricEntityInfo;
    }

    public void setScaleOverride(float scaleOverride) {
        this.scaleOverride = scaleOverride;
    }

    public float getScaleOverride() {
        return scaleOverride;
    }

    @Override
    public @NotNull Packet<ClientGamePacketListener> getAddEntityPacket() {
        return NetworkManager.createAddEntityPacket(this);
    }

    @Override
    public void saveAdditionalSpawnData(FriendlyByteBuf buf) {
        buf.writeEnum(getPrehistoricEntityInfo());
    }

    @Override
    public void loadAdditionalSpawnData(FriendlyByteBuf buf) {
        try {
            setPrehistoricEntityInfo(buf.readEnum(PrehistoricEntityInfo.class));
        } catch (IndexOutOfBoundsException e) {
            LOGGER.error("Dinosaur egg {} has invalid dinosaur specified: {}", stringUUID, e);
            setPrehistoricEntityInfo(PrehistoricEntityInfo.DODO);
        }
    }

    @Override
    public @NotNull Iterable<ItemStack> getArmorSlots() {
        //For compatibility with Ad Astra which can't handle an empty list here
        return ARMOR;
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
