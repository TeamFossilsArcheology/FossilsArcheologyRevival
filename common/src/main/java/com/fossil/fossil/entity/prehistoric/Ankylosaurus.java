package com.fossil.fossil.entity.prehistoric;

import com.fossil.fossil.entity.ai.DelayedAttackGoal;
import com.fossil.fossil.entity.ai.FleeBattleGoal;
import com.fossil.fossil.entity.prehistoric.base.Prehistoric;
import com.fossil.fossil.entity.prehistoric.base.PrehistoricEntityInfo;
import com.fossil.fossil.sounds.ModSounds;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.Mth;
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

public class Ankylosaurus extends Prehistoric {
    public static final String ANIMATIONS = "ankylosaurus.animation.json";
    public static final String ATTACK_FRONT_RIGHT = "animation.ankylosaurus.attack_strong_right";
    public static final String ATTACK_FRONT_LEFT = "animation.ankylosaurus.attack_strong_left";
    public static final String ATTACK_BACK_RIGHT = "animation.ankylosaurus.attack_back1";
    public static final String ATTACK_BACK_LEFT = "animation.ankylosaurus.attack_back2";
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
        goalSelector.addGoal(1, new DelayedAttackGoal(this, 1, false));
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
            double yawDiff = (Mth.atan2(z, x) * Mth.RAD_TO_DEG);
            float yRotD = Mth.degreesDifference(yBodyRot, (float) Mth.wrapDegrees(yawDiff - 90));
            return getAllAnimations().get(yRotD < 0 ? ATTACK_FRONT_RIGHT : ATTACK_FRONT_LEFT);
        }
        /*Vec3d right = getRotationVector().rotateY(90 * MathHelper.RADIANS_PER_DEGREE).normalize();
        if(Math.abs(right.dotProduct(entity.getPos().subtract(getPos()))) > 1.75f)
            return false;*/
        return getAllAnimations().get(ATTACK_FRONT_RIGHT);
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