package com.github.teamfossilsarcheology.fossil.entity.prehistoric.swimming;

import com.github.teamfossilsarcheology.fossil.entity.ai.DinoHurtByTargetGoal;
import com.github.teamfossilsarcheology.fossil.entity.ai.GrabMeleeAttackGoal;
import com.github.teamfossilsarcheology.fossil.entity.prehistoric.base.PrehistoricEntityInfo;
import com.github.teamfossilsarcheology.fossil.entity.prehistoric.base.PrehistoricSwimming;
import com.github.teamfossilsarcheology.fossil.entity.util.Util;
import com.github.teamfossilsarcheology.fossil.item.ModItems;
import com.github.teamfossilsarcheology.fossil.sounds.ModSounds;
import com.github.teamfossilsarcheology.fossil.util.Gender;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib3.core.builder.Animation;

public class Megalodon extends PrehistoricSwimming {
    public static final String ANIMATIONS = "megalodon.animation.json";
    public static final String ATTACK = "animation.megalodon.attack";
    public static final String BEACHED = "animation.megalodon.beached1";
    public static final String BEACHED2 = "animation.megalodon.beached2";
    public static final String EAT = "animation.megalodon.eat";
    public static final String FALL = "animation.megalodon.jump/fall";
    public static final String GRAB = "animation.megalodon.grab";
    public static final String IDLE = "animation.megalodon.idle";
    public static final String SLEEP = "animation.megalodon.sleep";
    public static final String SWIM = "animation.megalodon.swim";
    public static final String SWIM_FAST = "animation.megalodon.swim_fast";

    public Megalodon(EntityType<Megalodon> entityType, Level level) {
        super(entityType, level);
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        goalSelector.addGoal(Util.ATTACK, new GrabMeleeAttackGoal(this, 1, false));
        //goalSelector.addGoal(Util.ATTACK + 1, new BreachAttackGoal(this, 1));
        targetSelector.addGoal(3, new DinoHurtByTargetGoal(this));
    }

    @Override
    public boolean canDoBreachAttack() {
        return true;
    }

    @Override
    protected void handleAirSupply(int airSupply) {
        if (isAlive() && !isInWaterOrBubble() && !isNoAi()) {
            setAirSupply(airSupply - 1);
            if (getAirSupply() == -20) {
                setAirSupply(0);
                hurt(DamageSource.DROWN, 2.0f);
            }
        } else {
            setAirSupply(1000);
        }
    }

    @Override
    public PrehistoricEntityInfo info() {
        return PrehistoricEntityInfo.MEGALODON;
    }

    @Override
    public Item getOrderItem() {
        return ModItems.SKULL_STICK.get();
    }

    @Override
    public float getGenderedScale() {
        return getGender() == Gender.MALE ? 0.8f : super.getGenderedScale();
    }

    @Override
    public float getTargetScale() {
        return 2;
    }

    @Override
    public boolean canHuntMobsOnLand() {
        return false;
    }

    @Override
    public @NotNull Animation nextAttackAnimation() {
        return getAllAnimations().get(ATTACK);
    }

    @Override
    public @NotNull Animation nextBeachedAnimation() {
        return getAllAnimations().get(random.nextInt(2) == 0 ? BEACHED : BEACHED2);
    }

    @Override
    public @NotNull Animation nextEatingAnimation() {
        return getAllAnimations().get(EAT);
    }

    @Override
    public @NotNull Animation nextGrabbingAnimation() {
        return getAllAnimations().get(GRAB);
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
    public @NotNull Animation nextMovingAnimation() {
        return getAllAnimations().get(SWIM);
    }

    @Override
    public @NotNull Animation nextSprintingAnimation() {
        return getAllAnimations().get(SWIM_FAST);
    }

    @Nullable
    @Override
    protected SoundEvent getAmbientSound() {
        return isInWater() ? ModSounds.MEGALODON_AMBIENT.get() : ModSounds.MOSASAURUS_AMBIENT_OUTSIDE.get();

    }

    @Nullable
    @Override
    protected SoundEvent getHurtSound(DamageSource damageSource) {
        return isInWater() ? ModSounds.MEGALODON_HURT.get() : ModSounds.MEGALODON_HURT_OUTSIDE.get();
    }

    @Nullable
    @Override
    protected SoundEvent getDeathSound() {
        return ModSounds.MEGALODON_DEATH.get();
    }
}