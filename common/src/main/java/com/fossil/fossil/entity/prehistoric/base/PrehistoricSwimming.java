package com.fossil.fossil.entity.prehistoric.base;

import com.fossil.fossil.entity.ai.navigation.AmphibiousPathNavigation;
import com.fossil.fossil.entity.ai.navigation.LargeSwimNodeEvaluator;
import com.fossil.fossil.network.MessageHandler;
import com.fossil.fossil.network.debug.MarkMessage;
import com.fossil.fossil.util.Gender;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.entity.ai.control.SmoothSwimmingMoveControl;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.ai.navigation.WaterBoundPathNavigation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.level.pathfinder.PathFinder;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

public abstract class PrehistoricSwimming extends Prehistoric {
    private static final EntityDataAccessor<Boolean> IS_BREACHING = SynchedEntityData.defineId(PrehistoricSwimming.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Float> BREACHING_PITCH = SynchedEntityData.defineId(PrehistoricSwimming.class, EntityDataSerializers.FLOAT);
    public static final int MAX_TIME_IN_WATER = 1000;
    public static final int MAX_TIME_ON_LAND = 1000;
    public int timeInWater = 0;
    public int timeOnLand = 0;
    /**
     * Cache check for current navigator
     */
    protected boolean isLandNavigator = true;
    public float prevBreachPitch;
    protected int breachCooldown = 0;
    protected boolean isGoingDownAfterBreach = false;
    private Vec3 targetPos;

    public PrehistoricSwimming(EntityType<? extends Prehistoric> entityType, Level level, boolean isMultiPart) {
        super(entityType, level, isMultiPart);
        setPathfindingMalus(BlockPathTypes.WATER, 0);
        switchNavigator(true);
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        entityData.define(IS_BREACHING, false);
        entityData.define(BREACHING_PITCH, 0f);
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
        return new WaterBoundPathNavigation(this, level);
    }

    protected void switchNavigator(boolean onLand) {
        //TODO: Properly implement all four classes
        if (onLand) {
            moveControl = new MoveControl(this);
            navigation = new AmphibiousPathNavigation(this);
            isLandNavigator = true;
        } else {
            if (getGender() == Gender.FEMALE) {
                moveControl = new SwimmingMoveControl(this);
            } else {
                moveControl = new SmoothSwimmingMoveControl(this, 90, 5, 0.1f, 0.01f, true);
            }
            navigation = new LargeSwimmerPathNavigation(this);
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

    public abstract boolean isAmphibious();

    @Override
    public boolean canBreatheUnderwater() {
        return true;
    }

    @Override
    protected @NotNull MovementEmission getMovementEmission() {
        return MovementEmission.EVENTS;
    }

    protected boolean useSwimAI() {
        return isInWater();
    }

    @Override
    public void tick() {
        super.tick();
    }

    private static boolean isOverWater(LivingEntity entity) {
        if (entity.level.getBlockState(entity.blockPosition().below()).getMaterial() == Material.WATER) {
            return true;
        }
        if (entity.level.getBlockState(entity.blockPosition().below(2)).getMaterial() == Material.WATER) {
            return true;
        }
        if (entity.level.getBlockState(entity.blockPosition().below(3)).getMaterial() == Material.WATER) {
            return true;
        }
        return false;
    }

    @Override
    public void aiStep() {
        super.aiStep();
        prevBreachPitch = getBreachPitch();
        if (breachCooldown > 0) {
            breachCooldown--;
        }

        if (doesBreachAttack()) {
            if (getTarget() != null) {
                if (canReachPrey() && isBreaching()) {
                    isGoingDownAfterBreach = true;
                    setBreaching(false);
                }
                LivingEntity target = getTarget();
                if (!isEntitySubmerged(target) && isOverWater(target) && isInWater() && !hasPassenger(target) && breachCooldown == 0) {
                    setBreaching(true);
                    isGoingDownAfterBreach = false;
                    breachCooldown = 120;
                    targetPos = target.position().add(0, 1, 0);
                }
                if (isEntitySubmerged(target) || !isOverWater(target)) {
                    setBreaching(false);
                    isGoingDownAfterBreach = false;
                }
                if (isBreaching() && !isGoingDownAfterBreach) {
                    Vec3 distance = targetPos.subtract(position());
                    Vec3 current = getDeltaMovement();
                    double movementX = (Mth.sign(distance.x) * 0.5 - current.x) * 0.100000000372529 * 2;
                    double movementY = (Mth.sign(distance.y) * 0.5 - current.y) * 0.100000000372529 * 5;
                    double movementZ = (Mth.sign(distance.z) * 0.5 - current.z) * 0.100000000372529 * 2;
                    Vec3 next = current.add(movementX, movementY, movementZ);
                    setDeltaMovement(next);
                    float angle = (float) (Mth.atan2(next.z, next.x) * Mth.RAD_TO_DEG) - 90;
                    float rotation = Mth.wrapDegrees(angle - getYRot());
                    zza = 0.5f;
                    setRot(getYRot() + rotation, getXRot());
                    double dist = distanceToSqr(targetPos);
                    if (dist < 2.5) {
                        setBreaching(false);
                        isGoingDownAfterBreach = true;
                    }
                }
                if (!isEntitySubmerged(target) && !isOverWater(target)) {
                    setTarget(null);
                }
            }
            if (isInWater()) {
                isGoingDownAfterBreach = false;
            }
            //Rotate based on direction
            if (isInWater() || !isOnGround()) {
                Vec3 vec3 = getDeltaMovement();
                if (!level.isClientSide && navigation.getPath() != null) {
                    BlockPos blockPos = navigation.getPath().getNextNodePos();
                    int[] targets = new int[3];
                    targets[0] = blockPos.getX();
                    targets[1] = blockPos.getY();
                    targets[2] = blockPos.getZ();
                    BlockState[] blocks = new BlockState[1];
                    blocks[0] = Blocks.GOLD_BLOCK.defaultBlockState();
                    MessageHandler.DEBUG_CHANNEL.sendToPlayers(((ServerLevel)level).getPlayers(serverPlayer -> serverPlayer.hasLineOfSight(this)),
                            new MarkMessage(targets, blocks));
                }
                if (!level.isClientSide) {
                    if (Mth.abs((float) vec3.y) < 1.0E-4) {
                        setXRot(Mth.rotlerp(getXRot(), 0, 0.2f));
                    } else if (vec3.length() > 1.0E-5) {
                        float angle = (float) (Math.atan2(vec3.y, vec3.horizontalDistance()) * Mth.RAD_TO_DEG);
                        setXRot(angle * 0.5f);
                    }
                }
                Vec3 move = position().subtract(xo, yo, zo);
                /*if (move.length() > 1.0E-4) {
                    float angle = (float) (Mth.atan2(move.y, move.horizontalDistance()) * Mth.RAD_TO_DEG);
                    float rotation = Mth.wrapDegrees(angle - getXRot());
                    setRot(getYRot(), getXRot() + rotation);
                }*/
                float currentBreachPitch = (float) Mth.clamp(getBreachPitch() + move.y * 15, -60, 60);
                setBreachPitch(currentBreachPitch);
                if (currentBreachPitch > 0 && move.y == 0) {
                    decrementBreachPitch(1);
                } else if (currentBreachPitch < 0 && move.y == 0) {
                    incrementBreachPitch(1);
                }
            } else {
                setBreachPitch(0);
            }
        }

        if (!level.isClientSide) {
            if (isInWater() && useSwimAI() && isLandNavigator) {
                switchNavigator(false);
            } else if (!isInWater() && !useSwimAI() && !isLandNavigator) {
                switchNavigator(true);
            }
        }

        if (isInWater() && (isOrderedToSit() || isSleeping())) {
            setOrderedToSit(false);
            setSleeping(false);
        }
        if (getRidingPlayer() != null && isInWater()) {

        }
        if (isInWater()) {
            timeInWater++;
            timeOnLand = 0;
        } else if (onGround) {
            timeInWater = 0;
            timeOnLand++;
        }
    }

    protected void handleAirSupply(int airSupply) {
        if (isAlive() && !isInWaterOrBubble()) {
            setAirSupply(airSupply - 1);
            if (getAirSupply() == -40) {
                setAirSupply(0);
                hurt(DamageSource.DROWN, 2.0f);
            }
        } else {
            setAirSupply(500);
        }
    }

    @Override
    public void baseTick() {
        int i = getAirSupply();
        super.baseTick();
        handleAirSupply(i);
    }

    @Override
    public void travel(Vec3 travelVector) {
        if (isOrderedToSit() || !isAmphibious() && !isInWater()) {
            super.travel(Vec3.ZERO);
            return;
        }
        if (isVehicle() && canBeControlledByRider()) {
            Player rider = getRidingPlayer();
            if (rider != null) {
                float newStrafeMovement = rider.xxa * 0.5f;
                float newForwardMovement = rider.zza;
                fallDistance = 0;
                newStrafeMovement = newStrafeMovement * 0.125f;
                newForwardMovement = newForwardMovement * 0.25f;
                if (isInWater()) {
                    moveRelative(1, new Vec3(newStrafeMovement, travelVector.y, newForwardMovement));
                    float f4 = 0.01f;
                    double d0 = getScaledSwimSpeed() * 0.5;
                    if (!isOnGround()) {
                        d0 *= 0.5;
                    }
                    if (d0 > 0) {
                        f4 += (0.54600006 - f4) * d0 / 3.0;
                    }
                    f4 = f4 * 0.900000011920929f;
                    move(MoverType.SELF, getDeltaMovement());
                    setDeltaMovement(getDeltaMovement().x*f4, getDeltaMovement().y*f4+0.1185, getDeltaMovement().z*f4);
                } else {
                    setSpeed(1);
                    super.travel(new Vec3(newStrafeMovement, travelVector.y, newForwardMovement));
                    return;
                }
                setSpeed(1);
                super.travel(new Vec3(newStrafeMovement, 0, newForwardMovement));
                //TODO: copy from existing ride code
            }
        }
        if (isEffectiveAi() && isInWater()) {
            moveRelative(getSpeed(), travelVector);//TOOD: Figure out amount
            move(MoverType.SELF, getDeltaMovement());
            setDeltaMovement(getDeltaMovement().scale(0.9));
            if (getTarget() == null) {
                setDeltaMovement(getDeltaMovement().add(0.0, -0.005, 0.0));
            }
        } else {
            super.travel(travelVector);
        }
        super.travel(travelVector);
    }

    @Override
    public boolean onClimbable() {
        return super.onClimbable();
    }

    public abstract double swimSpeed();

    private double getScaledSwimSpeed() {
        return getScale() / data().maxScale() * swimSpeed();
    }

    @Override
    public boolean canDinoHunt(LivingEntity target, boolean hunger) {
        if (doesBreachAttack() && isOverWater(target)) {
            return super.canDinoHunt(target, hunger);
        }
        return super.canDinoHunt(target, hunger) && (target.isInWater() || canHuntMobsOnLand());
    }

    protected boolean canHuntMobsOnLand() {
        return false;
    }

    public boolean doesBreachAttack() {
        return false;
    }

    protected void destroyBoat(Entity targetSailor) {
        if (targetSailor.getVehicle() instanceof Boat boat && !level.isClientSide) {
            boat.kill();
            if (level.getGameRules().getBoolean(GameRules.RULE_DOENTITYDROPS)) {
                int i;
                for (i = 0; i < 3; ++i) {
                    spawnAtLocation(boat.getBoatType().getPlanks());
                }
                for (i = 0; i < 2; ++i) {
                    spawnAtLocation(Items.STICK);
                }
            }

        }
    }

    @Override
    public boolean canBeControlledByRider() {
        return type().isVivariousAquatic() ? isInWater() && super.canBeControlledByRider() : super.canBeControlledByRider();
    }

    private boolean isEntitySubmerged(LivingEntity entity) {
        return level.getBlockState(entity.blockPosition().above()).getMaterial() == Material.WATER;
    }

    public boolean isBreaching() {
        return entityData.get(IS_BREACHING);
    }

    public void setBreaching(boolean breaching) {
        entityData.set(IS_BREACHING, breaching);
    }

    public float getBreachPitch() {
        return entityData.get(BREACHING_PITCH);
    }

    public void setBreachPitch(float pitch) {
        entityData.set(BREACHING_PITCH, pitch);
    }

    public void incrementBreachPitch(float pitch) {
        entityData.set(BREACHING_PITCH, getBreachPitch() + pitch);
    }

    public void decrementBreachPitch(float pitch) {
        entityData.set(BREACHING_PITCH, getBreachPitch() - pitch);
    }


    static class LargeSwimmerPathNavigation extends WaterBoundPathNavigation {

        public LargeSwimmerPathNavigation(PrehistoricSwimming mob) {
            super(mob, mob.level);
        }

        @Override
        protected @NotNull PathFinder createPathFinder(int maxVisitedNodes) {
            nodeEvaluator = new LargeSwimNodeEvaluator(((PrehistoricSwimming)mob).doesBreachAttack());
            return new PathFinder(nodeEvaluator, maxVisitedNodes);
        }

        @Override
        protected @NotNull Vec3 getTempMobPos() {
            return super.getTempMobPos();
            //return new Vec3(mob.getX(), mob.getY() + 0.49, mob.getZ());
        }

        @Override
        protected boolean canMoveDirectly(Vec3 posVec31, Vec3 posVec32) {
            Vec3 vec3 = new Vec3(posVec32.x, posVec32.y + 0.49, posVec32.z);
            return level.clip(new ClipContext(posVec31, vec3, ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, mob)).getType() == HitResult.Type.MISS;
        }
    }

    static class SwimmingMoveControl extends SmoothSwimmingMoveControl {

        public SwimmingMoveControl(Mob mob) {
            super(mob, 90, 10, 0.02F, 0, true);
        }
    }
}
