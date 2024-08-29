package com.fossil.fossil.entity.prehistoric.swimming;

import com.fossil.fossil.entity.ai.DinoHurtByTargetGoal;
import com.fossil.fossil.entity.ai.GrabMeleeAttackGoal;
import com.fossil.fossil.entity.prehistoric.base.Prehistoric;
import com.fossil.fossil.entity.prehistoric.base.PrehistoricEntityInfo;
import com.fossil.fossil.entity.prehistoric.base.PrehistoricSwimming;
import com.fossil.fossil.entity.util.Util;
import com.fossil.fossil.item.ModItems;
import com.fossil.fossil.sounds.ModSounds;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib3.core.builder.Animation;
import software.bernie.geckolib3.core.manager.AnimationFactory;
import software.bernie.geckolib3.util.GeckoLibUtil;

public class Mosasaurus extends PrehistoricSwimming {
    public static final String ANIMATIONS = "mosasaurus.animation.json";
    public static final String ATTACK = "animation.mosasaurus.attack";
    public static final String BEACHED = "animation.mosasaurus.beached";
    public static final String EAT = "animation.mosasaurus.eat";
    public static final String IDLE = "animation.mosasaurus.idle";
    public static final String FALL = "animation.mosasaurus.jump/fall";
    public static final String GRAB = "animation.mosasaurus.grab";
    public static final String LEAP = "animation.mosasaurus.leap";
    public static final String SLEEP = "animation.mosasaurus.sleep";
    public static final String SWIM = "animation.mosasaurus.swim";
    public static final String SWIM_FAST = "animation.mosasaurus.swimfast";
    private final AnimationFactory factory = GeckoLibUtil.createFactory(this);

    public Mosasaurus(EntityType<Mosasaurus> entityType, Level level) {
        super(entityType, level);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Prehistoric.createAttributes().add(Attributes.FOLLOW_RANGE, 64);
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
    public PrehistoricEntityInfo info() {
        return PrehistoricEntityInfo.MOSASAURUS;
    }

    @Override
    public Item getOrderItem() {
        return ModItems.SKULL_STICK.get();
    }

    @Override
    public boolean doHurtTarget(Entity target) {
        return true;
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
        return getAllAnimations().get(BEACHED);
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

    @Override
    public AnimationFactory getFactory() {
        return factory;
    }

    @Nullable
    @Override
    protected SoundEvent getAmbientSound() {
        return isInWater() ? ModSounds.MOSASAURUS_AMBIENT_INSIDE.get() : ModSounds.MOSASAURUS_AMBIENT_OUTSIDE.get();
    }

    @Nullable
    @Override
    protected SoundEvent getHurtSound(DamageSource damageSource) {
        return ModSounds.MOSASAURUS_HURT.get();
    }

    @Nullable
    @Override
    protected SoundEvent getDeathSound() {
        return ModSounds.MOSASAURUS_DEATH.get();
    }
}