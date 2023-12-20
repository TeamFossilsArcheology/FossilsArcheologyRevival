package com.fossil.fossil.entity.prehistoric;

import com.fossil.fossil.entity.ai.*;
import com.fossil.fossil.entity.prehistoric.base.Prehistoric;
import com.fossil.fossil.entity.prehistoric.base.PrehistoricEntityType;
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

public class Stegosaurus extends Prehistoric {
    public static final String ANIMATIONS = "stegosaurus.animation.json";
    public static final String IDLE = "animation.stegosaurus.idle";
    public static final String SIT1 = "animation.stegosaurus.sit1";
    public static final String SIT2 = "animation.stegosaurus.sit2";
    public static final String SLEEP1 = "animation.stegosaurus.sleep1";
    public static final String SLEEP2 = "animation.stegosaurus.sleep2";
    public static final String WALK = "animation.stegosaurus.walk";
    public static final String RUN = "animation.stegosaurus.run";
    public static final String JUMP_FALL = "animation.stegosaurus.jump/fall";
    public static final String SWIM = "animation.stegosaurus.swim";
    public static final String EAT = "animation.stegosaurus.eat";
    public static final String TURN_RIGHT = "animation.stegosaurus.turn_right";
    public static final String TURN_LEFT = "animation.stegosaurus.turn_left";
    public static final String SPEAK = "animation.stegosaurus.speak";
    public static final String CALL1 = "animation.stegosaurus.call1";
    public static final String ATTACK_FRONT1 = "animation.stegosaurus.attack_front1";
    public static final String ATTACK_FRONT2 = "animation.stegosaurus.attack_front2";

    private final AnimationFactory factory = GeckoLibUtil.createFactory(this);

    public Stegosaurus(EntityType<Stegosaurus> entityType, Level level) {
        super(entityType, level);
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
        targetSelector.addGoal(1, new DinoOwnerHurtByTargetGoal(this));
        targetSelector.addGoal(2, new DinoOwnerHurtTargetGoal(this));
        targetSelector.addGoal(3, new DinoHurtByTargetGoal(this));
    }

    @Override
    public PrehistoricEntityType type() {
        return PrehistoricEntityType.STEGOSAURUS;
    }

    @Override
    public Item getOrderItem() {
        return Items.STICK;
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
        //return getRandom().nextInt(2) == 0 ? getAllAnimations().get(SLEEP1) : getAllAnimations().get(SLEEP2);
        return getAllAnimations().get(SLEEP1);
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
        return getAllAnimations().get(ATTACK_FRONT1);
    }

    @Override
    public AnimationFactory getFactory() {
        return factory;
    }

    @Nullable
    @Override
    protected SoundEvent getAmbientSound() {
        return ModSounds.STEGOSAURUS_AMBIENT.get();
    }

    @Nullable
    @Override
    protected SoundEvent getHurtSound(DamageSource damageSource) {
        return ModSounds.STEGOSAURUS_HURT.get();
    }

    @Nullable
    @Override
    protected SoundEvent getDeathSound() {
        return ModSounds.STEGOSAURUS_DEATH.get();
    }
}