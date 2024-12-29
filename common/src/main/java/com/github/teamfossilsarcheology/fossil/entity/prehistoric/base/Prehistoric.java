package com.github.teamfossilsarcheology.fossil.entity.prehistoric.base;

import com.github.darkpred.morehitboxes.api.EntityHitboxData;
import com.github.darkpred.morehitboxes.api.EntityHitboxDataFactory;
import com.github.darkpred.morehitboxes.api.GeckoLibMultiPartEntity;
import com.github.darkpred.morehitboxes.api.MultiPart;
import com.github.teamfossilsarcheology.fossil.FossilMod;
import com.github.teamfossilsarcheology.fossil.advancements.ModTriggers;
import com.github.teamfossilsarcheology.fossil.client.OptionalTextureLoader;
import com.github.teamfossilsarcheology.fossil.config.FossilConfig;
import com.github.teamfossilsarcheology.fossil.entity.ModEntities;
import com.github.teamfossilsarcheology.fossil.entity.ai.*;
import com.github.teamfossilsarcheology.fossil.entity.ai.control.PrehistoricLookControl;
import com.github.teamfossilsarcheology.fossil.entity.ai.control.SmoothTurningMoveControl;
import com.github.teamfossilsarcheology.fossil.entity.ai.navigation.PrehistoricPathNavigation;
import com.github.teamfossilsarcheology.fossil.entity.animation.*;
import com.github.teamfossilsarcheology.fossil.entity.data.AI;
import com.github.teamfossilsarcheology.fossil.entity.data.Attribute;
import com.github.teamfossilsarcheology.fossil.entity.data.EntityDataLoader;
import com.github.teamfossilsarcheology.fossil.entity.prehistoric.system.AISystem;
import com.github.teamfossilsarcheology.fossil.entity.prehistoric.system.MoodSystem;
import com.github.teamfossilsarcheology.fossil.entity.prehistoric.system.SitSystem;
import com.github.teamfossilsarcheology.fossil.entity.prehistoric.system.SleepSystem;
import com.github.teamfossilsarcheology.fossil.entity.util.InstructionSystem;
import com.github.teamfossilsarcheology.fossil.entity.util.Util;
import com.github.teamfossilsarcheology.fossil.item.ModItems;
import com.github.teamfossilsarcheology.fossil.network.C2SHitPlayerMessage;
import com.github.teamfossilsarcheology.fossil.network.MessageHandler;
import com.github.teamfossilsarcheology.fossil.network.debug.C2SDisableAIMessage;
import com.github.teamfossilsarcheology.fossil.network.debug.SyncDebugInfoMessage;
import com.github.teamfossilsarcheology.fossil.sounds.ModSounds;
import com.github.teamfossilsarcheology.fossil.util.Diet;
import com.github.teamfossilsarcheology.fossil.util.FoodMappings;
import com.github.teamfossilsarcheology.fossil.util.Gender;
import com.github.teamfossilsarcheology.fossil.util.Version;
import dev.architectury.extensions.network.EntitySpawnExtension;
import dev.architectury.networking.NetworkManager;
import it.unimi.dsi.fastutil.Pair;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientboundSoundPacket;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.Difficulty;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.ai.navigation.WallClimberNavigation;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;
import software.bernie.geckolib3.util.GeckoLibUtil;

import java.util.*;

import static com.github.teamfossilsarcheology.fossil.entity.prehistoric.base.PrehistoricEntityInfoAI.*;

public abstract class Prehistoric extends TamableAnimal implements GeckoLibMultiPartEntity<Prehistoric>, PlayerRideableJumping, EntitySpawnExtension, PrehistoricAnimatable<Prehistoric>, PrehistoricDebug {

