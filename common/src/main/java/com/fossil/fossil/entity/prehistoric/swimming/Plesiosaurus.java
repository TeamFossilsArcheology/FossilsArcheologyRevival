package com.fossil.fossil.entity.prehistoric.swimming;

import com.fossil.fossil.entity.ai.DelayedAttackGoal;
import com.fossil.fossil.entity.ai.DinoHurtByTargetGoal;
import com.fossil.fossil.entity.ai.MakeFishGoal;
import com.fossil.fossil.entity.prehistoric.base.PrehistoricEntityInfo;
import com.fossil.fossil.entity.prehistoric.base.PrehistoricSwimming;
import com.fossil.fossil.entity.util.Util;
import com.fossil.fossil.sounds.ModSounds;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib3.core.builder.Animation;
import software.bernie.geckolib3.core.manager.AnimationFactory;
import software.bernie.geckolib3.util.GeckoLibUtil;

public class Plesiosaurus extends PrehistoricSwimming {
    public static final String ANIMATIONS = "plesiosaurus.animation.json";
    public static final String ATTACK = "animation.plesiosaurus.attack";
    public static final String BEACHED = "animation.plesiosaurus.beached";
    public static final String EAT = "animation.plesiosaurus.eat";
    public static final String IDLE = "animation.plesiosaurus.randomidle";
    public static final String SLEEP = "animation.plesiosaurus.sleep";
    public static final String SWIM = "animation.plesiosaurus.swim";
    public static final String SWIM_FAST = "animation.plesiosaurus.swimfast";

    private final AnimationFactory factory = GeckoLibUtil.createFactory(this);

    public Plesiosaurus(EntityType<Plesiosaurus> entityType, Level level) {
        super(entityType, level);
        hasTeenTexture = false;
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        goalSelector.addGoal(Util.ATTACK, new DelayedAttackGoal(this, 1, false));
        goalSelector.addGoal(4, new MakeFishGoal(this));
        targetSelector.addGoal(3, new DinoHurtByTargetGoal(this));
    }

    @Override
    public boolean isAmphibious() {
        return false;
    }

    @Override
    public PrehistoricEntityInfo info() {
        return PrehistoricEntityInfo.PLESIOSAURUS;
    }

    @Override
    public Item getOrderItem() {
        return Items.NAUTILUS_SHELL;
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
        return isInWater() ? ModSounds.PLESIOSAURUS_AMBIENT_INSIDE.get() : ModSounds.PLESIOSAURUS_AMBIENT_OUTSIDE.get();
    }

    @Nullable
    @Override
    protected SoundEvent getHurtSound(DamageSource damageSource) {
        return ModSounds.PLESIOSAURUS_HURT.get();
    }

    @Nullable
    @Override
    protected SoundEvent getDeathSound() {
        return ModSounds.PLESIOSAURUS_DEATH.get();
    }
}