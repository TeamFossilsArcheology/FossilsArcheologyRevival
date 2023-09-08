package com.fossil.fossil.entity.prehistoric.base;

import com.fossil.fossil.config.FossilConfig;
import com.fossil.fossil.entity.ai.FindAirTargetGoal;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.animal.FlyingAnimal;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

import static com.fossil.fossil.entity.animation.AnimationLogic.ServerAnimationInfo;

public abstract class PrehistoricFlying extends Prehistoric implements FlyingAnimal {

    private final FindAirTargetGoal findAirTargetGoal = new FindAirTargetGoal(this);

    private int flyingTicks = 0;

    public PrehistoricFlying(EntityType<? extends PrehistoricFlying> entityType, Level level, boolean isMultiPart) {
        super(entityType, level, isMultiPart);
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        goalSelector.addGoal(5, findAirTargetGoal);
    }

    @Override
    public boolean isFlying() {
        return false;
    }

    public void setFlying(boolean flying) {

    }

    @Override
    public void aiStep() {
        super.aiStep();
        boolean flying = level.isEmptyBlock(blockPosition().below());
        if (isFlying() && isSleeping()) {
            setSleeping(false);
        }
        if (!isOnGround() && getDeltaMovement().y < 0) {
            //motionY *= 0.6;
        }
        if (!isFlying() && !isOnGround()) {

        }
        /*if (flying && flyProgress < 20.0F) {
            flyProgress += 2F;
            if (sitProgress != 0)
                sitProgress = sleepProgress = 0F;
        } else if (!flying && flyProgress > 0.0F) {
            flyProgress -= 1F;
            if (sitProgress != 0)
                sitProgress = sleepProgress = 0F;
        }*/
        if (random.nextInt(250) == 0 && !isFlying() && !isImmobile() && !level.isClientSide && isAdult() && isOnGround() && tickCount > 50) {
            startFlying();
        }
        if (!level.isClientSide) {
            if (isFlying()) {
                flyingTicks++;
            } else {
                flyingTicks = 0;
            }
            if (flyingTicks > 80 && isOnGround()) {
                setFlying(false);
            }
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

    protected void startFlying() {

    }

    public void flyTowardsTarget() {

    }

    protected void onReachAirTarget(BlockPos target) {

    }

    private boolean isTargetInAir() {
        return findAirTargetGoal.targetPos != null && level.isEmptyBlock(findAirTargetGoal.targetPos);
    }

    public @Nullable BlockPos getBlockInView() {
        float radius = -(random.nextInt(20) + 6.3f);
        float neg = random.nextBoolean() ? 1 : -1;
        float angle = (0.01745329251F * yBodyRot) + 3.15F + (random.nextFloat() * neg);
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

    public abstract ServerAnimationInfo getTakeOffAnimation();
}
