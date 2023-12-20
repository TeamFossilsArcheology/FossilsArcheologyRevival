package com.fossil.fossil.entity.prehistoric;

import com.fossil.fossil.entity.ModEntities;
import com.fossil.fossil.entity.ai.*;
import com.fossil.fossil.entity.prehistoric.base.PrehistoricEntityType;
import com.fossil.fossil.entity.prehistoric.base.PrehistoricEntityTypeAI;
import com.fossil.fossil.entity.prehistoric.base.PrehistoricLeaping;
import com.fossil.fossil.entity.prehistoric.base.PrehistoricScary;
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

public class Velociraptor extends PrehistoricLeaping implements PrehistoricScary {
    public static final String ANIMATIONS = "velociraptor.animation.json";
    public static final String IDLE = "animation.velociraptor.idle";
    public static final String WALK = "animation.velociraptor.walk";
    public static final String RUN = "animation.velociraptor.run";
    public static final String SPEAK = "animation.velociraptor.speak";
    public static final String CALL = "animation.velociraptor.call";
    public static final String ATTACK1 = "animation.velociraptor.attack1";
    public static final String LEAP = "animation.velociraptor.leap";
    public static final String DISPLAY = "animation.velociraptor.display";
    private final AnimationFactory factory = GeckoLibUtil.createFactory(this);

    public Velociraptor(EntityType<Velociraptor> entityType, Level level) {
        super(entityType, level, true);
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        goalSelector.addGoal(0, new DinoMeleeAttackGoal(this, 1, false));
        goalSelector.addGoal(0, new DinoOtherLeapAtTargetGoal(this));
        goalSelector.addGoal(1, new FloatGoal(this));
        goalSelector.addGoal(3, new DinoWanderGoal(this, 1));
        goalSelector.addGoal(4, new RestrictSunGoal(this));
        goalSelector.addGoal(6, new DinoFollowOwnerGoal(this, 1, 10, 2, false));
        goalSelector.addGoal(7, new DinoLookAroundGoal(this));
        targetSelector.addGoal(1, new DinoOwnerHurtByTargetGoal(this));
        targetSelector.addGoal(2, new DinoOwnerHurtTargetGoal(this));
        targetSelector.addGoal(3, new DinoHurtByTargetGoal(this));
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
    public PrehistoricEntityType type() {
        return PrehistoricEntityType.VELOCIRAPTOR;
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
    public PrehistoricEntityTypeAI.Response aiResponseType() {
        return isBaby() ? PrehistoricEntityTypeAI.Response.SCARED : PrehistoricEntityTypeAI.Response.TERRITORIAL;
    }

    @Override
    public @NotNull Animation nextEatingAnimation() {
        return getAllAnimations().get(DISPLAY);
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
        return getAllAnimations().get(ATTACK1);
    }

    @Override
    public String getLeapingAnimationName() {
        return LEAP;
    }

    @Override
    public AnimationFactory getFactory() {
        return factory;
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
