package com.github.teamfossilsarcheology.fossil.entity.prehistoric.base;

import com.github.teamfossilsarcheology.fossil.config.FossilConfig;
import com.github.teamfossilsarcheology.fossil.entity.LaserPointEntity;
import com.github.teamfossilsarcheology.fossil.entity.ai.*;
import com.github.teamfossilsarcheology.fossil.entity.ai.control.CustomFlightBodyRotationControl;
import com.github.teamfossilsarcheology.fossil.entity.ai.control.CustomFlightLookControl;
import com.github.teamfossilsarcheology.fossil.entity.ai.control.CustomFlightMoveControl;
import com.github.teamfossilsarcheology.fossil.entity.ai.navigation.FlightPathNavigation;
import com.github.teamfossilsarcheology.fossil.entity.animation.AnimationCategory;
import com.github.teamfossilsarcheology.fossil.entity.animation.AnimationInfo;
import com.github.teamfossilsarcheology.fossil.entity.animation.AnimationLogic;
import com.github.teamfossilsarcheology.fossil.entity.animation.PausableAnimationController;
import com.github.teamfossilsarcheology.fossil.entity.prehistoric.system.FlyingSleepSystem;
import com.github.teamfossilsarcheology.fossil.entity.prehistoric.system.SleepSystem;
import com.github.teamfossilsarcheology.fossil.entity.util.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.BodyRotationControl;
import net.minecraft.world.entity.ai.control.MoveControl.Operation;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.navigation.FlyingPathNavigation;
import net.minecraft.world.entity.ai.util.GoalUtils;
import net.minecraft.world.entity.ai.util.LandRandomPos;
import net.minecraft.world.entity.animal.FlyingAnimal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.core.animation.AnimatableManager;

public abstract class PrehistoricFlying extends Prehistoric implements FlyingAnimal {
    private static final EntityDataAccessor<Boolean> FLYING = SynchedEntityData.defineId(PrehistoricFlying.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> TAKING_OFF = SynchedEntityData.defineId(PrehistoricFlying.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> FLYING_UP = SynchedEntityData.defineId(PrehistoricFlying.class, EntityDataSerializers.BOOLEAN);

    private int flyingTicks = 0;
    private int groundTicks = 0;
    private long takeOffStartTick = 0;
    private boolean usingStuckNavigation;
    private boolean flyingDown;
    public float prevPitch;
    public float currentPitch;
    public float prevYaw;
    public float autoPitch;
    public float currentYaw;

    protected PrehistoricFlying(EntityType<? extends PrehistoricFlying> entityType, Level level) {
        super(entityType, level);
        moveControl = new CustomFlightMoveControl(this);
        lookControl = new CustomFlightLookControl<>(this);
    }

    @Override
    protected @NotNull BodyRotationControl createBodyControl() {
        return new CustomFlightBodyRotationControl<>(this);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Prehistoric.createAttributes()
                .add(Attributes.MOVEMENT_SPEED, 0.1)
                .add(Attributes.FLYING_SPEED, 0.4f);
    }

    @Override
    protected void registerGoals() {
        matingGoal = new DinoMatingGoal(this, 1);
        goalSelector.addGoal(Util.IMMOBILE + 1, new DinoPanicGoal(this, 1.5));
        goalSelector.addGoal(Util.IMMOBILE + 2, new FloatGoal(this));
        if (aiAttackType() != PrehistoricEntityInfoAI.Attacking.NONE && aiAttackType() != PrehistoricEntityInfoAI.Attacking.JUMP) {
            goalSelector.addGoal(Util.ATTACK + 1, new DelayedAttackGoal<>(this, attributes().sprintMod(), false));
        }
        goalSelector.addGoal(Util.SLEEP + 2, matingGoal);
        goalSelector.addGoal(Util.NEEDS, new FlyingLandNearFoodGoal(this));
        goalSelector.addGoal(Util.NEEDS + 1, new FlyingEatFromFeederGoal(this));
        goalSelector.addGoal(Util.NEEDS + 2, new FlyingEatItemEntityGoal(this));
        goalSelector.addGoal(Util.NEEDS + 3, new PlayGoal(this, 1));
        goalSelector.addGoal(Util.WANDER, new DinoFollowOwnerGoal(this, attributes().sprintMod(), 7, 2, false));
        goalSelector.addGoal(Util.WANDER + 1, new FlyingWanderGoal(this));
        goalSelector.addGoal(Util.WANDER + 1, new DinoWanderGoal(this, 1));
        goalSelector.addGoal(Util.LOOK, new LookAtPlayerGoal(this, Player.class, 8));
        goalSelector.addGoal(Util.LOOK + 1, new RandomLookAroundGoal(this));
        targetSelector.addGoal(5, new HuntingTargetGoal(this));

        targetSelector.addGoal(4, new NearestAttackableTargetGoal<>(this, LaserPointEntity.class, true));
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        entityData.define(FLYING, false);
        entityData.define(TAKING_OFF, false);
        entityData.define(FLYING_UP, false);
    }

    @Override
    public void onSyncedDataUpdated(EntityDataAccessor<?> key) {
        if (FLYING.equals(key)) {
            setXRot(0);
        }
        super.onSyncedDataUpdated(key);
    }

    @Override
    protected @NotNull SleepSystem createSleepSystem() {
        return new FlyingSleepSystem(this);
    }

    @Override
    public void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        compound.putBoolean("Flying", isFlying());
    }

    @Override
    public void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        setFlying(compound.getBoolean("Flying"));
    }

