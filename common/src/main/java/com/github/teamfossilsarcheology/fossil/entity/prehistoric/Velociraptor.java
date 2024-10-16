package com.github.teamfossilsarcheology.fossil.entity.prehistoric;

import com.github.teamfossilsarcheology.fossil.entity.ModEntities;
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

public class Velociraptor extends PrehistoricLeaping {
    public static final String ANIMATIONS = "velociraptor.animation.json";
    public static final String ATTACK = "animation.velociraptor.attack";
    public static final String EAT = "animation.velociraptor.eat";
    public static final String FALL = "animation.velociraptor.jump/fall";
    public static final String IDLE = "animation.velociraptor.idle";
    public static final String RUN = "animation.velociraptor.run";
    public static final String SIT = "animation.velociraptor.sit";
    public static final String SLEEP = "animation.velociraptor.sleep";
    public static final String SWIM = "animation.velociraptor.swim";
    public static final String WALK = "animation.velociraptor.walk";

    public Velociraptor(EntityType<Velociraptor> entityType, Level level) {
        super(entityType, level, true);
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        goalSelector.addGoal(Util.ATTACK, new DelayedAttackGoal(this, 1, false));
        goalSelector.addGoal(0, new DinoOtherLeapAtTargetGoal(this));
        goalSelector.addGoal(Util.NEEDS + 4, new RestrictSunGoal(this));
    }

    @Override
    public void doLeapMovement() {
        if (getTarget() != null) {
            lookAt(getTarget(), 100, 100);
            Vec3 offset = getTarget().position().subtract(position()).add(0, getTarget().getBbHeight(), 0);
            setDeltaMovement(offset.normalize());
        }
    }

    @Override
    public boolean useLeapAttack() {
        return true;//TODO: Implement
    }

    @Override
    public PrehistoricEntityInfo info() {
        return PrehistoricEntityInfo.VELOCIRAPTOR;
    }

    @Override
    public boolean canAttackType(EntityType<?> entityType) {
        return !entityType.equals(ModEntities.DEINONYCHUS.get()) && super.canAttackType(entityType);
    }

    @Override
    public Item getOrderItem() {
        return Items.BONE;
    }

    @Override
    public float getTargetScale() {
        return 2;
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

    @Override
    public String getLeapingAnimationName() {
        return IDLE;
    }

    @Nullable
    @Override
    protected SoundEvent getAmbientSound() {
        return ModSounds.VELOCIRAPTOR_AMBIENT.get();
    }

    @Nullable
    @Override
    protected SoundEvent getHurtSound(DamageSource damageSource) {
        return ModSounds.VELOCIRAPTOR_HURT.get();
    }

    @Nullable
    @Override
    protected SoundEvent getDeathSound() {
        return ModSounds.VELOCIRAPTOR_DEATH.get();
    }
}
