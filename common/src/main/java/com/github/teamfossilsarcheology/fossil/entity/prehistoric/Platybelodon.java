package com.github.teamfossilsarcheology.fossil.entity.prehistoric;

import com.github.teamfossilsarcheology.fossil.entity.ai.FleeBattleGoal;
import com.github.teamfossilsarcheology.fossil.entity.prehistoric.base.Prehistoric;
import com.github.teamfossilsarcheology.fossil.entity.prehistoric.base.PrehistoricEntityInfo;
import com.github.teamfossilsarcheology.fossil.entity.util.Util;
import com.github.teamfossilsarcheology.fossil.sounds.ModSounds;
import com.github.teamfossilsarcheology.fossil.util.Gender;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class Platybelodon extends Prehistoric {

    public Platybelodon(EntityType<Platybelodon> entityType, Level level) {
        super(entityType, level);
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        goalSelector.addGoal(Util.IMMOBILE + 3, new FleeBattleGoal(this, 1));
    }

    @Override
    public @NotNull EntityDimensions getDimensions(Pose poseIn) {
        if (poseIn == Pose.SLEEPING) {
            return super.getDimensions(poseIn).scale(1, 0.6f);
        }
        return super.getDimensions(poseIn);
    }

    @Override
    public PrehistoricEntityInfo info() {
        return PrehistoricEntityInfo.PLATYBELODON;
    }

    @Override
    public Item getOrderItem() {
        return Items.STICK;
    }

    @Override
    public float getGenderedScale() {
        return getGender() == Gender.MALE ? 1.1f : super.getGenderedScale();
    }

    @Nullable
    @Override
    protected SoundEvent getAmbientSound() {
        return ModSounds.PLATYBELODON_AMBIENT.get();
    }

    @Nullable
    @Override
    protected SoundEvent getHurtSound(DamageSource damageSource) {
        return ModSounds.PLATYBELODON_HURT.get();
    }

    @Nullable
    @Override
    protected SoundEvent getDeathSound() {
        return ModSounds.PLATYBELODON_DEATH.get();
    }
}