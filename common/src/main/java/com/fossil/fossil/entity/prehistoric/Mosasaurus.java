package com.fossil.fossil.entity.prehistoric;

import com.fossil.fossil.entity.ai.*;
import com.fossil.fossil.entity.prehistoric.base.Prehistoric;
import com.fossil.fossil.entity.prehistoric.base.PrehistoricEntityType;
import com.fossil.fossil.entity.prehistoric.base.PrehistoricSwimming;
import com.fossil.fossil.item.ModItems;
import com.fossil.fossil.sounds.ModSounds;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib3.core.builder.Animation;
import software.bernie.geckolib3.core.manager.AnimationFactory;
import software.bernie.geckolib3.util.GeckoLibUtil;

public class Mosasaurus extends PrehistoricSwimming {
    public static final String ANIMATIONS = "mosasaurus.animation.json";
    public static final String IDLE = "animation.dilophosaurus.idle";
    public static final String ATTACK1 = "animation.dilophosaurus.attack1";
    private final AnimationFactory factory = GeckoLibUtil.createFactory(this);

    public Mosasaurus(EntityType<Mosasaurus> entityType, Level level) {
        super(entityType, level, false);
        hasTeenTexture = false;
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Prehistoric.createAttributes().add(Attributes.FOLLOW_RANGE, 64);
    }

    @Override
    public Entity[] getCustomParts() {
        return new Entity[0];
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        goalSelector.addGoal(0, new EnterWaterWithoutTargetGoal(this, 1));
        goalSelector.addGoal(0, new DinoFollowOwnerGoal(this, 1, 10, 2, false));
        goalSelector.addGoal(1, new GrabMeleeAttackGoal(this, 1, false));
        goalSelector.addGoal(2, new BreachAttackGoal(this, 1));
        goalSelector.addGoal(1, new EnterWaterWithTargetGoal(this, 1));
        goalSelector.addGoal(7, new DinoLookAroundGoal(this));
        targetSelector.addGoal(3, new DinoHurtByTargetGoal(this));
    }

    @Override
    public boolean isAmphibious() {
        return false;
    }

    @Override
    public float swimSpeed() {
        return 1.2f;
    }

    @Override
    public boolean canDoBreachAttack() {
        return true;
    }

    @Override
    public PrehistoricEntityType type() {
        return PrehistoricEntityType.MOSASAURUS;
    }

    @Override
    public Item getOrderItem() {
        return ModItems.SKULL_STICK.get();
    }

    @Override
    public boolean doHurtTarget(Entity target) {
        return true;
    }

    @Override
    public float getTargetScale() {
        return 2;
    }

    @Override
    protected boolean canHuntMobsOnLand() {
        return false;
    }

    @Override
    public @NotNull Animation nextEatingAnimation() {
        return getAllAnimations().get(IDLE);
    }

    @Override
    public @NotNull Animation nextIdleAnimation() {
        return getAllAnimations().get(IDLE);
    }

    @Override
    public @NotNull Animation nextMovingAnimation() {
        return getAllAnimations().get(IDLE);
    }

    @Override
    public @NotNull Animation nextChasingAnimation() {
        return getAllAnimations().get(IDLE);
    }

    @Override
    public @NotNull Animation nextAttackAnimation() {
        return getAllAnimations().get(ATTACK1);
    }

    @Override
    public AnimationFactory getFactory() {
        return factory;
    }

    @Nullable
    @Override
    protected SoundEvent getAmbientSound() {
        return isInWater() ? ModSounds.MOSASAURUS_AMBIENT_INSIDE.get() : ModSounds.MOSASAURUS_AMBIENT_OUTSIDE.get();
    }

    @Nullable
    @Override
    protected SoundEvent getHurtSound(DamageSource damageSource) {
        return ModSounds.MOSASAURUS_HURT.get();
    }

    @Nullable
    @Override
    protected SoundEvent getDeathSound() {
        return ModSounds.MOSASAURUS_DEATH.get();
    }
}