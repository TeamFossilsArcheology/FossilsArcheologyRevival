package com.fossil.fossil.entity.prehistoric;

import com.fossil.fossil.entity.ai.DinoFollowOwnerGoal;
import com.fossil.fossil.entity.ai.DinoLookAroundGoal;
import com.fossil.fossil.entity.ai.DinoMeleeAttackGoal;
import com.fossil.fossil.entity.ai.DinoWanderGoal;
import com.fossil.fossil.entity.prehistoric.base.Prehistoric;
import com.fossil.fossil.entity.prehistoric.base.PrehistoricEntityType;
import com.fossil.fossil.sounds.ModSounds;
import com.fossil.fossil.util.Gender;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib3.core.builder.Animation;
import software.bernie.geckolib3.core.manager.AnimationFactory;
import software.bernie.geckolib3.util.GeckoLibUtil;

public class Dodo extends Prehistoric {
    public static final String ANIMATIONS = "dodo.animation.json";
    public static final String ATTACK1 = "animation.dodo.bite1";
    public static final String ATTACK2 = "animation.dodo.bite2";
    public static final String EAT = "animation.dodo.eat";
    public static final String FALL = "animation.dodo.jump/fall";
    public static final String IDLE = "animation.dodo.idle";
    public static final String RUN = "animation.dodo.run";
    public static final String SLEEP1 = "animation.dodo.sleep1";
    public static final String SLEEP2 = "animation.dodo.sleep2";
    public static final String SWIM = "animation.dodo.swim";
    public static final String WALK = "animation.dodo.walk";

    private final AnimationFactory factory = GeckoLibUtil.createFactory(this);

    public Dodo(EntityType<Dodo> entityType, Level level) {
        super(entityType, level);
    }
    //TODO: Festive texture

    @Override
    protected void registerGoals() {
        super.registerGoals();
        goalSelector.addGoal(0, new DinoMeleeAttackGoal(this, 1, false));
        goalSelector.addGoal(1, new FloatGoal(this));
        goalSelector.addGoal(3, new DinoWanderGoal(this, 1));
        goalSelector.addGoal(6, new DinoFollowOwnerGoal(this, 1, 10, 2, false));
        goalSelector.addGoal(7, new DinoLookAroundGoal(this));
    }

    @Override
    public void aiStep() {
        super.aiStep();
        Vec3 movement = getDeltaMovement();
        if (!isOnGround() && movement.y < 0) {
            setDeltaMovement(movement.x, movement.y * 0.6, movement.z);
        }
    }

    @Override
    public PrehistoricEntityType type() {
        return PrehistoricEntityType.DODO;
    }

    @Override
    public Item getOrderItem() {
        return Items.STICK;
    }

    @Override
    public float getGenderedScale() {
        return getGender() == Gender.MALE ? 1.25f : super.getGenderedScale();
    }

    @Override
    public @NotNull Animation nextAttackAnimation() {
        return getAllAnimations().get(random.nextInt(2) == 0 ? ATTACK1 : ATTACK2);
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
        return ModSounds.DODO_AMBIENT.get();
    }

    @Nullable
    @Override
    protected SoundEvent getHurtSound(DamageSource damageSource) {
        return ModSounds.DODO_HURT.get();
    }

    @Nullable
    @Override
    protected SoundEvent getDeathSound() {
        return ModSounds.DODO_DEATH.get();
    }
}