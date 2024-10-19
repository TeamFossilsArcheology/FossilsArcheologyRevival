package com.github.teamfossilsarcheology.fossil.entity.prehistoric;

import com.github.teamfossilsarcheology.fossil.entity.ai.DelayedAttackGoal;
import com.github.teamfossilsarcheology.fossil.entity.ai.FleeBattleGoal;
import com.github.teamfossilsarcheology.fossil.entity.prehistoric.base.OrderType;
import com.github.teamfossilsarcheology.fossil.entity.prehistoric.base.Prehistoric;
import com.github.teamfossilsarcheology.fossil.entity.prehistoric.base.PrehistoricEntityInfo;
import com.github.teamfossilsarcheology.fossil.entity.prehistoric.base.PrehistoricEntityInfoAI;
import com.github.teamfossilsarcheology.fossil.entity.util.Util;
import com.github.teamfossilsarcheology.fossil.sounds.ModSounds;
import com.github.teamfossilsarcheology.fossil.util.Gender;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.goal.target.TargetGoal;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib3.core.builder.Animation;

public class Megaloceros extends Prehistoric {
    public static final String ANIMATIONS = "megaloceros.animation.json";
    public static final String ATTACK = "animation.megaloceros.attack";
    public static final String EAT = "animation.megaloceros.eat";
    public static final String FALL = "animation.megaloceros.jump/fall";
    public static final String IDLE = "animation.megaloceros.idle";
    public static final String IDLE2 = "animation.megaloceros.idle2";
    public static final String IDLE3 = "animation.megaloceros.idle3";
    public static final String RUN = "animation.megaloceros.run";
    public static final String SWIM = "animation.megaloceros.swim";
    public static final String WALK = "animation.megaloceros.walk";
    private Megaloceros lastMate;

    public Megaloceros(EntityType<Megaloceros> entityType, Level level) {
        super(entityType, level);
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        goalSelector.addGoal(Util.IMMOBILE + 3, new FleeBattleGoal(this, 1));
        goalSelector.addGoal(Util.ATTACK, new DelayedAttackGoal(this, 1, false));
        targetSelector.addGoal(4, new LastMateHurtByTargetGoal(this));
    }

    @Override
    public void procreate(Prehistoric other) {
        super.procreate(other);
        if (other instanceof Megaloceros megaloceros) {
            lastMate = megaloceros;
        }
    }

    @Override
    public PrehistoricEntityInfo info() {
        return PrehistoricEntityInfo.MEGALOCEROS;
    }

    @Override
    public Item getOrderItem() {
        return Items.STICK;
    }

    @Override
    public double getPassengersRidingOffset() {
        return super.getPassengersRidingOffset() - 0.35;
    }

    @Override
    public float getGenderedScale() {
        return getGender() == Gender.MALE ? 1.2f : super.getGenderedScale();
    }

    @Override
    public PrehistoricEntityInfoAI.Response aiResponseType() {
        return getGender() == Gender.MALE && !isBaby() ? PrehistoricEntityInfoAI.Response.TERRITORIAL : PrehistoricEntityInfoAI.Response.SCARED;
    }

    @Override
    public @NotNull Animation nextAttackAnimation() {
        return getAllAnimations().get(ATTACK);
    }

    @Override
    public @NotNull Animation nextEatingAnimation() {
        return getAllAnimations().get(EAT);
    }

    @Override
    public @NotNull Animation nextIdleAnimation() {
        float rand = random.nextFloat();
        if (rand <= 0.1) {
            return getAllAnimations().get(IDLE3);
        } else if (rand <= 0.2) {
            return getAllAnimations().get(IDLE2);
        }
        return getAllAnimations().get(IDLE);
    }
    
    @Override
    public @NotNull Animation nextSittingAnimation() {
        return getAllAnimations().get(IDLE);
    }

    @Override
    public @NotNull Animation nextSleepingAnimation() {
        return getAllAnimations().get(IDLE);
    }

    @Override
    public @NotNull Animation nextWalkingAnimation() {
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
        return ModSounds.MEGALOCEROS_AMBIENT.get();
    }

    @Nullable
    @Override
    protected SoundEvent getHurtSound(DamageSource damageSource) {
        return ModSounds.MEGALOCEROS_HURT.get();
    }

    @Nullable
    @Override
    protected SoundEvent getDeathSound() {
        return ModSounds.MEGALOCEROS_DEATH.get();
    }

    static class LastMateHurtByTargetGoal extends TargetGoal {
        private final Megaloceros megaloceros;
        private int timestamp;

        public LastMateHurtByTargetGoal(Megaloceros megaloceros) {
            super(megaloceros, false);
            this.megaloceros = megaloceros;
        }

        @Override
        public boolean canUse() {
            if (megaloceros.aiResponseType() == PrehistoricEntityInfoAI.Response.SCARED || megaloceros.getCurrentOrder() != OrderType.WANDER) {
                return false;
            }
            if (megaloceros.lastMate == null) {
                return false;
            }
            targetMob = megaloceros.lastMate.getLastHurtByMob();
            int i = megaloceros.lastMate.getLastHurtByMobTimestamp();
            return i != timestamp && canAttack(targetMob, TargetingConditions.DEFAULT);
        }

        @Override
        public void start() {
            super.start();
            megaloceros.setTarget(targetMob);
            timestamp = megaloceros.lastMate.getLastHurtByMobTimestamp();
        }
    }
}