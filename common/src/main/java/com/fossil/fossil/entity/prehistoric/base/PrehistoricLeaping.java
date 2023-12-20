package com.fossil.fossil.entity.prehistoric.base;

import com.fossil.fossil.entity.ToyBase;
import com.fossil.fossil.entity.animation.AnimationLogic;
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
                int leapDelay = getServerAnimationInfos().get(getLeapingAnimationName()).actionDelay();
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

    public @NotNull Animation getLeapingAnimation() {
        return getAllAnimations().get(getLeapingAnimationName());
    }

    public abstract String getLeapingAnimationName();


    private PlayState leapingPredicate(AnimationEvent<PrehistoricLeaping> event) {
        AnimationController<PrehistoricLeaping> controller = event.getController();
        if (isLeaping()) {
            addActiveAnimation(controller.getName(), getLeapingAnimation());
        } else {
            if (event.isMoving()) {
                addActiveAnimation(controller.getName(), nextMovingAnimation());
            } else {
                addActiveAnimation(controller.getName(), nextIdleAnimation());
            }
        }
        AnimationLogic.ActiveAnimationInfo activeAnimation = getActiveAnimation(controller.getName());
        if (activeAnimation != null) {
            controller.setAnimation(new AnimationBuilder().addAnimation(activeAnimation.animationName()));
        }
        return PlayState.CONTINUE;
    }

    @Override
    public void registerControllers(AnimationData data) {
        data.addAnimationController(new AnimationController<>(this, "Movement/Idle/Eat", 5, this::leapingPredicate));
        data.addAnimationController(new AnimationController<>(this, "Attack", 5, getAnimationLogic()::attackPredicate));
    }
}
