package com.github.teamfossilsarcheology.fossil.entity.prehistoric;

import com.github.teamfossilsarcheology.fossil.entity.ai.DelayedAttackGoal;
import com.github.teamfossilsarcheology.fossil.entity.prehistoric.base.Prehistoric;
import com.github.teamfossilsarcheology.fossil.entity.prehistoric.base.PrehistoricEntityInfo;
import com.github.teamfossilsarcheology.fossil.entity.util.Util;
import com.github.teamfossilsarcheology.fossil.sounds.ModSounds;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib3.core.builder.Animation;

public class Citipati extends Prehistoric {
    public static final String ANIMATIONS = "citipati.animation.json";
    public static final String ATTACK = "animation.citipati.attack1";
    public static final String EAT = "animation.citipati.eat";
    public static final String FALL = "animation.citipati.jump/fall";
    public static final String IDLE = "animation.citipati.idle";
    public static final String RUN = "animation.citipati.run";
    public static final String SLEEP = "animation.citipati.sit/sleep";
    public static final String SWIM = "animation.citipati.swim";
    public static final String WALK = "animation.citipati.walk";


    public Citipati(EntityType<Citipati> entityType, Level level) {
        super(entityType, level);
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        goalSelector.addGoal(Util.ATTACK, new DelayedAttackGoal(this, 1, false));
    }

    @Override
    public PrehistoricEntityInfo info() {
        return PrehistoricEntityInfo.CITIPATI;
    }

    @Override
    public Item getOrderItem() {
        return Items.BONE;
    }

    @Override
    public @NotNull Animation nextAttackAnimation() {
        return getAllAnimations().get(ATTACK);
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
        return getAllAnimations().get(RUN);
    }

    @Nullable
    @Override
    protected SoundEvent getAmbientSound() {
        return ModSounds.CITIPATI_AMBIENT.get();
    }

    @Nullable
    @Override
    protected SoundEvent getHurtSound(DamageSource damageSource) {
        return ModSounds.CITIPATI_HURT.get();
    }

    @Nullable
    @Override
    protected SoundEvent getDeathSound() {
        return ModSounds.CITIPATI_DEATH.get();
    }
}