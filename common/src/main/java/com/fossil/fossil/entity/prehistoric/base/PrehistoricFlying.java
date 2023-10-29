package com.fossil.fossil.entity.prehistoric.base;

import com.fossil.fossil.config.FossilConfig;
import com.fossil.fossil.entity.ai.FindAirTargetGoal;
import com.fossil.fossil.entity.animation.AnimationLogic;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.animal.FlyingAnimal;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
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

    private FindAirTargetGoal findAirTargetGoal;
    private int flyingTicks = 0;
    private long takeOffStartTick = 0;
    private boolean takeOffAnimationStarted;

    public PrehistoricFlying(EntityType<? extends PrehistoricFlying> entityType, Level level, boolean isMultiPart) {
        super(entityType, level, isMultiPart);
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        findAirTargetGoal = new FindAirTargetGoal(this);
        goalSelector.addGoal(5, findAirTargetGoal);
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
            int flyDelay = getAnimationLogic().getActionDelay("Movement/Idle/Eat");
            if (isTakingOff() && flyDelay > -1 && level.getGameTime() > flyDelay + takeOffStartTick) {
                stopTakeOff();
                setFlying(true);
            }
            if (!isFlying() && !isImmobile() && random.nextInt(250) == 0 && isAdult() && isOnGround() && tickCount > 50) {
                startTakeOff();
            }
            if (isFlying()) {
                flyingTicks++;
            } else {
                flyingTicks = 0;
            }
            if (flyingTicks > 80 && isOnGround()) {
                setFlying(false);
            }
            if (isFlying() && canSleep() && !level.isEmptyBlock(blockPosition().below(2)) && !PrehistoricSwimming.isOverWater(this)) {
                setFlying(false);
            }
            if (isFlying() && getTarget() == null) {
                if (findAirTargetGoal.targetPos != null && isFlying()) {
                    if (!isTargetInAir()) {
                        findAirTargetGoal.targetPos = null;
                    }
                    flyTowardsTarget();
                }
            } else if (getTarget() != null) {
                flyTowardsTarget();
            }
        }
    }

    public boolean isTakingOff() {
        return entityData.get(TAKING_OFF);
    }

    protected void startTakeOff() {
        entityData.set(TAKING_OFF, true);
        takeOffStartTick = level.getGameTime();
    }

    protected void stopTakeOff() {
        entityData.set(TAKING_OFF, false);
    }

    public void flyTowardsTarget() {//TODO: Move to MoveControl?
        double bbLength = getBoundingBox().getSize() * 2.5;
        double maxDist = Math.min(3, bbLength * bbLength);
        if (isTargetInAir() && isFlying()) {
            Vec3 targetPos = Vec3.atCenterOf(findAirTargetGoal.targetPos);
            if (distanceToSqr(targetPos) > maxDist) {
                Vec3 offset = targetPos.subtract(position());
                Vec3 move = getDeltaMovement();
                move = move.add((Math.signum(offset.x) * 0.5 - move.x) * 0.2, (Math.signum(offset.y) * 0.5 - move.y) * 0.2, (Math.signum(offset.z) * 0.5 - move.z) * 0.2);
                setDeltaMovement(move);
                float angle = (float) (Mth.atan2(move.z, move.x) * Mth.RAD_TO_DEG - 90);
                float rotation = Mth.wrapDegrees(angle - getYRot());
                zza = 0.5f;
                if (Math.abs(move.x) > 0.12 || Math.abs(move.z) > 0.12) {
                    setYRot(getYRot() + rotation);
                }
            } else {
                onReachAirTarget(findAirTargetGoal.targetPos);
                findAirTargetGoal.targetPos = null;
            }
        } else {
            findAirTargetGoal.targetPos = null;
        }
        if (horizontalCollision) {
            findAirTargetGoal.targetPos = null;
        }
    }

    protected void onReachAirTarget(BlockPos target) {

    }

    private boolean isTargetInAir() {
        return findAirTargetGoal.targetPos != null && level.isEmptyBlock(findAirTargetGoal.targetPos);
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
            return !level.isEmptyBlock(new BlockPos(hitResult.getLocation())) || !level.isEmptyBlock(hitResult.getBlockPos());
        }
        return false;
    }

    public @Nullable BlockPos generateAirTarget() {
        BlockPos pos = null;
        for (int i = 0; i < 10; i++) {
            pos = getBlockInView();
            if (pos != null && level.isEmptyBlock(pos) && !isTargetBlocked(Vec3.atCenterOf(pos))) {
                return pos;
            }
        }
        return pos;
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
