package com.fossil.fossil.entity.prehistoric;

import com.fossil.fossil.entity.ai.DinoFollowOwnerGoal;
import com.fossil.fossil.entity.ai.DinoLookAroundGoal;
import com.fossil.fossil.entity.ai.DinoMeleeAttackGoal;
import com.fossil.fossil.entity.ai.DinoWanderGoal;
import com.fossil.fossil.entity.prehistoric.base.PrehistoricEntityInfo;
import com.fossil.fossil.entity.prehistoric.base.PrehistoricFlying;
import com.fossil.fossil.sounds.ModSounds;
import com.fossil.fossil.util.Gender;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib3.core.builder.Animation;
import software.bernie.geckolib3.core.manager.AnimationFactory;
import software.bernie.geckolib3.util.GeckoLibUtil;

public class Confuciusornis extends PrehistoricFlying {
    public static final String ANIMATIONS = "confuciusornis.animation.json";
    public static final String ATTACK = "animation.confuciusornis.attack1";
    public static final String EAT = "animation.confuciusornis.eating";
    public static final String FALL = "animation.confuciusornis.jump/fall";
    public static final String FLY = "animation.confuciusornis.flying";
    public static final String IDLE = "animation.confuciusornis.extra1";
    public static final String FLY_FAST = "animation.confuciusornis.flyfast";
    public static final String SLEEP = "animation.confuciusornis.sleep/sit";
    public static final String SWIM = "animation.confuciusornis.swim";
    public static final String WALK = "animation.confuciusornis.walk";

    private final AnimationFactory factory = GeckoLibUtil.createFactory(this);

    public Confuciusornis(EntityType<Confuciusornis> entityType, Level level) {
        super(entityType, level);
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        goalSelector.addGoal(0, new DinoMeleeAttackGoal(this, 1, false));
        goalSelector.addGoal(1, new FloatGoal(this));
        goalSelector.addGoal(3, new DinoWanderGoal(this, 1));
        goalSelector.addGoal(7, new DinoFollowOwnerGoal(this, 1, 10, 2, false));
        goalSelector.addGoal(8, new DinoLookAroundGoal(this));
    }

    @Override
    public PrehistoricEntityInfo info() {
        return PrehistoricEntityInfo.CONFUCIUSORNIS;
    }

    @Override
    public Item getOrderItem() {
        return Items.STICK;
    }

    @Override
    public float getGenderedScale() {
        return getGender() == Gender.MALE ? 0.8f : super.getGenderedScale();
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
        if (isFlying()) {
            return getAllAnimations().get(FLY);
        } else if (isInWater()) {
            return getAllAnimations().get(SWIM);
        }
        return getAllAnimations().get(WALK);
    }

    @Override
    public @NotNull Animation nextSprintingAnimation() {
        if (isFlying()) {
            return getAllAnimations().get(FLY_FAST);
        } else if (isInWater()) {
            return getAllAnimations().get(SWIM);
        }
        return getAllAnimations().get(WALK);
    }

    @Override
    public AnimationFactory getFactory() {
        return factory;
    }

    @Override
    public @NotNull Animation nextTakeOffAnimation() {
        return getAllAnimations().get(IDLE);
    }

    @Nullable
    @Override
    protected SoundEvent getAmbientSound() {
        return ModSounds.CONFUCIUSORNIS_AMBIENT.get();
    }

    @Nullable
    @Override
    protected SoundEvent getHurtSound(DamageSource damageSource) {
        return ModSounds.CONFUCIUSORNIS_HURT.get();
    }

    @Nullable
    @Override
    protected SoundEvent getDeathSound() {
        return ModSounds.CONFUCIUSORNIS_DEATH.get();
    }
}