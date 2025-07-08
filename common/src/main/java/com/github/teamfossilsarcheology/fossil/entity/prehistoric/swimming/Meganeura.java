package com.github.teamfossilsarcheology.fossil.entity.prehistoric.swimming;

import com.github.teamfossilsarcheology.fossil.entity.ai.*;
import com.github.teamfossilsarcheology.fossil.entity.ai.control.CustomSwimMoveControl;
import com.github.teamfossilsarcheology.fossil.entity.ai.control.MeganeuraFlyingMoveControl;
import com.github.teamfossilsarcheology.fossil.entity.ai.control.SmoothTurningMoveControl;
import com.github.teamfossilsarcheology.fossil.entity.ai.navigation.AmphibiousPathNavigation;
import com.github.teamfossilsarcheology.fossil.entity.animation.AnimationCategory;
import com.github.teamfossilsarcheology.fossil.entity.animation.AnimationLogic;
import com.github.teamfossilsarcheology.fossil.entity.animation.PausableAnimationController;
import com.github.teamfossilsarcheology.fossil.entity.prehistoric.base.Prehistoric;
import com.github.teamfossilsarcheology.fossil.entity.prehistoric.base.PrehistoricEntityInfo;
import com.github.teamfossilsarcheology.fossil.entity.prehistoric.base.PrehistoricEntityInfoAI;
import com.github.teamfossilsarcheology.fossil.entity.prehistoric.base.SwimmingAnimal;
import com.github.teamfossilsarcheology.fossil.entity.prehistoric.system.MeganeuraAttachSystem;
import com.github.teamfossilsarcheology.fossil.entity.prehistoric.system.SleepSystem;
import com.github.teamfossilsarcheology.fossil.entity.util.Util;
import com.github.teamfossilsarcheology.fossil.sounds.ModSounds;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.ai.control.LookControl;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.entity.ai.control.SmoothSwimmingLookControl;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.navigation.FlyingPathNavigation;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.ai.util.AirAndWaterRandomPos;
import net.minecraft.world.entity.ai.util.HoverRandomPos;
import net.minecraft.world.entity.animal.FlyingAnimal;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.Animation;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.core.object.PlayState;

import java.util.Optional;

import static software.bernie.geckolib.core.animation.Animation.LoopType.LOOP;
import static software.bernie.geckolib.core.animation.Animation.LoopType.PLAY_ONCE;


public class Meganeura extends Prehistoric implements FlyingAnimal, SwimmingAnimal {
    public static final String ATTACK = "animation.meganeura.attack";
    public static final String IDLE = "animation.meganeura.idle";
    public static final String WALK = "animation.meganeura.walk";
    public static final EntityDataAccessor<Float> ATTACHED_X = SynchedEntityData.defineId(Meganeura.class, EntityDataSerializers.FLOAT);
    public static final EntityDataAccessor<Float> ATTACHED_Y = SynchedEntityData.defineId(Meganeura.class, EntityDataSerializers.FLOAT);
    public static final EntityDataAccessor<Float> ATTACHED_Z = SynchedEntityData.defineId(Meganeura.class, EntityDataSerializers.FLOAT);
    public static final EntityDataAccessor<Direction> ATTACHED_FACE = SynchedEntityData.defineId(Meganeura.class, EntityDataSerializers.DIRECTION);
    public static final EntityDataAccessor<Boolean> ATTACHED = SynchedEntityData.defineId(Meganeura.class, EntityDataSerializers.BOOLEAN);
    private final MeganeuraAttachSystem attachSystem = registerSystem(new MeganeuraAttachSystem(this));
    private final PathNavigation flightNav = new FlyingPathNavigation(this, level());
    private final PathNavigation amphibiousNav = new AmphibiousPathNavigation<>(this, level());
    private final MoveControl flightMoveControl = new MeganeuraFlyingMoveControl(this);
    private final MoveControl aquaticMoveControl = new CustomSwimMoveControl<>(this);
    private final MoveControl landMoveControl = new SmoothTurningMoveControl(this);
    protected boolean isLandNavigator;
    protected boolean isAirNavigator;
    private int timeInWater = 0;
    private int timeOnLand = 0;
    private int dimensionSwitchCooldown = -1;

    public Meganeura(EntityType<Meganeura> entityType, Level level) {
        super(entityType, level);
    }

