package com.github.teamfossilsarcheology.fossil.entity.prehistoric;

import com.github.teamfossilsarcheology.fossil.FossilMod;
import com.github.teamfossilsarcheology.fossil.entity.ai.FleeBattleGoal;
import com.github.teamfossilsarcheology.fossil.entity.prehistoric.base.Prehistoric;
import com.github.teamfossilsarcheology.fossil.entity.prehistoric.base.PrehistoricEntityInfo;
import com.github.teamfossilsarcheology.fossil.entity.prehistoric.base.PrehistoricShearable;
import com.github.teamfossilsarcheology.fossil.entity.util.Util;
import com.github.teamfossilsarcheology.fossil.sounds.ModSounds;
import com.github.teamfossilsarcheology.fossil.util.Gender;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class Therizinosaurus extends Prehistoric implements PrehistoricShearable {
    //TODO: Eat1 and Eat2 are for blocks on head height. Eat3 is for blocks on ground height
    private static final EntityDataAccessor<Boolean> SHEARED = SynchedEntityData.defineId(Therizinosaurus.class, EntityDataSerializers.BOOLEAN);
    private int woolRegenTicks;

    public Therizinosaurus(EntityType<Therizinosaurus> entityType, Level level) {
        super(entityType, level);
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        goalSelector.addGoal(Util.IMMOBILE + 3, new FleeBattleGoal(this, 1.0D));
    }

    @Override
    public void refreshTexturePath() {
        if (!level().isClientSide) {
            return;
        }
        StringBuilder builder = new StringBuilder();
        builder.append("textures/entity/therizinosaurus/therizinosaurus");
        if (isBaby()) builder.append("_baby");
        if (isTeen()) builder.append("_teen");
        if (isAdult()) {
            if (getGender() == Gender.MALE) {
                builder.append("_male");
            } else {
                builder.append("_female");
            }
        }
        if (isSleeping()) builder.append("_sleeping");
        //if (isSheared()) builder.append("_shaved");
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
        return PrehistoricEntityInfo.THERIZINOSAURUS;
    }

    @Override
    public void feed(int foodAmount) {
        super.feed(foodAmount);
        if (isSheared()) {
            woolRegenTicks += foodAmount;
            if (woolRegenTicks >= 100) {
                setSheared(false);
                woolRegenTicks = 0;
            }
        }
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
        //Disabled because of missing texture
        //level.playSound(null, this, SoundEvents.SHEEP_SHEAR, source, 1, 1);
        setSheared(true);
        int maxWool = 1 + random.nextInt(10);
        for (int i = 0; i < maxWool; i++) {
            //ItemEntity itemEntity = spawnAtLocation(ModItems.THERIZINOSAURUS_DOWN.get(), 1);
            //if (itemEntity == null) continue;
            //itemEntity.setDeltaMovement(itemEntity.getDeltaMovement().add((random.nextFloat() - random.nextFloat()) * 0.1, random.nextFloat() * 0.05, (random.nextFloat() - random.nextFloat()) * 0.1));
        }
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
    public Item getOrderItem() {
        return Items.STICK;
    }

    @Nullable
    @Override
    protected SoundEvent getAmbientSound() {
        return ModSounds.THERIZINOSAURUS_AMBIENT.get();
    }

    @Nullable
    @Override
    protected SoundEvent getHurtSound(DamageSource damageSource) {
        return ModSounds.THERIZINOSAURUS_HURT.get();
    }

    @Nullable
    @Override
    protected SoundEvent getDeathSound() {
        return ModSounds.THERIZINOSAURUS_DEATH.get();
    }
}