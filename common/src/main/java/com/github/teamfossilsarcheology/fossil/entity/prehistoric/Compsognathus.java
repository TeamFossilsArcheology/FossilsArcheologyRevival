package com.github.teamfossilsarcheology.fossil.entity.prehistoric;

import com.github.teamfossilsarcheology.fossil.entity.ai.DinoLeapAtTargetGoal;
import com.github.teamfossilsarcheology.fossil.entity.animation.AnimationCategory;
import com.github.teamfossilsarcheology.fossil.entity.animation.AnimationInfo;
import com.github.teamfossilsarcheology.fossil.entity.prehistoric.base.PrehistoricEntityInfo;
import com.github.teamfossilsarcheology.fossil.entity.prehistoric.base.PrehistoricLeaping;
import com.github.teamfossilsarcheology.fossil.entity.util.Util;
import com.github.teamfossilsarcheology.fossil.sounds.ModSounds;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.goal.RestrictSunGoal;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

public class Compsognathus extends PrehistoricLeaping {
    public static final String ATTACK = "animation.compsognathus.attack";

    public Compsognathus(EntityType<Compsognathus> entityType, Level level) {
        super(entityType, level, false);
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        goalSelector.addGoal(Util.ATTACK, new DinoLeapAtTargetGoal(this));
        goalSelector.addGoal(Util.NEEDS + 4, new RestrictSunGoal(this));
    }

    @Override
    public PrehistoricEntityInfo info() {
        return PrehistoricEntityInfo.COMPSOGNATHUS;
    }

    @Override
    public Item getOrderItem() {
        return Items.BONE;
    }

    @Override
    public AnimationInfo getAnimation(AnimationCategory category) {
        if (category == AnimationCategory.WALK) {
            return getAnimation(AnimationCategory.SPRINT);//Walk to slow
        }
        return super.getAnimation(category);
    }

    @Override
    public String getLandAnimationName() {
        return ATTACK;
    }

    @Override
    public String getLeapStartAnimationName() {
        return ATTACK;
    }

    @Override
    public String getLeapAttackAnimationName() {
        return ATTACK;
    }

    @Override
    public boolean hasLeapAnimation() {
        return false;
    }

    @Nullable
    @Override
    protected SoundEvent getAmbientSound() {
        return ModSounds.COMPSOGNATHUS_AMBIENT.get();
    }

    @Nullable
    @Override
    protected SoundEvent getHurtSound(DamageSource damageSource) {
        return ModSounds.COMPSOGNATHUS_HURT.get();
    }

    @Nullable
    @Override
    protected SoundEvent getDeathSound() {
        return ModSounds.COMPSOGNATHUS_DEATH.get();
    }
}