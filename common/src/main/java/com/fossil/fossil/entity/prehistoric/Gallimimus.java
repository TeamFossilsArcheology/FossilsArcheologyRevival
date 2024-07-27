package com.fossil.fossil.entity.prehistoric;

import com.fossil.fossil.entity.ai.DelayedAttackGoal;
import com.fossil.fossil.entity.prehistoric.base.PrehistoricEntityInfo;
import com.fossil.fossil.entity.prehistoric.base.PrehistoricEntityInfoAI;
import com.fossil.fossil.entity.prehistoric.base.PrehistoricFlocking;
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

public class Gallimimus extends PrehistoricFlocking {
    public static final String ANIMATIONS = "gallimimus.animation.json";
    public static final String ATTACK = "animation.gallimimus.attack1";
    public static final String EAT = "animation.gallimimus.eat";
    public static final String FALL = "animation.gallimimus.jump/fall";
    public static final String IDLE = "animation.gallimimus.idle";
    public static final String RUN = "animation.gallimimus.run";
    public static final String SLEEP = "animation.gallimimus.sleep/sit";
    public static final String SWIM = "animation.gallimimus.swim";
    public static final String WALK = "animation.gallimimus.walk";
    private final AnimationFactory factory = GeckoLibUtil.createFactory(this);

    public Gallimimus(EntityType<Gallimimus> entityType, Level level) {
        super(entityType, level);
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        goalSelector.addGoal(0, new DelayedAttackGoal(this, 1, false));
    }

    @Override
    public PrehistoricEntityInfo info() {
        return PrehistoricEntityInfo.GALLIMIMUS;
    }

    @Override
    protected int getMaxGroupSize() {
        return 10;
    }

    @Override
    public Item getOrderItem() {
        return Items.STICK;
    }

    @Override
    public void aiStep() {
        super.aiStep();
        if (getRidingPlayer() != null) {
            maxUpStep = 2;
        } else {
            maxUpStep = 0;
        }
    }

    @Override
    public PrehistoricEntityInfoAI.Response aiResponseType() {
        return groupSize >= 3 ? PrehistoricEntityInfoAI.Response.TERRITORIAL : PrehistoricEntityInfoAI.Response.SCARED;
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
    public @NotNull Animation nextSleepingAnimation() {
        return getAllAnimations().get(SLEEP);
    }

    @Override
    public @NotNull Animation nextMovingAnimation() {
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

    @Override
    public AnimationFactory getFactory() {
        return factory;
    }

    @Nullable
    @Override
    protected SoundEvent getAmbientSound() {
        return ModSounds.GALLIMIMUS_AMBIENT.get();
    }

    @Nullable
    @Override
    protected SoundEvent getHurtSound(DamageSource damageSource) {
        return ModSounds.GALLIMIMUS_HURT.get();
    }

    @Nullable
    @Override
    protected SoundEvent getDeathSound() {
        return ModSounds.GALLIMIMUS_DEATH.get();
    }
}