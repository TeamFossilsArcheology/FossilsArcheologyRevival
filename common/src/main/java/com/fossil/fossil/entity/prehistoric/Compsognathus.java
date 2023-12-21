package com.fossil.fossil.entity.prehistoric;

import com.fossil.fossil.entity.ai.*;
import com.fossil.fossil.entity.prehistoric.base.PrehistoricEntityType;
import com.fossil.fossil.entity.prehistoric.base.PrehistoricLeaping;
import com.fossil.fossil.sounds.ModSounds;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.goal.FloatGoal;
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
    public static final String IDLE = "animation.compsognathus.idle";
    public static final String RUN = "animation.compsognathus.run";
    public static final String SWIM = "animation.compsognathus.swim";
    public static final String WALK = "animation.compsognathus.walk";
    private final AnimationFactory factory = GeckoLibUtil.createFactory(this);

    public Compsognathus(EntityType<Compsognathus> entityType, Level level) {
        super(entityType, level, false);
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        goalSelector.addGoal(0, new DinoMeleeAttackGoal(this, 1, false));
        goalSelector.addGoal(1, new FloatGoal(this));
        goalSelector.addGoal(3, new DinoWanderGoal(this, 1));
        goalSelector.addGoal(5, new DinoOtherLeapAtTargetGoal(this));
        goalSelector.addGoal(5, new RestrictSunGoal(this));
        goalSelector.addGoal(7, new DinoFollowOwnerGoal(this, 1, 10, 2, false));
        goalSelector.addGoal(8, new DinoLookAroundGoal(this));
        targetSelector.addGoal(1, new DinoOwnerHurtByTargetGoal(this));
        targetSelector.addGoal(2, new DinoOwnerHurtTargetGoal(this));
        targetSelector.addGoal(3, new DinoHurtByTargetGoal(this));
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
    public PrehistoricEntityType type() {
        return PrehistoricEntityType.COMPSOGNATHUS;
    }

    @Override
    public Item getOrderItem() {
        return Items.BONE;
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
    public @NotNull Animation nextMovingAnimation() {
        return getAllAnimations().get(WALK);
    }

    @Override
    public @NotNull Animation nextChasingAnimation() {
        return getAllAnimations().get(RUN);
    }

    @Override
    public @NotNull Animation nextAttackAnimation() {
        return getAllAnimations().get(ATTACK);
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