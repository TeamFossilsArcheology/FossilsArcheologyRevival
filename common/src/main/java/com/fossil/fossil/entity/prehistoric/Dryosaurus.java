package com.fossil.fossil.entity.prehistoric;

import com.fossil.fossil.entity.ai.*;
import com.fossil.fossil.entity.prehistoric.base.Prehistoric;
import com.fossil.fossil.entity.prehistoric.base.PrehistoricEntityInfo;
import com.fossil.fossil.sounds.ModSounds;
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

public class Dryosaurus extends Prehistoric {
    public static final String ANIMATIONS = "dryosaurus.animation.json";
    public static final String ATTACK = "animation.dryosaurus.attack";
    public static final String EAT = "animation.dryosaurus.eat";
    public static final String FALL = "animation.dryosaurus.jump/fall";
    public static final String IDLE = "animation.dryosaurus.idle";
    public static final String RUN = "animation.dryosaurus.run";
    public static final String SLEEP = "animation.dryosaurus.sleep1";
    public static final String SWIM = "animation.dryosaurus.swim";
    public static final String WALK = "animation.dryosaurus.walk";

    private final AnimationFactory factory = GeckoLibUtil.createFactory(this);

    public Dryosaurus(EntityType<Dryosaurus> entityType, Level level) {
        super(entityType, level);
        hasTeenTexture = false;
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        goalSelector.addGoal(0, new FleeBattleGoal(this, 1));
        goalSelector.addGoal(1, new DinoMeleeAttackGoal(this, 1, false));
        goalSelector.addGoal(1, new FloatGoal(this));
        goalSelector.addGoal(3, new DinoWanderGoal(this, 1));
        goalSelector.addGoal(6, new DinoFollowOwnerGoal(this, 1, 10, 2, false));
        goalSelector.addGoal(7, new DinoLookAroundGoal(this));
    }

    @Override
    public PrehistoricEntityInfo info() {
        return PrehistoricEntityInfo.DRYOSAURUS;
    }

    @Override
    public Item getOrderItem() {
        return Items.STICK;
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
        return ModSounds.DRYOSAURUS_AMBIENT.get();
    }

    @Nullable
    @Override
    protected SoundEvent getHurtSound(DamageSource damageSource) {
        return ModSounds.DRYOSAURUS_HURT.get();
    }

    @Nullable
    @Override
    protected SoundEvent getDeathSound() {
        return ModSounds.DRYOSAURUS_DEATH.get();
    }
}