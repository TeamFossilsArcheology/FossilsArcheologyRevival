package com.github.teamfossilsarcheology.fossil.entity.prehistoric;

import com.github.teamfossilsarcheology.fossil.FossilMod;
import com.github.teamfossilsarcheology.fossil.entity.prehistoric.base.Prehistoric;
import com.github.teamfossilsarcheology.fossil.entity.prehistoric.base.PrehistoricEntityInfo;
import com.github.teamfossilsarcheology.fossil.item.ModItems;
import com.github.teamfossilsarcheology.fossil.sounds.ModSounds;
import com.github.teamfossilsarcheology.fossil.util.Gender;
import net.minecraft.ChatFormatting;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.Difficulty;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

public class Megalania extends Prehistoric {

    public Megalania(EntityType<Megalania> entityType, Level level) {
        super(entityType, level);
    }

    @Override
    public void refreshTexturePath() {
        if (!level.isClientSide) {
            return;
        }
        if ("Moby".equals(ChatFormatting.stripFormatting(getName().getString()))) {
            if (isSleeping()) {
                textureLocation = FossilMod.location("textures/entity/megalania/megalania_moby_sleeping.png");
            } else {
                textureLocation = FossilMod.location("textures/entity/megalania/megalania_moby.png");
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
        return PrehistoricEntityInfo.MEGALANIA;
    }

    @Override
    public Item getOrderItem() {
        return ModItems.SKULL_STICK.get();
    }

    @Override
    public boolean doHurtTarget(Entity target) {
        if (super.doHurtTarget(target)) {
            if (target instanceof LivingEntity living) {
                int i = 3;
                if (level.getDifficulty() == Difficulty.NORMAL) {
                    i = 10;
                } else if (level.getDifficulty() == Difficulty.HARD) {
                    i = 15;
                }
                living.addEffect(new MobEffectInstance(MobEffects.POISON, i * 20, 0), this);
                living.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, i * 60, 0), this);
            }
            return true;
        } else {
            return false;
        }
    }

    @Override
    public float getGenderedScale() {
        return getGender() == Gender.MALE ? 1.15f : super.getGenderedScale();
    }

    @Nullable
    @Override
    protected SoundEvent getAmbientSound() {
        return ModSounds.MEGALANIA_AMBIENT.get();
    }

    @Nullable
    @Override
    protected SoundEvent getHurtSound(DamageSource damageSource) {
        return ModSounds.MEGALANIA_HURT.get();
    }

    @Nullable
    @Override
    protected SoundEvent getDeathSound() {
        return ModSounds.MEGALANIA_DEATH.get();
    }
}