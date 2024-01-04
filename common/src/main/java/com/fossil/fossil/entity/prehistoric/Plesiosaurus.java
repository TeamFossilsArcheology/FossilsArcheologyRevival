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

public class Plesiosaurus extends PrehistoricSwimming {
    public static final String ANIMATIONS = "plesiosaurus.animation.json";
    public static final String ATTACK = "animation.plesiosaurus.attack";
    public static final String BEACHED = "animation.plesiosaurus.beached";
    public static final String EAT = "animation.plesiosaurus.eat";
    public static final String FALL = "animation.plesiosaurus.jump/fall";
    public static final String IDLE = "animation.plesiosaurus.randomidle";
    public static final String SWIM = "animation.plesiosaurus.swim";
    public static final String SWIM_FAST = "animation.plesiosaurus.swimfast";

    private final AnimationFactory factory = GeckoLibUtil.createFactory(this);

    public Plesiosaurus(EntityType<Plesiosaurus> entityType, Level level) {
        super(entityType, level);
        hasTeenTexture = false;
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        goalSelector.addGoal(0, new EnterWaterWithoutTargetGoal(this, 1));
        goalSelector.addGoal(0, new DinoFollowOwnerGoal(this, 1, 10, 2, false));
        goalSelector.addGoal(1, new EnterWaterWithTargetGoal(this, 1));
        goalSelector.addGoal(1, new DinoMeleeAttackGoal(this, 1, false));
        goalSelector.addGoal(4, new MakeFishGoal(this));
        goalSelector.addGoal(7, new DinoLookAroundGoal(this));
        targetSelector.addGoal(3, new DinoHurtByTargetGoal(this));
    }

    @Override
    public boolean isAmphibious() {
        return false;
    }

    @Override
    public float swimSpeed() {
        return 1;
    }

    @Override
    public PrehistoricEntityType type() {
        return PrehistoricEntityType.PLESIOSAURUS;
    }

    @Override
    public Item getOrderItem() {
        return Items.NAUTILUS_SHELL;
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
        return getAllAnimations().get(SWIM);
    }

    @Override
    public @NotNull Animation nextChasingAnimation() {
        return getAllAnimations().get(SWIM_FAST);
    }

    @Override
    public @NotNull Animation nextAttackAnimation() {
        return getAllAnimations().get(ATTACK);
    }

    @Override
    public @Nullable Animation nextFloppingAnimation() {
        return getAllAnimations().get(BEACHED);
    }

    @Override
    public AnimationFactory getFactory() {
        return factory;
    }

    @Nullable
    @Override
    protected SoundEvent getAmbientSound() {
        return isInWater() ? ModSounds.PLESIOSAURUS_AMBIENT_INSIDE.get() : ModSounds.PLESIOSAURUS_AMBIENT_OUTSIDE.get();
    }

    @Nullable
    @Override
    protected SoundEvent getHurtSound(DamageSource damageSource) {
        return ModSounds.PLESIOSAURUS_HURT.get();
    }

    @Nullable
    @Override
    protected SoundEvent getDeathSound() {
        return ModSounds.PLESIOSAURUS_DEATH.get();
    }
}