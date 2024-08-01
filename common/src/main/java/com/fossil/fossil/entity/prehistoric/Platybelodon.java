package com.fossil.fossil.entity.prehistoric;

import com.fossil.fossil.entity.ai.DelayedAttackGoal;
import com.fossil.fossil.entity.ai.FleeBattleGoal;
import com.fossil.fossil.entity.prehistoric.base.Prehistoric;
import com.fossil.fossil.entity.prehistoric.base.PrehistoricEntityInfo;
import com.fossil.fossil.entity.util.Util;
import com.fossil.fossil.sounds.ModSounds;
import com.fossil.fossil.util.Gender;
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

public class Platybelodon extends Prehistoric {
    public static final String ANIMATIONS = "platybelodon.animation.json";
    public static final String ATTACK = "animation.platybelodon.attack1";
    public static final String EAT = "animation.platybelodon.drink";
    public static final String FALL = "animation.platybelodon.jump/fall";
    public static final String IDLE1 = "animation.platybelodon.idle1";
    public static final String IDLE2 = "animation.platybelodon.idle2";
    public static final String RUN = "animation.platybelodon.run";
    public static final String SLEEP = "animation.platybelodon.sleep";
    public static final String SWIM = "animation.platybelodon.swim";
    public static final String WALK = "animation.platybelodon.walk";

    private final AnimationFactory factory = GeckoLibUtil.createFactory(this);

    public Platybelodon(EntityType<Platybelodon> entityType, Level level) {
        super(entityType, level);
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        goalSelector.addGoal(Util.IMMOBILE + 3, new FleeBattleGoal(this, 1));
        goalSelector.addGoal(Util.ATTACK, new DelayedAttackGoal(this, 1, false));
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
        return random.nextFloat() > 0.1 ? getAllAnimations().get(IDLE1) : getAllAnimations().get(IDLE2);
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