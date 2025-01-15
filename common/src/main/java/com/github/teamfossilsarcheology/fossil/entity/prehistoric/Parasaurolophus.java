package com.github.teamfossilsarcheology.fossil.entity.prehistoric;

import com.github.teamfossilsarcheology.fossil.FossilMod;
import com.github.teamfossilsarcheology.fossil.entity.ai.FleeBattleGoal;
import com.github.teamfossilsarcheology.fossil.entity.animation.AnimationCategory;
import com.github.teamfossilsarcheology.fossil.entity.animation.AnimationLogic;
import com.github.teamfossilsarcheology.fossil.entity.animation.PausableAnimationController;
import com.github.teamfossilsarcheology.fossil.entity.prehistoric.base.Prehistoric;
import com.github.teamfossilsarcheology.fossil.entity.prehistoric.base.PrehistoricEntityInfo;
import com.github.teamfossilsarcheology.fossil.entity.util.Util;
import com.github.teamfossilsarcheology.fossil.sounds.ModSounds;
import com.github.teamfossilsarcheology.fossil.util.Gender;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.Animation;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;

import java.util.Optional;

import static software.bernie.geckolib3.core.builder.ILoopType.EDefaultLoopTypes.LOOP;
import static software.bernie.geckolib3.core.builder.ILoopType.EDefaultLoopTypes.PLAY_ONCE;

public class Parasaurolophus extends Prehistoric {
    private static final EntityDataAccessor<Boolean> STANDING = SynchedEntityData.defineId(Parasaurolophus.class, EntityDataSerializers.BOOLEAN);
    private final ParaAnimationLogic animationLogic = new ParaAnimationLogic(this);
    private static final String STAND = "animation.parasaurolophus.stand";
    private static final String STAND_UP = "animation.parasaurolophus.stand_up";

    private int ticksStanding;

    public Parasaurolophus(EntityType<Parasaurolophus> entityType, Level level) {
        super(entityType, level);
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        goalSelector.addGoal(Util.IMMOBILE + 3, new FleeBattleGoal(this, 1));
    }

    @Override
    protected void updateControlFlags() {
        boolean bl = !isSleeping();
        goalSelector.setControlFlag(Goal.Flag.MOVE, bl && !sitSystem.isSitting() && !isStanding());
        goalSelector.setControlFlag(Goal.Flag.JUMP, bl && !sitSystem.isSitting() && !isStanding());
        goalSelector.setControlFlag(Goal.Flag.LOOK, bl);
    }

    @Override
    public void refreshTexturePath() {
        if (!level.isClientSide) {
            return;
        }
        if ("Jackabird".equals(ChatFormatting.stripFormatting(getName().getString()))) {
            if (isSleeping()) {
                textureLocation = FossilMod.location("textures/entity/parasaurolophus/parasaurolophus_jackabird_sleeping.png");
            } else {
                textureLocation = FossilMod.location("textures/entity/parasaurolophus/parasaurolophus_jackabird.png");
            }
            return;
        }
        super.refreshTexturePath();
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        entityData.define(STANDING, false);
    }

    @Override
    public void onSyncedDataUpdated(EntityDataAccessor<?> key) {
        if (!level.isClientSide) {
            if (SLEEPING.equals(key) || SITTING.equals(key)) {
                setStanding(false);
            }
        } else if (DATA_CUSTOM_NAME.equals(key)) {
            refreshTexturePath();
        }
        super.onSyncedDataUpdated(key);
    }

