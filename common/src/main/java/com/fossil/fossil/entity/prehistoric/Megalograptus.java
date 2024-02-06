package com.fossil.fossil.entity.prehistoric;

import com.fossil.fossil.entity.ai.*;
import com.fossil.fossil.entity.prehistoric.base.PrehistoricEntityInfo;
import com.fossil.fossil.entity.prehistoric.base.PrehistoricSwimming;
import com.fossil.fossil.sounds.ModSounds;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib3.core.builder.Animation;
import software.bernie.geckolib3.core.manager.AnimationFactory;
import software.bernie.geckolib3.util.GeckoLibUtil;

public class Megalograptus extends PrehistoricSwimming {
    public static final String ANIMATIONS = "megalograptus.animation.json";
    public static final String ATTACK = "animation.megalograptus.Attack1";
    public static final String EAT = "animation.megalograptus.Eating";
    public static final String FALL = "animation.megalograptus.jump/fall";
    public static final String IDLE = "animation.megalograptus.Idle/sleep/sit";
    public static final String SLEEP = "animation.megalograptus.Idle/sleep/sit";
    public static final String SWIM = "animation.megalograptus.Swim";
    public static final String WALK = "animation.megalograptus.walk";

    private final AnimationFactory factory = GeckoLibUtil.createFactory(this);

    public Megalograptus(EntityType<Megalograptus> entityType, Level level) {
        super(entityType, level);
        hasTeenTexture = false;
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        goalSelector.addGoal(0, new EnterWaterWithoutTargetGoal(this, 1));
        goalSelector.addGoal(1, new DinoMeleeAttackGoal(this, 1, false));
        goalSelector.addGoal(1, new EnterWaterWithTargetGoal(this, 1));
        goalSelector.addGoal(3, new DinoWanderGoal(this, 1));
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
        return 0.5f;
    }

    //TODO: Bucket
    /*@Override
    public @NotNull ItemStack getBucketItemStack() {
        return new ItemStack(ModItems.COELACANTH_BUCKET.get());
    }*/

    @Override
    public PrehistoricEntityInfo info() {
        return PrehistoricEntityInfo.MEGALOGRAPTUS;
    }

    @Override
    public @NotNull MobType getMobType() {
        return MobType.ARTHROPOD;
    }

    @Override
    public Item getOrderItem() {
        return Items.NAUTILUS_SHELL;
    }

    @Override
    public @NotNull Animation nextAttackAnimation() {
        return getAllAnimations().get(ATTACK);
    }

    @Override
    public @NotNull Animation nextBeachedAnimation() {
        return nextIdleAnimation();
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
        return getAllAnimations().get(IDLE);
    }

    @Override
    public AnimationFactory getFactory() {
        return factory;
    }

    @Nullable
    @Override
    protected SoundEvent getAmbientSound() {
        return ModSounds.MEGALOGRAPTUS_AMBIENT.get();
    }

    @Nullable
    @Override
    protected SoundEvent getHurtSound(DamageSource damageSource) {
        return ModSounds.MEGALOGRAPTUS_HURT.get();
    }

    @Nullable
    @Override
    protected SoundEvent getDeathSound() {
        return ModSounds.MEGALOGRAPTUS_DEATH.get();
    }
}