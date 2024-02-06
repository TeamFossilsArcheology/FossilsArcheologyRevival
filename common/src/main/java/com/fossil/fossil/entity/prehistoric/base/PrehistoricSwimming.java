package com.fossil.fossil.entity.prehistoric.base;

import com.fossil.fossil.entity.ToyBase;
import com.fossil.fossil.entity.ai.control.SmoothTurningMoveControl;
import com.fossil.fossil.entity.ai.navigation.AmphibiousPathNavigation;
import com.fossil.fossil.entity.animation.AnimationLogic;
import com.mojang.math.Vector3d;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.LookControl;
import net.minecraft.world.entity.ai.control.SmoothSwimmingLookControl;
import net.minecraft.world.entity.ai.control.SmoothSwimmingMoveControl;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.ai.navigation.WaterBoundPathNavigation;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.level.pathfinder.PathFinder;
import net.minecraft.world.level.pathfinder.SwimNodeEvaluator;
import net.minecraft.world.phys.Vec3;
import org.apache.commons.lang3.tuple.Pair;
import org.jetbrains.annotations.NotNull;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.Animation;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.processor.IBone;
import software.bernie.geckolib3.core.snapshot.BoneSnapshot;
import software.bernie.geckolib3.geo.render.built.GeoBone;

import java.util.Map;

public abstract class PrehistoricSwimming extends Prehistoric {
    public static final int MAX_TIME_IN_WATER = 1000;
    public static final int MAX_TIME_ON_LAND = 1000;
    private static final EntityDataAccessor<Boolean> IS_BREACHING = SynchedEntityData.defineId(PrehistoricSwimming.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Float> BREACHING_PITCH = SynchedEntityData.defineId(PrehistoricSwimming.class, EntityDataSerializers.FLOAT);
    private static final EntityDataAccessor<Boolean> GRABBING = SynchedEntityData.defineId(PrehistoricSwimming.class, EntityDataSerializers.BOOLEAN);
    public int timeInWater = 0;
    public int timeOnLand = 0;
    /**
     * Cache check for current navigator
     */
    protected boolean isLandNavigator = true;
    protected boolean breachTargetReached = false;
    protected long grabStartTick = -1;
    private boolean beached;

    protected PrehistoricSwimming(EntityType<? extends Prehistoric> entityType, Level level) {
        super(entityType, level);
        setPathfindingMalus(BlockPathTypes.WATER, 0);
        switchNavigator(false);
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
        entityData.define(IS_BREACHING, false);
        entityData.define(BREACHING_PITCH, 0f);
        entityData.define(GRABBING, false);
    }

    @Override
    public void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        compound.putInt("TimeInWater", timeInWater);
        compound.putInt("TimeOnLand", timeOnLand);
        compound.putBoolean("Breaching", isBreaching());
        compound.putFloat("BreachPitch", getBreachPitch());
        compound.putBoolean("Grabbing", isDoingGrabAttack());
    }

