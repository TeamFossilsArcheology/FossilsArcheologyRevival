package com.fossil.fossil.entity.prehistoric;

import com.fossil.fossil.entity.ai.*;
import com.fossil.fossil.entity.prehistoric.base.Prehistoric;
import com.fossil.fossil.entity.prehistoric.base.PrehistoricEntityType;
import com.fossil.fossil.entity.prehistoric.base.PrehistoricScary;
import com.fossil.fossil.item.ModItems;
import com.fossil.fossil.sounds.ModSounds;
import com.fossil.fossil.util.Gender;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib3.core.builder.Animation;
import software.bernie.geckolib3.core.manager.AnimationFactory;
import software.bernie.geckolib3.util.GeckoLibUtil;

public class Megalania extends Prehistoric implements PrehistoricScary {
    public static final String ANIMATIONS = "megalania.animation.json";
    public static final String ATTACK = "animation.megalania.attack";
    public static final String EAT = "animation.megalania.eat";
    public static final String FALL = "animation.megalania.jump/fall";
    public static final String IDLE = "animation.megalania.idle";
    public static final String RUN = "animation.megalania.run";
    public static final String SLEEP = "animation.megalania.sleep1";
    public static final String SWIM = "animation.megalania.swim";
    public static final String WALK = "animation.megalania.walk";

    private final AnimationFactory factory = GeckoLibUtil.createFactory(this);

    public Megalania(EntityType<Megalania> entityType, Level level) {
        super(entityType, level);
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        goalSelector.addGoal(0, new DinoMeleeAttackGoal(this, 1, false));
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
        return PrehistoricEntityType.MEGALANIA;
    }

    @Override
    public Item getOrderItem() {
        return ModItems.SKULL_STICK.get();
    }

    @Override
    public float getGenderedScale() {
        return getGender() == Gender.MALE ? 1.15f : super.getGenderedScale();
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
    public AnimationFactory getFactory() {
        return factory;
    }

    @Nullable
    @Override
    protected SoundEvent getAmbientSound() {
        return ModSounds.MEGALANIA_AMBIENT.get();
    }

    @Nullable
    @Override
    protected SoundEvent getHurtSound(DamageSource damageSource) {
        return ModSounds.MEGALANIA_HURT.get();
    }

    @Nullable
    @Override
    protected SoundEvent getDeathSound() {
        return ModSounds.MEGALANIA_DEATH.get();
    }
}