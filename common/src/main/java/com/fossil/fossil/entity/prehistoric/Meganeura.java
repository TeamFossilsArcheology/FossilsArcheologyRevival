package com.fossil.fossil.entity.prehistoric;

import com.fossil.fossil.entity.ai.*;
import com.fossil.fossil.entity.data.EntityDataManager;
import com.fossil.fossil.entity.prehistoric.base.PrehistoricEntityType;
import com.fossil.fossil.entity.prehistoric.base.PrehistoricSwimming;
import com.fossil.fossil.network.MessageHandler;
import com.fossil.fossil.network.debug.MarkMessage;
import com.fossil.fossil.network.debug.NewMarkMessage;
import com.fossil.fossil.network.debug.VisionMessage;
import com.fossil.fossil.sounds.ModSounds;
import dev.architectury.injectables.annotations.ExpectPlatform;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.nbt.Tag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.entity.ai.control.SmoothSwimmingMoveControl;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.navigation.FlyingPathNavigation;
import net.minecraft.world.entity.ai.navigation.WaterBoundPathNavigation;
import net.minecraft.world.entity.animal.FlyingAnimal;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.Path;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import org.apache.commons.lang3.NotImplementedException;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib3.core.builder.Animation;
import software.bernie.geckolib3.core.manager.AnimationFactory;
import software.bernie.geckolib3.util.GeckoLibUtil;

import java.util.EnumSet;
import java.util.Optional;
import java.util.Random;


public class Meganeura extends PrehistoricSwimming implements FlyingAnimal {
    public static final String ANIMATIONS = "meganeura.animation.json";
    public static final String IDLE = "animation.meganeura.idle";
    public static final String VERTICAL_IDLE = "animation.meganeura.vertical_idle";
    public static final String WALK = "animation.meganeura.walk";
    public static final String FLY = "animation.meganeura.fly";
    public static final String EAT = "animation.meganeura.eat";
    public static final String ATTACK = "animation.meganeura.attack";
    private static final EntityDataManager.Data data = EntityDataManager.ENTITY_DATA.getData("meganeura");
    private static final EntityDataAccessor<Optional<BlockPos>> ATTACHED_BLOCK_POS = SynchedEntityData.defineId(Meganeura.class, EntityDataSerializers.OPTIONAL_BLOCK_POS);
    private static final EntityDataAccessor<Direction> ATTACHED_FACE = SynchedEntityData.defineId(Meganeura.class, EntityDataSerializers.DIRECTION);
    private final AnimationFactory factory = GeckoLibUtil.createFactory(this);
    private int attachCooldown = 0;
    private int attachTicks = 0;

    public Meganeura(EntityType<Meganeura> entityType, Level level) {
        super(entityType, level, false);
    }

    @ExpectPlatform
    public static Meganeura get(EntityType<Meganeura> entityType, Level level) {
        throw new NotImplementedException();
    }

