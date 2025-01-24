package com.github.teamfossilsarcheology.fossil.entity.prehistoric;

import com.github.teamfossilsarcheology.fossil.FossilMod;
import com.github.teamfossilsarcheology.fossil.entity.prehistoric.base.Prehistoric;
import com.github.teamfossilsarcheology.fossil.entity.prehistoric.base.PrehistoricEntityInfo;
import com.github.teamfossilsarcheology.fossil.sounds.ModSounds;
import net.minecraft.ChatFormatting;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

public class Psittacosaurus extends Prehistoric {

    public Psittacosaurus(EntityType<Psittacosaurus> type, Level level) {
        super(type, level);
    }

    @Override
    public void refreshTexturePath() {
        if (!level.isClientSide) {
            return;
        }
        if ("Bringer of Peace".equals(ChatFormatting.stripFormatting(getName().getString()))) {
            if (isSleeping()) {
                textureLocation = FossilMod.location("textures/entity/psittacosaurus/psittacosaurus_fire_sleeping.png");
            } else {
                textureLocation = FossilMod.location("textures/entity/psittacosaurus/psittacosaurus_fire.png");
            }
            return;
        }
        super.refreshTexturePath();
    }

    @Override
    public void onSyncedDataUpdated(EntityDataAccessor<?> key) {
        if (level.isClientSide && DATA_CUSTOM_NAME.equals(key)) {
            refreshTexturePath();
        }
        super.onSyncedDataUpdated(key);
    }

    @Override
    public PrehistoricEntityInfo info() {
        return PrehistoricEntityInfo.PSITTACOSAURUS;
    }

    @Override
    public Item getOrderItem() {
        return Items.STICK;
    }

    @Nullable
    @Override
    protected SoundEvent getAmbientSound() {
        return ModSounds.DRYOSAURUS_AMBIENT.get();
    }

    @Nullable
    @Override
    protected SoundEvent getHurtSound(DamageSource damageSource) {
        return ModSounds.DRYOSAURUS_HURT.get();
    }

    @Nullable
    @Override
    protected SoundEvent getDeathSound() {
        return ModSounds.DRYOSAURUS_DEATH.get();
    }
}