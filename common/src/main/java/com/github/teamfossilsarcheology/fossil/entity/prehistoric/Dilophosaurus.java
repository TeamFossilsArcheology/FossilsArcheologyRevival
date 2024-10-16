package com.github.teamfossilsarcheology.fossil.entity.prehistoric;

import com.github.teamfossilsarcheology.fossil.entity.ai.DelayedAttackGoal;
import com.github.teamfossilsarcheology.fossil.entity.prehistoric.base.Prehistoric;
import com.github.teamfossilsarcheology.fossil.entity.prehistoric.base.PrehistoricEntityInfo;
import com.github.teamfossilsarcheology.fossil.entity.util.Util;
import com.github.teamfossilsarcheology.fossil.sounds.ModSounds;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib3.core.builder.Animation;

public class Dilophosaurus extends Prehistoric {
    public static final String ANIMATIONS = "dilophosaurus.animation.json";
    public static final String ATTACK1 = "animation.dilophosaurus.attack1";
    public static final String ATTACK2 = "animation.dilophosaurus.attack2";
    public static final String ATTACK3 = "animation.dilophosaurus.attack3";
    public static final String CALL = "animation.dilophosaurus.call";
    public static final String EAT = "animation.dilophosaurus.eat";
    public static final String FALL = "animation.dilophosaurus.jump/fall";
    public static final String IDLE = "animation.dilophosaurus.idle";
    public static final String INFLATE_START = "animation.dilophosaurus.inflate_start";
    public static final String INFLATE_HOLD = "animation.dilophosaurus.inflate_hold";
    public static final String INFLATE_END = "animation.dilophosaurus.inflate_end";
    public static final String RUN = "animation.dilophosaurus.run";
    public static final String SIT1 = "animation.dilophosaurus.sit1";
    public static final String SIT2 = "animation.dilophosaurus.sit2";
    public static final String SLEEP1 = "animation.dilophosaurus.sleep1";
    public static final String SLEEP2 = "animation.dilophosaurus.sleep2";
    public static final String SPEAK = "animation.dilophosaurus.speak";
    public static final String SWIM = "animation.dilophosaurus.swim";
    public static final String WALK = "animation.dilophosaurus.walk";


    public Dilophosaurus(EntityType<Dilophosaurus> type, Level level) {
        super(type, level);
    }

    @Override
    public void registerGoals() {
        super.registerGoals();
        double speed = getAttributeValue(Attributes.MOVEMENT_SPEED);
        goalSelector.addGoal(Util.ATTACK, new DelayedAttackGoal(this, speed, false));
    }

    @Override
    public @NotNull EntityDimensions getDimensions(Pose poseIn) {
        if (poseIn == Pose.SLEEPING) {
            return super.getDimensions(poseIn).scale(1, 0.5f);
        }
        return super.getDimensions(poseIn);
    }

    @Override
    public PrehistoricEntityInfo info() {
        return PrehistoricEntityInfo.DILOPHOSAURUS;
    }

    @Override
    public Item getOrderItem() {
        return Items.BONE;
    }

    @Override
    public double getPassengersRidingOffset() {
        return super.getPassengersRidingOffset() - 0.25;
    }

    @Override
    public @NotNull Animation nextAttackAnimation() {
        String key;
        switch (getRandom().nextInt(3)) {
            case 0 -> key = ATTACK1;
            case 1 -> key = ATTACK2;
            default -> key = ATTACK3;
        }
        return getAllAnimations().get(key);
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
        return getAllAnimations().get(random.nextInt(2) == 0 ? SIT1 : SIT2);
    }

    @Override
    public @NotNull Animation nextSleepingAnimation() {
        return getAllAnimations().get(random.nextInt(2) == 0 ? SLEEP1 : SLEEP2);
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
        return getAllAnimations().get(RUN);
    }

    @Nullable
    @Override
    protected SoundEvent getAmbientSound() {
        return ModSounds.DILOPHOSAURUS_AMBIENT.get();
    }

    @Nullable
    @Override
    protected SoundEvent getHurtSound(DamageSource damageSource) {
        return ModSounds.DILOPHOSAURUS_HURT.get();
    }

    @Nullable
    @Override
    protected SoundEvent getDeathSound() {
        return ModSounds.DILOPHOSAURUS_DEATH.get();
    }
}