package com.fossil.fossil.entity.prehistoric;

import com.fossil.fossil.entity.ai.*;
import com.fossil.fossil.entity.prehistoric.base.PrehistoricEntityType;
import com.fossil.fossil.entity.prehistoric.base.PrehistoricSwimming;
import com.fossil.fossil.sounds.ModSounds;
import net.minecraft.sounds.SoundEvent;
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

public class Henodus extends PrehistoricSwimming {
    public static final String ANIMATIONS = "henodus.animation.json";
    public static final String EAT = "animation.henodus.drink/eat";
    public static final String IDLE = "animation.henodus.idle";
    public static final String RUN = "animation.henodus.run";
    public static final String SIT = "animation.henodus.sit";
    public static final String SWIM = "animation.henodus.swim";
    public static final String SWIM_FAST = "animation.henodus.swimfast";
    public static final String WALK = "animation.henodus.walk";

    private final AnimationFactory factory = GeckoLibUtil.createFactory(this);

    public Henodus(EntityType<Henodus> entityType, Level level) {
        super(entityType, level);
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        goalSelector.addGoal(0, new EnterWaterWithoutTargetGoal(this, 1));
        goalSelector.addGoal(1, new DinoMeleeAttackGoal(this, 1, false));
        goalSelector.addGoal(1, new EnterWaterWithTargetGoal(this, 1));
        goalSelector.addGoal(6, new DinoFollowOwnerGoal(this, 1, 10, 2, false));
        goalSelector.addGoal(7, new DinoLookAroundGoal(this));
        targetSelector.addGoal(1, new DinoOwnerHurtByTargetGoal(this));
        targetSelector.addGoal(2, new DinoOwnerHurtTargetGoal(this));
        targetSelector.addGoal(3, new DinoHurtByTargetGoal(this));
    }

    @Override
    public boolean isAmphibious() {
        return true;
    }

    @Override
    public float swimSpeed() {
        return 0.2f;
    }

    @Override
    public PrehistoricEntityType type() {
        return PrehistoricEntityType.HENODUS;
    }

    @Override
    public Item getOrderItem() {
        return Items.STICK;
    }

    @Override
    protected boolean canHuntMobsOnLand() {
        return false;
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
        return getAllAnimations().get(IDLE);
    }

    @Override
    public AnimationFactory getFactory() {
        return factory;
    }

    @Nullable
    @Override
    protected SoundEvent getAmbientSound() {
        return ModSounds.HENODUS_AMBIENT.get();
    }

    @Nullable
    @Override
    protected SoundEvent getHurtSound(DamageSource damageSource) {
        return ModSounds.HENODUS_HURT.get();
    }

    @Nullable
    @Override
    protected SoundEvent getDeathSound() {
        return ModSounds.HENODUS_DEATH.get();
    }
}