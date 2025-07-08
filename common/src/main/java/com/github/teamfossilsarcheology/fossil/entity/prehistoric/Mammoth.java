package com.github.teamfossilsarcheology.fossil.entity.prehistoric;

import com.github.teamfossilsarcheology.fossil.FossilMod;
import com.github.teamfossilsarcheology.fossil.entity.ai.FleeBattleGoal;
import com.github.teamfossilsarcheology.fossil.entity.prehistoric.base.PrehistoricEntityInfo;
import com.github.teamfossilsarcheology.fossil.entity.prehistoric.base.PrehistoricFlocking;
import com.github.teamfossilsarcheology.fossil.entity.prehistoric.base.PrehistoricShearable;
import com.github.teamfossilsarcheology.fossil.entity.util.Util;
import com.github.teamfossilsarcheology.fossil.item.ModItems;
import com.github.teamfossilsarcheology.fossil.sounds.ModSounds;
import com.github.teamfossilsarcheology.fossil.util.Gender;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.BiomeTags;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.gameevent.GameEvent;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class Mammoth extends PrehistoricFlocking implements PrehistoricShearable {
    private static final EntityDataAccessor<Boolean> SHEARED = SynchedEntityData.defineId(Mammoth.class, EntityDataSerializers.BOOLEAN);

    private int woolRegenTicks;

    public Mammoth(EntityType<Mammoth> entityType, Level level) {
        super(entityType, level);
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        goalSelector.addGoal(Util.IMMOBILE + 3, new FleeBattleGoal(this, 1));
    }

    @Override
    public void refreshTexturePath() {
        if (!level().isClientSide) {
            return;
        }
        StringBuilder builder = new StringBuilder();
        builder.append("textures/entity/mammoth/mammoth");
        if (isBaby()) builder.append("_baby");
        if (isTeen() || isAdult()) {
            if (getGender() == Gender.MALE) {
                builder.append("_male");
            } else {
                builder.append("_female");
            }
        }
        if (isSleeping()) builder.append("_sleeping");
        if (isSheared()) builder.append("_shaved");
        builder.append(".png");
        textureLocation = FossilMod.location(builder.toString());
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        entityData.define(SHEARED, false);
    }

    @Override
    public void onSyncedDataUpdated(EntityDataAccessor<?> key) {
        if (SHEARED.equals(key)) {
            refreshTexturePath();
        }
        super.onSyncedDataUpdated(key);
    }

    @Override
    public void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        compound.putBoolean("Sheared", isSheared());
        compound.putInt("WoolRegenTicks", woolRegenTicks);
    }

    @Override
    public void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        setSheared(compound.getBoolean("Sheared"));
        woolRegenTicks = compound.getInt("WoolRegenTicks");
    }

    @Override
    public PrehistoricEntityInfo info() {
        return PrehistoricEntityInfo.MAMMOTH;
    }

    @Override
    protected int getMaxGroupSize() {
        return 7;
    }

    @Override
    public void aiStep() {
        boolean tooWarm = level().getBiome(blockPosition()).is(BiomeTags.SNOW_GOLEM_MELTS);
        if (tooWarm && getEffect(MobEffects.WEAKNESS) != null && !isSheared()) {
            addEffect(new MobEffectInstance(MobEffects.WEAKNESS, 60, 1));
        }
        super.aiStep();
    }

    @Override
    public void feed(int foodAmount) {
        super.feed(foodAmount);
        if (isSheared()) {
            woolRegenTicks += foodAmount;
            if (woolRegenTicks >= 150) {
                setSheared(false);
                woolRegenTicks = 0;
            }
        }
    }

    @Override
    public Item getOrderItem() {
        return Items.STICK;
    }

    @Override
    public double getPassengersRidingOffset() {
        double d = 0.35;
        return super.getPassengersRidingOffset() + d;
    }

    @Override
    public @NotNull InteractionResult mobInteract(Player player, InteractionHand hand) {
        ItemStack itemStack = player.getItemInHand(hand);
        if (itemStack.is(Items.SHEARS) && readyForShearing()) {
            shear(SoundSource.PLAYERS);
            gameEvent(GameEvent.SHEAR, player);
            if (!level().isClientSide) {
                itemStack.hurtAndBreak(1, player, player2 -> player2.broadcastBreakEvent(hand));
            }
            return InteractionResult.sidedSuccess(level().isClientSide);
        }
        return super.mobInteract(player, hand);
    }

    @Override
    public void shear(SoundSource source) {
        level().playSound(null, this, SoundEvents.SHEEP_SHEAR, source, 1, 1);
        setSheared(true);
        int maxWool = 1 + random.nextInt(20);
        for (int i = 0; i < maxWool; i++) {
            ItemEntity itemEntity = spawnAtLocation(ModItems.MAMMOTH_FUR.get(), 1);
            if (itemEntity == null) continue;
            itemEntity.setDeltaMovement(itemEntity.getDeltaMovement().add((random.nextFloat() - random.nextFloat()) * 0.1, random.nextFloat() * 0.05, (random.nextFloat() - random.nextFloat()) * 0.1));
        }
    }

    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor levelIn, DifficultyInstance difficultyIn, MobSpawnType reason, @Nullable SpawnGroupData spawnDataIn, @Nullable CompoundTag dataTag) {
        var data = super.finalizeSpawn(levelIn, difficultyIn, reason, spawnDataIn, dataTag);
        if (isBaby()) {
            setSheared(true);
        }
        return data;
    }

    @Override
    public boolean readyForShearing() {
        return !isSheared() && !isBaby();
    }

    @Override
    public boolean isSheared() {
        return entityData.get(SHEARED);
    }

    @Override
    public void setSheared(boolean sheared) {
        entityData.set(SHEARED, sheared);
    }

    @Override
    public float getGenderedScale() {
        return getGender() == Gender.MALE ? 1.15f : super.getGenderedScale();
    }

    @Override
    public float getTargetScale() {
        return 1.5f;
    }

    @Nullable
    @Override
    protected SoundEvent getAmbientSound() {
        return ModSounds.MAMMOTH_AMBIENT.get();
    }

    @Nullable
    @Override
    protected SoundEvent getHurtSound(DamageSource damageSource) {
        return ModSounds.MAMMOTH_HURT.get();
    }

    @Nullable
    @Override
    protected SoundEvent getDeathSound() {
        return ModSounds.MAMMOTH_DEATH.get();
    }
}