    protected Pose getTargetPose() {
        return isFlying() ? Pose.FALL_FLYING : super.getTargetPose();
    }

    @Override
    public boolean isFlying() {
        return entityData.get(FLYING);
    }

    public void setFlying(boolean flying) {
        entityData.set(FLYING, flying);
        updatePose();
    }

    /**
     * If the mob should fly up and look up while being ridden
     */
    public boolean isFlyingUp() {
        return entityData.get(FLYING_UP);
    }

    public void setFlyingUp(boolean flyingUp) {
        entityData.set(FLYING_UP, flyingUp);
    }

    /**
     * If the mob should fly down and look down while being ridden
     */
    public boolean isFlyingDown() {
        return flyingDown;
    }

    public void setFlyingDown(boolean flyingDown) {
        this.flyingDown = flyingDown;
    }

    public void doStuckNavigation(Vec3 target) {
        switchNavigator(true);
        getNavigation().moveTo(target.x, target.y, target.z, 1);
    }

    public void switchNavigator(boolean fly) {
        usingStuckNavigation = fly;
        if (fly) {
            navigation = new FlightPathNavigation(this, level());//TODO: Maybe use custom class that works better with our larger mobs
        } else {
            navigation = createNavigation(level());
        }
    }

    public boolean isUsingStuckNavigation() {
        return usingStuckNavigation;
    }

    @Override
    public boolean canJump() {
        return false;
    }

    @Override
    public void travel(Vec3 travelVector) {
        LivingEntity rider = getControllingPassenger();
        if (rider == null || !canBeControlledByRider()) {
            super.travel(travelVector);
            return;
        }
        setYRot(rider.getYRot());
        setXRot(rider.getXRot() * 0.5f);
        setRot(getYRot(), getXRot());
        yBodyRot = getYRot();
        yHeadRot = getYRot();
        float newStrafeMovement = rider.xxa * 0.5f;
        float newForwardMovement = rider.zza;

        if (isControlledByLocalInstance()) {
            setSpeed((float) getAttributeValue(Attributes.MOVEMENT_SPEED));
            Vec3 newMovement = new Vec3(newStrafeMovement, travelVector.y, newForwardMovement).scale(0.5);
            if (isFlying()) {
                steering.airTravel(newMovement);
            } else {
                super.travel(newMovement);
            }
        } else {
            setDeltaMovement(Vec3.ZERO);
            calculateEntityAnimation(true);
        }
    }

    @Override
    protected @NotNull MovementEmission getMovementEmission() {
        return !isFlying() ? super.getMovementEmission() : MovementEmission.NONE;
    }

    @Override
    public void tick() {
        super.tick();
        if (level().isClientSide) {
            Entity rider = getControllingPassenger();
            if (rider != null) {
                if (isFlyingUp()) {
                    autoPitch = Mth.approach(autoPitch, -70, 5);
                } else if (isFlyingDown()) {
                    autoPitch = Mth.approach(autoPitch, 70, 5);
                } else {
                    autoPitch = Mth.approach(autoPitch, 0, 2);
                }
                prevPitch = currentPitch;
                currentPitch = Mth.clamp(rider.xRotO + autoPitch, -70, 70);
                prevYaw = currentYaw;
                float yawDiff = yRotO - getYRot();
                if (yawDiff > 1) {
                    currentYaw = Mth.approach(currentYaw, 10, 3);
                } else if (yawDiff < -1) {
                    currentYaw = Mth.approach(currentYaw, -10, 3);
                } else {
                    currentYaw = Mth.approach(currentYaw, 0, 3);
                }
            }
        }
    }

