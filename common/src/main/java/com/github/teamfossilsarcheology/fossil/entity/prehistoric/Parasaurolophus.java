package com.github.teamfossilsarcheology.fossil.entity.prehistoric;

import com.github.teamfossilsarcheology.fossil.entity.ai.FleeBattleGoal;
import com.github.teamfossilsarcheology.fossil.entity.animation.AnimationCategory;
import com.github.teamfossilsarcheology.fossil.entity.animation.AnimationLogic;
import com.github.teamfossilsarcheology.fossil.entity.animation.PausableAnimationController;
import com.github.teamfossilsarcheology.fossil.entity.prehistoric.base.Prehistoric;
import com.github.teamfossilsarcheology.fossil.entity.prehistoric.base.PrehistoricEntityInfo;
import com.github.teamfossilsarcheology.fossil.entity.util.Util;
import com.github.teamfossilsarcheology.fossil.sounds.ModSounds;
import com.github.teamfossilsarcheology.fossil.util.Gender;
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
import software.bernie.geckolib.core.animation.*;
import software.bernie.geckolib.core.object.PlayState;

import java.util.EnumSet;
import java.util.Optional;

import static software.bernie.geckolib.core.animation.Animation.LoopType.LOOP;
import static software.bernie.geckolib.core.animation.Animation.LoopType.PLAY_ONCE;

public class Parasaurolophus extends Prehistoric {
    private static final EntityDataAccessor<Boolean> STANDING = SynchedEntityData.defineId(Parasaurolophus.class, EntityDataSerializers.BOOLEAN);
    private final ParaAnimationLogic animationLogic = new ParaAnimationLogic(this);
    private static final String STAND = "animation.parasaurolophus.stand";
    private static final String STAND_UP = "animation.parasaurolophus.stand_up";

    public Parasaurolophus(EntityType<Parasaurolophus> entityType, Level level) {
        super(entityType, level);
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        goalSelector.addGoal(Util.IMMOBILE + 3, new FleeBattleGoal(this, 1));
        goalSelector.addGoal(Util.WANDER - 1, new ParaStandGoal());
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        entityData.define(STANDING, false);
    }

    @Override
    public void onSyncedDataUpdated(EntityDataAccessor<?> key) {
        if (!level().isClientSide) {
            if (SLEEPING.equals(key) || SITTING.equals(key)) {
                //Fallback
                setStanding(false);
            }
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
    public void registerControllers(AnimatableManager.ControllerRegistrar controllerRegistrar) {
        var controller = new PausableAnimationController<>(this, AnimationLogic.IDLE_CTRL, 5, animationLogic::paraPredicate);
        registerEatingListeners(controller);
        controllerRegistrar.add(controller);
        controllerRegistrar.add(new PausableAnimationController<>(
                this, AnimationLogic.ATTACK_CTRL, 0, getAnimationLogic()::attackPredicate));
    }

    @Override
    public AnimationLogic<Prehistoric> getAnimationLogic() {
        return animationLogic;
    }

    class ParaStandGoal extends Goal {
        private int cooldown;

        public ParaStandGoal() {
            setFlags(EnumSet.of(Flag.MOVE));
        }

        @Override
        public boolean canUse() {
            if (cooldown > tickCount || isInWater() || isImmobile()) {
                return false;
            }
            return random.nextInt(100) == 0;
        }

        @Override
        public boolean canContinueToUse() {
            return !isVehicle() && !isInWater() && !isImmobile() && random.nextInt(reducedTickDelay(2000)) != 0;
        }

        @Override
        public void start() {
            cooldown = 0;
            setStanding(true);
        }

        @Override
        public void stop() {
            cooldown = tickCount + (random.nextInt(120) + 10) * 20;
            setStanding(false);
        }
    }

    static class ParaAnimationLogic extends AnimationLogic<Prehistoric> {

        public ParaAnimationLogic(Parasaurolophus entity) {
            super(entity);
        }

        public PlayState paraPredicate(AnimationState<Parasaurolophus> state) {
            if (isBlocked()) return PlayState.STOP;
            AnimationController<Parasaurolophus> controller = state.getController();
            if (tryNextAnimation(state, controller)) {
                return PlayState.CONTINUE;
            }
            Optional<ActiveAnimationInfo> activeAnimation = getActiveAnimation(controller.getName());
            if (activeAnimation.isPresent() && tryForcedAnimation(state, activeAnimation.get())) {
                return PlayState.CONTINUE;
            }
            if (state.getAnimatable().isStanding()) {
                controller.setAnimation(RawAnimation.begin().thenPlay(STAND_UP).thenLoop(STAND));
                return PlayState.CONTINUE;
            }
            double animationSpeed = 1;
            if (entity.isSleeping()) {
                addActiveAnimation(controller.getName(), AnimationCategory.SLEEP);
            } else if (state.getAnimatable().sitSystem.isSitting()) {
                addActiveAnimation(controller.getName(), AnimationCategory.SIT);
            } else if (entity.isInWater()) {
                addActiveAnimation(controller.getName(), AnimationCategory.SWIM, true);
            } else if (state.isMoving()) {
                animationSpeed = addMovementAnimation(state, true);
            } else {
                addActiveAnimation(controller.getName(), AnimationCategory.IDLE);
            }
            setAnimationSpeed(controller, animationSpeed, state.getAnimationTick());
            Optional<ActiveAnimationInfo> newAnimation = getActiveAnimation(controller.getName());
            if (newAnimation.isPresent()) {
                controller.transitionLength(newAnimation.get().transitionLength());
                controller.setAnimation(RawAnimation.begin().then(newAnimation.get().animationName(), newAnimation.get().loop() ? LOOP : PLAY_ONCE));
            }
            return PlayState.CONTINUE;
        }
    }
}