    @Override
    protected void registerGoals() {
        matingGoal = new DinoMatingGoal(this, 1);
        goalSelector.addGoal(Util.IMMOBILE, new DinoPanicGoal(this, 1.5));
        goalSelector.addGoal(Util.SLEEP + 2, matingGoal);
        goalSelector.addGoal(Util.NEEDS, new EatFromFeederGoal(this));
        goalSelector.addGoal(Util.NEEDS + 1, new EatItemEntityGoal(this));
        goalSelector.addGoal(Util.NEEDS + 2, new WaterPlayGoal<>(this, 1));
        goalSelector.addGoal(Util.WANDER, new DinoFollowOwnerGoal(this, attributes().sprintMod(), 5, 2, false));
        goalSelector.addGoal(Util.WANDER + 1, new EnterWaterGoal<>(this, 1));
        goalSelector.addGoal(Util.WANDER + 2, new MeganeuraWanderAndAttachGoal(this));
        goalSelector.addGoal(Util.WANDER + 3, new DinoRandomSwimGoal<>(this, 1));
        goalSelector.addGoal(Util.LOOK, new RandomLookAroundGoal(this));
        targetSelector.addGoal(1, new DinoOwnerHurtByTargetGoal(this));
        targetSelector.addGoal(2, new DinoOwnerHurtTargetGoal(this));
        targetSelector.addGoal(3, new DinoHurtByTargetGoal(this));
        targetSelector.addGoal(5, new HuntingTargetGoal(this));
    }

    @Override
    protected void updateControlFlags() {
        boolean bl = !isSleeping() && !attachSystem.isAttached();
        goalSelector.setControlFlag(Goal.Flag.MOVE, bl && !sitSystem.isSitting());
        goalSelector.setControlFlag(Goal.Flag.JUMP, bl && !sitSystem.isSitting());
        goalSelector.setControlFlag(Goal.Flag.LOOK, bl);
    }

    @Override
    public void defineSynchedData() {
        super.defineSynchedData();
        entityData.define(ATTACHED_X, 0f);
        entityData.define(ATTACHED_Y, 0f);
        entityData.define(ATTACHED_Z, 0f);
        entityData.define(ATTACHED_FACE, Direction.UP);
        entityData.define(ATTACHED, false);
    }

    @Override
    public void onSyncedDataUpdated(EntityDataAccessor<?> key) {
        if (ATTACHED_FACE.equals(key)) {
            if (attachSystem.getAttachmentFace() == Direction.UP) {
                dimensionSwitchCooldown = 20;
            } else {
                refreshDimensions();
            }
        }
        super.onSyncedDataUpdated(key);
    }

    @Override
    protected @NotNull SleepSystem createSleepSystem() {
        return new MeganeuraSleepSystem(this);
    }

    @Override
    protected boolean canAgeUpNaturally() {
        if (isInWater() && isBaby() && (getAge() + 1) / 24000 >= data().teenAgeDays()) {
            //Forbid leaving larvae stage if in water to prevent drowning
            return false;
        }
        return super.canAgeUpNaturally();
    }

    @Override
    public boolean hurt(DamageSource source, float amount) {
        if (source == damageSources().inWall()) {
            return false;
        }
        boolean hurt = super.hurt(source, amount);
        if (hurt) {
            attachSystem.stopAttaching(1000 + random.nextInt(1500));
        }
        return hurt;
    }

    @Override
    public @NotNull EntityDimensions getDimensions(Pose poseIn) {
        if (usesAttachHitBox()) {
            return super.getDimensions(poseIn).scale(1, 2);
        }
        return super.getDimensions(poseIn);
    }

    @Override
    public boolean shouldRenderAtSqrDistance(double distance) {
        double d = getBbWidth();
        if (Double.isNaN(d)) {
            d = 1.0;
        }

        d *= 64.0 * getViewScale();
        return distance < d * d;
    }

    @Override
    protected void customServerAiStep() {
        switchNavigator();
        super.customServerAiStep();
        if (isInWater()) {
            timeInWater++;
            timeOnLand = 0;
        } else if (onGround()) {
            timeInWater = 0;
            timeOnLand++;
        }
    }

    @Override
    public void aiStep() {
        super.aiStep();
        if (dimensionSwitchCooldown > 0) {
            dimensionSwitchCooldown--;
            if (dimensionSwitchCooldown == 0) {
                refreshDimensions();
                dimensionSwitchCooldown = -1;
            }
        }
    }

