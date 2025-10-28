package com.github.teamfossilsarcheology.fossil.entity.prehistoric.base;

import com.github.teamfossilsarcheology.fossil.entity.LaserPointEntity;
import com.github.teamfossilsarcheology.fossil.entity.ai.*;
import com.github.teamfossilsarcheology.fossil.entity.ai.control.CustomSwimMoveControl;
import com.github.teamfossilsarcheology.fossil.entity.ai.control.PrehistoricLookControl;
import com.github.teamfossilsarcheology.fossil.entity.ai.control.SmoothTurningMoveControl;
import com.github.teamfossilsarcheology.fossil.entity.ai.navigation.AmphibiousPathNavigation;
import com.github.teamfossilsarcheology.fossil.entity.animation.AnimationInfo;
import com.github.teamfossilsarcheology.fossil.entity.animation.AnimationLogic;
import com.github.teamfossilsarcheology.fossil.entity.animation.PausableAnimationController;
import com.github.teamfossilsarcheology.fossil.entity.damagesource.ModDamageTypes;
import com.github.teamfossilsarcheology.fossil.entity.util.Util;
import com.github.teamfossilsarcheology.fossil.food.Diet;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.FluidTags;
import net.minecraft.tags.TagKey;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.SmoothSwimmingLookControl;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.animal.FlyingAnimal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import software.bernie.geckolib.core.animation.AnimatableManager;

public abstract class PrehistoricSwimming extends Prehistoric implements SwimmingAnimal {
    public static final int MAX_TIME_IN_WATER = 1000;
    public static final int MAX_TIME_ON_LAND = 1000;
    private static final EntityDataAccessor<Boolean> GRABBING = SynchedEntityData.defineId(PrehistoricSwimming.class, EntityDataSerializers.BOOLEAN);
    private int timeInWater = 0;
    private int timeOnLand = 0;
    /**
     * Cache check for current navigator
     */
    protected boolean isLandNavigator;
    private boolean beached;

    protected PrehistoricSwimming(EntityType<? extends Prehistoric> entityType, Level level, ResourceLocation animationLocation) {
        super(entityType, level, animationLocation);
        setPathfindingMalus(BlockPathTypes.WATER, 0);
        switchNavigator(false);
    }

    protected PrehistoricSwimming(EntityType<? extends Prehistoric> entityType, Level level) {
        super(entityType, level);
        setPathfindingMalus(BlockPathTypes.WATER, 0);
        switchNavigator(false);
    }

    @Override
    protected void registerGoals() {
        matingGoal = new DinoMatingGoal(this, 1);
        goalSelector.addGoal(Util.IMMOBILE + 1, new DinoPanicGoal(this, attributes().sprintMod()));
        if (aiAttackType() == PrehistoricEntityInfoAI.Attacking.GRAB) {
            goalSelector.addGoal(Util.ATTACK, new GrabMeleeAttackGoal(this, attributes().sprintMod(), false));
        } else if (aiAttackType() != PrehistoricEntityInfoAI.Attacking.NONE) {
            goalSelector.addGoal(Util.ATTACK, new DelayedAttackGoal<>(this, attributes().sprintMod(), false));
        }
        goalSelector.addGoal(Util.SLEEP + 2, matingGoal);
        if (data().diet() != Diet.PASSIVE) {
            goalSelector.addGoal(Util.NEEDS, new EatFromFeederGoal(this));
            goalSelector.addGoal(Util.NEEDS + 1, new EatItemEntityGoal(this));
            goalSelector.addGoal(Util.NEEDS + 2, new EatBlockGoal(this));
        } else {
            goalSelector.addGoal(Util.NEEDS, new PassiveFoodGoal(this));
        }
        goalSelector.addGoal(Util.NEEDS + 3, new WaterPlayGoal<>(this, 1));
        goalSelector.addGoal(Util.WANDER, new DinoFollowOwnerGoal(this, attributes().sprintMod(), 7, 2, false));
        goalSelector.addGoal(Util.WANDER + 1, new EnterWaterGoal<>(this, 1));
        goalSelector.addGoal(Util.WANDER + 2, new DinoRandomSwimGoal<>(this, 1));
        goalSelector.addGoal(Util.WANDER + 3, new AmphibiousWanderGoal(this, 1));
        goalSelector.addGoal(Util.LOOK, new LookAtPlayerGoal(this, Player.class, 8.0f));
        goalSelector.addGoal(Util.LOOK + 1, new RandomLookAroundGoal(this));
        targetSelector.addGoal(5, new HuntingTargetGoal(this));

        targetSelector.addGoal(4, new NearestAttackableTargetGoal<>(this, LaserPointEntity.class, true));
    }

    public static boolean isOverWater(LivingEntity entity) {
        if (entity.level().getFluidState(entity.blockPosition().below()).is(FluidTags.WATER)) {
            return true;
        }
        if (entity.level().getFluidState(entity.blockPosition().below(2)).is(FluidTags.WATER)) {
            return true;
        }
        return entity.level().getFluidState(entity.blockPosition().below(3)).is(FluidTags.WATER);
    }

