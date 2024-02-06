package com.fossil.fossil.entity.prehistoric;

import com.fossil.fossil.entity.ai.*;
import com.fossil.fossil.entity.prehistoric.base.Prehistoric;
import com.fossil.fossil.entity.prehistoric.base.PrehistoricEntityInfo;
import com.fossil.fossil.sounds.ModSounds;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.Mth;
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

public class Ankylosaurus extends Prehistoric {
    public static final String ANIMATIONS = "ankylosaurus.animation.json";
    public static final String ATTACK_FRONT = "animation.ankylosaurus.attack_strong1";
    public static final String ATTACK_BACK1 = "animation.ankylosaurus.attack_back1";
    public static final String ATTACK_BACK2 = "animation.ankylosaurus.attack_back2";
    public static final String EAT = "animation.ankylosaurus.eat";
    public static final String FALL = "animation.ankylosaurus.jump/fall";
    public static final String IDLE = "animation.ankylosaurus.idle";
    public static final String RUN = "animation.ankylosaurus.run";
    public static final String SLEEP = "animation.ankylosaurus.sleep1";
    public static final String SWIM = "animation.ankylosaurus.swim";
    public static final String WALK = "animation.ankylosaurus.walk";

    private final AnimationFactory factory = GeckoLibUtil.createFactory(this);

    public Ankylosaurus(EntityType<Ankylosaurus> entityType, Level level) {
        super(entityType, level);
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        goalSelector.addGoal(0, new FleeBattleGoal(this, 1));
        goalSelector.addGoal(1, new DinoMeleeAttackGoal(this, 1, false));
        goalSelector.addGoal(1, new FloatGoal(this));
        goalSelector.addGoal(6, new DinoFollowOwnerGoal(this, 1, 10, 2, false));
        goalSelector.addGoal(7, new DinoWanderGoal(this, 1));
        goalSelector.addGoal(8, new DinoLookAroundGoal(this));
        targetSelector.addGoal(1, new DinoOwnerHurtByTargetGoal(this));
        targetSelector.addGoal(2, new DinoOwnerHurtTargetGoal(this));
        targetSelector.addGoal(3, new DinoHurtByTargetGoal(this));
    }

    @Override
    public PrehistoricEntityInfo info() {
        return PrehistoricEntityInfo.ANKYLOSAURUS;
    }

    @Override
    public Item getOrderItem() {
        return Items.BONE;
    }

    @Override
    public @NotNull Animation nextAttackAnimation() {
        if (getTarget() != null) {
            double x = getTarget().getX() - getX();
            double z = getTarget().getZ() - getZ();
            float yRotD = (float) (-(Mth.atan2(x, z) * Mth.RAD_TO_DEG));
            if (Math.abs(Mth.degreesDifference(yBodyRot, yRotD)) > 130) {
                return getAllAnimations().get(random.nextInt(2) == 0 ? ATTACK_BACK1 : ATTACK_BACK2);
            }
        }
        return getAllAnimations().get(ATTACK_FRONT);
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
        return ModSounds.ANKYLOSAURUS_AMBIENT.get();
    }

    @Nullable
    @Override
    protected SoundEvent getHurtSound(DamageSource damageSource) {
        return ModSounds.ANKYLOSAURUS_HURT.get();
    }

    @Nullable
    @Override
    protected SoundEvent getDeathSound() {
        return ModSounds.ANKYLOSAURUS_DEATH.get();
    }
}