    @Override
    protected void checkFallDamage(double y, boolean onGround, BlockState state, BlockPos pos) {

    }

    @Override
    public boolean isPushable() {
        return super.isPushable() && !attachSystem.isAttached();
    }

    public boolean isFlying() {
        return !onGround() && !attachSystem.isAttached();
    }

    private void switchNavigator() {
        //TODO: Rework + clean up
        if (isLandNavigator) {
            if (isBaby()) {
                if (isInWater()) {
                    //Baby start swimming
                    moveControl = aquaticMoveControl;
                    lookControl = new SmoothSwimmingLookControl(this, 20);
                    navigation = amphibiousNav;
                    isLandNavigator = false;
                }
            } else {
                moveControl = flightMoveControl;
                lookControl = new LookControl(this);
                navigation = flightNav;
                isLandNavigator = false;
                isAirNavigator = true;
            }
        } else if (isAirNavigator) {
            if (isBaby()) {
                //Just needed for debug purposes
                moveControl = landMoveControl;
                lookControl = new LookControl(this);
                navigation = amphibiousNav;
                isLandNavigator = true;
                isAirNavigator = false;
            }
        } else {//isWaterNavigator
            if (isBaby()) {
                if (!isInWater() && onGround()) {
                    //Baby start walking
                    moveControl = landMoveControl;
                    lookControl = new LookControl(this);
                    navigation = amphibiousNav;
                    isLandNavigator = true;
                }
            } else {
                moveControl = flightMoveControl;
                lookControl = new LookControl(this);
                navigation = flightNav;
                isLandNavigator = false;
                isAirNavigator = true;
            }
        }
    }

    public MeganeuraAttachSystem getAttachSystem() {
        return attachSystem;
    }

    public boolean usesAttachHitBox() {
        return attachSystem == null || attachSystem.getAttachmentFace().getAxis().isHorizontal();
    }

    @Override
    public int timeInWater() {
        return timeInWater;
    }

    @Override
    public int timeOnLand() {
        return timeOnLand;
    }

    public boolean isPushedByFluid() {
        return !isBaby();
    }

    @Override
    public boolean canBreatheUnderwater() {
        return isBaby();
    }

    @Override
    public boolean isAmphibious() {
        return isBaby();
    }

    @Override
    public boolean canSwim() {
        return isBaby();
    }

    @Override
    public double swimSpeed() {
        return 1;
    }

    @Override
    public PrehistoricEntityInfo info() {
        return PrehistoricEntityInfo.MEGANEURA;
    }

    @Override
    public Item getOrderItem() {
        return Items.ARROW;
    }

    @Override
    public PrehistoricEntityInfoAI.Moving aiMovingType() {
        return isBaby() ? PrehistoricEntityInfoAI.Moving.SEMI_AQUATIC : PrehistoricEntityInfoAI.Moving.FLIGHT;
    }

    @Override
    public @NotNull MobType getMobType() {
        return MobType.ARTHROPOD;
    }

    @Nullable
    @Override
    protected SoundEvent getAmbientSound() {
        return ModSounds.MEGANEURA_AMBIENT.get();
    }

    @Nullable
    @Override
    protected SoundEvent getHurtSound(DamageSource damageSource) {
        return ModSounds.MEGANEURA_HURT.get();
    }

    @Nullable
    @Override
    protected SoundEvent getDeathSound() {
        return ModSounds.MEGANEURA_DEATH.get();
    }

