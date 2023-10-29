package com.fossil.fossil.entity.prehistoric;

import com.fossil.fossil.entity.ai.*;
import com.fossil.fossil.entity.prehistoric.base.PrehistoricEntityType;
import com.fossil.fossil.entity.prehistoric.base.PrehistoricSwimming;
import com.fossil.fossil.entity.prehistoric.parts.PrehistoricPart;
import com.fossil.fossil.sounds.ModSounds;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib3.core.builder.Animation;
import software.bernie.geckolib3.core.manager.AnimationFactory;
import software.bernie.geckolib3.util.GeckoLibUtil;

public class Ichthyosaurus extends PrehistoricSwimming {
    public static final String ANIMATIONS = "ichthyosaurus.animation.json";
    public static final String IDLE = "animation.ichthyosaurus.idle";
    public static final String SWIM = "animation.ichthyosaurus.swim";
    public static final String SWIM_FAST = "animation.ichthyosaurus.swim_fast";
    public static final String EAT = "animation.ichthyosaurus.eat";
    public static final String ATTACK = "animation.ichthyosaurus.attack";
    
    private final AnimationFactory factory = GeckoLibUtil.createFactory(this);
    private final Entity[] parts = new Entity[3];

    public Ichthyosaurus(EntityType<Ichthyosaurus> entityType, Level level) {
        super(entityType, level, false);
        var head = PrehistoricPart.get(this, 1, 0.7f);
        var body = PrehistoricPart.get(this, 1.2f, 1);
        var tail = PrehistoricPart.get(this, 1, 0.5f);
        this.parts[0] = body;
        this.parts[1] = head;
        this.parts[2] = tail;
    }

    @Override
    protected void tickCustomParts() {
        Vec3 view = calculateViewVector(0, yBodyRot);
        Vec3 offset = view.scale(0.2 * getScale());
        parts[0].setPos(getX() + offset.x, getY() + offset.y, getZ() + offset.z);

        Vec3 offsetHor = view.scale(getBbWidth() - (getBbWidth() - parts[1].getBbWidth()) / 2);
        parts[1].setPos(getX() + offset.x + offsetHor.x, getY(), getZ() + offset.z + offsetHor.z);

        offsetHor = view.scale(getBbWidth() - (getBbWidth() - parts[2].getBbWidth()) / 2).reverse();
        parts[2].setPos(getX() + offset.x + offsetHor.x, getY(), getZ() + offset.z + offsetHor.z);
    }

    @Override
    public Entity[] getCustomParts() {
        return parts;
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

    @Override
    public PrehistoricEntityType type() {
        return PrehistoricEntityType.ICHTHYOSAURUS;
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
        return getAllAnimations().get(SWIM);
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