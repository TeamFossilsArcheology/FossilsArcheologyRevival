package com.github.teamfossilsarcheology.fossil.entity.prehistoric;

import com.github.teamfossilsarcheology.fossil.entity.ai.DelayedAttackGoal;
import com.github.teamfossilsarcheology.fossil.entity.ai.DinoOtherLeapAtTargetGoal;
import com.github.teamfossilsarcheology.fossil.entity.prehistoric.base.PrehistoricEntityInfo;
import com.github.teamfossilsarcheology.fossil.entity.prehistoric.base.PrehistoricLeaping;
import com.github.teamfossilsarcheology.fossil.entity.util.Util;
import com.github.teamfossilsarcheology.fossil.sounds.ModSounds;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.goal.RestrictSunGoal;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib3.core.builder.Animation;
import software.bernie.geckolib3.core.manager.AnimationFactory;
import software.bernie.geckolib3.util.GeckoLibUtil;

public class Compsognathus extends PrehistoricLeaping {
    public static final String ANIMATIONS = "compsognathus.animation.json";
    public static final String ATTACK = "animation.compsognathus.attack";
    public static final String EAT = "animation.compsognathus.eat";
    public static final String FALL = "animation.compsognathus.jump/fall2";
    public static final String IDLE = "animation.compsognathus.idle";
    public static final String RUN = "animation.compsognathus.run";
    public static final String SLEEP = "animation.compsognathus.sleep";
    public static final String SWIM = "animation.compsognathus.swim";
    public static final String WALK = "animation.compsognathus.walk";
    private final AnimationFactory factory = GeckoLibUtil.createFactory(this);

    public Compsognathus(EntityType<Compsognathus> entityType, Level level) {
        super(entityType, level, false);
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        goalSelector.addGoal(Util.ATTACK, new DelayedAttackGoal(this, 1, false));
        goalSelector.addGoal(5, new DinoOtherLeapAtTargetGoal(this));
        goalSelector.addGoal(Util.NEEDS + 4, new RestrictSunGoal(this));
    }

    @Override
    public void doLeapMovement() {
        if (getTarget() != null) {
            Vec3 offset = getTarget().position().subtract(position().add(0, getTarget().getBbHeight(), 0));
            setDeltaMovement(offset.normalize());
        }
    }

    @Override
    public boolean useLeapAttack() {
        return true;
    }

    @Override
    public PrehistoricEntityInfo info() {
        return PrehistoricEntityInfo.COMPSOGNATHUS;
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
    public @NotNull Animation nextMovingAnimation() {
        if (isInWater()) {
            return getAllAnimations().get(SWIM);
        }
        return getAllAnimations().get(RUN);//Walk to slow
    }

    @Override
    public @NotNull Animation nextSprintingAnimation() {
        if (isInWater()) {
            return getAllAnimations().get(SWIM);
        }
        return getAllAnimations().get(RUN);
    }

    @Override
    public String getLeapingAnimationName() {
        return ATTACK;
    }

    @Override
    public AnimationFactory getFactory() {
        return factory;
    }

    @Nullable
    @Override
    protected SoundEvent getAmbientSound() {
        return ModSounds.COMPSOGNATHUS_AMBIENT.get();
    }

    @Nullable
    @Override
    protected SoundEvent getHurtSound(DamageSource damageSource) {
        return ModSounds.COMPSOGNATHUS_HURT.get();
    }

    @Nullable
    @Override
    protected SoundEvent getDeathSound() {
        return ModSounds.COMPSOGNATHUS_DEATH.get();
    }
}