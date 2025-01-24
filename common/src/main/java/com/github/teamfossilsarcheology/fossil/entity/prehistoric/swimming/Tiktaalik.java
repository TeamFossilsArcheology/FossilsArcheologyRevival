package com.github.teamfossilsarcheology.fossil.entity.prehistoric.swimming;

import com.github.teamfossilsarcheology.fossil.entity.ai.DinoHurtByTargetGoal;
import com.github.teamfossilsarcheology.fossil.entity.ai.DinoOwnerHurtByTargetGoal;
import com.github.teamfossilsarcheology.fossil.entity.ai.DinoOwnerHurtTargetGoal;
import com.github.teamfossilsarcheology.fossil.entity.ai.LeaveWaterGoal;
import com.github.teamfossilsarcheology.fossil.entity.prehistoric.base.PrehistoricEntityInfo;
import com.github.teamfossilsarcheology.fossil.entity.prehistoric.base.PrehistoricSwimmingBucketable;
import com.github.teamfossilsarcheology.fossil.entity.util.Util;
import com.github.teamfossilsarcheology.fossil.item.ModItems;
import com.github.teamfossilsarcheology.fossil.sounds.ModSounds;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

public class Tiktaalik extends PrehistoricSwimmingBucketable {

    public Tiktaalik(EntityType<Tiktaalik> entityType, Level level) {
        super(entityType, level);
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        goalSelector.addGoal(Util.WANDER + 1, new LeaveWaterGoal<>(this, 1));
        targetSelector.addGoal(1, new DinoOwnerHurtByTargetGoal(this));
        targetSelector.addGoal(2, new DinoOwnerHurtTargetGoal(this));
        targetSelector.addGoal(3, new DinoHurtByTargetGoal(this));
    }

    @Override
    public PrehistoricEntityInfo info() {
        return PrehistoricEntityInfo.TIKTAALIK;
    }

    @Override
    public Item getOrderItem() {
        return ModItems.MAGIC_CONCH.get();
    }

    @Nullable
    @Override
    protected SoundEvent getAmbientSound() {
        return isInWater() ? ModSounds.TIKTAALIK_AMBIENT.get() : ModSounds.HENODUS_AMBIENT.get();
    }

    @Nullable
    @Override
    protected SoundEvent getHurtSound(DamageSource damageSource) {
        return ModSounds.TIKTAALIK_HURT.get();
    }

    @Nullable
    @Override
    protected SoundEvent getDeathSound() {
        return ModSounds.TIKTAALIK_DEATH.get();
    }
}