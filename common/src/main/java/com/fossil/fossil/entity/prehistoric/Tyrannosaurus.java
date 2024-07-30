package com.fossil.fossil.entity.prehistoric;

import com.fossil.fossil.entity.ai.DelayedAttackGoal;
import com.fossil.fossil.entity.prehistoric.base.Prehistoric;
import com.fossil.fossil.entity.prehistoric.base.PrehistoricEntityInfo;
import com.fossil.fossil.entity.prehistoric.base.PrehistoricScary;
import com.fossil.fossil.entity.util.Util;
import com.fossil.fossil.item.ModItems;
import com.fossil.fossil.sounds.ModSounds;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib3.core.builder.Animation;
import software.bernie.geckolib3.core.manager.AnimationFactory;
import software.bernie.geckolib3.util.GeckoLibUtil;

public class Tyrannosaurus extends Prehistoric implements PrehistoricScary {
    public static final String ANIMATIONS = "tyrannosaurus.animation.json";
    public static final String ATTACK_NORMAL1 = "animation.tyrannosaurus.attack_normal1";
    public static final String ATTACK_NORMAL2 = "animation.tyrannosaurus.attack_normal2";
    public static final String EAT = "animation.tyrannosaurus.eat";
    public static final String FALL = "animation.tyrannosaurus.jump/fall";
    public static final String IDLE = "animation.tyrannosaurus.idle";
    public static final String RUN = "animation.tyrannosaurus.run";
    public static final String SIT1 = "animation.tyrannosaurus.sit1";
    public static final String SIT2 = "animation.tyrannosaurus.sit2";
    public static final String SLEEP1 = "animation.tyrannosaurus.sleep1";
    public static final String SLEEP2 = "animation.tyrannosaurus.sleep2";
    public static final String SWIM = "animation.tyrannosaurus.swim";
    public static final String WALK = "animation.tyrannosaurus.walk";

    private final AnimationFactory factory = GeckoLibUtil.createFactory(this);

    public Tyrannosaurus(EntityType<Tyrannosaurus> entityType, Level level) {
        super(entityType, level);
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        goalSelector.addGoal(Util.ATTACK, new DelayedAttackGoal(this, 1.5, false));
    }

    @Override
    public PrehistoricEntityInfo info() {
        return PrehistoricEntityInfo.TYRANNOSAURUS;
    }

    @Override
    public Item getOrderItem() {
        return ModItems.SKULL_STICK.get();
    }

    @Override
    public float getTargetScale() {
        return 2;
    }

    @Override
    public @NotNull Animation nextAttackAnimation() {
        return getAllAnimations().get(random.nextInt(2) == 0 ? ATTACK_NORMAL1 : ATTACK_NORMAL2);
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
        return getAllAnimations().get(random.nextInt(2) == 0 ? SIT1 : SIT2);
    }

    @Override
    public @NotNull Animation nextSleepingAnimation() {
        return getAllAnimations().get(random.nextInt(2) == 0 ? SLEEP1 : SLEEP2);
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
        return isWeak() ? ModSounds.TYRANNOSAURUS_WEAK.get() : ModSounds.TYRANNOSAURUS_AMBIENT.get();
    }

    @Nullable
    @Override
    protected SoundEvent getHurtSound(DamageSource damageSource) {
        return ModSounds.TYRANNOSAURUS_HURT.get();
    }

    @Nullable
    @Override
    protected SoundEvent getDeathSound() {
        return ModSounds.TYRANNOSAURUS_DEATH.get();
    }
}
