package com.fossil.fossil.entity.prehistoric.swimming;

import com.fossil.fossil.entity.ai.DelayedAttackGoal;
import com.fossil.fossil.entity.ai.DinoHurtByTargetGoal;
import com.fossil.fossil.entity.ai.DinoOwnerHurtByTargetGoal;
import com.fossil.fossil.entity.ai.DinoOwnerHurtTargetGoal;
import com.fossil.fossil.entity.prehistoric.base.PrehistoricEntityInfo;
import com.fossil.fossil.entity.prehistoric.base.PrehistoricSwimmingBucketable;
import com.fossil.fossil.entity.util.Util;
import com.fossil.fossil.item.ModItems;
import com.fossil.fossil.sounds.ModSounds;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib3.core.builder.Animation;
import software.bernie.geckolib3.core.manager.AnimationFactory;
import software.bernie.geckolib3.util.GeckoLibUtil;

public class Crassigyrinus extends PrehistoricSwimmingBucketable {
    public static final String ANIMATIONS = "crassigyrinus.animation.json";
    public static final String ATTACK = "animation.crassigyrinus.attack";
    public static final String BEACHED = "animation.crassigyrinus.idle/beached";
    public static final String EAT = "animation.crassigyrinus.eat";
    public static final String FALL = "animation.crassigyrinus.jump/fall";
    public static final String IDLE = "animation.crassigyrinus.swimidle";
    public static final String SLEEP = "animation.crassigyrinus.sleep/sit";
    public static final String SWIM = "animation.crassigyrinus.swim";
    public static final String SWIM_FAST = "animation.crassigyrinus.swimfast";

    private final AnimationFactory factory = GeckoLibUtil.createFactory(this);

    public Crassigyrinus(EntityType<Crassigyrinus> entityType, Level level) {
        super(entityType, level);
        hasTeenTexture = false;
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        goalSelector.addGoal(Util.ATTACK, new DelayedAttackGoal(this, 1, false));
        targetSelector.addGoal(1, new DinoOwnerHurtByTargetGoal(this));
        targetSelector.addGoal(2, new DinoOwnerHurtTargetGoal(this));
        targetSelector.addGoal(3, new DinoHurtByTargetGoal(this));
    }

    @Override
    public @NotNull ItemStack getBucketItemStack() {
        return new ItemStack(ModItems.CRASSIGYRINUS_BUCKET.get());
    }

    @Override
    public PrehistoricEntityInfo info() {
        return PrehistoricEntityInfo.CRASSIGYRINUS;
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
    public @NotNull Animation nextSittingAnimation() {
        return getAllAnimations().get(SLEEP);
    }

    @Override
    public @NotNull Animation nextSleepingAnimation() {
        return getAllAnimations().get(SLEEP);
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
        return isInWater() ? ModSounds.TIKTAALIK_AMBIENT.get() : ModSounds.HENODUS_AMBIENT.get();
    }

    @Nullable
    @Override
    protected SoundEvent getHurtSound(DamageSource damageSource) {
        return ModSounds.TIKTAALIK_HURT.get();
    }

    @Nullable
    @Override
    protected SoundEvent getDeathSound() {
        return ModSounds.TIKTAALIK_DEATH.get();
    }

    @Override
    protected float getSoundVolume() {
        return super.getSoundVolume() * 0.75f;
    }

    @Override
    public float getVoicePitch() {
        return super.getVoicePitch() * 1.1f;
    }
}
