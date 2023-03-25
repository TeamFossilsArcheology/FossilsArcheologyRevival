package com.fossil.fossil.entity;

import com.fossil.fossil.entity.prehistoric.base.Prehistoric;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;

import java.util.Collections;

public abstract class ToyBase extends LivingEntity {

    protected final int moodBonus;
    protected final SoundEvent attackNoise;

    protected ToyBase(EntityType<? extends ToyBase> type, Level level, int moodBonus, SoundEvent attackNoise) {
        super(type, level);
        this.moodBonus = moodBonus;
        this.attackNoise = attackNoise;
    }

    public static AttributeSupplier.Builder createAttributes() {
        return createLivingAttributes();
    }

    @Override
    public boolean hurt(DamageSource source, float amount) {
        if (source.getEntity() != null) {
            if (source.isCreativePlayer()) {
                discard();
                return true;
            }
            if (source.getEntity() instanceof Player) {
                if (!((Player)source.getEntity()).getAbilities().mayBuild) {
                    return false;
                }
                Block.popResource(level, blockPosition(), getPickResult());
                discard();
                playSound(attackNoise, getSoundVolume(), getVoicePitch());
                return true;
            } else if (source.getEntity() instanceof Prehistoric prehistoric) {
                prehistoric.useToy(moodBonus);
                playSound(attackNoise, getSoundVolume(), getVoicePitch());
                return false;
            }
        }
        return source != DamageSource.OUT_OF_WORLD;
    }

    @Override
    public Iterable<ItemStack> getArmorSlots() {
        return Collections.emptyList();
    }

    @Override
    public ItemStack getItemBySlot(EquipmentSlot slot) {
        return ItemStack.EMPTY;
    }

    @Override
    public void setItemSlot(EquipmentSlot slot, ItemStack stack) {

    }

    @Override
    public HumanoidArm getMainArm() {
        return HumanoidArm.RIGHT;
    }
}
