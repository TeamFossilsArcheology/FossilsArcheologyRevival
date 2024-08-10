package com.fossil.fossil.entity.prehistoric;

import com.fossil.fossil.entity.ai.DelayedAttackGoal;
import com.fossil.fossil.entity.prehistoric.base.Prehistoric;
import com.fossil.fossil.entity.prehistoric.base.PrehistoricEntityInfo;
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

public class Psittacosaurus extends Prehistoric {
    public static final String ATTACK = "animation.psittacosaurus.attack";
    public static final String EAT = "animation.psittacosaurus.eat";
    public static final String FALL = "animation.psittacosaurus.jump/fall";
    public static final String RUN = "animation.psittacosaurus.run";
    public static final String SIT = "animation.psittacosaurus.sit";
    public static final String SLEEP = "animation.psittacosaurus.sleep";
    public static final String SWIM = "animation.psittacosaurus.swim";
    public static final String WALK = "animation.psittacosaurus.walk";
    public final AnimationFactory factory = GeckoLibUtil.createFactory(this);

    public Psittacosaurus(EntityType<Psittacosaurus> type, Level level) {
        super(type, level);
    }

    @Override
    public void registerGoals() {
        super.registerGoals();

        goalSelector.addGoal(Util.ATTACK, new DelayedAttackGoal(this, 1, false));
    }

    @Override
    public PrehistoricEntityInfo info() {
        return PrehistoricEntityInfo.PSITTACOSAURUS;
    }

    @Override
    public Item getOrderItem() {
        return Items.STICK;
    }

    @Override
    public AnimationFactory getFactory() {
        return factory;
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
        return getAllAnimations().get(SIT);
    }
    
    @Override
    public @NotNull Animation nextSittingAnimation() {
        return getAllAnimations().get(SIT);
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

    @Nullable
    @Override
    protected SoundEvent getAmbientSound() {
        return ModSounds.TRICERATOPS_AMBIENT.get();
    }

    @Nullable
    @Override
    protected SoundEvent getHurtSound(DamageSource damageSource) {
        return ModSounds.TRICERATOPS_HURT.get();
    }

    @Nullable
    @Override
    protected SoundEvent getDeathSound() {
        return ModSounds.TRICERATOPS_DEATH.get();
    }
}