    @Override
    public void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        compound.putInt("AttachCooldown", attachCooldown);
        compound.putInt("AttachFace", getAttachmentFace().get3DDataValue());
        if (getAttachmentPos() != null) {
            compound.put("AttachPos", NbtUtils.writeBlockPos(getAttachmentPos()));
        }
    }

    @Override
    public void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        attachCooldown = compound.getInt("AttachCooldown");
        setAttachmentFace(Direction.from3DDataValue(compound.getInt("AttachFace")));
        if (compound.contains("AttachPos", Tag.TAG_COMPOUND)) {
            setAttachmentPos(NbtUtils.readBlockPos(compound.getCompound("AttachPos")));
        }
    }

    @Override
    public Entity[] getCustomParts() {
        return new Entity[0];
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        goalSelector.addGoal(0, new EnterWaterWithoutTargetGoal(this, 1));
        goalSelector.addGoal(1, new MeganeuraEnterWaterGoal(this, 1));
        goalSelector.addGoal(3, new MeganeuraWanderAndAttachGoal(this));
        goalSelector.addGoal(4, new DinoFollowOwnerGoal(this, 1, 10, 2, 50, false));
        goalSelector.addGoal(5, new DinoMeleeAttackAI(this, 1, false));
        goalSelector.addGoal(7, new DinoLookAroundGoal(this));
        targetSelector.addGoal(1, new DinoOwnerHurtByTargetGoal(this));
        targetSelector.addGoal(2, new DinoOwnerHurtTargetGoal(this));
        targetSelector.addGoal(3, new HurtByTargetGoal(this));
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        entityData.define(ATTACHED_BLOCK_POS, Optional.empty());
        entityData.define(ATTACHED_FACE, Direction.DOWN);
    }

    public @Nullable BlockPos getAttachmentPos() {
        return entityData.get(ATTACHED_BLOCK_POS).orElse(null);
    }

    public void setAttachmentPos(@Nullable BlockPos blockPos) {
        entityData.set(ATTACHED_BLOCK_POS, Optional.ofNullable(blockPos));
    }

    public Direction getAttachmentFace() {
        return entityData.get(ATTACHED_FACE);
    }

    public void setAttachmentFace(Direction direction) {
        entityData.set(ATTACHED_FACE, direction);
    }

    @Override
    public boolean hurt(DamageSource source, float amount) {
        if (source == DamageSource.IN_WALL) {
            return false;
        }
        stopAttaching();
        attachCooldown = 1000 + random.nextInt(1500);
        return super.hurt(source, amount);
    }

    @Override
    public void aiStep() {
        super.aiStep();
        if (attachCooldown > 0) {
            attachCooldown--;
        }
        if (getAttachmentPos() == null) {
            Path path = getNavigation().getPath();
            if (path != null) {
                int[] targets = new int[path.getNodeCount() * 3];
                for (int i = 0; i < path.getNodeCount(); i++) {
                    targets[3 * i] = path.getNode(i).x;
                    targets[3 * i + 1] = path.getNode(i).y;
                    targets[3 * i + 2] = path.getNode(i).z;
                }
                MessageHandler.DEBUG_CHANNEL.sendToPlayers(((ServerLevel) level).getPlayers(serverPlayer -> serverPlayer.hasLineOfSight(this)),
                        new NewMarkMessage(targets, new BlockPos(getMoveControl().getWantedX(), getMoveControl().getWantedY(), getMoveControl().getWantedZ())));
            }
            attachTicks = 0;
            if ((verticalCollision || horizontalCollision) && attachCooldown == 0 && !isOnGround()) {
                attachCooldown = 5;
                Vec3 prevEyePos = getEyePosition(0);
                Vec3 prevViewVector = getViewVector(0);
                Vec3 target = prevEyePos.add(prevViewVector);
                BlockHitResult hitResult = level.clip(new ClipContext(prevEyePos, target, ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, this));

                MessageHandler.DEBUG_CHANNEL.sendToPlayers(((ServerLevel) level).getPlayers(serverPlayer -> serverPlayer.hasLineOfSight(this)),
                        new VisionMessage(hitResult.getBlockPos(), Blocks.GOLD_BLOCK.defaultBlockState()));
                BlockPos sidePos = hitResult.getBlockPos();
                if (level.getBlockState(sidePos).isFaceSturdy(level, sidePos, hitResult.getDirection())) {
                    setAttachmentPos(sidePos);
                    setAttachmentFace(hitResult.getDirection().getOpposite());
                    setDeltaMovement(Vec3.ZERO);
                }
            }
        } else {
            BlockPos attachmentPos = getAttachmentPos();
            if (level.getBlockState(attachmentPos).isFaceSturdy(level, attachmentPos, getAttachmentFace())) {
                attachTicks++;
                attachCooldown = 150;
                setYRot(getAttachmentFace().toYRot());
                yBodyRot = getAttachmentFace().toYRot();
                yHeadRot = getAttachmentFace().toYRot();
                //this.moveControl.operation = EntityMoveHelper.Action.WAIT;
                setDeltaMovement(Vec3.ZERO);
            } else {
                stopAttaching();
            }
        }
        if (attachTicks > 1150 && random.nextInt(123) == 0 || getAttachmentPos() != null && getTarget() != null) {
            stopAttaching();
            attachCooldown = 1000 + random.nextInt(1500);
        }

        boolean flying = isFlying();
        if (!isImmobile() && !useSwimAI() && getAttachmentPos() == null) {
            //setDeltaMovement(getDeltaMovement().add(0, 0.08, 0));
        } else if (!isBaby()) {
            //this.moveHelper.action = EntityMoveHelper.Action.WAIT;
        }
        if (flying && tickCount % 20 == 0 && !level.isClientSide && !isBaby() && getAttachmentPos() == null) {
            //playSound(FASoundRegistry.MEGANEURA_FLY, this.getSoundVolume(), 1);
        }
    }

    private void stopAttaching() {
        attachTicks = 0;
        setAttachmentFace(Direction.DOWN);
        setAttachmentPos(null);
    }

    @Override
    protected void checkFallDamage(double y, boolean onGround, BlockState state, BlockPos pos) {

    }

    public boolean isFlying() {
        return !isOnGround() && !isImmobile();
    }

    @Override
    protected void switchNavigator(boolean onLand) {
        if (onLand) {
            moveControl = new MeganeuraFlyingMoveControl(this);
            navigation = new FlyingPathNavigation(this, level);
            isLandNavigator = true;
        } else {
            moveControl = new SmoothSwimmingMoveControl(this, 90, 5, 0.1f, 0.01f, true);
            navigation = new WaterBoundPathNavigation(this, level);
            isLandNavigator = false;
        }
    }

    @Override
    public boolean isAmphibious() {
        return true;
    }

    @Override
    public float swimSpeed() {
        return 0.25f;
    }

    @Override
    public PrehistoricEntityType type() {
        return PrehistoricEntityType.MEGANEURA;
    }

    @Override
    public Item getOrderItem() {
        return Items.ARROW;
    }

    @Override
    public EntityDataManager.Data data() {
        return data;
    }

    @Override
    public @NotNull MobType getMobType() {
        return MobType.ARTHROPOD;
    }

    @Override
    public @NotNull Animation nextEatingAnimation() {
        return getAllAnimations().get(EAT);
    }

    @Override
    public @NotNull Animation nextIdleAnimation() {
        if (attachTicks > 0) {
            return getAllAnimations().get(VERTICAL_IDLE);
        }
        return getAllAnimations().get(IDLE);
    }

    @Override
    public @NotNull Animation nextMovingAnimation() {
        if (isFlying()) {
            return getAllAnimations().get(FLY);
        }
        return getAllAnimations().get(WALK);
    }

    @Override
    public @NotNull Animation nextChasingAnimation() {
        return getAllAnimations().get(WALK);
    }

    @Override
    public @NotNull Animation nextAttackAnimation() {
        return getAllAnimations().get(ATTACK);
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

    public boolean isDirectPathBetweenPoints(Vec3 target) {
        BlockHitResult hitResult = level.clip(new ClipContext(position().add(0, -0.25, 0), target, ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, this));
        BlockPos sidePos = hitResult.getBlockPos();
        BlockPos pos = new BlockPos(hitResult.getLocation());
        if (!level.isEmptyBlock(pos) || !level.isEmptyBlock(sidePos)) {
            return true;
        } else {
            return hitResult.getType() == HitResult.Type.BLOCK;
        }
    }

    public static BlockPos getPositionRelativetoGround(LivingEntity entity, double x, double z) {
        BlockPos.MutableBlockPos pos = new BlockPos.MutableBlockPos(x, entity.getY(), z);
        while (entity.level.isEmptyBlock(pos.below()) && pos.getY() > 0) {
            pos.move(0, -1, 0);
        }
        return pos.above(2 + entity.getRandom().nextInt(3));
    }

    static class MeganeuraFlyingMoveControl extends MoveControl {

        private final Meganeura meganeura;

        public MeganeuraFlyingMoveControl(Meganeura meganeura) {
            super(meganeura);
            this.meganeura = meganeura;
        }

        @Override
        public void setWantedPosition(double x, double y, double z, double speed) {
            super.setWantedPosition(x, y, z, speed);
        }

        @Override
        public void tick() {
            if (operation == Operation.MOVE_TO) {
                mob.setNoGravity(true);
                double xDist = wantedX - mob.getX();
                double yDist = wantedY - mob.getY();
                double zDist = wantedZ - mob.getZ();
                double targetDistance = Mth.sqrt((float) (xDist * xDist + zDist * zDist + yDist * yDist));
                if (targetDistance < 2.500000277905201E-7 || meganeura.getAttachmentPos() != null || (targetDistance <= mob.getBbWidth() / 2 && mob.level.isEmptyBlock(new BlockPos(wantedX, wantedY, wantedZ)))) {
                    operation = Operation.WAIT;
                    mob.setDeltaMovement(mob.getDeltaMovement().scale(0.5));
                    return;
                }
                if (mob.getTarget() == null) {
                    float newYRot = (float)(Mth.atan2(zDist, xDist) * Mth.RAD_TO_DEG) - 90;
                    mob.setYRot(newYRot);
                    mob.yBodyRot = newYRot;
                    float speed = (float) mob.getAttributeValue(Attributes.FLYING_SPEED);
                    double xSpeed = speed * 0.07 * xDist / targetDistance;
                    double ySpeed = speed * 0.07 * yDist / targetDistance;
                    double zSpeed = speed * 0.07 * zDist / targetDistance;
                    mob.setDeltaMovement(mob.getDeltaMovement().add(xSpeed, ySpeed, zSpeed));
                } else {
                    xDist = mob.getTarget().getX() - mob.getX();
                    zDist = mob.getTarget().getZ() - mob.getZ();
                    float newYRot = (float)(Mth.atan2(zDist, xDist) * Mth.RAD_TO_DEG) - 90;
                    mob.setYRot(newYRot);
                    mob.yBodyRot = newYRot;
                }
            } else if (meganeura.getAttachmentPos() == null) {
                mob.setNoGravity(false);
            }
        }
    }

    static class MeganeuraEnterWaterGoal extends EnterWaterWithTargetGoal {

        public MeganeuraEnterWaterGoal(Meganeura dino, float speedModifier) {
            super(dino, 40, speedModifier);
        }

        @Override
        public boolean canUse() {
            return dino.isBaby() && super.canUse();
        }
    }

    static class MeganeuraWanderAndAttachGoal extends Goal {
        private final Meganeura meganeura;
        protected BlockPos targetPos = BlockPos.ZERO;
        private boolean isGoingToAttach;

        public MeganeuraWanderAndAttachGoal(Meganeura meganeura) {
            this.meganeura = meganeura;
            setFlags(EnumSet.of(Goal.Flag.MOVE));
        }

        @Override
        public boolean canUse() {
            if (meganeura.attachCooldown != 0) {
                return false;
            }
            if (meganeura.random.nextInt(4) != 0 || meganeura.isBaby() || meganeura.isInSittingPose()) {
                return false;
            }
            findTargetPos();
            if (!meganeura.isDirectPathBetweenPoints(Vec3.atCenterOf(targetPos)) || meganeura.getAttachmentPos() != null) {
                return false;
            }
            return true;
        }

        @Override
        public boolean canContinueToUse() {
            return !meganeura.getNavigation().isDone() && !meganeura.isVehicle();
        }

        @Override
        public void start() {
            if (!meganeura.isDirectPathBetweenPoints(Vec3.atCenterOf(targetPos))) {
                targetPos = getPositionRelativetoGround(meganeura, meganeura.getX() + meganeura.getRandom().nextInt(15) - 7, meganeura.getZ() + meganeura.getRandom().nextInt(15) - 7);
            }
            if (meganeura.level.isEmptyBlock(targetPos) || isGoingToAttach) {
                if (!meganeura.isFlying()) {
                    meganeura.switchNavigator(true);
                }
                double x = targetPos.getX();
                double y = targetPos.getY();
                double z = targetPos.getZ();
                meganeura.getNavigation().moveTo(x, y, z, 0.25);
                if (meganeura.getTarget() == null) {
                    meganeura.getLookControl().setLookAt(x, y, z);
                }
            }
        }

        @Override
        public void stop() {
            meganeura.getNavigation().stop();
        }

        private void findTargetPos() {
            Random random = meganeura.getRandom();
            Level level = meganeura.level;
            if (meganeura.attachCooldown == 0) {
                for (int i = 0; i < 15; i++) {
                    BlockPos blockPos = meganeura.blockPosition().offset(random.nextInt(16) - 8, random.nextInt(10), random.nextInt(16) - 8);
                    if (!level.isEmptyBlock(blockPos)) {
                        BlockHitResult hitResult = level.clip(new ClipContext(meganeura.position().add(0, 0.25, 0), Vec3.atCenterOf(blockPos), ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, meganeura));
                        if (level.getBlockState(hitResult.getBlockPos()).isFaceSturdy(level, hitResult.getBlockPos(), hitResult.getDirection())) {
                            isGoingToAttach = true;
                            targetPos = hitResult.getBlockPos();
                            if (level.getBlockState(targetPos).isAir()) {
                                targetPos = getPositionRelativetoGround(meganeura, meganeura.getX() + random.nextInt(16) - 8, meganeura.getZ() + random.nextInt(16) - 8);
                            }
                            int[] targets = new int[6];
                            targets[0] = targetPos.getX();
                            targets[1] = targetPos.getY();
                            targets[2] = targetPos.getZ();
                            targets[3] = blockPos.getX();
                            targets[4] = blockPos.getY();
                            targets[5] = blockPos.getZ();
                            BlockState[] blocks = new BlockState[2];
                            blocks[0] = Blocks.GOLD_BLOCK.defaultBlockState();
                            blocks[1] = Blocks.EMERALD_BLOCK.defaultBlockState();
                            MessageHandler.DEBUG_CHANNEL.sendToPlayers(((ServerLevel) meganeura.level).getPlayers(serverPlayer -> serverPlayer.hasLineOfSight(meganeura)),
                                    new MarkMessage(targets, blocks, false));
                            return;
                        }
                    }
                }
            }
            targetPos = getPositionRelativetoGround(meganeura, meganeura.getX() + random.nextInt(16) - 8, meganeura.getZ() + random.nextInt(16) - 8);
        }
    }
}