    @Override
    protected float getSoundVolume() {
        return super.getSoundVolume() * 0.5f;
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllerRegistrar) {
        AnimationLogic<Prehistoric> animationLogic = getAnimationLogic();
        var ctrl = new PausableAnimationController<>(this, AnimationLogic.IDLE_CTRL, 5, state -> {
            AnimationController<Meganeura> controller = state.getController();
            if (animationLogic.tryNextAnimation(state, controller)) {
                return PlayState.CONTINUE;
            }
            Optional<AnimationLogic.ActiveAnimationInfo> activeAnimation = animationLogic.getActiveAnimation(controller.getName());
            Animation.LoopType loopType = null;
            if (activeAnimation.isPresent() && activeAnimation.get().forced() && !animationLogic.isAnimationDone(controller.getName())) {
                loopType = activeAnimation.get().loop() ? LOOP : PLAY_ONCE;
            } else {
                if (isSleeping()) {
                    animationLogic.addActiveAnimation(controller.getName(), AnimationCategory.SLEEP);
                } else if (sitSystem.isSitting()) {
                    animationLogic.addActiveAnimation(controller.getName(), AnimationCategory.SIT);
                } else if (state.isMoving()) {
                    if (isBaby()) {
                        if (isInWater()) {
                            animationLogic.addActiveAnimation(controller.getName(), AnimationCategory.SWIM);
                        }
                    } else {
                        if (isInWater()) {
                            animationLogic.addActiveAnimation(controller.getName(), AnimationCategory.IDLE);//Drown
                        } else if (isFlying()) {
                            animationLogic.addActiveAnimation(controller.getName(), AnimationCategory.FLY);
                        }
                    }
                } else {
                    if (isFlying()) {
                        animationLogic.addActiveAnimation(controller.getName(), AnimationCategory.FLY);
                    } else {
                        animationLogic.addActiveAnimation(controller.getName(), AnimationCategory.IDLE);
                    }
                }
            }
            Optional<AnimationLogic.ActiveAnimationInfo> newAnimation = animationLogic.getActiveAnimation(controller.getName());
            if (newAnimation.isPresent()) {
                if (loopType == null) {
                    loopType = newAnimation.get().loop() ? LOOP : PLAY_ONCE;
                }
                controller.setAnimation(RawAnimation.begin().then(newAnimation.get().animationName(), loopType));
            }
            return PlayState.CONTINUE;
        });
        registerEatingListeners(ctrl);
        controllerRegistrar.add(ctrl);
        controllerRegistrar.add(new PausableAnimationController<>(
                this, AnimationLogic.ATTACK_CTRL, 0, getAnimationLogic()::attackPredicate));
    }

    class MeganeuraSleepSystem extends SleepSystem {

        public MeganeuraSleepSystem(Meganeura mob) {
            super(mob);
        }

        @Override
        protected boolean canSleep() {
            if (!super.canSleep()) {
                return false;
            }
            if (!isBaby() && !attachSystem.isAttached()) {
                if (!attachSystem.attachStarted()) attachSystem.setAttachCooldown(0);
                return false;
            }
            return true;
        }
    }

    static class MeganeuraWanderAndAttachGoal extends DinoWanderGoal {
        private final Meganeura meganeura;

        public MeganeuraWanderAndAttachGoal(Meganeura meganeura) {
            super(meganeura, 1, 10);
            this.meganeura = meganeura;
        }

        @Override
        public boolean canUse() {
            if (meganeura.isBaby() || meganeura.attachSystem.attachStarted()) {
                return false;
            }
            return super.canUse();
        }

        @Override
        protected @Nullable Vec3 getPosition() {
            if (meganeura.attachSystem.getAttachCooldown() == 0) {
                RandomSource random = meganeura.random;
                Level level = mob.level();
                for (int i = 0; i < 5; i++) {
                    BlockPos blockPos = mob.blockPosition().offset(random.nextInt(16) - 8, random.nextInt(10) - 2, random.nextInt(16) - 8);
                    BlockHitResult hitResult = level.clip(new ClipContext(mob.getEyePosition(), Vec3.atCenterOf(blockPos), ClipContext.Block.COLLIDER, ClipContext.Fluid.WATER, mob));
                    if (hitResult.getType() == HitResult.Type.MISS || hitResult.getDirection().getAxis().isVertical()) {
                        continue;
                    }
                    if (!level.getBlockState(hitResult.getBlockPos()).isFaceSturdy(level, hitResult.getBlockPos(), hitResult.getDirection())) {
                        continue;
                    }
                    meganeura.attachSystem.setAttachTarget(hitResult.getBlockPos().immutable(), hitResult.getDirection());
                    return Vec3.atCenterOf(hitResult.getBlockPos());
                }
            }
            Vec3 view = mob.getViewVector(0.0f);
            Vec3 pos = HoverRandomPos.getPos(mob, 8, 7, view.x, view.z, Mth.HALF_PI, 3, 1);
            if (pos != null) {
                return pos;
            }
            return AirAndWaterRandomPos.getPos(mob, 8, 4, -2, view.x, view.z, Mth.HALF_PI);

        }
    }
}