    @Override
    public void aiStep() {
        super.aiStep();
        if (!onGround() && getDeltaMovement().y < 0 && !isFlying()) {
            setDeltaMovement(getDeltaMovement().multiply(1, 0.6, 1));
        }
        if (!level().isClientSide) {
            if (isTakingOff() && isTakeOffAnimationDone()) {
                finishTakeOff();
            }
            if (getNavigation() instanceof FlyingPathNavigation flyingPathNavigation) {
                if (flyingPathNavigation.isDone() || (usingStuckNavigation && !isFlying())) {
                    switchNavigator(false);
                }
            }
            if (isFlying()) {
                flyingTicks++;
            } else {
                groundTicks = 0;
                flyingTicks = 0;
            }
            if (flyingTicks > 80 && onGround()) {
                groundTicks++;
                if (groundTicks > 80) {
                    setFlying(false);
                }
            } else {
                groundTicks = 0;
            }
            boolean debug = false;
            if (debug || flyingTicks > getMaxExhaustion()) {
                moveTo(Vec3.atCenterOf(findLandPosition(true)), true, true);
            }
            if (isFlyingUp() && (!isFlying() || !isVehicle())) {
                setFlyingUp(false);
            }
        }
    }

    @Override
    public boolean eatItem(ItemStack stack) {
        if (isFlying() || isTakingOff()) {
            return false;
        }
        return super.eatItem(stack);
    }

    @Nullable
    @Contract("true -> !null")
    public BlockPos findLandPosition(boolean force) {
        Vec3 vec3 = LandRandomPos.getPos(this, 8, 15);
        BlockPos.MutableBlockPos pos;
        if (vec3 == null) {
            int x = blockPosition().getX() - 8 + random.nextInt(16);
            int z = blockPosition().getZ() - 8 + random.nextInt(16);
            int y = level().getHeight(Heightmap.Types.MOTION_BLOCKING, x, z);
            pos = new BlockPos.MutableBlockPos(x, y - 1, z);
        } else {
            pos = BlockPos.containing(vec3).mutable().move(Direction.DOWN);
        }
        if (force || GoalUtils.isSolid(this, pos)) {
            BlockHitResult result = level().clip(new ClipContext(position(), Vec3.atCenterOf(pos), ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, this));
            if (result.getType() != HitResult.Type.MISS) {
                pos = result.getBlockPos().relative(result.getDirection()).mutable();
                while (level().isEmptyBlock(pos) && pos.getY() > level().getMinBuildHeight()) {
                    pos.move(0, -1, 0);
                }
            }
            return pos.immutable();
        }
        return null;
    }

    private int getMaxExhaustion() {
        return 20 * 60 * 5;
    }

    public void moveTo(Vec3 pos, boolean shouldLand, boolean shouldFly) {
        if (!(getMoveControl() instanceof CustomFlightMoveControl control)) {
            return;
        }
        if (isFlying()) {
            control.setFlyingTarget(pos.x, pos.y, pos.z, shouldLand);
            control.setOperation(Operation.MOVE_TO);
        } else if (isTakingOff()) {
            control.setFlyingTarget(pos.x, pos.y, pos.z, shouldLand);
            control.setOperation(Operation.WAIT);
        } else if (distanceToSqr(pos) > 40 || shouldFly) {
            //start fly
            if (hasTakeOffAnimation()) {
                startTakeOff();
                control.setFlyingTarget(pos.x, pos.y, pos.z, shouldLand);
                control.setOperation(Operation.WAIT);
            } else {
                setFlying(true);
                control.setFlyingTarget(pos.x, pos.y, pos.z, shouldLand);
                control.setOperation(Operation.MOVE_TO);
            }
        } else {
            //walk
            getNavigation().moveTo(pos.x, pos.y, pos.z, 1);
        }
    }

    public boolean isTakingOff() {
        return entityData.get(TAKING_OFF);
    }

    public void startTakeOff() {
        entityData.set(TAKING_OFF, true);
        takeOffStartTick = level().getGameTime();
        getAnimationLogic().triggerAnimation(AnimationLogic.IDLE_CTRL, nextTakeOffAnimation(), AnimationCategory.NONE);
    }

