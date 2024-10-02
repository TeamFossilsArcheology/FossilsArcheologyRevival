package com.fossil.fossil.entity.prehistoric.swimming;

import com.fossil.fossil.entity.ai.*;
import com.fossil.fossil.entity.ai.control.CustomSwimMoveControl;
import com.fossil.fossil.entity.ai.control.MeganeuraFlyingMoveControl;
import com.fossil.fossil.entity.ai.control.SmoothTurningMoveControl;
import com.fossil.fossil.entity.ai.navigation.AmphibiousPathNavigation;
import com.fossil.fossil.entity.prehistoric.base.PrehistoricEntityInfo;
import com.fossil.fossil.entity.prehistoric.base.PrehistoricEntityInfoAI;
import com.fossil.fossil.entity.prehistoric.base.PrehistoricSwimming;
import com.fossil.fossil.entity.prehistoric.system.MeganeuraAttachSystem;
import com.fossil.fossil.entity.prehistoric.system.SleepSystem;
import com.fossil.fossil.entity.util.Util;
import com.fossil.fossil.sounds.ModSounds;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.Mth;
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
import software.bernie.geckolib3.core.builder.Animation;
import software.bernie.geckolib3.core.manager.AnimationFactory;
import software.bernie.geckolib3.util.GeckoLibUtil;

import java.util.Random;


public class Meganeura extends PrehistoricSwimming implements FlyingAnimal {
    public static final String ANIMATIONS = "meganeura.animation.json";
    public static final String ATTACK = "animation.meganeura.attack";
    public static final String ATTACK_BABY = "animation.meganeura.attack_baby";
    public static final String DROWN = "animation.meganeura.drown";
    public static final String EAT = "animation.meganeura.eat";
    public static final String EAT_BABY = "animation.meganeura.eat_baby";
    public static final String FLY = "animation.meganeura.fly";
    public static final String IDLE = "animation.meganeura.idle";
    public static final String IDLE_BABY = "animation.meganeura.idle_baby";
    public static final String HOVER = "animation.meganeura.hover";
    public static final String SIT = "animation.meganeura.sit";
    public static final String SIT_BABY = "animation.meganeura.sit_baby";
    public static final String SWIM_BABY = "animation.meganeura.swim_baby";
    public static final String SLEEP = "animation.meganeura.sleep";
    public static final String SLEEP_BABY = "animation.meganeura.sleep_baby";
    public static final String WALK = "animation.meganeura.walk";
    public static final String WALK_BABY = "animation.meganeura.walk_baby";
    public static final EntityDataAccessor<Float> ATTACHED_X = SynchedEntityData.defineId(Meganeura.class, EntityDataSerializers.FLOAT);
    public static final EntityDataAccessor<Float> ATTACHED_Y = SynchedEntityData.defineId(Meganeura.class, EntityDataSerializers.FLOAT);
    public static final EntityDataAccessor<Float> ATTACHED_Z = SynchedEntityData.defineId(Meganeura.class, EntityDataSerializers.FLOAT);
    public static final EntityDataAccessor<Direction> ATTACHED_FACE = SynchedEntityData.defineId(Meganeura.class, EntityDataSerializers.DIRECTION);
    public static final EntityDataAccessor<Boolean> ATTACHED = SynchedEntityData.defineId(Meganeura.class, EntityDataSerializers.BOOLEAN);
    private final AnimationFactory factory = GeckoLibUtil.createFactory(this);
    private final MeganeuraAttachSystem attachSystem = registerSystem(new MeganeuraAttachSystem(this));
    private final PathNavigation flightNav = new FlyingPathNavigation(this, level);
    private final PathNavigation amphibiousNav = new AmphibiousPathNavigation(this, level);
    private final MoveControl flightMoveControl = new MeganeuraFlyingMoveControl(this);
    private final MoveControl aquaticMoveControl = new CustomSwimMoveControl(this);
    private final MoveControl landMoveControl = new SmoothTurningMoveControl(this);
    protected boolean isAirNavigator;

    public Meganeura(EntityType<Meganeura> entityType, Level level) {
        super(entityType, level);
    }

