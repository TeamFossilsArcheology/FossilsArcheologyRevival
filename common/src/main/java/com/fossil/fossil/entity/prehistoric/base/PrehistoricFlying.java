package com.fossil.fossil.entity.prehistoric.base;

import com.fossil.fossil.config.FossilConfig;
import com.fossil.fossil.entity.ai.*;
import com.fossil.fossil.entity.ai.control.CustomFlightMoveControl;
import com.fossil.fossil.entity.animation.AnimationLogic;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.MoveControl.Operation;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.SitWhenOrderedToGoal;
import net.minecraft.world.entity.ai.util.GoalUtils;
import net.minecraft.world.entity.animal.FlyingAnimal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.Animation;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;

public abstract class PrehistoricFlying extends Prehistoric implements FlyingAnimal {
    private static final EntityDataAccessor<Boolean> FLYING = SynchedEntityData.defineId(PrehistoricFlying.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> TAKING_OFF = SynchedEntityData.defineId(PrehistoricFlying.class, EntityDataSerializers.BOOLEAN);

    private int flyingTicks = 0;
    private int groundTicks = 0;
    private long takeOffStartTick = 0;
    private boolean takeOffAnimationStarted;
    public PrehistoricFlying(EntityType<? extends PrehistoricFlying> entityType, Level level, boolean isMultiPart) {
        super(entityType, level, isMultiPart);
        moveControl = new CustomFlightMoveControl(this);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Prehistoric.createAttributes()
                .add(Attributes.MOVEMENT_SPEED, 0.1)
                .add(Attributes.FLYING_SPEED, 0.4f);
    }

    @Override
    protected void registerGoals() {
        matingGoal = new DinoMatingGoal(this, 1);
        goalSelector.addGoal(1, new DinoPanicGoal(this, 1.5));
        goalSelector.addGoal(2, new SitWhenOrderedToGoal(this));
        goalSelector.addGoal(2, matingGoal);
        goalSelector.addGoal(3, new EatFromFeederGoal(this));
        goalSelector.addGoal(4, new EatItemEntityGoal(this));
        goalSelector.addGoal(5, new FlyingSleepGoal(this));
        goalSelector.addGoal(6, new FlyingWanderGoal(this));
        goalSelector.addGoal(6, new DinoWanderGoal(this, 1));
        goalSelector.addGoal(8, new LookAtPlayerGoal(this, Player.class, 8));
        targetSelector.addGoal(4, new HuntAndPlayGoal(this));
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        entityData.define(FLYING, false);
        entityData.define(TAKING_OFF, false);
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

    @Override
    public boolean isFlying() {
        return entityData.get(FLYING);
    }

    public void setFlying(boolean flying) {
        entityData.set(FLYING, flying);
    }

    @Override
    public void startSleeping(BlockPos pos) {
        if (!isFlying()) {
            super.startSleeping(pos);
        } else {
            //stopSleeping();
        }
    }

    @Override
    public void aiStep() {
        super.aiStep();
        if (!isOnGround() && getDeltaMovement().y < 0) {
            setDeltaMovement(getDeltaMovement().multiply(1, 0.6, 1));
        }
        if (!level.isClientSide) {
            if (isTakingOff() && isTakeOffAnimationDone()) {
                finishTakeOff();
            }
            if (isFlying()) {
                flyingTicks++;
            } else {
                groundTicks = 0;
                flyingTicks = 0;
            }
            if (flyingTicks > 80 && isOnGround()) {
                groundTicks++;
                if (groundTicks > 80) {
                    setFlying(false);
                }
            } else {
                groundTicks = 0;
            }
            boolean debug = false;
            if (debug || flyingTicks > getMaxExhaustion()) {
                moveTo(Vec3.atCenterOf(findLandPosition(true)), true);
            }
        }
    }

    @Nullable
    @Contract("true -> !null")
    public BlockPos findLandPosition(boolean force) {
        int x = blockPosition().getX() - 8 + random.nextInt(16);
        int z = blockPosition().getZ() - 8 + random.nextInt(16);
        int y = level.getHeight(Heightmap.Types.MOTION_BLOCKING, x, z);
        BlockPos.MutableBlockPos pos = new BlockPos.MutableBlockPos(x, y, z);
        if (force || GoalUtils.isSolid(this, new BlockPos(x, y - 1, z))) {
            //TODO: This is somewhat messy
            BlockHitResult result = level.clip(new ClipContext(position(), Vec3.atCenterOf(pos), ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, this));
            if (result.getType() != HitResult.Type.MISS) {
                pos = result.getBlockPos().relative(result.getDirection()).mutable();
                while (level.isEmptyBlock(pos) && pos.getY() > level.getMinBuildHeight()) {
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

    @Override
    public @NotNull CustomFlightMoveControl getMoveControl() {
        return (CustomFlightMoveControl) super.getMoveControl();
    }

    public void moveTo(Vec3 pos, boolean shouldLand) {
        if (isFlying()) {
            getMoveControl().setWantedPosition(pos.x, pos.y, pos.z, shouldLand);
        } else if (isTakingOff()) {
            getMoveControl().setWantedPosition(pos.x, pos.y, pos.z, shouldLand);
            getMoveControl().setOperation(Operation.WAIT);
        } else if (distanceToSqr(pos) > 40) {
            //start fly
            startTakeOff();
            getMoveControl().setWantedPosition(pos.x, pos.y, pos.z, shouldLand);
            getMoveControl().setOperation(Operation.WAIT);
        } else {
            //walk
            getNavigation().moveTo(pos.x, pos.y, pos.z, 1);
        }
    }

    @Override
    public boolean wantsToSleep() {
        return super.wantsToSleep() && !isFlying();
    }

    public boolean isTakingOff() {
        return entityData.get(TAKING_OFF);
    }

    public void startTakeOff() {
        entityData.set(TAKING_OFF, true);
        takeOffStartTick = level.getGameTime();
    }

    /**
     * Finish the take-off process and start flying afterward
     */
    public void finishTakeOff() {
        entityData.set(TAKING_OFF, false);
        takeOffStartTick = -1;
        setFlying(true);
        getMoveControl().setOperation(Operation.MOVE_TO);
    }

    /**
     * Cancel the take-off process and don't start flying afterward
     */
    public void cancelTakeOff() {
        entityData.set(TAKING_OFF, false);
        takeOffStartTick = -1;
    }

    public boolean isTakeOffAnimationDone() {
        int flyDelay = getAnimationLogic().getActionDelay("Movement/Idle/Eat");
        if (flyDelay < 0) {
            //Use animation end for animations without delay
            AnimationLogic.ActiveAnimationInfo activeAnimation = getActiveAnimation("Movement/Idle/Eat");
            return activeAnimation == null || level.getGameTime() > activeAnimation.endTick() + takeOffStartTick;
        }
        return level.getGameTime() > flyDelay + takeOffStartTick;
    }

    public void onReachAirTarget(BlockPos target) {

    }

    public @Nullable BlockPos getBlockInView() {
        float radius = -(random.nextInt(20) + 6.3f);
        float neg = random.nextBoolean() ? 1 : -1;
        float angle = (Mth.DEG_TO_RAD * yBodyRot) + 3.15f + (random.nextFloat() * neg);
        double extraX = radius * Mth.sin((float) (Math.PI + angle));
        double extraZ = radius * Mth.cos(angle);
        BlockPos radialPos = new BlockPos(getX() + extraX, 0, getZ() + extraZ);
        BlockPos ground = level.getHeightmapPos(Heightmap.Types.MOTION_BLOCKING, radialPos);
        int distFromGround = (int) getY() - ground.getY();
        BlockPos newPos = radialPos.above(distFromGround > 16 ? (int) Math.min(FossilConfig.getInt(FossilConfig.FLYING_TARGET_MAX_HEIGHT), getY() + random.nextInt(16) - 8) : (int) getY() + random.nextInt(16) + 1);
        if (!isTargetBlocked(Vec3.atCenterOf(newPos)) && distanceToSqr(Vec3.atCenterOf(newPos)) > 6) {
            return newPos;
        }
        return null;
    }

    public boolean isTargetBlocked(Vec3 target) {
        if (target != null) {
            BlockHitResult hitResult = level.clip(new ClipContext(position(), target, ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, this));
            return hitResult.getType() == HitResult.Type.BLOCK;
        }
        return false;
    }
    public @Nullable Vec3 generateAirTarget() {
        BlockHitResult[] results = new BlockHitResult[10];
        for (int i = 0; i < 10; i++) {
            float heightMod = (float)(getY() + 1) / (float)(level.getHeight(Heightmap.Types.MOTION_BLOCKING, (int)getX(), (int)getZ()) + 10);
            double targetX = getX() + (double)((random.nextFloat() * 2 - 1) * 16);
            double targetY = getY() + (double)((random.nextFloat() * 2 - heightMod) * 16);
            double targetZ = getZ() + (double)((random.nextFloat() * 2 - 1) * 16);
            Vec3 pos = new Vec3(targetX, targetY, targetZ);
            results[i] = level.clip(new ClipContext(position(), pos, ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, this));
            if (results[i].getType() == HitResult.Type.MISS) {
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

    private PlayState flyingPredicate(AnimationEvent<PrehistoricFlying> event) {
        AnimationController<PrehistoricFlying> controller = event.getController();
        if (isTakingOff()) {
            if (!takeOffAnimationStarted) {
                addActiveAnimation(controller.getName(), nextTakeOffAnimation());
                takeOffAnimationStarted = true;
            }
        } else {
            takeOffAnimationStarted = false;
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
        data.addAnimationController(new AnimationController<>(this, "Movement/Idle/Eat", 5, this::flyingPredicate));
        data.addAnimationController(new AnimationController<>(this, "Attack", 5, getAnimationLogic()::attackPredicate));
    }

    public abstract @NotNull Animation nextTakeOffAnimation();
}