    @Override
    public void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        timeInWater = compound.getInt("TimeInWater");
        timeOnLand = compound.getInt("TimeOnLand");
        setBreaching(compound.getBoolean("Breaching"));
        setBreachPitch(compound.getFloat("BreachPitch"));
        setDoingGrabAttack(compound.getBoolean("Grabbing"));
    }

    @Override
    protected @NotNull PathNavigation createNavigation(Level level) {
        return new WaterBoundPathNavigation(this, level);
    }

    protected void switchNavigator(boolean onLand) {
        //TODO: Properly implement all four classes
        if (onLand) {
            moveControl = new SmoothTurningMoveControl(this);
            navigation = new AmphibiousPathNavigation(this);
            lookControl = new LookControl(this);
            isLandNavigator = true;
        } else {
            moveControl = new SwimmingMoveControl(this);
            navigation = new LargeSwimmerPathNavigation(this);
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

    public abstract boolean isAmphibious();

    @Override
    public boolean canBreatheUnderwater() {
        return true;
    }

    public boolean canBreatheOnLand() {
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
    public void aiStep() {
        super.aiStep();
        if (canDoBreachAttack() && !level.isClientSide) {
            LivingEntity target = getTarget();
            if (target != null) {
                if (!isEntitySubmerged(target) && !isOverWater(target)) {
                    //If target out of reach
                    setTarget(null);
                }
            }
        }
        if (!level.isClientSide) {
            if (isInWater() && useSwimAI() && isLandNavigator) {
                switchNavigator(false);
            } else if (!isInWater() && isOnGround() && !useSwimAI() && !isLandNavigator) {
                switchNavigator(true);
            }
            if (isInWater() && (isSitting() || isSleeping())) {
                setSitting(false);
                setSleeping(false);
            }
            if (isInWater()) {
                timeInWater++;
                timeOnLand = 0;
            } else if (onGround) {
                timeInWater = 0;
                timeOnLand++;
            }

            for (Entity passenger : getPassengers()) {
                if (passenger instanceof LivingEntity && passenger != getRidingPlayer()) {
                    if (passenger instanceof ToyBase toy && level.getGameTime() == grabStartTick + 55) {
                        stopGrabAttack(passenger);
                        setTarget(null);
                        moodSystem.useToy(toy.moodBonus);
                    } else {
                        if (tickCount % 20 == 0) {
                            boolean hurt = passenger.hurt(DamageSource.mobAttack(this), (float) getAttributeValue(Attributes.ATTACK_DAMAGE));
                            if (!hurt || (level.getGameTime() >= grabStartTick + 55 && random.nextInt(5) == 0)) {
                                stopGrabAttack(passenger);
                            }
                        }
                    }
                }
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
    public void positionRider(Entity passenger) {
        super.positionRider(passenger);
        if (passenger != getRidingPlayer() && isDoingGrabAttack()) {
            if (level.isClientSide) {
                AnimationData data = getFactory().getOrCreateAnimationData(getId());
                Map<String, Pair<IBone, BoneSnapshot>> map = data.getBoneSnapshotCollection();
                if (map.get("grabPos") != null) {
                    if (map.get("grabPos").getLeft() instanceof GeoBone geoBone) {
                        Vector3d pos = geoBone.getLocalPosition();
                        passenger.setPos(getX() + pos.x, getY() + pos.y - 0.2 + passenger.getMyRidingOffset(), getZ() + pos.z);
                    }
                }
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

    public void startGrabAttack(LivingEntity target) {
        target.startRiding(this);
        grabStartTick = level.getGameTime();
        entityData.set(GRABBING, true);
    }

    public void stopGrabAttack(Entity target) {
        target.stopRiding();
        grabStartTick = -1;
        entityData.set(GRABBING, false);
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
        if (rider == null || !canBeControlledByRider() || !steering.trySteer(rider)) {
            if (isEffectiveAi() && isInWater()) {
                moveRelative(getSpeed(), travelVector);
                move(MoverType.SELF, getDeltaMovement());
                setDeltaMovement(getDeltaMovement().scale(0.9));
                if (getTarget() == null && level.getFluidState(blockPosition().below()).is(FluidTags.WATER)) {
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
            steering.waterTravel(new Vec3(newStrafeMovement, travelVector.y, newForwardMovement), (LocalPlayer) rider);
        } else {
            setDeltaMovement(Vec3.ZERO);
        }
    }

    @Override
    public boolean onClimbable() {
        return false;
    }

    public abstract float swimSpeed();

    private double getScaledSwimSpeed() {
        return getScale() / data().maxScale() * swimSpeed();
    }

    @Override
    public boolean canDinoHunt(LivingEntity target) {
        //TODO: Update
        if (canDoBreachAttack() && isOverWater(target)) {
            return super.canDinoHunt(target);
        }
        return super.canDinoHunt(target) && (target.isInWater() || canHuntMobsOnLand());
    }

    protected boolean canHuntMobsOnLand() {
        return true;
    }

    public boolean canDoBreachAttack() {
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
        return info().isVivariousAquatic() ? isInWater() && super.canBeControlledByRider() : super.canBeControlledByRider();
    }

    public boolean isEntitySubmerged(LivingEntity entity) {
        return level.getFluidState(entity.blockPosition().above()).is(FluidTags.WATER);
    }

    public boolean isBeached() {
        return beached;
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

    public boolean isDoingGrabAttack() {
        return entityData.get(GRABBING);
    }

    public void setDoingGrabAttack(boolean grabbing) {
        entityData.set(GRABBING, grabbing);
    }

    public abstract @NotNull Animation nextBeachedAnimation();

    public @NotNull Animation nextGrabbingAnimation() {
        return nextIdleAnimation();
    }

    @Override
    public void registerControllers(AnimationData data) {
        data.addAnimationController(new AnimationController<>(this, "Movement/Idle/Eat", 0, getAnimationLogic()::waterPredicate));
        data.addAnimationController(new AnimationController<>(this, "Attack", 5, event -> {
            AnimationController<PrehistoricSwimming> controller = event.getController();
            AnimationLogic.ActiveAnimationInfo activeAnimation = getAnimationLogic().getActiveAnimation(controller.getName());
            if (swinging) {
                if (activeAnimation == null || !activeAnimation.category().equals("Attack")) {
                    getAnimationLogic().addActiveAnimation(controller.getName(), nextAttackAnimation(), "Attack");
                }
            } else if (isDoingGrabAttack()) {
                getAnimationLogic().addActiveAnimation(controller.getName(), nextGrabbingAnimation(), "Grab");
            }
            AnimationLogic.ActiveAnimationInfo newAnimation = getAnimationLogic().getActiveAnimation(controller.getName());
            if (newAnimation != null) {
                controller.setAnimation(new AnimationBuilder().addAnimation(newAnimation.animationName()));
                return PlayState.CONTINUE;
            } else {
                event.getController().markNeedsReload();
                return PlayState.STOP;
            }
        }));
    }

    static class LargeSwimmerPathNavigation extends WaterBoundPathNavigation {

        public LargeSwimmerPathNavigation(PrehistoricSwimming mob) {
            super(mob, mob.level);
        }

        @Override
        protected @NotNull PathFinder createPathFinder(int maxVisitedNodes) {
            nodeEvaluator = new SwimNodeEvaluator(true);
            return new PathFinder(nodeEvaluator, maxVisitedNodes);
        }
    }

    @Override
    public int getMaxHeadXRot() {
        return 1;//TODO: Check if needed
    }

    @Override
    public int getMaxHeadYRot() {
        return 1;
    }

    static class SwimmingMoveControl extends SmoothSwimmingMoveControl {

        private final PrehistoricSwimming mob;

        public SwimmingMoveControl(PrehistoricSwimming mob) {
            super(mob, 85, 10, 0.1f, 0.1f, true);
            this.mob = mob;
        }

        @Override
        public void tick() {
            if (mob.isBreaching()) {
                double x = wantedX - mob.getX();
                double y = wantedY - mob.getY();
                double z = wantedZ - mob.getZ();
                double dist = x * x + y * y + z * z;
                if (dist < 2.5) {
                    operation = Operation.WAIT;//TODO: Same logic needs to be called by the goal
                    mob.setBreaching(false);
                    mob.setDeltaMovement(mob.getDeltaMovement().x * 2, mob.getDeltaMovement().y, mob.getDeltaMovement().z * 2);
                    mob.breachTargetReached = true;
                } else {
                    Vec3 current = mob.getDeltaMovement();
                    double movementX = (Mth.sign(x) * 0.5 - current.x) * 0.1 * 2;
                    double movementY = (Mth.sign(y) * 0.5 - current.y) * 0.1 * 3;//TODO: figure out best values
                    double movementZ = (Mth.sign(z) * 0.5 - current.z) * 0.1 * 2;
                    Vec3 next = current.add(movementX, movementY, movementZ);
                    mob.setDeltaMovement(next);
                    float angle = (float) (Mth.atan2(next.z, next.x) * Mth.RAD_TO_DEG) - 90;
                    float rot = Mth.wrapDegrees(angle - mob.getYRot());
                    mob.setYRot(mob.getYRot() + rot);

                    float k = (float) (-Mth.atan2(next.y, next.horizontalDistance()) * Mth.RAD_TO_DEG);
                    mob.setXRot(k);

                    mob.setXxa(0);
                    mob.setYya(0);
                    mob.setZza(0);
                }
            } else if (operation == Operation.MOVE_TO && !mob.getNavigation().isDone()) {
                mob.setDeltaMovement(this.mob.getDeltaMovement().add(0.0, 0.005, 0.0));
                double x = wantedX - mob.getX();
                double y = wantedY - mob.getY();
                double z = wantedZ - mob.getZ();
                double dist = x * x + y * y + z * z;
                if (dist < 2.500000277905201E-7) {
                    mob.setZza(0);
                } else {
                    float h = floatMod((float) ((Mth.atan2(z, x) * Mth.RAD_TO_DEG) - 90), 360);
                    float g = rotlerp(floatMod(mob.getYRot(), 360), h, 10);
                    mob.setYRot(g);
                    mob.yBodyRot = mob.getYRot();
                    mob.yHeadRot = mob.getYRot();
                    float i = (float) (speedModifier * mob.getAttributeValue(Attributes.MOVEMENT_SPEED));
                    if (mob.isInWater()) {
                        mob.setSpeed(i * 0.1f);
                        double horDist = Math.sqrt(x * x + z * z);
                        float k;
                        if (Math.abs(y) > 9.999999747378752E-6 || Math.abs(horDist) > 9.999999747378752E-6) {
                            k = (float) (-Mth.atan2(y, horDist) * Mth.RAD_TO_DEG) + 90;
                            k = Mth.clamp(k, 30, 150);
                            g = rotlerp(mob.getXRot() + 90, k, 5);
                            mob.setXRot(g - 90);
                        }
                        k = Mth.cos(mob.getXRot() * Mth.DEG_TO_RAD);
                        float l = Mth.sin(mob.getXRot() * Mth.DEG_TO_RAD);
                        mob.zza = k * i;
                        mob.yya = -l * i;
                    } else {
                        mob.setSpeed(i * 0.2f);//TODO: Out of water
                    }
                }
            } else {
                mob.setSpeed(0);
                mob.setXxa(0);
                mob.setYya(0);
                mob.setZza(0);
            }
        }

        private float floatMod(float x, float y) {
            //x mod y behaving the same way as Math.floorMod but with doubles
            return (float) (x - Math.floor(x / y) * y);
        }
    }
}