    private static final EntityDataAccessor<CompoundTag> DEBUG = SynchedEntityData.defineId(Prehistoric.class, EntityDataSerializers.COMPOUND_TAG);
    private static final EntityDataAccessor<Boolean> EATING = SynchedEntityData.defineId(Prehistoric.class, EntityDataSerializers.BOOLEAN);
    public static final EntityDataAccessor<Integer> MOOD = SynchedEntityData.defineId(Prehistoric.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> AGE_TICK = SynchedEntityData.defineId(Prehistoric.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> HUNGER = SynchedEntityData.defineId(Prehistoric.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Boolean> FLEEING = SynchedEntityData.defineId(Prehistoric.class, EntityDataSerializers.BOOLEAN);
    public static final EntityDataAccessor<Boolean> SITTING = SynchedEntityData.defineId(Prehistoric.class, EntityDataSerializers.BOOLEAN);
    public static final EntityDataAccessor<Boolean> SLEEPING = SynchedEntityData.defineId(Prehistoric.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> CLIMBING = SynchedEntityData.defineId(Prehistoric.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Direction> CLIMBING_DIR = SynchedEntityData.defineId(Prehistoric.class, EntityDataSerializers.DIRECTION);
    private static final EntityDataAccessor<Boolean> AGING_DISABLED = SynchedEntityData.defineId(Prehistoric.class, EntityDataSerializers.BOOLEAN);
    private final AnimationFactory factory = GeckoLibUtil.createFactory(this);
    private final List<AISystem> aiSystems = new ArrayList<>();
    public final MoodSystem moodSystem = registerSystem(new MoodSystem(this));
    public final SleepSystem sleepSystem = registerSystem(createSleepSystem());
    public final SitSystem sitSystem = registerSystem(new SitSystem(this));
    protected final WhipSteering steering = new WhipSteering(this);
    private final AnimationLogic<Prehistoric> animationLogic = new AnimationLogic<>(this);
    private final InstructionSystem instructionSystem = registerSystem(new InstructionSystem(this));
    public final ResourceLocation animationLocation;
    private OrderType currentOrder = OrderType.WANDER;
    public ResourceLocation textureLocation;
    public int climbTick;
    public int prevClimbTick;
    public Direction prevClimbDirection = Direction.UP;
    protected DinoMatingGoal matingGoal;
    protected float playerJumpPendingScale;
    private Gender gender = Gender.random(random);
    private int fleeTicks = 0;
    private int matingCooldown = random.nextInt(6000) + 6000;
    private int ticksClimbing = 0;
    private int climbingCooldown = 0;
    private Vec3 eatPos;
    private final EntityHitboxData<Prehistoric> hitboxData = EntityHitboxDataFactory.create(this);

    protected Prehistoric(EntityType<? extends Prehistoric> entityType, Level level, ResourceLocation animationLocation) {
        super(entityType, level);
        this.animationLocation = animationLocation;
        this.moveControl = new SmoothTurningMoveControl(this);
        this.lookControl = new PrehistoricLookControl(this);
        this.setHunger(this.getMaxHunger() / 2);
        this.updateAbilities();
        refreshDimensions();
        if (this.getMobType() == MobType.WATER) {
            this.setPathfindingMalus(BlockPathTypes.WATER, -1.0F);
            this.getNavigation().getNodeEvaluator().setCanFloat(true);
        }
        setPersistenceRequired();
    }

    protected Prehistoric(EntityType<? extends Prehistoric> entityType, Level level) {
        this(entityType, level, FossilMod.location("animations/" + EntityType.getKey(entityType).getPath() + ".animation.json"));
    }

    @Override
    public EntityHitboxData<Prehistoric> getEntityHitboxData() {
        return hitboxData;
    }

    @Override
    public boolean partHurt(MultiPart<Prehistoric> multiPart, @NotNull DamageSource damageSource, float v) {
        return hurt(damageSource, v);
    }

    @Override
    public void setAnchorPos(String boneName, Vec3 localPos) {
        if ("eat_pos".equals(boneName)) {
            eatPos = position().add(localPos);
        }
    }

    @Override
    public boolean canSetAnchorPos(String boneName) {
        return "eat_pos".equals(boneName);
    }

    @Override
    public boolean attackBoxHit(Player player) {
        MessageHandler.SYNC_CHANNEL.sendToServer(new C2SHitPlayerMessage(this, player));
        return true;
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 20)
                .add(Attributes.ATTACK_DAMAGE, 2)
                .add(Attributes.FLYING_SPEED, 0.4f);
    }

    @Override
    protected void registerGoals() {
        matingGoal = new DinoMatingGoal(this, 1);
        goalSelector.addGoal(Util.IMMOBILE, new DinoStayGoal(this));
        goalSelector.addGoal(Util.IMMOBILE + 1, new DinoPanicGoal(this, 1.5));
        goalSelector.addGoal(Util.IMMOBILE + 2, new FloatGoal(this));
        if (aiAttackType() != Attacking.NONE && aiAttackType() != Attacking.JUMP) {
            goalSelector.addGoal(Util.ATTACK + 1, new DelayedAttackGoal<>(this, 1, false));
        }
        goalSelector.addGoal(Util.SLEEP + 2, matingGoal);
        goalSelector.addGoal(Util.NEEDS, new EatFromFeederGoal(this));
        goalSelector.addGoal(Util.NEEDS + 1, new EatItemEntityGoal(this));
        goalSelector.addGoal(Util.NEEDS + 2, new EatBlockGoal(this));
        goalSelector.addGoal(Util.NEEDS + 3, new PlayGoal(this, 1));
        goalSelector.addGoal(Util.WANDER, new DinoFollowOwnerGoal(this, 1, 10, 2, false));
        goalSelector.addGoal(Util.WANDER + 1, new DinoWanderGoal(this, 1));
        goalSelector.addGoal(Util.LOOK, new LookAtPlayerGoal(this, Player.class, 8.0f));
        goalSelector.addGoal(Util.LOOK + 1, new RandomLookAroundGoal(this));
        targetSelector.addGoal(1, new DinoOwnerHurtByTargetGoal(this));
        targetSelector.addGoal(2, new DinoOwnerHurtTargetGoal(this));
        targetSelector.addGoal(3, new DinoHurtByTargetGoal(this));
        targetSelector.addGoal(5, new HuntingTargetGoal(this));
    }

    @Override
    public double getFluidJumpThreshold() {
        return 0.7 * getBbHeight();
    }

    @Override
    protected void updateControlFlags() {
        boolean enabled = !isSleeping() && !isWeak();
        goalSelector.setControlFlag(Goal.Flag.MOVE, enabled && !sitSystem.isSitting());
        goalSelector.setControlFlag(Goal.Flag.JUMP, enabled && !sitSystem.isSitting());
        goalSelector.setControlFlag(Goal.Flag.LOOK, enabled);
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        entityData.define(EATING, false);
        entityData.define(MOOD, 0);
        entityData.define(AGE_TICK, data().adultAgeDays() * 24000);
        entityData.define(HUNGER, 0);
        entityData.define(FLEEING, false);
        entityData.define(SITTING, false);
        entityData.define(SLEEPING, false);
        entityData.define(CLIMBING, false);
        entityData.define(CLIMBING_DIR, Direction.UP);
        entityData.define(AGING_DISABLED, false);

        CompoundTag tag = new CompoundTag();
        tag.putBoolean("disableGoalAI", false);
        tag.putBoolean("disableMoveAI", false);
        tag.putBoolean("disableLookAI", false);
        entityData.define(DEBUG, tag);
    }

    @Override
    public void onSyncedDataUpdated(EntityDataAccessor<?> key) {
        if (SLEEPING.equals(key)) {
            refreshTexturePath();
        } else if (AGE_TICK.equals(key)) {
            refreshDimensions();
            if (level.isClientSide) {
                refreshTexturePath();
            }
            updateAbilities();
        } else if (CLIMBING_DIR.equals(key)) {
            if (level.isClientSide && entityData.get(CLIMBING_DIR) != Direction.UP) {
                //Store climb direction after climbing stopped to undo rotation
                prevClimbDirection = entityData.get(CLIMBING_DIR);
            }
        }
        super.onSyncedDataUpdated(key);
    }

    @Override
    public void saveAdditionalSpawnData(FriendlyByteBuf buf) {
        buf.writeBoolean(getGender() == Gender.MALE);
        buf.writeInt(getAge());
        buf.writeFloat(getXRot());
        instructionSystem.saveAdditionalSpawnData(buf);
    }

    @Override
    public void loadAdditionalSpawnData(FriendlyByteBuf buf) {
        if (buf.readBoolean()) {
            gender = Gender.MALE;
        } else {
            gender = Gender.FEMALE;
        }
        setAgeInTicks(buf.readInt());
        setXRot(buf.readFloat());
        refreshTexturePath();
        instructionSystem.loadAdditionalSpawnData(buf);
    }

    @Override
    public void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        aiSystems.forEach(system -> system.saveAdditional(compound));
        compound.putInt("MatingCooldown", getMatingCooldown());
        compound.putInt("Hunger", getHunger());
        compound.putBoolean("Fleeing", isFleeing());
        compound.putBoolean("Climbing", isClimbing());
        compound.putInt("TicksClimbing", ticksClimbing);
        compound.putInt("ClimbingCooldown", climbingCooldown);
        compound.putByte("CurrentOrder", (byte) currentOrder.ordinal());
        compound.putFloat("YBodyRot", yBodyRot);
        compound.putFloat("YHeadRot", yHeadRot);
        compound.putBoolean("AgingDisabled", isAgingDisabled());
        compound.putString("Gender", getGender().toString());
    }

    @Override
    public void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        aiSystems.forEach(system -> system.load(compound));
        setAgeInTicks(compound.getInt("Age"));
        setMatingCooldown(compound.getInt("MatingCooldown"));
        setHunger(compound.getInt("Hunger"));
        setFleeing(compound.getBoolean("Fleeing"));
        setClimbing(compound.getBoolean("Climbing"));
        ticksClimbing = compound.getInt("TicksClimbing");
        climbingCooldown = compound.getInt("ClimbingCooldown");
        if (compound.contains("CurrentOrder", Tag.TAG_BYTE)) {
            setCurrentOrder(OrderType.values()[compound.getByte("CurrentOrder")]);
        }
        yBodyRot = compound.getInt("YBodyRot");
        yHeadRot = compound.getInt("YHeadRot");
        setAgingDisabled(compound.getBoolean("AgingDisabled"));
        if ("female".equalsIgnoreCase(compound.getString("Gender"))) {
            setGender(Gender.FEMALE);
        } else {
            setGender(Gender.MALE);
        }
    }

    @Override
    public @NotNull EntityDimensions getDimensions(Pose poseIn) {
        return getType().getDimensions().scale(getScale());
    }

    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor levelIn, DifficultyInstance difficultyIn, MobSpawnType reason, @Nullable SpawnGroupData spawnDataIn, @Nullable CompoundTag dataTag) {
        //Skip AgeableMob#finalizeSpawn
        getAttribute(Attributes.FOLLOW_RANGE).addPermanentModifier(new AttributeModifier("Random spawn bonus", this.random.nextGaussian() * 0.05, AttributeModifier.Operation.MULTIPLY_BASE));
        if (spawnDataIn instanceof PrehistoricGroupData prehistoricGroupData) {
            setAgeInDays(prehistoricGroupData.ageInDays());
        } else if (spawnDataIn == null) {
            spawnDataIn = new PrehistoricGroupData(data().adultAgeDays());
            setAgeInDays(((PrehistoricGroupData) spawnDataIn).ageInDays);
        }
        updateAbilities();
        refreshDimensions();
        moodSystem.setPlayingCooldown(0);
        setMatingCooldown(24000);
        heal(getMaxHealth());
        setCurrentOrder(OrderType.WANDER);
        setNoAi(false);
        return spawnDataIn;
    }

    public OrderType getCurrentOrder() {
        return this.currentOrder;
    }

    public void setCurrentOrder(OrderType newOrder) {
        currentOrder = newOrder;
    }

    @Override
    public boolean isImmobile() {
        return super.isImmobile() || isWeak();
    }

    @Override
    public void push(double x, double y, double z) {
        EntityDimensions dimensions = getDimensions(Pose.STANDING);
        float max = Math.max(dimensions.width, dimensions.height);
        if (max < 3) {
            float mult = Mth.lerp((Math.max(max, 1) - 1) / 2f, 1, 0);
            super.push(x * mult, y, z * mult);
        }
    }

    @Override
    public boolean isPushable() {
        return super.isPushable() && !isVehicle();
    }

    public boolean hasTarget() {
        return getTarget() != null || moodSystem.getToyTarget() != null;
    }

    @Override
    public float getWalkTargetValue(BlockPos pos, LevelReader level) {
        return 0;
    }

    @Override
    public boolean isFood(ItemStack stack) {
        return false;
    }

    @Override
    public boolean canBeLeashed(Player player) {
        return super.canBeLeashed(player) && isOwnedBy(player);
    }

    @Nullable
    public Player getRidingPlayer() {
        return getControllingPassenger() instanceof Player player ? player : null;
    }

    @Override
    public double getPassengersRidingOffset() {
        return getBbHeight() * 0.85;
    }

    @Override
    public void positionRider(Entity passenger) {
        super.positionRider(passenger);
        if (canBeControlledByRider() && passenger instanceof LivingEntity livingEntity) {
            yBodyRot = livingEntity.yHeadRot;
        }
        getEntityHitboxData().getAnchorData().getAnchorPos("rider_pos").ifPresentOrElse(pos -> {
            if (passenger instanceof Player) {
                passenger.setPos(pos.x, pos.y + passenger.getMyRidingOffset() - 0.2, pos.z);
            } else {
                passenger.setPos(pos.x, pos.y + passenger.getMyRidingOffset(), pos.z);
            }
        }, () -> {
            passenger.setPos(getX(), getY() + getPassengersRidingOffset() + passenger.getMyRidingOffset(), getZ());
        });
    }

    public double getJumpStrength() {
        return 0.3;
    }

    private void doJump(double upwardMovement, double forwardMovement) {
        hasImpulse = true;
        if (forwardMovement > 0) {
            float h = Mth.sin(getYRot() * ((float) Math.PI / 180));
            float i = Mth.cos(getYRot() * ((float) Math.PI / 180));
            setDeltaMovement(getDeltaMovement().add(-0.4f * h * playerJumpPendingScale, upwardMovement, 0.4f * i * playerJumpPendingScale));
        } else {
            setDeltaMovement(getDeltaMovement().x, upwardMovement, getDeltaMovement().z);
        }
    }

    @Override
    public boolean canJump() {
        return isVehicle();
    }

    @Override
    public void handleStartJump(int i) {
        playSound(SoundEvents.HORSE_JUMP, 0.4f, 1);
    }

    @Override
    public void handleStopJump() {
    }

    @Override
    public void onPlayerJump(int jumpPower) {
        playerJumpPendingScale = jumpPower >= 90 ? 1.0f : 0.4f + 0.4f * jumpPower / 90.0f;
    }

    @Override
    public boolean canBeControlledByRider() {
        return data().canBeRidden() && getControllingPassenger() instanceof LivingEntity rider && isOwnedBy(rider);
    }

    @Override
    public void travel(Vec3 travelVector) {
        if (isImmobile() && !isVehicle()) {
            super.travel(Vec3.ZERO);
            return;
        }
        LivingEntity rider = (LivingEntity) getControllingPassenger();
        if (rider == null || !canBeControlledByRider() || !steering.trySteering(rider)) {
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
        boolean fastInWater = aiMovingType() == Moving.AQUATIC || aiMovingType() == Moving.SEMI_AQUATIC;
        if (playerJumpPendingScale > 0) {
            double newYMovement = getJumpStrength() * playerJumpPendingScale * getBlockJumpFactor() + getJumpBoostPower();
            if (isInWater()) {
                if (fastInWater) {
                    doJump(newYMovement, newForwardMovement);
                } else {
                    doJump(newYMovement / 2, newForwardMovement / 2);
                }
            } else if (isOnGround()) {
                doJump(newYMovement, newForwardMovement);
            }
            playerJumpPendingScale = 0;
            //TODO: Fall damage reset
        }
        if (isControlledByLocalInstance()) {
            setSpeed((float) getAttributeValue(Attributes.MOVEMENT_SPEED));
            Vec3 newMovement = new Vec3(newStrafeMovement, travelVector.y, newForwardMovement).scale(0.5);
            if (isInWater()) {
                steering.slowWaterTravel(newMovement);
            } else {
                super.travel(newMovement);
            }
        } else {
            setDeltaMovement(Vec3.ZERO);
        }
        if (isOnGround()) {
            playerJumpPendingScale = 0;
        }
    }

    @Override
    @Nullable
    public Entity getControllingPassenger() {
        for (Entity passenger : getPassengers()) {
            if (passenger instanceof Player player && isOwnedBy(player) && getTarget() != passenger) {
                return player;
            }
        }
        return null;
    }

    @Override
    public boolean rideableUnderWater() {
        return true;
    }

    @Override
    public boolean causeFallDamage(float distance, float damageMultiplier, DamageSource source) {
        if (aiClimbType() == Climbing.ARTHROPOD || aiMovingType() == Moving.WALK_AND_GLIDE || aiMovingType() == Moving.FLIGHT) {
            return false;
        } else {
            return super.causeFallDamage(distance, damageMultiplier, source);
        }
    }

    @Override
    protected void customServerAiStep() {
        super.customServerAiStep();
        setSprinting(getMoveControl().getSpeedModifier() >= 1.25);
    }

    @Override
    public void aiStep() {
        updateSwingTime();
        super.aiStep();

        if (getRidingPlayer() != null) {
            maxUpStep = 1;
        } else {
            maxUpStep = 0.6f;
        }

        if (!level.isClientSide) {
            if (getHunger() > getMaxHunger()) {
                setHunger(getMaxHunger());
            }
            if (getMatingCooldown() > 0) {
                setMatingCooldown(getMatingCooldown() - 1);
            }
            if (FossilConfig.isEnabled(FossilConfig.HEALING_DINOS) && random.nextInt(500) == 0 && deathTime == 0) {
                heal(1);
            }

            if (Version.debugEnabled()) {
                MessageHandler.DEBUG_CHANNEL.sendToPlayers(((ServerLevel) level).getPlayers(serverPlayer -> serverPlayer.distanceTo(this) < 16),
                        new SyncDebugInfoMessage(getId(), gender.name(), getAge(), matingCooldown, moodSystem.getPlayingCooldown(), climbingCooldown, getHunger(), moodSystem.getMood()));
            }
            aiSystems.forEach(AISystem::serverTick);
            if (horizontalCollision && data().breaksBlocks() && moodSystem.getMood() < 0) {
                breakBlock((float) FossilConfig.getDouble(FossilConfig.BLOCK_BREAK_HARDNESS));
            }
            if (isFleeing()) {
                fleeTicks++;
                if (fleeTicks > getFleeingCooldown()) {
                    this.setFleeing(false);
                    fleeTicks = 0;
                }
            }
        } else {
            aiSystems.forEach(AISystem::clientTick);
        }
    }

    @Override
    public void tick() {
        super.tick();
        if (level.isClientSide) {
            //Used for smooth rotation
            prevClimbTick = climbTick;
            if (isClimbing()) {
                climbTick = Math.min(5, climbTick + 1);
            } else {
                climbTick = Math.max(0, climbTick - 1);
            }
        } else {
            if (!isAgingDisabled() && canAgeUpNaturally()) {
                setAgeInTicks(getAge() + 1);
            }
            if (tickCount % 1200 == 0 && getHunger() > 0 && FossilConfig.isEnabled(FossilConfig.ENABLE_HUNGER)) {
                if (!isNoAi()) {
                    setHunger(getHunger() - 1);
                } else {
                    setHunger(getMaxHunger());
                    setHealth(getMaxHealth());
                }
            }
            if (tickCount % 40 == 0 && getHunger() == 0 && getHealth() > (FossilConfig.isEnabled(FossilConfig.ENABLE_STARVATION) ? 0 : getMaxHealth() / 2)) {
                hurt(DamageSource.STARVE, 1);
            }

            if (aiClimbType() == Climbing.ARTHROPOD) {
                if (isClimbing()) {
                    ticksClimbing++;
                    boolean onCooldown = ticksClimbing >= 100 || level.getBlockState(blockPosition().above()).getMaterial().isSolid();
                    if (!horizontalCollision || onCooldown) {
                        stopClimbing();
                        ticksClimbing = 0;
                        if (onCooldown) {
                            climbingCooldown = 900;
                        }
                    } else {
                        Pair<Direction, Double> dir = Util.getClosestSide(getBoundingBox(), blockPosition());
                        entityData.set(CLIMBING_DIR, dir.key());
                        if (getDeltaMovement().horizontalDistance() < Mth.EPSILON) {
                            //Climbing in corner
                            setYRot(dir.key().toYRot());
                        }
                    }
                } else {
                    climbingCooldown--;
                    if (canClimb()) {
                        ticksClimbing = 0;
                        setClimbing(true);
                    }
                }
            }
        }
    }

    protected boolean canClimb() {
        return climbingCooldown <= 0 && horizontalCollision && !sleepSystem.wantsToSleep() && !isSleeping();
    }

    private float scaleOverride = -1;

    public void setScaleOverride(float scaleOverride) {
        this.scaleOverride = scaleOverride;
    }

    @Override
    public float getScale() {
        if (scaleOverride > 0) {
            return scaleOverride;
        }
        if (isAdult()) {
            return data().maxScale() * getGenderedScale();
        }
        float step = (data().maxScale() - data().minScale()) / ((data().adultAgeDays() * 24000) + 1);
        return (data().minScale() + step * getAge()) * getGenderedScale();
    }

    public float getGenderedScale() {
        return 1;
    }

    @Override
    protected int getExperienceReward(Player player) {
        float base = 6 * getBbWidth() * (data().diet() == Diet.HERBIVORE ? 1 : 2)
                * (aiTameType() == Taming.GEM ? 2 : 1)
                * (aiAttackType() == Attacking.BASIC ? 1 : 1.25f);
        return Mth.floor((float) Math.min(data().adultAgeDays(), getAgeInDays()) * base);
    }

    public void updateAbilities() {
        float scale = (data().minScale() + (data().maxScale() - data().minScale()) / (data().adultAgeDays() * 24000) * getAge());
        scale = Math.min(scale, data().maxScale());
        if (level.isClientSide) {
            animationLogic.setAttributeSpeed(Util.calculateSpeed(data(), scale));
            return;
        }
        double percent = Math.min(getAge() / data().adultAgeDays() * 24000, 1);

        double healthDifference = getAttributeValue(Attributes.MAX_HEALTH);
        getAttribute(Attributes.MAX_HEALTH).setBaseValue(Math.round(Mth.lerp(percent, attributes().baseHealth(), attributes().maxHealth())));
        healthDifference = getAttributeValue(Attributes.MAX_HEALTH) - healthDifference;
        heal((float) healthDifference);
        getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(Math.round(Mth.lerp(percent, attributes().baseDamage(), attributes().maxDamage())));
        getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(Util.calculateSpeed(data(), scale));
        getAttribute(Attributes.ARMOR).setBaseValue(Mth.lerp(percent, attributes().baseArmor(), attributes().maxArmor()));
        getAttribute(Attributes.KNOCKBACK_RESISTANCE).setBaseValue(Mth.lerp(percent, attributes().baseKnockBackResistance(), attributes().maxKnockBackResistance()));
    }

    public void breakBlock(float maxHardness) {
        if (!FossilConfig.isEnabled(FossilConfig.DINOS_BREAK_BLOCKS) || !level.getGameRules().getBoolean(GameRules.RULE_MOBGRIEFING)) {
            return;
        }
        if (!isAdult() || !isHungry()) {
            return;
        }
        AABB aabb = getBoundingBox().inflate(0.1, 0, 0.1);
        boolean waterMob = this instanceof PrehistoricSwimming;
        int lowest = Mth.floor(aabb.minY) + (waterMob ? 0 : 1);
        for (BlockPos targetPos : BlockPos.betweenClosed(Mth.floor(aabb.minX), lowest, Mth.floor(aabb.minZ), Mth.floor(aabb.maxX), Mth.ceil(aabb.maxY), Mth.floor(aabb.maxZ))) {
            if (Util.canBreak(level, targetPos, maxHardness)) {
                setDeltaMovement(getDeltaMovement().multiply(0.6, 1, 0.6));
                level.destroyBlock(targetPos, random.nextInt(10) == 0, this);
                if (waterMob && targetPos.getY() == lowest && isInWater()) {
                    level.setBlock(targetPos, Blocks.WATER.defaultBlockState(), 3);
                }
            }
        }
    }

    @Override
    public boolean isSleeping() {
        return entityData.get(SLEEPING);
    }

    @Override
    public void startSleeping(BlockPos pos) {
    }

    @Override
    public void stopSleeping() {
    }

    @Override
    public boolean onClimbable() {
        return aiClimbType() == Climbing.ARTHROPOD && isClimbing();
    }

    public boolean isClimbing() {
        return entityData.get(CLIMBING);
    }

    public void setClimbing(boolean climbing) {
        this.entityData.set(CLIMBING, climbing);
    }
    public void stopClimbing() {
        setClimbing(false);
        entityData.set(CLIMBING_DIR, Direction.UP);
    }

    public Direction getClimbingDirection() {
        return entityData.get(CLIMBING_DIR);
    }

    public boolean isFleeing() {
        return entityData.get(FLEEING);
    }

    public void setFleeing(boolean fleeing) {
        entityData.set(FLEEING, fleeing);
    }

    protected int getFleeingCooldown() {
        if (this.getLastHurtByMob() != null) {
            int i = (int) (Math.max(this.getLastHurtByMob().getBbWidth() / 2F, 1) * 95);
            int j = (int) (Math.min(this.getBbWidth() / 2F, 0.5D) * 50);
            return i - j;
        }
        return 100;
    }

    public boolean isAdult() {
        return getAgeInDays() >= data().adultAgeDays();
    }

    public boolean isTeen() {
        return getAgeInDays() >= data().teenAgeDays() && getAgeInDays() < data().adultAgeDays();
    }

    @Override
    public boolean isBaby() {
        return getAgeInDays() < data().teenAgeDays();
    }

    public int getAgeInDays() {
        return getAge() / 24000;
    }

    public void setAgeInDays(int days) {
        setAgeInTicks(days * 24000);
    }

    @Override
    public int getAge() {
        return level.isClientSide ? entityData.get(AGE_TICK) : age;
    }

    @Override
    public void setAge(int age) {
    }

    public void setAgeInTicks(int age) {
        if (isAgingDisabled()) {
            return;
        }
        if (tickCount % 120 == 0 || age > this.age + 120 || age < this.age - 120) {
            entityData.set(AGE_TICK, age);
        }
        this.age = age;
    }

    public void grow(int ageInDays) {
        if (isAgingDisabled()) {
            return;
        }
        setAgeInDays(getAgeInDays() + ageInDays);
        updateAbilities();
        level.broadcastEntityEvent(this, GROW_UP_PARTICLES);
    }

    public boolean isAgingDisabled() {
        return this.entityData.get(AGING_DISABLED);
    }

    protected boolean canAgeUpNaturally() {
        return true;
    }

    public void setAgingDisabled(boolean isAgingDisabled) {
        this.entityData.set(AGING_DISABLED, isAgingDisabled);
    }

    @Override
    public boolean canMate(Animal otherAnimal) {
        if (otherAnimal == this || otherAnimal.getClass() != getClass() || moodSystem.getMood() <= 50 || !isAdult() || getMatingCooldown() > 0) {
            return false;
        }
        Prehistoric other = ((Prehistoric) otherAnimal);
        return other.gender != gender && other.getMatingCooldown() <= 0 && other.moodSystem.getMood() > 50 && (matingGoal.getPartner() == null || matingGoal.getPartner() == otherAnimal);
    }

    @Override
    public AgeableMob getBreedOffspring(ServerLevel serverLevel, AgeableMob otherParent) {
        if (otherParent instanceof Prehistoric) {
            Entity baby = info().entityType().create(level);
            if (baby instanceof Prehistoric prehistoricBaby) {
                prehistoricBaby.finalizeSpawn((ServerLevelAccessor) level, level.getCurrentDifficultyAt(blockPosition()),
                        MobSpawnType.BREEDING, new Prehistoric.PrehistoricGroupData(0), null);
                prehistoricBaby.grow(0);
                return prehistoricBaby;
            }
        }
        return null;
    }

    public void procreate(Prehistoric other) {
        if (getGender() == Gender.MALE) {
            setTarget(null);
            return;
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        if (random.nextInt(100) == 0 || calendar.get(Calendar.MONTH) + 1 == 4 && calendar.get(Calendar.DATE) == 1) {
            playSound(ModSounds.MUSIC_MATING.get(), 1, 1);
        }
        if (!level.isClientSide) {
            Entity hatchling;
            if (info().mobType == PrehistoricMobType.MAMMAL) {
                hatchling = getType().create(level);
            } else if (info().cultivatedBirdEggItem != null) {
                hatchling = new ItemEntity(level, getX(), getY(), getZ(), new ItemStack(info().cultivatedBirdEggItem));
            } else if (FossilConfig.isEnabled(FossilConfig.EGGS_LIKE_CHICKENS) || info().isViviparousAquatic()) {
                hatchling = new ItemEntity(level, getX(), getY(), getZ(), new ItemStack(info().eggItem));
            } else {
                hatchling = ModEntities.DINOSAUR_EGG.get().create(level);
                ((DinosaurEgg) hatchling).setPrehistoricEntityInfo(info());
                //((ServerLevel)level).getServer().getAdvancements().getAdvancement(Fossil.location("all_eggs"));
                if (getOwner() instanceof ServerPlayer player && player.getRecipeBook().contains(DinosaurEgg.GOLDEN_EGG_RECIPE)) {
                    ((DinosaurEgg) hatchling).setGoldenEgg(random.nextFloat() < 0.05);
                }
            }
            setTarget(null);
            hatchling.moveTo(getX(), getY(), getZ(), yBodyRot, 0);
            if (hatchling instanceof Prehistoric prehistoricHatchling) {
                prehistoricHatchling.finalizeSpawn((ServerLevelAccessor) level, level.getCurrentDifficultyAt(blockPosition()),
                        MobSpawnType.BREEDING, new Prehistoric.PrehistoricGroupData(0), null);
                prehistoricHatchling.grow(0);
            }
            level.addFreshEntity(hatchling);
        }
    }

    public int getMatingCooldown() {
        return matingCooldown;
    }

    public void setMatingCooldown(int cooldown) {
        this.matingCooldown = cooldown;
    }

    public Gender getGender() {
        return gender == null ? Gender.MALE : gender;
    }

    public void setGender(@NotNull Gender gender) {
        this.gender = gender;
        refreshTexturePath();
    }

    public int getClimbingCooldown() {
        return climbingCooldown;
    }

    public void setClimbingCooldown(int cooldown) {
        this.climbingCooldown = cooldown;
    }

    public boolean isWeak() {
        return (aiTameType() == Taming.AQUATIC_GEM || aiTameType() == Taming.GEM) && getHealth() < 8 && isAdult() && !isTame();
    }

    public int getMaxHunger() {
        return data().maxHunger();
    }

    public boolean isHungry() {
        return getHunger() < getMaxHunger() * 0.75F;
    }

    public boolean isDeadlyHungry() {
        return getHunger() < getMaxHunger() * 0.25F;
    }

    public int getHunger() {
        return this.entityData.get(HUNGER);
    }

    public void setHunger(int hunger) {
        entityData.set(HUNGER, Math.min(hunger, getMaxHunger()));
    }

    public void eatItem(ItemStack stack) {
        if (stack != null && (FoodMappings.getFoodAmount(stack.getItem(), data().diet()) != 0)) {
            moodSystem.increaseMood(5);
            feed(FoodMappings.getFoodAmount(stack.getItem(), data().diet()));
            stack.shrink(1);
            animationLogic.triggerAnimation(AnimationLogic.IDLE_CTRL, getAnimation(AnimationCategory.EAT), AnimationCategory.EAT);
        }
    }

    public void feed(int foodAmount) {
        setHunger(getHunger() + foodAmount);
    }

    @Override
    public void killed(ServerLevel level, LivingEntity killedEntity) {
        super.killed(level, killedEntity);
        if (data().diet() != Diet.HERBIVORE) {
            feed(FoodMappings.getMobFoodPoints(killedEntity, data().diet()));
            heal(FoodMappings.getMobFoodPoints(killedEntity, data().diet()) / 10f);
            moodSystem.increaseMood(25);
        }
    }

    @Override
    public boolean hurt(DamageSource source, float amount) {
        if (source == DamageSource.IN_WALL) {
            return false;
        }
        boolean hurt = super.hurt(source, amount);
        if (hurt) {
            if (getLastHurtByMob() instanceof Player player && getOwner() == player) {
                setOwnerUUID(null);
                setTame(false);
                moodSystem.increaseMood(-15);
                player.displayClientMessage(new TranslatableComponent("entity.fossil.situation.betrayed", getName()), true);
            }

            if (amount > 0) {
                sleepSystem.setSleeping(false);
            }
            if (source.getEntity() != null) {
                moodSystem.increaseMood(-5);
            }
        }
        return hurt;
    }

    @Override
    protected void reassessTameGoals() {
        if (!isTame()) {
            setCurrentOrder(OrderType.WANDER);
        }
    }

    @Override
    public @NotNull InteractionResult mobInteract(Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        if (stack.isEmpty()) {
            return InteractionResult.PASS;
        }
        if (isWeak() && (aiTameType() == Taming.GEM && stack.is(ModItems.SCARAB_GEM.get()) || aiTameType() == Taming.AQUATIC_GEM && stack.is(ModItems.AQUATIC_SCARAB_GEM.get()))) {
            //Tame with gem
            if (!level.isClientSide) {
                ModTriggers.SCARAB_TAME_TRIGGER.trigger((ServerPlayer) player);
                heal(200);
                moodSystem.setMood(100);
                feed(500);
                getNavigation().stop();
                setTarget(null);
                setLastHurtByMob(null);
                tame(player);
                level.broadcastEntityEvent(this, TOTEM_PARTICLES);
                stack.shrink(1);
            }
            return InteractionResult.sidedSuccess(level.isClientSide);
        } else if (stack.is(ModItems.CHICKEN_ESSENCE.get()) && aiTameType() != Taming.GEM && aiTameType() != Taming.AQUATIC_GEM) {
            //Grow up with chicken essence
            if (!level.isClientSide) {
                if (isAdult()) {
                    player.displayClientMessage(new TranslatableComponent("prehistoric.essence_fail_adult"), true);
                    return InteractionResult.PASS;
                }
                if (isDeadlyHungry()) {
                    player.displayClientMessage(new TranslatableComponent("prehistoric.essence_fail_hungry"), true);
                    return InteractionResult.PASS;
                }
                if (!player.getAbilities().instabuild) {
                    stack.shrink(1);
                    player.addItem(new ItemStack(Items.GLASS_BOTTLE, 1));
                }
                grow(1);
                setHunger(1 + random.nextInt(getHunger()));
            }
            return InteractionResult.sidedSuccess(level.isClientSide);
        } else if (stack.is(ModItems.STUNTED_ESSENCE.get()) && !isAgingDisabled()) {
            //Stunt growth with stunted essence
            if (!level.isClientSide) {
                setHunger(getHunger() + 20);
                heal(getMaxHealth());
                setAgingDisabled(true);
                stack.shrink(1);
                playSound(SoundEvents.ZOMBIE_VILLAGER_CURE, getSoundVolume(), getVoicePitch());
            } else {
                AABB aabb = eatPos == null ? getBoundingBoxForCulling() : new AABB(eatPos, eatPos);
                Util.spawnItemParticles(level, stack.getItem(), 15, aabb);
                Util.spawnItemParticles(level, stack.getItem(), 15, aabb);
                Util.spawnItemParticles(level, Items.POISONOUS_POTATO, 15, aabb);
                Util.spawnItemParticles(level, Items.POISONOUS_POTATO, 15, aabb);
                Util.spawnItemParticles(level, Items.EGG, 15, aabb);
            }
            return InteractionResult.sidedSuccess(level.isClientSide);
        } else if (FoodMappings.getFoodAmount(stack.getItem(), data().diet()) > 0) {
            //Feed dino
            if (getHunger() < getMaxHunger() || getHealth() < getMaxHealth() && FossilConfig.isEnabled(FossilConfig.HEALING_DINOS) || !isTame() && aiTameType() == Taming.FEEDING) {
                if (!level.isClientSide) {
                    eatItem(stack);
                    if (FossilConfig.isEnabled(FossilConfig.HEALING_DINOS)) {
                        heal(3);
                    }
                    if (getHunger() >= getMaxHunger() && isTame()) {
                        player.displayClientMessage(new TranslatableComponent("entity.fossil.situation.full", getName()), true);
                    }
                    if (aiTameType() == Taming.FEEDING && !isTame() && random.nextInt(10) == 1) {
                        tame(player);
                        level.broadcastEntityEvent(this, TOTEM_PARTICLES);
                    }
                }
                return InteractionResult.sidedSuccess(level.isClientSide);
            }
        } else if (stack.is(ModItems.WHIP.get()) && aiTameType() != Taming.NONE && isAdult()) {
            if (isOwnedBy(player) && data().canBeRidden()) {
                if (getRidingPlayer() == null && !level.isClientSide) {
                    player.yBodyRot = this.yBodyRot;
                    player.setXRot(getXRot());
                    player.startRiding(this);
                    sitSystem.setSitting(false);
                    sleepSystem.setSleeping(false);
                    setCurrentOrder(OrderType.WANDER);
                } else if (getRidingPlayer() == player) {
                    setSprinting(true);
                    moodSystem.increaseMood(-5);
                }
            } else if (FossilConfig.isEnabled(FossilConfig.WHIP_TO_TAME_DINO) && !isTame() && aiTameType() != Taming.AQUATIC_GEM && aiTameType() != Taming.GEM) {
                if (!level.isClientSide) {
                    moodSystem.increaseMood(-5);
                    if (random.nextInt(5) == 0) {
                        player.displayClientMessage(new TranslatableComponent("entity.fossil.prehistoric.tamed", info().displayName.get()), true);
                        moodSystem.increaseMood(-25);
                        tame(player);
                    }
                }
            }
            return InteractionResult.sidedSuccess(level.isClientSide);
        } else if (stack.is(getOrderItem()) && isOwnedBy(player) && !player.isPassenger()) {
            if (!level.isClientSide) {
                jumping = false;
                getNavigation().stop();
                setCurrentOrder(OrderType.values()[(currentOrder.ordinal() + 1) % 3]);
                sendOrderMessage(currentOrder);
            }
            return InteractionResult.sidedSuccess(level.isClientSide);
        }
        return InteractionResult.PASS;
    }

    private void sendOrderMessage(OrderType orderType) {
        if (getOwner() instanceof Player player) {
            player.displayClientMessage(new TranslatableComponent("entity.fossil.order." + orderType.name().toLowerCase(), getName()), true);
        }
    }

    protected boolean hasTeenTexture() {
        return OptionalTextureLoader.INSTANCE.hasTeenTexture(info().resourceName);
    }

    public void refreshTexturePath() {
        if (!level.isClientSide) {
            return;
        }
        String name = getType().arch$registryName().getPath();
        StringBuilder builder = new StringBuilder();
        builder.append("textures/entity/");
        builder.append(name);
        builder.append("/");
        builder.append(name);
        if (isBaby()) builder.append("_baby");
        if (hasTeenTexture() && isTeen()) builder.append("_teen");
        if (!hasTeenTexture() && isTeen() || isAdult()) {
            if (gender == Gender.MALE) {
                builder.append("_male");
            } else {
                builder.append("_female");
            }
        }
        if (isSleeping()) builder.append("_sleeping");
        builder.append(".png");
        textureLocation = FossilMod.location(builder.toString());
    }

    @Override
    public boolean canAttack(LivingEntity target) {
        if (isOwnedBy(target) && moodSystem.getMoodFace() != PrehistoricMoodType.ANGRY) {
            return false;
        }
        if (target instanceof Player && level.getDifficulty() == Difficulty.PEACEFUL) {
            return false;
        }
        return target.canBeSeenAsEnemy();
    }

    public float getTargetScale() {
        return 1;
    }

    @Override
    protected @NotNull PathNavigation createNavigation(Level levelIn) {
        return aiClimbType() == Climbing.ARTHROPOD ? new WallClimberNavigation(this, levelIn) : new PrehistoricPathNavigation(this, levelIn);
    }

    protected @NotNull SleepSystem createSleepSystem() {
        return new SleepSystem(this);
    }

    protected <T extends AISystem> T registerSystem(T system) {
        aiSystems.add(system);
        return system;
    }

    public List<? extends Prehistoric> getNearbySpeciesMembers(int range) {
        return level.getEntitiesOfClass(getClass(), getBoundingBox().inflate(range, 4.0D, range), prehistoric -> prehistoric != this);
    }

    @Override
    public void playAmbientSound() {
        if (isSleeping() || level.isClientSide) {
            return;
        }
        if (isUnderWater()) {
            //Copy of playAmbientSound, but we filter out players that aren't also underwater
            SoundEvent soundEvent = getAmbientSound();
            if (soundEvent != null) {
                float volume = getSoundVolume();
                double radius = volume > 1 ? (double) (16 * volume) : 16;
                var packet = new ClientboundSoundPacket(soundEvent, getSoundSource(), getX(), getY(), getZ(), volume, getVoicePitch());
                for (ServerPlayer player : ((ServerLevel) level).getServer().getPlayerList().getPlayers()) {
                    if (player.isUnderWater() && player.level.dimension() == level.dimension()) {
                        double d = getX() - player.getX();
                        double e = getY() - player.getY();
                        double f = getZ() - player.getZ();
                        if (d * d + e * e + f * f < radius * radius) {
                            player.connection.send(packet);
                        }
                    }
                }
            }
        } else {
            super.playAmbientSound();
        }
    }

    @Override
    public int getAmbientSoundInterval() {
        return 200;
    }

    @Override
    protected float getSoundVolume() {
        return isBaby() ? super.getSoundVolume() * 0.375f : 0.5f;
    }

    public ServerAnimationInfo startAttack() {
        ServerAnimationInfo attackAnim = (ServerAnimationInfo) nextAttackAnimation();
        getAnimationLogic().triggerAnimation(AnimationLogic.ATTACK_CTRL, attackAnim, AnimationCategory.ATTACK);
        return attackAnim;
    }

    public boolean attackTarget(LivingEntity target) {
        if (getBbWidth() > target.getBbWidth()) {
            double resistance = 1 - target.getAttributeValue(Attributes.KNOCKBACK_RESISTANCE);
            target.setDeltaMovement(target.getDeltaMovement().add(0, 0.4 * resistance + 0.1, 0));
        }
        return doHurtTarget(target);
    }

    @Override
    public boolean doHurtTarget(Entity target) {
        // TODO Implement this properly, all dinosaurs may run away after attacking player if pvp is disabled
        //Not to be affected: Compso, Lio, Megalodon, Mosa, Nautilus, Spino. Why? Who knows
        // Should the check be based on size?
        boolean wasEffective = super.doHurtTarget(target);
        if (!wasEffective) setFleeing(true);
        return wasEffective;
    }

    public static final byte TOTEM_PARTICLES = 35;
    public static final byte WHEAT_SEEDS_PARTICLES = 62;
    public static final byte BREAD_PARTICLES = 63;
    public static final byte BEEF_PARTICLES = 64;
    public static final byte HAPPY_VILLAGER_PARTICLES = 65;
    public static final byte GROW_UP_PARTICLES = 66;

    @Override
    public void handleEntityEvent(byte id) {
        if (id == WHEAT_SEEDS_PARTICLES) {
            Util.spawnItemParticles(this, Items.WHEAT_SEEDS, 3);
        } else if (id == BREAD_PARTICLES) {
            Util.spawnItemParticles(this, Items.BREAD, 3);
        } else if (id == BEEF_PARTICLES) {
            Util.spawnItemParticles(this, Items.BREAD, 3);
            Util.spawnItemParticles(this, Items.BEEF, 3);
        } else if (id == HAPPY_VILLAGER_PARTICLES) {
            Util.spawnParticles(this, ParticleTypes.HAPPY_VILLAGER, 6);
        } else if (id == GROW_UP_PARTICLES) {
            Util.spawnParticles(this, ParticleTypes.HAPPY_VILLAGER, getAgeInDays());
        } else {
            super.handleEntityEvent(id);
        }
    }

    private void makeEatingSounds() {
        level.playLocalSound(getX(), getY(), getZ(), SoundEvents.GENERIC_EAT, getSoundSource(), getSoundVolume(), getVoicePitch(), false);
    }

    public float getMaxTurnDistancePerTick() {
        return Mth.clamp(90 - getBbWidth() * 35, 5, 90);
    }

    public float getProximityToNextPathSkip() {
        return this.getBbWidth() > 0.75F ? this.getBbWidth() / 2.0F : 0.75F - this.getBbWidth() / 2.0F;
    }

    @Override
    public @NotNull Packet<?> getAddEntityPacket() {
        return NetworkManager.createAddEntityPacket(this);
    }

    public abstract PrehistoricEntityInfo info();

    public abstract Item getOrderItem();

    public EntityDataLoader.Data data() {
        return EntityDataLoader.INSTANCE.getData(info().resourceName);
    }

    public Attribute attributes() {
        return data().attributes();
    }

    private AI ai() {
        return data().ai();
    }

    public Activity aiActivityType() {
        return ai().activity();
    }

    public Attacking aiAttackType() {
        return ai().attacking();
    }

    public Climbing aiClimbType() {
        return ai().climbing();
    }

    public Response aiResponseType() {
        return isBaby() ? Response.SCARED : ai().response();
    }

    public Taming aiTameType() {
        return ai().taming();
    }

    public Moving aiMovingType() {
        return ai().moving();
    }

    @Override
    public Map<AnimationCategory, AnimationHolder> getAnimations() {
        return AnimationCategoryLoader.INSTANCE.getAnimations(animationLocation);
    }

    @Override
    public Map<String, ? extends AnimationInfo> getAllAnimations() {
        if (level.isClientSide) {
            return ClientAnimationInfoLoader.INSTANCE.getAnimations(animationLocation).animations();
        }
        return ServerAnimationInfoLoader.INSTANCE.getAnimations(animationLocation).animations();
    }

    @Override
    public AnimationInfo getAnimation(AnimationCategory category) {
        return getRandomAnimation(category);
    }

    @Override
    public Map<String, ServerAnimationInfo> getServerAnimationInfos() {
        return ServerAnimationInfoLoader.INSTANCE.getAnimations(animationLocation).animations();
    }

    public @NotNull AnimationInfo nextAttackAnimation() {
        return getAnimation(AnimationCategory.ATTACK);
    }

    @Override
    public void registerControllers(AnimationData data) {
        var controller = new PausableAnimationController<>(this, AnimationLogic.IDLE_CTRL, 5, animationLogic::landPredicate);
        registerEatingListeners(controller);
        data.addAnimationController(controller);
        data.addAnimationController(new PausableAnimationController<>(
                this, AnimationLogic.ATTACK_CTRL, 5, animationLogic::attackPredicate));
    }

    protected void registerEatingListeners(AnimationController<? extends Prehistoric> controller) {
        controller.registerParticleListener(event -> {
            if ("eat".equals(event.effect)) {
                //TODO: Could use event.script + getScale to increase the aabb size
                AABB aabb = eatPos == null ? getBoundingBoxForCulling() : new AABB(eatPos, eatPos);
                switch (data().diet()) {
                    case HERBIVORE -> Util.spawnItemParticles(level, Items.WHEAT_SEEDS, 4, aabb);
                    case OMNIVORE -> Util.spawnItemParticles(level, Items.BREAD, 4, aabb);
                    case PISCIVORE -> Util.spawnItemParticles(level, Items.COD, 4, aabb);
                    default -> Util.spawnItemParticles(level, Items.BEEF, 4, aabb);
                }
            }
        });
        controller.registerSoundListener(event -> {
            if ("eat".equals(event.sound)) {
                makeEatingSounds();
            }
            if ("call".equals(event.sound)) {
                playAmbientSound();
            }
        });
    }

    @Override
    public AnimationFactory getFactory() {
        return factory;
    }

    public AnimationLogic<Prehistoric> getAnimationLogic() {
        return animationLogic;
    }

    public InstructionSystem getInstructionSystem() {
        return instructionSystem;
    }

    @Override
    public CompoundTag getDebugTag() {
        return entityData.get(DEBUG);
    }

    @Override
    public void disableCustomAI(byte type, boolean disableAI) {
        if (!Version.debugEnabled()) return;
        CompoundTag tag = entityData.get(DEBUG).copy();
        switch (type) {
            case 0 -> setNoAi(disableAI);
            case 1 -> tag.putBoolean("disableGoalAI", disableAI);
            case 2 -> tag.putBoolean("disableMoveAI", disableAI);
            case 3 -> tag.putBoolean("disableLookAI", disableAI);
        }
        entityData.set(DEBUG, tag);
        if (level instanceof ServerLevel serverLevel && tickCount > 5) {
            MessageHandler.DEBUG_CHANNEL.sendToPlayers(serverLevel.getPlayers(serverPlayer -> serverPlayer.distanceTo(this) < 32),
                    new C2SDisableAIMessage(getId(), disableAI, type));
        }
    }

    public record PrehistoricGroupData(int ageInDays) implements SpawnGroupData {
    }
}
