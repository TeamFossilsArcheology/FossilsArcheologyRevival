package com.github.teamfossilsarcheology.fossil.entity.prehistoric.base;

import com.github.teamfossilsarcheology.fossil.entity.ai.control.PrehistoricLookControl;
import com.github.teamfossilsarcheology.fossil.entity.ai.control.SmoothTurningMoveControl;
import com.github.teamfossilsarcheology.fossil.entity.animation.AnimationInfo;
import com.github.teamfossilsarcheology.fossil.entity.animation.AnimationLogic;
import com.github.teamfossilsarcheology.fossil.entity.animation.PausableAnimationController;
import com.github.teamfossilsarcheology.fossil.entity.prehistoric.system.LeapSystem;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import software.bernie.geckolib.core.animation.AnimatableManager;

import java.util.Objects;

public abstract class PrehistoricLeaping extends Prehistoric {
    public static final EntityDataAccessor<Boolean> LEAPING = SynchedEntityData.defineId(PrehistoricLeaping.class, EntityDataSerializers.BOOLEAN);
    public static final EntityDataAccessor<Boolean> LEAP_STARTED = SynchedEntityData.defineId(PrehistoricLeaping.class, EntityDataSerializers.BOOLEAN);
    public static final EntityDataAccessor<Boolean> LEAP_FLYING = SynchedEntityData.defineId(PrehistoricLeaping.class, EntityDataSerializers.BOOLEAN);
    public static final EntityDataAccessor<Integer> LEAP_TARGET_ID = SynchedEntityData.defineId(PrehistoricLeaping.class, EntityDataSerializers.INT);
    public static final EntityDataAccessor<Boolean> LEAP_RIDING = SynchedEntityData.defineId(PrehistoricLeaping.class, EntityDataSerializers.BOOLEAN);
    public static final EntityDataAccessor<Boolean> LEAP_LANDING = SynchedEntityData.defineId(PrehistoricLeaping.class, EntityDataSerializers.BOOLEAN);

    private static final EntityDataAccessor<Boolean> LANDING = SynchedEntityData.defineId(PrehistoricLeaping.class, EntityDataSerializers.BOOLEAN);
    private final LeapSystem leapSystem = registerSystem(new LeapSystem(this));

    protected PrehistoricLeaping(EntityType<? extends PrehistoricLeaping> entityType, Level level, boolean canAttach) {
        super(entityType, level);
        moveControl = new LeapMoveControl();
        lookControl = new LeapLookControl();
    }

    @Override
    protected void updateControlFlags() {
        boolean bl = !leapSystem.isLeaping() && !isSleeping();
        goalSelector.setControlFlag(Goal.Flag.MOVE, bl && !sitSystem.isSitting());
        goalSelector.setControlFlag(Goal.Flag.JUMP, bl && !sitSystem.isSitting());
        goalSelector.setControlFlag(Goal.Flag.LOOK, bl);
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        entityData.define(LEAPING, false);
        entityData.define(LEAP_STARTED, false);
        entityData.define(LEAP_RIDING, false);
        entityData.define(LEAP_FLYING, false);
        entityData.define(LEAP_LANDING, false);
        entityData.define(LEAP_TARGET_ID, -1);
        entityData.define(LANDING, false);
    }

    @Override
    public void onSyncedDataUpdated(EntityDataAccessor<?> key) {
        if (LEAP_RIDING.equals(key)) {
            if (entityData.get(LEAP_RIDING) && entityData.get(LEAP_TARGET_ID) >= 0) {
                startRiding(Objects.requireNonNull(level().getEntity(entityData.get(LEAP_TARGET_ID))), true);
            } else {
                stopRiding();
            }
        }
        super.onSyncedDataUpdated(key);
    }

    @Override
    public double getMyRidingOffset() {
        return -0.025;
    }

    @Override
    public boolean hurt(DamageSource source, float amount) {
        boolean hurt = super.hurt(source, amount);
        if (hurt && leapSystem.isAttackRiding() && getLastHurtByMob() != null && getLastHurtByMob() == getVehicle()) {
            if (random.nextInt(2) == 0) {
                getLeapSystem().stopAttackRiding();
                stopRiding();
            }
        }
        return hurt;
    }

    @Override
    protected boolean canClimb() {
        return !getLeapSystem().isLeaping() && super.canClimb();
    }

    public LeapSystem getLeapSystem() {
        return leapSystem;
    }

    public void startLeap(Entity target) {
        leapSystem.setLeapStarted(true);
        entityData.set(LEAP_TARGET_ID, target.getId());
    }

    @Override
    public void push(Entity entity) {
        super.push(entity);
        if (getLeapSystem().isLeapingAt(entity)) {
            getLeapSystem().tryAttackRiding(entity);
        }
    }

    public @NotNull AnimationInfo getLeapStartAnimation() {
        return getAllAnimations().get(getLeapStartAnimationName());
    }

    public @NotNull AnimationInfo getLandAnimation() {
        return getAllAnimations().get(getLandAnimationName());
    }

    public @NotNull AnimationInfo getLeapAttackAnimation() {
        return getAllAnimations().get(getLeapAttackAnimationName());
    }

    public abstract String getLandAnimationName();

    public abstract String getLeapStartAnimationName();

    public abstract String getLeapAttackAnimationName();

    public boolean hasLeapAnimation() {
        return true;
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllerRegistrar) {
        var controller = new PausableAnimationController<>(this, AnimationLogic.IDLE_CTRL, 5, getAnimationLogic()::leapingPredicate);
        registerEatingListeners(controller, effect -> {
            if ("land".equals(effect) && onGround()) {
                BlockState below = level().getBlockState(BlockPos.containing(getX(), getY() - 0.2, getZ()));
                if (below.getRenderShape() != RenderShape.INVISIBLE) {
                    Vec3 vec3 = getDeltaMovement();
                    level().addParticle(new BlockParticleOption(ParticleTypes.BLOCK, below),
                            getX() + (random.nextDouble() - 0.5) * (double) getBbWidth() / 2,
                            getY(),
                            getZ() + (random.nextDouble() - 0.5) * (double) getBbWidth() / 2,
                            vec3.x * 4, 1, vec3.z * 4
                    );
                }
            }
        });
        registerControllerWithTriggers(controllerRegistrar, controller);

        var attackController = new PausableAnimationController<>(this, AnimationLogic.ATTACK_CTRL, 5, getAnimationLogic()::attackPredicate);
        registerControllerWithTriggers(controllerRegistrar, attackController);
    }

    class LeapMoveControl extends SmoothTurningMoveControl {

        public LeapMoveControl() {
            super(PrehistoricLeaping.this);
        }

        @Override
        public void tick() {
            if (!getLeapSystem().isLeaping()) {
                super.tick();
            }
        }
    }

    class LeapLookControl extends PrehistoricLookControl {

        public LeapLookControl() {
            super(PrehistoricLeaping.this);
        }

        @Override
        public void tick() {
            if (!getLeapSystem().isLeaping()) {
                super.tick();
            }
        }
    }
}
