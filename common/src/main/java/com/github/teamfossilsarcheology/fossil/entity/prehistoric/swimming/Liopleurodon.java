package com.github.teamfossilsarcheology.fossil.entity.prehistoric.swimming;

import com.github.teamfossilsarcheology.fossil.entity.ai.DinoHurtByTargetGoal;
import com.github.teamfossilsarcheology.fossil.entity.animation.AnimationInfo;
import com.github.teamfossilsarcheology.fossil.entity.prehistoric.base.PrehistoricEntityInfo;
import com.github.teamfossilsarcheology.fossil.entity.prehistoric.base.PrehistoricSwimming;
import com.github.teamfossilsarcheology.fossil.item.ModItems;
import com.github.teamfossilsarcheology.fossil.sounds.ModSounds;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class Liopleurodon extends PrehistoricSwimming {
    public static final String GRAB = "animation.liopleurodon.grab";
    public static final String IDLE1 = "animation.liopleurodon.idle/sleep";
    public static final String IDLE2 = "animation.liopleurodon.idle";

    public Liopleurodon(EntityType<Liopleurodon> entityType, Level level) {
        super(entityType, level);
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        targetSelector.addGoal(3, new DinoHurtByTargetGoal(this));
    }

    @Override
    public PrehistoricEntityInfo info() {
        return PrehistoricEntityInfo.LIOPLEURODON;
    }

    @Override
    public Item getOrderItem() {
        return ModItems.MAGIC_CONCH.get();
    }

    @Override
    public @NotNull AnimationInfo nextGrabbingAnimation() {
        return getAllAnimations().get(GRAB);
    }

    @Override
    public @NotNull AnimationInfo nextIdleAnimation() {
        return random.nextFloat() > 0.1 ? getAllAnimations().get(IDLE1) : getAllAnimations().get(IDLE2);
    }

    @Nullable
    @Override
    protected SoundEvent getAmbientSound() {
        return isInWater() ? ModSounds.LIOPLEURODON_AMBIENT_INSIDE.get() : ModSounds.LIOPLEURODON_AMBIENT_OUTSIDE.get();
    }

    @Nullable
    @Override
    protected SoundEvent getHurtSound(DamageSource damageSource) {
        return ModSounds.LIOPLEURODON_HURT.get();
    }

    @Nullable
    @Override
    protected SoundEvent getDeathSound() {
        return ModSounds.LIOPLEURODON_DEATH.get();
    }
}