    @Override
    public void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        compound.putBoolean("Standing", isStanding());
    }

    @Override
    public void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        setStanding(compound.getBoolean("Standing"));
    }

    @Override
    public PrehistoricEntityInfo info() {
        return PrehistoricEntityInfo.PARASAUROLOPHUS;
    }

    @Override
    public Item getOrderItem() {
        return Items.STICK;
    }

    @Override
    public double getPassengersRidingOffset() {
        return super.getPassengersRidingOffset() + 0.4;
    }

    @Override
    public void aiStep() {
        super.aiStep();
        if (isStanding()) {
            ticksStanding++;
        } else {
            ticksStanding = 0;
        }
        if (!level.isClientSide) {
            if (!isStanding() && !isImmobile() && random.nextInt(100) == 0) {
                setStanding(true);
            }
            if (isStanding() && ticksStanding > 800 && random.nextInt(800) == 0) {
                setStanding(false);
            }
        }
    }

    public boolean isStanding() {
        return entityData.get(STANDING);
    }

    public void setStanding(boolean standing) {
        entityData.set(STANDING, standing);
    }

    @Override
    public float getGenderedScale() {
        return getGender() == Gender.MALE ? 1.15f : super.getGenderedScale();
    }

    @Nullable
    @Override
    protected SoundEvent getAmbientSound() {
        return ModSounds.PARASAUROLOPHUS_AMBIENT.get();
    }

    @Nullable
    @Override
    protected SoundEvent getHurtSound(DamageSource damageSource) {
        return ModSounds.PARASAUROLOPHUS_HURT.get();
    }

    @Nullable
    @Override
    protected SoundEvent getDeathSound() {
        return ModSounds.PARASAUROLOPHUS_DEATH.get();
    }

    @Override
    public void registerControllers(AnimationData data) {
        var controller = new PausableAnimationController<>(this, AnimationLogic.IDLE_CTRL, 5, animationLogic::paraPredicate);
        registerEatingListeners(controller);
        data.addAnimationController(controller);
        data.addAnimationController(new PausableAnimationController<>(
                this, AnimationLogic.ATTACK_CTRL, 5, getAnimationLogic()::attackPredicate));
    }

    static class ParaAnimationLogic extends AnimationLogic<Prehistoric> {

        public ParaAnimationLogic(Parasaurolophus entity) {
            super(entity);
        }

        public PlayState paraPredicate(AnimationEvent<Parasaurolophus> event) {
            if (isBlocked()) return PlayState.STOP;
            AnimationController<Parasaurolophus> controller = event.getController();
            if (tryNextAnimation(controller)) {
                return PlayState.CONTINUE;
            }
            Optional<ActiveAnimationInfo> activeAnimation = getActiveAnimation(controller.getName());
            if (activeAnimation.isPresent() && tryForcedAnimation(event, activeAnimation.get())) {
                return PlayState.CONTINUE;
            }
            if (event.getAnimatable().isStanding()) {
                controller.setAnimation(new AnimationBuilder().playOnce(STAND_UP).loop(STAND));
                return PlayState.CONTINUE;
            }
            double animationSpeed = 1;
            if (entity.isSleeping()) {
                addActiveAnimation(controller.getName(), AnimationCategory.SLEEP);
            } else if (event.getAnimatable().sitSystem.isSitting()) {
                addActiveAnimation(controller.getName(), AnimationCategory.SIT);
            } else if (entity.isInWater()) {
                addActiveAnimation(controller.getName(), AnimationCategory.SWIM, true);
            } else if (event.isMoving()) {
                Animation walkAnim = entity.nextWalkingAnimation().animation;
                Animation sprintAnim = entity.nextSprintingAnimation().animation;
                //All animations were done at a scale of 1 -> Slow down animation if scale is bigger than 1
                double scaleMult = 1 / event.getAnimatable().getScale();
                //the deltaMovement of the animation should match the mobs deltaMovement
                double mobSpeed = entity.getDeltaMovement().horizontalDistance() * 20;
                //Limit mobSpeed to the mobs maximum natural movement speed (23.55 * maxSpeed^2)
                mobSpeed = Math.min(Util.attributeToSpeed(attributeSpeed), mobSpeed);
                //All animations were done for a specific movespeed -> Slow down animation if mobSpeed is slower than that speed
                double animationTargetSpeed = getAnimationTargetSpeed(event.getAnimatable(), walkAnim.animationName);
                if (animationTargetSpeed > 0) {
                    animationSpeed = scaleMult * mobSpeed / animationTargetSpeed;
                }
                if (animationSpeed > 2.75 || entity.isSprinting()) {
                    //Choose sprint
                    animationTargetSpeed = getAnimationTargetSpeed(event.getAnimatable(), sprintAnim.animationName);
                    if (animationTargetSpeed > 0) {
                        animationSpeed = scaleMult * mobSpeed / animationTargetSpeed;
                    }
                    addActiveAnimation(controller.getName(), sprintAnim, AnimationCategory.SPRINT, false);
                } else {
                    addActiveAnimation(controller.getName(), walkAnim, AnimationCategory.WALK, false);
                }
            } else {
                addActiveAnimation(controller.getName(), AnimationCategory.IDLE);
            }
            setAnimationSpeed(controller, animationSpeed, event.getAnimationTick());
            Optional<ActiveAnimationInfo> newAnimation = getActiveAnimation(controller.getName());
            if (newAnimation.isPresent()) {
                controller.transitionLengthTicks = newAnimation.get().transitionLength();
                controller.setAnimation(new AnimationBuilder().addAnimation(newAnimation.get().animationName(), newAnimation.get().loop() ? LOOP : PLAY_ONCE));
            }
            return PlayState.CONTINUE;
        }
    }
}