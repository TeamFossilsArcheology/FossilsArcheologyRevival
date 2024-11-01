package com.github.teamfossilsarcheology.fossil.entity.prehistoric.base;

import com.github.teamfossilsarcheology.fossil.entity.ToyBase;
import com.github.teamfossilsarcheology.fossil.entity.animation.AnimationCategory;
import com.github.teamfossilsarcheology.fossil.entity.animation.AnimationInfo;
import com.github.teamfossilsarcheology.fossil.entity.animation.AnimationLogic;
import com.github.teamfossilsarcheology.fossil.entity.util.Util;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.Animation;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;

import java.util.Optional;

public abstract class PrehistoricLeaping extends Prehistoric {
    private static final EntityDataAccessor<Boolean> LEAPING = SynchedEntityData.defineId(PrehistoricLeaping.class, EntityDataSerializers.BOOLEAN);
    private final boolean canAttach;
    private long leapStartTick;

    public PrehistoricLeaping(EntityType<? extends PrehistoricLeaping> entityType, Level level, boolean canAttach) {
        super(entityType, level);
        this.canAttach = canAttach;
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        entityData.define(LEAPING, false);
    }

    @Override
    public void aiStep() {
        super.aiStep();
        if (!level.isClientSide) {
            if (isLeaping() && isOnGround()) {
                int leapDelay = (int) getServerAnimationInfos().get(getLeapingAnimationName()).actionDelay;
                boolean hasTarget = getTarget() != null && getTarget().isAlive();
                if (hasTarget && level.getGameTime() == leapStartTick + leapDelay) {
                    doLeapMovement();
                } else if (!hasTarget || level.getGameTime() > leapStartTick + leapDelay + 5) {
                    setLeaping(false);
                }
            }
            //TODO: Might need to reset target here if vehicle and target are not the same
            if (tickCount % 20 == 0 && getTarget() != null && getVehicle() == getTarget()) {
                getTarget().hurt(DamageSource.mobAttack(this), (float) getAttributeValue(Attributes.ATTACK_DAMAGE));
            }
        }
    }

    @Override
    public boolean hurt(DamageSource source, float amount) {
        boolean hurt = super.hurt(source, amount);
        if (hurt && isPassenger() && getLastHurtByMob() != null && getLastHurtByMob() == getVehicle()) {
            if (random.nextInt(2) == 0) {
                stopRiding();
            }
        }
        return hurt;
    }

    @Override
    public void push(Entity entity) {
        super.push(entity);
        if (canAttach) {
            if (isLeaping() && !isOnGround() && getTarget() == entity && getVehicle() != entity) {
                if (entity instanceof ToyBase) {
                    entity.hurt(DamageSource.mobAttack(this), 1);
                } else if (entity.getPassengers().isEmpty()) {
                    startRiding(entity);
                } else {
                    entity.hurt(DamageSource.mobAttack(this), (float) getAttributeValue(Attributes.ATTACK_DAMAGE));
                }
                setLeaping(false);
            }
        } else if (isLeaping() && !isOnGround() && getTarget() == entity) {
            entity.hurt(DamageSource.mobAttack(this), (float) getAttributeValue(Attributes.ATTACK_DAMAGE));
        }
    }

    public abstract void doLeapMovement();

    public abstract boolean useLeapAttack();

    public boolean isLeaping() {
        return entityData.get(LEAPING);
    }

    public void setLeaping(boolean leaping) {
        entityData.set(LEAPING, leaping);
    }

    public void startLeaping() {
        setLeaping(true);
        leapStartTick = level.getGameTime();
    }

    public @NotNull AnimationInfo getLeapingAnimation() {
        return getAllAnimations().get(getLeapingAnimationName());
    }

    public abstract String getLeapingAnimationName();

    private double lastSpeed = 0;

    private PlayState leapingPredicate(AnimationEvent<PrehistoricLeaping> event) {
        AnimationController<PrehistoricLeaping> controller = event.getController();
        double animSpeed = 1;
        if (isLeaping()) {
            getAnimationLogic().addActiveAnimation(controller.getName(), getLeapingAnimation().animation, AnimationCategory.ATTACK);
        } else {
            if (event.isMoving() || isClimbing()) {
                Animation movementAnim;
                if (isSprinting()) {
                    movementAnim = nextSprintingAnimation().animation;
                } else {
                    movementAnim = nextWalkingAnimation().animation;
                }
                getAnimationLogic().addActiveAnimation(controller.getName(), movementAnim, AnimationCategory.WALK);
                //TODO: Refactor to use the same code for AnimationLogic, PrehistoricFlying and this
                //All animations were done at a scale of 1 -> Slow down animation if scale is bigger than 1
                animSpeed = 1 / event.getAnimatable().getScale();
                double animationBaseSpeed = AnimationLogic.getAnimationTargetSpeed(event.getAnimatable(), movementAnim.animationName);
                if (animationBaseSpeed > 0) {
                    //All animations were done for a specific movespeed -> Slow down animation if mobSpeed is slower than that speed
                    double mobSpeed = getDeltaMovement().horizontalDistance() * 20;
                    //Limit mobSpeed to the mobs maximum natural movement speed (23.55 * maxSpeed^2)
                    mobSpeed = Math.min(Util.attributeToSpeed(event.getAnimatable().attributes().maxSpeed()), mobSpeed);
                    animSpeed *= mobSpeed / animationBaseSpeed;
                }
                if (lastSpeed > animSpeed) {
                    //I would love to always change speed but that causes stuttering, so we just find one max speed that's good enough
                    animSpeed = lastSpeed;
                }
            } else {
                getAnimationLogic().addActiveAnimation(controller.getName(), nextIdleAnimation().animation, AnimationCategory.IDLE);
            }
        }
        lastSpeed = animSpeed;
        event.getController().setAnimationSpeed(animSpeed);
        Optional<AnimationLogic.ActiveAnimationInfo> activeAnimation = getAnimationLogic().getActiveAnimation(controller.getName());
        if (activeAnimation.isPresent()) {
            controller.setAnimation(new AnimationBuilder().addAnimation(activeAnimation.get().animationName()));
        }
        return PlayState.CONTINUE;
    }

    @Override
    public void registerControllers(AnimationData data) {
        data.addAnimationController(new AnimationController<>(
                this, AnimationLogic.IDLE_CTRL, 5, this::leapingPredicate));
        data.addAnimationController(new AnimationController<>(
                this, AnimationLogic.ATTACK_CTRL, 5, getAnimationLogic()::attackPredicate));
    }
}
