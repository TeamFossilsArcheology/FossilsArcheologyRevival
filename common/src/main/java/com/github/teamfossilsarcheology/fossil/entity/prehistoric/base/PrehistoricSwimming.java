package com.github.teamfossilsarcheology.fossil.entity.prehistoric.base;

import com.github.teamfossilsarcheology.fossil.entity.ai.*;
import com.github.teamfossilsarcheology.fossil.entity.ai.control.CustomSwimMoveControl;
import com.github.teamfossilsarcheology.fossil.entity.ai.control.SmoothTurningMoveControl;
import com.github.teamfossilsarcheology.fossil.entity.ai.navigation.AmphibiousPathNavigation;
import com.github.teamfossilsarcheology.fossil.entity.animation.AnimationInfo;
import com.github.teamfossilsarcheology.fossil.entity.animation.AnimationLogic;
import com.github.teamfossilsarcheology.fossil.entity.util.Util;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.LookControl;
import net.minecraft.world.entity.ai.control.SmoothSwimmingLookControl;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.manager.AnimationData;

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
    private Vec3 grabPos;

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
        goalSelector.addGoal(Util.IMMOBILE + 1, new DinoPanicGoal(this, 1.5));
        if (aiAttackType() == PrehistoricEntityInfoAI.Attacking.GRAB) {
            goalSelector.addGoal(Util.ATTACK, new GrabMeleeAttackGoal(this, 1, false));
        } else if (aiAttackType() != PrehistoricEntityInfoAI.Attacking.NONE){
            goalSelector.addGoal(Util.ATTACK, new DelayedAttackGoal(this, 1, false));
        }
        goalSelector.addGoal(Util.SLEEP + 2, matingGoal);
        goalSelector.addGoal(Util.NEEDS, new EatFromFeederGoal(this));
        goalSelector.addGoal(Util.NEEDS + 1, new EatItemEntityGoal(this));
        goalSelector.addGoal(Util.NEEDS + 2, new EatBlockGoal(this));
        goalSelector.addGoal(Util.NEEDS + 3, new WaterPlayGoal(this, 1));
        goalSelector.addGoal(Util.WANDER, new DinoFollowOwnerGoal(this, 1, 10, 2, false));
        goalSelector.addGoal(Util.WANDER + 1, new EnterWaterGoal(this, 1));
        goalSelector.addGoal(Util.WANDER + 2, new DinoRandomSwimGoal(this, 1));
        goalSelector.addGoal(Util.WANDER + 3, new AmphibiousWanderGoal(this, 1));
        goalSelector.addGoal(Util.LOOK, new LookAtPlayerGoal(this, Player.class, 8.0f));
        goalSelector.addGoal(Util.LOOK + 1, new RandomLookAroundGoal(this));
        targetSelector.addGoal(5, new HuntingTargetGoal(this));
    }

    public static boolean isOverWater(LivingEntity entity) {
        if (entity.level.getFluidState(entity.blockPosition().below()).is(FluidTags.WATER)) {
            return true;
        }
        if (entity.level.getFluidState(entity.blockPosition().below(2)).is(FluidTags.WATER)) {
            return true;
        }
        return entity.level.getFluidState(entity.blockPosition().below(3)).is(FluidTags.WATER);
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
            lookControl = new LookControl(this);
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
        if (!level.isClientSide) {
            if (isInWater() && isLandNavigator) {
                switchNavigator(false);
            } else if (!isInWater() && isOnGround() && isAmphibious() && !isLandNavigator) {
                switchNavigator(true);
            }
            if (isInWater()) {
                timeInWater++;
                timeOnLand = 0;
            } else if (onGround) {
                timeInWater = 0;
                timeOnLand++;
            }
        } else {
            beached = !isAmphibious() && !isInWater() && isOnGround();
            if (beached) {
                setXRot(0);
            }
        }
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
                hurt(DamageSource.DROWN, 2);
            }
        } else {
            setAirSupply(500);
        }
    }

    @Override
    public void setAnchorPos(String boneName, Vec3 localPos) {
        super.setAnchorPos(boneName, localPos);
        if ("grab_pos".equals(boneName)) {
            grabPos = position().add(localPos);
        }
    }

    @Override
    public boolean canSetAnchorPos(String boneName) {
        return super.canSetAnchorPos(boneName) || "grab_pos".equals(boneName);
    }

    @Override
    public void positionRider(Entity passenger) {
        super.positionRider(passenger);
        if (passenger != getRidingPlayer() && isDoingGrabAttack()) {
            if (level.isClientSide) {
                passenger.setPos(grabPos.x, grabPos.y - 0.55, grabPos.z);
            } else {
                float t = 5 * Mth.sin(Mth.PI + tickCount * 0.275f);
                float radius = 0.35f * 0.7f * getScale() * -3;
                float angle = Mth.DEG_TO_RAD * yBodyRot + 3.15f + t * 1.75f * 0.05f;
                double extraX = radius * Mth.sin(Mth.PI + angle);
                double extraY = 0.065 * getScale();
                double extraZ = radius * Mth.cos(angle);
                passenger.setPos(getX() + extraX, getY() + extraY, getZ() + extraZ);
            }
        }
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
        switch (type) {
            case 0, 2 -> {
                super.disableCustomAI(type, disableAI);
                setNoGravity(disableAI);
            }
            default -> super.disableCustomAI(type, disableAI);
        }
    }

    @Override
    public void travel(Vec3 travelVector) {
        if (isImmobile() && !isVehicle() || !isAmphibious() && !isInWater()) {
            super.travel(Vec3.ZERO);
            return;
        }
        LivingEntity rider = (LivingEntity) getControllingPassenger();
        if (rider == null || !canBeControlledByRider() || (isControlledByLocalInstance() && !steering.trySteering(rider))) {
            if (isEffectiveAi() && isInWater()) {
                moveRelative(getSpeed(), travelVector);
                move(MoverType.SELF, getDeltaMovement());
                setDeltaMovement(getDeltaMovement().scale(0.9));
                if (!isNoGravity() && level.getFluidState(blockPosition().below()).is(FluidTags.WATER)) {
                    setDeltaMovement(getDeltaMovement().add(0.0, -0.005, 0.0));
                }
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
            steering.waterTravel(new Vec3(newStrafeMovement, travelVector.y, newForwardMovement), (Player) rider, playerJumpPendingScale);
        } else {
            setDeltaMovement(Vec3.ZERO);
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
        if (targetSailor.getVehicle() instanceof Boat boat && !level.isClientSide) {
            boat.kill();
            if (level.getGameRules().getBoolean(GameRules.RULE_DOENTITYDROPS)) {
                int i;
                for (i = 0; i < 3; i++) {
                    spawnAtLocation(boat.getBoatType().getPlanks());
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
    public void registerControllers(AnimationData data) {
        var controller = new AnimationController<>(this, AnimationLogic.IDLE_CTRL, 5, getAnimationLogic()::waterPredicate);
        registerEatingListeners(controller);
        data.addAnimationController(controller);
        data.addAnimationController(new AnimationController<>(
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