    @Override
    protected void registerGoals() {
        matingGoal = new DinoMatingGoal(this, 1);
        goalSelector.addGoal(Util.IMMOBILE, new DinoPanicGoal(this, 1.5));
        goalSelector.addGoal(Util.ATTACK, new DelayedAttackGoal(this, 1, false));
        goalSelector.addGoal(Util.SLEEP + 2, matingGoal);
        goalSelector.addGoal(Util.NEEDS, new EatFromFeederGoal(this));
        goalSelector.addGoal(Util.NEEDS + 1, new EatItemEntityGoal(this));
        goalSelector.addGoal(Util.NEEDS + 2, new WaterPlayGoal(this, 1));
        goalSelector.addGoal(Util.WANDER, new DinoFollowOwnerGoal(this, 1, 5, 2, false));
        goalSelector.addGoal(Util.WANDER + 1, new EnterWaterGoal(this, 1));
        goalSelector.addGoal(Util.WANDER + 2, new MeganeuraWanderAndAttachGoal(this));
        goalSelector.addGoal(Util.WANDER + 3, new DinoRandomSwimGoal(this, 1));
        goalSelector.addGoal(Util.LOOK, new RandomLookAroundGoal(this));
        targetSelector.addGoal(1, new DinoOwnerHurtByTargetGoal(this));
        targetSelector.addGoal(2, new DinoOwnerHurtTargetGoal(this));
        targetSelector.addGoal(3, new DinoHurtByTargetGoal(this));
        targetSelector.addGoal(5, new HuntingTargetGoal(this));
    }

    @Override
    protected void updateControlFlags() {
        super.updateControlFlags();
        boolean bl = !attachSystem.isAttached();
        goalSelector.setControlFlag(Goal.Flag.MOVE, bl);
        goalSelector.setControlFlag(Goal.Flag.JUMP, bl);
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
            refreshDimensions();
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
        if (source == DamageSource.IN_WALL) {
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
            return super.getDimensions(poseIn).scale(getAttachHitBoxScale(), 2f);
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
    }

    @Override
    public void absMoveTo(double x, double y, double z, float yRot, float xRot) {
        if (Thread.currentThread().getStackTrace()[2].getClassName().contains("SpawnEntityPacket")) {
            //TODO: Bug in Architectury. Is removed in 1.19
            super.absMoveTo(x, y, z, xRot, yRot);
        } else {
            super.absMoveTo(x, y, z, yRot, xRot);
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
        return !isOnGround() && !attachSystem.isAttached();
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
                if (!isInWater() && isOnGround()) {
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

    @Override
    protected void switchNavigator(boolean onLand) {
    }

    public MeganeuraAttachSystem getAttachSystem() {
        return attachSystem;
    }

    public boolean usesAttachHitBox() {
        return attachSystem == null || attachSystem.getAttachmentFace().getAxis().isHorizontal();
    }

    public float getAttachHitBoxScale() {
        return 0.5f;
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

    @Override
    public @NotNull Animation nextBeachedAnimation() {
        return nextIdleAnimation();
    }

    @Override
    public @NotNull Animation nextEatingAnimation() {
        return getAllAnimations().get(isBaby() ? EAT_BABY : EAT);
    }

    @Override
    public @NotNull Animation nextIdleAnimation() {
        if (isBaby()) {
            return getAllAnimations().get(IDLE_BABY);
        }
        if (isInWater()) {
            return getAllAnimations().get(DROWN);
        }
        if (isFlying()) {
            return getAllAnimations().get(HOVER);
        }
        return getAllAnimations().get(IDLE);
    }

    @Override
    public @NotNull Animation nextSittingAnimation() {
        return getAllAnimations().get(isBaby() ? SIT_BABY : SIT);
    }

    @Override
    public @NotNull Animation nextSleepingAnimation() {
        return getAllAnimations().get(isBaby() ? SLEEP_BABY : SLEEP);
    }

    @Override
    public @NotNull Animation nextMovingAnimation() {
        if (isBaby()) {
            if (isInWater()) {
                return getAllAnimations().get(SWIM_BABY);
            }
            return getAllAnimations().get(WALK_BABY);
        }
        if (isFlying()) {
            return getAllAnimations().get(FLY);
        }
        return getAllAnimations().get(WALK);
    }

    @Override
    public @NotNull Animation nextSprintingAnimation() {
        return nextMovingAnimation();
    }

    @Override
    public @NotNull Animation nextAttackAnimation() {
        return getAllAnimations().get(isBaby() ? ATTACK_BABY : ATTACK);
    }

    @Override
    public AnimationFactory getFactory() {
        return factory;
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

    class MeganeuraSleepSystem extends SleepSystem {

        public MeganeuraSleepSystem(PrehistoricSwimming mob) {
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
                Random random = meganeura.random;
                Level level = mob.level;
                for (int i = 0; i < 15; i++) {
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
