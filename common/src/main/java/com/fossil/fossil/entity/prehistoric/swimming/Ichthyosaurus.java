package com.fossil.fossil.entity.prehistoric.swimming;

import com.fossil.fossil.entity.ai.*;
import com.fossil.fossil.entity.prehistoric.base.PrehistoricEntityInfo;
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

public class Ichthyosaurus extends PrehistoricSwimming {
    public static final String ANIMATIONS = "ichthyosaurus.animation.json";
    public static final String ATTACK = "animation.ichthyosaurus.attack";
    public static final String BEACHED = "animation.ichthyosaurus.beached";
    public static final String EAT = "animation.ichthyosaurus.eat";
    public static final String IDLE = "animation.ichthyosaurus.idle";
    public static final String SLEEP1 = "animation.ichthyosaurus.sleep1";
    public static final String SLEEP2 = "animation.ichthyosaurus.sleep2";
    public static final String SWIM = "animation.ichthyosaurus.swim";
    public static final String SWIM_FAST = "animation.ichthyosaurus.swim_fast";

    private final AnimationFactory factory = GeckoLibUtil.createFactory(this);

    public Ichthyosaurus(EntityType<Ichthyosaurus> entityType, Level level) {
        super(entityType, level);
        hasTeenTexture = false;
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        goalSelector.addGoal(0, new DelayedAttackGoal(this, 1, false));
        goalSelector.addGoal(4, new MakeFishGoal(this));
        targetSelector.addGoal(1, new DinoOwnerHurtByTargetGoal(this));
        targetSelector.addGoal(2, new DinoOwnerHurtTargetGoal(this));
        targetSelector.addGoal(3, new DinoHurtByTargetGoal(this));
    }

    @Override
    public boolean isAmphibious() {
        return false;
    }

    @Override
    public float swimSpeed() {
        return 0.75f;
    }

    //TODO: Bucket
    /*@Override
    public @NotNull ItemStack getBucketItemStack() {
        return new ItemStack(ModItems.COELACANTH_BUCKET.get());
    }*/

    @Override
    public PrehistoricEntityInfo info() {
        return PrehistoricEntityInfo.ICHTHYOSAURUS;
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
    public @NotNull Animation nextAttackAnimation() {
        return getAllAnimations().get(ATTACK);
    }

    @Override
    public @NotNull Animation nextBeachedAnimation() {
        return getAllAnimations().get(BEACHED);
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
        return getAllAnimations().get(SWIM);
    }

    @Override
    public @NotNull Animation nextSprintingAnimation() {
        return getAllAnimations().get(SWIM_FAST);
    }

    @Override
    public AnimationFactory getFactory() {
        return factory;
    }

    @Nullable
    @Override
    protected SoundEvent getAmbientSound() {
        return isInWater() ? ModSounds.ICHTHYOSAURUS_AMBIENT.get() : ModSounds.ICHTHYOSAURUS_OUTSIDE.get();
    }

    @Nullable
    @Override
    protected SoundEvent getHurtSound(DamageSource damageSource) {
        return ModSounds.ICHTHYOSAURUS_HURT.get();
    }

    @Nullable
    @Override
    protected SoundEvent getDeathSound() {
        return ModSounds.ICHTHYOSAURUS_DEATH.get();
    }
}