    /**
     * Finish the take-off process and start flying afterward
     */
    public void finishTakeOff() {
        entityData.set(TAKING_OFF, false);
        takeOffStartTick = -1;
        setFlying(true);
        if (getMoveControl() instanceof CustomFlightMoveControl control) {
            control.setOperation(Operation.MOVE_TO);
        }
    }

    /**
     * Cancel the take-off process and don't start flying afterward
     */
    public void cancelTakeOff() {
        entityData.set(TAKING_OFF, false);
        takeOffStartTick = -1;
    }

    public boolean isTakeOffAnimationDone() {
        double flyDelay = getAnimationLogic().getActionDelay(AnimationLogic.IDLE_CTRL);
        return level().getGameTime() > flyDelay + takeOffStartTick;
    }

    public void onReachAirTarget(BlockPos target) {

    }

    public @Nullable BlockPos getBlockInView() {
        float radius = -(random.nextInt(20) + 6.3f);
        float neg = random.nextBoolean() ? 1 : -1;
        float angle = (Mth.DEG_TO_RAD * yBodyRot) + 3.15f + (random.nextFloat() * neg);
        double extraX = radius * Mth.sin((float) (Math.PI + angle));
        double extraZ = radius * Mth.cos(angle);
        BlockPos radialPos = BlockPos.containing(getX() + extraX, 0, getZ() + extraZ);
        BlockPos ground = level().getHeightmapPos(Heightmap.Types.MOTION_BLOCKING, radialPos);
        int distFromGround = (int) getY() - ground.getY();
        BlockPos newPos = radialPos.above(distFromGround > 16 ? (int) Math.min(FossilConfig.getInt(FossilConfig.FLYING_TARGET_MAX_HEIGHT), getY() + random.nextInt(16) - 8) : (int) getY() + random.nextInt(16) + 1);
        if (!isTargetBlocked(Vec3.atCenterOf(newPos)) && distanceToSqr(Vec3.atCenterOf(newPos)) > 6) {
            return newPos;
        }
        return null;
    }

    public boolean isTargetBlocked(Vec3 target) {
        if (target != null) {
            BlockHitResult hitResult = level().clip(new ClipContext(position(), target, ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, this));
            return hitResult.getType() == HitResult.Type.BLOCK;
        }
        return false;
    }

    public @Nullable Vec3 generateAirTarget() {
        BlockHitResult[] results = new BlockHitResult[10];
        for (int i = 0; i < 10; i++) {
            float heightMod = (float) (getY() + 1) / (float) (level().getHeight(Heightmap.Types.MOTION_BLOCKING, (int) getX(), (int) getZ()) + 10);
            double targetX = getX() + (double) ((random.nextFloat() * 2 - 1) * 16);
            double targetY = getY() + (double) ((random.nextFloat() * 2 - heightMod) * 16);
            double targetZ = getZ() + (double) ((random.nextFloat() * 2 - 1) * 16);
            Vec3 pos = new Vec3(targetX, targetY, targetZ);
            BlockHitResult result = level().clip(new ClipContext(position(), pos, ClipContext.Block.COLLIDER, isInWater() ? ClipContext.Fluid.NONE : ClipContext.Fluid.ANY, this));
            results[i] = result;
            BlockPos.MutableBlockPos mutable = result.getBlockPos().mutable();
            while (!level().getFluidState(mutable).isEmpty()) {
                mutable.move(0, 1, 0);
            }
            results[i] = new BlockHitResult(result.getLocation(), result.getDirection(), mutable.immutable(), result.isInside());
            if (result.getType() == HitResult.Type.MISS) {
                return pos;
            }
        }
        //If there is no direct path to all the targets we instead look for the one farthest away
        BlockHitResult furthest = null;
        double distance = 5;
        for (int i = 1; i < results.length; i++) {
            double g = position().distanceTo(results[i].getLocation());
            if (g > distance) {
                furthest = results[i];
                distance = g;
            }
        }
        return furthest != null ? Vec3.atCenterOf(furthest.getBlockPos().relative(furthest.getDirection())) : null;
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllerRegistrar) {
        var controller = new PausableAnimationController<>(this, AnimationLogic.IDLE_CTRL, 5, getAnimationLogic()::flyingPredicate);
        registerControllerWithTriggers(controllerRegistrar, controller);
        registerControllerWithTriggers(controllerRegistrar, new PausableAnimationController<>(
                this, AnimationLogic.ATTACK_CTRL, 5, getAnimationLogic()::attackPredicate));
    }

    public boolean hasTakeOffAnimation() {
        return true;
    }

    public abstract @NotNull AnimationInfo nextTakeOffAnimation();
}
