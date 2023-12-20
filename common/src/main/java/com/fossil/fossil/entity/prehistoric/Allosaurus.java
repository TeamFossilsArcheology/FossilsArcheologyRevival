package com.fossil.fossil.entity.prehistoric;

import com.fossil.fossil.entity.ai.*;
import com.fossil.fossil.entity.prehistoric.base.Prehistoric;
import com.fossil.fossil.entity.prehistoric.base.PrehistoricEntityType;
import com.fossil.fossil.entity.prehistoric.base.PrehistoricScary;
import com.fossil.fossil.item.ModItems;
import com.fossil.fossil.sounds.ModSounds;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import org.apache.commons.lang3.tuple.Pair;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib3.core.builder.Animation;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;
import software.bernie.geckolib3.core.processor.IBone;
import software.bernie.geckolib3.core.snapshot.BoneSnapshot;
import software.bernie.geckolib3.util.GeckoLibUtil;

import java.util.Map;

public class Allosaurus extends Prehistoric implements PrehistoricScary {
    public static final String ANIMATIONS = "allosaurus.animation.json";
    public static final String IDLE = "animation.allosaurus.idle";
    public static final String WALK = "animation.allosaurus.walk";
    public static final String ATTACK1 = "animation.allosaurus.attack";

    private final AnimationFactory factory = GeckoLibUtil.createFactory(this);

    public Allosaurus(EntityType<Allosaurus> entityType, Level level) {
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
        return PrehistoricEntityType.ALLOSAURUS;
    }

    @Override
    public double getPassengersRidingOffset() {
        if (level.isClientSide) {
            AnimationData data = factory.getOrCreateAnimationData(getId());
            Map<String, Pair<IBone, BoneSnapshot>> map = data.getBoneSnapshotCollection();
            double offset = 0.5;
            //return super.getPassengersRidingOffset() + map.get("lowerBody").getRight().positionOffsetY - offset;
        }
        return super.getPassengersRidingOffset();
    }

    @Override
    public Item getOrderItem() {
        return ModItems.SKULL_STICK.get();
    }

    @Override
    public float getTargetScale() {
        return 2;
    }

    @Override
    public @NotNull Animation nextIdleAnimation() {
        return getAllAnimations().get(IDLE);
    }

    @Override
    public @NotNull Animation nextEatingAnimation() {
        return getAllAnimations().get(IDLE);
    }

    @Override
    public @NotNull Animation nextMovingAnimation() {
        return getAllAnimations().get(WALK);
    }

    @Override
    public @NotNull Animation nextChasingAnimation() {
        return getAllAnimations().get(WALK);
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
        return ModSounds.ALLOSAURUS_AMBIENT.get();
    }

    @Nullable
    @Override
    protected SoundEvent getHurtSound(DamageSource damageSource) {
        return ModSounds.ALLOSAURUS_HURT.get();
    }

    @Nullable
    @Override
    protected SoundEvent getDeathSound() {
        return ModSounds.ALLOSAURUS_DEATH.get();
    }
}