    @Override
    protected void updateControlFlags() {
        boolean bl = !isBeached() && !isSleeping();
        goalSelector.setControlFlag(Goal.Flag.MOVE, bl && !sitSystem.isSitting());
        goalSelector.setControlFlag(Goal.Flag.JUMP, bl && !sitSystem.isSitting());
        goalSelector.setControlFlag(Goal.Flag.LOOK, bl);
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        entityData.define(GRABBING, false);
    }

    @Override
    public void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        compound.putInt("TimeInWater", timeInWater);
        compound.putInt("TimeOnLand", timeOnLand);
    }

    @Override
    public void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        timeInWater = compound.getInt("TimeInWater");
        timeOnLand = compound.getInt("TimeOnLand");
    }

    @Override
    protected @NotNull PathNavigation createNavigation(Level level) {
        return new AmphibiousPathNavigation<>(this, level);
    }

    protected void switchNavigator(boolean onLand) {
        if (onLand) {
            moveControl = new SmoothTurningMoveControl(this);
            lookControl = new PrehistoricLookControl(this);
            isLandNavigator = true;
        } else {
            moveControl = new CustomSwimMoveControl<>(this);
            lookControl = new SmoothSwimmingLookControl(this, 20);
            isLandNavigator = false;
        }
    }

    @Override
    public @NotNull MobType getMobType() {
        return MobType.WATER;
    }

    @Override
    public boolean checkSpawnObstruction(LevelReader level) {
        return level.isUnobstructed(this);
    }

    @Override
    public boolean isPushedByFluid() {
        return false;
    }

    @Override
    public boolean isAmphibious() {
        return aiMovingType() == PrehistoricEntityInfoAI.Moving.SEMI_AQUATIC;
    }

    @Override
    public boolean canSwim() {
        return aiMovingType() == PrehistoricEntityInfoAI.Moving.AQUATIC || aiMovingType() == PrehistoricEntityInfoAI.Moving.SEMI_AQUATIC;
    }

    @Override
    public double swimSpeed() {
        return swimSpeed;
    }

    @Override
    public boolean canBreatheUnderwater() {
        return true;
    }

    @Override
    protected @NotNull MovementEmission getMovementEmission() {
        return MovementEmission.EVENTS;
    }

    @Override
    public void aiStep() {
        super.aiStep();
        boolean wasBeached = beached;
        boolean inWater = isInWater();
        beached = !isAmphibious() && !inWater && onGround();
        if (!level().isClientSide) {
            if (isAmphibious()) {
                if (isLandNavigator && inWater) {
                    switchNavigator(false);
                } else if (!isLandNavigator && !inWater && onGround()) {
                    switchNavigator(true);
                }
            } else if (isLandNavigator) {
                switchNavigator(false);
            }
            if (inWater) {
                timeInWater++;
                timeOnLand = 0;
                setNoGravity(true);
                if (isSleeping() && level().getBlockState(blockPosition().offset(0, (int) (getBbHeight() + 1), 0)).isAir()) {
                    setNoGravity(false);
                }
            } else if (onGround()) {
                timeInWater = 0;
                timeOnLand++;
            } else {
                setNoGravity(false);
            }
            if (isDoingGrabAttack() && !isVehicle()) {
                //Failsafe
                setDoingGrabAttack(false);
            }
        } else {
            if (wasBeached != beached) {
                refreshTexturePath();
            }
            if (beached) {
                setXRot(0);
            }
        }
    }

    @Override
    protected void jumpInLiquid(TagKey<Fluid> fluidTag) {
        setDeltaMovement(getDeltaMovement().add(0.0, 0.3, 0.0));
    }

    @Override
    public void calculateEntityAnimation(boolean isFlying) {
        super.calculateEntityAnimation(isInWater());
    }

    @Override
    protected float tickHeadTurn(float yRot, float animStep) {
        if (isBeached()) {
            return animStep;
        }
        return super.tickHeadTurn(yRot, animStep);
    }

    protected void handleAirSupply(int airSupply) {
        if (!canBreatheOnLand() && isAlive() && !isInWaterOrBubble() && !isNoAi()) {
            setAirSupply(airSupply - 1);
            if (getAirSupply() == -40) {
                setAirSupply(0);
                hurt(damageSources().source(ModDamageTypes.SUFFOCATE_KEY), 2);
            }
        } else {
            setAirSupply(500);
        }
    }

    @Override
    public void positionRider(Entity passenger, MoveFunction callback) {
        super.positionRider(passenger, callback);
        if (passenger != getRidingPlayer() && isDoingGrabAttack()) {
            getEntityHitboxData().getAnchorData().getAnchorPos("grab_pos").ifPresentOrElse(pos -> {
                callback.accept(passenger, pos.x, pos.y + passenger.getMyRidingOffset(), pos.z);
            }, () -> {
                // holy magic numbers. what is going on here?
                // I'm guessing the numbers were tweaked until it looked good?
                // can you add them as labeled constants?
                // also, stuff like '0.35f * 0.7f * -3' is kinda crazy lol
                // could just be -0.735 for instance
                float t = 5 * Mth.sin(Mth.PI + tickCount * 0.275f);
                float radius = 0.35f * 0.7f * getScale() * -3;
                float angle = Mth.DEG_TO_RAD * yBodyRot + 3.15f + t * 1.75f * 0.05f;
                double extraX = radius * Mth.sin(Mth.PI + angle);
                double extraY = 0.065 * getScale();
                double extraZ = radius * Mth.cos(angle);
                callback.accept(passenger, getX() + extraX, getY() + extraY, getZ() + extraZ);
            });
        }
    }

    public float grabTargetSize() {
        return getBbWidth() / 2;
    }

    public void startGrabAttack(Entity target) {
        target.startRiding(this);
        setDoingGrabAttack(true);
    }

    public void stopGrabAttack(Entity target) {
        target.stopRiding();
        setDoingGrabAttack(false);
    }

    @Override
    public void baseTick() {
        int i = getAirSupply();
        super.baseTick();
        handleAirSupply(i);
    }

    @Override
    public void disableCustomAI(byte type, boolean disableAI) {
        if (type == 0 || type == 2) {
            super.disableCustomAI(type, disableAI);
            setNoGravity(disableAI);
        } else {
            super.disableCustomAI(type, disableAI);
        }
    }

    @Override
    public void travel(Vec3 travelVector) {
        if (isImmobile() && !isVehicle() || !isAmphibious() && !isInWater()) {
            super.travel(Vec3.ZERO);
            return;
        }
        LivingEntity rider = getControllingPassenger();
        if (rider == null || !canBeControlledByRider() || (isControlledByLocalInstance() && !steering.trySteering(rider))) {
            if (isEffectiveAi() && isInWater()) {
                moveRelative(getSpeed(), travelVector);
                move(MoverType.SELF, getDeltaMovement());
                setDeltaMovement(getDeltaMovement().scale(0.9));
                if (!isNoGravity() && level().getFluidState(blockPosition().below()).is(FluidTags.WATER)) {
                    setDeltaMovement(getDeltaMovement().add(0.0, -0.005, 0.0));
                }
                calculateEntityAnimation(this instanceof FlyingAnimal);
            } else {
                super.travel(travelVector);
            }
            return;
        }
        if (!isInWater()) {
            super.travel(travelVector);
            return;
        }
        setYRot(rider.getYRot());
        yRotO = getYRot();
        setXRot(rider.getXRot() * 0.5f);
        setRot(getYRot(), getXRot());
        yBodyRot = getYRot();
        yHeadRot = getYRot();
        float newStrafeMovement = rider.xxa * 0.5f;
        float newForwardMovement = rider.zza;
        if (isControlledByLocalInstance()) {
            setSpeed((float) getAttributeValue(Attributes.MOVEMENT_SPEED));
            steering.waterTravel(new Vec3(newStrafeMovement, travelVector.y, newForwardMovement), (Player) rider);
        } else {
            setDeltaMovement(Vec3.ZERO);
            calculateEntityAnimation(this instanceof FlyingAnimal);
        }
    }

    @Override
    public void onInsideBubbleColumn(boolean downwards) {
        if (getBbWidth() < 2) {
            super.onInsideBubbleColumn(downwards);
        }
    }

    @Override
    public boolean onClimbable() {
        return false;
    }

    public void destroyBoat(Entity targetSailor) {
        if (targetSailor.getVehicle() instanceof Boat boat && !level().isClientSide) {
            boat.kill();
            if (level().getGameRules().getBoolean(GameRules.RULE_DOENTITYDROPS)) {
                int i;
                for (i = 0; i < 3; i++) {
                    spawnAtLocation(boat.getVariant().getPlanks());
                }
                for (i = 0; i < 2; i++) {
                    spawnAtLocation(Items.STICK);
                }
            }

        }
    }

    @Override
    public boolean canBeControlledByRider() {
        return !isAmphibious() ? isInWater() && super.canBeControlledByRider() : super.canBeControlledByRider();
    }

    public boolean isBeached() {
        return beached;
    }

    @Override
    public int timeInWater() {
        return timeInWater;
    }

    @Override
    public int timeOnLand() {
        return timeOnLand;
    }

    public boolean isDoingGrabAttack() {
        return entityData.get(GRABBING);
    }

    public void setDoingGrabAttack(boolean grabbing) {
        entityData.set(GRABBING, grabbing);
    }

    public @NotNull AnimationInfo nextGrabbingAnimation() {
        //TODO: Grab category
        return nextIdleAnimation();
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllerRegistrar) {
        var controller = new PausableAnimationController<>(this, AnimationLogic.IDLE_CTRL, 5, getAnimationLogic()::waterPredicate);
        registerEatingListeners(controller);
        registerControllerWithTriggers(controllerRegistrar, controller);
        registerControllerWithTriggers(controllerRegistrar, new PausableAnimationController<>(
                this, AnimationLogic.ATTACK_CTRL, 5, getAnimationLogic()::grabAttackPredicate));
    }

    @Override
    public int getMaxHeadXRot() {
        //I can't really tell if this does anything
        return 1;
    }

    @Override
    public int getMaxHeadYRot() {
        return 1;
    }
}
