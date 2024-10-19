package com.github.teamfossilsarcheology.fossil.entity.prehistoric.swimming;

import com.github.teamfossilsarcheology.fossil.entity.ai.DelayedAttackGoal;
import com.github.teamfossilsarcheology.fossil.entity.ai.DinoHurtByTargetGoal;
import com.github.teamfossilsarcheology.fossil.entity.ai.DinoOwnerHurtByTargetGoal;
import com.github.teamfossilsarcheology.fossil.entity.ai.DinoOwnerHurtTargetGoal;
import com.github.teamfossilsarcheology.fossil.entity.prehistoric.base.PrehistoricEntityInfo;
import com.github.teamfossilsarcheology.fossil.entity.prehistoric.base.PrehistoricSwimmingBucketable;
import com.github.teamfossilsarcheology.fossil.entity.util.Util;
import com.github.teamfossilsarcheology.fossil.sounds.ModSounds;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib3.core.builder.Animation;

public class Megalograptus extends PrehistoricSwimmingBucketable {
    public static final String ANIMATIONS = "megalograptus.animation.json";
    public static final String ATTACK = "animation.megalograptus.attack1";
    public static final String EAT = "animation.megalograptus.eat";
    public static final String FALL = "animation.megalograptus.jump/fall";
    public static final String IDLE = "animation.megalograptus.idle/sleep/sit";
    public static final String SLEEP = "animation.megalograptus.idle/sleep/sit";
    public static final String SWIM = "animation.megalograptus.swim";
    public static final String WALK = "animation.megalograptus.walk";


    public Megalograptus(EntityType<Megalograptus> entityType, Level level) {
        super(entityType, level);
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        goalSelector.addGoal(Util.ATTACK, new DelayedAttackGoal(this, 1, false));
        targetSelector.addGoal(1, new DinoOwnerHurtByTargetGoal(this));
        targetSelector.addGoal(2, new DinoOwnerHurtTargetGoal(this));
        targetSelector.addGoal(3, new DinoHurtByTargetGoal(this));
    }
    @Override
    public PrehistoricEntityInfo info() {
        return PrehistoricEntityInfo.MEGALOGRAPTUS;
    }

    @Override
    public @NotNull MobType getMobType() {
        return MobType.ARTHROPOD;
    }

    @Override
    public Item getOrderItem() {
        return Items.NAUTILUS_SHELL;
    }

    @Override
    public @NotNull Animation nextAttackAnimation() {
        return getAllAnimations().get(ATTACK);
    }

    @Override
    public @NotNull Animation nextBeachedAnimation() {
        return nextIdleAnimation();
    }

    @Override
    public @NotNull Animation nextEatingAnimation() {
        return getAllAnimations().get(EAT);
    }

    @Override
    public @NotNull Animation nextIdleAnimation() {
        return getAllAnimations().get(IDLE);
    }
    
    @Override
    public @NotNull Animation nextSittingAnimation() {
        return getAllAnimations().get(SLEEP);
    }

    @Override
    public @NotNull Animation nextSleepingAnimation() {
        return getAllAnimations().get(SLEEP);
    }

    @Override
    public @NotNull Animation nextWalkingAnimation() {
        if (isInWater()) {
            return getAllAnimations().get(SWIM);
        }
        return getAllAnimations().get(WALK);
    }

    @Override
    public @NotNull Animation nextSprintingAnimation() {
        if (isInWater()) {
            return getAllAnimations().get(SWIM);
        }
        return getAllAnimations().get(IDLE);
    }

    @Nullable
    @Override
    protected SoundEvent getAmbientSound() {
        return ModSounds.MEGALOGRAPTUS_AMBIENT.get();
    }

    @Nullable
    @Override
    protected SoundEvent getHurtSound(DamageSource damageSource) {
        return ModSounds.MEGALOGRAPTUS_HURT.get();
    }

    @Nullable
    @Override
    protected SoundEvent getDeathSound() {
        return ModSounds.MEGALOGRAPTUS_DEATH.get();
    }
}